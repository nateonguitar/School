package brooksNathan;

import java.util.Arrays;
import java.util.List;

public class PrintListTest {

	public static void printList(List<?> list){
		for(Object elem : list){
			System.out.print(elem + " ");
		}
		System.out.println();
	}
	public static void main(String[] args) {
		List<Integer> li = Arrays.asList(1, 2, 3);
		List<String> ls = Arrays.asList("one", "two", "three");
		printList(li);
		printList(ls);
		
		List<Student> la = Arrays.asList(new Student(1), new Student(2), new Student(3));
		printList(la);

	}

}
