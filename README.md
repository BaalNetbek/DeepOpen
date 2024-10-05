
![Deep Open](/extras/DeepOpen.png)

Attempt to recreate source code of Fishlabs games and their common core Abyss Engine for education and enternainment purpouses.

Currently the essence of this repo is the [name mapping for GoF2](Recaf/GoF2/GoF2_JSR_1.0.4.mapping) version 1.0.4 (the last update for J2ME published for free by the fishes)
___
![progress chart](/extras/logger/progress_chart.png)
### Goals
* Deobufuscating GoF2 classes, fields and methods names using Recaf - now
* Deobufuscating ther inner variables of methods - touched
* Porting to other platforms. - propably will never happed
___

### Want to contribute?

Software requirements:<br/>
Linux, Windows or [an other one] <br/>
**Recaf 4**: Java 22+ 64-bit <br/>
Testing and dynamic analysis (emulator):<br/> 
**KEmulator** ([nnmod](https://nnp.nnchan.ru/kem/) - has multiplatform version)

Instrucions:
1. Install [Recaf 4](https://github.com/Col-E/Recaf-Launcher/blob/master/MANUAL.md)
2. In Recaf:
    - *File* -> *Open workspace* -> load /Recaf/GoF2/[GoF2_JSR_1.0.4.jar](/Recaf/GoF2/GoF2_JSR_1.0.4.jar)
    - *Mapping* -> *Apply* -> *Simple* -> load /Recaf/GoF2/[GoF2_JSR_1.0.4.mapping](/Recaf/GoF2/GoF2_JSR_1.0.4.mapping)
    - *File* -> *Export application* -> save as a .jar file
    - Test buy running the .jar with KEmulator
    - Dynamic analysis:  tab *View*->  *Watches/Methods/Memory View/Log/Options...*
    - If testing went well: *Mapping* -> *Export* -> *Simple*


More: <br/>
[Pre Recaf attempts](/src/README.md)<br/>
[Renaming inspiration](/extras/gof2-1.0.1-ios-symbols/)
