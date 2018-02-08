import java.io.FileNotFoundException;
import java.io.PrintStream;

public class MapPrint {
	MapNode node1;
	MapNode node2;
	PrintStream route;
	PrintStream routeCropped;
	
	public MapPrint() {
		try {
			route = new PrintStream("Route.txt");
			routeCropped = new PrintStream("RouteCropped.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setNode1(MapNode node1) {
		this.node1 = node1;
	}
	
	public void setNode2(MapNode node2) {
		this.node2 = node2;
	}
	
	public void printOut() {
		int aR = (int)(node1.x * (2000.0/4369));
		int bR = (int)(node1.y * (2528.0/5521));
		int cR = (int)(node2.x * (2000.0/4369));
		int dR = (int)(node2.y * (2528.0/5521));
		route.println("\t"+aR+" "+bR+" "+cR+" "+dR);
		
		int aC = aR - 150;
		int bC = bR - 125;
		int cC = cR - 150;
		int dC = dR - 125;
		routeCropped.println("\t"+aC+" "+bC+" "+cC+" "+dC);
		
	}
	
	
}
