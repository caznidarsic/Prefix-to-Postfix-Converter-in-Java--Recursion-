/* Christian Znidarsic
 * Lab 2
 * EN.605.202.86.SP22 Data Structures
 * 
 * The PreToPost Class. 
 * 
 * 	This class includes the method called preToPost, which takes BufferedReader, Scanner, and PrintWriter 
 * objects as inputs. The BufferedReader reads from the input file, and the PrintWriter writes to the 
 * output .txt file. The Scanner copies the contents from the input file to the output file, so that 
 * the user can view both the input and output files next to each other.
 * 
 * 	Other methods are isOperator(), isOperand(), and isSpaceOrTab(), which are all used in the 
 * conversion process.
 * 
 * 	The convertExpression() method uses recursion to convert a prefix expression to a postfix expression.
 * */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class PreToPost {
	
	/* 	
	 * 	The preToPost method is static, so it can be called from main easily without creating an object
	 * It takes a BufferedReader item, which is the input .txt file, as input. It also takes a
	 * PrintWriter object which holds the output .txt file, and a Scanner object which copies 
	 * from the input to the output file.
	 * 
	 * 	preToPost() implements a queue to discern between valid and invalid prefix expressions in input.
	 * It calls convertExpression to convert from prefix to postfix.
	*/
	public static void preToPost(BufferedReader input, PrintWriter output) throws IOException {
		
		output.println("Prefix to Postfix Results\n");
//		//print labels and lines in the output file
		output.println("----------------------------------------------------------------------------------------------------------------------------------------");
//		output.println("Input Prefix Expressions:\n");
//
//		//copy input file to output file
//		while (input2.hasNextLine()) {
//			String line = input2.nextLine();
//			output.println(line);
//		}
//		//print spacing after the prefix expressions
//		output.println("-------------------------------------------------------------------------------------------");
		output.print("\n\n");
		
		
		output.printf("%-32s", "Input Prefix Expressions:");
		output.print("Output Postfix Expressions:\n");
		output.println();
		
		
		
		//LOCAL VARIABLES
		
		/* The inputQueue will contain all valid operands and operators.
		 * The errorQueue will contain all invalid characters.
		 */
		
		Queue inputQueue = new Queue();
		Queue errorQueue = new Queue();
		
		int c = 0;
		/* operatorCounter starts at one, and increases by one for every 
		 * operator that is read by the buffered reader, and decreases by 
		 * one for every operand that is read by the buffered reader.
		 * For the input to be a valid prefix expression, two conditions 
		 * must be met. 1. operatorCounter must be equal to zero after the 
		 * entire expression has been read. 2. operatorCounter must never 
		 * reach zero or less at any point before the very last character
		 * is read.
		 */
		int operatorCounter = 1;
		/* hitZeroCounter works along with operatorCounter by counting the
		 * number of times operatorCounter hits zero. If hitZeroCounter is
		 * greater than 1 after the input expression has been read, then 
		 * the expression is not a valid prefix expression.
		 */
		int hitZeroCounter = 0;
		
		/* The while loop that reads through the entire input. Terminates 
		 * when -1 is read.
		 */
		
		while((c = input.read()) != -1) {
			
			char character = (char) c;
			
			
			//disregard spaces and tabs outright
			if (!isSpaceOrTab(character)) {
				
				//if the character is an operand, add it to the inputQueue
				if (isOperand(Character.toString(character))) {
					
					inputQueue.enqueue(Character.toString(character));
					
					operatorCounter -= 1; //decrement the operatorCounter by one when an operand is read
					
					//increment hitZeroCounter when the operatorCounter hits zero
					if (operatorCounter <= 0 && hitZeroCounter < 2) {
						
						hitZeroCounter += 1;
						
					}
					
				}
				
				
				/* If the input is an operator, add it to the inputQueue
				 * 
				 */
				else if (isOperator(Character.toString(character))) {
					
					
					inputQueue.enqueue(Character.toString(character));
					
					operatorCounter += 1; //increment operatorCounter when an operator is added to inputQueue
					
					
				}
				
				
				//if a newline is read, then the input expression has been read completely
				else if (character == '\n') {
					
					//test to make sure the input line isn't empty. This rules out erroneous blank lines in the input file
					if (!inputQueue.isEmpty()) {
						
						/*
						 * Print the input prefix expression. To do this, inputQueue is converted to 
						 * a string and printed using printf(). inputQueue() is copied into a
						 * temporary queue tempQueue(). After printing, inputQueue() is reinstated
						 * from tempQueue() and tempQueue() is emptied. This process is necessary 
						 * so that the input prefix expression can be printed with spacing using
						 * printf().
						 */
						String temp = "";
						Queue tempQueue = new Queue();
						while (!inputQueue.isEmpty()) {
							temp = temp + inputQueue.front.key;
							tempQueue.enqueue(inputQueue.front.key);
							inputQueue.dequeue();
						}
						output.printf("%-32s" , temp);
						
						while (!tempQueue.isEmpty()) {
							inputQueue.enqueue(tempQueue.front.key);
							tempQueue.dequeue();
						}
						
						/*
						 * if the errorQueue is not empty, then the input contained invalid characters.
						 * Print an error message along with the invalid characters stored in 
						 * errorQueue.
						 */
						if (!errorQueue.isEmpty()) {
							
							output.print("<--- This prefix expression contains the following invalid characters: ");
							
							while (!errorQueue.isEmpty()) {
								output.print("'" + errorQueue.front.key + "' ");
								errorQueue.dequeue();
							}
							output.println();
						}
						
						/*
						 * If the operatorCounter is not zero after reading the entire expression,
						 * then it is an invalid prefix expression. Print an error message.
						 */
						else if (operatorCounter != 0) {
							
							if (operatorCounter > 0) {
								output.println("<--- This is an invalid prefix expression. Too many operators.");
							}
							else {
								output.println("<--- This is an invalid prefix expression. Too many operands.");
							}
							
						}
						
						/*
						 * If zero has been hit more than once, then the input expression
						 * is invalid. Print an error message.
						 */
						else if (hitZeroCounter > 1) {
							
							output.println("<--- This is an invalid prefix expression. Check order of operands and operators.");
							
						}
						
						
						/*
						 * If none of the above conditions are met, then the input is a valid 
						 * prefix expression. At this point, the valid expression is stored in
						 * inputQueue. Pass inputQueue to convertString to convert from
						 * prefix to postfix and print the result.
						 */
						else {
							
							output.println(convertExpression(inputQueue));
						}
						
						
						//clear the inputQueue
						while (!inputQueue.isEmpty()) {
							inputQueue.dequeue();
						}
						
						//clear the errorQueue
						while (!errorQueue.isEmpty()) {
							errorQueue.dequeue();
						}
					}
					
					/* 
					 * Reset the operatorCounter and hitZeroCounter after an expression has
					 * been processed.
					 */
					operatorCounter = 1;
					hitZeroCounter = 0;
										
				}
				
				/*
				 * If none of the above conditions are met, then the current character
				 * is an invalid character. Add it to the errorQueue. Also add it to
				 * the inputQueue so the original expression can be printed along with
				 * the error message.
				 */
				else {
					
					errorQueue.enqueue(Character.toString(character));
					inputQueue.enqueue(Character.toString(character));
					
				}
				
			}

			
		}
		
		
		output.println("----------------------------------------------------------------------------------------------------------------------------------------");
		
	}
	
	

	
	//returns true if input String is a valid operator
	//inputs: String
	//outputs: boolean
	private static boolean isOperator(String input) {
		switch (input) {
		case "+":
			return true;
		case "-":
			return true;
		case "*":
			return true;
		case "/":
			return true;
		case "$":
			return true;
		default:
			return false;
		}
	}
	
	//isOperand returns true if the input String is a valid operand, and false otherwise
	//inputs: String
	//outputs: boolean
	private static boolean isOperand(String input) {
		
		char c = input.charAt(0);
		
		//determine if the input is a valid letter operand
		if (Character.isLetter(c)) {
			return true;
		}
		else {
			return false;
		}
	}

	
	//isSpaceOrTab determines if input is a space or tab
	//inputs: char
	//outputs: boolean
	private static boolean isSpaceOrTab(char input) {
		
		//determine if the input is a space or tab
		if (input == ' ' || input == '\t') {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	/*
	 * convertExpression() utilizes recursion and takes a queue 
	 * containing a prefix expression as input, and outputs a 
	 * String that holds the corresponding postfix expression.
	 */
	private static String convertExpression(Queue input) {
		
		String temp = input.front.key; //store current character in temp
		input.dequeue(); //take character off of queue
		
		/*
		 * if the current char is an operator, then return the next two 
		 * operands concatenated with the current char. If one of the
		 * next to chars in the expression is an operator, then call 
		 * convertExpression recursively to get a block instead of a 
		 * single operand.
		 */
		if (isOperator(temp)) {
			return convertExpression(input) + convertExpression(input) + temp;
		}
		
		else if (isOperand(temp)) { //if current char is an operand, return it
			return temp;
		}
		
		else {
			System.out.println("error!");
			return "error";
		}
	}
}
	
	
	
	
	