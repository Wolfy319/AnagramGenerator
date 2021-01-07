import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Scanner;

public class AnagramGenerator {
	final static String DICTIONARY_DIRECTORY = "src/dictionary.csv"; // Directory of dictionary file
	public static Hashtable<String, ArrayList<String>> dictionary = new Hashtable<String, ArrayList<String>>(); // Dictionary
	public static ArrayList<String> results = new ArrayList<String>(); // All anagrams possible from userWord
	
	// Main method
	public static void main(String[] args)  throws FileNotFoundException {
		// Initialize dictionary
		dictionary = Dictionary.createDictionary(DICTIONARY_DIRECTORY);
		
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
			System.out.println("Enter word or press q to quit");
			userWord = scnr.nextLine();
			
			// Quit if user enters "q"
			if(userWord.replaceAll(" ", "").equals("q")) {
				System.out.println("Quitting program now. Goodbye! (^_^)/~");
				running = false;
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
		ArrayList<String> anagrams = new ArrayList<String>();
		ArrayList<String> permutations = new ArrayList<String>();
		String sortedPermutation;
		
		// Make list of all possible letter combinations of word
		powerSet(word, -1, "", permutations);
		
		// Iterate through all permutations and search dictionary
		for(int i = 0; i < permutations.size(); i++) {
			// Alphabetize letters in current permutation
			sortedPermutation = sortString(permutations.get(i));
			// If not in dictionary, go to next permutation
			if(dictionary.get(sortedPermutation) == null) {
				continue;
			}
			// Else add all words that fit current permutation to the results
			else {
				anagrams = dictionary.get(sortedPermutation);
				for(int j = 0; j < anagrams.size(); j++) {
					results.add(anagrams.get(j));
				}
			}
		}
		// Sort all results by length in descending order
		Collections.sort(results, Comparator.comparing(String::length));	
		Collections.reverse(results);
	} // End anagramize()
	
	// Creates a list of all possible letter combinations of a word
	static void powerSet(String str, int index, String curr, ArrayList<String> permutations) { 
		int n = str.length(); 

		// Base case  
		if (index == n) { 
			return; 
		} 
		
		// First add current subset  
		if(curr.length() != 0) {
			permutations.add(curr);
		}

		// Try appending remaining characters  
		// to current subset  
		for (int i = index + 1; i < n; i++)  
		{ 
			curr += str.charAt(i); 
			powerSet(str, i, curr, permutations); 

			// Once all subsets beginning with  
			// initial "curr" are printed, remove  
			// last character to consider a different  
			// prefix of subsets.  
			curr = curr.substring(0, curr.length() - 1); 
		} 
		return;
	} // End powerSet()

	// Sorts letters in a word alphabetically
	public static String sortString(String word) {
		// Convert word to a character array
		char[] tempArray = word.toCharArray();
		// Sort the array
		Arrays.sort(tempArray);
		return new String(tempArray);
	} // End sortString()
	
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
