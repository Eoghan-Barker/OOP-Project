package ie.atu.sw;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.Executors;

public class FileProcessor {

	private Set<String> ignore = new ConcurrentSkipListSet<>();
	private Map<String, String> dictionary = new ConcurrentHashMap<>();
	private Map<String, WordDetail> index = new ConcurrentHashMap<>();

	String commonWordsFile = "./google-1000.txt";
	String dictionaryFile = "./dictionary.csv";

	int lineCounter = 0;

	/**
	 * Create a virtual thread executer which starts a new virtual thread for each
	 * line of text in the input file. It also increments a line counter which is
	 * used to calculate which pages each word appears on.
	 * 
	 * @param ebook
	 */
	public void go(String ebook) {
		try (var es = Executors.newVirtualThreadPerTaskExecutor()) {
			Files.lines(Paths.get(ebook)).forEach(text -> es.execute(() -> processText(text, lineCounter++)));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Parses through each line in the common words file and adds each word to a
	 * list
	 * 
	 * @throws IOException
	 */
	public void buildCommonWords() throws IOException {
		Files.lines(Paths.get(commonWordsFile)).forEach(text -> ignore.add(commonWordsFile));
	}

	/**
	 * Parses through each line in the dictionary file. Split each line at "," and
	 * add the word as a key and the definition as a value to a hashmap
	 * 
	 * @throws IOException
	 */
	public void buildDictionary() throws IOException {
		Files.lines(Paths.get(dictionaryFile)).forEach(text -> {
			String[] spliter = text.split(",");
			dictionary.put(spliter[0].toLowerCase(), spliter[1]);
		});
	}

	/**
	 * Takes in a line of text and a line count and splits the line at each space.
	 * Then takes each word from the split and gets rid of any non-alphabetical
	 * characters. If the word is contained in the dictionary and not in the list of
	 * common words it is added to the index map with the word as the key and a
	 * WordDetail object as the value. Also creates/updates and instance of
	 * WordDetail for each unique word which contains the definition for the word
	 * and the page numbers that the word has appeared on.
	 * 
	 * @param text
	 * @param lineCount
	 */
	public void processText(String text, int lineCount) {
		// Split the text using whitespace regex
		Arrays.stream(text.split("\\s+")).forEach(w -> {

			String word = w.replaceAll("[^a-zA-Z]", "");

			// Only add the word to the index if its not in the list of common words
			if (dictionary.containsKey(word) && !ignore.contains(word)) {

				// If the word has already been added to the index then use the previously
				// created instance
				// If not in index create a new instance of WordDetail
				WordDetail wordInfo = index.get(word);
				if (wordInfo == null) {
					wordInfo = new WordDetail();
				}

				// Divide the line number by 40(amount of lines in a page) which will
				// give a truncated whole number. Add 1 as there is no page 0
				int pageNum = (lineCount / 40) + 1;

				wordInfo.setDefinition(dictionary.get(word));
				wordInfo.setWord(word);
				wordInfo.addPage(pageNum);

				index.put(word, wordInfo);
			}
		});
	}

	/**
	 * Returns the index organised alphabetically as a string The string contains
	 * each word, its definition and the page number that the word appears on. Uses
	 * a comparator with the words of the index.
	 * 
	 * @return
	 */
	public String orderAlphabetically() {
		List<String> outputList = new ArrayList<>();
		String outputString = "";

		Comparator<WordDetail> compare = (a, b) -> {
			return a.getWord().compareTo(b.getWord());
		};

		index.values().stream().sorted(compare).forEachOrdered((v) -> {
			outputList.add(v.toString());
		});

		for (String string : outputList) {
			outputString += string;
		}

		return outputString;
	}
	
	/**
	 * Get the size of the index
	 * @return
	 */
	public int getIndexSize() {
		return index.size();
	}
}
