package test2;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;


public class Person implements Comparable<Person> {
	
	private String firstName;
	private String lastName;
	private String url;
	private String currentWorkplace;
	private String currentJob;
	private String headerString = null;
	
	public Person(String firstname, String lastname, String url, String workplace, String title)
	{
		this.firstName=firstname;
		this.lastName=lastname;
		this.url=url;
		this.currentWorkplace=workplace;
		this.currentJob=title;
	}
	public Person(String firstname, String lastname, String url, String workHeader)
	{
		this.firstName=firstname;
		this.lastName=lastname;
		this.url=url;
		this.headerString=workHeader;
	}
	@Override
	public String toString()
	{
		if (this.headerString!=null)
		{
			return toStringHeader();
		}
		
		StringBuilder builder = new StringBuilder();
		
		builder.append(fieldStringBuilder(firstName));
		builder.append(fieldStringBuilder(lastName));
		builder.append(this.currentWorkplace + "\t");
		builder.append(this.currentJob + "\t");
		builder.append((url));
		builder.append("\n");
		return builder.toString();
	}
	public String toStringHeader()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(fieldStringBuilder(firstName));
		builder.append(fieldStringBuilder(lastName));
		builder.append((url + "\t"));
		builder.append(this.headerString);
		//builder.append("\n");
		return builder.toString();
	}
	
	private String fieldStringBuilder(String string)
	{
		String result;
		if (string.length()!=0 && !string.equals("null"))
		{
			result = string.substring(2, string.length()-3);
		}
		else
		{
			result = "NA";
		}
		return result + "\t";
	}
	
	@Override
	public int compareTo(Person o) {
		// TODO Auto-generated method stub
		return this.lastName.compareTo(o.lastName);
	}
	
	public void write(String file)
	{
		BufferedWriter bw = null;

	      try {
	         // APPEND MODE SET HERE
	         bw = new BufferedWriter(new FileWriter(file, true));
	         
		 bw.write(this.toString());
		 bw.flush();
	      } catch (IOException ioe) {
		 ioe.printStackTrace();
	      } finally {                       // always close the file
		 if (bw != null) try {
		    bw.close();
		 } catch (IOException ioe2) {
		    // just ignore it
		 }
	      } // end try/catch/finally
	}
	
	public String getFirstName()
	{
		return this.firstName;
	}
	
	public String getLastName()
	{
		return this.lastName;
	}
	
	public String getUrl()
	{
		return this.url;
	}
	
	public String getTitle()
	{
		return this.currentJob;
	}
	public String getWorkplace()
	{
		return this.currentWorkplace;
	}
	public String getHeader()
	{
		return this.headerString;
	}
	
	/*
	public static String[] workplaceSplitter(String workplaceString)
	{
		if (workplaceString.length()==0) return null;
		
		workplaceString = workplaceString.substring(1, workplaceString.length()-1);
		String[] workplaces = workplaceString.split(",");
		for(int i = 0; i<workplaces.length; i++)
		{
			int index = workplaces[i].indexOf(" at ");
			int length = workplaces[i].length();
			
			if (length==0 ) return null;
			
			try{
				workplaces[i]=workplaces[i].substring(index + 4, length-1);
				workplaces[i]=workplaces[i].replace("\"", "");
			}
			catch(Exception e)
			{	
				e.printStackTrace(System.out);
				workplaces[i]=workplaces[i];
			}
			
		}
		return workplaces;
	}
	public static String[] jobSplitter(String workplaceString)
	{
		if (workplaceString.length()==0) return null;
		
		workplaceString = workplaceString.substring(1, workplaceString.length()-1);
		String[] workplaces = workplaceString.split(",");
		for(int i = 0; i<workplaces.length; i++)
		{
			int index = workplaces[i].indexOf(" at ");
			int length = workplaces[i].length();
			
			if (length==0 ) return null;
			
			try{
				workplaces[i]=workplaces[i].substring(1, index);
			}
			catch(Exception e)
			{	
				e.printStackTrace(System.out);
				workplaces[i]=workplaces[i];
			}
		}
		return workplaces;
	}*/
	
	
	
}

	
