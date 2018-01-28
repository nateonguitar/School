/****************************************************
* Dean & Dean
*
*This class stores and retrieves a sales clerk's data
*****************************************************/

class SalesClerk
{
   private String name;       // sales clerk's name
   private double sales = 0.0;// total sales for clerk
   
   //******************************************************
   
   public SalesClerk(String name)
   {
      this.name = name;
   }
   
   //******************************************************
   
   public String getName()
   {
      return name;
   }
   
   public double getSales()
   {
      return sales;
   }
   
   //*****************************************************
   
      // adjust clerk's total sales by adding the passed in sale
      
   public void adjustSales(double amount)
   {
      sales += amount;
   }      
} // end class SalesClerk