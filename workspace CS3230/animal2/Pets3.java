package animal2;

import java.util.Scanner;

public class Pets3 {
	public static void main(String[] args){
		char choice = ' ';
		Scanner in = new Scanner(System.in);
		Animal2 animal;
		
		System.out.println("Would you like a cat or a dog? ('d' or any other character for cat)");
		choice = Character.toLowerCase(in.next().charAt(0));
		
		if(choice == 'd'){
			animal = new Dog2();
		}
		else{
			animal = new Cat2();
		}
			
		animal.speak();
	}
}
