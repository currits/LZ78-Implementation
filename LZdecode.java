
///Name: Kurtis-Rae Mokaraka
///ID: 1256115
///Name: Ethyn Gillies
///ID: 1503149
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LZdecode {

    public static void main(String[] args) throws IOException {

        // Dictionary to build and read to
        ArrayList<String> dictionary = new ArrayList<String>();

        // IO
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedOutputStream writer = new BufferedOutputStream(System.out);

        String line;

        // While there is data left to read
        while ((line = reader.readLine()) != null) {

            // If the line is ever empty, we have finished
            if (line == "") {
                break;
            }

            // This splits the lz78 line into the phrase number and mismatched character
            String[] strings = line.split("\\s+");

            // Splitting into int for phrase and char for character
            int phrase = Integer.parseInt(strings[0]);

            // If the mismatch is -1, we are at the end of the file and just need to output
            // the phrase number
            if (strings[1].equals(Integer.toString(-1))) {
                write(writer, dictionary.get(phrase - 1));
                break;
            }

            // Converting the decimal representation of the hex digit to an actual hex digit
            String mismatch = Integer.toHexString(Integer.parseInt(strings[1]));

            // If the phrase is 0, we can just add it and add the value to the dictionary
            if (phrase == 0) {
                dictionary.add(mismatch);
                write(writer, mismatch);
            } else {
                // First we write the entry in the dictionary the phrase number points to then
                // the mismatch
                write(writer, dictionary.get(phrase - 1));
                write(writer, mismatch);

                // Then we add the mismatch to the dictionary along with the phrase before it
                dictionary.add(dictionary.get(phrase - 1) + mismatch);
            }
        }
        writer.close();
        reader.close();
    }

    /**
     * Writes the entirety of a string to the output stream a byte at a time,
     * parsing as hex digits
     * 
     * @param writer The output stream to write to
     * @param s      The string we are writing
     * @throws IOException
     */
    private static void write(BufferedOutputStream writer, String s) throws IOException {

        // Splits the string into an array of strings, each with a single character
        String[] array = s.split("");

        // Writes each character to the output stream parsed as a hex digit
        for (String string : array) {
            writer.write(Integer.parseInt(string, 16));
        }
    }
}
