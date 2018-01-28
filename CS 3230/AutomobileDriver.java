package brooksNathan;

/*************************************************
* Name: AutomobileDriver.java
* Author: Nathan Brooks
* Due Date: 30 January 2016
* Assignment: Assignment 2b - Chapter 3: Tax Preparation
* Description: Lets you pick a color and a make for an Automobile
*/

import java.util.Scanner;

public class AutomobileDriver {

	public static void main(String[] args) {
		Automobile auto = new Automobile();
		Scanner input = new Scanner(System.in);
		String inputString = "";
		int count;
		
		// capture user input and validate
		do{
			System.out.print("How many cars do you want to consider? ");
			inputString = input.next();
			if(hasNonNumbers(inputString)){
				System.out.println("Only numbers are valid");
			}
		}while(hasNonNumbers(inputString));
		
		// snag the input string so we can use it
		count = Integer.parseInt(inputString);
		
		// set as many automobiles as the user specified
		for(int i=0; i<count; i++){
			auto.setMake();
			auto.setColor();
			auto.printColor().printMake();
		}
		input.close();
	}
	
	// helper function, checks for any character that's not a number
	public static boolean hasNonNumbers(String st){
		for(int i=0; i<st.length(); i++){
			if(!Character.isDigit(st.charAt(i))){
				return true;
			}
		}
		return false;
	}

}

class Automobile{
	private String make = "";
	private String color = "";
	private String choice = "";
	Scanner in = new Scanner(System.in);
	Automobile(){
		
	}
	
	// receives user input, validates, and sets car make if input is valid
	public void setMake(){
		while( !(choice.equals("b") || choice.equals("c") || choice.equals("p")) ){
			System.out.print("\nSelect Buick, Chevrolet, or Pontiac (b,c,p): ");
			choice = in.next();
			switch(choice){
			case "b":
				this.make = "Buick";
				break;
			case "c":
				this.make = "Chevrolet";
				break;
			case "p":
				this.make = "Pontiac";
				break;
			default:
				System.out.println("The only valid selections are 'b', 'c', or 'p'");
				break;
			}
		}
		choice = "";
	}
	
	// receives user input, validates, and sets car color if input is valid
	public void setColor(){
		while( !(choice.equals("b") || choice.equals("g") || choice.equals("r")) ){
			System.out.print("\nSelect blue, green, or red (b,g,r): ");
			choice = in.next();
			switch(choice){
			case "b":
				this.color = "blue";
				break;
			case "g":
				this.color = "green";
				break;
			case "r":
				this.color = "red";
				break;
			default:
				System.out.println("The only valid selections are 'b', 'g', or 'r'");
				break;
			}
		}
		choice = "";
	}
	
	// display make
	public Automobile printMake(){
		System.out.print(this.make + " ");
		return this;
	}
	
	// display color
	public Automobile printColor(){
		System.out.print(this.color + " ");
		return this;
	}
}