package ghost;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DictionaryLoader {

	public static final int MIN_WORD_LENGTH = 3;
	
	public static Trie loadDictionary(String file) throws IOException {
		Trie trie = new Trie();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	String word = line.trim().toLowerCase();
		    	if (word.length() < MIN_WORD_LENGTH) {
		    		continue;
		    	}
		        trie.insert(word);
		    }
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		
		return trie;
	}
}
