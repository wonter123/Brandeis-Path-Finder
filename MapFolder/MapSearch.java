
public class MapSearch {
	MapNode list[];
	int bound;
	
	public MapSearch(MapNode[] list, int bound) {
		this.list = list;
		this.bound = bound;
	}
	
	public MapNode search(String key) {
		for (int i = 0; i < bound; i++) {
			if (list[i].name.toLowerCase().contains(key.toLowerCase())) return list[i];
		}
		return null;
	}
	
	public MapNode searchID(String key) {
		for (int i = 0; i < bound; i++) {
			if (list[i].label.equals(key)) return list[i];
		}
		return null;
	}
	
}
