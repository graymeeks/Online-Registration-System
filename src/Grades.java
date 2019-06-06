import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.util.ArrayList;

// Houses driver functions of grade-based menu options
// as well as related helper functions.
public class Grades 
{
	// Driver function for upload grades menu option
	public static void uploadGrades(Connection c, Scanner kb)
	{
		String code;
		int year;
		String semester;
		
		System.out.println("****UPLOAD GRADES*****");
		code = Course.promptExistingCode(c, kb);
		System.out.println("Please enter the year of the course:");
		year = IOHelper.getNextInt(kb, "Error: please enter a numeric value for year.");
		System.out.println("Please enter the semester of the course:");
		semester = IOHelper.getNextString(kb);
		
		// Get a list of student ssns
		ArrayList<Integer> ssnList = new ArrayList<Integer>();
		try
		{
			Statement stmt = c.createStatement();
			String sql = "SELECT ssn FROM Registered WHERE code='" + code + "' and year=" + year + " and semester='" + semester + "'";
			ResultSet rs =stmt.executeQuery(sql);
			while (rs.next())
			{
				int ssn = rs.getInt("ssn");
				ssnList.add(ssn);
			}
			rs.close();
			stmt.close();
		}
		catch(Exception e)
		{
			System.out.println("Error interacting with the database.");
			System.out.println("Program terminated.");
			System.exit(0);
		}
		
		// Flow control based on existence of students in course
		if (ssnList.size() == 0)
		{
			System.out.println("No students exist for the corresponding course you entered.");
			System.out.println("Returning to the main menu...");
			IOHelper.pause(4000);
			return;
		}
		else
		{
			System.out.println("\nPlease enter a grade for each student:\n");
			// Prompt user to enter character grade for each name
			for (int i=0; i<ssnList.size(); i++) 
			{
				int curr = ssnList.get(i);
				try
				{
					System.out.println(SQLHelper.getStudentName(c, curr));
					char grade = IOHelper.getNextGrade(kb);
					// Update specific record to hold the assigned grade
					SQLHelper.assignGrade(c, curr, code, year, semester, grade);
					IOHelper.actionSuccessful();
				}
				catch(Exception e)
				{
					System.out.println("Error interacting with the database.");
					System.out.println("Program terminated...");
					System.exit(0);
				}
			}
		}
		
	}
	
	// Prints out a grade given a 'registered' key
	public static void checkGrade(Connection c, Scanner kb)
	{
		Registration r = Registered.promptExistingRegistration(c, kb);
		String s = null;
		try
		{
			Statement stmt = c.createStatement();
			String sql = "SELECT grade FROM Registered WHERE code='" + r.code + "' and year=" + r.year + " and semester='" + r.semester + "'";
			ResultSet rs =stmt.executeQuery(sql);
			while (rs.next())
			{
				s = rs.getString("grade");
			}
			rs.close();
			stmt.close();
		}
		catch(Exception e)
		{
			System.out.println("Error interacting with the database.");
			System.out.println("Program terminated.");
			System.exit(0);
		}
		if (s != null)
		{
			System.out.println("The grade received for this course: " + s);
		}
		else
		{
			System.out.println("No grade has been submitted for this student/course combination yet.");
		}
		System.out.println("Returning to the main menu...");
		IOHelper.pause(4000);
	}
	
}
