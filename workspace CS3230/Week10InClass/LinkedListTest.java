package brooksNathan;

import java.util.LinkedList;

public class LinkedListTest {

	public static void main(String[] args) {
		LinkedList<Integer> numbers = new LinkedList<>();
		
		int max = 100;
		int min = 0;
		
		int sum = 0;
		float avg = 0.0f;
		
		for(int i=0; i<25; i++){
			numbers.add((int)(Math.random() * (max - min) + min));
		}
		
		for(int i=0; i<numbers.size(); i++){
			sum += numbers.get(i);
		}
		
		avg = sum / 25;
		
		System.out.print("Sum: " + sum + " - Average: " + avg);
	}
}
