import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Scanner;

public class Dictionary {
	public static String dictionaryDirectory;
	public static Hashtable<String, ArrayList<String>> dictionary = new Hashtable<String, ArrayList<String>>();
	
	public Dictionary(String directory) throws FileNotFoundException {
		Dictionary.dictionaryDirectory = directory;
		createDictionary();
	}

	public static void createDictionary() throws FileNotFoundException {
		Scanner file = new Scanner(new File(dictionaryDirectory));
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
