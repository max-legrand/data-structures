package lse;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map.Entry;

public class driver{
	
	
	public static void main(String[] args) throws FileNotFoundException {
		LittleSearchEngine lse = new LittleSearchEngine();
		lse.makeIndex("docs.txt", "noisewords.txt");
		
		//for (String w : lse.noiseWords) {
		//	System.out.println(w);
		//}
		
		/*
		for(Entry<String, ArrayList<Occurrence>> e : lse.keywordsIndex.entrySet()) {
	        String key = e.getKey();
	        System.out.print("KEY: "+key);
	        ArrayList<Occurrence> value = e.getValue();
	        for (Occurrence e1 : value) {
	        		System.out.print(" || Document: "+e1.document+" | Frequency: "+e1.frequency);
	        }
	        System.out.println("\n");
	    }
		*/
		
		
		ArrayList<Occurrence> test = new ArrayList<Occurrence>();
		/*
		test.add(new Occurrence("test.txt", 27));
		test.add(new Occurrence("test.txt", 15));
		test.add(new Occurrence("test.txt", 12));
		test.add(new Occurrence("test.txt", 10));
		test.add(new Occurrence("test.txt", 8));
		test.add(new Occurrence("test.txt", 7));
		test.add(new Occurrence("test.txt", 5));
		test.add(new Occurrence("test.txt", 3));
		test.add(new Occurrence("test.txt", 2));
		test.add(new Occurrence("test.txt", 11));
		ArrayList<Integer> test2 = lse.insertLastOccurrence(test);
		for(Integer i: test2) {
			System.out.println(i);
		}
		System.out.println("_________");
		for (Occurrence o: test) {
			System.out.println(o.frequency);
		}
		
		*/
		
		ArrayList<String> results = lse.top5search("red","car");
		System.out.println();
		for (String i: results) {
			System.out.println(i);
		}
		
		
	}
	
}