package ie.atu.sw;

/**
 * @author Eoghan Barker
 * @version 1.0
 */

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Runner {

	/**
	 * Presents the user with a menu of options relating to building an index with
	 * an input text file, a dictionary.csv file and a text file of common words. It
	 * always has options for outputing data relating to the index to a user
	 * specified file.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		Scanner scanner = new Scanner(System.in);

		int choice;
		String inputDir = "";
		String outputFile = "";
		String outputText = "";

		FileProcessor fileProcessor = new FileProcessor();

		do {
			System.out.println("************************************************************");
			System.out.println("*       ATU - Dept. Computer Science & Applied Physics     *");
			System.out.println("*                                                          *");
			System.out.println("*              Virtual Threaded Text Indexer               *");
			System.out.println("*                                                          *");
			System.out.println("************************************************************");
			System.out.println("(1) Specify Text File");
			System.out.println("(2) Configure Dictionary");
			System.out.println("(3) Configure Common Words");
			System.out.println("(4) Specify Output File");
			System.out.println("(5) Generate index");
			System.out.println("(6) Output Number of unique words");
			System.out.println("(7) Output List of words in alphabetical order");
			System.out.println("(8) Generate output file & Exit");

			// Output a menu of options and solicit text from the user
			System.out.print("Select Option [1-8]>");
			choice = scanner.nextInt();
			// clear the scanner buffer
			scanner.nextLine();

			switch (choice) {
			case 1:
				// ./GulliversTravelsSwift.txt
				System.out.println("Enter file location:");
				inputDir = scanner.nextLine();

				break;

			case 2:
				// Read in dictionary
				fileProcessor.buildDictionary();
				System.out.println("Dictionary configured");
				break;
			case 3:
				// Read in common words
				fileProcessor.buildCommonWords();
				System.out.println("Common words configured");
				break;
			case 4:
				// Specify output file
				System.out.println("Enter output file name: ");
				outputFile = scanner.nextLine();
				break;
			case 5:
				// Create index
				if (inputDir.isBlank()) {
					System.out.println("Please specify an input file");
				} else {
					fileProcessor.go(inputDir);
					System.out.println("Index Created");
				}

				break;
			case 6:
				// Output num of unique words
				if (outputFile.isBlank()) {
					System.out.println("Please specify an output file");
				} else {
					outputText += "Number of unique words: " + fileProcessor.index.size() + "\n";
					System.out.println("Added to output");
				}

				break;
			case 7:
				// Output words in alphabetical order
				if (outputFile.isBlank()) {
					System.out.println("Please specify an output file");
				} else {
					outputText += fileProcessor.orderAlphabetically();
					System.out.println("Added to output");
				}
				break;
			case 8:
				// Exit and write to file
				FileWriter fileWriter = new FileWriter(new File(outputFile));
				fileWriter.write(outputText);
				fileWriter.close();

				System.out.println("Exiting...");
				break;
			default:
				System.out.println("Please choose a valid option \n");
				break;
			}
		} while (choice != 8);

		scanner.close();
	}
}