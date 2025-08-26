import re

class_pat = re.compile(r"^(([^(\s]*\/)?(\w*))\s(?:(.*\/)*)(\w*?)$")
field_pat = re.compile(r"^((.*?\/*?)(\w*)\.(\w*)\s(\[*?L?(?:\w+(?:\/?\w+)*)*;?);?)\s(\w*)$")
method_pat = re.compile(r"^((.*?\/*?)(\w*)\.(.*?)\((.*?)\)(.*?))\s(.*?)$")
type_pat = re.compile(r"(\[?L\w+(?:\/\w+)*;)") 
# Łapie pojedynczo LTYP;

KEY = 1
CLASS_PATH_OLD = 2
CLASS_NAME_OLD = 3
CLASS_PATH_NEW = 4
CLASS_NAME_NEW = 5

FIELD_NAME_OLD = 4
FIELD_TYPE_OLD = 5
FIELD_NEW_NAME = 6

METH_NAME_OLD = 4
METH_ARG_OLD = 5
METH_RET_OLD = 6
METH_NAME_NEW = 7

class MapObj():
    def __init__(self, match):
        self.key = match.group(KEY)
        self.remap_name: str = None

class MapClass(MapObj):
    def __init__(self, match):
        super().__init__(match)
        self.class_path_old = match.group(CLASS_PATH_OLD) or ""
        self.class_name_old = match.group(CLASS_NAME_OLD)
        self.class_path_new = match.group(CLASS_PATH_NEW) or ""
        self.class_name_new = match.group(CLASS_NAME_NEW)

    def __str__(self):
        return f"CLASS: {self.class_path_old}{self.class_name_old} -> {self.class_path_new}{self.class_name_new}"


class MapField(MapObj):
    def __init__(self, match):
        super().__init__(match)
        self.class_path_old = match.group(CLASS_PATH_OLD) or ""
        self.class_name_old = match.group(CLASS_NAME_OLD)
        self.field_name_old = match.group(FIELD_NAME_OLD)
        self.field_type_old = match.group(FIELD_TYPE_OLD)
        self.field_name_new = match.group(FIELD_NEW_NAME)

    def __str__(self):
        return f"FIELD: {self.class_path_old}{self.class_name_old}.{self.field_name_old}:{self.field_type_old} -> {self.field_name_new}"


class MapMeth(MapObj):
    def __init__(self, match):
        super().__init__(match)
        self.class_path_old = match.group(CLASS_PATH_OLD) or ""
        self.class_name_old = match.group(CLASS_NAME_OLD)
        self.meth_name_old = match.group(METH_NAME_OLD)
        self.meth_arg_old = match.group(METH_ARG_OLD)
        self.meth_ret_old = match.group(METH_RET_OLD)
        self.meth_name_new = match.group(METH_NAME_NEW)

    def __str__(self):
        return f"METHOD: {self.class_path_old}{self.class_name_old}.{self.meth_name_old}({self.meth_arg_old}){self.meth_ret_old} -> {self.meth_name_new}"

    
class SimpleParser():
    def parse_mapping(self, file_path):
        self.file_path = file_path
        with open(file_path, 'r') as f:
            lines = f.readlines()


        parsed_lines = {}
        for line_num, line in enumerate(lines, 1):
            line = line.strip()
            if not line or line.startswith("#"):
                continue  # skip empty/comments

            match = field_pat.match(line)
            if match:
                map_obj = MapField(match)
                parsed_lines[map_obj.key] = map_obj
                continue

            match = method_pat.match(line)
            if match:
                map_obj = MapMeth(match)
                parsed_lines[map_obj.key] = map_obj
                continue

            match = class_pat.match(line)
            if match:
                map_obj = MapClass(match)
                parsed_lines[map_obj.key] = map_obj
                continue

            raise(Exception(f"Parsing stopped at line {line_num}: {line}"))
            # print(f"Parsing stopped at line {line_num}: {line}")
            # break

        return lines, parsed_lines
    #def m (self, type: str, old:str , new:str):

class ABAC2BC():
    def combine_mappings(self, AB: dict, AC: dict):
        A = AB.keys()
        for C in AC.values():
            for B in AB.values():

        # w AC trzeba znaleźć wszystko co da się z mapować


        
if __name__ == "__main__":
    from os.path import dirname

    new_mapping = dirname(dirname(dirname(__file__))) + r'/Recaf/GoF2/JSR_1.0.4/GoF2_JSR_1.0.4.mapping'
    renamer = SimpleParser()
    lines, parsed = renamer.parse_mapping(new_mapping)
    # for p in parsed.values():
    #     print(p)
    ABAC2BC().combine_mappings(parsed, parsed)