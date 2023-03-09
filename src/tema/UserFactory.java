package tema;

public class UserFactory 
{
	
	   //we use getUser method to get object of type user 
	   public User getUser(String user, String firstName, String lastName)
	   {
	      if(user == null)
	      {
	         return null;
	      }	
	      
	      if(user.equalsIgnoreCase("STUDENT"))
	      {
	         return new Student(firstName,lastName);
	         
	      } else if(user.equalsIgnoreCase("PARENT"))
	      {
	         return new Parent(firstName,lastName);
	         
	      } else if(user.equalsIgnoreCase("ASSISTANT"))
	      {
	         return new Assistant(firstName,lastName);
	         
	      } else if(user.equalsIgnoreCase("TEACHER"))
	      {
		        return new Teacher(firstName,lastName);
		  }
	      
	      return null;
	   }
}
