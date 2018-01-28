
public class P1PartB5
{
  public static void main(String args[])
  {
    long[] arr = new long[51];
        
    int x;
    if(args.length >= 1){
         x = Integer.parseInt(args[0]);
      
         arr[0] = 0;
         arr[1] = 1;
         arr[2] = 3;
     
        for(int i=3; i<=50; i++)
        {
           arr[i] = 2 * arr[i-1] - 2 * arr[i-2];
        }
      
      
      System.out.println("\n Input value x = " + x + "    Output: " + arr[x]);
      
    }
    return;
  } // end main
} // end class