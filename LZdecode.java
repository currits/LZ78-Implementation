import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class LZdecode {

    public static void main(String[] args) throws IOException {

        ArrayList<String> dictionary = new ArrayList<String>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        String line;

        while ((line = reader.readLine()) != null) {

            if (line == "") {
                break;
            }

            String[] strings = line.split("\\s+");

            int phrase = Integer.parseInt(strings[0]);
            char mismatch = (char) Integer.parseInt(strings[1]);

            if (phrase == 0) {
                dictionary.add(String.valueOf(mismatch));
                writer.write(mismatch);
            } else {
                writer.write(dictionary.get(phrase - 1));
                writer.write(mismatch);

                dictionary.add(dictionary.get(phrase - 1) + mismatch);
            }
        }
        writer.close();
        reader.close();
    }
}
