The following code in the MapFolder can produce the the shortest path from given start point and end point or it can make a campus tour from a given location as well.

The data structure I use includes min heap and list which refers to the powerpoints of Professor Di Lillo's COSI 21A. Also I use a spanning tree to do the part2.

I comments some methods and variables in the code which are used as test code and validation code.

Dijkstra algorithm is used for the part one code (in the MapAdjacency.java) and Prim is used for the part two.

You can choose to print three different route for part2: Pre order, Hamilton Tour and Enhanced Hamilton Tour.

The code includes two mode: "enter" mode means that you can enter the from, to , skateboard and time by yourself and check the validity.
"read" mode means that you can automatically generate all the output for the 12 build-in test in the Output.txt file. The print of this mode is not fully displayed(no input question) but the Output.txt contains everything.

the route.txt and routcropped.txt will display the late route in the read mode

For the part2, route.txt and routecropped.txt are all print the same minimum spanning tree for the whole graph. (Although Enhanced Hamilton Tour use a different way to traverse the vertices, it is still based on the same spanning tree)

Enhanced Hamilton Tour uses the previous adjacency list to check whether there are shortcut to avoid going back to same vertices. Some redundant routes can be eliminated using this method

The Output Folder for Part 2 uses castle as the start point

Use javac *.java to compile all the java code in the MapFolder

Use java Map to start the program
