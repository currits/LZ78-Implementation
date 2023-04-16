public class TrieList {

    private TrieNode head;

    public TrieList(){
        head = null;
    }

    //sorting of values in list, probaly best done here
	public void insert(int i){
		
		//create new node from passed int
		TrieNode newNode = new TrieNode(i);
		
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
				System.out.println(current.getValue());
				//then move loop along
				current = current.getNext();
			}
		}
	}

}
