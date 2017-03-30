/**  
 * 
 * Copyright: Copyright (c) 2004 Carnegie Mellon University
 * 
 * This program is part of an implementation for the PARKR project which is 
 * about developing a search engine using efficient Datastructures.
 * 
 * Modified by Mahender on 12-10-2009
 */
package searchengine.spider;
import java.util.*;
import java.net.*;

import searchengine.indexer.Indexer;
import searchengine.parser.WebReader;

/** Web-crawling objects.  Instances of this class will crawl a given
 *  web site in breadth-first order.
 */
public class BreadthFirstSpider implements SpiderInterface {

	/** Create a new web spider.
	@param u The URL of the web site to crawl.
	@param i The initial web index object to extend.
	 */

	private Indexer id = null;
	private URL u; 
	public ArrayList<String> words = new ArrayList<String>();

	public BreadthFirstSpider (URL u, Indexer id) {
		this.u = u;
		this.id = id;

	}

	/** Crawl the web, up to a certain number of web pages.
	@param limit The maximum number of pages to crawl.
	 */
	public Indexer crawl (int limit) {
		try {			
			ArrayList<String> urlList = new ArrayList<String>();
			ArrayList<String> temp = new ArrayList<String>();
			WebReader wb = new WebReader(); //class in searchengine.parser
			urlList.add(u.toString());

			for (int i = 0 ; i < temp.size(); i++) {
				if(urlList.indexOf(temp.get(i)) == -1) {
					urlList.add(temp.get(i));
				}
			}
			
			int count = 0;
			while(urlList.size() < limit && count < urlList.size()) {
				u = new URL(urlList.get(count));// converting to url
				temp = wb.linkgetter(u);//searchengine.parser: webreader 
				count++;
				for (int i = 0 ; i < temp.size(); i++) {
					if(urlList.indexOf(temp.get(i)) == -1) {
						urlList.add(temp.get(i));
					}
				}
			}
			
			if(urlList.size() > 0) {
				count = 0;
				Iterator<String> it;
				while(count < limit && count < urlList.size()) {
					u = new URL(urlList.get(count++));
					words = wb.getWords(u);
					it = words.iterator();
					id.addPage(u , it);
					words.clear();
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	/** Crawl the web, up to the default number of web pages.*/
	public Indexer  crawl(){
		// This redirection may effect performance, but its OK !!
		System.out.println("Crawling: "+u.toString());
		return  crawl(crawlLimitDefault);
	}
	/** The maximum number of pages to crawl. */
	public int crawlLimitDefault = 5;

}
