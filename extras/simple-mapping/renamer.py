import re

class_pat = re.compile(r"^([^(\s]*\/)?(\w*)\s(?:(.*\/)*)(\w*?)$")
field_pat = re.compile(r"^(.*?\/*?)(\w*)\.(\w*)\s(\[?L?(?:\w+\/?\w+)*;?);?\s(\w*)$")
method_pat = re.compile(r"^(.*?\/*?)(\w*)\.(.*?)\((.*?)\)(.*?)\s(.*?)$")

CLASS_PATH_OLD = 1
CLASS_NAME_OLD = 2

CLASS_PATH_NEW = 3
CLASS_NAME_NEW = 4

FIELD_NAME_OLD = 3
FIELD_TYPE_OLD = 4
FIELD_NEW_NAME = 5

METH_NAME_OLD = 3
METH_ARG_OLD = 4
METH_RET_OLD = 5
METH_NAME_NEW = 6

# example call
# renamer -c a com.class.Runner
# renamer -f int a.b.a varNamed

class MapClass():
    def __init__(self, match):
        self.class_path_old = match.group(CLASS_PATH_OLD)
        self.class_name_old = match.group(CLASS_NAME_OLD)
        self.class_path_new = match.group(CLASS_PATH_NEW)
        self.class_name_new = match.group(CLASS_NAME_NEW)
        
    def __str__(self):
        return (
            self.class_path_old + ', ' 
            + self.class_name_old + ', ' 
            + self.class_path_new + ', '
            + self.class_name_new
        )
class MapField():
    def __init__(self, match):
        self.class_path_old = match.group(CLASS_PATH_OLD)
        self.class_name_old = match.group(CLASS_NAME_OLD)
        self.field_name_old = match.group(FIELD_NAME_OLD)
        ##continue

class MapMeth():
    def __init__(self, match):
        pass
        #continue
class Renamer():
    def parse_mapping_file(self, file_path):
        #families = {}
        self.file_path = file_path
        with open(file_path, 'r') as f:
            lines = f.readlines()
        
        if type == 'c':
            pattern = class_pat
            map_object = MapClass()
        if type == 'f':
            pattern = field_pat

        if type == 'm':
            pattern = method_pat

        parsed_lines = []
        for line_num, line in enumerate(lines, 1):
            line = line.strip()
            match = field_pat.match(line)
            if match:
                parsed_lines.append(MapField(match))
            else:
                match = method_pat.match(line)
                if match:
                    parsed_lines.append(MapMeth(match))
                else:
                    match = class_pat.match(line)
                    if match:
                        parsed_lines.append(MapClass(match))
                    else:
                        print("Parsing stopped at line: %d"%line_num)
                        break

        return parsed_lines, lines
        print

if __name__ == "__main__": 
    from os.path import dirname
    new_mapping = dirname(dirname(dirname(__file__))) + r'/Recaf/GoF2/JSR_1.0.4/GoF2_JSR_1.0.4.mapping'
    renamer = Renamer()
    parsed, lines = renamer.parse_mapping_file(new_mapping) 
    for p in parsed:
        print (p)
        print('\n')