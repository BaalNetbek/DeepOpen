How to extract debug symbols to text having GoF or similar binary:
```bash
BINARY_NAME="Galaxy on Fire 2 HD" #adjust
DEMANGLER=demumble.exe #or c++filt.exe
mkdir strdump
cd strdump
#rip all strings from binary 
strings "../$BINARY_NAME" > stringsdump.txt
#find gcc mangled symbols
grep '_Z' stringsdump.txt > mangled_functions.txt
#demangle ripped symbols
$DEMANGLER < mangled_functions.txt > demangled.h
#remove most unwanted symbols
cat demangled.h | grep -Ev 'OF|^_|^global|^OpenFeint|^vtable|^typeinfo' | grep -vi 'fmod' > temp.txt
#remove dublicate lines
awk '!seen[$0]++' temp.txt > ripped_symbols.h
rm temp.txt
sort ripped_symbols.h > ripped_symbols_sorted.h
cd ..
```
U need [demuble](https://github.com/nico/demumble) or [c++filt](https://www.man7.org/linux/man-pages/man1/c++filt.1.html).
Preferably in path.