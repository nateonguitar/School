// Test Finite State Machine Class
public class CS2130FinalProblem16
{
   
   public static boolean isPrime(long numToCheck)
   {
      // 1 will not work with my checker loop
      if(numToCheck == 1)
      {
         return false;
      }
      // 2 is the only even prime number
      else if(numToCheck == 2)
      {
         return true;
      }
      // check for even numbers
      else if(numToCheck % 2 == 0)
      {
         return false;
      }
      else
      {
         // only check up to half the number
      // skip evens, already checked
         for(long i=3; i<numToCheck/2; i+=2)
         {
            if(numToCheck % i == 0)
            {
               return false;
            }
         }
         return true;
      }
   }
   
   public static void main(String[] args)
   {
      int count = 0;
      for(int j=1; j<1000; j++)
      {
         if(isPrime(j) == true)
         {
            count++;
            System.out.println(j);
         }
      }
      System.out.println("\n number of primes: " + count);

   } // end main
} // end class
