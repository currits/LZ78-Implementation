public class TrieNode {

    //next value in current level
    private TrieNode next;
    //each node's next lower level is another whole list
    private TrieList down;
    //since we're dealing in hex nibbles I imagine this needs to be an int
    private int value;

    public TrieNode(int i){
        next = null;
        down = null;
        value = i;
    }

    public int getValue(){
		return value;
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
