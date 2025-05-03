import re

class InheritanceVerifier:
    def __init__(self):
        self.reg_pattern = re.compile(r"^([\w+/]*\w+)\.(\w+)(\(.*\)\[?\w+;?)\s+(\w+;*)\s*$") 
        self.families = []
        self.valid = True
        self.file_path = ""
    

    def parse_mapping_file(self, file_path):
        families = {}
        self.file_path = file_path
        with open(file_path, 'r') as f:
            for line_num, line in enumerate(f, 1):
                line = line.strip()
                match = self.reg_pattern.match(line)
                if match:
                    class_name = match.group(1)
                    descriptor = match.group(2) + match.group(3)
                    mapped_name = match.group(4)
                    
                    key = (descriptor, mapped_name)
                    
                    if key not in families:
                        families[key] = []
                    families[key].append({
                        'line': line_num,
                        'class': class_name,
                        'descriptor': descriptor,
                        'mapped_name': mapped_name,
                        'full_line': line
                    })
        
        self.families = []
        for key, methods in families.items():
            if len(methods) <= 1:
                continue
            unique_names = {m['descriptor'] for m in methods}
            if len(unique_names) > 1:
                print(f"Warning: Family with descriptor '{key[0]}' and mapped name '{key[1]}' has differing method names: {unique_names}")
            self.families.append(methods)

    def validate_inheritance(self, new_mapping):
        def print_map_line(line_num, name, line, indent = 0):
            print(' '*indent,format(line_num, '>5'), ' ', format(name, "<25"), format(line, "<10")[:-1])

        lines = []
        with open(new_mapping, 'r') as file:
            lines = file.readlines()
            
        bad_families = []
        for fam_num, family in enumerate(self.families):
            new_name = ''
            for method in family:
                try:
                    new_line = lines[method['line']-1]
                    match = self.reg_pattern.match(new_line)
                    if new_name == '':
                        new_name = match.group(4)
                    elif new_name != match.group(4):
                        self.valid = False
                        if fam_num not in bad_families:
                            bad_families.append(fam_num)
                        break
                except Exception as e:
                    print(f"Exception {e}\nin line {new_line})")
        for i in bad_families:
            print(f"Inheritance Error of: {self.families[i][0]['full_line']}")
            for method in self.families[i]:
                line = lines[method['line']-1]
                match = self.reg_pattern.match(line)
                print_map_line(method['line'], match.group(4), line, 1)
        if bad_families == []:
            print("Inheritance verification passed for: ", self.file_path)      

    def run(self, standard_mapping, new_mapping):
        self.parse_mapping_file(standard_mapping)
        self.validate_inheritance(new_mapping)
        return self.valid

if __name__ == "__main__":  
    from os.path import dirname
    from sys import exit
    order_file = dirname(dirname(__file__)) + r'/simple-mapping/GoF2_JSR_1.0.4_fernflower.txt'
    new_mapping = dirname(dirname(dirname(__file__))) + r'/Recaf/GoF2/GoF2_JSR_1.0.4.mapping'

    ret = InheritanceVerifier().run(order_file, new_mapping)
    if ret:
        exit(1)
    exit(0)
   