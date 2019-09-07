package poly;

import java.io.IOException;
import java.util.Scanner;
/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		
		Node pointer1 = poly1;
		Node pointer2 = poly2;
		Node head = null;
	
		while (pointer1 != null || pointer2 !=null) {
			
			if (pointer1 == null) {
				Node n = new Node(pointer2.term.coeff, pointer2.term.degree, null);
				if (head == null) {
					head = n;
				} 
				else {
					Node add = head;
					while(add.next!=null){
						add = add.next;
					}
					add.next = n;
					
				}
				pointer2 = pointer2.next;
			}
			else if (pointer2 == null) {
				Node n = new Node(pointer1.term.coeff, pointer1.term.degree, null);
				if (head == null) {
					head = n;
				} 
				else {
					Node add = head;
					while(add.next!=null){
						add = add.next;
					}
					add.next = n;
					
				}
				pointer1 = pointer1.next;
			}
			else if (pointer1.term.degree == pointer2.term.degree) {
				if (pointer1.term.coeff + pointer2.term.coeff != 0) {
					Node n = new Node(pointer1.term.coeff+pointer2.term.coeff, pointer1.term.degree, null);
					if (head == null) {
						head = n;
					}
					else {
						Node add = head;
						while(add.next!=null){
							add = add.next;
						}
						add.next = n;
					}
				}
				pointer1 = pointer1.next;
				pointer2 = pointer2.next;
			}
			
			else if (pointer1.term.degree < pointer2.term.degree) {
				Node n = new Node(pointer1.term.coeff, pointer1.term.degree, null);
				if (head == null) {
					head = n;
				} 
				else {
					Node add = head;
					while(add.next!=null){
						add = add.next;
					}
					add.next = n;
				}
				pointer1 = pointer1.next;
			}
			
			else if (pointer1.term.degree > pointer2.term.degree) {
				Node n = new Node(pointer2.term.coeff, pointer2.term.degree, null);
				if (head == null) {
					head = n;
				} 
				else {
					Node add = head;
					while(add.next!=null){
						add = add.next;
					}
					add.next = n;
				}
				pointer2 = pointer2.next;
			}
		}
		return head;
	}
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		Node pointer1= poly1;
		Node pointer2 = poly2;
		Node result1 = null;
		Node result2 = null;
		boolean onetotwo = false;
		while (pointer1 != null) {
			while (pointer2 != null) {
				if (onetotwo == false) {
					Node n = new Node (pointer1.term.coeff*pointer2.term.coeff, pointer1.term.degree+pointer2.term.degree, null);
					if (result1 == null) {
						result1 = n;
					}
					else {
						Node add = result1;
						while (add.next != null) {
							add = add.next;
						}
						add.next = n;
					}
					pointer2 = pointer2.next;
				}
				else {
					Node n = new Node (pointer1.term.coeff*pointer2.term.coeff, pointer1.term.degree+pointer2.term.degree, null);
					if (result2 == null) {
						result2 = n;
					}
					else {
						Node add = result2;
						while (add.next != null) {
							add = add.next;
						}
						add.next = n;
					}
					pointer2 = pointer2.next;
				}
			}
			if (onetotwo == false) {
				onetotwo = true;
			}
			pointer2 = poly2;
			
			if (result2 != null) {
				result1 = add(result1, result2);
				result2 = null;
			}
			pointer1 = pointer1.next;
		}
		
		return result1;
	}
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		
		Node pointer = poly;
		float total = 0;
		while (pointer != null) {
			total = (float) (Math.pow(x, pointer.term.degree)*pointer.term.coeff + total);
			pointer = pointer.next;
		}
		
		return total;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}
