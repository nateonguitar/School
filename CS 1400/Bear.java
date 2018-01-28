/*******************************************
* Dean and Dean
*
* This class models a toy bear
*******************************************/
   
public class Bear
{
   private final String MAKER; // bear's manufacturer
   private final String TYPE;  // type of bear
   
   //******************************************************8
   
   public Bear(String maker, String type)
   {
      MAKER = maker;
      TYPE = type;
   }
   
   //********************************************************
   
   public void display()
   {
      System.out.println(MAKER + " " + TYPE);
   }
} // end class Bear