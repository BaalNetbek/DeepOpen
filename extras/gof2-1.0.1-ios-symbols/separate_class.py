with open("ripped_symbols.txt", 'r') as fi, open('class_sperated_symbols.txt', 'w') as fo:
    lines = fi.readlines()
    for line in lines:
        s = 0
        for i, c in enumerate(line):
            if c == '(': break
            if line[i:i+2] == "::": s = i
        if s > 1:
            line = line[:s]+'%'+line[2+s:]    
        else:
            line = '%'+line    
        fo.write(line)    