package tema;

import java.util.ArrayList;

public class BestTotalScore implements Strategy
{
	public Student getBestScore(Course c) 
	{
		ArrayList<Student> allStudents = new ArrayList<Student>();
		allStudents = c.getAllStudents();
		
		Double maxGrade = 0.0;
		Student auxStudent = null;
		
		for(Student student :allStudents)
			if(c.getGrade(student).getTotal() > maxGrade)
			{
				maxGrade = c.getGrade(student).getExamScore();
				auxStudent = student;
			}
			
		return auxStudent;
	}
}
