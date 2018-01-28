/*****************************************************
* Nathan Brooks
* CS 1400
* 
* This class instantiates new books
*****************************************************/

public class Book
{
   private String title;
   private String author;
   private String isbn;
   
   public Book(String title, String author, String isbn)
   {
      this.title = title;
      this.author = author;
      this.isbn = isbn;
   } // end constructor
   
   //***************************************************
   
   public String getTitle() 
    {
		return title;
	}
   
   //**************************************************
   
   public String toString()
   {
	   return title + " " + author + " " + isbn;
   }
} // end class Book