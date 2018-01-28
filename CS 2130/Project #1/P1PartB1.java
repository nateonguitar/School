public class P1PartB1
{
  public static void main(String args[])
  {
    long X, Y;
    if(args.length >= 1){
      X = Long.parseLong(args[0]);
      if(X >= 1){
        Y = SumOfDivisors(X);
        System.out.println("\n X = " + X + "     Y = " + Y);
      }
    }
    return;
  }

  public static long SumOfDivisors(long x)
  {
    long y = 0;
    for(long i=1; i<=x; i++)
    {
      if(x % i == 0)
      {
         y += i;
      }
    }
    
    return y;
  }

} // end class