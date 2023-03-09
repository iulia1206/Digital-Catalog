package tema;

import java.util.ArrayList;
import java.util.Comparator;

@SuppressWarnings("serial")
public class Group extends ArrayList<Student>
{
	Assistant assistant;
	String ID;
	Comparator<Student> comparator;
	
	public Group(String ID, Assistant assistant, Comparator<Student> comp) 
	{
		super(new ArrayList<Student>());
		this.assistant = assistant;
		this.ID = ID;
		this.comparator = comp;
		
	}

	public Group(String ID, Assistant assistant)
	{ 
		super(new ArrayList<Student>());
		this.assistant = assistant;
		this.ID = ID;
	}
	
}