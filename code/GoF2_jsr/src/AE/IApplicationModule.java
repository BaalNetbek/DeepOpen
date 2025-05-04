package AE;

public abstract class IApplicationModule {
   public abstract void renderScene(int var1);

   public abstract void handleKeystate(int var1);

   public abstract void OnInitialize();

   public abstract void OnRelease();

   public abstract boolean isLoaded();
}
