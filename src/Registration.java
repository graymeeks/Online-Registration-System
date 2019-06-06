// Binds multiple database attributes together into
// a single object that can be referenced as
// the key for a "Registered" database record.
public class Registration 
{
	int ssn;
	String code;
	int year;
	String semester;
	
	public Registration(int ssn, String code, int year, String semester)
	{
		this.ssn = ssn;
		this.code = code;
		this.year = year;
		this.semester = semester;
	}
}
