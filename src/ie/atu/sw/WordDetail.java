package ie.atu.sw;

import java.util.ArrayList;
import java.util.List;

public class WordDetail {
	String definition;
	List<Integer> pages = new ArrayList<>();
	
	// getters + setters
	public String getDefinition() {
		return definition;
	}
	
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	
	public void setPage(int p) {
		pages.add(p);
	}
	
	boolean isListed() {
		return pages.size() > 0;
	}

}
