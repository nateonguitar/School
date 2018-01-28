/*****************************************************
* Nathan Brooks
* CS 1400
* 
* This class has methods that search for Books
* from the books array
*****************************************************/

import java.util.ArrayList;
import java.util.Scanner;

public class Bookstore
{
	Scanner scanner = new Scanner(System.in);
	
	public int i = 0;
	
	//builds the array of Books
	private ArrayList<Book> books = new ArrayList<Book>();
   
   //******************************************************
   
   public void fillTheStore()
   {
	   books.add(new Book("Siddhartha", "Hesse", "11111"));	// title, author, isbn
	   books.add(new Book("Lolita", "Nabokov", "22222"));	// title, author, isbn
   }
   
   //******************************************************
   
  
   public Book getBook(String query) // query is user entered
   {
	   for (int i=0; i<books.size(); i++)
	   {
		   if (books.get(i).getTitle().contains(query) == true)
		   {
			   return books.get(i);
		   }
	   }
	   return null;
   }
} // end class Bookstore