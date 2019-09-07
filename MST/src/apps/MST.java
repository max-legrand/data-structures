package apps;

import structures.*;
import java.util.ArrayList;

public class MST {
	
	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) {
		
		PartialTreeList L = new PartialTreeList();
		
		for (Vertex i: graph.vertices) {
			
			PartialTree T = new PartialTree(i);
			Vertex.Neighbor next = i.neighbors;
			MinHeap<PartialTree.Arc> heap = new MinHeap<PartialTree.Arc>();
			//System.out.println("NAME: "+i.name);
			while (next!=null) {
				PartialTree.Arc test = new PartialTree.Arc(i, next.vertex, next.weight);
				//System.out.println("NEIGHBOR: "+next.vertex.name+" || WEIGHT: "+next.weight);
				next=next.next;
				heap.insert(test);
				//System.out.println(heap.toString());
			
			}
			
			
			
			MinHeap<PartialTree.Arc> ref = T.getArcs();
			//while(!heap.isEmpty()) {
			//	ref.insert(heap.deleteMin());
			//}
			ref.merge(heap);
			
			
			L.append(T);
		}
		
		
		
		
		
		
		
	
		return L;
	}

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	
	private static boolean intree(Vertex v2, Vertex v1, Vertex root) {
		if (v2 == root) {
			return true;
		}
		while (v2.parent!=v2) {
			if(v2 == v1 || v2 == root) {
				return true;
			}
			v2 = v2.parent;
		}
		if (v2 == root) {
			return true;
		}
		return false;
		
	}
	
	public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist) {

		ArrayList<PartialTree.Arc> mst = new ArrayList<PartialTree.Arc>();
		while(ptlist.size() != 1) {
			PartialTree PTX = ptlist.remove();
			MinHeap<PartialTree.Arc> PQX = PTX.getArcs();
			PartialTree.Arc hp = PQX.deleteMin();
			Vertex v1 = hp.v1;
			Vertex v2 = hp.v2;
			Vertex root = PTX.getRoot();
			while (intree(v2, v1, root)) {
				
				PQX = PTX.getArcs();
				hp = PQX.deleteMin();
				v1 = hp.v1;
				v2 = hp.v2;
				root = PTX.getRoot();
			}
			mst.add(hp);
			
			
			PartialTree PTY = ptlist.removeTreeContaining(v2);
			PTX.merge(PTY);
			ptlist.append(PTX);
			
		}

		return mst;
	}
}
