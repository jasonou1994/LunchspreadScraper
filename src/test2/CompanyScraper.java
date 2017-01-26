package test2;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import com.jaunt.JNode;
import com.jaunt.NotFound;
import com.jaunt.UserAgent;

public class CompanyScraper {

	public static void main(String[] args) throws Exception {
		 
		 Scraper.scrape("",96,99,"https://www.linkedin.com/vsearch/c?type=companies&orig=FCTD&rsid=5432411131482082483097&pageKey=voltron_company_search_internal_jsp&trkInfo=tarId%3A1481074555776&trk=global_header&search=Search&f_CCR=us%3A70&pt=companies&f_I=80&openFacets=N,CCR,JO,I,CS&f_CS=C&page_num=3");
	}
}
