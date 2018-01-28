public class P1PartB4
{
	public static void main(String args[])
	{
		long X, B, Y;
		if(args.length >= 1){
			X = Long.parseLong(args[0]);
         B = Long.parseLong(args[1]);
         
			Y = BaseB(X, B);
         
			System.out.println("\n X = " + X + "      B = " + B + "     Y = " + Y);         
		}
	return;
	}
   
   //--------------------------------------------
   // I wrote my own exponent function right here
   //--------------------------------------------
   
   public static long power(long base, long exponent)
   {
      long product = base;
      for(int i=0; i<exponent; i++)
      {
         product *= base;
      }
      return product;
   }
   
   //------------------------------------------
   
	public static long BaseB(long x, long b)
	{
      long exponentCounter = b;
      long exponentCount = 0;
      long remainder = 0;
      long numToReturn = 0;
      long timesMultiplied;
      
      
      // finding the largest exponent possible for our base
      while(exponentCounter < x)
      {
         exponentCounter *= b;
         exponentCount++;
      }
      
      for(long i=exponentCount; i>=-1; i--)
      {         
         // shift the number to the left one space.  First time does nothing because it is 0
         numToReturn *= 10;
         
         // while the base raised to the current power is less than the x being manipulated
         timesMultiplied = 0;
         while(power(b, i) < x)
         {
            timesMultiplied++;
            x -= power(b, i);
         }
         numToReturn += timesMultiplied;
      }
      
      // for the final addition so I don't end up with a 0 as my final digit
      // the power function I wrote does not handle a 0 exponent, it just returns 0
      // this line fixes my lack-luster power function I wrote
      numToReturn += x;
      
      return numToReturn;
	}
} // end class