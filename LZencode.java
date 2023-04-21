import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class LZencode {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        TrieList tList = new TrieList();
        TrieList currList = tList;

        int in;
        int phraseNum = 1;
        int lastPhrase = 0;
        TrieNode currNode;

        while ((in = reader.read()) != -1) {

            char c = (char) in;

            if ((currNode = currList.find(c)) == null) {

                currList.insert(new TrieNode(c, phraseNum++));
                writer.write(lastPhrase + " " + (int) c);
                writer.newLine();

                lastPhrase = 0;
                currList = tList;
            } else {
                lastPhrase = currNode.getPhraseNumber();
                currList = currNode.getDown();
            }
        }
        reader.close();
        writer.close();
    }
}
