package AbyssEngine;

public final class Class_198 extends Group {
   private Class_121c[] var_16 = null;

   public final void sub_5c(Class_121c var1) {
      if (this.var_16 == null) {
         this.var_16 = new Class_121c[1];
         this.var_16[0] = var1;
      } else {
         Class_121c[] var2 = new Class_121c[this.var_16.length + 1];
         System.arraycopy(this.var_16, 0, var2, 0, this.var_16.length);
         var2[this.var_16.length] = var1;
         this.var_16 = var2;
      }
   }

   public final void sub_109(boolean var1) {
      GraphNode var2;
      if (this.var_16 != null) {
         if (this.var_ee || var1) {
            if (this.var_c1 || var1) {
               if (this.meshGroup != null) {
                  this.var_14c = this.meshGroup.var_14c.sub_6a(this.currentTransform, this.var_14c);
               } else {
                  this.var_14c.set(this.currentTransform);
               }
            }

            var2 = this.var_3e;

            while(true) {
               if (var2 == null) {
                  this.var_19f.sub_7f(this.var_14c.getPositionX(), this.var_14c.getPositionY(), this.var_14c.getPositionZ(), 0);

                  for(var2 = this.var_3e; var2 != null; var2 = var2.parent) {
                     this.var_19f.sub_52(var2.var_19f);
                  }

                  this.var_ee = false;
                  this.var_c1 = false;
                  break;
               }

               var2.sub_109(this.var_c1 || var1);
               var2 = var2.parent;
            }
         }

         for(int var3 = this.var_16.length - 1; var3 >= 0; --var3) {
            if (this.var_16[var3].sub_356()) {
               this.var_16[var3].sub_2aa();
            }
         }
      }

      if (this.var_ee || var1) {
         if (this.var_c1 || var1) {
            if (this.meshGroup != null) {
               this.var_14c = this.meshGroup.var_14c.sub_6a(this.currentTransform, this.var_14c);
            } else {
               this.var_14c.set(this.currentTransform);
            }
         }

         for(var2 = this.var_3e; var2 != null; var2 = var2.parent) {
            var2.sub_109(this.var_c1 || var1);
         }

         this.var_19f.sub_7f(this.var_14c.getPositionX(), this.var_14c.getPositionY(), this.var_14c.getPositionZ(), 0);

         for(var2 = this.var_3e; var2 != null; var2 = var2.parent) {
            this.var_19f.sub_52(var2.var_19f);
         }

         this.var_ee = false;
         this.var_c1 = false;
      }

   }
}
