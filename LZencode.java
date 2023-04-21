import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class LZencode {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        // Creating the top level list and creating a pointer for the current list we
        // are looking at
        TrieList tList = new TrieList();
        TrieList currList = tList;

        int in;
        // First phrase number is always 1
        int phraseNum = 1;
        // Tracks the last phrase we saw when traversing the trie, default is 0
        int lastPhrase = 0;

        // The current node we are at
        TrieNode currNode;

        while ((in = reader.read()) != -1) {

            // Get the current character
            char c = (char) in;

            // If we cant find the character at this level, it needs to be added
            if ((currNode = currList.find(c)) == null) {

                // Inserting a new trie node at the current level of the trie
                // Also indicating the phrase number and increasing it
                currList.insert(new TrieNode(c, phraseNum++));

                // Writing the LZ78 dictionary to out (in decimal format)
                // The last phrase will always be the phrase of the characters parent, for case
                // 0, the parent is the null character
                writer.write(lastPhrase + " " + (int) c);
                writer.newLine();

                // Resetting last phrase to 0 and the list to the top level list
                lastPhrase = 0;
                currList = tList;
            } else {
                // If we saw the character, we need to traverse down a level to that characters
                // children
                // We also set the last phrase to the phrase of what we just saw for writing
                // later
                lastPhrase = currNode.getPhraseNumber();
                currList = currNode.getDown();
            }
        }

        // Be a tidy kiwi!
        reader.close();
        writer.close();
    }
}
