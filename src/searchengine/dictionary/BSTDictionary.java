package searchengine.dictionary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Hashtable;
import java.util.Set;

public class BSTDictionary implements DictionaryInterface {
	public BSTNode root;
	public String [] keys;
	public int k;
	public int count;
	public BSTDictionary() {
		root = null;
		k = 0;
		count = 0;
	}
	@Override
	public String[] getKeys() {
		// TODO Auto-generated method stub
		keys = new String[count];
		k = 0;
		inorder(root);
		return keys;
	}
	
	public void inorder(BSTNode n) {
		if(n != null) {
			inorder(n.getLeft());
			keys[k++] = n.getWord();
			inorder(n.getRight());
		}
	}
	@Override
	public Hashtable<String , Integer> getValue(String key) {
		// TODO Auto-generated method stub
		ArrayList<Link> array = search(root , key);
		Hashtable<String, Integer> hash = new Hashtable<String, Integer>();
		if(array != null) {
			Iterator<Link> i = array.iterator();
			while (i.hasNext()) {
				Link link = (Link)i.next();
				hash.put(link.getName(), link.getFreq());
			}
		}
		return hash;
	}
	private ArrayList<Link> search(BSTNode root , String value) {
			if(root != null) {
				if(value.equals(root.getWord())) {
					return root.getLinks();
				}
				if((value.compareTo(root.getWord())) < 0) {
					return search(root.getLeft() , value);
				}
				else if((value.compareTo(root.getWord())) > 0) {
					return search(root.getRight() , value);
				}
			}
		return null;
	}
	
	
	@Override
	public void insert(String key, Hashtable<String , Integer> value) {
		// TODO Auto-generated method stub
		root = insert(root, key, value);
	}
	
	public BSTNode insert(BSTNode n, String key, Hashtable<String , Integer> value) {
		if(n == null) {
			n = new BSTNode(key);
			Set<String> set = value.keySet();
			Iterator<String> it = set.iterator();
			while(it.hasNext()) {
				String str = it.next().toString();
				n.addLink(str , value.get(str));
			}
		} else {
			if(n.getWord().compareToIgnoreCase(key) >= 0) {
				n.setLeft(insert(n.getLeft(),key, value));
			} else if(n.getWord().compareToIgnoreCase(key) < 0){
				n.setRight(insert(n.getRight(),key, value));
			} 
		}
		count++;
		return n;
	}
	public void remove(String key) {
		
	}
	public void print() {
		
	}
}
