/****************************************************
* Dean & Dean
*
*This class handles processing of a student's name.
*****************************************************/

import java.util.Scanner;

public class Student
{
   private String first = "";
   private String last = "";
   
   //*************************************************
   
   public Student()
   { }
   
   
      // This constructor verifies that each passed-in name starts with an uppercase letter and follows with lowercase letters.
   public Student(String first, String last)
   {
      setFirst(first);
      setLast(last);
   }
   
   //*************************************************
   
      // This method verifies that first starts with an uppercase letter and contains lowercase letters thereafter
   public void setFirst(String first)
   {
         // [A-Z] [a-z]* is a regular expression.  See API Patterns class
      if (first.matches("[A-Z][a-z]*"))
      {
         this.first = first;
      }
      else
      {
         System.out.println(last + " is an invalid name.\n" +
            "Names must start with an uppercase letter and have" +
            " lowercase letters thereafter.");
      }
   } // end setFirst()
   
   //************************************************
   
      // This method verifies that last starts with an uppercase letter and contains lowercase letters thereafter
   public void setLast(String last)
   {
         // [A-Z] [a-z]* is a regular expression.  See API Patterns class
      if (last.matches("[A-Z][a-z]*"))
      {
         this.last = last;
      }
      else
      {
         System.out.println(last + " is an invalid name.\n" +
            "Names must start with an uppercase letter and have" +
            " lowercase letters thereafter.");
      }
   } // end setFirst()
   
   //************************************************
      //Print the student's first and last names
   public void printFullName()
   {
      System.out.println(this.first + " " + this.last);
   } // end printFullName()
} // end class Student