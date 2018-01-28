
public class ThreadDemo 
{
	public static void main(String args[])
	{
		Thread thread = new Thread()
		{
			public void run()
			{
				for (int i = 0; i < 10; i += 2) 
				{
                    System.out.println("hello this is thread one");
                }
			}
		};
		

		Thread threadTwo = new Thread()
		{
			public void run()
			{
				for (int i = 0; i < 10; i+=2)
				{
					System.out.println("hello this is thread two");
				}
			}
		};
		
		thread.start();
		threadTwo.start();
	}
}
