public class TrieNode {

    //next value in current level
    private TrieNode next;
    //each node's next lower level is another whole list
    private TrieList down;
    //since we're dealing in hex nibbles I imagine this needs to be an int
    private int value;
    //an int to store the phrase number this node holds
    private int phraseNum;

    public TrieNode(int v, int p){
        next = null;
        down = null;
        value = v;
        phraseNum = v;
    }

    public int getValue(){
		return value;
	}
    
    public int getPhraseNumber(){
        return phraseNum;
    }

    public TrieNode getNext(){
        return next;
    }

    public void setNext(TrieNode newNext){
		this.next = newNext;
	}

    public TrieList getDown(){
        return down;
    }
    
}
