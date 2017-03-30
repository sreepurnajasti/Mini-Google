/**  
 * 
 * Copyright: Copyright (c) 2004 Carnegie Mellon University
 * 
 * This program is part of an implementation for the PARKR project which is 
 * about developing a search engine using efficient Datastructures.
 * 
 * Modified by Mahender on 12-10-2009
 */ 

package searchengine.indexer;
import java.util.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;
//import java.io.ObjectOutputStream;


import searchengine.dictionary.BSTDictionary;
import searchengine.dictionary.DictionaryInterface;
import searchengine.dictionary.HashDictionary;
import searchengine.dictionary.ObjectIterator;
//import searchengine.element.PageElementInterface;
//import searchengine.element.PageWord;


/**
 * Web-indexing objects.  This class implements the Indexer interface
 * using a list-based index structure.

A Hash Map based implementation of Indexing 

 */
public class Indexer /*implements IndexerInterface*/
{
	/** The constructor for ListWebIndex.
	 */

	// Index Structure 
	DictionaryInterface index;

	// This is for calculating the term frequency
	Hashtable<String,Hashtable<String,Integer>> wordFrequency = new Hashtable<String,Hashtable<String,Integer>>(); 

	public Indexer(String mode)
	{
		// hash - Dictionary Structure based on a Hashtable or HashMap from the Java collections 
		// list - Dictionary Structure based on Linked List 
		// myhash - Dictionary Structure based on a Hashtable implemented by the students
		// bst - Dictionary Structure based on a Binary Search Tree implemented by the students
		// avl - Dictionary Structure based on AVL Tree implemented by the students

		
		if(mode.equals("bst"))
			index = new BSTDictionary();
		
	}

	/** Add the given web page to the index.
	 *
	 * @param url The web page to add to the index
	 * @param keywords The keywords that are in the web page
	 * @param links The hyperlinks that are in the web page
	 */
	public void addPage(URL url, Iterator<String> keywords)	
	{
		while(keywords.hasNext()) {
			Hashtable<String,Integer> temp= new Hashtable<String,Integer>();
			String ele = keywords.next();
			if(wordFrequency.containsKey(ele)) {
				temp = wordFrequency.get(ele);
				if(temp.containsKey(url.toString())) {
					int count = temp.get(url.toString());
					count++;
					temp.put(url.toString(), count);
				}
				else {
					temp.put(url.toString(), 1);
				}
			}
			else {
				temp.put(url.toString(), 1);
			}
			wordFrequency.put(ele, temp);
		}
		Set<String> st = wordFrequency.keySet();
		Hashtable<String , Integer> ht;
		for(String k : st) {
			ht = wordFrequency.get(k);
			index.insert(k , ht);
		}
//		index.print();
	}

	/** Produce a printable representation of the index.
	 *
	 * @return a String representation of the index structure
	 */
	public String toString()
	{
		////////////////////////////////////////////////////////////////////
	    //  Write your Code here as part of Integrating and Running Mini Google assignment
	    //  
	    ///////////////////////////////////////////////////////////////////
		return "You dont need to implement it\n";
	}

	/** Retrieve all of the web pages that contain any of the given keywords.
	 *	
	 * @param keywords The keywords to search on
	 * @return An iterator of the web pages that match.
	 * 
	 * Calculating the Intersection of the pages here itself
	 **/
	public Iterator<Hashtable<String,Integer>> retrievePages(ObjectIterator<String> keywords)
	{
		Vector<Hashtable<String,Integer>> v=new Vector<Hashtable<String,Integer>>();
		while(keywords.hasNext()) {
			String str = keywords.next();
			Hashtable<String,Integer> temp = new Hashtable<String,Integer>();
			temp = index.getValue(str);
			v.addElement(temp);
		}
		Iterator<Hashtable<String,Integer>> i = v.iterator();
		return i;
	}

	/** Save the index to a file.
	 *
	 * @param stream The stream to write the index
	 */
	public void save(FileOutputStream stream) throws IOException
	{
		FileOutputStream fos = stream;
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		Set<String> set = wordFrequency.keySet();
		Iterator<String> it = set.iterator();
		while(it.hasNext()) {
			String str = it.next();
			Hashtable<String,Integer> table = wordFrequency.get(str);
			Set<String> set2 = table.keySet();
			Iterator<String> it2 = set2.iterator();
			bw.write(str + "~");
			while(it2.hasNext()) {
				String url = it2.next();
				int val = table.get(url);
				bw.write(url + "^" + val + "*");
			}
			bw.newLine();
		}
		bw.close();
	}

	/** Restore the index from a file.
	 *
	 * @param stream The stream to read the index
	 */
	public void restore(FileInputStream stream) throws IOException {
		String word = "";
		try {
			String url = "";
			int val = 0;
			Hashtable<String,Integer> table;
			BufferedReader br = new BufferedReader(new InputStreamReader(stream));
			String str = "";
			while((str = br.readLine()) != null) {
				word = str.substring(0, str.indexOf('~'));
				String remain = str.substring(str.indexOf('~')+1, str.length());
				StringTokenizer st = new StringTokenizer(remain , "*");
				table = new Hashtable<String,Integer>();
				while(st.hasMoreTokens()) {
					String temp = st.nextToken();
					url = temp.substring(0, temp.indexOf('^'));
					val = Integer.parseInt(temp.substring(temp.indexOf('^') + 1, temp.length()));
					table.put(url, val);
				}
				index.insert(word, table);
			}
		}
		catch(Exception e) {
			System.out.println(word);
		}
	}

	/* Remove Page method not implemented right now
	 * @see searchengine.indexer#removePage(java.net.URL)
	 */
	public void removePage(URL url) {
	}
};
