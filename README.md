
![Deep Open](/Recaf/extras/DeepOpen.png)

Attempt to recreate source code of Fishlabs games and their common core Abyss Engine for education and enternainment purpouses.

Goals:
* Deobufuscating GoF2 classes, fields and methods names using Recaf - now
* Deobufuscating ther inner variables of methods - touched
* Porting to other platforms. - propably will never happed

![progress chart](/Recaf/extras/logger/progress_chart.png)

Want to contribute?
<ul>
Software requirements:<br/>
**Recaf 4**<br/>
emulator (optional):<br/> 
KEmulator - testing and dynamic analysis (I use latest [nnmod](https://nnp.nnchan.ru/kem/) but any not ancient verions will do)

Instrucions:
1. Install [Recaf 4](https://github.com/Col-E/Recaf-Launcher/blob/master/MANUAL.md)
2. In Recaf:
    - *File* -> *Open workspace* -> load /Recaf/GoF2/[GoF2_JSR_1.0.4.jar](/Recaf/GoF2/GoF2_JSR_1.0.4.jar)
    - *Mapping* -> *Apply* -> *Simple* -> load /Recaf/GoF2/[GoF2_JSR_1.0.4.mapping](/Recaf/GoF2/GoF2_JSR_1.0.4.mapping)
    - *File* -> *Export application* -> save as a .jar file
    - Test buy running the .jar with KEmulator
    - Dynamic analysis:  tab *View*->  *Watches/Methods/Memory View/Log/Options...*
    - If testing went well: *Mapping* -> *Export* -> *Simple*
</ul>

More: <br/>
[Pre Recaf attempts](/Recaf/src/README.md)<br/>
[Renaming inspiration](/Recaf/extras/gof2-1.0.1-ios-symbols/README.md)
