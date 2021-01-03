import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Scanner;

public class AnagramGenerator {
	final static String DICTIONARY_DIRECTORY = "src/words_alpha.txt";
	public static Hashtable<String, ArrayList<String>> dictionary = new Hashtable<String, ArrayList<String>>();
	public static ArrayList<String> results = new ArrayList<String>();
	public static ArrayList<String> permutations = new ArrayList<String>();


	public static void main(String[] args) throws FileNotFoundException {
		createDictionary();
		acceptInput();
	}
	
	public static void acceptInput() {
		Scanner scnr = new Scanner(System.in);
		String userWord;
		boolean running = true;
		do {
			System.out.println("Enter word or press q to quit");
			userWord = scnr.nextLine();
			
			if(userWord.replaceAll(" ", "").equals("q")) {
				System.out.println("Quitting program now. Goodbye! (^_^)/~");
				running = false;
			}
			else {
				anagramize(userWord);
				if(results.size() == 0) {
					System.out.println(userWord + " doesn't have any anagrams! (o_0)");
				}
				else {
					System.out.println(userWord + " anagrams are:\n" + results + "\n");
					results.clear();
				}
			}
		} while(running);
		scnr.close();
	}

	public static void anagramize(String word) {
		ArrayList<String> anagrams = new ArrayList<String>();
		String sortedPermutation;
		
		permutations.clear();
		powerSet(word, -1, "");
		
		for(int i = 0; i < permutations.size(); i++) {
			sortedPermutation = sortString(permutations.get(i));
			if(dictionary.get(sortedPermutation) == null) {
				continue;
			}
			else {
				anagrams = dictionary.get(sortedPermutation);
				for(int j = 0; j < anagrams.size(); j++) {
					results.add(anagrams.get(j));
				}
			}
		}
		Collections.sort(results);
	}
	
	static void powerSet(String str, int index, String curr) { 
		int n = str.length(); 

		// base case  
		if (index == n) { 
			return; 
		} 
		
		// First print current subset  
		if(curr.length() != 0) {permutations.add(curr);}

		// Try appending remaining characters  
		// to current subset  
		for (int i = index + 1; i < n; i++)  
		{ 
			curr += str.charAt(i); 
			powerSet(str, i, curr); 

			// Once all subsets beginning with  
			// initial "curr" are printed, remove  
			// last character to consider a different  
			// prefix of subsets.  
			curr = curr.substring(0, curr.length() - 1); 
		} 
		return;
	} 
	
	public static void createDictionary() throws FileNotFoundException {
		Scanner file = new Scanner(new File(DICTIONARY_DIRECTORY));
		String currWord;
		
		while(file.hasNextLine()) {
			currWord = file.nextLine();
			if(dictionary.get(sortString(currWord)) == null) {
				ArrayList<String> list = new ArrayList<String>();
				list.add(currWord);
				dictionary.put(sortString(currWord), list);
			}
			else {
				dictionary.get(sortString(currWord)).add(currWord);
			}
		}
		
		file.close();
	}
	
	public static String sortString(String word) {
		char[] tempArray = word.toCharArray();
		
		Arrays.sort(tempArray);
		return new String(tempArray);
	}
}
