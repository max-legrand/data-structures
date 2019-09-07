package apps;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Iterator;

import structures.*;
import structures.Vertex.Neighbor;


public class driver{
	
	
	public static void main(String[]args) throws IOException {
		Graph test = new Graph("graph3.txt");
		
		MST test1 = new MST();
		PartialTreeList test2 = new PartialTreeList();
		test2 = test1.initialize(test);
		/*
		Iterator<PartialTree> iter = test2.iterator();
		   while (iter.hasNext()) {
		       PartialTree pt = iter.next();
		       System.out.println(pt.toString());
		       
		 }
		 */
		
		ArrayList<PartialTree.Arc> arcs = test1.execute(test2);
		System.out.println();
		System.out.println(arcs.toString());
	}
}