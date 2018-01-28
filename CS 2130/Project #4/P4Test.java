// Project #4 test program
public class P4Test
{
	public static void main(String[] args)
	{
		// Boolean matrix definitions
		int A[][] = new int[][]
			{{1, 1, 0, 0, 1},
			 {1, 0, 1, 0, 0},
			 {0, 0, 0, 0, 0},
			 {1, 0, 0, 0, 0},
			 {0, 0, 1, 0, 1}};

		int B[][] = new int[][]
			{{0, 1, 0, 0, 1},
			 {0, 1, 1, 0, 0},
			 {1, 0, 1, 0, 0},
			 {1, 0, 0, 0, 0},
			 {0, 1, 0, 0, 1}};

		int C[][] = new int[][]
			{{0, 1, 0, 0, 0},
			 {0, 0, 1, 0, 0},
			 {0, 0, 0, 1, 0},
			 {1, 0, 0, 0, 1},
			 {0, 1, 0, 0, 0}};

		int D[][] = new int[][]
			{{1, 1, 0, 0, 0, 0},
			 {1, 1, 1, 0, 0, 0},
			 {0, 1, 1, 1, 0, 0},
			 {0, 0, 1, 1, 0, 0},
			 {0, 0, 0, 0, 0, 1},
			 {0, 0, 0, 0, 1, 1}};

		int E[][] = new int[][]
			{{0, 1, 1, 0, 0, 1},
			 {0, 1, 1, 0, 0, 1},
			 {0, 0, 1, 0, 0, 1},
			 {0, 0, 0, 0, 1, 1},
			 {0, 0, 0, 1, 1, 1},
			 {0, 0, 0, 0, 0, 0}};

		int F[][] = new int[][]
			{{0, 0, 0, 0, 1, 0, 1, 0, 0},
			 {1, 0, 0, 1, 0, 0, 0, 0, 0},
			 {0, 0, 0, 0, 0, 0, 0, 0, 0},
			 {0, 0, 1, 0, 0, 0, 0, 1, 1},
			 {0, 0, 0, 0, 0, 0, 0, 0, 0},
			 {0, 1, 0, 0, 0, 0, 0, 0, 0},
			 {0, 0, 0, 0, 0, 0, 0, 0, 0},
			 {0, 0, 0, 0, 0, 0, 0, 0, 0},
			 {0, 0, 0, 0, 0, 0, 0, 0, 0}};

		int G[][] = new int[][]
			{{0, 0, 0, 1, 0, 0, 0, 0, 0},
			 {0, 0, 0, 0, 0, 0, 0, 0, 0},
			 {1, 0, 0, 0, 0, 0, 0, 0, 0},
			 {0, 0, 1, 0, 0, 1, 0, 0, 0},
			 {0, 1, 0, 0, 0, 0, 1, 0, 1},
			 {0, 0, 0, 0, 0, 0, 0, 1, 0},
			 {0, 0, 0, 0, 0, 0, 0, 0, 0},
			 {0, 0, 0, 0, 0, 0, 0, 0, 0},
			 {0, 0, 0, 0, 0, 0, 0, 0, 0}};

		BMat BMA = new BMat(A);
		BMat BMB = new BMat(B);
      BMat BMC = new BMat(C);
      BMat BMD = new BMat(D);
      BMat BME = new BMat(E);
      BMat BMF = new BMat(F);
      BMat BMG = new BMat(G);
      
      //////////////////////////////
      // part a                   //
      //////////////////////////////
      BMat firstPriority = new BMat(5);
      BMat secondPriority = new BMat(5);
      BMat thirdPriority = new BMat(5);
      BMat fourthPriority = new BMat(5);
      BMat fifthPriority = new BMat(5);
      
      System.out.println("a: W = (C' ^ (A v B)) ^ B'");
      
      // (A v B)
      firstPriority = BMA.join(BMB);
      
      // C'
		secondPriority = BMC.complement();
      
      // (C' ^ (A v B))
      thirdPriority = secondPriority.meet(firstPriority);
      
      // B'
      fourthPriority = BMB.complement();
      
      // (C' ^ (A v B)) ^ B'
      fifthPriority = thirdPriority.meet(fourthPriority);
      fifthPriority.show();
      
      
      
      
      //////////////////////////////
      // part b                   //
      //////////////////////////////  
        
      firstPriority = new BMat(5); 
      secondPriority = new BMat(5);
      thirdPriority = new BMat(5);
      fourthPriority = new BMat(5);
      fifthPriority = new BMat(5);
      
      System.out.println("\nb: W = (B^T O B) ^ (C v C^T)");
      
      // B^T
      firstPriority = BMB.transpose();
      
      // (B^T O B)
      secondPriority = firstPriority.product(BMB);
      
      // C^T
      thirdPriority = BMC.transpose();
      
      // (C v C^T)
      fourthPriority = BMC.join(thirdPriority);
      
      // (B^T O B) ^ (C v C^T)
      fifthPriority = secondPriority.meet(fourthPriority);
      
      fifthPriority.show();
      
      
      
      //////////////////////////////
      // part C                   //
      //////////////////////////////  
        
      firstPriority = new BMat(5); 
      secondPriority = new BMat(5);
      thirdPriority = new BMat(5);
      fourthPriority = new BMat(5);
      fifthPriority = new BMat(5);
      
      
      System.out.println("\nc: W = OC^18");
      firstPriority = BMC.product(BMC); // 2
      
      for(int i=0; i<16; i++)
      {
         firstPriority = firstPriority.product(firstPriority);
      }
      firstPriority.show();
      
      //////////////////////////////
      // part D                   //
      //////////////////////////////  
        
      firstPriority = new BMat(6); 
      secondPriority = new BMat(6);
      thirdPriority = new BMat(6);
      fourthPriority = new BMat(6);
      fifthPriority = new BMat(6);

      System.out.println("\nd: W = (D v E)T ^ (DT v ET)");
      
      // (D v E)
      firstPriority = BMD.join(BME);
      
      // (D v E)T
      firstPriority = firstPriority.transpose();
      
      // DT
      secondPriority = BMD.transpose();
      
      // ET
      thirdPriority = BME.transpose();
      
      // (DT v ET)
      fourthPriority = secondPriority.join(thirdPriority);
      
      // (D v E)T ^ (DT v ET)
      fifthPriority = firstPriority.meet(fourthPriority);
      
      fifthPriority.show();
      
      
      //////////////////////////////
      // part E                   //
      //////////////////////////////  
        
      firstPriority = new BMat(6); 
      secondPriority = new BMat(6);
      thirdPriority = new BMat(6);
      fourthPriority = new BMat(6);
      fifthPriority = new BMat(6);
      BMat sixthPriority = new BMat(6);
      BMat seventhPriority = new BMat(6);
      
      System.out.println("\ne: W = OD1 v OD2 v OD3 v OD4");
      
      // OD1 = D O D
      firstPriority = BMD.product(BMD);
      
      // OD2 = D O D O D
      secondPriority = BMD.product(BMD);
      secondPriority = secondPriority.product(BMD);
      
      // OD3 = D O D O D O D
      thirdPriority = BMD.product(BMD);
      thirdPriority = thirdPriority.product(BMD);
      thirdPriority = thirdPriority.product(BMD);
      
      // OD4 = D O D O D O D O D
      fourthPriority = BMD.product(BMD);
      fourthPriority = fourthPriority.product(BMD);
      fourthPriority = fourthPriority.product(BMD);
      fourthPriority = fourthPriority.product(BMD);
      
      // OD1 v OD2
      fifthPriority = firstPriority.join(secondPriority);
      
      // OD1 v OD2 v OD3
      sixthPriority = fifthPriority.join(thirdPriority);
      
      // OD1 v OD2 v OD3 v OD4
      seventhPriority = sixthPriority.join(fourthPriority);
      
      seventhPriority.show();
      
      
      
      
      //////////////////////////////
      // part F                   //
      //////////////////////////////  
      
      System.out.println("\nf: X = maximum out-degree of all nodes in D");
      System.out.println("  0: " + BMD.outdegree(0));
      System.out.println("  1: " + BMD.outdegree(1));
      System.out.println("  2: " + BMD.outdegree(2));
      System.out.println("  3: " + BMD.outdegree(3));
      System.out.println("  4: " + BMD.outdegree(4));
      System.out.println("  5: " + BMD.outdegree(5));

      
      //////////////////////////////
      // part G                   //
      //////////////////////////////  
        
      System.out.println("\ng: W = symmetric closure of D. Is D symmetric?");
      firstPriority = new BMat(6);
      
      firstPriority = BMD.sclosure();
      
      firstPriority.show();
      System.out.println("  Yes D is symmetric");
      System.out.println("  The diagonal does not matter and matrix is mirrored about that axis"); 
      
      
      
      //////////////////////////////
      // part H                   //
      //////////////////////////////  
        
      System.out.println("\nh: W = transitive closure of E. Is E transitive?");
      firstPriority = new BMat(6);
      firstPriority = BME.tclosure();
      firstPriority.show();
      System.out.println("  I think it is transitive, I drew out the digraph and it looks like every relation fits,");
      System.out.println("  but I'm not sure if that means the entire matrix is transitive,");
      System.out.println("  It was never explained in the videos and I can't seem to find an answer");
      
      //////////////////////////////
      // part I                   //
      //////////////////////////////
      
      System.out.println("\ni: Show that matrix F represents a tree");
      
      if(BMF.rootnode() == -1)
      {
         System.out.println("  Matrix F does not have a root node");
         BMF.show();
      }
      else
      {
         System.out.println("  Matrix F has only 1 root node candidate\n   which is node " + firstPriority.rootnode());         
         System.out.println("                 v");
         BMF.show();
         System.out.println("                 ^");
         
         for(int i=0; i<9; i++)
         {
            System.out.println("Node " + i + " is out-degree " + BMF.outdegree(i) + " and in-degree " + BMF.indegree(i));
         }
            
      }
      
      /////////////////////////////
      // part J                  //
      /////////////////////////////
      
      System.out.println("\nj: Show that matrix G does not represent a tree");
      
      if(BMG.rootnode() == -1)
      {
         System.out.println("  Matrix G does not have a root node");
         BMG.show();
      }
      else
      {
         System.out.println("  Matrix G has only 1 root node candidate\n   which is node " + BMG.rootnode());         
         System.out.println("                 v");
         BMF.show();
         System.out.println("                 ^");
         
         for(int i=0; i<9; i++)
         {
            System.out.println("Node " + i + " is out-degree " + BMG.outdegree(i) + " and in-degree " + BMG.indegree(i));
         }
      } 
	} // end main
} // end class