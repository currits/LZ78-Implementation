import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class LZdecode {

    public static void main(String[] args) throws IOException {

        // Dictionary to build and read to
        ArrayList<String> dictionary = new ArrayList<String>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        String line;

        while ((line = reader.readLine()) != null) {

            // If the line is ever empty, we have finished
            if (line == "") {
                break;
            }

            // This splits the lz78 line into the phrase number and mismatched character
            String[] strings = line.split("\\s+");

            // Splitting into int for phrase and char for character
            int phrase = Integer.parseInt(strings[0]);
            char mismatch = (char) Integer.parseInt(strings[1]);
            //if the mismatch if null character, then we have reached end of stream
            if (Character.compare(mismatch, '\0') == 0){
                //so write the phrase and break
                writer.write(dictionary.get(phrase - 1));
                break;
            }
            

            // If the phrase is 0, we can just add it and add the value to the dictionary
            if (phrase == 0) {
                dictionary.add(String.valueOf(mismatch));
                writer.write(mismatch);
            } else {
                // First we write the entry in the dictionary the phrase number points to then
                // the mismatch
                writer.write(dictionary.get(phrase - 1));
                writer.write(mismatch);

                // Then we add the mismatch to the dictionary along with the phrase before it
                dictionary.add(dictionary.get(phrase - 1) + mismatch);
            }
        }
        writer.close();
        reader.close();
    }
}
