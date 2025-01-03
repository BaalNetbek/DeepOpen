package AbyssEngine;

public class Group extends GraphNode {
   protected GraphNode var_3e = null;

   public final void sub_25(GraphNode var1) {
      if (var1 != null && var1 != this) {
         GraphNode var3 = var1;
         GraphNode var2 = this.var_3e;

         boolean var10000;
         while(true) {
            if (var2 == null) {
               var10000 = false;
               break;
            }

            if (var2 == var3) {
               var10000 = true;
               break;
            }

            var2 = var2.parent;
         }

         if (!var10000) {
            var1.meshGroup = this;
            var1.parent = this.var_3e;
            this.var_3e = var1;
         }
      }

   }

   public final void sub_7f(GraphNode var1) {
      if (var1 != null && this.var_3e != null) {
         if (var1 == this.var_3e) {
            this.var_3e = this.var_3e.parent;
            var1.meshGroup = null;
            var1.parent = null;
         } else {
            for(GraphNode var2 = this.var_3e; var2 != null; var2 = var2.parent) {
               if (var2.parent == var1) {
                  var2.parent = var2.parent.parent;
                  var1.meshGroup = null;
                  var1.parent = null;
                  return;
               }
            }

         }
      }
   }

   public final GraphNode sub_97() {
      return this.var_3e;
   }

   public final void sub_11b(Camera var1, Class_db var2) {
      if (this.draw) {
         GraphNode var3;
         switch(var1.sub_14a(this.var_19f)) {
         case 1:
            for(var3 = this.var_3e; var3 != null; var3 = var3.parent) {
               var3.sub_11b(var1, var2);
            }

            return;
         case 2:
            for(var3 = this.var_3e; var3 != null; var3 = var3.parent) {
               var3.sub_b2(var1, var2);
            }

            return;
         }
      }

   }

   public final void sub_b2(Camera var1, Class_db var2) {
      if (this.draw) {
         for(GraphNode var3 = this.var_3e; var3 != null; var3 = var3.parent) {
            var3.sub_b2(var1, var2);
         }
      }

   }

   public void sub_109(boolean var1) {
      if (this.var_ee || var1) {
         if (this.var_c1 || var1) {
            if (this.meshGroup != null) {
               this.var_14c = this.meshGroup.var_14c.sub_6a(this.currentTransform, this.var_14c);
            } else {
               this.var_14c.set(this.currentTransform);
            }
         }

         GraphNode var2;
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

   protected final String getString(String var1, int var2) {
      for(int var3 = 0; var3 < var2; ++var3) {
         var1 = var1 + "  ";
      }

      var1 = var1 + " |\t" + this.var_19f.center.x + "\t\t" + this.var_19f.center.y + "\t\t" + this.var_19f.center.z + "\t\t" + this.var_19f.radius + "\n";
      ++var2;

      for(GraphNode var4 = this.var_3e; var4 != null; var4 = var4.parent) {
         var1 = var4.getString(var1, var2);
      }

      return var1;
   }

   public final void sub_980(int var1, int var2) {
      for(GraphNode var3 = this.var_3e; var3 != null; var3 = var3.parent) {
         var3.sub_980(var1, var2);
      }

   }

   public final void sub_9aa(byte var1) {
      for(GraphNode var2 = this.var_3e; var2 != null; var2 = var2.parent) {
         var2.sub_9aa(var1);
      }

   }

   public final void sub_a04() {
      for(GraphNode var1 = this.var_3e; var1 != null; var1 = var1.parent) {
         var1.sub_a04();
      }

   }

   public final boolean sub_a37() {
      boolean var1 = false;

      for(GraphNode var2 = this.var_3e; var2 != null; var2 = var2.parent) {
         var1 |= var2.sub_a37();
      }

      return var1;
   }
}
