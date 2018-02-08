
public class MapHeap {
	MapNode[] list;
	int length;
	int fix;
	
	public MapHeap(int len, MapNode[] list) {
		this.list = list.clone();
		length = len;
		for (int i = length/2; i >= 0; i--) {
			heapifyDown(i);
		}
	}
	
	// for test
	public MapHeap(MapNode[] list, int len) {
		this.list = list;
		length = len;
		fix = len;
	}
	
	// for test
	public MapNode FindMin() {
		int min = Integer.MAX_VALUE;
		MapNode node = null;
		int record = fix;
		for (int i = 0; i < fix; i++) {
			if (list[i].dist < min && list[i].inHeap) {
				min = Math.min(min,list[i].dist);
				node = list[i];
				record = i;
			}
			
		}
		list[record].inHeap = false;
		length--;
		return node;
	}
	
	public void updateDist(MapNode v,MapNode[] dist) {
		int num = -1;
		for (int i = 0; i < length; i++) {
			if (list[i].num == v.num) {
				list[i].dist = dist[v.num].dist;
				list[i].predecessor = dist[v.num].predecessor;
				list[i].edge = dist[v.num].edge;
				num = i;
				break;
			}
		}
		if (num == -1) return;
		int parent = parent(num);
		while (num > 0 && list[parent].dist > list[num].dist) {
			swap(num,parent);
			num = parent;
			parent = parent(parent);
		}
	}
	
	public int left(int i) {
		return 2*i+1;
	}
	
	public int right(int i) {
		return 2*i+2;
	}
	
	public int parent(int i) {
		if (i % 2 == 1) return i / 2;
		return (i-1)/2;
	}
	
	public void swap(int x, int y) {
		MapNode temp = list[x];
		list[x] = list[y];
		list[y] = temp;
	}
	
	public void heapifyDown(int i) {
		int l = left(i);
		int r = right(i);
		int smallest = i;
		if (l < length && list[l].dist < list[i].dist)  smallest = l;
		if (r < length && list[r].dist < list[smallest].dist) smallest = r;
		if (smallest != i) {
			swap(i, smallest);
			heapifyDown(smallest);
		}
	}
	
//	public void UpdateDist(MapNode v, MapNode[] dist) {
//		if (list[v.num].dist == dist[v.num].dist) return;
//		if (list[v.num].dist < dist[v.num].dist) System.err.println("New Dist should be smaller");
//		list[v.num].dist = dist[v.num].dist;
//		list[v.num].predecessor = dist[v.num].predecessor;
//		
//		int parent = parent(v.num);
//		while (num > 0 && list[parent].dist > list[num].dist) {
//			swap(v.num,parent);
//			num = parent;
//			parent = parent(parent);
//		}
//	}
	
//	public void heapifyUp(int i) {
//		while (i > 0 && list[i].dist < list[parent(i)].dist) {
//			swap(i, parent(i));
//			i = parent(i);
//		}
//	}
	
	public boolean contains(MapNode node) {
		for (int i = 0; i < length; i++) {
			if (list[i].num == node.num) return true;
		}
		return false;
	}
	
	public MapNode minimum() {
		return list[0];
	}
	
	public MapNode extractMin() {
		if (length < 1) System.err.println("Heap Underflow");
		MapNode min = minimum();
		list[0] = list[length-1];
		length--;
		heapifyDown(0);
		return min;
	}
	
	public boolean isEmpty() {
		return length == 0;
	}
	
}
