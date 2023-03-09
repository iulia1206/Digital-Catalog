package tema;

public class Notification
{
	Grade newGrade = new Grade();
	Student student;
	
	public Notification(Grade newGrade, Student student)
	{
		this.newGrade = newGrade;
		this.student = student;
	}
	
	public String toString()
	{
		return "New updates about" + this.student + "'s grade: " + "\n" + "PartialGrade: " +
				newGrade.getPartialScore() + "\n" + "ExamScore: " + newGrade.getExamScore() + "\n" +
				"Total: " + newGrade.getTotal();
	}

}
