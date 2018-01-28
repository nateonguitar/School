/********************************************
 * 
 * for loop and an identical foreach loop
 * 
 ********************************************/

public class ForeachLoop 
{
	public static void main(String[] args)
	{
		int[] numbers = {2, 4, 6, 8};
		
			// for loop
		for(int i=0; i<numbers.length; i++)
		{
			System.out.println(numbers[i]);
		}
		
		System.out.println();
		
			// foreach loop
		for (int element : numbers)
		{
			System.out.println(element);
		}
	}
}
