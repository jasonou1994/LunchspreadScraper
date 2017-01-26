package test2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Settings {
	
	public static void main(String[] args) throws IOException {
		Settings settings = new Settings("/Users/jasonou/Desktop/scraperSettings.txt");
		System.out.println(settings.toString());
	}
	
	//singleton
	Settings(String settingsLocation) throws IOException{
		try (BufferedReader br = new BufferedReader(new FileReader(settingsLocation)))
		{
			String line;
			while ((line = br.readLine()) != null)
			{
				if (line.contains("companyFolder::")){
					companyFolder = line.split("companyFolder::")[1].trim();
				}
				
				if (line.contains("employeeFolder::")){
					employeeFolder = line.split("employeeFolder::")[1].trim();
				}
				
				if (line.contains("log::")){
					log = line.split("log::")[1].trim();
				}
				
				if (line.contains("seedUrl::")){
					seedUrl = line.split("seedUrl::")[1].trim();
				}
				
				if (line.contains("comment::")){
					comment = line.split("comment::")[1].trim();
				}
				
				if (line.contains("cookie::")){
					cookie = line.split("cookie::")[1].trim();
				}
			}
		}
	}
	
	private static Settings instance = null;
	
	public static Settings instance(String settingsLocation) throws IOException
	{
		if (instance == null)
		{
			instance = new Settings(settingsLocation);
		}
		return instance;
	}
	
	private String companyFolder;
	private String employeeFolder;
	private String log;
	
	private String seedUrl;
	private String comment;
	
	private String cookie;

	public String getCompanyFolder() {
		return companyFolder;
	}

	public String getEmployeeFolder() {
		return employeeFolder;
	}

	public String getLog() {
		return log;
	}

	public String getSeedUrl() {
		return seedUrl;
	}

	public String getComment() {
		return comment;
	}

	public String getCookie() {
		return cookie;
	}
	
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("companyFolder: " + companyFolder+"\n");
		builder.append("employeeFolder: " + employeeFolder+"\n");
		builder.append("log: " + log+"\n");
		builder.append("seedUrl: " + seedUrl+"\n");
		builder.append("comment: " + comment+"\n");
		builder.append("cookie: " + cookie+"\n");
		return builder.toString();
	}
	
	
	
	
	
}
