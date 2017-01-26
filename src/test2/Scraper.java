package test2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.jaunt.JNode;
import com.jaunt.NotFound;
import com.jaunt.UserAgent;

public class Scraper {

	public static void main(String[] args) throws Exception {
		 
		Scraper.scrape("",51,51,"/Users/jasonou/Desktop/scraperSettings.txt");
		//Scraper.scrape("",Integer.parseInt(args[0]),Integer.parseInt(args[1]), args[2]);
	}
	
	public static void scrape(String searchString, int start, int end, String settingsLocation) throws IOException
	{
		Settings settings = new Settings(settingsLocation);
		
		//JSoup Connection objects
		Response res;
		Document doc;
		
		//Jaunt JSON processor
		UserAgent userAgent = new UserAgent();
		
		PageIterator pageIterator = new PageIterator(searchString,start,settings.getSeedUrl());
		
		//request headers
		Hashtable<String,String> headers = new Hashtable<String,String>();
		headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		headers.put("Accept-Encoding", "gzip, deflate, sdch, br");
		headers.put("Accept-Language", "en-US,en;q=0.8");
		headers.put("Avail-Dictionary", "YFA7RCTS");
		headers.put("Connection", "keep-alive");
		headers.put("Cookie", settings.getCookie());
		headers.put("Host", "www.linkedin.com");
		headers.put("Upgrade-Insecure-Requests", "1");
		headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.98 Safari/537.36");
		
		//looping through each page of business results
		for (int i = start; i <= end; i ++)
		{
			int companyCounter = 0, employeeCounter = 0;
			
			//create new .txt results files for the page.
			File companyFile = new File(settings.getCompanyFolder() + "Businesses_" + settings.getComment()+ "_"+ Integer.toString(i));
			File employeeFile = new File(settings.getEmployeeFolder() + "Employees_" + settings.getComment()+ "_"+ Integer.toString(i));
			
			try {
				System.out.println("Crawling page " + pageIterator.getCurrentPage() + " for string: \""+ searchString+ "\"");
				String currentUrl = pageIterator.getURL();
				System.out.println(currentUrl);
				//Log in with Jsoup
				res = Jsoup
				    .connect(currentUrl)
				    .headers(headers)
				    .method(Method.GET)
				    .execute();
				
				//find JSON component of search page containing links to individual companies.
				doc = res.parse();	
				
				String links = doc.select("[id*=voltron_srp_main-content]").toString();
				links = links.substring(64, links.length()-10);
				
				//pass json to jaunt
				
				userAgent.openJSON(links);
				
				JNode companyResults = userAgent.json.getFirst("content ")
						.findFirst("page")
						.findFirst("voltron_unified_search_json")
						.findFirst("search")
						.findFirst("results");
				
				//individual company processing.
				Iterator<JNode> companyUrls = companyResults.findEvery("link_biz_overview_6").iterator();
				while (companyUrls.hasNext())
				{
					//obtaining the url from iterator;
					String url = "https://www.linkedin.com/" + companyUrls.next().toString();
					try {
						if (url.length()==0) throw new IndexOutOfBoundsException("user url broken");
						url = url.replace("\\", "");
					} catch(Exception e){e.printStackTrace(System.err);}
					System.out.println("\t"+url);
					
					try
					{
						res=Jsoup
							.connect(url)
							.headers(headers)
						    .method(Method.GET)
						    .execute();
						
						doc = res.parse();
						
						Element html = doc.getElementById("stream-right-rail-embed-id-content");
						try (PrintStream out = new PrintStream(new FileOutputStream("/Users/jasonou/Desktop/companyJson.html"))) {
						    out.print(doc.toString());
						}
						
						userAgent.openJSON(html.toString());
						
						
						//write company info
						String companyName = userAgent.json.findEvery("companyName").toString();
						companyName = companyName.substring(2,companyName.length()-3);
						String website = userAgent.json.findEvery("website").toString();
						website = website.substring(2,website.length()-3);
						String industry = userAgent.json.findEvery("industry").toString();
						industry = industry.substring(2,industry.length()-3);
						String size = userAgent.json.findEvery("size").toString();
						size = size.substring(2,size.length()-3);
						String selfReportEmployeeCount = userAgent.json.findEvery("employeeCount").toString();
						if (selfReportEmployeeCount.length()>2) selfReportEmployeeCount = selfReportEmployeeCount.substring(1,selfReportEmployeeCount.length()-2);
						String street1 = userAgent.json.findEvery("street1").toString();
						//if (street1.length()>3) street1 = street1.substring(2,street1.length()-3);
						String street2 = userAgent.json.findEvery("street2").toString();
						//if (street2.length()>2)street2 = street2.substring(2,street2.length()-3);
						String zip = userAgent.json.findEvery("zip").toString();
						zip = zip.substring(2,zip.length()-3);
						String country = userAgent.json.findEvery("country").toString();
						country = country.substring(2,country.length()-3);

						String company = 
								companyName.trim() + "\t"
								+website.trim() + "\t"
								+ industry.trim() + "\t"
								+ size.trim() + "\t"
								+ selfReportEmployeeCount.trim() + "\t"
								+ street1.trim() + "\t"
								+ street2.trim() + "\t"
								+ zip.trim() + "\t"
								+ country.trim()+ "\n";
						
						System.out.println("\t"+"\t"+company);
						
						BufferedWriter bw = null;
						try {
							bw = new BufferedWriter(new FileWriter(companyFile, true));
					        bw.write(company);
					        bw.flush();
					    } catch (IOException ioe) {
					    	ioe.printStackTrace();
					    } finally {                       // always close the file
					    	if (bw != null) try {
					    		bw.close();
					    	} catch (IOException ioe2) {}
					    } 
						companyCounter++;
					    
						//individual write employee info
						String string = userAgent.json.findEvery("employees").toString().replace("[", "").replace("]", "").trim();
						String[] strings = string.split("}");
						
						for(int j = 0; j < strings.length; j ++)
						{
							String x = strings[j]+"}";
							userAgent.openJSON(x);
							
							String ln = userAgent.json.findEvery("lastName").toString();
							ln = ln.substring(2, ln.length()-3);
							String fn = userAgent.json.findEvery("firstName").toString();
							fn = fn.substring(2, fn.length()-3);
							String empUrl = userAgent.json.findEvery("url").toString();
							empUrl = empUrl.substring(2, empUrl.length()-3);
							String empCompany = companyName;
							
							String empStr = 
									ln.trim() + "\t"
									+ fn.trim() + "\t"
									+ empUrl.trim() + "\t"
									+ empCompany.trim() + "\n";
							
							//write it...
							BufferedWriter emp = null;
							try {// APPEND MODE SET HERE
								emp = new BufferedWriter(new FileWriter(employeeFile, true));
								emp.write(empStr);
								emp.flush();
						    } catch (IOException ioe) {
						    	ioe.printStackTrace();
						    } finally {                       // always close the file
						    	if (emp != null) try {
						    		emp.close();
						    	} catch (IOException ioe2) {}
						    }
							System.out.println("\t"+"\t"+"\t"+empStr);
							employeeCounter++;
						}
					}
					catch(Exception e1)
					{
						e1.printStackTrace(System.err);
					}
				}
			
			} catch (IOException e) {
				e.printStackTrace();
			
			} catch (NotFound e1) {
				e1.printStackTrace();
				
			} 
			
			BufferedWriter log = null;
			try {// APPEND MODE SET HERE
				log = new BufferedWriter(new FileWriter(settings.getLog(), true));
				String times = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH).format(new Date());
				log.write(settings.getComment()+i+": bus rec " + companyCounter + ", emp rec " + employeeCounter+" "+ times + "\n");
				log.flush();
		    } catch (IOException ioe) {
		    	ioe.printStackTrace();
		    } finally {                       // always close the file
		    	if (log != null) try {
		    		log.close();
		    	} catch (IOException ioe2) {}
		    }
			
		}
	}
}
