import re
from os.path import dirname, abspath

def check_obfuscation_progerss(file_path, field_match = "var_", meth_match = "sub_", class_match = "Class_"):

    names_count = lines_count = 0
    fields = classes = methods = 0
    obfuscated_fields = obfuscated_classes = obfuscated_methods = 0
    with open(file_path, 'r') as file:
        print(f"Reading mapping file: {abspath(file_path)}")
        for line in file:
            words = line.split()
            if len(words) > 0:
                names_count += 1
                mapped_name = words[-1].split('/')[-1]
                if len(words) == 3:
                    fields += 1
                    if mapped_name.startswith(field_match):
                        obfuscated_fields += 1
                elif len(words) == 2 and re.match(r'(?=.*\(*\)).', words[0]):
                    methods += 1
                    if mapped_name.startswith(meth_match):
                        obfuscated_methods += 1
                elif len(words) == 2:
                    classes += 1
                    if mapped_name.startswith(class_match):
                        obfuscated_classes += 1
                else:
                    print(f"Detection failed in line {lines_count}: {words}")

    total_obfuscated = obfuscated_fields + obfuscated_classes + obfuscated_methods
    
    deobfuscated_total = 100-(total_obfuscated / names_count) * 100 if names_count > 0 else 0
    deobfuscated_fields = 100-(obfuscated_fields / fields) * 100 if fields > 0 else 0 
    deobfuscated_methods = 100-(obfuscated_methods / methods) * 100 if methods > 0 else 0 
    deobfuscated_classes = 100-(obfuscated_classes / classes) * 100 if classes > 0 else 0 


    return [deobfuscated_total, deobfuscated_classes, deobfuscated_methods, deobfuscated_fields]

if __name__ == "__main__":
    file_path = dirname(dirname(dirname(__file__))) + r'\Recaf\GoF2\GoF2_JSR_1.0.4.mapping'
    tot, clas, meth, fld = check_obfuscation_progerss(file_path)

    print(f'Deobfuscated names in total: \t{tot:.0f}%')
    print(f'Deobfuscated classe names: \t{clas:.0f}%') 
    print(f'Deobfuscated method names: \t{meth:.0f}%')
    print(f'Deobfuscated field names: \t{fld:.0f}%')

    input("\nAny enter to exit...")
