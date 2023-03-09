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

public class AssistantTeacherPage
{
    JFrame frame1;
    Catalog coursesCatalog = Catalog.getInstance();

    public AssistantTeacherPage(Assistant assistant, Teacher teacher) throws IOException
    {
        if(assistant != null && teacher != null)
            return;

        if(assistant != null)
            this.frame1 = new JFrame("Assistant's Page");
        else
            this.frame1 = new JFrame("Teacher's Page");

        // Setting up the frame
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setSize(1680,1080);
        frame1.setMinimumSize(new Dimension(500,500));
        frame1.setLayout(new GridLayout(2,1));
        frame1.getContentPane().setBackground(new Color(255,230,230));

        // border
        Border lowered, raised;
        TitledBorder title;
        lowered = BorderFactory.createLoweredBevelBorder();
        raised = BorderFactory.createRaisedBevelBorder();

        if(assistant != null)
            title = BorderFactory.createTitledBorder("UPB - Assistant");
        else
            title = BorderFactory.createTitledBorder("UPB - Teacher");

        //panel for border
        Font f1 = new Font(Font.SERIF, Font.ITALIC, 20);
        title.setTitleFont(f1);
        final JPanel panel1 = new JPanel();
        panel1.setPreferredSize(new Dimension(1200, 700));
        panel1.setBackground(new Color(255,230,230));
        panel1.setBorder(title);
        frame1.add(panel1);

        // adding the welcome text
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(4,1));
        String stringText1;

        if(assistant != null)
            stringText1 = "Welcome, " + assistant + "! We are so glad to have you back! ";
        else
            stringText1 = "Welcome, " + teacher + "! We are so glad to have you back! ";

        String stringText2 = "   Here is the list of courses you teach. Have a good day!";

        JTextArea text1 = new JTextArea();
        Font f2 = new Font(Font.SERIF, Font.ITALIC, 20);
        text1.setFont(f2);
        text1.setBackground(new Color(255,230,230));
        text1.setEditable(false);
        text1.setText(stringText1);
        panel2.add(text1);

        JTextArea text2 = new JTextArea();
        Font f3 = new Font(Font.SERIF, Font.ITALIC, 20);
        text2.setFont(f3);
        text2.setBackground(new Color(255,230,230));
        text2.setEditable(false);
        text2.setText(stringText2);
        panel2.add(text2);

        // null panel to make the page look good
        JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayout());
        panel3.setBackground(new Color(255,230,230));
        panel2.add(panel3);

        // button that leads to frame2
        JButton button1 = new JButton("Choose Course");
        button1.setPreferredSize(new Dimension(20,20));
        button1.setForeground(Color.black);
        button1.setBackground(new Color(255,179,179));
        Font f4 = new Font(Font.SERIF, Font.BOLD, 15);
        button1.setFont(f4);
        panel2.add(button1);

        if(assistant != null)
            button1.addActionListener(new Action3(assistant));
        else
            button1.addActionListener(new Action4(teacher));

        // polytehnic university image
        BufferedImage image = ImageIO.read(new File("D:\\POO\\Tema\\src\\tema\\image.png"));
        JLabel icon = new JLabel(new ImageIcon(image));
        frame1.add(icon);

        panel2.setVisible(true);
        panel1.add(panel2);

        frame1.setVisible(true);
    }
}

// action for button1 for assistant user
class Action3 implements ActionListener
{
    Catalog coursesCatalog = Catalog.getInstance();
    Assistant assistant;
    JList<Course> list;

    public Action3(Assistant assistant)
    {
        this.assistant = assistant;
    }

    public void actionPerformed(ActionEvent e)
    {
        // find out what courses the assistant teaches
        DefaultListModel<Course> courses = new DefaultListModel<Course>();
        for(Course course: coursesCatalog.catalog)
            if(course.getAssistants().contains(assistant))
                courses.addElement(course);

        // new frame with courses
        JFrame frame2 = new JFrame("Courses");
        frame2.setLayout(new GridLayout(3,1));
        frame2.setSize(500,300);
        frame2.setBackground(new Color(255,230,230));
        frame2.setVisible(true);

        // introduction text
        String stringText3 = "              Please select a course!";
        JTextArea text3 = new JTextArea();
        text3.setLayout(new BorderLayout());
        text3.setEditable(false);
        text3.setText(stringText3);
        Font f5 = new Font(Font.SERIF, Font.ITALIC, 30);
        text3.setFont(f5);
        text3.setBackground(new Color(255,179,179));
        frame2.add(text3);

        // scroll panel with courses
        this.list = new JList<Course>(courses);
        JScrollPane scrollPanel1 = new JScrollPane(list);
        scrollPanel1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPanel1.setViewportView(list);
        scrollPanel1.setBackground(new Color(255,179,179));
        JPanel panel4 = new JPanel(new GridLayout());
        panel4.setBackground(new Color(255,230,230));
        panel4.add(scrollPanel1);

        // button for list of students and grades
        JPanel panel5 = new JPanel();
        panel5.setLayout(new BoxLayout(panel5, BoxLayout.Y_AXIS));
        panel5.setBackground(new Color(255,179,179));
        JButton button2 = new JButton("See Students");
        button2.setPreferredSize(new Dimension(50,50));
        button2.setForeground(Color.black);
        button2.setBackground(new Color(255,230,230));
        button2.setAlignmentX(Component.CENTER_ALIGNMENT);
        Font f6 = new Font(Font.SERIF, Font.BOLD, 15);
        button2.setFont(f6);
        panel5.add(button2, BorderLayout.SOUTH);
        button2.addActionListener(new Action5(list, assistant));

        frame2.add(panel4);
        frame2.add(panel5);
    }
}

// action for button1 for teacher user
class Action4 implements ActionListener
{
    Catalog coursesCatalog = Catalog.getInstance();
    Teacher teacher;
    JList<Course> list;
    public Action4(Teacher teacher)
    {
        this.teacher = teacher;
    }

    public void actionPerformed(ActionEvent e)
    {
        // find out what courses the assistant teaches
        DefaultListModel<Course> courses = new DefaultListModel<Course>();
        for(Course course: coursesCatalog.catalog)
            if(course.getTeacher().equals(teacher))
                courses.addElement(course);

        // new frame with courses
        JFrame frame2 = new JFrame("Courses");
        frame2.setLayout(new GridLayout(3,1));
        frame2.setSize(500,300);
        frame2.setBackground(new Color(255,230,230));
        frame2.setVisible(true);

        // introduction text
        String stringText3 = "              Please select a course!";
        JTextArea text3 = new JTextArea();
        text3.setLayout(new BorderLayout());
        text3.setEditable(false);
        text3.setText(stringText3);
        Font f5 = new Font(Font.SERIF, Font.ITALIC, 30);
        text3.setFont(f5);
        text3.setBackground(new Color(255,179,179));
        frame2.add(text3);

        // scroll panel with courses
        this.list = new JList<Course>(courses);
        JScrollPane scrollPanel1 = new JScrollPane(list);
        scrollPanel1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPanel1.setViewportView(list);
        scrollPanel1.setBackground(new Color(255,179,179));
        JPanel panel4 = new JPanel(new GridLayout());
        panel4.setBackground(new Color(255,230,230));
        panel4.add(scrollPanel1);

        // button for list of students and grades
        JPanel panel5 = new JPanel();
        panel5.setLayout(new BoxLayout(panel5, BoxLayout.Y_AXIS));
        panel5.setBackground(new Color(255,179,179));
        JButton button2 = new JButton("See Students");
        button2.setPreferredSize(new Dimension(50,50));
        button2.setForeground(Color.black);
        button2.setBackground(new Color(255,230,230));
        button2.setAlignmentX(Component.CENTER_ALIGNMENT);
        Font f6 = new Font(Font.SERIF, Font.BOLD, 15);
        button2.setFont(f6);
        panel5.add(button2, BorderLayout.SOUTH);
        button2.addActionListener(new Action6(list, teacher));

        frame2.add(panel4);
        frame2.add(panel5);
    }
}

// action for button "See Students" for user assistant
class Action5 implements ActionListener
{
    Assistant assistant;
    Catalog coursesCatalog = Catalog.getInstance();
    JList<Course> list;

    ScoreVisitor info = ScoreVisitor.getInstance();

    public Action5(JList<Course> list, Assistant assistant)
    {
        this.list = list;
        this.assistant = assistant;
    }

    public void actionPerformed(ActionEvent e)
    {
        Course course = list.getSelectedValue();

        JFrame frame3 = new JFrame("Students");
        frame3.setLayout(new GridLayout(3,1));
        frame3.setSize(500,300);

        // if there isn't a course selected
        if(course == null)
        {
            // new frame if a course doesn't get selected
            frame3.getContentPane().setBackground(new Color(255,230,230));
            frame3.setVisible(true);

            // null panel to make the page look good
            JPanel panel6 = new JPanel();
            panel6.setLayout(new GridLayout());
            panel6.setBackground(new Color(255,230,230));
            frame3.add(panel6);

            JTextArea text4 = new JTextArea("You have not selected any course. Please select a course to see the students.");
            Font f12 = new Font(Font.SERIF, Font.ITALIC, 15);
            text4.setFont(f12);
            text4.setEditable(false);
            text4.setBackground(new Color(255,230,230));
            frame3.add(text4);

            // button for closing the frame
            JPanel panel7 = new JPanel();
            panel7.setLayout(new BoxLayout(panel7, BoxLayout.Y_AXIS));
            panel7.setBackground(new Color(255,230,230));
            JButton button3 = new JButton("OK");
            button3.setPreferredSize(new Dimension(50,50));
            button3.setForeground(Color.black);
            button3.setBackground(new Color(255,179,179));
            button3.setAlignmentX(Component.CENTER_ALIGNMENT);
            Font f13 = new Font(Font.SERIF, Font.BOLD, 15);
            button3.setFont(f13);
            panel7.add(button3);
            button3.addActionListener(event -> {
                frame3.dispose();
            });
            frame3.add(panel7);
        }
        else
        {
            // new frame with courses
            frame3.setVisible(true);
            frame3.setBackground(new Color(255,230,230));

            // lists with the students and grades from a specific assistant's class
            ArrayList<Student> s = new ArrayList<Student>();
            ArrayList<Double> g = new ArrayList<Double>();

            // default list to make a scrollbar
            DefaultListModel<String> results = new DefaultListModel<String>();

            // completing the students' and teachers' lists
            for(Course c: coursesCatalog.catalog)
                if(c.getName().toString().equals(course.toString()))
                {
                    s = info.getStudents(info.partialScores, assistant);
                    g = info.getGrades(info.partialScores, assistant);
                }

            ArrayList<String> strings = new ArrayList<>();

            // putting the students and grades into a string
            for(int i = 0; i < s.size(); i++)
                strings.add(s.get(i).toString() + ": " +g.get(i));

            for(String st: strings)
                results.addElement(st);

            JTextArea text4 = new JTextArea( "                   Students' grades");
            text4.setLayout(new BorderLayout());
            text4.setEditable(false);
            Font f7 = new Font(Font.SERIF, Font.ITALIC, 30);
            text4.setFont(f7);
            text4.setBackground(new Color(255,179,179));
            frame3.add(text4);

            // scroll panel with students and their grades
            JList<String> list2 = new JList<>(results);
            JScrollPane scrollPanel2 = new JScrollPane(list2);
            scrollPanel2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPanel2.setViewportView(list2);
            scrollPanel2.setBackground(new Color(255,179,179));
            JPanel panel6 = new JPanel(new GridLayout());
            panel6.setBackground(new Color(255,230,230));
            panel6.add(scrollPanel2);

            // button for validating the grades(adding them in the catalog)
            JPanel panel7 = new JPanel();
            panel7.setLayout(new BoxLayout(panel7, BoxLayout.Y_AXIS));
            panel7.setBackground(new Color(255,179,179));
            JButton button3 = new JButton("Validate grades");
            button3.setPreferredSize(new Dimension(50,50));
            button3.setForeground(Color.black);
            button3.setBackground(new Color(255,230,230));
            button3.setAlignmentX(Component.CENTER_ALIGNMENT);
            Font f6 = new Font(Font.SERIF, Font.BOLD, 18);
            button3.setFont(f6);
            panel7.add(button3, BorderLayout.SOUTH);
            button3.addActionListener(new Action7(assistant, course));

            frame3.add(panel6);
            frame3.add(panel7);
        }
    }
}

// action for button "See Students" for user teacher
class Action6 implements ActionListener
{
    Teacher teacher;
    Catalog coursesCatalog = Catalog.getInstance();
    JList<Course> list;
    ScoreVisitor info = ScoreVisitor.getInstance();

    public Action6(JList<Course> list, Teacher teacher)
    {
        this.list = list;
        this.teacher = teacher;
    }

    public void actionPerformed(ActionEvent e)
    {
        Course course = list.getSelectedValue();

        JFrame frame3 = new JFrame("Students");
        frame3.setLayout(new GridLayout(3,1));
        frame3.setSize(500,300);

        // if there isn't a course selected
        if(course == null)
        {
            // new frame if a course doesn't get selected
            frame3.getContentPane().setBackground(new Color(255,230,230));
            frame3.setVisible(true);

            // null panel to make the page look good
            JPanel panel6 = new JPanel();
            panel6.setLayout(new GridLayout());
            panel6.setBackground(new Color(255,230,230));
            frame3.add(panel6);

            JTextArea text4 = new JTextArea("You have not selected any course. Please select a course to see the students.");
            Font f12 = new Font(Font.SERIF, Font.ITALIC, 15);
            text4.setFont(f12);
            text4.setEditable(false);
            text4.setBackground(new Color(255,230,230));
            frame3.add(text4);

            // button for closing the frame
            JPanel panel7 = new JPanel();
            panel7.setLayout(new BoxLayout(panel7, BoxLayout.Y_AXIS));
            panel7.setBackground(new Color(255,230,230));
            JButton button3 = new JButton("OK");
            button3.setPreferredSize(new Dimension(50,50));
            button3.setForeground(Color.black);
            button3.setBackground(new Color(255,179,179));
            button3.setAlignmentX(Component.CENTER_ALIGNMENT);
            Font f13 = new Font(Font.SERIF, Font.BOLD, 15);
            button3.setFont(f13);
            panel7.add(button3);
            button3.addActionListener(event -> {
                frame3.dispose();
            });
            frame3.add(panel7);
        }
        else
        {
            // new frame with courses
            frame3.setVisible(true);
            frame3.setBackground(new Color(255,230,230));

            // lists with the students and grades from a specific teacher's class
            ArrayList<Student> s = new ArrayList<Student>();
            ArrayList<Double> g = new ArrayList<Double>();

            // default list to make a scrollbar
            DefaultListModel<String> results = new DefaultListModel<String>();

            // completing the students' and teachers' lists
            for(Course c: coursesCatalog.catalog)
                if(c.getName().toString().equals(course.toString()))
                {
                    s = info.getStudents(info.examScores, teacher);
                    g = info.getGrades(info.examScores, teacher);
                }

            ArrayList<String> strings = new ArrayList<>();

            // putting the students and grades into a string
            for(int i = 0; i < s.size(); i++)
                strings.add(s.get(i).toString() + ": " +g.get(i));

            for(String st: strings)
                results.addElement(st);

            JTextArea text4 = new JTextArea( "                   Students' grades");
            text4.setLayout(new BorderLayout());
            text4.setEditable(false);
            Font f7 = new Font(Font.SERIF, Font.ITALIC, 30);
            text4.setFont(f7);
            text4.setBackground(new Color(255,179,179));
            frame3.add(text4);

            // scroll panel with courses
            JList<String> list2 = new JList<>(results);
            JScrollPane scrollPanel2 = new JScrollPane(list2);
            scrollPanel2.setSize( new Dimension(1000,1000));
            scrollPanel2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPanel2.setViewportView(list2);
            scrollPanel2.setBackground(new Color(255,179,179));
            JPanel panel6 = new JPanel(new GridLayout());
            panel6.setBackground(new Color(255,230,230));
            panel6.add(scrollPanel2);

            // button for validating the grades(adding them in the catalog)
            JPanel panel7 = new JPanel();
            panel7.setLayout(new BoxLayout(panel7, BoxLayout.Y_AXIS));
            panel7.setBackground(new Color(255,179,179));
            JButton button3 = new JButton("Validate grades");
            button3.setPreferredSize(new Dimension(50,50));
            button3.setForeground(Color.black);
            button3.setBackground(new Color(255,230,230));
            button3.setAlignmentX(Component.CENTER_ALIGNMENT);
            Font f6 = new Font(Font.SERIF, Font.BOLD, 15);
            button3.setFont(f6);
            panel7.add(button3, BorderLayout.SOUTH);
            button3.addActionListener(new Action8(teacher));

            frame3.add(panel6);
            frame3.add(panel7);
        }
    }
}

// action for button "Validate grades" for user assistant
class Action7 implements ActionListener
{
    ScoreVisitor info = ScoreVisitor.getInstance();
    Catalog catalog = Catalog.getInstance();
    Assistant assistant;
    Course course;

    public Action7(Assistant assistant, Course course)
    {
        this.assistant = assistant;
        this.course = course;
    }

    public void actionPerformed(ActionEvent e)
    {
        info.visit(assistant);

        System.out.println("Grades validated by " + assistant.toString() + " :");

        //printing the grades added to catalog by assistant
        for(Course c: catalog.catalog)
            for(Assistant a: c.getAssistants())
                if(a.toString().equals(assistant.toString()))
                    for(Grade g: c.getGrades())
                    {
                        if(g.getStudent().mother != null)
                            catalog.addObserver(g.getStudent().mother);

                        if(g.getStudent().father != null)
                            catalog.addObserver(g.getStudent().father);

                        catalog.notifyObservers(g);

                        System.out.println(g.getStudent() + ":" + g.getPartialScore());
                    }

        System.out.println("\n");

    }
}

// action for button "Validate grades" for user teacher
class Action8 implements ActionListener
{
    ScoreVisitor info = ScoreVisitor.getInstance();
    Catalog catalog = Catalog.getInstance();
    Teacher teacher;

    public Action8(Teacher teacher)
    {
        this.teacher = teacher;
    }

    public void actionPerformed(ActionEvent e)
    {
        info.visit(teacher);

        System.out.println("Grades validated by " + teacher.toString() + " :");

        //printing the grades added to catalog by teacher
        for(Course c: catalog.catalog)
            if(c.getTeacher().toString().equals(teacher.toString()))
                for(Grade g: c.getGrades())
                {
                    if(g.getStudent().mother != null)
                        catalog.addObserver(g.getStudent().mother);

                    if(g.getStudent().father != null)
                        catalog.addObserver(g.getStudent().father);

                    catalog.notifyObservers(g);

                    System.out.println(g.getStudent() + ":" + g.getExamScore());
                }


        System.out.println("\n");
    }
}




