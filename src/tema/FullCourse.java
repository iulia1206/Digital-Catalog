package tema;

import java.util.ArrayList;

public class FullCourse extends Course
{
	
	public FullCourse(FullCourseBuilder builder)
	{
		super(builder);
	}

	public ArrayList<Student> getGraduatedStudents()
	{
		ArrayList<Student> promotedStudents = new ArrayList<Student>();
		ArrayList<Student> studentsList = this.getAllStudents();

		for (Student student : studentsList)
			if (getGrade(student).getExamScore() >= 2 && getGrade(student).getPartialScore() >= 3)
				promotedStudents.add(student);
		
		return promotedStudents;
	}
	
	public static class FullCourseBuilder extends CourseBuilder
	{

		public FullCourseBuilder(String name) 
		{
			super(name);
		}

		public Course build() 
		{
			return new FullCourse(this);
		}
		
	}

}
