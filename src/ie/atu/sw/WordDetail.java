package ie.atu.sw;

import java.util.ArrayList;
import java.util.List;

public class WordDetail {
	String definition;
	String word;
	List<Integer> pages = new ArrayList<>();
	
	@Override
	public String toString() {		
		return "Word: "+ word + "\n" + "Definition: " + definition + "\n" + "Pages: " + pages + "\n\n";
	}
	
	// getters + setters
	public String getDefinition() {
		return definition;
	}
	
	public String getWord() {
		return word;
	}
	
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
	
	public synchronized void addPage(int p) {
		pages.add(p);
	}
	
	boolean isListed() {
		return pages.size() > 0;
	}

}
