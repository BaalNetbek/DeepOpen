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

def proguard_to_simple_mapping(input_file: str, output_file: str) -> None:
    current_class = ''
    current_class_new = ''
    
    with open(input_file, 'r') as infile, open(output_file, 'w') as outfile:
        for line in infile:
            line = line.strip()
            
            if '->' in line and line.endswith(':'):
                # Class
                old_class, new_class = line[:-1].split(' -> ')
                current_class = old_class.replace('.', '/')
                current_class_new = new_class.replace('.', '/')
                outfile.write(f"{current_class} {current_class_new}\n")
            
            elif '->' in line and not line.endswith(':'):
                # Field or method
                old, new = line.split(' -> ')
                old_parts = old.split()
                
                if '(' in old:
                    # Method
                    return_type = convert_type(old_parts[0])
                    method_name = old_parts[1].split('(')[0]
                    args = old_parts[1].split('(')[1].split(')')[0]
                    converted_args = parse_arguments(args)
                    outfile.write(f"{current_class}.{method_name}({converted_args}){return_type} {new}\n")
                else:
                    # Field
                    field_type = convert_type(old_parts[0])
                    field_name = old_parts[1]
                    outfile.write(f"{current_class}.{field_name} {field_type} {new}\n")

# Example usage
if __name__ == "__main__":
    input_file = r"[Path to Proguard mapping file (.txt)]" 
    output_file = r"[Output mapping file path]" 
    proguard_to_simple_mapping(input_file, output_file)
    print("Job done.")