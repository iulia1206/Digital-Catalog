package tema;

public class Parent extends User implements Observer
{
	Grade studentGrade;
	Student student;

	public Parent(String firstName, String lastName) 
	{
		super(firstName, lastName);
	}

	public void update(Notification notification) 
	{
		this.studentGrade = notification.newGrade;
		this.student = notification.student;

	}

}
