import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class AnagramGenerator {
	final static String DICTIONARY_DIRECTORY = "src/dictionary.csv"; // Directory of dictionary file
	public static TrieNode root;
	public static ArrayList<String> results = new ArrayList<String>(); // All anagrams possible from userWord
	
	// Main method
	public static void main(String[] args)  throws FileNotFoundException {
		// Initialize dictionary
		root = Dictionary.createDictionary(DICTIONARY_DIRECTORY);
		// Accept user input
		acceptInput();
	} // End main
	
	// Accepts user input and parses
	public static void acceptInput() {
		Scanner scnr = new Scanner(System.in);
		String userWord;
		
		// Continuously ask for user input until user decides to quit
		boolean running = true;
		do {
			System.out.println("Enter 29 or less letters or press q to quit");
			userWord = scnr.nextLine();
			
			// Quit if user enters "q"
			if(userWord.replaceAll(" ", "").equals("q")) {
				System.out.println("Quitting program now. Goodbye! (^_^)/~");
				running = false;
			}
			// Make sure word is under character limit
			else if(userWord.length() > 29) {
				System.out.println("More than 29 characters entered. Please re-enter letters");
			}
			// Otherwise, find all anagrams of their word
			else {
				anagramize(userWord);
				
				if(results.size() == 0) {
					System.out.println(userWord + " doesn't have any anagrams! (o_0)");
				}
				else {
					// Remove all duplicate words and print all anagrams
					clearDuplicates();
					System.out.println(userWord + " anagrams are:\n" + results + "\n");
					// Clear all anagrams so next word can be processed
					results.clear();
				}
			}
		} while(running);
		scnr.close();
	} // End acceptInput()
	
	// Produces all anagrams of word and adds them to result
	public static void anagramize(String word) {
		// Searches through all permutations of word to find anagrams
		recursiveSearch(word, root);
		// Sort all results by length in descending order
		Collections.sort(results, Comparator.comparing(String::length));	
		Collections.reverse(results);
	} // End anagramize()
	
	// Searches all permutations of a word against the dictionary
	public static void recursiveSearch(String word, TrieNode currNode) {
		String tempWord;
		int n = word.length();
		
		// Base case
		if(n == 0) {
			if(currNode.isWord) {
				results.add(currNode.content);
			}
			return;
		}
		
		// If the current node contains a word, add it as a result
		if(currNode.isWord) {
			results.add(currNode.content);
		}
		
		// Iterate through each character in current substring
		for(int i = 0; i < word.length(); i++) {
			// Check if currNode's children contains current character
			if(currNode.children.containsKey(word.charAt(i))) {
				// Because current character can be used, remove it from the word
				tempWord = word.substring(0, i) + word.substring(i + 1);
				// Recurse
				recursiveSearch(tempWord, currNode.children.get(word.charAt(i)));
			}
		}
		return;
	} // End recursiveSearch()
	
	// Removes all duplicates from a list of words
	public static void clearDuplicates() {
		ArrayList<String> noDupes = new ArrayList<String>();
		// Iterate through results list
		for(int i = 0; i < results.size(); i++) {
			// If list doesn't already contain current word
			if(!noDupes.contains(results.get(i))) {
				// Add to list
				noDupes.add(results.get(i));
			}
		}
		// New list with no duplicates
		results = noDupes;
	} // End clearDuplicates()
}
