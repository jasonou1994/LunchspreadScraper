package test2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class employee {

	public static void main(String[] args) {
		
		
		
		// Open the file
		FileInputStream fstream;
		try {
			fstream = new FileInputStream("/Users/jasonou/Desktop/companyEmployees.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			
			String strLine;
			
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {
			  // Print the content on the console
			  String[] items = strLine.split("\t");
			  for (int i = 0; i < 4; i++)
			  {
				  items[i] = items[i].substring(2,items[i].length()-2);
			  }
			  String result = items[0] + "\t" +items[1] + "\t" +items[2] + "\t" +items[3] + "\n";
			  write(result);
			}

			//Close the input stream
			br.close();
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public static void write(String string)
	{
		String resultsDoc = "/Users/jasonou/Desktop/cleanedEmp.txt";
		BufferedWriter bw = null;
		try {// APPEND MODE SET HERE
			bw = new BufferedWriter(new FileWriter(resultsDoc, true));
	        bw.write(string);
	        bw.flush();
	    } catch (IOException ioe) {
	    	ioe.printStackTrace();
	    } finally {                       // always close the file
	    	if (bw != null) try {
	    		bw.close();
	    	} catch (IOException ioe2) {}
	    } 	
	}
}
