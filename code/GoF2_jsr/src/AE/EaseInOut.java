package AE;

import AE.Math.AEMath;

public final class EaseInOut {
   private int minValue;
   private int range;
   private int phase;
   private int currentValue;

   public EaseInOut(int var1, int var2) {
      this.minValue = var1;
      this.range = var2 - var1;
      this.phase = 3072;
   }

   public final void Increase(int var1) {
      this.phase += var1;
      this.phase = this.phase > 5120 ? 5120 : this.phase;
      this.currentValue = (((AEMath.sin(this.phase) >> 1) + 2048) * this.range >> 12) + this.minValue;
   }

   public final void SetRange(int var1, int var2) {
      this.minValue = var1;
      this.range = var2 - var1;
      this.phase = 3072;
   }

   public final int GetValue() {
      return this.currentValue;
   }

   public final boolean IsAtMaxPhase(boolean var1) {
      return this.phase == 5120;
   }
}
