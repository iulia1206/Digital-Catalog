package tema;

import java.util.ArrayList;

public class PartialCourse extends Course
{
	
	public PartialCourse(PartialCourseBuilder builder)
	{
		super(builder);
	}

	public ArrayList<Student> getGraduatedStudents()
	{
		ArrayList<Student> promotedStudents = new ArrayList<Student>();
		ArrayList<Student> studentsList = this.getAllStudents();
		
		for (int i = 0; i < studentsList.size(); i++)
			if (getGrade(studentsList.get(i)).getTotal() >= 5)
				promotedStudents.add(studentsList.get(i));
		
		return promotedStudents;
	}
	
	public static class PartialCourseBuilder extends CourseBuilder
	{

		public PartialCourseBuilder(String name) 
		{
			super(name);
		}

		public Course build() 
		{
			return new PartialCourse(this);
		}
		
	}

}
