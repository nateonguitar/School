import java.util.HashMap;

public class MapTest {

	public static void main(String[] args) {
		HashMap staff = new HashMap();
		
		Employee employee1 = new Employee("Harry", 30);
		Employee employee2 = new Employee("Dude", 55);
		
		staff.put(123, employee1);
		staff.put(456, employee2);
		
		staff.forEach((k, v) -> System.out.println("Key: " + k + " - Value: " + v));
	}
}
