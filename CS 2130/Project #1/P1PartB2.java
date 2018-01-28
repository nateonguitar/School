public class P1PartB2
{
	public static void main(String args[])
	{
		long X, Y;
		if(args.length >= 1){
			X = Long.parseLong(args[0]);
			if(X >= 1){
				Y = reverseDigits(X);
				System.out.println("\n X = " + X + "     Y = " + Y);
			}
		}
	return;
	}

	public static long reverseDigits(long x)
	{
		long y = 0;
      int intLength = 0; // to test how many digits x has
      
		for(int i=1; i<=x; i*=10)
		{
         if(x >= i)
         {
            intLength++; // count how many digits x has
         }
		}
      
      for(int i=0; i<intLength; i++)
      {
         // shift result to the left one
         y *= 10;
         
         // add the remainder from dividing by 10
         y += x % 10;
         
         // shift x right one and truncates off the decimal because it is a long
         x /= 10;
      }
      
	return y;
	}

} // end class