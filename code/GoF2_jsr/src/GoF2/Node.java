package GoF2;

import java.util.Vector;

final class Node {
	Vector neighbors;
	Node parentNode;
	int systemIndex;

	public Node(final SystemPathFinder var1, final int var2) {
		this.systemIndex = var2;
		this.parentNode = null;
		this.neighbors = new Vector();
	}
}
