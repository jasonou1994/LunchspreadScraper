package test2;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;


public class Businesses {

	private ArrayList<Person> company;
	
	public Businesses()
	{
		company=new ArrayList<Person>();
	}
	
	public void addPerson(Person person)
	{
		company.add(person);
	}
	
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		for (int i =0; i<company.size();i++)
		{
			builder.append(company.get(i).toString());
			builder.append("\n");
		}
		return builder.toString();
	}
	
	public void write(String document)
	{
		try (PrintStream out = new PrintStream(new FileOutputStream(document),true)) {
			
			StringBuilder builder = new StringBuilder();
			for (int i =0; i<company.size();i++)
			{
				builder.append(company.get(i).toString());
			}
		    out.print(builder.toString());
		    
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
