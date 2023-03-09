package tema;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

public class StudentPage
{
	JFrame frame1;
	
	public StudentPage(Student student) throws IOException
	{
		this.frame1 = new JFrame("Student's Page");
        
		// Setting up the frame
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setSize(1680,1080);
        frame1.setMinimumSize(new Dimension(500,500));
        frame1.setLayout(new GridLayout(2,1));
        frame1.getContentPane().setBackground(new Color(230,251,255));
        
        // border
        Border lowered, raised;
        TitledBorder title;
        lowered = BorderFactory.createLoweredBevelBorder();
        raised = BorderFactory.createRaisedBevelBorder();
        title = BorderFactory.createTitledBorder("UPB - Student");
        Font f1 = new Font(Font.SERIF, Font.ITALIC, 20);
        title.setTitleFont(f1);
        final JPanel panel1 = new JPanel();
        panel1.setPreferredSize(new Dimension(1200, 700));
        panel1.setBackground(new Color(230,251,255));
        panel1.setBorder(title);
        frame1.add(panel1);

		// welcome text
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(3,1));
        String stringText1 = "Welcome, " + student + "! We are so glad to have you back! Here is your list of courses. Have a good day!";
        JTextArea text1 = new JTextArea();
        Font f2 = new Font(Font.SERIF, Font.ITALIC, 20);
        text1.setFont(f2);
		text1.setBackground(new Color(230,251,255));
        text1.setEditable(false);
        text1.setText(stringText1);
        panel2.add(text1);
        
        // null panel to make the page look good
        JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayout());
        panel3.setBackground(new Color(230,251,255));
        panel2.add(panel3);
        
        // button that leads to the second frame
        JButton button1 = new JButton("Choose Course");
        button1.setPreferredSize(new Dimension(20,20));
        button1.setForeground(Color.black);
        button1.setBackground(new Color(179,242,255));
        Font f3 = new Font(Font.SERIF, Font.BOLD, 15);
        button1.setFont(f3);
        panel2.add(button1);
        button1.addActionListener(new Action1(student));
        
        // polytechnic university image
        BufferedImage image = ImageIO.read(new File("D:\\POO\\Tema\\src\\tema\\image.png"));
        JLabel icon = new JLabel(new ImageIcon(image));
        frame1.add(icon);
        
        panel2.setVisible(true);
        panel1.add(panel2);
        
        frame1.setVisible(true);
	}
}

	// action for button "Choose course"
	class Action1 implements ActionListener
	{
		Catalog coursesCatalog = Catalog.getInstance();
		Student student;
		JList<Course> list;
		
		public Action1(Student student)
		{
			this.student = student;
		}

		public void actionPerformed(ActionEvent e) 
		{
			// find out what courses the student attends
			DefaultListModel<Course> courses = new DefaultListModel<Course>();
			for(Course course: coursesCatalog.catalog)
				for(Student s: course.getAllStudents())
					if(s.toString().equals(student.toString()))
						courses.addElement(course);

			// new frame with courses
			JFrame frame2 = new JFrame("Courses");
			frame2.setLayout(new GridLayout(3,1));
		    frame2.setSize(500,300);
		    frame2.setBackground(new Color(179,242,255));
		    frame2.setVisible(true);

			// introduction text
		    String stringText2 = "              Please select a course!";
	        JTextArea text2 = new JTextArea();
	        text2.setLayout(new BorderLayout());
	        text2.setEditable(false);
	        text2.setText(stringText2);
	        Font f4 = new Font(Font.SERIF, Font.ITALIC, 30);
	        text2.setFont(f4);
	        text2.setBackground(new Color(179,242,255));
	        frame2.add(text2);

			// scroll panel with courses
			this.list = new JList<Course>(courses);
			JScrollPane scrollPanel1 = new JScrollPane(list);
			scrollPanel1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scrollPanel1.setViewportView(list);
			scrollPanel1.setBackground(new Color(179,242,255));
			JPanel panel4 = new JPanel(new GridLayout());
			panel4.setBackground(new Color(179,242,255));
			panel4.add(scrollPanel1);

		    // button for more details about that specific course
	        JPanel panel5 = new JPanel();
	        panel5.setLayout(new BoxLayout(panel5, BoxLayout.Y_AXIS));
	        panel5.setBackground(new Color(153,238,255));
		    JButton button2 = new JButton("See Details");
	        button2.setPreferredSize(new Dimension(50,50));
	        button2.setForeground(Color.black);
	        button2.setBackground(new Color(230,251,255));
	        button2.setAlignmentX(Component.CENTER_ALIGNMENT);
	        Font f5 = new Font(Font.SERIF, Font.BOLD, 15);
	        button2.setFont(f5);
	        panel5.add(button2, BorderLayout.SOUTH);
	        button2.addActionListener(new Action2(list, student));
		    
		    frame2.add(panel4);
		    frame2.add(panel5);
		}
	}

	// action for button "See Details"
	class Action2 implements ActionListener
	{
		Student student;
		Catalog coursesCatalog = Catalog.getInstance();
		JList<Course> list;
		
		public Action2(JList<Course> list, Student student)
		{
			this.list = list;
			this.student = student;
		}
		
		public void actionPerformed(ActionEvent e) 
		{
			Course course = list.getSelectedValue();

			// if there isn't any course selected
			if(course == null)
			{
				JFrame frame3 = new JFrame("Details");
				frame3.setLayout(new GridLayout(3,1));
			    frame3.setSize(500,300);
			    frame3.getContentPane().setBackground(new Color(230,251,255));
			    frame3.setVisible(true);
			    
			    // null panel to make the page look good
			    JPanel panel8 = new JPanel();
		        panel8.setLayout(new GridLayout());
		        panel8.setBackground(new Color(230,251,255));
		        frame3.add(panel8);
			    
			    JTextArea text = new JTextArea("    You have not selected any course. Please select a course.");
			    Font f12 = new Font(Font.SERIF, Font.ITALIC, 20);
			    text.setFont(f12);
			    text.setEditable(false);
			    text.setBackground(new Color(230,251,255));
			    frame3.add(text);

				// button for closing the frame
			    JPanel panel9 = new JPanel();
			    panel9.setLayout(new BoxLayout(panel9, BoxLayout.Y_AXIS));
		        panel9.setBackground(new Color(230,251,255));
			    JButton button3 = new JButton("OK");
		        button3.setPreferredSize(new Dimension(50,50));
		        button3.setForeground(Color.black);
		        button3.setBackground(new Color(153,238,255));
		        button3.setAlignmentX(Component.CENTER_ALIGNMENT);
		        Font f13 = new Font(Font.SERIF, Font.BOLD, 15);
		        button3.setFont(f13);
		        panel9.add(button3);
		        button3.addActionListener(event -> {
		        	   frame3.dispose();
		        });
		        frame3.add(panel9);
			    
			}
			else
			{
				// new frame with courses	
				JFrame frame3 = new JFrame("Details");
				frame3.setLayout(new GridLayout(3,1));
			    frame3.setSize(500,300);
			    frame3.setVisible(true);

				// details about teacher
			    String teacher;
			    if(course.getTeacher() == null)
			    	teacher = "A teacher has not been assigned";
			    else
			    	teacher = course.getTeacher().toString();

				// details about course assistants
			    String assistants = "";
			    Set<Assistant> setAssistants = course.getAssistants();
			    if(setAssistants.iterator().hasNext() == false)
			    	assistants = "Assistants have not been assigned";
			    else
			    {
			    	Iterator<Assistant> itr = setAssistants.iterator();
				    while(itr.hasNext())
				    {
				    	assistants = assistants + itr.next().toString();
				    	setAssistants.iterator().next();
				    	if(itr.hasNext())
				    			assistants = assistants + ",";
				    	else
				    		break;
				    }
			    }

				// details about the assigned assistant
			    String assignedAssistant = null;
			    Set<String> IDs = course.getGroups().keySet();
			    for(String key: IDs)
				{
					for(int i = 0; i < course.getGroups().get(key).size(); i++)
						if(course.getGroups().get(key).get(i).toString().equals(student.toString()))
						{
							 assignedAssistant = course.getGroups().get(key).assistant.toString();
						}
				}
			    if(assignedAssistant == null)
			    	assignedAssistant = "Assistant has not been assigned";

				// the scores
			    Double examScore = 0.0;
			    Double partialScore = 0.0;
				Double totalScore = 0.0;
			    if(!(course.getGrade(student) == null))
			    {
			    	examScore = course.getGrade(student).getExamScore();
					partialScore = course.getGrade(student).getPartialScore();
					totalScore = course.getGrade(student).getTotal();
			    }
			    
		        JPanel panel6 = new JPanel(new GridLayout(6,1));
		        panel6.setVisible(true);
			    panel6.setBackground(new Color(230,251,255));
			    
			    // null panel to make the page look good
			    JPanel panel7 = new JPanel();
		        panel7.setLayout(new GridLayout());
		        panel7.setBackground(new Color(230,251,255));
		        frame3.add(panel7);
			    
			    JTextArea label1= new JTextArea("    This is the information you have requested");
			    label1.setBackground(new Color(179,242,255));
			    Font f6 = new Font(Font.SERIF, Font.ITALIC, 24);
			    label1.setFont(f6);
			    frame3.add(label1);
			    
			    String string1 = "         Teacher: " + teacher;
			    JLabel label2 = new JLabel(string1);
			    Font f7 = new Font(Font.SERIF, Font.ITALIC, 15);
			    label2.setFont(f7);
			    panel6.add(label2);
			    
			    String string2 = "         Assistants: " + assistants;
			    JLabel label3 = new JLabel(string2);
			    Font f8 = new Font(Font.SERIF, Font.ITALIC, 15);
			    label3.setFont(f8);
			    panel6.add(label3);
			    
			    String string3 = "         Assigned Assistant: " + assignedAssistant;
			    JLabel label4 = new JLabel(string3);
			    Font f9 = new Font(Font.SERIF, Font.ITALIC, 15);
			    label4.setFont(f9);
			    panel6.add(label4);
			    
			    String string4 = "         Exam Score: " + examScore;
			    JLabel label5 = new JLabel(string4);
			    Font f10 = new Font(Font.SERIF, Font.ITALIC, 15);
			    label5.setFont(f10);
			    panel6.add(label5);
			    
			    String string5 = "         Partial Score: " + partialScore;
			    JLabel label6 = new JLabel(string5);
			    Font f11 = new Font(Font.SERIF, Font.ITALIC, 15);
			    label6.setFont(f11);
			    panel6.add(label6);

				String string6 = "         Total Score: " + partialScore;
				JLabel label7 = new JLabel(string6);
				Font f12 = new Font(Font.SERIF, Font.ITALIC, 15);
				label7.setFont(f12);
				panel6.add(label7);
			    
			    frame3.add(panel6);
			    panel6.setVisible(true);
			}
		}
	}

	
	
	
	