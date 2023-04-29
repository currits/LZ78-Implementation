///Name: Kurtis-Rae Mokaraka
///ID: 1256115
///Name: Ethyn Gillies
///ID: 1503149
public class TrieNode {

    // Next node in current level
    private TrieNode next;
    // Each node's next lower level is another whole list
    private TrieList down;

    // The character to store
    private char character;
    // The phrase number to store
    private int phraseNum;

    /**
     * Defines a node in the Trie structure
     * 
     * @param c The character to store
     * @param p The phrase number to store
     */
    public TrieNode(char c, int p) {
        next = null;
        down = null;
        character = c;
        phraseNum = p;
        down = new TrieList();
    }

    /**
     * Gets the character
     * 
     * @return The character stored in this node
     */
    public char getValue() {
        return character;
    }

    /**
     * Gets the phrase number
     * 
     * @return The phrase number stored in this node
     */
    public int getPhraseNumber() {
        return phraseNum;
    }

    /**
     * Gets the next node
     * 
     * @return The next node at this level
     */
    public TrieNode getNext() {
        return next;
    }

    /**
     * Sets the next node for this node to the node specified
     * 
     * @param newNext The node to set next to
     */
    public void setNext(TrieNode newNext) {
        this.next = newNext;
    }

    /**
     * Gets the children of this node.
     * 
     * @return The TrieList of children for this node
     */
    public TrieList getDown() {
        return down;
    }

    /**
     * Sets the children to a new TrieList
     * 
     * @param newDown The TrieList to set this nodes children to
     */
    public void setDown(TrieList newDown) {
        down = newDown;
    }

}
