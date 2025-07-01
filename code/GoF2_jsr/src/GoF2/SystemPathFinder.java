package GoF2;

import java.util.Vector;

public final class SystemPathFinder {
    public final int[] getSystemPath(final SolarSystem[] var1, final int var2, final int var3) {
        final Node[] var4 = new Node[var1.length];

        int var5;
        for(var5 = 0; var5 < var1.length; ++var5) {
            var4[var5] = new Node(this, var5);
        }

        int var6;
        for(var5 = 0; var5 < var1.length; ++var5) {
            int[] var7 = var1[var5].getNeighbourSystems();
            if (var7 != null) {
                for(var6 = 0; var6 < var7.length; ++var6) {
                    if (Status.getVisibleSystems()[var1[var7[var6]].getId()]) {
                        var4[var5].neighbors.addElement(var4[var7[var6]]);
                    }
                }
            }
        }

        final Node var10001 = var4[var2];
        final Node var10 = var4[var3];
        Node var9 = var10001;
        final Vector var8 = new Vector();
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

                        final Node var13 = (Node)var9.neighbors.elementAt(var6);
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
// Proycon alternative without label (essentialy goto)
//   public final int[] getSystemPath(final SolarSystem[] array, final int n, final int n2) {
//        final Node[] array2 = new Node[array.length];
//        for (int i = 0; i < array.length; ++i) {
//            array2[i] = new Node(this, i);
//        }
//        for (int j = 0; j < array.length; ++j) {
//            final int[] neighbourSystems;
//            if ((neighbourSystems = array[j].getNeighbourSystems()) != null) {
//                for (int k = 0; k < neighbourSystems.length; ++k) {
//                    if (Status.getVisibleSystems()[array[neighbourSystems[k]].getId()]) {
//                        array2[j].neighbors.addElement(array2[neighbourSystems[k]]);
//                    }
//                }
//            }
//        }
//        final Node node = array2[n];
//        final Node node2 = array2[n2];
//        final Node node3 = node;
//        final Vector vector = new Vector();
//        final Vector vector2;
//        (vector2 = new Vector()).addElement(node3);
//        node3.parentNode = null;
//        while (true) {
//            while (!vector2.isEmpty()) {
//                final Node parentNode = (Node)vector2.firstElement();
//                vector2.removeElementAt(0);
//                if (parentNode == node2) {
//                    final Vector constructPath = constructPath(node2);
//                    final Vector vector3 = constructPath;
//                    int[] array3 = null;
//                    if (vector3 != null && vector3.size() > 0) {
//                        (array3 = new int[vector3.size() + 1])[0] = n;
//                        for (int l = 1; l < array3.length; ++l) {
//                            array3[l] = ((Node)vector3.elementAt(l - 1)).systemIndex;
//                        }
//                    }
//                    return array3;
//                }
//                vector.addElement(parentNode);
//                for (int n3 = 0; n3 < parentNode.neighbors.size(); ++n3) {
//                    final Node node4 = (Node)parentNode.neighbors.elementAt(n3);
//                    if (!vector.contains(node4) && !vector2.contains(node4)) {
//                        node4.parentNode = parentNode;
//                        vector2.addElement(node4);
//                    }
//                }
//            }
//            final Vector constructPath = null;
//            continue;
//        }
//    }
     

    private static Vector constructPath(Node var0) {
        Vector var1;
        for(var1 = new Vector(); var0.parentNode != null; var0 = var0.parentNode) {
            var1.insertElementAt(var0, 0);
        }

        return var1;
    }
}
