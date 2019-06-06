import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

// Class that holds course-related functionality
public class Course 
{
	// Prompts user to enter a unique course code that is not in use
	public static String promptNewCode(Connection c, Scanner kb)
	{
		System.out.println("Please enter a new course code:");
		String code;
		boolean valid = false;
		while (!valid)
		{
			code = IOHelper.getNextString(kb);
			try
			{
				Statement st=c.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM Course WHERE code='" + code + "'");
				boolean empty = SQLHelper.rsIsEmpty(rs);
				if (empty && code.length() <= 10)
				{
					return code;
				}
				else if (!empty)
				{
					System.out.println("Invalid course code; code already in use.");
					System.out.println("Please enter a valid course code.");
				}
				else if (code.length() > 10)
				{
					System.out.println("Invalid course code; code must not exceed 10 characters.");
					System.out.println("Please enter a valid course code.");
				}
				rs.close();
				st.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("Error interacting with database.");
				System.out.println("Terminating program.");
				System.exit(0);
			}
		}
		return null;
	}
	
	
	// Prompts user to enter a unique course code that is currently in the table
	public static String promptExistingCode(Connection c, Scanner kb)
	{
		System.out.println("Please enter an existing course code:");
		String code;
		boolean valid = false;
		while (!valid)
		{
			code = IOHelper.getNextString(kb);
			try
			{
				Statement st=c.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM Course WHERE code='" + code + "'");
				boolean empty = SQLHelper.rsIsEmpty(rs);
				if (!empty)
				{
					return code;
				}
				else
				{
					System.out.println("Invalid course code; code not in use.");
					System.out.println("Please enter a valid course code.");
				}
				rs.close();
				st.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("Error interacting with database.");
				System.out.println("Terminating program.");
				System.exit(0);
			}
		}
		return null;
	}
	// Driver function for menu option 1
	public static void add(Connection c, Scanner kb)
	{
		System.out.println("****ADD A COURSE****");
		String code = promptNewCode(c, kb);
		System.out.println("Please enter the course title:");
		String courseTitle = IOHelper.getNextString(kb);
		try
		{
			SQLHelper.addCourse(c, code, courseTitle);
			IOHelper.actionSuccessful();
		}
		catch(Exception e)
		{
			System.out.println("Error adding course to database.");
			System.out.println("Returning to the main menu...");
			IOHelper.pause(4000);
		}
	}
	
	public static void delete(Connection c, Scanner kb)
	{
		System.out.println("****DELETE COURSE****");
		String code = promptExistingCode(c, kb);
		try
		{
			SQLHelper.deleteCourse(c, code);
			IOHelper.actionSuccessful();
		}
		catch(Exception e)
		{
			System.out.println("Error deleting course from database.");
			System.out.println("Returning the the main menu...");
			IOHelper.pause(4000);
		}
	}
}
