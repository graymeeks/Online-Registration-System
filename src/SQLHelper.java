import java.sql.*;

// Helper class for SQL related functionality
public class SQLHelper 
{
	
	// Closes a given sql connection
	public static void close(Connection c)
	{
		try
		{
			c.close();
		}
		catch (Exception e)
		{
			System.out.println("Error closing sql connection.");
		}
	}
	
	// Prints a given result set
	public static void printResultSet(ResultSet rs)
	{
		try
		{
			ResultSetMetaData rsmd = rs.getMetaData();
			int col = rsmd.getColumnCount();
			while (rs.next())
			{
				for (int i=1; i<=col; i++)
				{
					if (i > 1)
					{
						System.out.print(", ");
					}
					String value = rs.getString(i);
					System.out.print(value + " " + rsmd.getColumnName(i));
				}
				System.out.println("");
			}
		}
		catch(Exception e)
		{
			System.out.println("Error printing result set.");
			e.printStackTrace();
		}
	}
	
	// Returns a boolean value indicating whether or
	// not the given result set is empty.
	public static boolean rsIsEmpty(ResultSet rs)
	{
		try
		{
			boolean ans = true;
			while (rs.next())
			{
				ans = false;
			}
			return ans;
		}
		catch(Exception e)
		{
			System.out.println("rsIsEmpty error");
			return false;
		}
	}
	
	// Adds a course to the database
	public static void addCourse(Connection c, String code, String title) throws SQLException
	{
		Statement stmt = c.createStatement();
		String sql = "INSERT INTO Course " + "VALUES ('"+ code + "', '" + title + "')";
		stmt.executeUpdate(sql);
		stmt.close();
		return;
	}
	
	// Deletes the course with pk 'code' from the database given using Connection 'c'
	public static void deleteCourse(Connection c, String code) throws SQLException
	{
		Statement stmt = c.createStatement();
		String sql = "DELETE FROM Course " + "WHERE code='" + code + "'";
		stmt.executeUpdate(sql);
		stmt.close();
		return;
	}
	
	// Adds a given student to the database
	public static void addStudent(Connection c, int ssn, String name, String address, String major) throws SQLException
	{
		Statement stmt = c.createStatement();
		String sql = "INSERT INTO Student " + "VALUES (" + ssn + ", '" + name + "', '" + address + "', '" + major + "')";
		stmt.executeUpdate(sql);
		stmt.close();
		return;
	}
	
	// Deletes a given student from the database
	public static void deleteStudent(Connection c, int ssn) throws SQLException
	{
		Statement stmt = c.createStatement();
		String sql = "DELETE FROM Student WHERE ssn=" + ssn;
		stmt.executeUpdate(sql);
		stmt.close();
		return;
	}
	
	// Register a student to a course given the key fields
	public static void addRegistered(Connection c, int ssn, String code, int year, String semester) throws SQLException
	{
		Statement stmt = c.createStatement();
		String sql = "INSERT INTO Registered(ssn,code,year,semester) " + "VALUES (" + ssn + ", '" + code + "', " + year + ", '" + semester + "')";
		stmt.executeUpdate(sql);
		stmt.close();
		return;
	}
	
	// Drops a registration record given the key fields of the record
	public static void dropRegistered(Connection c, int ssn, String code, int year, String semester) throws SQLException
	{
		Statement stmt = c.createStatement();
		String sql = "DELETE FROM Registered WHERE ssn=" + ssn + " and code='" + code + "' and year=" + year + " and semester='" + semester + "'";
		stmt.executeUpdate(sql);
		stmt.close();
		return;
	}
	
	// Returns the name of a student given a valid ssn
	public static String getStudentName(Connection c, int ssn) throws SQLException
	{
		Statement stmt = c.createStatement();
		String sql = "SELECT name FROM Student WHERE ssn=" + ssn;
		ResultSet rs =stmt.executeQuery(sql);
		String ans = null;
		while (rs.next())
		{
			ans = rs.getString("name");
		}
		return ans;
	}
	
	// Assigns a specified letter grade to a registration record
	// whose primary key attributes are provided.
	public static void assignGrade(Connection c, int ssn, String code, int year, String semester, char grade) throws SQLException
	{
		Statement stmt = c.createStatement();
	    String sql = "UPDATE Registered " + 
	    			 "SET grade = '" + grade + "' " +
	    			 "WHERE ssn=" + ssn + " and code='" + code + "' and year=" + year + " and semester='" + semester + "'";
	    stmt.executeUpdate(sql);
	    stmt.close();
	}
	
}
