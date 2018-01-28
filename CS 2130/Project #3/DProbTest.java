class DprobTest{
   public static void main(String args[])
   {
      System.out.println("1A: " + DProb.Permutations(33, 4));
      System.out.println("1B: " + DProb.Combinations(33, 4) + "\n");
      
      System.out.println("2A: " + DProb.HyperGeometric(50, 8, 9, 0));
      System.out.println("2B: " + DProb.HyperGeometric(30, 8, 9, 0));
      System.out.println("2C: " + (DProb.HyperGeometric(50, 8, 9, 0) * 
                                   DProb.HyperGeometric(30, 8, 9, 0)) );
      System.out.println("2D: " + DProb.HyperGeometric(80, 8, 13, 0) + "\n");

      System.out.println("3A: " + DProb.HyperGeometric(71, 10, 5, 2));
      System.out.println("3B: " + (DProb.HyperGeometric(71, 10, 5, 1) + 
                                   DProb.HyperGeometric(71, 10, 5, 0)) + "\n");
      
      //X successes in N independent trials, where P is the probability of a success
      System.out.println("4A: " + DProb.Binomial(0.069, 30, 3));
      System.out.println("4B: " + (DProb.Binomial(0.069, 30, 2) + 
                                   DProb.Binomial(0.069, 30, 1) + 
                                   DProb.Binomial(0.069, 30, 0)) + "\n");
      
      System.out.println("5A Binomial: " + DProb.Binomial(0.026, 125, 4));
      System.out.println("5B Binomial: " + (DProb.Binomial(0.026, 125, 3) + 
                                            DProb.Binomial(0.026, 125, 2) + 
                                            DProb.Binomial(0.026, 125, 1) + 
                                            DProb.Binomial(0.026, 125, 0)) + "\n");
                                            
      System.out.println("5A Poisson: " + DProb.Poisson(0.026 * 125, 4));
      System.out.println("5B Poisson: " + (DProb.Poisson(0.026 * 125, 3) + 
                                            DProb.Poisson(0.026 * 125, 2) + 
                                            DProb.Poisson(0.026 * 125, 1) + 
                                            DProb.Poisson(0.026 * 125, 0)) + "\n");
                                            
      System.out.println("\n\n\n\n " + (DProb.Permutations(7, 1) * DProb.Permutations(10, 1) * DProb.Permutations(6, 1))        );
                                            
   }// end main
}// end class