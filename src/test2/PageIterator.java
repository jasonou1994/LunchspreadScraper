package test2;

public class PageIterator {
	
	private int currentPage;
	private String searchTerm;
	private String rawUrl;
	
	private int keywordIndex;
	private int pageIndex;
	
	public static void main(String[] args) {
		PageIterator iterator = new PageIterator("",1,"https://www.linkedin.com/vsearch/c?type=companies&orig=FCTD&rsid=5432411131481087887203&pageKey=voltron_company_search_internal_jsp&trkInfo=tarId%3A1481074555776&trk=global_header&search=Search&f_CCR=us%3A70&f_CS=B&pt=companies&f_I=80&openFacets=N,CCR,JO,I,CS&page_num=4");
		System.out.println(iterator.getURL());
		System.out.println(iterator.getURL());
		System.out.println(iterator.getURL());
		System.out.println(iterator.getURL());
		System.out.println(iterator.getURL());
		System.out.println(iterator.getURL());
		System.out.println(iterator.getURL());
		
	}
	
	public PageIterator(String searchTerm, int searchStartPage, String rawUrl)
	{
		this.rawUrl = rawUrl;
		this.currentPage = searchStartPage;
		this.searchTerm = searchTerm;		
	}
	
	public String getURL()
	{
		String url = rawUrl.replaceAll("keywords=[^&].", "keywords="+searchTerm+"&").replaceAll("page_num=[^&]", "page_num="+currentPage+"&");
		if (url.charAt(url.length()-1)=='&') url = url.substring(0, url.length()-1);
		
		currentPage++;
		return url;
	}
	
	public int getCurrentPage()
	{
		return this.currentPage;
	}
}
