package brooksNathan;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/*
 * Write a program that receives a list of words 
 * (either from a user prompt or from command line arguments).  
 * The application should print the list of words 
 * in reverse order and then print out all the distinct words in the list. 
 */

public class Words {

	public static void main(String[] args){
		String input = "";
		Scanner in = new Scanner(System.in);
		String[] strings;
		
		
		System.out.print("Enter a bunch of words: ");
		input = in.nextLine();
		
		// enter all the words as individual elements
		strings = input.split(" ");
		
		//put all the elements into a linked list so I can easily reverse them
		List<String> individualStrings = new LinkedList<>();
		individualStrings = Arrays.asList(strings);
		
		// create a hash set so I can get rid of duplicates
		Set<String> uniqueStrings = new HashSet<String>();
		
		// reverse the individual words
		Collections.reverse(individualStrings);
		
		// print the individual words and add them to the hash set
		// the has set will filter out duplicates
		for(String word : individualStrings){
			System.out.println(word);
			uniqueStrings.add(word);
		}
		
		// print out uniques
		System.out.println("***********");
		for(String word : uniqueStrings){
			System.out.println(word);
		}
	}
}
