/****************************************************
* PersonDriver.java
* Dean & Dean
*
*This class is a demonstration driver for the Person class
*****************************************************/

public class PersonDriver
{
   public static void main(String[] args)
   {
      Person person1 = new Person();
      Person person2 = new Person();
      
      person1.setName("Jonathan");
      person2.setName("Benji");
      
      
      System.out.println(person1.getName() + ", " + person2.getName());
      
      person1.swapPerson(person2);
      System.out.println(person1.getName() + ", " + person2.getName());
   }  // end main
} // end class PersonDriver