import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MapPrim {
	
	public MapList[] adjList;
	public MapNode[] list;
	public MapTourNode[] Tnode;
	public int tourCount;
	public int count;
	
	public MapPrim() throws FileNotFoundException {
		list = new MapNode[160];
		count = 0;
		File file = new File("MapDataVertices.txt");
		int num = 0;
		Scanner scan = new Scanner(file);
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			if (!line.isEmpty() && !line.contains("//")) {
				Scanner console = new Scanner(line);
				num = console.nextInt();
				list[count] = new MapNode(num,console.next(),console.nextInt(),console.nextInt(),console.nextLine());
				count++;
				
			}
		}
		scan.close();
		adjList = new MapList[num+1];
	}
	
	public void AddEdge() throws FileNotFoundException {
		File file = new File("MapDataEdges.txt");
		Scanner scan = new Scanner(file);
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			if (!line.isEmpty() && !line.contains("//")) {
				Scanner console = new Scanner(line);
				int num = console.nextInt();
				console.next();
				console.next();
				int x = console.nextInt();
				int y = console.nextInt();
				if (adjList[x] == null) {
					adjList[x] = new MapList(list[y],new MapEdge(num,console.nextInt(), console.nextInt(),console.next(),console.next(),console.nextLine()));
					adjList[x].root = adjList[x];
				} else {
					adjList[x].next = new MapList(list[y],new MapEdge(num,console.nextInt(), console.nextInt(),console.next(),console.next(),console.nextLine()));
					adjList[x].next.root = adjList[x].root;
					adjList[x] = adjList[x].next;
				}
			}
		}
		for (int i = 0; i < adjList.length; i++) {
			if (adjList[i] != null) {
				adjList[i] = adjList[i].root;
			}
		}
		scan.close();
	}
	
	// For Part2
		public void Prim(MapNode node,boolean skateboard, boolean time) {		
			MapNode[] dist = list.clone();
			MapList[] cloneAdj = adjList.clone();
			
			for (int i = 0; i < count; i++) {
				dist[i].dist = Integer.MAX_VALUE;
			}
			dist[node.num].dist = 0;
			MapHeap nodes =  new MapHeap(count,dist);
			
			while (!nodes.isEmpty()) {
				MapNode u = nodes.extractMin();
				
				while (cloneAdj[u.num] != null) {
					MapList v = cloneAdj[u.num];
					int distribution;
					if (time) {
						MapSpeed ms = new MapSpeed(v.adjEdge.code,skateboard);
						distribution = ms.getTime(v.adjEdge.length);
					} else {
						distribution = v.adjEdge.length;
					}
					if (nodes.contains(v.adjNode) && distribution < dist[v.adjNode.num].dist) {
						dist[v.adjNode.num].dist = distribution;
						dist[v.adjNode.num].predecessor = u;
						dist[v.adjNode.num].edge = v.adjEdge;
					}
					nodes.updateDist(cloneAdj[u.num].adjNode,dist);
					cloneAdj[u.num] = cloneAdj[u.num].next;
				}
				
			}
			traversePrim(dist, node);
			
		}
		
		
		
		// To make spanning tree for part 2
		public void traversePrim(MapNode[] dist, MapNode node) {
			MapPrint mp = new MapPrint();
			MapSpanningTree[] tree = new MapSpanningTree[count];
			for (int i = 0; i < count; i++) {
				MapNode temp = dist[i];
				mp.setNode2(temp);
				if (temp.predecessor == null) {
					continue;
				} else {
					if (tree[temp.predecessor.num] == null) {
						tree[temp.predecessor.num] = new MapSpanningTree(temp);
					}
					tree[temp.predecessor.num].addAdj(temp,temp.edge);
					mp.setNode1(temp.predecessor);
				}
				mp.printOut();
			}
			Scanner scan = new Scanner(System.in);
			System.out.println("Enter the tour you want to do: p means Pre order, h means Hamilton Tour, a means enhanced Hamilton Tour");
			String ans = scan.next();
			if (ans.contains("p")) {
				System.out.println("\nPre Order Traverse:\n");
				PreOrder(tree, node);
			} else if (ans.contains("h")) {
				System.out.println("\nBasic Hamilton Tour:\n");
				HamiltonTour(tree, node);
			} else {
				Tnode = new MapTourNode[500];
				tourCount = 0;
				EnhancedHamiltonTour(tree, node);
				ETour();
				System.out.println("	Backward: "+node.name);
			}
			
		}
		
		// Preorder the Part2A
		public void PreOrder(MapSpanningTree[] tree, MapNode node) {
			System.out.println(node.name);
			while (tree[node.num] != null && tree[node.num].adjNode != null) {
				PreOrder(tree, tree[node.num].adjNode.adjNode);
				tree[node.num].adjNode = tree[node.num].adjNode.next;
			}
		}
		
		// Hamilton Tour for Part2
		public void HamiltonTour(MapSpanningTree[] tree, MapNode node) {
			System.out.println(node.name);
			while (tree[node.num] != null && tree[node.num].adjNode != null) {
				HamiltonTour(tree, tree[node.num].adjNode.adjNode);
				System.out.println("      Back to: "+node.name);
				tree[node.num].adjNode = tree[node.num].adjNode.next;
				
			}
		}
		
		// Enhance Hamilton Tour
		public void EnhancedHamiltonTour(MapSpanningTree[] tree, MapNode node) {
			Tnode[tourCount] = new MapTourNode(node,true,"Forward: ");
			tourCount++;
			while (tree[node.num] != null && tree[node.num].adjNode != null) {
				EnhancedHamiltonTour(tree, tree[node.num].adjNode.adjNode);
				Tnode[tourCount] = new MapTourNode(node,false,"\tBackward: ");
				tourCount++;
				tree[node.num].adjNode = tree[node.num].adjNode.next;
				
			}
		}
		
		public void ETour() {
			MapList[] cloneAdj = adjList.clone();
			int back = 0;
			MapNode forw = null;
			for (int i = 0; i < tourCount; i++) {
				if (Tnode[i].forward) {
					if (back == 0) {
						System.out.println(Tnode[i].way+Tnode[i].node.name);
						forw = Tnode[i].node;
						continue;
					}
					int breakLoop = i-back-1;
					for (int j = i; j >= i-back;j--) {
						boolean endloop = false;
						while (cloneAdj[forw.num] != null && cloneAdj[forw.num].adjNode != null) {
							if (cloneAdj[forw.num].adjNode.num == Tnode[j].node.num) {
								System.out.println(Tnode[j].way+Tnode[j].node.name);
								breakLoop = j;
								endloop = true;
								break;
							}
							cloneAdj[forw.num] = cloneAdj[forw.num].next;
						}
						if (cloneAdj[forw.num] != null) cloneAdj[forw.num] = cloneAdj[forw.num].root;
						if (endloop) break;
					}
					for (int k = breakLoop+1; k <= i; k++) {
						System.out.println(Tnode[k].way+Tnode[k].node.name);
					}
					back = 0;
				}
				else {
					back++;
				}
			}
		}
		
		
}
