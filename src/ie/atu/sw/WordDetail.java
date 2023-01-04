package ie.atu.sw;

import java.util.ArrayList;
import java.util.List;

public class WordDetail {
	String definition;
	String word;
	List<Integer> pages = new ArrayList<>();

	/**
	 * Returns the object as a string with the format: "Word: word" "Definition:
	 * definition" "Pages: list of page numbers"
	 */
	@Override
	public String toString() {
		return "Word: " + word + "\n" + "Definition: " + definition + "\n" + "Pages: " + pages + "\n\n";
	}

	// getters + setters
	/**
	 * Get the definition
	 * 
	 * @return
	 */
	public String getDefinition() {
		return definition;
	}

	/**
	 * Get the word
	 * 
	 * @return
	 */
	public String getWord() {
		return word;
	}

	/**
	 * Set the definition
	 * 
	 * @param definition
	 */
	public void setDefinition(String definition) {
		this.definition = definition;
	}

	/**
	 * Set the word
	 * 
	 * @param word
	 */
	public void setWord(String word) {
		this.word = word;
	}

	/**
	 * Concurrently add a page number to the pages list
	 * 
	 * @param p
	 */
	public synchronized void addPage(int p) {
		pages.add(p);
	}
}
