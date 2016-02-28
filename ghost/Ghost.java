package ghost;

import java.io.IOException;
import java.util.Random;


public class Ghost {
	
	private static final int BLUFF_PERCENT = 30;
	
	// using a smaller word set from
	// https://raw.githubusercontent.com/first20hours/google-10000-english/master/20k.txt
	// exclude small words - otherwise it is not interesting to play
	private static final String SMALL_WORD_SET = "./resources/20k.txt";
	private static final String LARGE_WORD_SET = "./resources/web2.txt";
	
	public static void main(String [] args) {
		Ghost ghost  = new Ghost();
		
		try {
			ghost.init();
		} catch (IOException e) {
			System.out.println("Failed to load. Exiting.");
			return;
		}

		System.out.println("Warning! To make it more interesting, I pretend that words with less than " + 
				DictionaryLoader.MIN_WORD_LENGTH + " characters don't exist");
		do {
			ghost.play();
			System.out.println("Do you want to play again? Please 'q' to exit and any key to play");
			System.out.print(">");
		} while(!System.console().readLine().equals("q"));
	}
	
	private Trie dictionary;
	
	public void init() throws IOException {
		dictionary = DictionaryLoader.loadDictionary(SMALL_WORD_SET);
	}

	public void play() {
		String currentWord = new String("" + getRandomLetter());
		System.out.println("Let's start. I go first. My first letter is " + currentWord);
		System.out.print(">");
		
		while (true) {
			String input = System.console().readLine();
			
			if (input.equals("challenge")) {
				if (dictionary.isCompleteWord(currentWord)) {
					System.out.println("This is indeed a complete word. You won!");
				} else if (!dictionary.isValidPrefix(currentWord)) {
					System.out.println("I bluffed. You won!");
				} else {
					// provide the word
					String word = getWordForPrefix(currentWord);
					System.out.println("Here you go: " + word + " I won!");
				}
				break;
			} else {
				currentWord += input.charAt(0);
				if (dictionary.isCompleteWord(currentWord)) {
					System.out.println("Word " + currentWord + " exists. I won!");
					break;
				} else if (!dictionary.isValidPrefix(currentWord)) {
					//challenge
					System.out.println("I doubt that word starting with " + currentWord + " exists. Enter it:");
					System.out.print(">");
					input = System.console().readLine();
					if (input.isEmpty()) {
						System.out.println("Told ya! I won!");
					} else {
						System.out.println("OK, looks like you won. :(");
					}
					break;
				} else {
					// keep going - it is not a complete word but it is a valid prefix
					// add letter
					if (shouldBluff()) {
						currentWord += getRandomLetter();
					} else {
						currentWord += getNextLetter(currentWord);
					}
					System.out.println("Current word is: " + currentWord);
					System.out.println("Your turn!");
					System.out.print(">");
				}
			}
		}
	}
	
	/**
	 * Returns one of the possible full words given a prefix.
	 * Method assumes that such word exists 'cause the isValidPrefix(prefix) is checked before 
	 * @param prefix prefix
	 * @return full word
	 */
	private String getWordForPrefix(String prefix) {
		while (!dictionary.isCompleteWord(prefix)) {
			TrieNode node = dictionary.getNodeForPrefix(prefix);
			prefix += node.getChildren().keySet().iterator().next();
		}
		return prefix;
	}

	// returns a random number 0 to max
	private int getRandom(int max) {
		return new Random().nextInt(max + 1);
	}
	
	// returns a random alphabet letter
	private char getRandomLetter() {
		return (char) ('a' + getRandom(25));
	}
	
	// returns whether computer would want to bluff at random with preset probability
	private boolean shouldBluff() {
		return getRandom(100) < BLUFF_PERCENT;
	}
	
	// returns the next character which preferably won't complete the word if possible
	// assumes that the prefix is not a complete word (if it is then we have already won)
	// and that this is a valid prefix (if not then we will challenge the player)
	private Character getNextLetter(String prefix) {
		// go over children and pick the one which doesn't complete the word (preferably)
		Character c = null;
		for (Character cur : dictionary.getNodeForPrefix(prefix).getChildren().keySet()) {
			if (!dictionary.isCompleteWord(prefix + cur)) {
				c = cur;
				break;
			}
		}
		
		if (c == null) {
			// we couldn't find the character which doesn't complete the word. Pick the first one
			// and hope that the player won't catch it
			c = dictionary.getNodeForPrefix(prefix).getChildren().keySet().iterator().next();
		}
		return c;
	}
}
