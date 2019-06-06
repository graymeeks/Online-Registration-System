import java.util.Scanner;

// Helper class to make frequent IO
// functionality publicly available.
public class IOHelper 
{
	// Pauses the program for x milliseconds
	public static void pause(int x)
	{
		try
		{
			Thread.sleep(x);
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
	}
	
	// Prints action success message plus pauses
	// before the assumed return to main menu.
	public static void actionSuccessful()
	{
		System.out.println("Action successful. Returning to the main menu...");
		pause(2000);
		return;
	}
	
	// Returns the next entered integer from the
	// given 'keyboard' Scanner object. Continues
	// to print invalid message until valid input
	// is provided. If invalid input is given,
	// the function will display the provided message.
	public static int getNextInt(Scanner kb, String message)
	{
		int ans = -9999;
		boolean valid = false;
		while(!valid)
		{
			try
			{
				String s = kb.nextLine();
				ans = Integer.parseInt(s);
			}
			catch(Exception e)
			{
				System.out.println(message);
			}
			finally
			{
				if (ans != -9999)
				{
					valid = true;
				}
			}
		}
		return ans;
	}
	
	// Returns the next entered double from the
	// given 'keyboard' Scanner object. Continues
	// to print invalid message until valid input
	// is provided. If invalid input is given,
	// the function will display the provided message.
	public static double getNextDouble(Scanner kb, String message)
	{	
		double ans = -9999.0;
		boolean valid = false;
		while(!valid)
		{
			try
			{
				String s = kb.nextLine();
				ans = Double.parseDouble(s);
			}
			catch(Exception e)
			{
				System.out.println(message);
			}
			finally
			{
				if (ans != -9999.0)
				{
					valid = true;
				}
			}
		}
		return ans;
	}
	
	// Returns the next entered String from the
	// given 'keyboard' Scanner object.
	public static String getNextString(Scanner kb)
	{
		String s = kb.nextLine();
		return s;
	}
	
	// Returns the next valid grade entered by the user.
	public static char getNextGrade(Scanner kb)
	{
		boolean valid = false;
		char ans = 'Z';
		while (!valid)
		{
			String s = getNextString(kb);
			// First layer of error checking: user input must be a character
			if ( (s.length() == 1) && (Character.isLetter(s.charAt(0))) )
			{
				// Second layer of error checking: char must be A-F
				ans = Character.toUpperCase(s.charAt(0));
				switch(ans)
				{
					case 'A':
						ans = 'A';
						valid = true;
						break;
					case 'B':
						ans = 'B';
						valid = true;
						break;
					case 'C':
						ans = 'C';
						valid = true;
						break;
					case 'D':
						ans = 'D';
						valid = true;
						break;
					case 'F':
						ans = 'F';
						valid = true;
						break;
					default:
						System.out.println("Invalid grade input. Please enter a valid grade.");
				}
			}
			else
			{
				System.out.println("Invalid grade input. Please enter a valid grade.");
			}
		}
		return ans;
	}
}
