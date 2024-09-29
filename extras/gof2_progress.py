import re
from os.path import dirname

def count_special_words(file_path):
    names_count = lines_count = 0
    fields = classes = methods = 0
    obfuscated_fields = obfuscated_classes = obfuscated_methods = 0
    with open(file_path, 'r') as file:
        for line in file:
            words = line.split()
            if len(words) > 0:
                names_count += 1
                mapped_name = words[-1].split('/')[-1]
                if len(words) == 3:
                    fields += 1
                    if mapped_name.startswith("var_"):
                        obfuscated_fields += 1
                elif len(words) == 2 and re.match(r'(?=.*\(*\)).', words[0]):
                    methods += 1
                    if mapped_name.startswith("sub_"):
                        obfuscated_methods += 1
                elif len(words) == 2:
                    classes += 1
                    if mapped_name.startswith("Class_"):
                        obfuscated_classes += 1
                else:
                    print(f"Detection failed in line {lines_count}: {words}")

    total_obfuscated = obfuscated_fields + obfuscated_classes + obfuscated_methods
    
    deobfuscated_total = 100-(total_obfuscated / names_count) * 100 if names_count > 0 else 0
    deobfuscated_fields = 100-(obfuscated_fields / fields) * 100 if fields > 0 else 0 
    deobfuscated_methods = 100-(obfuscated_methods / methods) * 100 if methods > 0 else 0 
    deobfuscated_classes = 100-(obfuscated_classes / classes) * 100 if classes > 0 else 0 
    print(f'Deobfuscated names in total: \t{deobfuscated_total:.2f}%')
    print(f'Deobfuscated field names: \t{deobfuscated_fields:.2f}%')
    print(f'Deobfuscated method names: \t{deobfuscated_methods:.2f}%')
    print(f'Deobfuscated classe names: \t{deobfuscated_classes:.2f}%') 

file_path = dirname(dirname(__file__)) + r'\Recaf\GoF2\GoF2_JSR_1.0.4.mapping'
count_special_words(file_path)
input("\nAny key to exit...")
