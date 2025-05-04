package GoF2;

import java.util.Vector;

public final class SystemPathFinder {
   public final int[] getSystemPath(SolarSystem[] var1, int var2, int var3) {
      Node[] var4 = new Node[var1.length];

      int var5;
      for(var5 = 0; var5 < var1.length; ++var5) {
         var4[var5] = new Node(this, var5);
      }

      int var6;
      for(var5 = 0; var5 < var1.length; ++var5) {
         int[] var7;
         if ((var7 = var1[var5].getNeighbourSystems()) != null) {
            for(var6 = 0; var6 < var7.length; ++var6) {
               if (Status.getVisibleSystems()[var1[var7[var6]].getId()]) {
                  var4[var5].neighbors.addElement(var4[var7[var6]]);
               }
            }
         }
      }

      Node var10001 = var4[var2];
      Node var10 = var4[var3];
      Node var9 = var10001;
      Vector var8 = new Vector();
      Vector var11;
      (var11 = new Vector()).addElement(var9);
      var9.parentNode = null;

      Vector var10000;
      label56:
      while(true) {
         if (!var11.isEmpty()) {
            var9 = (Node)var11.firstElement();
            var11.removeElementAt(0);
            if (var9 == var10) {
               var10000 = constructPath(var10);
               break;
            }

            var8.addElement(var9);
            var6 = 0;

            while(true) {
               if (var6 >= var9.neighbors.size()) {
                  continue label56;
               }

               Node var13 = (Node)var9.neighbors.elementAt(var6);
               if (!var8.contains(var13) && !var11.contains(var13)) {
                  var13.parentNode = var9;
                  var11.addElement(var13);
               }

               ++var6;
            }
         }

         var10000 = null;
         break;
      }

      var11 = var10000;
      int[] var12 = null;
      if (var11 != null && var11.size() > 0) {
         (var12 = new int[var11.size() + 1])[0] = var2;

         for(int var14 = 1; var14 < var12.length; ++var14) {
            var12[var14] = ((Node)var11.elementAt(var14 - 1)).systemIndex;
         }
      }

      return var12;
   }

   private static Vector constructPath(Node var0) {
      Vector var1;
      for(var1 = new Vector(); var0.parentNode != null; var0 = var0.parentNode) {
         var1.insertElementAt(var0, 0);
      }

      return var1;
   }
}
