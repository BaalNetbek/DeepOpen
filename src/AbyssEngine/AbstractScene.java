package AbyssEngine;

public abstract class AbstractScene {
   public abstract void renderScene(int var1);

   public abstract void handleKeystate(int var1);

   public abstract void loadResources();

   public abstract void freeResources();

   public abstract boolean isLoaded();
}
