
public class MapSpanningTree {
	MapNode node;
	MapList adjNode;
	
	public MapSpanningTree(MapNode node) {
		this.node = node;
		adjNode = null;
	}
	
	public void addAdj(MapNode adj, MapEdge adjEdge) {
		if (adjNode == null) {
			adjNode = new MapList(adj,adjEdge);
			adjNode.root = adjNode;
		} else {
			MapList temp = adjNode;
			adjNode = new MapList(adj,adjEdge);
			adjNode.root = adjNode;
			adjNode.next = temp;
		}
	}
	
	public MapList extractRoot() {
		MapList extract = adjNode.root;
		adjNode = adjNode.next;
		if (adjNode != null) adjNode.root = adjNode;
		
		return extract;
	}
}
