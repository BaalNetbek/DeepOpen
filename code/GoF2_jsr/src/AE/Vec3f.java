package AE;

public final class Vec3f {
   public float x;
   public float y;
   public float z;

   public Vec3f() {
      this.x = this.y = this.z = 0.0F;
   }

   public final String toString() {
      return "x: " + this.x + "   y: " + this.y + "   z: " + this.z;
   }
}
