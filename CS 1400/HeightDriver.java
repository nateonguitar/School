/****************************************************
* Dean & Dean
*
*This class stores and prints height values
* "overloading"
*  -using the same method names but requiring different
*  -data types.  
*  -Makes it so you don't have to remember
*  -different names for similar methods.
*****************************************************/

public class HeightDriver
{
   public static void main(String[] args)
   {
      Height myHeight = new Height();
      
      myHeight.setHeight(72.0, "in");
      myHeight.print();
      myHeight.setHeight(180.0);
      myHeight.print();
   }  // end main
}  // end class HeightDriver