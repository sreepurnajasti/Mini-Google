package searchengine.dictionary;

/**  
 * 
 * Copyright: Copyright (c) 2004 Carnegie Mellon University
 * 
 * This program is part of an implementation for the PARKR project which is 
 * about developing a search engine using efficient Datastructures.
 * 
 * Modified by Mahender K on 04-10-2009
 */ 



//import java.io.*;


public class DictionaryDriver 
{
	String type  = "";
	DictionaryInterface di;
	public DictionaryDriver(String str)
	{
		type = str;
		if(type.equals("hash")) {
			di = new HashDictionary();
		}
	}


	public void test() throws Exception
	{
		// hash - Dictionary Structure based on a Hashtable or HashMap from the Java collections 
		// list - Dictionary Structure based on Linked List 
		// myhash - Dictionary Structure based on a Hashtable implemented by the students
		// bst - Dictionary Structure based on a Binary Search Tree implemented by the students
		// avl - Dictionary Structure based on AVL Tree implemented by the students


		
			di = new BSTDictionary();
		
		
	}
	public void insert(String key , String url) {
		//di.insert(key,url);
	}
	public void print() {
		di.print();
	}


	public static void main(String args[]) throws Exception
	{
		if(args.length<1)
			System.out.println("Usage java DictionaryDriver <type of Dictionary>");
		else if(args[0].equals("bst"))
		{
			DictionaryDriver dd = new DictionaryDriver(args[0]);
			dd.test();
		}
		else
		{
			System.out.println("Invalid type entered");
		}
	}
}
