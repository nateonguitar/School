// Test Finite State Machine Class
public class TestFSM
{
  public static void main(String[] args)
  {
  
    /*
    // L1
    String A   = "ab";
    int[][] ST = {{1,4},
                  {0,2},
                  {3,5},
                  {2,5},
                  {2,5},
                  {5,5}};
    int[] AS   = {0,0,1,0,0,0};
    */
    
    /*
    // L2
    String A   = "01";
    int[][] ST = {{1,6},
                  {2,5},
                  {3,4},
                  {8,8},
                  {8,8},
                  {3,8},
                  {7,8},
                  {3,8},
                  {8,8}};
    int[] AS   = {0,1,1,1,1,0,0,0,0};
    */
    
    /*
    // L3
    String A   = "xyz";
    int[][] ST = {{1,0,4},
                  {1,2,0},
                  {3,0,4},
                  {3,3,3},
                  {0,5,4},
                  {1,0,6},
                  {6,6,6}};
    int[] AS   = {0,0,0,1,0,0,1};
    */
    
    // L4
    String A   = "pqr";
    int[][] ST = {{4,0,4},
                  {4,2,1},
                  {4,0,3},
                  {3,3,3},
                  {5,0,1},
                  {5,0,1}};
    int[] AS   = {0,0,0,1,0,1};


    String inString;
    boolean accept1 = false;
    FSM FSM1 = new FSM(A, ST, AS);
    if(args.length >= 1)
    {
      // Input string is command line parameter
      inString = args[0];
      accept1 = FSM1.validString(inString);
      System.out.println("\n String:  " + inString 
        + "    Accept?  " + accept1);
    }
  } // end main

} // end class
