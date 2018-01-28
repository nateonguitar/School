/****************************************************
* Car2Driver.java
* Dean & Dean
*
*This class is a demonstration driver for the Car2 class.
*****************************************************/

public class Car2Driver
{
   public static void main(String[] args)
   {
      Car2 nathanCar = new Car2();
      Car2 nickCar = new Car2();
      
      nathanCar.setMake("Audi");
      nathanCar.setYear(2012);
      nathanCar.setColor("green");
      
      nickCar.setMake("Audi");
      nickCar.setYear(2012);
      nickCar.setColor("green");
      
      if (nathanCar.equals(nickCar))
      {
         System.out.println("Cars have identical features.");
      }
   } // end main
} // end Car2Driver
