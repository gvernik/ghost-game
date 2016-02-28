package ghost;

import java.util.Map;

public class Trie {

	private TrieNode root;

	public Trie() {
		root = new TrieNode();
	}

	public void insert(String word) {
		Map<Character, TrieNode> children = root.getChildren();

		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);

			TrieNode node;
			if (children.containsKey(c)) {
				node = children.get(c);
			} else {
				node = new TrieNode(c);
				children.put(c, node);
			}

			children = node.getChildren();

			node.setLeaf(i == word.length() - 1);
		}
	}

	public boolean isCompleteWord(String word) {
		TrieNode t = getNodeForPrefix(word);

		return t != null && t.isLeaf();
	}

	public boolean isValidPrefix(String prefix) {
		return getNodeForPrefix(prefix) != null;
	}

	public TrieNode getNodeForPrefix(String str) {
		Map<Character, TrieNode> children = root.getChildren();
		TrieNode node = null;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (children.containsKey(c)) {
				node = children.get(c);
				children = node.getChildren();
			} else {
				return null;
			}
		}

		return node;
	}
}
