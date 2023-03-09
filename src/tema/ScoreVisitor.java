package tema;

import java.util.*;

public class ScoreVisitor implements Visitor
{
	private static ScoreVisitor object;
	HashMap<Teacher, ArrayList<Tuple<Student, String, Double>>> examScores = new HashMap<Teacher, ArrayList<Tuple<Student, String, Double>>>();
	HashMap<Assistant, ArrayList<Tuple<Student, String, Double>>> partialScores = new HashMap<Assistant, ArrayList<Tuple<Student, String, Double>>>();
	Catalog coursesCatalog = Catalog.getInstance();
	
	private class Tuple<studentArg ,nameArg, gradeArg>
	{
		studentArg student; 
		nameArg courseName;
		gradeArg garde;
	}
	
	public ScoreVisitor(HashMap<Teacher, ArrayList<Tuple<Student, String, Double>>> examScores, HashMap<Assistant, ArrayList<Tuple<Student, String, Double>>> partialScores)
	{
		this.examScores = examScores;
		this.partialScores = partialScores;
	}

	private ScoreVisitor()
	{

	}

	public static ScoreVisitor getInstance()
	{
		//creates the object if it's not already created
		if(object == null)
		{
			object = new ScoreVisitor();
		}

		//returns the Singleton object
		return object;
	}

	//returns list of Tuples
	public ArrayList<Tuple<Student, String, Double>> getTuple(String courseName, ArrayList<Student> students, ArrayList<Double> scores)
	{
		ArrayList<Tuple<Student, String, Double>> newList = new ArrayList<Tuple<Student, String, Double>>();

		for(Course c: coursesCatalog.catalog)
			if(c.getName().equals(courseName))
			{
				for(int i = 0; i < students.size(); i++)
				{
					Tuple<Student, String, Double> tuple = new Tuple<Student, String, Double>();

					for (Student s : c.getAllStudents())
						if(s.toString().equals(students.get(i).toString()))
						{
							tuple.courseName = courseName;
							tuple.garde = scores.get(i);
							tuple.student = s;
						}
					newList.add(i, tuple);
				}

			}
		return newList;
	}

	//adds an element to examScores
	public void buildExamScores(String courseName, ArrayList<Student> students, ArrayList<Double> scores)
	{
		for(Course c: coursesCatalog.catalog)
			if(c.getName().equals(courseName))
			{
				examScores.put(c.getTeacher(), getTuple(courseName, students, scores));
			}
	}

	//adds an element to partialScores
	public void buildPartialScores(String courseName, ArrayList<Student> students, ArrayList<Double> scores, Assistant assistant)
	{
		for(Course c: coursesCatalog.catalog)
			if(c.getName().equals(courseName))
			{

				partialScores.put(assistant, getTuple(courseName, students, scores));

			}
	}

	//returns the list of students from the Tuples from hashmap partialScores
	public ArrayList<Student> getStudents(HashMap<Assistant, ArrayList<Tuple<Student, String, Double>>> partialScores, Assistant assistant)
	{
		// assistant's notes about students
		ArrayList<Tuple<Student, String, Double>> notes = partialScores.get(assistant);

		ArrayList<Student> students = new ArrayList<Student>();

		for(Tuple<Student, String, Double> currentNote: notes)
		{
			students.add(currentNote.student);
		}

		return students;
	}

	//returns the list of grades from the Tuples from hashmap partialScores
	public ArrayList<Double> getGrades(HashMap<Assistant, ArrayList<Tuple<Student, String, Double>>> partialScores, Assistant assistant)
	{
		// assistant's notes about students
		ArrayList<Tuple<Student, String, Double>> notes = partialScores.get(assistant);

		ArrayList<Double> grades = new ArrayList<Double>();

		for(Tuple<Student, String, Double> currentNote: notes)
		{
			grades.add(currentNote.garde);
		}

		return grades;
	}

	//returns the list of students from the Tuples from hashmap examScores
	public ArrayList<Student> getStudents(HashMap<Teacher, ArrayList<Tuple<Student, String, Double>>> examScores, Teacher teacher)
	{
		// assistant's notes about students
		ArrayList<Tuple<Student, String, Double>> notes = examScores.get(teacher);

		ArrayList<Student> students = new ArrayList<Student>();

		for(Tuple<Student, String, Double> currentNote: notes)
		{
			students.add(currentNote.student);
		}

		return students;
	}

	//returns the list of grades from the Tuples from hashmap examScores
	public ArrayList<Double> getGrades(HashMap<Teacher, ArrayList<Tuple<Student, String, Double>>> examScores, Teacher teacher)
	{
		// assistant's notes about students
		ArrayList<Tuple<Student, String, Double>> notes = examScores.get(teacher);

		ArrayList<Double> grades = new ArrayList<Double>();

		for(Tuple<Student, String, Double> currentNote: notes)
		{
			grades.add(currentNote.garde);
		}

		return grades;
	}

	public void visit(Assistant assistant)
	{
		// assistant's notes about students
		ArrayList<Student> students = getStudents(partialScores, assistant);
		ArrayList<Double> grades = getGrades(partialScores, assistant);
		ArrayList<Grade> allGrades= new ArrayList<Grade>();

		for(Course c: coursesCatalog.catalog)
		{
			for(Assistant a: c.getAssistants())
			{
				if (a.toString().equals(assistant.toString()))
				{
					for (int i = 0; i < students.size(); i++)
					{
						ArrayList<Grade> oldGrades = c.getGrades();
						int ok = 0;

						for(Grade grade: oldGrades)
							if(grade.getStudent().toString().equals(students.get(i).toString()))
							{
								grade.setPartialScore(grades.get(i));
								allGrades.add(grade);
								ok = 1;
								coursesCatalog.notifyObservers(grade);
							}

						if(ok == 0)
						{
							//create a grade for every student-grade pair
							Grade newGrade = new Grade();
							newGrade.setCourse(c.getName());
							newGrade.setPartialScore(grades.get(i));
							newGrade.setStudent(students.get(i));
							allGrades.add(newGrade);
							coursesCatalog.notifyObservers(newGrade);
						}
					}
				}
			}
			for(Grade g: allGrades)
				c.addGrade(g);
		}
	}

	public void visit(Teacher teacher)
	{
		ArrayList<Student> students = getStudents(examScores, teacher);
		ArrayList<Double> grades = getGrades(examScores, teacher);
		ArrayList<Grade> allGrades= new ArrayList<>();

		for(Course c: coursesCatalog.catalog)
		{
			if (c.getTeacher().toString().equals(teacher.toString()))
			{
				for (int i = 0; i < students.size(); i++)
				{
					ArrayList<Grade> oldGrades = c.getGrades();
					int ok = 0;

					for(Grade grade: oldGrades)
						if(grade.getStudent().toString().equals(students.get(i).toString()))
						{
							grade.setExamScore(grades.get(i));
							allGrades.add(grade);
							ok = 1;
							coursesCatalog.notifyObservers(grade);
						}

					if(ok == 0)
					{
						//create a grade for every student-grade pair
						Grade newGrade = new Grade();
						newGrade.setCourse(c.getName());
						newGrade.setExamScore(grades.get(i));
						newGrade.setStudent(students.get(i));
						allGrades.add(newGrade);
						coursesCatalog.notifyObservers(newGrade);
					}
				}
			}
			c.setGrades(allGrades);
		}
	}
}
