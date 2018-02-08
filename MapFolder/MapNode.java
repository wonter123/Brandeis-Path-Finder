
public class MapNode {
	int x,y;
	int num;
	String label;
	String name;
	
	// for dijkstra
	int dist;
	MapNode predecessor = null;
	boolean inHeap = true;
	MapEdge edge = null;
	
	// for prim
	MapNode child;
	
	public MapNode(int num, String label, int x, int y, String name) {
		this.num = num;
		this.label = label;
		this.x = x;
		this.y = y;
		this.name = name;
	}
	
	// test
	public MapNode(int num) {
		dist = num;
	}
	
}
