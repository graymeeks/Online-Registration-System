import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

// Functions that deal with manipulating
// the "Registered" record of the database
// along with its corresponding menu
// options.
public class Registered

{
	// Continually prompts user for an new registration record. Upon receiving
	// a unique code/ssn combination that is not in use, this function returns
	// a Registration object containing those two attributes.
	public static Registration promptNewRegistration(Connection c, Scanner kb)
	{
		// Attributes
		int ssn;
		String code;
		int year;
		String semester;
		
		// Continually prompt for valid, new record
		boolean valid = false;
		while(!valid)
		{
			// Prompt user for data
			ssn = Student.promptExistingSSN(c, kb);
			code = Course.promptExistingCode(c, kb);
			System.out.println("Please enter the year corresponding with this registration:");
			year = IOHelper.getNextInt(kb, "Invalid year; please enter a numeric value.");
			System.out.println("Please enter the semester corresponding with this registration:");
			semester = IOHelper.getNextString(kb);
			// Execute lookup
			try
			{
				Statement st=c.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM Registered WHERE ssn=" + ssn + " and code='" + code + "' and year=" + year + " and semester='" + semester + "'");
				boolean empty = SQLHelper.rsIsEmpty(rs);
				if (empty)
				{
					return new Registration(ssn, code, year, semester);
				}
				else
				{
					System.out.println("Invalid registration input; this registration already exists.");
					System.out.println("Please enter a registration record that is not already stored.\n");
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
		return null;
	}
	
	// Continually prompts user until they enter a ssn/code combo
	// that exists in the database as a 'registered' record. The
	// primary key for said record is returned.
	public static Registration promptExistingRegistration(Connection c, Scanner kb)
	{
		// Attributes
		int ssn;
		String code;
		int year;
		String semester;
		
		// Continually prompt for existing record
		boolean valid = false;
		while (!valid)
		{
			ssn = Student.promptExistingSSN(c, kb);
			code = Course.promptExistingCode(c, kb);
			System.out.println("Please enter the year corresponding with this registration:");
			year = IOHelper.getNextInt(kb, "Invalid year; please enter a numeric value.");
			System.out.println("Please enter the semester corresponding with this registration:");
			semester = IOHelper.getNextString(kb);
			try
			{
				Statement st=c.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM Registered WHERE ssn=" + ssn + " and code='" + code + "' and year=" + year + " and semester='" + semester + "'");
				boolean empty = SQLHelper.rsIsEmpty(rs);
				if (!empty)
				{
					return new Registration(ssn, code, year, semester);
				}
				else
				{
					System.out.println("Invalid registration input; the specified registration does not exist.");
					System.out.println("Please enter data for an existing registration.\n");
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
		return null;
	}
	
	// Driver function for register course menu option
	public static void registerCourse(Connection c, Scanner kb)
	{
		// Prompt user for data
		System.out.println("****COURSE REGISTRATION****");
		Registration r = promptNewRegistration(c, kb);
		
		// Create database record based on input
		try
		{
			SQLHelper.addRegistered(c, r.ssn, r.code, r.year, r.semester);
			IOHelper.actionSuccessful();
		}
		catch(Exception e)
		{
			System.out.println("Error adding record to database.");
			System.out.println("Returning to main menu...");
			IOHelper.pause(4000);
		}
	}
	
	// Driver function for drop course menu option
	public static void dropCourse(Connection c, Scanner kb)
	{		
		// Prompt user for existing registration
		System.out.println("****DROP COURSE****");
		Registration r = promptExistingRegistration(c, kb);
		
		// Make necessary deletion
		try
		{
			SQLHelper.dropRegistered(c, r.ssn, r.code, r.year, r.semester);
			IOHelper.actionSuccessful();
		}
		catch(Exception e)
		{
			System.out.println("Error deleting record from database.");
			System.out.println("Returning to main menu...");
			IOHelper.pause(4000);
		}
	}
	
	public static void checkRegistration(Connection c, Scanner kb)
	{
		System.out.println("****CHECK STUDENT REGISTRATION****");
		int ssn = Student.promptExistingSSN(c, kb);
		try
		{
			Statement a = c.createStatement();
			ResultSet x = a.executeQuery("SELECT name FROM Student WHERE ssn=" + ssn);
			SQLHelper.printResultSet(x);
			System.out.println("");
			x.close();
			a.close();
			Statement b = c.createStatement();
			ResultSet y = b.executeQuery("SELECT * FROM Registered WHERE ssn=" + ssn);
			SQLHelper.printResultSet(y);
			y.close();
			b.close();
			System.out.println("\nPress any button to return to the main menu.");
			String s = IOHelper.getNextString(kb);
		}
		catch(Exception e)
		{
			System.out.println("Error occurred when checking for student registration.");
			System.out.println("Returning to main menu...");
			IOHelper.pause(4000);
		}
	}
}
