public class TestOperators
   {
   public static void main(String[] args)
      {
      double interestRate = 5.2;
      double balance = 23.7;
      int interestInDollars;
      
      double total;
      
      total = (balance * interestRate);
      
      System.out.println("Interest total: " + total);
      
      interestInDollars = (int) (balance * interestRate);
      
      System.out.println("Interest in dollars: " + interestInDollars);
      } // End main
   } // End class TestOperators