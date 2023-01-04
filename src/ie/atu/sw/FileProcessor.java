package ie.atu.sw;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.Executors;

public class FileProcessor {

	Set<String> ignore = new ConcurrentSkipListSet<>();
	Map<String, String> dictionary = new ConcurrentHashMap<>();
	Map<String, WordDetail> index = new ConcurrentHashMap<>();

	String commonWordsFile = "./google-1000.txt";
	String dictionaryFile = "./dictionary.csv";

	int lineCounter = 0;

	
	public void go(String ebook) {
		try (var es = Executors.newVirtualThreadPerTaskExecutor()) {
			Files.lines(Paths.get(ebook)).forEach(text -> es.execute(() -> processText(text, lineCounter++)));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void buildCommonWords() throws IOException {
		Files.lines(Paths.get(commonWordsFile)).forEach(text -> ignore.add(commonWordsFile));
	}

	public void buildDictionary() throws IOException {
		Files.lines(Paths.get(dictionaryFile)).forEach(text -> {
			var spliter = text.split(",");
			dictionary.put(spliter[0].toLowerCase(), spliter[1]);
		});
	}

	public void processText(String text, int lineCount) {
		// Split the text using whitespace regex
		Arrays.stream(text.split("\\s+")).forEach(w -> {

			String word = w.replaceAll("[^a-zA-Z]", "");

			// Only add the word to the index if its not in the list of common words
			if (!ignore.contains(word)) {
				WordDetail wordInfo = new WordDetail();
				// Divide the line number by 40(amount of lines in a page) which will
				// give a truncated whole number. Add 1 as there is no page 0
				int pageNum = (lineCount / 40) + 1;

				wordInfo.setDefinition(dictionary.get(word));
				wordInfo.setPage(pageNum);
				
				index.put(word, wordInfo);
			}
		});
	}

	public void test() {
		System.out.println(ignore.isEmpty());
		System.out.println(index.isEmpty());
		System.out.println(dictionary.isEmpty());
	}
}
