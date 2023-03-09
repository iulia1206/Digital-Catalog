package tema;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Test 
{
	public static void main(String args[]) throws CourseAlreadyExists, CloneNotSupportedException, CourseDoesNotExists, IOException
	{
		Catalog catalog = Catalog.getInstance();

		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("D:\\POO\\Tema\\src\\tema\\catalog.json"));

			// courses
			for (Object course : (JSONArray) jsonObject.get("courses")) {
				UserFactory userFactory = new UserFactory();

				// course type
				String type = (String) ((JSONObject) course).get("type");

				//course name
				String name = (String) ((JSONObject) course).get("name");

				String strategy = (String) ((JSONObject) course).get("strategy");

				// course teacher
				JSONObject t = (JSONObject) ((JSONObject) course).get("teacher");
				String firstNameTeacher = (String) t.get("firstName");
				String lastNameTeacher = (String) t.get("lastName");
				Teacher teacher = (Teacher) userFactory.getUser("TEACHER", firstNameTeacher, lastNameTeacher);

				// course assistants
				Set<Assistant> assistants = new HashSet<Assistant>();

				for (Object a : (JSONArray) ((JSONObject) course).get("assistants")) {
					String firstNameAssistant = (String) ((JSONObject) a).get("firstName");
					String lastNameAssistant = (String) ((JSONObject) a).get("lastName");
					Assistant assistant = (Assistant) userFactory.getUser("ASSISTANT", firstNameAssistant, lastNameAssistant);
					assistants.add(assistant);
				}

				// course groups
				Hashtable<String, Group> groups = new Hashtable<String, Group>();

				for (Object g : (JSONArray) ((JSONObject) course).get("groups")) {
					String id = (String) ((JSONObject) g).get("ID");

					// group assistant
					JSONObject assistant = (JSONObject) ((JSONObject) g).get("assistant");
					String firstNameAssistant = (String) ((JSONObject) assistant).get("firstName");
					String lastNameAssistant = (String) ((JSONObject) assistant).get("lastName");
					Assistant a = (Assistant) userFactory.getUser("ASSISTANT", firstNameAssistant, lastNameAssistant);

					// new group
					Group group = null;
					for(Assistant assist: assistants)
						if(assist.toString().equals(a.toString()))
							group = new Group(id, assist);

					// students from group
					for (Object s : (JSONArray) ((JSONObject) g).get("students")) {
						String firstNameStudent = (String) ((JSONObject) s).get("firstName");
						String lastNameStudent = (String) ((JSONObject) s).get("lastName");
						Student student = (Student) userFactory.getUser("STUDENT", firstNameStudent, lastNameStudent);

						// student's mother
						if (!(((JSONObject) s).get("mother") == null)) {
							JSONObject mother = (JSONObject) ((JSONObject) s).get("mother");
							String firstNameMother = (String) ((JSONObject) mother).get("firstName");
							String lastNameMother = (String) ((JSONObject) mother).get("lastName");
							Parent m = (Parent) userFactory.getUser("PARENT", firstNameMother, lastNameMother);
							student.setMother(m);
							m.student = student;
						}

						//student's father
						if (!(((JSONObject) s).get("father") == null)) {
							JSONObject father = (JSONObject) ((JSONObject) s).get("father");
							String firstNameFather = (String) ((JSONObject) father).get("firstName");
							String lastNameFather = (String) ((JSONObject) father).get("lastName");
							Parent f = (Parent) userFactory.getUser("PARENT", firstNameFather, lastNameFather);
							student.setFather(f);
							f.student = student;
						}

						group.add(student);
					}

					// group students
					groups.put(id, group);
				}

				// building a course
				Course c;

				if (type.equals("FullCourse"))
				{
					c = new FullCourse.FullCourseBuilder(name)
							.teacher(teacher)
							.assistants(assistants)
							.groups(groups)
							.build();

					if(strategy.equals("BestExamScore"))
						c.setStrategy(new BestExamScore());

					if(strategy.equals("BestPartialScore"))
						c.setStrategy(new BestPartialScore());

					if(strategy.equals("BestTotalScore"))
						c.setStrategy(new BestTotalScore());
				}
				else
				{
					c = new PartialCourse.PartialCourseBuilder(name)
							.teacher(teacher)
							.assistants(assistants)
							.groups(groups)
							.build();

					if(strategy.equals("BestExamScore"))
						c.setStrategy(new BestExamScore());

					if(strategy.equals("BestPartialScore"))
						c.setStrategy(new BestPartialScore());

					if(strategy.equals("BestTotalScore"))
						c.setStrategy(new BestTotalScore());
				}

				catalog.addCourse(c);

			}

			// complete examScores dictionary
			ScoreVisitor allScores = ScoreVisitor.getInstance();

			for (Course c : catalog.catalog)
			{
				// we store all grades and students from course c and we send them as parameters
				// to buildExamScores along with courseName
				ArrayList<Student> students = new ArrayList<Student>();
				ArrayList<Double> grades = new ArrayList<Double>();

				// look into examScores
				for (Object examS : (JSONArray) jsonObject.get("examScores"))
				{
					UserFactory userFactory = new UserFactory();

					Double examScore = (Double) ((JSONObject) examS).get("grade");

					JSONObject student = (JSONObject) ((JSONObject) examS).get("student");
					String firstNameStudent = (String) student.get("firstName");
					String lastNameStudent = (String) student.get("lastName");
					Student s = (Student) userFactory.getUser("STUDENT", firstNameStudent, lastNameStudent);

					String courseName = (String) ((JSONObject) examS).get("course");

					//finds the right student so that parents would be completed too
					for (int i = 0; i < catalog.catalog.size(); i++)
						if (catalog.catalog.get(i).getName().toString().equals(courseName))
							for (int j = 0; j < catalog.catalog.get(i).getAllStudents().size(); j++)
								if (catalog.catalog.get(i).getAllStudents().get(j).toString().equals(s.toString()))
								{
									s = catalog.catalog.get(i).getAllStudents().get(j);
								}

					if(c.getName().equals(courseName))
					{
						students.add(s);
						grades.add(examScore);
					}
				}

				allScores.buildExamScores(c.getName(), students, grades);
			}

			// complete partialScores dictionary
			for (Course c : catalog.catalog)
			{
				for(Assistant assist: c.getAssistants())
				{
					// we store all grades and students from course c with a specific assistant and we
					// send them as parameters to buildExamScores along with courseName
					ArrayList<Student> students = new ArrayList<Student>();
					ArrayList<Double> grades = new ArrayList<Double>();

					// look into partialScores
					for (Object partialS : (JSONArray) jsonObject.get("partialScores"))
					{
						UserFactory userFactory = new UserFactory();

						JSONObject teacher = (JSONObject) ((JSONObject) partialS).get("assistant");
						String firstNameAssistant = (String) teacher.get("firstName");
						String lastNameAssistant = (String) teacher.get("lastName");
						Assistant a = (Assistant) userFactory.getUser("ASSISTANT", firstNameAssistant, lastNameAssistant);

						Double partialScore = (Double) ((JSONObject) partialS).get("grade");

						JSONObject student = (JSONObject) ((JSONObject) partialS).get("student");
						String firstNameStudent = (String) student.get("firstName");
						String lastNameStudent = (String) student.get("lastName");
						Student s = (Student) userFactory.getUser("STUDENT", firstNameStudent, lastNameStudent);

						String courseName = (String) ((JSONObject) partialS).get("course");

						//finds the right student so that parents would be completed too
						for (int i = 0; i < catalog.catalog.size(); i++)
							if (catalog.catalog.get(i).getName().toString().equals(courseName))
								for (int j = 0; j < catalog.catalog.get(i).getAllStudents().size(); j++)
									if (catalog.catalog.get(i).getAllStudents().get(j).toString().equals(s.toString()))
									{
										s = catalog.catalog.get(i).getAllStudents().get(j);
									}

						if (c.getName().equals(courseName) && a.toString().equals(assist.toString()))
						{
							students.add(s);
							grades.add(partialScore);
						}
					}

					allScores.buildPartialScores(c.getName(), students, grades, assist);
				}
			}

			// the catalog and the rest were completed successfully
			System.out.println("All the connections were made");
			System.out.println("\n");

			// finding the assistant that matches the course the selected teacher teaches
			// to check if the parent page gets the right output
 			Assistant a = new Assistant("Andrei","Georgescu");
			for(Course c: catalog.catalog)
				for(Assistant as: c.getAssistants())
					if(as.toString().equals(a.toString()))
						a = as;

			// testing the student page
			StudentPage d = new StudentPage(catalog.catalog.get(0).getAllStudents().get(1));

			// testing the assistant page
			AssistantTeacherPage s = new AssistantTeacherPage(a,null);

			AssistantTeacherPage sa = new AssistantTeacherPage(null,catalog.catalog.get(0).getTeacher());
			ParentPage p = new ParentPage(catalog.catalog.get(0).getAllStudents().get(1).father);

			// testing Strategy
			//get information from course 3 to create allGrades to test Strategy
			Teacher teacherCourse2 = catalog.catalog.get(2).getTeacher();

			Iterator<Assistant> assistants = catalog.catalog.get(2).getAssistants().iterator();
			Assistant assistantCourse2 = assistants.next();
			allScores.visit(assistantCourse2);
			assistantCourse2 = assistants.next();
			allScores.visit(assistantCourse2);
			allScores.visit(teacherCourse2);

			System.out.println("Best student: " + catalog.catalog.get(2).getBestStudent());
			System.out.println("\n");

			//testing Memento
			catalog.catalog.get(2).makeBackup();

			// initial grades
			for(Grade g: catalog.catalog.get(2).getGrades())
				System.out.println(g.getStudent().toString() + " - ExamScore: " + g.getExamScore() + "    " +
						g.getStudent().toString() + " - PartialScore: " + g.getPartialScore());
			System.out.println("\n");

			for(Grade g: catalog.catalog.get(2).getGrades())
			{
				g.setPartialScore(400.0);
				g.setExamScore(400.0);
			}

			// changed grades
			for(Grade g: catalog.catalog.get(2).getGrades())
				System.out.println(g.getStudent().toString() + " - ExamScore: " + g.getExamScore() + "    " +
						g.getStudent().toString() + " - PartialScore: " + g.getPartialScore());
			System.out.println("\n");

			catalog.catalog.get(2).undo();

			// original grades
			for(Grade g: catalog.catalog.get(2).getGrades())
				System.out.println(g.getStudent().toString() + " - ExamScore: " + g.getExamScore() + "    " +
						g.getStudent().toString() + " - PartialScore: " + g.getPartialScore());
			System.out.println("\n");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
