package app;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import structures.Stack;

public class Expression {

	public static String delims = " \t*+-/()[]";
			
    /**
     * Populates the vars list with simple variables, and arrays lists with arrays
     * in the expression. For every variable (simple or array), a SINGLE instance is created 
     * and stored, even if it appears more than once in the expression.
     * At this time, values for all variables and all array items are set to
     * zero - they will be loaded from a file in the loadVariableValues method.
     * 
     * @param expr The expression
     * @param vars The variables array list - already created by the caller
     * @param arrays The arrays array list - already created by the caller
     */
    public static void 
    makeVariableLists(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
    	
    	
    	
    	/** COMPLETE THIS METHOD **/
    	/** DO NOT create new vars and arrays - they are already created before being sent in
    	 ** to this method - you just need to fill them in.
    	 **/
    	
    		int counter = 0;
    		int length = expr.length()-1;
    		while (counter <= length) {
    			if (Character.isLetter(expr.charAt(counter))) {
    				int start = counter;
    				try {
	    				counter++;
	    				while (Character.isLetter(expr.charAt(counter))) {
	    					counter++;
	    				}
    				}catch(Exception e) {
    					
    				}
    				int end = counter;
    				
    				try {
    					
    					if (expr.charAt(counter)=='[') {
    						
    						boolean found = false;
    						for (Array object : arrays) {
    							if (object.name.equals(expr.substring(start,end))) {
    								found = true;
    							}
    						}
    						if (found == false) {
    							arrays.add(new Array(expr.substring(start, end)));
    						}
    						
    					}
    					else {
    						
    						boolean found = false;
    						for (Variable object : vars) {
    							if (object.name.equals(expr.substring(start,end))) {
    								found = true;
    							}
    						}
    						if (found == false) {
    							vars.add(new Variable(expr.substring(start, end)));
    						}
    						
    					}
    				}
    				catch(Exception e) {
    					
    					boolean found = false;
						for (Variable object : vars) {
							if (object.name.equals(expr.substring(start,end))) {
								found = true;
							}
						}
						if (found == false) {
							vars.add(new Variable(expr.substring(start, end)));
						}
						
					
    				}
    				
    			}
    			counter++;
    			
    			
    			
    		}
    		
    }
    
    /**
     * Loads values for variables and arrays in the expression
     * 
     * @param sc Scanner for values input
     * @throws IOException If there is a problem with the input 
     * @param vars The variables array list, previously populated by makeVariableLists
     * @param arrays The arrays array list - previously populated by makeVariableLists
     */
    public static void 
    loadVariableValues(Scanner sc, ArrayList<Variable> vars, ArrayList<Array> arrays) 
    throws IOException {
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine().trim());
            int numTokens = st.countTokens();
            String tok = st.nextToken();
            Variable var = new Variable(tok);
            Array arr = new Array(tok);
            int vari = vars.indexOf(var);
            int arri = arrays.indexOf(arr);
            if (vari == -1 && arri == -1) {
            	continue;
            }
            int num = Integer.parseInt(st.nextToken());
            if (numTokens == 2) { // scalar symbol
                vars.get(vari).value = num;
            } else { // array symbol
            	arr = arrays.get(arri);
            	arr.values = new int[num];
                // following are (index,val) pairs
                while (st.hasMoreTokens()) {
                    tok = st.nextToken();
                    StringTokenizer stt = new StringTokenizer(tok," (,)");
                    int index = Integer.parseInt(stt.nextToken());
                    int val = Integer.parseInt(stt.nextToken());
                    arr.values[index] = val;              
                }
            }
        }
    }
    
    /**
     * Evaluates the expression.
     * 
     * @param vars The variables array list, with values for all variables in the expression
     * @param arrays The arrays array list, with values for all array items
     * @return Result of evaluation
     */
    public static float 
    evaluate(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
    
   
 
    	
    	HashMap<Character, Integer> prectable = new HashMap<Character, Integer>();
    	prectable.put('+',1);
    	prectable.put('-',1);
    	prectable.put('*',2);
    	prectable.put('/',2);
    	int counter = 0;
    	int length = expr.length()-1;
    	Stack<Character> operators = new Stack<Character>();
    	Stack<Float> operands = new Stack<Float>();
    	while (counter<=length) {
    		//System.out.println(expr.charAt(counter));
    		if (Character.isLetterOrDigit(expr.charAt(counter))) {
    			int start = counter;
    			int end = 0;
    			if (Character.isLetter(expr.charAt(counter))) {
    				try {
        				while (Character.isLetter(expr.charAt(counter))) {
        					counter++;
        				}
        				 end = counter;
        			}
        			catch(Exception e) {
        				 end = counter;
        			}
        			String name = expr.substring(start,end);
        			try {
        				if (expr.charAt(counter) == '[') {
        					int start2 = counter+1;
        					counter = counter+1;
        					int marker = 0;
        					while (counter<=length) {
        						if (expr.charAt(counter)==']') {
        							marker = counter;
        						}
        						counter = counter+1;
        					}
        					
        					counter = marker+2;
        					int end2 = marker+1;
        					
        					int index = (int) evaluate(expr.substring(start2, end2), vars, arrays);
        					
        					for (Array object:arrays) {
        						if (object.name.equals(name)) {
        							operands.push((float) object.values[index]);
        							
        						}
        					}
        					
        				}
        				else {
        					for (Variable object:vars) {
        						if (object.name.equals(name)) {
        							operands.push((float) object.value);
        						}
        					}
        				}
        				
        			}
        			catch(Exception e){
        				for (Variable object:vars) {
    						if (object.name.equals(name)) {
    							operands.push((float) object.value);
    						}
    					}
        			}
        			
        			
        			counter = counter - 1;
        			
        			
    			}
    			
    			else {
    				try {
        				while (Character.isDigit(expr.charAt(counter))) {
        					counter++;
        				}
        				 end = counter;
        			}
        			catch(Exception e) {
        				 end = counter;
        			}
        			
        			
        			operands.push(Float.parseFloat(expr.substring(start, end)));
        			
        			counter = counter - 1;	
    			}
    			
    			
    		}
    		else if (expr.charAt(counter)=='(') {
    			int start = counter+1;
    			
    			while (expr.charAt(counter)!=')') {
    				counter++;
    			}
    			int end = counter;
    			
    			float res = evaluate(expr.substring(start,end), vars, arrays);
    			
    			operands.push(res);
    			counter = end;
    		}
    		
    		else if (expr.charAt(counter)=='+' || expr.charAt(counter)=='-' || expr.charAt(counter)=='*' || expr.charAt(counter)=='/'){
    			
    			
    			if (operators.isEmpty()) {
    				
    				operators.push(expr.charAt(counter));
    			}
    			
    			
    			else if(prectable.get(operators.peek())<prectable.get(expr.charAt(counter))) {
    				
    				operators.push(expr.charAt(counter));
    			}
    			else {
    				
    				float res = 0;
    				char operator = operators.pop();
    				//System.out.println(operator);
    				float val1 = operands.pop();
    				float val2 = operands.pop();
    				
    				
    				if (operator=='+') {
    					res = val1+val2;
    				}
    				else if (operator=='-') {
    					res = val2-val1;
    				}
    				else if (operator=='*') {
    					 res = val1*val2;
    				}
    				else if (operator=='/') {
    					res = val2/val1;
    				}
    				operands.push(res);
    				//operators.push(expr.charAt(counter));
    				counter = counter -1;
    			}
    		}
    		counter++;
    	}
    	float result = 0;
    
    	while (!operators.isEmpty()) {
    			
    			char operator = operators.pop();
			float val1 = operands.pop();
			
			float val2 = operands.pop();
			
			if (operator=='+') {
				result = val1+val2;
			}
			else if (operator=='-') {
				result = val2-val1;
			}
			else if (operator=='*') {
				 result = val1*val2;
			}
			else if (operator=='/') {
				result = val2/val1;
			}
			operands.push(result);
    	}
    	return operands.pop();
    }
}
