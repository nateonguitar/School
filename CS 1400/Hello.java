public class TestOperators
   {
   public static void main(String[] args)
      {
      int x;
      int y = 2;
      double z = 3.0;
      
      x = 5;
      System.out.println("x = y - z = " + (x + y + z));
      x += y;
      y++;
      z--;
      z *= x;
      System.out.println("x + y + z = " + (x + y + z));
      } // End main
   } // End class TestOperators