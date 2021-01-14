import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Dictionary {
	public static String dictionaryDirectory; // Directory of dictionary file
	public static TrieNode root = new TrieNode();
	// Parses dictionary file and creates a hashtable containing all words
	public static TrieNode createDictionary(String dictionaryDirectory) throws FileNotFoundException {
		Scanner file = new Scanner(new File("src/dictionary.csv")); // File containing all dictionary words
		String currWord; // Current word
		
		 //Iterate through dictionary file
		while(file.hasNextLine()) {
			// Get next word
			currWord = file.nextLine();
			insert(currWord);
		}
		
		file.close();
		return root;
	}
	
	public static void insert(String word) {
		TrieNode temp = root;
		for(int i = 0; i < word.length(); i++) {
			if(temp.children.containsKey(word.charAt(i))) {
				temp = temp.children.get(word.charAt(i));
			}
			else {
				temp.children.put(word.charAt(i), new TrieNode());
				temp = temp.children.get(word.charAt(i));
			}
		}
		temp.isWord = true;
		temp.content = word;
	}
}
