import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

// Class that houses student-related functions
public class Student 
{
	// Prompts user to enter a unique student ssn that is currently used in the table
	public static int promptExistingSSN(Connection c, Scanner kb)
	{
		System.out.println("Please enter an existing SSN (no dashes):");
		int ssn;
		boolean valid = false;
		while (!valid)
		{
			ssn = IOHelper.getNextInt(kb, "Invalid SSN. Please enter a numeric value.");
			try
			{
				Statement st=c.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM Student WHERE ssn=" + ssn);
				boolean empty = SQLHelper.rsIsEmpty(rs);
				if (!empty)
				{
					return ssn;
				}
				else
				{
					System.out.println("Invalid student SSN; ssn not in use.");
					System.out.println("Please enter a valid student SSN.");
				}
				rs.close();
				st.close();
			}
			catch(Exception e)
			{
				System.out.println("Error interacting with database.");
				System.out.println("Terminating program.");
				System.exit(0);
			}
		}
		return -1;
	}
	
	// Prompts user to enter a unique student ssn that is not in use
	public static int promptNewSSN(Connection c, Scanner kb)
	{
		System.out.println("Please enter a new student SSN (no dashes):");
		int ssn;
		boolean valid = false;
		while (!valid)
		{
			ssn = IOHelper.getNextInt(kb, "Invalid SSN. Please enter a numeric value.");
			try
			{
				Statement st=c.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM Student WHERE ssn=" + ssn);
				boolean empty = SQLHelper.rsIsEmpty(rs);
				if (empty && ssn <= 999999999 && ssn > 0)
				{
					return ssn;
				}
				else if (!empty)
				{
					System.out.println("Invalid student SSN; SSN already in use.");
					System.out.println("Please enter a valid student SSN.");
				}
				else if (ssn > 999999999 || ssn < 0)
				{
					System.out.println("Invalid student SSN; number entered exists outside of SSN range.");
					System.out.println("Please enter a student SSN.");
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
		return -1;
	}
	
	public static void add(Connection c, Scanner kb)
	{
		// Attributes
		int ssn;
		String name;
		String address;
		String major;
		
		// Prompt user for data
		System.out.println("****ADD NEW STUDENT****");
		ssn = promptNewSSN(c, kb);
		System.out.println("Please enter the student's name:");
		name = IOHelper.getNextString(kb);
		System.out.println("Please enter the student's address:");
		address = IOHelper.getNextString(kb);
		System.out.println("Please enter the student's major:");
		major = IOHelper.getNextString(kb);
		
		// Funnel data into db
		try
		{
			SQLHelper.addStudent(c, ssn, name, address, major);
			IOHelper.actionSuccessful();
		}
		catch(Exception e)
		{
			System.out.println("Error adding student to the database.");
			System.out.println("Returning the the main menu...");
			IOHelper.pause(4000);
		}
	}
	
	public static void delete(Connection c, Scanner kb)
	{
		int ssn;
		System.out.println("****DELETE EXISTING STUDENT****");
		ssn = promptExistingSSN(c, kb);
		try
		{
			SQLHelper.deleteStudent(c, ssn);
			IOHelper.actionSuccessful();
		}
		catch(Exception e)
		{
			System.out.println("Error removing student from the database.");
			System.out.println("Returning to the main menu...");
			IOHelper.pause(4000);
		}
		
	}
}
