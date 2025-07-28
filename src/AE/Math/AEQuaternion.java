package AE.Math;

public final class AEQuaternion {
   public int x = 0;
   public int y = 0;
   public int z = 0;
   public int w = 4096;

   private AEQuaternion(int var1, int var2, int var3, int var4) {
   }

   public AEQuaternion() {
      this(0, 0, 0, 4096);
   }

   public final String toString() {
      return "AEQuaternion | " + this.x + ",\t" + this.y + ",\t" + this.z + ",\t" + this.w;
   }
}
