/**  
 * 
 * Copyright: Copyright (c) 2004 Carnegie Mellon University
 * 
 * This program is part of an implementation for the PARKR project which is 
 * about developing a search engine using efficient Datastructures.
 * 
 * 
 */ 

package searchengine.parser;
import java.util.ArrayList;
import java.io.*;
import java.net.URL;
import searchengine.element.PageElementInterface;
import searchengine.element.PageHref;
import searchengine.element.PageImg;
import searchengine.element.PageNum;
import searchengine.element.PageWord;
import searchengine.parser.PageLexer;
import searchengine.url.URLTextReader;


/**
 * A simple test program for Webreader assignment
 *
 * This class provides a main program for testing Part 1.  It opens a
 * web page and creates a PageLexer object for it.  It then retrieves
 * and prints out all of the PageElements returned by the PageLexer.
 *
 * To run this program from the command line, type the following:
 *
 *     % java WebReader <url>
 *
 * where <url> is the URL of a web page to read.
 *
 */

public class WebReader {
	public ArrayList<String> words;
	
	public WebReader() {
		words = new ArrayList<String>();
	}
	
	public ArrayList<String> linkgetter(URL url) throws IOException {
		ArrayList<String> urlList = new ArrayList<String>();
	
		if(url != null) {
			URLTextReader in = new URLTextReader(url);
			PageLexer<PageElementInterface> elts = new PageLexer<PageElementInterface>(in, url);
			
			while (elts.hasNext()) {
				PageElementInterface elt = (PageElementInterface)elts.next();
				if(elt instanceof PageHref) {
					urlList.add(elt.toString());
				}
			}
		}
		return urlList;
	}
	public void wordgetter (URL url) {
		try {
			if (url != null) {
				URLTextReader in = new URLTextReader(url);
				PageLexer<PageElementInterface> elts = new PageLexer<PageElementInterface>(in, url);
				int count = 0;
				while (elts.hasNext()) {
					count++;
					PageElementInterface elt = (PageElementInterface)elts.next();
					if (elt instanceof PageWord) {
						words.add(elt.toString());
					}
					else if (elt instanceof PageNum) {						
						words.add(elt.toString());
					}
					else if (elt instanceof PageImg) {
						words.add(elt.toString());
					}
				}
				System.out.println();
				System.out.println(count + " total page elements retrieved.");
			}
			else {
				System.out.println("Usage: WebReader url");
				return;
			}
		}
		catch (IOException e) {
			System.out.println("Bad file or URL specification from web reader");
		}
	}
	public ArrayList<String> getWords(URL url) {
		wordgetter(url);
		return words;
	}
}

