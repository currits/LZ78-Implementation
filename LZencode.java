
///Name:
///ID:
///Name: Ethyn Gillies
///ID: 1503149
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class LZencode {

    public static void main(String[] args) throws IOException {
        // IO
        BufferedInputStream reader = new BufferedInputStream(System.in);
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

        while (true) {

            // Read a byte
            in = reader.read();

            // if end of stream
            if (in == -1) {
                // check if we have matched phrases so far
                if (lastPhrase != 0) {
                    // if so, write the phrase with null character as mismatch, signifying end of
                    // stream
                    writer.write(lastPhrase + " " + -1);
                    writer.newLine();
                }
                // then stop looping
                break;
            }
            // Get the current character
            // Its okay to use tohexstring here since only one hex digit will ever be read
            // at each step; we dont have to worry about 0 padding here
            String s1 = Integer.toHexString(in);

            // Converting to char type for trie
            char c = s1.charAt(0);

            // If we cant find the character at this level, it needs to be added
            if ((currNode = currList.find(c)) == null) {

                // Inserting a new trie node at the current level of the trie
                // Also indicating the phrase number and increasing it
                currList.insert(new TrieNode(c, phraseNum++));

                // Writing the LZ78 dictionary to out (in decimal format)
                // The lastPhrase will always be the phrase of the characters parent, for case
                // 0, the parent is the null character
                writer.write(lastPhrase + " " + Integer.parseInt(String.valueOf(c), 16));
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
