package searchengine.dictionary;
public class Link {
	String name;
	int freq;
	public Link(){
		name ="";
		freq = 0;
	}
	Link(String name, int freq) {
		this.name = name;
		this.freq = freq;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setFreq(int n) {
		freq = n;
	}
	public int getFreq() {
		return freq;
	}
}