![open deep2](https://github.com/user-attachments/assets/9069af36-9420-4c6a-8735-0cd8c33d40a8)

Attempt to recreate source code of Fishlabs games and their common core Abyss Engine for education and enternainment purpouses.

Goals:
* Deobufuscating GoF2 classes, fields and methods names - in progress (see below)
    * **2024.10.01**<br/>
Gof2 debuscation progress:<br/>
Deobfuscated names in total:  15% <br/>
Deobfuscated field names:     16% <br/>
Deobfuscated method names:    13% <br/>
Deobfuscated classe names:    35% <br/>
* Deobufuscating ther inner variables of methods - touched
* Porting to other platforms. - propably will never happed

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

For inspiration with renaming: <br/>
/extras/gof2 1.0.1 ios symbols/
