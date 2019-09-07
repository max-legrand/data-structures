package structures;

import java.util.*;

/**
 * This class implements an HTML DOM Tree. Each node of the tree is a TagNode, with fields for
 * tag/text, first child and sibling.
 * 
 */
public class Tree {
	
	/**
	 * Root node
	 */
	TagNode root=null;
	
	/**
	 * Scanner used to read input HTML file when building the tree
	 */
	Scanner sc;
	
	/**
	 * Initializes this tree object with scanner for input HTML file
	 * 
	 * @param sc Scanner for input HTML file
	 */
	public Tree(Scanner sc) {
		this.sc = sc;
		root = null;
	}
	
	/**
	 * Builds the DOM tree from input HTML file, through scanner passed
	 * in to the constructor and stored in the sc field of this object. 
	 * 
	 * The root of the tree that is built is referenced by the root field of this object.
	 */
	public void build() {
		String value = null;
		boolean lastvaltag = false;
		Stack<TagNode> brackets = new Stack<TagNode>();
		TagNode current = null;
		boolean reset = false;
		try {	
			while(sc != null) {	
					//print();
					value = sc.nextLine();
					//System.out.println(value);
					//print();
					if (root == null) {
						if (value.contains("<") && !value.contains("/")) {
							String newval = value.substring(1,value.length()-1);
							root = new TagNode(newval, null,null);
							brackets.push(root);
							lastvaltag = true;
						}
						else if (!value.contains("/")){
							root = new TagNode(value, null,null);
						}
					}
					else {
						if (!reset) {
							current = root;
							while(current.firstChild!=null || current.sibling!=null) {
								if (current.sibling!=null) {
									
									current = current.sibling;
								}
								else if(current.firstChild!=null) {
									current = current.firstChild;
								}
							}
						}
					
						if(!value.contains("/")) {
							
							if (lastvaltag) {
								
								if (value.contains("<") && !value.contains("/")) {
									String newval = value.substring(1,value.length()-1);
									current.firstChild = new TagNode(newval, null,null);
									TagNode copy = current.firstChild;
									brackets.push(copy);
									lastvaltag = true;
								}
								else if (!value.contains("/")){
									current.firstChild = new TagNode(value, null,null);
									lastvaltag = false;
								}
							}
							else {
								if (value.contains("<") && !value.contains("/")) {
									String newval = value.substring(1,value.length()-1);
									current.sibling = new TagNode(newval, null,null);
									TagNode copy = current.sibling;
									brackets.push(copy);
									lastvaltag = true;
								}
								else if (!value.contains("/")){
									current.sibling = new TagNode(value, null,null);
									lastvaltag = false;
								}
							}
							reset = false;
						}
						else {
							
							current = brackets.pop();
							lastvaltag = false;
							reset = true;
							
						}
						
					}
				
			}
		}catch(Exception e) {}
	}
	
	/**
	 * Replaces all occurrences of an old tag in the DOM tree with a new tag
	 * 
	 * @param oldTag Old tag
	 * @param newTag Replacement tag
	 */
	public void replaceTag(String oldTag, String newTag) {
		TagNode current = root;
		boolean pass = false;
		if (current != null)  {
			Stack<TagNode> brackets = new Stack<TagNode>();
			while(current != null) {
				//print();
				//System.out.println(current.tag);
				if (current.tag.equals(oldTag)) {
					current.tag = newTag;
				}
				
				if(current.firstChild!=null || current.sibling!=null) {
					if (current.firstChild!=null && current.sibling!=null && pass == false) {
						TagNode copy = current;
						brackets.push(copy);
					}
					if (current.firstChild!=null && pass == false) {
						
						current = current.firstChild;
						pass = false;
						
					}
					else if (current.sibling!=null) {
						current = current.sibling;
						pass = false;
					}
					else if(pass == true) {
						current = brackets.pop();
						pass = true;
						
					}
					
				}
				else {
					if (brackets.isEmpty()) {
						//print();
						return;
					}
					else {
						current = brackets.pop();
						pass = true;
					}
					
				}
				
			}
		}
		//print();
	}
	
	/**
	 * Boldfaces every column of the given row of the table in the DOM tree. The boldface (b)
	 * tag appears directly under the td tag of every column of this row.
	 * 
	 * @param row Row to bold, first row is numbered 1 (not 0).
	 */
	public void boldRow(int row) {
		TagNode current = root;
		boolean pass = false;
		int counter = 1;
		if (current != null)  {
			Stack<TagNode> brackets = new Stack<TagNode>();
			while(current != null) {
				
				if(current.tag.equals("table")) {
					if(current.firstChild!=null) {
						current = current.firstChild;
					}
					while (current.sibling!=null && counter!=row) {
						current = current.sibling;
						counter++;
					}
					if (counter == row) {
						TagNode colpointer = current;
						if (current.firstChild!=null) {
							colpointer = current.firstChild;
						}
						
						while (colpointer != null) {
							
							colpointer.firstChild = new TagNode("b", colpointer.firstChild, null);
							colpointer = colpointer.sibling;
							
						}
					}
				}
				
				if(current.firstChild!=null || current.sibling!=null) {
					if (current.firstChild!=null && current.sibling!=null && pass == false) {
						TagNode copy = current;
						brackets.push(copy);
					}
					if (current.firstChild!=null && pass == false) {
						
						current = current.firstChild;
						pass = false;
						
					}
					else if (current.sibling!=null) {
						current = current.sibling;
						pass = false;
					}
					else if(pass == true) {
						current = brackets.pop();
						pass = true;
						
					}
					
				}
				else {
					if (brackets.isEmpty()) {
						//print();
						return;
					}
					else {
						current = brackets.pop();
						pass = true;
					}
					
				}
				
			}
		}
		
		
	}
	
	/**
	 * Remove all occurrences of a tag from the DOM tree. If the tag is p, em, or b, all occurrences of the tag
	 * are removed. If the tag is ol or ul, then All occurrences of such a tag are removed from the tree, and, 
	 * in addition, all the li tags immediately under the removed tag are converted to p tags. 
	 * 
	 * @param tag Tag to be removed, can be p, em, b, ol, or ul
	 */
	public void removeTag(String tag) {
		/*
		if (tag.equals("ol") || tag.equals("ul")) {
			
			TagNode prev = null;
			TagNode current = root;
			if (current == null) {
				return;
			}
			boolean pass = false;
			if (current != null)  {
				
				Stack<TagNode> brackets = new Stack<TagNode>();
				while(current != null) {
					
					if (current.tag.equals(tag)) {
						
						TagNode pointer = current;
						if (pointer.firstChild!=null) {
							pointer = pointer.firstChild;
						}
						TagNode lastSibling = pointer;
						while (lastSibling.sibling!=null) {
							if (lastSibling.tag.equals("li")) {
								lastSibling.tag = "p";
							}
							lastSibling = lastSibling.sibling;
						}
						if (lastSibling.tag.equals("li")) {
							lastSibling.tag = "p";
						}
						lastSibling.sibling = current.sibling;
						current = lastSibling;
						prev.sibling = pointer;
					}
					if(current.firstChild!=null || current.sibling!=null) {
						if (current.firstChild!=null && current.sibling!=null && pass == false) {
							TagNode copy = current;
							TagNode copy2 = prev;
							brackets.push(copy);
							brackets.push(copy2);
						}
						if (current.firstChild!=null && pass == false) {
							prev = current;
							current = current.firstChild;
							pass = false;	
						}
						else if (current.sibling!=null) {
							prev = current;
							current = current.sibling;
							pass = false;
						}
						else if(pass == true) {
							prev = brackets.pop();
							current = brackets.pop();
							pass = true;	
						}
					}
					else {
						if (brackets.isEmpty()) {
							//print();
							return;
						}
						else {
							prev = brackets.pop();
							current = brackets.pop();
							pass = true;
						}
					}		
				}
			}
		}
		*/
		
		//else {
			TagNode prev = null;
			TagNode current = root;
			if (current == null) {
				return;
			}
			boolean pass = false;
			if (current != null)  {
				
				Stack<TagNode> brackets = new Stack<TagNode>();
				while(current != null) {
				
					if (current.tag.equals(tag)) {
						if (tag.equals("ol")||tag.equals("ul")) {
							
								
								TagNode pointer = current;
								if (pointer.firstChild!=null) {
									pointer = pointer.firstChild;
								}
								TagNode lastSibling = pointer;
								while (lastSibling.sibling!=null) {
									if (lastSibling.tag.equals("li")) {
										lastSibling.tag = "p";
									}
									lastSibling = lastSibling.sibling;
								}
								if (lastSibling.tag.equals("li")) {
									lastSibling.tag = "p";
								}
								lastSibling.sibling = current.sibling;
								current = lastSibling;
								//prev.sibling = pointer;
								current = lastSibling;
								if(prev.firstChild!=null) {
									if (prev.firstChild.tag.equals(tag)) {
										prev.firstChild = pointer;
									}
								}
								else if (prev.sibling!=null) {
									if(prev.sibling.tag.equals(tag)) {
										prev.sibling = pointer;
									}
								}
								else{prev.firstChild = null; prev.sibling = null;}
								
							
						}
						else {
						
						TagNode pointer = current;
						if (pointer.firstChild!=null) {
							pointer = pointer.firstChild;
						}
						TagNode lastSibling = pointer;
						while (lastSibling.sibling!=null) {
							lastSibling = lastSibling.sibling;
						}
						lastSibling.sibling = current.sibling;
							
							
						
							
						current = lastSibling;
						if(prev.firstChild!=null) {
							if (prev.firstChild.tag.equals(tag)) {
								prev.firstChild = pointer;
							}
						}
						else if (prev.sibling!=null) {
							if(prev.sibling.tag.equals(tag)) {
								prev.sibling = pointer;
							}
						}
						else{prev.firstChild = null; prev.sibling = null;}
						}
						
					}
					if(current.firstChild!=null || current.sibling!=null) {
						if (current.firstChild!=null && current.sibling!=null && pass == false) {
							
							TagNode copy = current;
							TagNode copy2 = prev;
							brackets.push(copy);
							brackets.push(copy2);
						}
						if (current.firstChild!=null && pass == false) {
							prev = current;
							current = current.firstChild;
							pass = false;
							
						}
						else if (current.sibling!=null) {
							prev = current;
							current = current.sibling;
							pass = false;
						}
						else if(pass == true) {
							prev = brackets.pop();
							current = brackets.pop();
							pass = true;
							
						}
						
					}
					else {
						if (brackets.isEmpty()) {
							//print();
							return;
						}
						else {
							prev = brackets.pop();
							current = brackets.pop();
							pass = true;
						}
						
					}
					
				}
			}
			
		//}
		/** COMPLETE THIS METHOD **/
	}
	
	/**
	 * Adds a tag around all occurrences of a word in the DOM tree.
	 * 
	 * @param word Word around which tag is to be added
	 * @param tag Tag to be added
	 */
	public void addTag(String word, String tag) {
		
		TagNode prev = null;
		TagNode current = root;
		if (current == null) {
			return;
		}
		boolean pass = false;
		if (current != null)  {
			
			Stack<TagNode> brackets = new Stack<TagNode>();
			while(current != null) {
				System.out.println(current.tag);
				if (current.tag.toLowerCase().contains(word)) {
					String s = current.tag;
					 
				//  How can I split this string on the word 'the' to get the strings 'pot fell off' and 'table'?
					String delim = "\\s";
					
					String[] tokens = s.split(delim); 
					
					
					
					TagNode start = new TagNode(null,null,null);
					TagNode pointer = start;
					TagNode end = current.sibling;
					String before = null;
					boolean flag = false;
					
					boolean afterword = false;
					
					for (String item : tokens) {
						try {
							if(item.charAt(word.length()+1) == '.' || item.charAt(word.length()+1) == ',' ||item.charAt(word.length()+1) == '?' ||item.charAt(word.length()+1) == '!'||item.charAt(word.length()+1) == ';'||item.charAt(word.length()+1) == ':' || !item.substring(0,word.length()-1).equals(word)) {
								flag = true;
							}
						}catch(Exception e) {
							flag = false;
						}
						if (!item.toLowerCase().contains(word)) {
							if (before == null) {
								if (afterword) {
									before = " "+item;
									afterword = false;
								}else {
									before = item;
								}
								
							
							}
							else{
								if (afterword) {
									before = " "+ before +" "+ item;
									afterword = false;
								}else {
									before = before +" "+ item;
								}
								
								}
						}
						else {
							
							if(flag || !item.substring(0,word.length()).toLowerCase().equals(word)){
								if (before == null) {
									if (afterword) {
										before = " "+item;
										afterword = false;
									}else {
										before = item;
									}
									
								
								}
								else{
									if (afterword) {
										before = " "+ before +" "+ item;
										afterword = false;
									}else {
										before = before +" "+ item;
									}
									
									}
							}

							else {
								if (start.tag==null) {
									if (before == null) {
										start.tag = tag;
										start.firstChild= new TagNode(item,null,null);
										
									}
									else {
										start.tag = before +" ";
										pointer.sibling = new TagNode(tag,new TagNode(item,null,null),null);
										pointer = pointer.sibling;
										before = null;
									}
								}
								else {
									
									if(before == null) {
										pointer.sibling = new TagNode(tag, new TagNode(item,null,null),null);
										pointer = pointer.sibling;
									}
									else {
										
										pointer.sibling = new TagNode(before, null, new TagNode(tag,new TagNode(item,null,null),null));
										pointer = pointer.sibling.sibling;
										before = null;
									}
								}
								afterword = true;
								
							}
						}
					}
					if (before!= null) {
						pointer.sibling = new TagNode(before, null,null);
						pointer = pointer.sibling;
					}
					if (end!=null) {
						pointer.sibling = end;
					}
					
					if(prev.sibling == current && start.tag!=null) {
						prev.sibling = start;
					}
					if(prev.firstChild == current  && start.tag!=null) {
						prev.firstChild = start;
					}
					
					prev = pointer;
					current = end;
					
				}
				if (current == null) {
					if (brackets.isEmpty()) {
						//print();
						return;
					}
					else {
						prev = brackets.pop();
						current = brackets.pop();
						pass = true;
					}
				}
				else if(current.firstChild!=null || current.sibling!=null) {
					if (current.firstChild!=null && current.sibling!=null && pass == false) {
						
						TagNode copy = current;
						TagNode copy2 = prev;
						brackets.push(copy);
						brackets.push(copy2);
					}
					if (current.firstChild!=null && pass == false) {
						prev = current;
						current = current.firstChild;
						pass = false;
						
					}
					else if (current.sibling!=null) {
						prev = current;
						current = current.sibling;
						pass = false;
					}
					else if(pass == true) {
						prev = brackets.pop();
						current = brackets.pop();
						pass = true;
						
					}
					
				}
				else {
					if (brackets.isEmpty()) {
						//print();
						return;
					}
					else {
						prev = brackets.pop();
						current = brackets.pop();
						pass = true;
					}
					
				}
				
			}
		}
		
		
	}
			
		
	
	
	/**
	 * Gets the HTML represented by this DOM tree. The returned string includes
	 * new lines, so that when it is printed, it will be identical to the
	 * input file from which the DOM tree was built.
	 * 
	 * @return HTML string, including new lines. 
	 */
	public String getHTML() {
		StringBuilder sb = new StringBuilder();
		getHTML(root, sb);
		return sb.toString();
	}
	
	private void getHTML(TagNode root, StringBuilder sb) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			if (ptr.firstChild == null) {
				sb.append(ptr.tag);
				sb.append("\n");
			} else {
				sb.append("<");
				sb.append(ptr.tag);
				sb.append(">\n");
				getHTML(ptr.firstChild, sb);
				sb.append("</");
				sb.append(ptr.tag);
				sb.append(">\n");	
			}
		}
	}
	
	/**
	 * Prints the DOM tree. 
	 *
	 */
	public void print() {
		print(root, 1);
	}
	
	private void print(TagNode root, int level) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			for (int i=0; i < level-1; i++) {
				System.out.print("      ");
			};
			if (root != this.root) {
				System.out.print("|---- ");
			} else {
				System.out.print("      ");
			}
			System.out.println(ptr.tag);
			if (ptr.firstChild != null) {
				print(ptr.firstChild, level+1);
			}
		}
	}
}
