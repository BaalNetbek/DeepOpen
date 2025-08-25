<p align="center">
  <img src="/extras/DeepOpen.png" />
</p>

Attempt to recreate source code of Fishlabs games and their common core Abyss Engine, by revese engineering, for education and entertainment purposes.

Currently the essence of this repo is decompiled code of [GoF2](/code/GoF2_jsr/) version 1.0.4 (the last update for J2ME published for free by the Fihlabs in 2009). Which can be [compiled and built](./code/build/) back. 
___
<p align="center">
  <img src="/extras/logger/progress_chart.png" />
</p>

___
### Goals
1. Getting fully readable java code
    * deobfuscating GoF2 classes, fields and methods names using Recaf (done, still needs some fixing)
    * deobfuscating insides of methods (in progress)
    * Making documentation for the code. (starterd, low priority)
2. Porting to other platforms, modding java...
  * see [branch lighting test](https://github.com/BaalNetbek/DeepOpen/tree/lighting-test)
___

### How to contribute?
Don't:
* Don't reformat and otherwise refactor in one pull request as it makes it hard to verify changes.

You can:
* Look for TODO in the code.
* Rename for names that make no sense.
* Document the code with javadoc.
* Deobfuscate other (than GoF2) Abyss Engine games. I suggest Deep or GoF3D.
* Write an issue.
* Document bugs.
* Make tools related reverse engineering modding Abyss Engine games.
* Port reversed games to other platforms


For analysis used mainly:\
 [Col-E/Recaf](https://github.com/Col-E/Recaf)\
 [shinovon/KEmulator](https://github.com/shinovon/KEmulator)\
 [NationalSecurityAgency/ghidra](https://github.com/NationalSecurityAgency/ghidra) (GoF binaries from other platforms)\
 [MinecraftForge/Srg2Source](https://github.com/MinecraftForge/Srg2Source)\
 [FabricMC/Matcher](https://github.com/FabricMC/Matcher)


More: \
 [Renaming inspiration](/extras/gof2-1.0.1-ios-symbols/simple-mapping)\
 [Lookup tables for GoF2](https://docs.google.com/spreadsheets/u/1/d/e/2PACX-1vRjJFtnrG9-7vdqHtHtPCu0Tg7C-1A89lxo434_7fgEguS9I6O1u3wcRmoWnHEhgUP2Mbd9EMIzAPJA/pubhtml#)\
 [other extras](/extras/)\
 [GoF2:Reforged](https://drive.google.com/drive/folders/198TUt7ERvaK7kdShcHvn_otm48rbBnKV) (interesting mod)
