import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Scanner;

public class Dictionary {
	public static String dictionaryDirectory; // Directory of dictionary file

	// Parses dictionary file and creates a hashtable containing all words
	public static Hashtable<String, ArrayList<String>> createDictionary(String dictionaryDirectory) throws FileNotFoundException {
		Hashtable<String, ArrayList<String>> dictionary = new Hashtable<String, ArrayList<String>>(); // Dictionary table
		Scanner file = new Scanner(new File(dictionaryDirectory)); // File containing all dictionary words
		String currWord; // Current word
		
		// Iterate through dictionary file
		while(file.hasNextLine()) {
			// Get next word
			currWord = file.nextLine();
			
			// If the key is null
			if(dictionary.get(sortString(currWord)) == null) {
				// Initialize a list of words for the current key
				ArrayList<String> list = new ArrayList<String>();
				// Add the sorted word to the list and add the list to the dictionary
				list.add(currWord);
				dictionary.put(sortString(currWord), list);
			}
			else {
				// Add the sorted word to the list
				dictionary.get(sortString(currWord)).add(currWord);
			}
		}
		file.close();
		return dictionary;
	}
	
	// Sorts letters in a word alphabetically
	public static String sortString(String word) {
		// Convert word to a character array
		char[] tempArray = word.toCharArray();
		// Sort the array
		Arrays.sort(tempArray);
		return new String(tempArray);
	}
}
