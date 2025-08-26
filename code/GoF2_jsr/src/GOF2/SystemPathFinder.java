package GOF2;

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
                    if (Status.getSystemVisibilities()[var1[var7[var6]].getIndex()]) {
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

            for(int i = 1; i < var12.length; ++i) {
                var12[i] = ((Node)var11.elementAt(i - 1)).systemIndex;
            }
        }

        return var12;
    }
// JADX alternative
// public final class SystemPathFinder {
//     public final int[] getSystemPath(GoF2.SolarSystem[] solarSystemArr, int i, int i2) {
//         java.util.Vector vectorConstructPath;
//         GoF2.Node[] nodeArr = new GoF2.Node[solarSystemArr.length];
//         for (int i3 = 0; i3 < solarSystemArr.length; i3++) {
//             nodeArr[i3] = new GoF2.Node(this, i3);
//         }
//         for (int i4 = 0; i4 < solarSystemArr.length; i4++) {
//             int[] neighbourSystems = solarSystemArr[i4].getNeighbourSystems();
//             if (neighbourSystems != null) {
//                 for (int i5 = 0; i5 < neighbourSystems.length; i5++) {
//                     if (GoF2.Status.getVisibleSystems()[solarSystemArr[neighbourSystems[i5]].getId()]) {
//                         nodeArr[i4].neighbors.addElement(nodeArr[neighbourSystems[i5]]);
//                     }
//                 }
//             }
//         }
//         GoF2.Node node = nodeArr[i];
//         GoF2.Node node2 = nodeArr[i2];
//         java.util.Vector vector = new java.util.Vector();
//         java.util.Vector vector2 = new java.util.Vector();
//         vector2.addElement(node);
//         node.parentNode = null;
//         while (true) {
//             if (vector2.isEmpty()) {
//                 vectorConstructPath = null;
//                 break;
//             }
//             GoF2.Node node3 = (GoF2.Node) vector2.firstElement();
//             vector2.removeElementAt(0);
//             if (node3 == node2) {
//                 vectorConstructPath = constructPath(node2);
//                 break;
//             }
//             vector.addElement(node3);
//             for (int i6 = 0; i6 < node3.neighbors.size(); i6++) {
//                 GoF2.Node node4 = (GoF2.Node) node3.neighbors.elementAt(i6);
//                 if (!vector.contains(node4) && !vector2.contains(node4)) {
//                     node4.parentNode = node3;
//                     vector2.addElement(node4);
//                 }
//             }
//         }
//         java.util.Vector vector3 = vectorConstructPath;
//         int[] iArr = null;
//         if (vector3 != null && vector3.size() > 0) {
//             int[] iArr2 = new int[vector3.size() + 1];
//             iArr = iArr2;
//             iArr2[0] = i;
//             for (int i7 = 1; i7 < iArr.length; i7++) {
//                 iArr[i7] = ((GoF2.Node) vector3.elementAt(i7 - 1)).systemIndex;
//             }
//         }
//         return iArr;
//     }


// Proycon alternative 
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
