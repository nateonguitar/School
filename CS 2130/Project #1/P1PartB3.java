public class P1PartB3
{
	public static void main(String args[])
	{
		long X1, X2, Y;
		if(args.length >= 1){
			X1 = Long.parseLong(args[0]);
         X2 = Long.parseLong(args[1]);
         
			if(X1 >= 1 && X2 >= 1){
				Y = GCD(X1, X2);
				System.out.println("\n X1 = " + X1 + "      X2 = " + X2 + "     Y = " + Y);
			}
         else{
            System.out.println("Invalid numbers");
         }
         
		}
	return;
	}

	public static long GCD(long x1, long x2)
	{
      long y = 0;
      long swapper = 0;
      long larger = 0;
      
      // find out which of the values is smaller
      if(x1 - x2 < 0)
      {
         y = x1;
         larger = x2;
      }
      else
      {
         y = x2;
         larger = x1;
      }
      
      // while remainder != 0
      while(larger % y != 0)
      {
         swapper = y;
         y = larger % y;
         larger = swapper;
      } 
		return y;
	}
} // end class