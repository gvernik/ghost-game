package ghost;

import java.util.HashMap;
import java.util.Map;

class TrieNode {
    private char c;
    private Map<Character, TrieNode> children = new HashMap<>();
    private boolean isLeaf;
 
    public TrieNode() {}
 
    public TrieNode(char c){
        this.c = c;
    }

	public char getChar() {
		return c;
	}

	public void setChar(char c) {
		this.c = c;
	}

	public Map<Character, TrieNode> getChildren() {
		return children;
	}

	public void setChildren(Map<Character, TrieNode> children) {
		this.children = children;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
    
    
}
