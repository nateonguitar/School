
public class Employee {
	String name;
	int age;
	
	public Employee(String name, int age){
		this.name = name;
		this.age = age;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getAge(){
		return this.age;
	}
	
	@Override
	public String toString(){
		return this.name + " " + this.age;
	}
}
