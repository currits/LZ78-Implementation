public class TrieListTest {
    public static void main(String[] args) {
        TrieNode testNode1 = new TrieNode('F', 0);
        TrieNode testNode2 = new TrieNode('9', 1);
        testNode1.setNext(testNode2);
        System.out.println("Testing Node class:-");
        System.out.println("testNode1 value: " + testNode1.getValue());
        System.out.println("testNode2 value: " + testNode2.getValue());
        System.out.println("testNode2 getNext(): " + testNode2.getNext());
        testNode1.setNext(testNode2);
        System.out.println("testNode1.setNext(testNode2) check: " + testNode1.getNext().getValue());
        System.out.println("testNode1 getDown(): " + testNode2.getDown());
        TrieList tList = new TrieList();
        char[] testArray = { '5', '4', '3', '2', '1' };
        tList.dump();
        System.out.println("Inserting values 5, 4, 3, 2, 1");
        for (int i = 0; i < 5; i++) {
            tList.insert(new TrieNode(testArray[i], i));
        }
        tList.dump();
    }
    // expected output:
    /**
     * Testing Node class:-
     * testNode1 value: 99
     * testNode2 value: 420
     * testNode2 getNext(): null
     * testNode1.setNext(testNode2) check: 420
     * testNode1 getDown(): null
     * List is empty
     * Inserting values 5, 4, 3, 2, 1
     * Trie List length: 5
     * TrieList dump from head:-
     * 1
     * 2
     * 3
     * 4
     * 5
     */
}
