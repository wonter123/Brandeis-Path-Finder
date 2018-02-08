import java.io.*;
import java.util.Scanner;

public class MapAdjacency {
	private final int bond_X = 5521;
	private final int bond_Y = 4369;
	
	public MapList[] adjList;
	public MapNode[] list;
	public int count;
	
	public MapAdjacency() throws FileNotFoundException {
		list = new MapNode[160];
		count = 0;
		File file = new File("MapDataVertices.txt");
//		try {
//		    System.out.println(file.getCanonicalPath());
//		} catch (IOException e) {
//		    e.printStackTrace();
//		}
		int num = 0;
		Scanner scan = new Scanner(file);
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
//			System.out.println(line);
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
	
//	public void Dijkstra(MapNode node,MapNode destination,boolean skateboard, boolean time) {		
//		MapDijkstra md = new MapDijkstra(node, destination, skateboard, time, list, adjList, count);
//		md.Dijkstra();
//	}
	
	// Part 1
	public void Dijkstra(MapNode node,MapNode destination,boolean skateboard, boolean time, PrintStream ps) {		
		MapNode[] dist = list.clone();
		MapList[] cloneAdj = adjList.clone();
		
		for (int i = 0; i < count; i++) {
			dist[i].dist = Integer.MAX_VALUE;
		}
		dist[node.num].dist = 0;
		MapHeap nodes =  new MapHeap(count,dist);
//		MapHeap nodes = new MapHeap(dist,count);
		while (!nodes.isEmpty()) {
			MapNode u = nodes.extractMin();
//			MapNode u = nodes.FindMin();
			
			while (cloneAdj[u.num] != null) {
				if (time) {
					timeRelax(u,cloneAdj[u.num].adjNode,dist,cloneAdj[u.num].adjEdge,skateboard);
				} else {
					relax(u,cloneAdj[u.num].adjNode,cloneAdj[u.num].adjEdge.length,dist,cloneAdj[u.num].adjEdge);
				}
				nodes.updateDist(cloneAdj[u.num].adjNode,dist);
//				nodes.UpdateDist(cloneAdj[u.num].adjNode.num,dist);
				cloneAdj[u.num] = cloneAdj[u.num].next;
			}
		}
		toString(dist, destination, skateboard, ps);
		
	}
	
	// For Dijkstra
	public void relax(MapNode x, MapNode y, int dis, MapNode[] dist, MapEdge edge) {
		if (dist[y.num].dist > dist[x.num].dist + dis) {
			dist[y.num].dist = dist[x.num].dist + dis;
			dist[y.num].predecessor = dist[x.num];
			dist[y.num].edge = edge;
		}
	}
	
	// For Dijkstra
	public void timeRelax(MapNode x, MapNode y, MapNode[] dist, MapEdge edge, boolean skateboard) {
		MapSpeed sp = new MapSpeed(edge.code,skateboard);
		int dis = sp.getTime(edge.length);
		if (dist[y.num].dist > dist[x.num].dist + dis) {
			dist[y.num].dist = dist[x.num].dist + dis;
			dist[y.num].predecessor = dist[x.num];
			dist[y.num].edge = edge;
		}
	}
	
	// For Dijkstra to Print
	public void toString(MapNode dist[], MapNode destination, boolean skateboard, PrintStream ps) {
		MapList m = new MapList();
		MapList n = new MapList();
		while(dist[destination.num].predecessor != null) {
			n = new MapList(destination,destination.edge);
			n.next = m;
			m = n;
			destination = dist[destination.num].predecessor;
		}
		n = new MapList(destination,destination.edge);
		n.next = m;
		int leg = 0;
		int distance = 0;
		int time = 0;
		MapPrint mp = new MapPrint();
		while (n.next.adjNode != null) {
			mp.setNode1(n.adjNode);
			ps.println("\nFROM: ("+n.adjNode.label+") "+n.adjNode.name);
			System.out.println("\nFROM: ("+n.adjNode.label+") "+n.adjNode.name);
			n = n.next;
			MapSpeed speed = new MapSpeed(n.adjNode.edge.code,skateboard);
			if (n.adjNode.edge.name.length() != 3) {
				ps.println("ON: "+n.adjNode.edge.name);
				System.out.println("ON: "+n.adjNode.edge.name);
			}
			ps.println(speed.walkStatus+" "+n.adjNode.edge.length+" feet in direction "+n.adjNode.edge.angle+" degrees "+n.adjNode.edge.direction+".");
			System.out.println(speed.walkStatus+" "+n.adjNode.edge.length+" feet in direction "+n.adjNode.edge.angle+" degrees "+n.adjNode.edge.direction+".");
			ps.println("TO: ("+n.adjNode.label+") "+n.adjNode.name);
			System.out.println("TO: ("+n.adjNode.label+") "+n.adjNode.name);
			ps.println("("+speed.canSkateboard()+speed.toTime(n.adjNode.edge.length)+")");
			System.out.println("("+speed.canSkateboard()+speed.toTime(n.adjNode.edge.length)+")");
			leg++;
			distance+= n.adjNode.edge.length;
			time+=speed.time;
			mp.setNode2(n.adjNode);
			mp.printOut();
			
		}
		ps.println("\nlegs = "+leg+", distance = "+distance+" feet, time = "+getTime(time));
		System.out.println("\nlegs = "+leg+", distance = "+distance+" feet, time = "+getTime(time));
		
	}
	
	// For Dijkstra to get time
	public String getTime(int time) {
		if (time < 60) return ""+time+" seconds";
		else {
			double min = time / 60 + (int)(Math.round((time % 60 / 6)))/10.0;
//			double second = time % 60 / 6;
			return ""+min+" minutes";
		}
			
	}
		
}

