import java.util.HashMap;

public class TrieNode {
	public HashMap<Character, TrieNode> children = new HashMap<Character,TrieNode>();
	public String content = "";
	public boolean isWord;
}
