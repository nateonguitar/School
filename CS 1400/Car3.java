/****************************************************
* Car3.java
* Dean & Dean
*
*This class stores and prints height values
* "overloading"
*  -using the same method names but requiring different
*  -data types.  
*  -Makes it so you don't have to remember
*  -different names for similar methods.
*****************************************************/

class Height{
   double height;    // a person's height
   String units;     // like cm for centimeters
   
   //*****************************************************
   
   public void setHeight(double height)
   {
      this.height = height;
      this.units = "cm";
   }
   
   //*****************************************************
   
   public void setHeight(double height, String units)
   {
      this.height = height;
      this.units = units;
   }
   
   //*****************************************************
   
   public void print()
   {
      System.out.println(this.height + " " + this.units);
   }
} // end class Height