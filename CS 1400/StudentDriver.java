/****************************************************
* Dean & Dean
*
*This class acts as a driver for the Student class
*****************************************************/
   
public class StudentDriver
{
   public static void main(String[] args)
   {
      Student s1;
      Student s2;
      
      s1 = new Student();
      s1.setFirst("Adeeb");
      s1.setLast("Jarrah");
      s1.printFullName();
      
      s2 = new Student("Heejoo", "Chun");
      s2.printFullName();
   } // end main
} // end class StudentDriver