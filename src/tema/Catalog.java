package tema;

import java.util.*;

@SuppressWarnings("serial")
class CourseDoesNotExists extends Exception 
{
    public CourseDoesNotExists() 
    {
        super("The course does not exist");
    }
}
 
@SuppressWarnings("serial")
class CourseAlreadyExists extends Exception
{
    public CourseAlreadyExists() 
    {
        super("The course is already in the catalog");
    }
}


public class Catalog implements Subject
{
	private static Catalog object;
	public List<Course> catalog = new ArrayList<Course>();
	public List<Observer> parents = new ArrayList<>();
	    
    private Catalog() 
    {

    }

    public static Catalog getInstance() 
    {
    	  //creates the object if it's not already created
    	if(object == null) 
	    {
	    	object = new Catalog();
	    }

	       //returns the Singleton object
	       return object;
   }
   
   //adds a course to catalog
   public void addCourse(Course course) throws CourseAlreadyExists
   {
	   if(catalog.contains(course))
		   throw new CourseAlreadyExists();
	   catalog.add(course);
   }
   
   //removes a course from the catalog
   public void removeCourse(Course course) throws CourseDoesNotExists
   { 
	   if(catalog.contains(course) == false)
		   throw new CourseDoesNotExists();
	   catalog.remove(course);
   }

	public void addObserver(Observer observer)
	{
		if(observer == null) 
			throw new NullPointerException("Null Observer");
		if(! (parents.contains(observer)))
			this.parents.add(observer);
		
	}
	
	public void removeObserver(Observer observer) 
	{
		if(parents.contains(observer))
			this.parents.remove(observer);
		
	}
	
	public void notifyObservers(Grade grade) {
		Notification notification = new Notification(grade, grade.getStudent());
		for (Observer parent : parents) {
			if (grade.getStudent().mother != null)
				if (grade.getStudent().mother.toString().equals(parent.toString()))
					parent.update(notification);

			if (grade.getStudent().father != null)
				if (grade.getStudent().father.toString().equals(parent.toString()))
					parent.update(notification);
		}
	}

}
