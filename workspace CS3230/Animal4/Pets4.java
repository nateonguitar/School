package Animal4;

import java.util.Scanner;

public class Pets4 {
	public static void main(String[] args){
		char choice = ' ';
		Scanner in = new Scanner(System.in);
		Animal3 animal;
		
		System.out.println("Would you like a cat or a dog? ('d' or any other character for cat)");
		choice = Character.toLowerCase(in.next().charAt(0));
		
		if(choice == 'd'){
			animal = new Dog3();
		}
		else{
			animal = new Cat3();
		}
			
		animal.speak();
	}
}
