#in a text file regex search for r"(.*\h)[^\s]*" if sort the matches into into arrays by common group 1, like dublicates[][] :str

import re
from collections import defaultdict

def find_duplicate_groups(filename):
    groups = defaultdict(list)
    
    with open(filename, 'r') as file:
        lines = file.readlines()
    for line_num, line in enumerate(lines, start=1):
        matches = re.finditer(r"(.* )([^\s]*)", line)
        for match in matches:
            group1 = match.group(1)
            groups[group1].append((match.group(0), match.group(2), line_num))
    
    removed_lines = []
    for key, duplicates in groups.items():
        if len(duplicates) > 1:

            for i, line in enumerate(duplicates):
                if len(duplicates) == 1: 
                    break
                if line[1][:4] in ('sub_', 'var_') or line[1][:6] == 'Class_':
                    duplicates.pop(i)
                    removed_lines.append((line[2],line[0]))
            if len(duplicates) > 1:
                print(f"UNSOLVED DUBLICATE: {duplicates}")
    
    if len(removed_lines) > 0:
        import shutil
        copy_name = filename + '.bak'
        shutil.copyfile(filename, copy_name)
        
        print(f"REMOVED LINES:")
        for line in removed_lines:
            print(f"{line[0]}\t{line[1]}")
        removed_indices = [line[0] for line in removed_lines]
        
        print(f"Backed up unsorted before automatic cleanup: {copy_name}")
        
        with open(filename+".test", 'w') as file:
            for i, line in enumerate(lines):
                if i+1 not in removed_indices:
                    file.write(line)
        
if __name__ == "__main__":
    from os.path import dirname
    from sys import exit
    mapping = dirname(dirname(dirname(__file__))) + r'\Recaf\GoF2\V3_1.0.4\GoF2_V3_1.0.4.mapping'
    find_duplicate_groups(mapping)

