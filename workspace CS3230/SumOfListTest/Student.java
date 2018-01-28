package brooksNathan;

public class Student {
	int studentID;
	
	public Student(int id){
		this.studentID = id;
	}
	
	@Override
	public String toString(){
		return Integer.toString(this.studentID);
	}
}
