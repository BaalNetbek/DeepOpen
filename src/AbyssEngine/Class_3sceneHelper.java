package AbyssEngine;

import Main.GOF2MIDlet;

public final class Class_3sceneHelper {
   private AbstractScene scene = null;
   private GOF2MIDlet midlet;
   private LoadingScreen loadingScreen;

   public Class_3sceneHelper(GOF2MIDlet var1, LoadingScreen var2) {
      this.midlet = var1;
      this.loadingScreen = var2;
   }

   public final void setScene(AbstractScene var1) {
      if (this.scene != null) {
         if (this.loadingScreen != null) {
            this.loadingScreen.startLoading_(true);
         }

         LangManager.init();
         this.scene.freeResources();
      }

      this.scene = var1;
   }

   public final AbstractScene sub_28() {
      return this.scene;
   }

   public final void sub_54() {
      this.midlet.quit();
   }

   public final void handleKeystate(int var1) {
      if (this.scene != null) {
         this.scene.handleKeystate(var1);
      }

   }

   public final void renderScene_andLoad_(int var1) {
      if (this.scene != null) {
         if (this.scene.isLoaded()) {
            this.scene.renderScene(var1);
            return;
         }

         this.scene.loadResources();
         if (this.scene.isLoaded() && this.loadingScreen != null) {
            this.loadingScreen.close();
         }

         System.gc();
      }

   }
}
