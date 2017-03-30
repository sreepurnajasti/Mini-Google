package searchengine.dictionary;
import java.util.ArrayList;
import searchengine.dictionary.Link;
public class BSTNode {
	private String word;
	private ArrayList<Link> links;
	private BSTNode left;
	private BSTNode right;
	public BSTNode(){
		word = "";
		links = new ArrayList<Link>();
		left = null;
		right = null;
	}
	public BSTNode(String word) {
		this.word = word;
		links = new ArrayList<Link>();
		left = null;
		right = null;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public void addLink(String l , int val) {
		Link url = new Link(l , val);
		links.add(url);
	}
	public void setLeft(BSTNode n){
		left = n;
	}
	public void setRight(BSTNode n) {
		right = n;
	}
	public String getWord() {
		return word;
	}
	public ArrayList<Link> getLinks() {
		return links;
	}
	public BSTNode getLeft() {
		return left;
	}
	public BSTNode getRight(){
		return right;
	}
}