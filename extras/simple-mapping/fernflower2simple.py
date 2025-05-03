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
    
    no_ren_class_pattern = r'[^\w]\s(?:\w+ )*class (\w+)[\w\s]*{'
    class_pattern = r'(\w+)\s*\n\s*(?:[\w\s]+)?class (\w+)'
    interface_pattern = r'(\w+)\s*\n\s*(?:[\w\s]+)?interface (\w+)'
    field_pattern = r'// \$FF: renamed from: (\w+) ([\w\[\].]+)\s*\n\s*(?:[\w\s]+)([\w\[\].]+) (\w+);'
    method_pattern = r'// \$FF: renamed from: (\w+) \((.*?)\) ([\w\[\].]+)\s*\n\s*(?:[\w\s]+)?([\w\[\].]+) (\w+)\('

    mappings = []
    old_class_name = '' #assumes 1 class per file
    package = package+'/' if len(package)>0  else ''
    
    zero_fill = lambda match: match.group(0) if match.group(1) != "class" else f"{match.group(1)}_{match.group(2).zfill(3)}"
    
    # not renamed classes
    for old_name in re.findall(no_ren_class_pattern, content):
        old_class_name = old_name
        mapping = f"{package}{old_name} {package}{old_name}"
        new_content = re.sub(r'(\w+)_(\d+)', zero_fill, mapping)
        mappings.append(new_content)
    # classes
    for old_name, new_name in re.findall(class_pattern, content):
        # old_name = old_name.replace('.','')
        old_class_name = old_name
        mapping = f"{package}{old_name} {package}{new_name}"
        new_content = re.sub(r'(\w+)_(\d+)', zero_fill, mapping)
        mappings.append(new_content)
    # interfaces    
    for old_name, new_name in re.findall(interface_pattern, content):
        # old_name = old_name.replace('.','')
        old_class_name = old_name
        mapping = f"{package}{old_name} {package}{new_name}"
        new_content = re.sub(r'(\w+)_(\d+)', zero_fill, mapping)
        mappings.append(new_content)   

    old_class_name = old_class_name+'.' if len(old_class_name)>0  else ''
    # fields
    for old_name, old_type, _, new_name in re.findall(field_pattern, content):
        mappings.append(f"{package}{old_class_name}{old_name} {convert_type(old_type)} {new_name}")

    # methods
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
    exec_good = True
    input_dir = r"C:\Users\Ismail\Desktop\GALAXY_ON_FIRE\GOF2 JAVA\Tools\FernFlower Decompiler\JAVA\jars\new\Galaxy on Fire 2 1.0.4" 
    output_file = r"C:\Users\Ismail\Desktop\GALAXY_ON_FIRE\GOF2 JAVA\Tools\FernFlower Decompiler\JAVA\jars\new\hopefuly_fernflower2.txt"  
    
    if not os.path.isdir(input_dir):
        print(f"Input dir path is not a directory or does not exist: {input_dir}")
    try:
        result = fernflower_to_simple_mapping(input_dir, output_file)
    except Exception as e:
        exec_good = False
        print(e)

    if exec_good == True: 
        print("Job done.")



