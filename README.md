<p align="center">
  <img src="/extras/DeepOpen.png" />
</p>

Attempt to recreate code of Fishlabs games and their common core Abyss Engine for education and entertainment purposes, by revese engineering.

Currently the essence of this repo is the [name mapping for GoF2](/Recaf/GoF2/GoF2_JSR_1.0.4.mapping) version 1.0.4 (the last update for J2ME published for free by the fishes)
___
<p align="center">
  <img src="/extras/logger/progress_chart.png" />
</p>

___
### Goals
1. Getting fully readable java code
    * deobfuscating GoF2 classes, fields and methods names using Recaf - now
    * deobfuscating insides of methods - touched (.aem loading class implemented in [GoF2:Reforged](https://drive.google.com/drive/folders/198TUt7ERvaK7kdShcHvn_otm48rbBnKV)$^1$)
    * Making documentation for the code.
2. Porting to other platforms, modding java...
___

### Want to contribute?

Software requirements:\
Operating system:
* Windows or Linux or MacOS may work too.

Decompilation, static analysis, deobfuscation:
* **Recaf 4**: ([manual](https://github.com/Col-E/Recaf-Launcher/blob/master/MANUAL.md)) Java 22+ 64-bit

Testing and dynamic analysis:
* **KEmulator** ([nnmod](https://github.com/shinovon/KEmulator)) - standalone emulator with many options
* **EclpiseME** ([video guide](www.youtube.com/watch?v=udm5OtkRkew), [debuging fix](https://web.archive.org/web/20080120104502/https://eclipseme.org/docs/configuring.html#step2)) - J2ME development extension for Eclipse IDE, allows breakpoint, step debuging

Deobfuscation procedure (what I do):

1. In Recaf:
      - *File* -> *Open workspace* -> load [/Recaf/GoF2/GoF2_JSR_1.0.4.jar](/Recaf/GoF2/GoF2_JSR_1.0.4.jar)
      - *Mapping* -> *Apply* -> *Simple* -> load [/Recaf/GoF2/GoF2_JSR_1.0.4.mapping](/Recaf/GoF2/GoF2_JSR_1.0.4.mapping)
      - ***Alternatively to the above if you don't have Github account skip above***$^3$: 
      - (optional) do the *renaming: *context menu* or *Alt+R* 
      - *File* -> *Export application* -> save as a .jar file
2. In KEmulator:
    - Test $^2$ by running the .jar
    - Dynamic analysis:  tab *View*-> Watches/Methods/Memory View/Log/Options...*
3. In Recaf:
    - If testing went well: *Mapping* -> *Export* -> *Simple* -> save [/Recaf/GoF2/GoF2_JSR_1.0.4_unordered.jar](/Recaf/GoF2_JSR_1.0.4_unordered.mapping)$^0$

$^3$ Just load the [GoF2_deobfuscation.jar](/Recaf/GoF2/GoF2_deobfuscation.jar) deobfuscate, save the mapping, send it to me and I will be able to apply it on top of main mapping. \
$^2$ If you screwed up or Recaf breaks (happens), but have unsaved renaming, go to step 3. and Save the mapping as without overwritting the old one. Try step 1. it or try fixing the new mapping in text editor.\
$^1$ Biggest GoF2 Mod as of now (2024-10-20). Source code I haven't seen, haven't accessed and will not be published. \
$^0$ 'unordered' becouse Recaf shuffles lines in mapping file on export so they should be sorted before commiting using [sorter.py](/extras/sorter/sorter.py). Better use my [pre-commit hook](/extras/hooks/pre-commit).

More: \
[Pre Recaf attempts](/src/README.md)\
[Renaming inspiration](/extras/gof2-1.0.1-ios-symbols/simple-mapping)\
[Lookup tables for GoF2](https://docs.google.com/spreadsheets/u/1/d/e/2PACX-1vRjJFtnrG9-7vdqHtHtPCu0Tg7C-1A89lxo434_7fgEguS9I6O1u3wcRmoWnHEhgUP2Mbd9EMIzAPJA/pubhtml#)  (work in progress)
