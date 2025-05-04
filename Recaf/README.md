used **Recaf 4**: ([manual](https://github.com/Col-E/Recaf-Launcher/blob/master/MANUAL.md)) Java 22+ 64-bit

Deobfuscation procedure (what I did):

1. In Recaf:
      - *File* -> *Open workspace* -> load [/Recaf/GoF2/JSR_1.0.4/GoF2_JSR_1.0.4.jar](/Recaf/GoF2/JSR_1.0.4/GoF2_JSR_1.0.4.jar)
      - *Mapping* -> *Apply* -> *Simple* -> load [/Recaf/GoF2/JSR_1.0.4/GoF2_JSR_1.0.4.mapping](/Recaf/GoF2/JSR_1.0.4/GoF2_JSR_1.0.4.mapping)
      - ***Alternatively to the above if you don't have Github account skip above***<sup>3</sup>: 
      - (optional) do the *renaming: *context menu* or *Alt+R* 
      - *File* -> *Export application* -> save as a .jar file
2. In KEmulator:
    - Test<sup>2</sup> by running the .jar
    - Dynamic analysis:  tab *View*-> Watches/Methods/Memory View/Log/Options...*
3. In Recaf:
    - If testing went well: *Mapping* -> *Export* -> *Simple* -> save [/Recaf/GoF2/JSR_1.0.4/GoF2_JSR_1.0.4_unordered.jar](/Recaf/GoF2_JSR_1.0.4_unordered.mapping)<sup>0</sup>

<sup>3</sup> Just load the [GoF2_deobfuscation.jar](/Recaf/GoF2/JSR_1.0.4/GoF2_deobfuscation.jar) deobfuscate, save the mapping, send it to me and I will be able to apply it on top of main mapping. \
<sup>2</sup> If you screwed up or Recaf breaks (happens), but have unsaved renaming, go to step 3. and Save the mapping as without overwritting the old one. Try step 1. it or try fixing the new mapping in text editor.\
<sup>1</sup> Biggest GoF2 Mod as of now (2024-10-20). Source code I haven't seen, haven't accessed and will not be published. \
<sup>0</sup> 'unordered' becouse Recaf shuffles lines in mapping file on export so they should be sorted before commiting using [sorter.py](/extras/sorter/sorter.py). Better use my [pre-commit hook](/extras/hooks/pre-commit).