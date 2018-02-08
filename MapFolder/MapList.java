
public class MapList {
	MapList next;
	MapList root;
	MapNode adjNode;
	MapEdge adjEdge;
	
	public MapList(MapNode adjNode, MapEdge adjEdge) {
		this.adjEdge = adjEdge;
		this.adjNode = adjNode;
	}
	
	public MapList() {
		adjNode = null;
		adjEdge = null;
	}
	
}
