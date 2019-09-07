package lse;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

/**
 * This class builds an index of keywords. Each keyword maps to a set of pages in
 * which it occurs, with frequency of occurrence in each page.
 *
 */
public class LittleSearchEngine {
	
	/**
	 * This is a hash table of all keywords. The key is the actual keyword, and the associated value is
	 * an array list of all occurrences of the keyword in documents. The array list is maintained in 
	 * DESCENDING order of frequencies.
	 */
	HashMap<String,ArrayList<Occurrence>> keywordsIndex;
	
	/**
	 * The hash set of all noise words.
	 */
	HashSet<String> noiseWords;
	
	/**
	 * Creates the keyWordsIndex and noiseWords hash tables.
	 */
	public LittleSearchEngine() {
		keywordsIndex = new HashMap<String,ArrayList<Occurrence>>(1000,2.0f);
		noiseWords = new HashSet<String>(100,2.0f);
	}
	
	/**
	 * Scans a document, and loads all keywords found into a hash table of keyword occurrences
	 * in the document. Uses the getKeyWord method to separate keywords from other words.
	 * 
	 * @param docFile Name of the document file to be scanned and loaded
	 * @return Hash table of keywords in the given document, each associated with an Occurrence object
	 * @throws FileNotFoundException If the document file is not found on disk
	 */
	public HashMap<String,Occurrence> loadKeywordsFromDocument(String docFile) 
	throws FileNotFoundException {
		/** COMPLETE THIS METHOD **/
		
		
		Scanner scan = new Scanner(new File(docFile));
		HashMap<String,Occurrence> kws = new HashMap<String,Occurrence>();
		while (scan.hasNext()) {
			String word = scan.next();
			
			String result = getKeyword(word);
			
			if (result != null) {
				if (kws.get(result)!=null) {
					kws.get(result).frequency++;
				}
				else {
					kws.put(result, new Occurrence(docFile, 1));
				}
			}
		}
		/*
		for(Entry<String, Occurrence> e : kws.entrySet()) {
	        String key = e.getKey();
	        Occurrence value = e.getValue();
	        System.out.println("KEY: "+key+" | Document: "+value.document+" | Frequency: "+value.frequency);
	    }
		*/
		
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		return kws;
	}
	
	/**
	 * Merges the keywords for a single document into the master keywordsIndex
	 * hash table. For each keyword, its Occurrence in the current document
	 * must be inserted in the correct place (according to descending order of
	 * frequency) in the same keyword's Occurrence list in the master hash table. 
	 * This is done by calling the insertLastOccurrence method.
	 * 
	 * @param kws Keywords hash table for a document
	 */
	public void mergeKeywords(HashMap<String,Occurrence> kws) {
		/** COMPLETE THIS METHOD **/
	
		for(Entry<String, Occurrence> e : kws.entrySet()) {
			if (keywordsIndex.get(e.getKey())!=null) {
				keywordsIndex.get(e.getKey()).add(e.getValue());
			}
			else {
				ArrayList<Occurrence> arr = new ArrayList<Occurrence>();
				arr.add(e.getValue());
				keywordsIndex.put(e.getKey(), arr);
			}
	    }
		
	}
	
	/**
	 * Given a word, returns it as a keyword if it passes the keyword test,
	 * otherwise returns null. A keyword is any word that, after being stripped of any
	 * trailing punctuation, consists only of alphabetic letters, and is not
	 * a noise word. All words are treated in a case-INsensitive manner.
	 * 
	 * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!'
	 * 
	 * @param word Candidate word
	 * @return Keyword (word without trailing punctuation, LOWER CASE)
	 */
	
	
	
	
	public String getKeyword(String word) {
		/** COMPLETE THIS METHOD **/
		
		word = word.toLowerCase();
		if (noiseWords.contains(word)==false) {
			
			try {
			while (word.charAt(word.length()-1)=='.' || word.charAt(word.length()-1)==',' ||word.charAt(word.length()-1)=='?' ||word.charAt(word.length()-1)=='!' ||word.charAt(word.length()-1)==':'||word.charAt(word.length()-1)==';') {
				//System.out.println(word.charAt(word.length()-1));
				word = word.substring(0,word.length()-1);
			}
			} catch(Exception e) {}
		
			for(int i=0;i<word.length();i++) {
				 
				if (Character.isLetter(word.charAt(i)) == false) {
					return null;
				}
			}
		}
		//System.out.println(word);
		if (noiseWords.contains(word)==false) {
			return word;
		}
		else {
			return null;
		}
	}
	
	/**
	 * Inserts the last occurrence in the parameter list in the correct position in the
	 * list, based on ordering occurrences on descending frequencies. The elements
	 * 0..n-2 in the list are already in the correct order. Insertion is done by
	 * first finding the correct spot using binary search, then inserting at that spot.
	 * 
	 * @param occs List of Occurrences
	 * @return Sequence of mid point indexes in the input list checked by the binary search process,
	 *         null if the size of the input list is 1. This returned array list is only used to test
	 *         your code - it is not used elsewhere in the program.
	 */
	public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {
		/** COMPLETE THIS METHOD **/
		
		if (occs.size() == 1) {
			
			return null;
		}
		ArrayList<Integer> midpoints = new ArrayList<Integer>();
		Occurrence target = occs.remove(occs.size()-1);
		int l = 0;
        int r = occs.size()-1;
        int m = 0;
        while (l<=r){
            m = (l+r)/2;
            midpoints.add(m);
            if (target.frequency==(occs.get(m).frequency)){
            		occs.add(m, target);
            }
            else if (target.frequency > occs.get(m).frequency){
                r = m - 1;
            }
            else{
                l = m+1;
            }
        }
        if (occs.get(m).frequency>target.frequency) {
        		occs.add(m+1, target);
        }
        else {
        occs.add(m, target);
        }
        return midpoints;
    
	
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		
	}
	
	/**
	 * This method indexes all keywords found in all the input documents. When this
	 * method is done, the keywordsIndex hash table will be filled with all keywords,
	 * each of which is associated with an array list of Occurrence objects, arranged
	 * in decreasing frequencies of occurrence.
	 * 
	 * @param docsFile Name of file that has a list of all the document file names, one name per line
	 * @param noiseWordsFile Name of file that has a list of noise words, one noise word per line
	 * @throws FileNotFoundException If there is a problem locating any of the input files on disk
	 */
	public void makeIndex(String docsFile, String noiseWordsFile) 
	throws FileNotFoundException {
		// load noise words to hash table
		Scanner sc = new Scanner(new File(noiseWordsFile));
		while (sc.hasNext()) {
			String word = sc.next();
			noiseWords.add(word);
		}
		
		// index all keywords
		sc = new Scanner(new File(docsFile));
		while (sc.hasNext()) {
			String docFile = sc.next();			
			HashMap<String,Occurrence> kws = loadKeywordsFromDocument(docFile);
			mergeKeywords(kws);
		}
		sc.close();
	}
	
	/**
	 * Search result for "kw1 or kw2". A document is in the result set if kw1 or kw2 occurs in that
	 * document. Result set is arranged in descending order of document frequencies. (Note that a
	 * matching document will only appear once in the result.) Ties in frequency values are broken
	 * in favor of the first keyword. (That is, if kw1 is in doc1 with frequency f1, and kw2 is in doc2
	 * also with the same frequency f1, then doc1 will take precedence over doc2 in the result. 
	 * The result set is limited to 5 entries. If there are no matches at all, result is null.
	 * 
	 * @param kw1 First keyword
	 * @param kw1 Second keyword
	 * @return List of documents in which either kw1 or kw2 occurs, arranged in descending order of
	 *         frequencies. The result size is limited to 5 documents. If there are no matches, returns null.
	 */
	public ArrayList<String> top5search(String kw1, String kw2) {
		/** COMPLETE THIS METHOD **/
		
		ArrayList<Occurrence> result1 = keywordsIndex.get(kw1);
		if (result1!=null) {
			int counter = 0;
			while (counter<result1.size()) {
				if (counter!=0) {
					int index = counter;
					while(result1.get(index).frequency>result1.get(index-1).frequency) {
						Occurrence o = result1.get(index);
						
						result1.remove(index);
						result1.add(index-1, o);
						index = index-1;
						if (index==0) {
							break;
						}
					}
				}
				counter++;
			}
		/*
		System.out.println("RESULT 1:");
		for(Occurrence o: result1) {
			System.out.println(o.toString());
		}
		*/
		}
		
		ArrayList<Occurrence> result2 = keywordsIndex.get(kw2);
		if(result2!=null) {
		
		int counter = 0;
		while (counter<result2.size()) {
			if (counter!=0) {
				int index = counter;
				while(result2.get(index).frequency>result2.get(index-1).frequency) {
					Occurrence o = result2.get(index);
					
					result2.remove(index);
					result2.add(index-1, o);
					index = index-1;
					if (index==0) {
						break;
					}
				}
			}
			counter++;
		}
		/*
		for(Occurrence o: result2) {
			if (result2.indexOf(o)!=0) {
				
			while(o.frequency>result2.get(result2.indexOf(o)-1).frequency) {
				int index = result2.indexOf(o);
				result2.remove(index);
				result2.add(index-1, o);
				
			}
			}
		}
		*/
		/*
		System.out.println("RESULT 2:");
		for(Occurrence o: result2) {
			System.out.println(o.toString());
		}
		*/
		}
		if ((result1==null&&result2==null)) {
			return null;
		}
		else if (result1 == null) {
			result1 = new ArrayList<Occurrence>();
		}
		else if(result2 == null) {
			result2 = new ArrayList<Occurrence>();
		}
		ArrayList<String> results = new ArrayList<String>();
		while(!result1.isEmpty() || !result2.isEmpty()) {
			if (results.size()==5) {
				break;
			}
			
			if (result1.isEmpty()) {
				if (!results.contains(result2.get(0).document)) {
					results.add(result2.get(0).document);
				}
				result2.remove(0);
			}
			else if (result2.isEmpty()){
				if (!results.contains(result1.get(0).document)) {
					results.add(result1.get(0).document);
				}
				result1.remove(0);
			}
			else if(result1.get(0).frequency >= result2.get(0).frequency) {
				if (!results.contains(result1.get(0).document)) {
					results.add(result1.get(0).document);
				}
				result1.remove(0);
			}
			else {
				if (!results.contains(result2.get(0).document)) {
					results.add(result2.get(0).document);
				}
				result2.remove(0);
			}
		}
		
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		return results;
	
	}
	
	
}
