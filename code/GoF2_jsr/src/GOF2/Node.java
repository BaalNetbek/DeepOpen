package GOF2;

import java.util.Vector;

/**
 * Represents a node (linked to system on a map) for path finder.
 * 
 * @author Fishlabs 2009
 */
final class Node {
	Vector neighbors;
	Node parentNode;
	int systemIndex;

	public Node(final SystemPathFinder pf, final int idx) {
		this.systemIndex = idx;
		this.parentNode = null;
		this.neighbors = new Vector();
	}
}
