/*****************************************************
* Nathan Brooks
* CS 1400
* 
* 
* This is the driver for the bookstore.
* It asks the user for a book and implements methods that
* search for the book
*****************************************************/

import java.util.Scanner;

public class BookstoreDriver
{
   public static void main(String[] args)
   {
      Scanner stdIn = new Scanner(System.in);
      Bookstore bookstore = new Bookstore();
      String query;								// user entered search
      boolean done = false;						// to end the while loop
      char ch;									// to test for Q for quit
      
      	// build the book store
      bookstore.fillTheStore();
      
      System.out.print("Please enter a title to search for a book (or 'q' to quit):");
      query = stdIn.nextLine();
      ch = query.charAt(0);
      
      if (ch == 'q' || ch == 'Q')
      {
    	  done = true;
      }
      while (!done)
      {          
	      	// use entered information to query the bookstore for book's existence
	      if (bookstore.getBook(query) != null)
	      {
	    	  System.out.println("\t- " + bookstore.getBook(query).toString() + " -");
	    	  
	    	  System.out.print("\nPlease enter a title to search for a book (or 'q' to quit):");
	          query = stdIn.nextLine();
	          ch = query.charAt(0);
	      }
	      else if (ch == 'q' || ch == 'Q')
	      {
	    	  done = true;
	      }
	      else
	      {
	    	 System.out.println("\t- Sorry - book not found. -"); 
	    	 
	    	 System.out.print("\nPlease enter a title to search for a book (or 'q' to quit):");
	         query = stdIn.nextLine();
	         ch = query.charAt(0);
	      }
      } // end while */
   } // end main
} // end class BookstoreDriver