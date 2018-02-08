import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class Map {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			PrintStream ps = new PrintStream("Output.txt");
			Scanner console = new Scanner(System.in);

			System.out.println("Type read to generate 12 output, type enter to enter by yourself:");
			String mode = console.nextLine();
			
			if (mode.equals("enter")) {
				System.out.println("\nCaution: Please enter name instead of label (eg. castle, farber l...)\n");
				ps.println("\n\n************* WELCOME TO THE BRANDEIS MAP *************");
				ps.print("Enter start (return to quit): ");
				System.out.println("************* WELCOME TO THE BRANDEIS MAP *************");
				System.out.print("Enter start (return to quit): ");
				String from = console.nextLine();
				ps.print(from+"\n");
				if (from.isEmpty()) return;
				ps.print("Enter finish (or return to do a tour): ");
				System.out.print("Enter finish (or return to do a tour): ");
				String to = console.nextLine();
				ps.print(to+"\n");
				if (to.isEmpty()) to = "tour";
				ps.print("Have a skateboard (y/n - default=n)? ");
				System.out.print("Have a skateboard (y/n - default=n)? ");
				String skateboard = console.nextLine();
				ps.print(skateboard+"\n");
				if (skateboard.isEmpty()) skateboard = "n";
				ps.print("Minimize time (y/n - default=n)? ");
				System.out.print("Minimize time (y/n - default=n)? ");
				String minitime = console.nextLine();
				ps.print(minitime+"\n");
				if (minitime.isEmpty()) minitime = "n";
				
				
				MapAdjacency tour = new MapAdjacency();
				tour.AddEdge();
				MapSearch search = new MapSearch(tour.list,tour.count);
				
				MapNode from_m = search.search(from);
				MapNode to_m = search.search(to);
				
				boolean skate = false;
				boolean time = false;
				if (skateboard.contains("y")) skate = true;
				if (minitime.contains("y")) time = true;
				if (to.equals("tour")) {
					MapPrim round = new MapPrim();
					round.AddEdge();
					round.Prim(from_m, skate, time);
				} else {
					tour.Dijkstra(from_m, to_m,skate,time, ps);
				}
			} else {		
				System.out.println("\nCaution: Route.txt and RouteCropped.txt will be the last route generated. The output will be the full version.");
				MapSample[] samples = new MapSample[12];
				samples[0] = new MapSample("U14","L24",true,true);
				samples[1] = new MapSample("U14","L24",true,false);
				samples[2] = new MapSample("U14","L24",false,false);
				samples[3] = new MapSample("U37","L5",true,true);
				samples[4] = new MapSample("U37","L5",true,false);
				samples[5] = new MapSample("U37","L5",false,false);
				samples[6] = new MapSample("U40","+",true,true);
				samples[7] = new MapSample("U40","+",false,false);
				samples[8] = new MapSample("U17","L36",true,true);
				samples[9] = new MapSample("L1","A4",false,true);
				samples[10] = new MapSample("L36","$",false,true);
				samples[11] = new MapSample("!","=",false,false);
				
				for (MapSample ms : samples) {
					ps.println("\n\n************* WELCOME TO THE BRANDEIS MAP *************");
					ps.print("Enter start (return to quit): ");
					ps.print(ms.ID1+"\n");
					ps.print("Enter finish (or return to do a tour): ");
					ps.print(ms.ID2+"\n");
					ps.print("Have a skateboard (y/n - default=n)? ");
					ps.print((ms.skateboard? "y" : "n")+"\n");
					ps.print("Minimize time (y/n - default=n)? ");
					ps.print((ms.time? "y" : "n")+"\n");
					
					MapAdjacency tour = new MapAdjacency();
					tour.AddEdge();
					MapSearch search = new MapSearch(tour.list,tour.count);
					
					MapNode from_m = search.searchID(ms.ID1);
					MapNode to_m = search.searchID(ms.ID2);
					
					tour.Dijkstra(from_m, to_m,ms.skateboard,ms.time, ps);
				}
				System.out.println("\nCaution: Route.txt and RouteCropped.txt will be the last route generated. The output will be the full version.\n");

			}
			console.close();
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			
	}

}
