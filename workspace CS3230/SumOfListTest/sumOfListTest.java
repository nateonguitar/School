package brooksNathan;

import java.util.Arrays;
import java.util.List;

public class sumOfListTest {

	public static void main(String[] args) {
		List<Integer> li = Arrays.asList(1, 2, 3);
		System.out.println("Sum = " + sumOfList(li));
		
		List<Double> ld = Arrays.asList(1.2, 2.3, 3.5);
		System.out.println("Sum = " + sumOfList(ld));
	}

	public static double sumOfList(List<? extends Number> list){
		double s = 0.0;
		for(Number n : list){
			s += n.doubleValue();
		}
		return s;
	}
}
