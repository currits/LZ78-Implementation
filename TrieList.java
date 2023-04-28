///Name:
///ID:
///Name: Ethyn Gillies
///ID: 1503149
public class TrieList {

	private TrieNode head;

	/**
	 * Defines a list of TrieNodes to form a Trie structure
	 */
	public TrieList() {
		head = null;
	}

	/**
	 * Inserts a new node into the list of nodes
	 * 
	 * @param newNode The TrieNode to add to the list
	 */
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

	/**
	 * Checks if the list is empty
	 * 
	 * @return True if empty, false if not empty
	 */
	public boolean isEmpty() {
		// checks if the head value is empty (null)
		// list is empty if no head, not empty otherwise
		if (head == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if the list contains a certain character
	 * 
	 * @param c The character to search for
	 * @return True if the character is found, false if not
	 */
	public boolean has(char c) {

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
			if (Character.compare(current.getValue(), c) == 0) {
				return true;
			}
			// move the pointer along the list
			current = current.getNext();
		}
		// if the end of the list is reached and the value is not found, return false
		return false;
	}

	/**
	 * Returns the node for which a character is specified, if it exists
	 * 
	 * @param c The character to search for
	 * @return A TrieNode that contains the character specified, null is returned if
	 *         the character is not found
	 */
	public TrieNode find(char c) {
		if (isEmpty() || !has(c))
			return null;

		TrieNode current = head;
		// while loop that runs for as long as the reference pointer hasnt reached the
		// end of the list(is null)
		while (current != null) {
			// if the current node has the value, returns true
			if (Character.compare(current.getValue(), c) == 0) {
				break;
			}
			// move the pointer along the list
			current = current.getNext();
		}
		return current;

	}

	/**
	 * Gets the head of the list
	 * 
	 * @return The head TrieNode of the list
	 */
	public TrieNode getHead() {
		return head;
	}
}
