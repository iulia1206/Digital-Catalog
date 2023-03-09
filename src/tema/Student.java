package tema;

public class Student extends User
{
	Parent mother;
	Parent father;
	
	public Student(String firstName, String lastName) 
	{
		super(firstName, lastName);
	}
	
	public void setMother(Parent mother) 
	{
		this.mother = mother;
	}
	
	public void setFather(Parent father) 
	{
		this.father = father;
	}

	public int compareTo(Student s2) 
	{
		if(this.toString().length() > s2.toString().length())
			return 1;
		else
			if(this.toString().length() < s2.toString().length())
				return -1;
			
		return 0;
	}


}
