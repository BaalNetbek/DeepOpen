This symbols were ripped from GoF2 1.0.1 binary for IOS ("GalaxyOnFire2~.arm-6") using:
```bash
#rip all strings from binary
strings GalaxyOnFire2~.arm-6 > stringsdumpv6.txt
#find gcc mangled symbols
cat stringsdumpv6.txt | grep '_Z' > mangled_functions.txt
#demangle ripped symbols
c++filt.exe < mangled_functions.txt > demangled.txt
#remove most unwanted symbols
cat demangled.h | grep -Ev 'OF|^_|^global|^OpenFeint|^vtable|^typeinfo' | grep -vi 'fmod' > temp.txt
#remove dublicate lines
cat temp.txt | awk '!seen[$0]++' > ripped_symbols.txt
#sort
sort ripped_symbols.txt > ripped_symbols_sorted.txt

```