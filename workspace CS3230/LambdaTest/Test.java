package LambdaTest;

public class Test {
	public static void main(String[] args) {
		ILambda iLambda = new ILambda(){

			@Override
			public int add(int x, int y) {
				return x + y;
			}
		};
		
		ILambda iLambda2 = (x, y) -> x + y;
		
		
		System.out.println(iLambda.add(12,  1));
		System.out.println(iLambda2.add(44, 2));
	}
}
