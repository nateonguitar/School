package brooksNathan;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Converter {
	/*
	Provided the following requirements, create test cases in JUnit to test the requirement and 
	then create the code that will pass the tests.  You will also need a driver class to run the 
	classes/methods.

	Convert an amount on a check to the appropriate text. For example, if the check has 324.56 on it, 
	the method should return “Three Hundred Twenty Four Dollars and Fifty Six cents.”
	 
	Given the expression in infix form (operators appear inline in the expression) like 3 * (2 + 5), 
	transform the expression to its postfix equivalent 3 2 5 + *.
	*/
	
	public static String convertCheck(String string){
		boolean teen = false;
		int numberOfDots = 0;
		String outputString = "";
		int digits = 0;
		int loopTimes = 0;
		String firstHalf = "";
		
		// check for bad input
		for (int i=0; i<string.length(); i++){
			if(string.charAt(i) == ('.')){
				numberOfDots++;
			}
			
			// if the character isn't a digit or is not a .
			if(!Character.isDigit(string.charAt(i))    &&   !(string.charAt(i) == ('.'))     ){
				throw new RuntimeException("Not a valid string");
			}
			
			if(numberOfDots > 1){
				throw new RuntimeException("Too many dots in your string");
			}
		}
			
		// convert the string
		digits = string.indexOf(".");
		
		// if no decimal was placed it returns a negative
		if(digits < 0){
			digits = string.length();
			loopTimes = 0;
		}
		else{
			loopTimes = digits;
		}
		
		firstHalf = string.substring(0, loopTimes);
		
		// string now equals what's after the decimal
		string = string.substring(string.indexOf('.') + 1);
		
		for(int i=0; i<loopTimes; i++){
			// thousands
			if(firstHalf.length() == 4){
				switch (firstHalf.charAt(0)){
				case '1':
					outputString += "One ";
					break;
				case '2':
					outputString += "Two ";
					break;
				case '3':
					outputString += "Three ";
					break;
				case '4':
					outputString += "Four ";
					break;
				case '5':
					outputString += "Five ";
					break;
				case '6':
					outputString += "Six ";
					break;
				case '7':
					outputString += "Seven ";
					break;
				case '8':
					outputString += "Eight ";
					break;
				case '9':
					outputString += "Nine ";
					break;
				default:
					outputString += "";
				}
				outputString += "Thousand ";
			}
			
			if(firstHalf.length() == 3){
				switch (firstHalf.charAt(0)){
				case '1':
					outputString += "One ";
					break;
				case '2':
					outputString += "Two ";
					break;
				case '3':
					outputString += "Three ";
					break;
				case '4':
					outputString += "Four ";
					break;
				case '5':
					outputString += "Five ";
					break;
				case '6':
					outputString += "Six ";
					break;
				case '7':
					outputString += "Seven ";
					break;
				case '8':
					outputString += "Eight ";
					break;
				case '9':
					outputString += "Nine ";
					break;
				default:
					outputString += "";
				}
				outputString += "Hundred ";
			}
			
			if(firstHalf.length() == 2){
				switch (firstHalf.charAt(0)){
				case '1':
					teen = true;
					break;
				case '2':
					outputString += "Twenty ";
					break;
				case '3':
					outputString += "Thirty ";
					break;
				case '4':
					outputString += "Forty ";
					break;
				case '5':
					outputString += "Fifty ";
					break;
				case '6':
					outputString += "Sixty ";
					break;
				case '7':
					outputString += "Seventy ";
					break;
				case '8':
					outputString += "Eighty ";
					break;
				case '9':
					outputString += "Ninety ";
					break;
				default:
					outputString += "";
				}
			}
			
			if(firstHalf.length() == 1 && teen == true){
				switch (firstHalf.charAt(0)){
				case '0':
					outputString += "Ten ";
					break;
				case '1':
					outputString += "Eleven ";
					break;
				case '2':
					outputString += "Twelve ";
					break;
				case '3':
					outputString += "Thirteen ";
					break;
				case '4':
					outputString += "Fourteen ";
					break;
				case '5':
					outputString += "Fifteen ";
					break;
				case '6':
					outputString += "Sixteen ";
					break;
				case '7':
					outputString += "Seventeen ";
					break;
				case '8':
					outputString += "Eighteen ";
					break;
				case '9':
					outputString += "Nineteen ";
					break;
				default:
					outputString += "";
				}
			}
			else if(firstHalf.length() == 1 && teen == false){
				switch (firstHalf.charAt(0)){
				case '0':
					outputString += "Ten ";
					break;
				case '1':
					outputString += "One ";
					break;
				case '2':
					outputString += "Two ";
					break;
				case '3':
					outputString += "Three ";
					break;
				case '4':
					outputString += "Four ";
					break;
				case '5':
					outputString += "Five ";
					break;
				case '6':
					outputString += "Six ";
					break;
				case '7':
					outputString += "Seven ";
					break;
				case '8':
					outputString += "Eight ";
					break;
				case '9':
					outputString += "Nine ";
					break;
				default:
					outputString += "";
				}
			}
			
			// remove first character from string
			firstHalf = firstHalf.substring(1);
		}
		
		outputString += "Dollars and ";
		
		loopTimes = string.length();
		
		if(string.charAt(0) == '1'){
			teen = true;
		}
		else{
			teen = false;
		}
		
		if(numberOfDots == 1){
			// now string should just be what's after the decimal
			for(int i=0; i<loopTimes; i++){
				if(string.length() == 2){
					switch (string.charAt(0)){
					case '1':
						teen = true;
						break;
					case '2':
						outputString += "Twenty ";
						break;
					case '3':
						outputString += "Thirty ";
						break;
					case '4':
						outputString += "Forty ";
						break;
					case '5':
						outputString += "Fifty ";
						break;
					case '6':
						outputString += "Sixty ";
						break;
					case '7':
						outputString += "Seventy ";
						break;
					case '8':
						outputString += "Eighty ";
						break;
					case '9':
						outputString += "Ninety ";
						break;
					default:
						outputString += "";
					}
				}
				
				if(string.length() == 1 && teen == true){
					switch (string.charAt(0)){
					case '0':
						outputString += "Ten ";
						break;
					case '1':
						outputString += "Eleven ";
						break;
					case '2':
						outputString += "Twelve ";
						break;
					case '3':
						outputString += "Thirteen ";
						break;
					case '4':
						outputString += "Fourteen ";
						break;
					case '5':
						outputString += "Fifteen ";
						break;
					case '6':
						outputString += "Sixteen ";
						break;
					case '7':
						outputString += "Seventeen ";
						break;
					case '8':
						outputString += "Eighteen ";
						break;
					case '9':
						outputString += "Nineteen ";
						break;
					default:
						outputString += "";
					}
				}
				else if(string.length() == 1 && teen == false){
					switch (string.charAt(0)){
					case '0':
						outputString += "Ten ";
						break;
					case '1':
						outputString += "One ";
						break;
					case '2':
						outputString += "Two ";
						break;
					case '3':
						outputString += "Three ";
						break;
					case '4':
						outputString += "Four ";
						break;
					case '5':
						outputString += "Five ";
						break;
					case '6':
						outputString += "Six ";
						break;
					case '7':
						outputString += "Seven ";
						break;
					case '8':
						outputString += "Eight ";
						break;
					case '9':
						outputString += "Nine ";
						break;
					default:
						outputString += "";
					}
				}
				string = string.substring(1);
			}
		}
		else{
			outputString += "Zero ";
		}
		
		outputString += "Cents";
		
		return outputString;
	}
	
	public static String convertInfixToPostfix(String stringToTest){
		
		return " ";
	}
}
