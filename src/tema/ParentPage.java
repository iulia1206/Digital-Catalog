package tema;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ParentPage
{
    JFrame frame1;

    public ParentPage(Parent parent) throws IOException
    {
        this.frame1 = new JFrame("Parent's Page");

        // Setting up the frame
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setSize(1680,1080);
        frame1.setMinimumSize(new Dimension(500,500));
        frame1.setLayout(new GridLayout(2,1));
        frame1.getContentPane().setBackground(new Color(234,255,230));

        // border
        Border lowered, raised;
        TitledBorder title;
        lowered = BorderFactory.createLoweredBevelBorder();
        raised = BorderFactory.createRaisedBevelBorder();
        title = BorderFactory.createTitledBorder("UPB - Parent");
        Font f1 = new Font(Font.SERIF, Font.ITALIC, 20);
        title.setTitleFont(f1);
        final JPanel panel1 = new JPanel();
        panel1.setPreferredSize(new Dimension(1200, 700));
        panel1.setBackground(new Color(234,255,230));
        panel1.setBorder(title);
        frame1.add(panel1);

        // welcome text
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(4,1));

        String stringText1 = "Welcome, " + parent + "! We are so glad to have you back!";
        String stringText2 = "     Click here for your notification. Have a good day!";

        JTextArea text1 = new JTextArea();
        Font f2 = new Font(Font.SERIF, Font.ITALIC, 20);
        text1.setFont(f2);
        text1.setBackground(new Color(234,255,230));
        text1.setEditable(false);
        text1.setText(stringText1);
        panel2.add(text1);

        JTextArea text2 = new JTextArea();
        Font f3 = new Font(Font.SERIF, Font.ITALIC, 20);
        text2.setFont(f3);
        text2.setBackground(new Color(234,255,230));
        text2.setEditable(false);
        text2.setText(stringText2);
        panel2.add(text2);

        // null panel to make the page look good
        JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayout());
        panel3.setBackground(new Color(234,255,230));
        panel2.add(panel3);

        // button that leads to the second frame
        JButton button1 = new JButton("Show notification");
        button1.setPreferredSize(new Dimension(20,20));
        button1.setForeground(Color.black);
        button1.setBackground(new Color(191,255,179));
        Font f4 = new Font(Font.SERIF, Font.BOLD, 15);
        button1.setFont(f4);
        panel2.add(button1);
        button1.addActionListener(new Action9(parent));

        // polytehnic university image
        BufferedImage image = ImageIO.read(new File("D:\\POO\\Tema\\src\\tema\\image.png"));
        JLabel icon = new JLabel(new ImageIcon(image));
        frame1.add(icon);

        panel2.setVisible(true);
        panel1.add(panel2);

        frame1.setVisible(true);
    }
}

class Action9 implements ActionListener
{
    Parent parent;
    Catalog coursesCatalog = Catalog.getInstance();

    public Action9(Parent parent)
    {
        this.parent = parent;
    }

    public void actionPerformed(ActionEvent e)
    {
        // if there isn't any notification
        if(parent.studentGrade == null)
        {
            JFrame frame2 = new JFrame("Notifications");
            frame2.setLayout(new GridLayout(3,1));
            frame2.setSize(500,300);
            frame2.getContentPane().setBackground(new Color(234,255,230));
            frame2.setVisible(true);

            // null panel to make the page look good
            JPanel panel4 = new JPanel();
            panel4.setLayout(new GridLayout());
            panel4.setBackground(new Color(234,255,230));
            frame2.add(panel4);

            JTextArea text = new JTextArea("                 You have no notification");
            Font f5 = new Font(Font.SERIF, Font.ITALIC, 25);
            text.setFont(f5);
            text.setEditable(false);
            text.setBackground(new Color(234,255,230));
            frame2.add(text);

            // button for closing the frame
            JPanel panel5 = new JPanel();
            panel5.setLayout(new BoxLayout(panel5, BoxLayout.Y_AXIS));
            panel5.setBackground(new Color(234,255,230));
            JButton button2 = new JButton("OK");
            button2.setPreferredSize(new Dimension(50,50));
            button2.setForeground(Color.black);
            button2.setBackground(new Color(191,255,179));
            button2.setAlignmentX(Component.CENTER_ALIGNMENT);
            Font f6 = new Font(Font.SERIF, Font.BOLD, 15);
            button2.setFont(f6);
            panel5.add(button2);
            button2.addActionListener(event -> {
                frame2.dispose();
            });
            frame2.add(panel5);
        }
        else
        {
            // new frame with notification
            JFrame frame2 = new JFrame("Details");
            frame2.setLayout(new GridLayout(3,1));
            frame2.setSize(500,300);
            frame2.setVisible(true);

            // null panel to make the page look good
            JPanel panel4 = new JPanel();
            panel4.setLayout(new GridLayout());
            panel4.setBackground(new Color(234,255,230));

            JTextArea label1= new JTextArea("                Here is your notification");
            label1.setBackground(new Color(234,255,230));
            Font f6 = new Font(Font.SERIF, Font.ITALIC, 24);
            label1.setEditable(false);
            label1.setFont(f6);
            frame2.add(label1);

            JPanel panel5 = new JPanel(new GridLayout(5,1));
            panel5.setVisible(true);
            panel5.setBackground(new Color(191,255,179));

            JTextArea label2= new JTextArea("New updates about " + parent.student.toString() + "'s grade:");
            label2.setBackground(new Color(191,255,179));
            Font f7 = new Font(Font.SERIF, Font.ITALIC, 16);
            label2.setEditable(false);
            label2.setFont(f7);
            panel5.add(label2);

            // partial score
            String stringText3 = "";
            if(parent.studentGrade.getPartialScore() == null)
               stringText3 = "        Partial Grade: This score is not completed yet";
            else
                stringText3 = "        Partial Grade: " + parent.studentGrade.getPartialScore();

            JTextArea label3= new JTextArea(stringText3);
            label3.setBackground(new Color(191,255,179));
            Font f8 = new Font(Font.SERIF, Font.ITALIC, 15);
            label3.setEditable(false);
            label3.setFont(f8);
            panel5.add(label3);

            // partial score
            String stringText4 = "";
            if(parent.studentGrade.getExamScore() == null)
                stringText4 = "        Exam Grade: This score is not completed yet";
            else
                stringText4 = "        Exam Grade: " + parent.studentGrade.getExamScore();

            JTextArea label4= new JTextArea(stringText4);
            label4.setBackground(new Color(191,255,179));
            Font f9 = new Font(Font.SERIF, Font.ITALIC, 15);
            label4.setEditable(false);
            label4.setFont(f9);
            panel5.add(label4);

            // total score
            String stringText5 = "";
            if(parent.studentGrade.getPartialScore() != null && parent.studentGrade.getExamScore() != null)
                stringText5 = "        Total Grade: " + parent.studentGrade.getTotal();
            else
                if(parent.studentGrade.getPartialScore() != null && parent.studentGrade.getExamScore() == null)
                stringText5 = "        Total Grade: " + parent.studentGrade.getPartialScore() + "(up till now)";
                else
                    if(parent.studentGrade.getPartialScore() == null && parent.studentGrade.getExamScore() != null)
                        stringText5 = "        Total Grade: " + parent.studentGrade.getExamScore() + "(up till now)";
                    else
                        stringText5 = "        Total Grade: This score is not completed yet";

            JTextArea label5= new JTextArea(stringText5);
            label5.setBackground(new Color(191,255,179));
            Font f10 = new Font(Font.SERIF, Font.ITALIC, 15);
            label5.setEditable(false);
            label5.setFont(f10);
            panel5.add(label5);

            frame2.add(panel5);
            frame2.add(panel4);
            panel5.setVisible(true);
        }
    }
}
