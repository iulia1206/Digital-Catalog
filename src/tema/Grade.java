package tema;



public class Grade implements Cloneable, Comparable<Grade>
{
	private Double partialScore, examScore;
	private Student student;
	private String course;
	
	public void setCourse(String c)
	{
		this.course = c;
	}
	
	public void setStudent(Student s)
	{
		this.student = s;
	}
	
	public void setPartialScore(Double ps)
	{
		this.partialScore = ps;
	}
	
	public void setExamScore(Double es)
	{
		this.examScore = es;
	}
	
	public String getCourse()
	{
		return this.course;
	}
	
	public Student getStudent()
	{
		return this.student;
	}
	
	public Double getPartialScore()
	{
		return this.partialScore;
	}
	
	public Double getExamScore()
	{
		return this.examScore;
	}

	public Double getTotal()
	{
		return this.examScore + this.partialScore;
	}

	public int compareTo(Grade o) 
	{
		if (o == null ) 
			throw new NullPointerException();
		
		if (!( o instanceof Grade )) 
			throw new ClassCastException("Nu pot compara !");
		
		Grade g = (Grade) o;
		
		if(this.getTotal() > g.getTotal())
			return 1;
		else if(this.getTotal() == g.getTotal())
			return 0;
		else
			return -1;
	}
	
	protected Object clone()
	{
	        Grade grade = new Grade();
	        grade.setCourse(course);
	        grade.setExamScore(examScore);
	        grade.setPartialScore(partialScore);
	        grade.setStudent(student);
	        return grade;
    }

}
