/****************************************************
* Car3Drivers.java
* Dean & Dean
*
*This drives Car3 to illistrate method-call chaining.
*****************************************************/

public class Car3Driver
{
   public static void main(String[] args)
   {
      Car3 car1 = new Car3();
      Car3 car2 = new Car3();
      
      car1.setMake("Honda").setYear(2012).setColor("silver").printIt();
      car2.setMake("Mazda").setYear(2008).setColor("black").printIt();
   } // end main
} // end class Car3Driver

/* In general, to make a method chainable, do these 2 things:
1. In the method heading, specify the return type as the class ofa potential following method.
2. Finish the method boy with:
      return<reference-to-object-that-will-call-the-following-method>; 
      
   for example:
      
   ch = stdIn.nextLine().charAt(0);   */