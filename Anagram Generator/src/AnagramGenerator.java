import java.util.ArrayList;
import java.util.Hashtable;

public class AnagramGenerator {
	final static String DICTIONARY_DIRECTORY = "src/dictionary.csv"; // Directory of dictionary file
	public static TrieNode root;
	public static ArrayList<String> results = new ArrayList<String>(); // All anagrams possible from userWord
	public static Hashtable<Integer, ArrayList<String>> organizedWords = 
			new Hashtable<Integer, ArrayList<String>>(); // Table of word length lists
	public static long time; // Time taken to search for anagrams
	
	// Produces all anagrams of word and adds them to result
	public static void anagramize(String word) {
		results.clear();
		// Searches through all permutations of word to find anagrams
		long start = System.currentTimeMillis();
		recursiveSearch(word, root);
		long end = System.currentTimeMillis();
		
		// Time taken searching
		time = end - start;
		System.out.print(time + "ms");
		
		// Clear all duplicates and format results
		clearDuplicates();
		organizedWords = organizeWords();
		
	}
	
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
	} 
	
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
	} 
	
	// Sorts words into lists of words of the same length
	public static Hashtable<Integer, ArrayList<String>> organizeWords() {
		// Make a table for strings of different lengths
		Hashtable<Integer, ArrayList<String>> lists = new Hashtable<Integer,ArrayList<String>>();
		
		// Iterate through all results
		for(int k = 0; k < results.size(); k++) {
			// If there is already list of words of current word size, add word to list
			if(lists.containsKey(results.get(k).length())) {
				lists.get(results.get(k).length()).add(results.get(k));
			}
			// Otherwise initiate a list and add word
			else {
				lists.put(results.get(k).length(), new ArrayList<String>());
				lists.get(results.get(k).length()).add(results.get(k));
			}
		}
		return lists;
	} 
}
