package tema;

import java.util.*;

public class BestExamScore implements Strategy
{

	public Student getBestScore(Course c) 
	{
		ArrayList<Student> allStudents = new ArrayList<Student>();
		allStudents = c.getAllStudents();
		
		Double maxGrade = 0.0;
		Student auxStudent = null;
		
		for(Student student :allStudents)
			if(c.getGrade(student).getExamScore() > maxGrade)
			{
				maxGrade = c.getGrade(student).getExamScore();
				auxStudent = student;
			}
			
		return auxStudent;
	}

}
