package animal2;

import java.util.Scanner;

public class Pets2 {
	public static void main(String[] args){
		char choice = ' ';
		Scanner in = new Scanner(System.in);
		Object obj = new Object();
		
		while(!(choice == 'd' || choice == 'c')){
			System.out.println("Would you like a cat or a dog? ('c' or 'd')");
			choice = Character.toLowerCase(in.next().charAt(0));
			
			if(choice == 'd'){
				obj = new Dog2();
			}
			else if(choice == 'c'){
				obj = new Cat2();
			}
			else{
				System.out.println("You must choose either a 'c' or a 'd'");
			}
		}
		
		if(obj instanceof Dog2){
			System.out.println("Wag tail");
			((Dog2) obj).speak();
		}
		else if(obj instanceof Cat2){
			((Cat2) obj).speak();
		}
	}
}
