
public class MapTourNode {
	MapNode node;
	//true : forward, false: back
	boolean forward;
	String way;
	
	public MapTourNode(MapNode node, boolean forward,String way) {
		this.node = node;
		this.forward = forward;
		this.way = way;
	}
}
