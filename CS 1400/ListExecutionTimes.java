/****************************************************
* Dean & Dean
*
* This measures average time to perform indexed operations.
*****************************************************/

import java.util.*; // ArrayList, LinkedList, ArrayDeque, Random

public class ListExecutionTimes
{
   public static void main(String[] args)
   {
      String operationType = "average get and set time";
         // String operationType = "average remove and add time";
      int length = 10000;
      int[] indicesA = getIndices(length);    // random sequence
      int[] indicesB = getIndices(length);    // random sequence
      ArrayList<Double> list = new ArrayList<>(); // alternate implimentation
      // LinkedList<Double> list = new LinkedList<>();
      Double element;
      long time0, time1;
      
      for (int i=0; i<length; i++)
      {
         list.add(new Double(i));
      }
      
      time0 = System.nanoTime();
      
      for (int i=1; i<length; i++)
      {
         // element = list.get(indicesA[i]);
         // list.set(indicesB[i], element);
         element = list.remove(indicesA[i]);
         list.add(indicesB[i], element);
      }
      
      time1 = System.nanoTime();
      System.out.println(list.getClass());
      System.out.printf("for length = %d, %s = %,d ns\n",
         length, operationType, (time1 - time0) / length);
   } // end main
   
   //***********************************************************
   
      // this returns an array of all integers between zero and length
      // in a random sequence with no duplications
   private static int[] getIndices(int length)
   {
      Random random = new Random();
      ArrayList<Integer> integers = new ArrayList<>();
      int[] indices = new int[length];
      
      for (int i=0; i<length; i++)
      {
         integers.add(random.nextInt(i+1), new Integer(i));
      }
      
      for (int i=0; i<length; i++)
      {
         indices[i] = integers.get(i);
      } 
      return indices;
   } // end getIndices()   
} // end class ListexecutionTimes