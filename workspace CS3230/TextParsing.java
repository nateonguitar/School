/*************************************************
* Name: TextParsing.java
* Author: Nathan Brooks
* Due Date: 27 January 2016
* Assignment: Week 2 In Class Assignment - Text Parsing
* Description: Converts inputs to pig latin
*              All caps is ok and checks for bad inputs
*/

import java.util.Scanner;

public class TextParsing {

	public static void main(String[] args) {
		// DATA:
		String input = "input";
		char first = 'a';
		boolean ok = true; // flagged false if something goes wrong
		Scanner scanner = new Scanner(System.in);
		
		
		// CODE:
		System.out.println("PIG LATIN CONVERTER");
		
		while(ok && (Character.toLowerCase(first) != 'q' || input.length() > 1)){
			System.out.print("Enter a word: ");
			
			// catch input and store first character
			// make lower case to make switch statement easier
			input = scanner.next();
			first = input.charAt(0);
			first = Character.toLowerCase(first);
			
			// make sure input is all letters
			ok = isAllLetters(input);
			
			if(ok){
				switch(first){
				// vowels
				case 'a':
				case 'e':
				case 'i':
				case 'o':
				case 'u':
					System.out.println(input);
					break;
					
				
				case 'c':
				case 't':
				case 's':
				case 'p':
					// 2 letter consonant starts like "stab" or "phalanx" or "spot"
					if(Character.toLowerCase(input.charAt(1)) == 'h' || 
					   Character.toLowerCase(input.charAt(1)) == 't' || 
					   Character.toLowerCase(input.charAt(1)) == 'p'){
						input = convertToPigLatin(input, 2);
						System.out.println(input);
					}
					// 1 letter consonant starts
					else{
						input = convertToPigLatin(input, 1);
						System.out.println(input);
					}
					break;
				
				// possible quit
				case 'q':
					if(input.length() == 1){
						System.out.println("I hope you enjoyed your porcine experience! Please come again.");
					}
					
				// starts with "qu" like "quack"
					else if(Character.toLowerCase(input.charAt(1)) == 'u'){
						input = convertToPigLatin(input, 2);
						System.out.println(input);
					}
					break;
					
				// regular 1 consonant starts
				default:
					input = convertToPigLatin(input, 1);
					System.out.println(input);
					
				}
			}
			else{
				// used non-letters
				// which means ok is false
				System.out.print("Invalid input - can't process these characters: ");
				for(int i=0; i<input.length(); i++){
					if( !Character.isLetter(input.charAt(i)) ){
						System.out.print(input.charAt(i) + " ");
					}
				}
			}
		} // end while
	} // end main
	
	
	// functions!!!!!!
	//--------------------------------------------------------
	
	// checks to make sure a string is all letters
	public static boolean isAllLetters(String st){
		for(int i=0; i<st.length(); i++){
			if(!(Character.isLetter(st.charAt(i)))){
				return false;
			}
		}
		return true;
	}
	
	//--------------------------------------------------------
	
	// chops the start off and sticks it on the end, then adds "ay"
	public static String convertToPigLatin(String input, int startPoint){
		input = input.substring(startPoint, input.length()) + input.substring(0, startPoint) + "ay";
		return input;
	}
}// end class
