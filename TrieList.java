public class TrieList {

	private TrieNode head;

	public TrieList() {
		head = null;
	}

	// sorting of values in list, probaly best done here
	public void insert(TrieNode newNode) {
		// assigns it to the head if the list is empty
		if (isEmpty()) {
			head = newNode;
		} else {
			TrieNode current = head;
			TrieNode next = head.getNext();
			// loop to move along to end of list
			while (next != null) {
				current = next;
				next = next.getNext();
			}
			// add new node to end
			current.setNext(newNode);
		}
	}

	// Method to check if the list is empty
	// returns true or false
	public boolean isEmpty() {
		// checks if the head value is empty (null)
		// list is empty if no head, not empty otherwise
		if (head == null) {
			return true;
		} else {
			return false;
		}
	}

	// Method for checking if the list contains the passed integer
	// Returns true or false
	public boolean has(char i) {

		// first checks if the list is empty, stopping here if it is
		if (isEmpty()) {
			return false;
		}

		// then creates a node reference to be used to loop through
		TrieNode current = head;

		// while loop that runs for as long as the reference pointer hasnt reached the
		// end of the list(is null)
		while (current != null) {
			// if the current node has the value, returns true
			if (Character.compare(current.getValue(), i) == 0) {
				return true;
			}
			// move the pointer along the list
			current = current.getNext();
		}
		// if the end of the list is reached and the value is not found, return false
		return false;
	}

	// method to return a node with the passed phrase
	// to access the node's stores value and phrase number
	// could replace has(method)
	public TrieNode find(char i) {
		if (isEmpty() || !has(i))
			return null;

		TrieNode current = head;
		// while loop that runs for as long as the reference pointer hasnt reached the
		// end of the list(is null)
		while (current != null) {
			// if the current node has the value, returns true
			if (Character.compare(current.getValue(), i) == 0) {
				break;
			}
			// move the pointer along the list
			current = current.getNext();
		}
		return current;

	}

	// Method to print the value of each node to the terminal
	// probs only useful for testing
	public void dump() {
		// checks if empty and responds appropriately
		System.out.println("TrieList dump from head:-");
		if (isEmpty()) {
			System.out.println("List is empty");
		}
		// otherwise, does the thing
		else {
			// reference pointer to loop with
			TrieNode current = head;
			// loop that will run until end of the list
			while (current != null) {
				// print the current node's value onto a new line
				System.out.println("Value: " + current.getValue() + ", phrase: " + current.getPhraseNumber());
				// then move loop along
				current = current.getNext();
			}
		}
	}

	public TrieNode getHead() {
		return head;
	}
}
