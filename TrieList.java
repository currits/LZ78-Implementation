public class TrieList {

    private TrieNode head;

    public TrieList(){
        head = null;
    }

    //sorting of values in list, probaly best done here
	public void insert(TrieNode newNode){
		int i = newNode.getValue();
		//assigns it to the head if the list is empty
		if(isEmpty()){
			head = newNode;
		}
        //check if new node needs to be the head
        else if (head.getValue() > i){
            newNode.setNext(head);
            head = newNode;
        }
		//otherwise, interate through the list and insert the node
        //assuming no value appears in a level more than once
        //assuming ascending value order
		else {
			TrieNode current = head;
            TrieNode next = head.getNext();
            while(true){
                //if list of one node, add newNode
                if (next == null){
                    current.setNext(newNode);
                    break;
                }
                //if newNode fits between current and next, insert here
                else if(next.getValue() < i){
                    newNode.setNext(next);
                    current.setNext(next);
                    break;
                }
                //otherwise, move pointers along
                current = next;
                next = current.getNext();
            }
		}
	}
	
	//Method to check if the list is empty
	//returns true or false
	public boolean isEmpty(){
		//checks if the head value is empty (null)
		//list is empty if no head, not empty otherwise
		if (head == null){
			return true; 
		}
		else {
			return false;
		}
	}
	
	//Method for checking if the list contains the passed integer
	//Returns true or false
	public boolean has(int i){
		
		//first checks if the list is empty, stopping here if it is
		if (isEmpty()){
			return false;
		}
		
		//then creates a node reference to be used to loop through
		TrieNode current = head;
		
		//while loop that runs for as long as the reference pointer hasnt reached the end of the list(is null)
		while(current != null){
			//if the current node has the value, returns true
			if (current.getValue() == i){
				return true;
			}
			//move the pointer along the list
			current = current.getNext();
		}
		//if the end of the list is reached and the value is not found, return false
		return false;
	}

	//method to return a node with the passed phrase
	//to access the node's stores value and phrase number
	//could replace has(method)
	public TrieNode find(int i){
		if (isEmpty() || !has(i))
			return null;

		TrieNode current = head;
		//while loop that runs for as long as the reference pointer hasnt reached the end of the list(is null)
		while(current != null){
			//if the current node has the value, returns true
			if (current.getValue() == i){
				break;
			}
			//move the pointer along the list
			current = current.getNext();
		}
		return current;
		
	}
	
    //can chuck this perhaps
	public int length(){
		//local variable to be returned to the caller
		int counter = 0;
		//reference pointer to loop with
		TrieNode current = head;
		//loop that will run until the end of the list
		while (current != null) {
			//increment the counter
			++counter;
			//move the loop along
			current = current.getNext();
		}
		//returns the counter variable
		// if the list is empty, returns 0 as counter is initialised, avoids using isEmpty method
		return counter;
	}
	
    //can also chuck this, dont think we'll be removing items from the list
	public void remove(int i){
		
		//first checks if the loop is empty, stops if it is
		if(isEmpty() || !has(i)){
			return;
		}
		
		//creates two reference pointers, one for the current node and the node ahead
		TrieNode previous = head;
		TrieNode current = head.getNext();
		
		//lopp that will run until the end of the list
		while (current != null){
			//if the node ahead has the value we want to delete
			if (current.getValue() == i){
				//create a new reference to it
				TrieNode toRemove = current;
				//set the current node's Next to the node beyond the deletion node
				//pushes the deleteion node out of the list
				previous.setNext(toRemove.getNext());
				//then set the 'current' reference node to the new node ahead
				current = toRemove.getNext();
				//return statement to end the loop when the first character is removed
				return;
			}
			//otherwise, moves the loop along
			else {
				previous = previous.getNext();
				current = current.getNext();
			}
		}
		
		//if the node to be deleted is the head, then remove that
		if (head.getValue() == i){
			head = head.getNext();
		}
		
	}
	
	//Method to print the value of each node to the terminal
	//probs only useful for testing
	public void dump(){
		//checks if empty and responds appropriately
		System.out.println("TrieList dump from head:-");
		if (isEmpty()) {
			System.out.println("List is empty");
		}
		//otherwise, does the thing
		else {
			//reference pointer to loop with
			TrieNode current = head;
			//loop that will run until end of the list
			while (current != null){
				//print the current node's value onto a new line
				System.out.println("Value: " + current.getValue() + ", phrase: " + current.getPhraseNumber());
				//then move loop along
				current = current.getNext();
			}
		}
	}

	public TrieNode getHead(){
		return head;
	}

	/**
	 * Ancilliary Methods for traversing a list
	 * Call using the TrieList class instead of on a TrieList object
	 */

	//method to traverse phrase list depth first
	public void depthTraverse(TrieList level){
		//start with the passed list root node
		TrieNode current = level.getHead();
		//loop, using while;true as linkedlist is not array and thus cannot for;each
		while(true){
			//if theres a level, go down it first and call this method
			if (current.getDown() != null)
				depthTraverse(current.getDown());
			//otherwise, shift along until we reach the end of the list
			else if (current.getNext() != null){
				current = current.getNext();
			}
			//when we are at the deepest level, last item, stop looping
			else
				break;
		}
		//do the thing we want to do here, for now just dumping the entire level of the depth we have reached
		level.dump();
	}

	//method to traverse phrase list depth first
	public void widthTraverse(TrieList rootList){
		//trie list to store the heads of each list along a level
		TrieList queueList = new TrieList();
		//node to iterate with
		TrieNode node = rootList.getHead();
		//loop
		while(true){
			//do the thing we want using the current node here, in this case just printing it
			System.out.println("Value: " + node.getValue() + ", phrase: " + node.getPhraseNumber());
			//then check if there is a level descending from this node
			if (node.getDown() != null){
				//store that head if there is
				queueList.insert(node.getDown().getHead());
			}
			//then check if this node has more nodes in it's list
			if(node.getNext() != null){
				//if so, shift and continue looping (to act on the next node)
				node = node.getNext();
			}
			//otherwise, if at the end of a list, pop the head of the queue to start iterating through
			else if(!queueList.isEmpty()){
				node = queueList.getHead();
				queueList.remove(node.getValue());
			}
			//otherwise, if at lowermost level of right most node, we are done so break
			else
				break;
		}
	}

}
