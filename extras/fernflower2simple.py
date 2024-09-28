import os
import re

def convert_type(type_str):
    type_map = {
        'void': 'V',
        'int': 'I',
        'boolean': 'Z',
        'short': 'S',
        'float': 'F',
        'byte': 'B',
        'long': 'J',
        'double': 'D',
        'char': 'C',
    }
    
    if type_str in type_map:
        return type_map[type_str]
    elif type_str.endswith('[]'):
        return '[' + convert_type(type_str[:-2])
    else:
        return 'L' + type_str.replace('.', '/') + ';'

def parse_arguments(args_str):
    if not args_str:
        return ''
    args = args_str.split(',')
    return ''.join(convert_type(arg.strip()) for arg in args)

def process_file(file_path, package):
    with open(file_path, 'r') as file:
        content = file.read()

    class_pattern = r'(\w+)\s*\n\s*(?:[\w\s]+)?class (\w+)'
    field_pattern = r'// \$FF: renamed from: (\w+) ([\w\[\].]+)\s*\n\s*(?:[\w\s]+)?(\w+) (\w+);'
    method_pattern = r'// \$FF: renamed from: (\w+) \((.*?)\) ([\w\[\].]+)\s*\n\s*(?:[\w\s]+)?(\w+) (\w+)\('

    mappings = []
    old_class_name = '' #assumes 1 class per file

    package = package+'/' if len(package)>0  else ''
    # Class
    for old_name, new_name in re.findall(class_pattern, content):
        old_name = old_name.replace('.','')
        old_class_name = old_name
        #if(package!=''):
        #    print(f"{package}{old_name} {package}/{new_name}")
        mappings.append(f"{package}{old_name} {package}{new_name}")

    old_class_name = old_class_name+'.' if len(old_class_name)>0  else ''
    # field
    for old_name, old_type, _, new_name in re.findall(field_pattern, content):
        mappings.append(f"{package}{old_class_name}{old_name} {convert_type(old_type)} {new_name}")

    # method
    for old_name, args, return_type, _, new_name in re.findall(method_pattern, content):
        args_simple = parse_arguments(args)
        new_name
        return_type_simple = convert_type(return_type)
        mappings.append(f"{package}{old_class_name}{old_name}({args_simple}){return_type_simple} {new_name}")
    return mappings

def fernflower_to_simple_mapping(root_dir, output_file):
    mappings = []
    for root, _, files in os.walk(root_dir):
        for file in files:
            if file.endswith('.java'):
                file_path = os.path.join(root, file)
                package = os.path.relpath(root, root_dir).replace(os.path.sep, '/').replace('.','')
                mappings.extend(process_file(file_path, package))

    with open(output_file, 'w') as outfile:
        for mapping in mappings:
            outfile.write(f"{mapping}\n")

# Example usage
if __name__ == "__main__":
    root_dir = r"[Path to Fernflower decompiled source folder]" 
    output_file = r"[Output mapping file path]"  

    fernflower_to_simple_mapping(root_dir, output_file)
    print("Job done.")


