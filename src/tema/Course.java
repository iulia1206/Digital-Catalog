package tema;

import java.util.*;
 
@SuppressWarnings("serial")
class StudentAlreadyExists extends Exception 
{
    public StudentAlreadyExists() 
    {
        super("The student is already in the group");
    }
}

public abstract class Course 
{
	private String name;
	private Teacher teacher;
	private Set<Assistant> assistants = new HashSet<Assistant>();
	private ArrayList<Grade> grades = new ArrayList<Grade>();
	private Hashtable<String,Group> groups = new Hashtable<String,Group>();
	private int creditPoints;
	public Strategy strategy;
	private Snapshot snapshot;
	
	public Course(CourseBuilder builder)
	{
		this.name = builder.name;
		this.teacher = builder.teacher;
		this.assistants = builder.assistants;
		this.grades = builder.grades;
		this.groups = builder.groups;
		this.creditPoints = builder.creditPoints;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setTeacher(Teacher teacher)
	{
		this.teacher = teacher;
	}
	
	public void setAssistants(Set<Assistant> assistants)
	{
		this.assistants = assistants;
	}
	
	public void setGrades(ArrayList<Grade> grades)
	{
		this.grades = grades;
	}
	
	public void setGroups(Hashtable<String,Group> groups)
	{
		this.groups = groups;
	}
	
	public void setCreditPoints(int creditPoints)
	{
		this.creditPoints = creditPoints;
	}

	public void setStrategy(Strategy strategy)
	{
		this.strategy = strategy;
	}

	public String getName()
	{
		return this.name;
	}
	
	public Teacher getTeacher()
	{
		return this.teacher;
	}
	
	public Set<Assistant> getAssistants()
	{
		return this.assistants;
	}
	
	public ArrayList<Grade> getGrades()
	{
		return this.grades;
	}
	
	public Hashtable<String,Group> getGroups()
	{
		return this.groups;
	}
	
	public int getCreditPoints()
	{
		return this.creditPoints;
	}
	
	// Sets the assistant in Group with the indicated ID or, if it doesn't exist, also adds it to 
	// assistant's list
	public void addAssistant(String ID, Assistant assistant)
	{
		for(Assistant a: assistants)
			if(a.toString().equals(assistant.toString()))
			{
				groups.get(ID).assistant = assistant;

			}
			else
			{
				assistants.add(assistant);
				groups.get(ID).assistant = assistant;
			}
			
	}
	
	// Sets the student in Group with the indicated ID
	public void addStudent(String ID, Student student) throws StudentAlreadyExists
	{
		for(Student s: groups.get(ID))
			if(s.toString().equals(student.toString()))
				throw new StudentAlreadyExists();
			else
				groups.get(ID).add(student);
		
	}
	
	// Adds group to groups
	public void addGroup(Group group)
	{
		groups.put(group.ID, group);
	}
	
	// Instantiates the group then adds it to groups 
	public void addGroup(String ID, Assistant assistant)
	{
		Group group = new Group(ID, assistant);
		groups.put(group.ID, group);
	}
	
	// Instantiates the group then adds it to groups 
	public void addGroup(String ID, Assistant assist, Comparator<Student> comp)
	{
		Group group = new Group(ID, assist, comp);
		groups.put(group.ID, group);
	}
	
	// Returns the grade of a student
	public Grade getGrade(Student student)
	{
	
		for(int i = 0; i < grades.size(); i++)
			if(grades.get(i).getStudent().toString().equals(student.toString()))
			{
				return grades.get(i);
			}
		
		return null;
	}
	
	// Adds a grade
	public void addGrade(Grade grade)
	{
		grades.add(grade);
	}
	
	// Returns a list with all students
	public ArrayList<Student> getAllStudents()
	{
		ArrayList<Student> studentsList = new ArrayList<Student>();
		Set<String> IDs = groups.keySet();
		
		for(String key: IDs)
		{
			for(int i = 0; i < groups.get(key).size(); i++)
            	studentsList.add(groups.get(key).get(i));
        }
		
		return studentsList;
	}
	
	// Returns a dictionary with students' situation
	public HashMap<Student, Grade> gettAllStudentGrades()
	{
		HashMap<Student, Grade> studentsGrade = new HashMap<Student, Grade>();
		ArrayList<Student> studentsList = this.getAllStudents();

		for (Student student : studentsList) {
			studentsGrade.put(student, getGrade(student));
		}
		
		return studentsGrade;
	}
	
	public String toString()
	{
		return this.name;
	}
	
	public Student getBestStudent()
	{
		return strategy.getBestScore(this);
	}
	
	public void makeBackup() 
	{ 
		ArrayList<Grade> gradeList = new ArrayList<Grade>();
		
		for(Grade g: grades)
		{
			Grade newGrade = new Grade();
			newGrade = (Grade)g.clone();
			gradeList.add(newGrade);
		}
		snapshot = new Snapshot(gradeList);  
		
	}
	
	public void undo() 
	{  
		grades = snapshot.getState();
	}
	
	private class Snapshot 
	{
		private ArrayList<Grade> gradesState = new ArrayList<Grade>();  
		  
	    public Snapshot(ArrayList<Grade> gradesState) 
	    {  
	        this.gradesState=gradesState;  
	    }  
	    public ArrayList<Grade> getState()
	    {  
	        return gradesState;  
	    }  
	    
	}
	
	// Method for discovering the promoted students
	public abstract ArrayList<Student> getGraduatedStudents();
	
	
	public static abstract class CourseBuilder
	{
		private String name;
		private Teacher teacher;
		private Set<Assistant> assistants = new HashSet<Assistant>();
		private ArrayList<Grade> grades = new ArrayList<Grade>();
		private Hashtable<String,Group> groups = new Hashtable<String,Group>();
		private int creditPoints;
		
		public CourseBuilder (String name)
		{
			this.name = name;
		}
		
		public CourseBuilder teacher(Teacher teacher)
		{
			this.teacher = teacher;
			return this;
		}

		public CourseBuilder assistants(Set<Assistant> assistants)
		{
			this.assistants = assistants;
			return this;
		}
		
		public CourseBuilder grades(ArrayList<Grade> grades)
		{
			this.grades = grades;
			return this;
		}
		
		public CourseBuilder groups(Hashtable<String,Group> groups)
		{
			this.groups = groups;
			return this;
		}
		
		public CourseBuilder creditPoints(int creditPoints)
		{
			this.creditPoints = creditPoints;
			return this;
		}
		
		public abstract Course build();
		
	}
}
