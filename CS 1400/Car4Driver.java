/****************************************************
* Dean & Dean
*
*This class is a demonstration driver for the Car4 class
*****************************************************/

public class Car4Driver
{
   public static  void main(String[] args)
   {
      Car4 lanceCar = new Car4("Ford", 2011, "lime"); // constructor call
      Car4 azadehCar = new Car4("Volt", 2014, "red"); // constructor call
      
      System.out.println(lanceCar.getMake());
   } // end main
} // end class Car4Driver