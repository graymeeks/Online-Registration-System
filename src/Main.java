import java.sql.*;
import java.util.*;

// Driver class for Online Registration System
public class Main 
{
	// Global variables
	public static Scanner keyboard = new Scanner(System.in);
	
	// Prints the main menu in its entirety
	public static void printMainMenu()
	{
		System.out.println("ONLINE REGISTRATION SYSTEM\n");
		System.out.println("****************************");
		System.out.println("*********Main Menu**********");
		System.out.println("****************************\n");
		System.out.println("1. Add a course");
		System.out.println("2. Delete a course");
		System.out.println("3. Add a student");
		System.out.println("4. Delete a student");
		System.out.println("5. Register a course");
		System.out.println("6. Drop a course");
		System.out.println("7. Check student registration");
		System.out.println("8. Upload grades");
		System.out.println("9. Check grade");
		System.out.println("10. Quit");
	}	
	
	// "Clears" the screen
	// 
	// Partially a barbaric solution since there's no
	// truly established way to clear the terminal
	// universally in java.
	public static void clearScreen()
	{
		for (int i=0; i<50; i++)
		{
			System.out.println("");
		}
	}
	

	// Main method
	public static void main(String[] args) 
	{
		// Prompt user to login to sql database
		String sqlUser;
		String sqlPwd;
		String sqlUrl = "jdbc:oracle:thin:@fedora2.uscupstate.edu:1521:xe";
		System.out.println("Please enter the SQL login username:");
		sqlUser = IOHelper.getNextString(keyboard);
		System.out.println("Please enter the SQL login password:");
		sqlPwd = IOHelper.getNextString(keyboard);
		Connection conn = null;
		try
		{
			conn = DriverManager.getConnection(sqlUrl,sqlUser,sqlPwd);
			clearScreen();
		}
		catch(Exception e)
		{
			System.out.println("Error opening the SQL connection.");
			System.out.println("Program terminated.");
			System.exit(0);
		}
		
		// Program (main menu) loop
		boolean program = true;
		while(program)
		{
			printMainMenu();
			int x = IOHelper.getNextInt(keyboard, "Invalid menu selection.");
			switch(x)
			{
			case 1:
				// DONE
				Course.add(conn, keyboard);
				break;
			case 2:
				// DONE
				Course.delete(conn, keyboard);
				break;
			case 3:
				// DONE
				Student.add(conn, keyboard);
				break;
			case 4:
				// DONE
				Student.delete(conn, keyboard);
				break;
			case 5:
				// DONE
				Registered.registerCourse(conn, keyboard);
				break;
			case 6:
				// DONE
				Registered.dropCourse(conn, keyboard);
				break;
			case 7:
				// DONE?
				Registered.checkRegistration(conn, keyboard);
				break;
			case 8:
				Grades.uploadGrades(conn, keyboard);
				break;
			case 9:
				Grades.checkGrade(conn, keyboard);
				break;
			case 10:
				// DONE
				System.out.println("Thank you for using Online Registration System.");
				System.out.println("Program terminated.");
				IOHelper.pause(4000);
				program = false;
				break;
			default:
				// DONE
				System.out.println("Invalid selection. Please make another selection.");
			}
			// Clear screen before restarting main menu
			clearScreen();
		}
		keyboard.close();
		SQLHelper.close(conn);
		return;
	}

}
