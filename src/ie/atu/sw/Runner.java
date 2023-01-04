package ie.atu.sw;

import java.util.Scanner;

public class Runner {

	public static void main(String[] args) throws Exception {

		Scanner scanner = new Scanner(System.in);
		
		int choice;
		String inputDir = "";
		String outputFile = "";
		
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
			System.out.println("(5) Execute");
			System.out.println("(6) Quit");

			// Output a menu of options and solicit text from the user
			System.out.print("Select Option [1-6]>");
			choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
			case 1:
				// ./GulliversTravelsSwift.txt
				
				System.out.println("Enter file location:");
				inputDir = scanner.nextLine();
				
				break;
				
			case 2:
				// Read in dictionary
				fileProcessor.test();
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
				System.out.println(inputDir);
				if(inputDir.isBlank()) {
					System.out.println("Please specify an input file");
				} else {
					fileProcessor.go(inputDir);
				}
				
				break;
			case 6:
				// Exit
				System.out.println("Exiting...");
				break;
			case 7:
				// Testing
				fileProcessor.test();
			default:
				System.out.println("Please choose a valid option \n");
				break;
			}
		} while (choice != 6);
		
		scanner.close();
	}
}