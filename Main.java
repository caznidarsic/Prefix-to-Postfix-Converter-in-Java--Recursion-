/* Christian Znidarsic
 * Lab 2
 * EN.605.202.86.SP22 Data Structures
 * 
 * The Main Class.
 * 
 * 	Main is the driver class of this project. It requests two file names from the 
 * user. 1. The input file path/name, and 2. the output file path/name. These 
 * operations are enclosed in a try-catch block to ensure that a valid input 
 * file name is given.*/

import java.io.*;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


	
public class Main {

	public static void main(String[] args) throws IOException {
		boolean stopper = true;
		
		/*a while loop to keep iterating until the user has entered a valid 
		input file*/
		while(stopper) {
			try {
				//get name of input file from user
				Scanner keyboard = new Scanner(System.in);
				
				/*because we are getting input from the console, the user
				must specify the input file path in addition to the name*/
				System.out.println("Please enter an input file path:");
				
				String fileName = keyboard.nextLine();
				
				
				//get path of output file from user
				System.out.println("Please enter an output file path:");
				
				String outputFileName = keyboard.nextLine();
				
				PrintWriter outputFile = new PrintWriter (outputFileName);
				
				
				//create a File object using the input file name from user
				File inputFile = new File(fileName);
				
				FileReader fr = new FileReader(inputFile);
				
				BufferedReader br = new BufferedReader(fr);

				
				/*call the static method preToPost from PreToPost. 
				Pass in the BufferedReader object br (the input file), 
				and the PrintWriter object outputfile (the output file).*/
				PreToPost.preToPost(br, outputFile);
				
				
				//close the bufferedReader, keyboard, and outputFile
				br.close();
				keyboard.close();
				outputFile.close();
				
				//stop the while loop once the program has executed
				stopper = false;
				
			//if an invalid input file was entered, catch the exception
			} catch (FileNotFoundException e) {
				
				System.out.println("Input file not found.");
				
			}
		}
				
		
	}
	
}


