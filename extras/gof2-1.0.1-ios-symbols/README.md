This symbols were ripped from GoF binaries like:
```bash
mkdir strdump
cd strdump
#rip all strings from binary 
strings ../[BINARY NAME] > stringsdump.txt
#find gcc mangled symbols
cat stringsdump.txt | grep '_Z' > mangled_functions.h
#demangle ripped symbols
c++filt.exe < mangled_functions.txt > demangled.txt
#remove most unwanted symbols
cat demangled.h | grep -Ev 'OF|^_|^global|^OpenFeint|^vtable|^typeinfo' | grep -vi 'fmod' > temp.txt
#remove dublicate lines
cat temp.txt | awk '!seen[$0]++' > ripped_symbols.txt
del temp.txt
#sort
sort ripped_symbols.txt > ripped_symbols_sorted.txt

```