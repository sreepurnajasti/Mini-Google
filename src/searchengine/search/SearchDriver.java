/**  
 * 
 * Copyright: Copyright (c) 2004 Carnegie Mellon University
 * 
 * This program is part of an implementation for the PARKR project which is 
 * about developing a search engine using efficient Datastructures.
 * 
 * Modified by Mahender K on 12-10-2009
 */ 


package searchengine.search;


import java.util.*;
import java.io.*;

import searchengine.dictionary.ObjectIterator;
import searchengine.indexer.Indexer;


/**
 * The user interface for the index structure.
 *
 * This class provides a main program that allows users to search a web
 * site for keywords.  It essentially uses the index structure generated
 * by WebIndex or ListWebIndex, depending on parameters, to do this.
 *
 * To run this, type the following:
 *
 *    % java SearchDriver indexfile list|custom keyword1 [keyword2] [keyword3] ...
 *
 * where indexfile is a file containing a saved index and list or custom indicates index structure.
 *
 */
public class SearchDriver
{
	public static Vector<String> array = new Vector<String>();
    public static void main(String [] args)
    {
        Vector<String> v=new Vector<String>();
	
	if(args.length<3)
	    System.out.println("Usage: java SearchDriver indexfile list|hash keyword1 [keyword2] [keyword3] [...]");
	else
	    {
		Indexer w = null;
		
		// Take care to use the right usage of the Index structure
		// hash - Dictionary Structure based on a Hashtable or HashMap from the Java collections 
		// list - Dictionary Structure based on Linked List 
		// myhash - Dictionary Structure based on a Hashtable implemented by the students
		// bst - Dictionary Structure based on a Binary Search Tree implemented by the students
		// avl - Dictionary Structure based on AVL Tree implemented by the students
		if(args[1].equalsIgnoreCase("list") || args[1].equals("hash") || args[1].equals("myhash") || args[1].equals("bst") 
				|| args[1].equals("avl")){
		    w = new Indexer(args[1]);	
		}
		else
		{
			System.out.println("Invalid Indexer mode \n");
		}
		
		try{
		    FileInputStream indexSource=new FileInputStream(args[0]);
		    w.restore(indexSource);
		}
		catch(IOException e){
		    System.out.println(e.toString());
		}
		
		for(int i=2;i<args.length;i++)
		    v.addElement(args[i]);
		
		Iterator<Hashtable<String,Integer>> i= w.retrievePages(new ObjectIterator<String>(v));
		Hashtable<String,Integer> endtable = new Hashtable<String,Integer>();
		Hashtable<String,Integer> temp;		
		Set<String> set;
		Iterator<String> it;
		if(i!=null)
		{
			//creating a a single hastable consisting of all the urls and frequencies
			while(i.hasNext()) {
				temp = i.next();
				set = temp.keySet();
				it = set.iterator();
				while(it.hasNext()) {
					String str = it.next();
					if(!endtable.contains(str))
						endtable.put(str, temp.get(str));
					else {
						int val = temp.get(str);
						int val2 = endtable.get(str);
						val = val+ val2;
						endtable.remove(str);
						endtable.put(str, val);
					}						
				}
			}
			set = endtable.keySet();
			it = set.iterator();
			//adding links into a vector to sort them
			while(it.hasNext()) {
				array.addElement(it.next());
			}
			//sorting the links on basis of ranks
			for(int k = 0 ; k < array.size(); k++) {
				String str1 = array.elementAt(k);
				int depth1 = 0;
				for(int t = 0; t < str1.length();t++) {
					if(str1.charAt(t)=='/') {
						depth1++;
					}
				}
				depth1 = depth1 - 2;
				int val1 = endtable.get(str1);
				double rank1 = ((float)(val1)/(float)(depth1))*100;
				int min = k;
				for(int j = k + 1; j < array.size(); j++) {
					String str2 = array.elementAt(j);
					int depth2 = 0;
					for(int t = 0; t < str2.length();t++) {
						if(str2.charAt(t)=='/') {
							depth2++;
						}
					}
					depth2 = depth2 - 2;
					int val2 = endtable.get(str2);
					double rank2 = ((float)(val2)/(float)(depth2))*100;
					if(rank1 < rank2) {
						min = j;
					}					
				}
				array.set(k, array.get(min));
				array.set(min , str1);
			}
			System.out.println("Search complete.");
			System.out.println("---------------\n");
		}
		else
		{
			System.out.println("Search complete.  0  hits found.");
		}
		for(int k = 0; k < array.size(); k++) {
			System.out.println(array.get(k) + "---->" + endtable.get(array.get(k)));
		}
	    }
    }
}


