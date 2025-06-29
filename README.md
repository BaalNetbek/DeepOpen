<p align="center">
  <img src="/extras/DeepOpen.png" />
</p>

Attempt to recreate code of Fishlabs games and their common core Abyss Engine for education and entertainment purposes, by revese engineering.

Currently the essence of this repo is decompiled code of [GoF2](/code/GoF2_jsr/) version 1.0.4 (the last update for J2ME published for free by the fishes)
___
<p align="center">
  <img src="/extras/logger/progress_chart.png" />
</p>

All names I renamed classes, methods and fields, but there are still bad renames (espcialy marked with _ sign at the end). Also local variables (declared inside methods) have generic names.
___
### Goals
1. Getting fully readable java code
    * deobfuscating GoF2 classes, fields and methods names using Recaf
    * deobfuscating insides of methods - touched (.aem loading class implemented in [GoF2:Reforged](https://drive.google.com/drive/folders/198TUt7ERvaK7kdShcHvn_otm48rbBnKV)<sup>1</sup>)
    * Making documentation for the code.
2. Porting to other platforms, modding java...
___

### Want to contribute?
You can:
* Look for #TODO in the code. (please don't use refactor whole files in one commit)
* Look for names that make no sense.
* Document the code with javadoc.
* Deobfuscate other (than GoF2) Abyss Engine games. First ones in queue would be Deep and GoF.
* Write an issue if you want to discuss something or need help.
* Document bugs.

For analysis used mainly:\
 [Col-E/Recaf](https://github.com/Col-E/Recaf)\
 [shinovon/KEmulator](https://github.com/shinovon/KEmulator)\
 [NationalSecurityAgency/ghidra](https://github.com/NationalSecurityAgency/ghidra) (GoF binaries from other platforms)

More: \
 [Renaming inspiration](/extras/gof2-1.0.1-ios-symbols/simple-mapping)\
 [Lookup tables for GoF2](https://docs.google.com/spreadsheets/u/1/d/e/2PACX-1vRjJFtnrG9-7vdqHtHtPCu0Tg7C-1A89lxo434_7fgEguS9I6O1u3wcRmoWnHEhgUP2Mbd9EMIzAPJA/pubhtml#)
