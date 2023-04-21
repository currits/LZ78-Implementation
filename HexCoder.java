
///Name:
///ID:
///Name: Ethyn Gillies
///ID: 1503149
import java.io.*;

public class HexCoder {
    static int mask = 0x0F;

    public static void main(String[] args) throws IOException {

        if (args.length == 0) {
            System.out.println("Usage: HexCoder {0|1}");
            System.out.println("0:  Encodes Standard input stream into Hexadecimal");
            System.out.println("1:  Decodes Standard input stream from Hexadecimal into bytes");
            return;
        }

        int param = -1;

        try {
            param = Integer.parseInt(args[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // Reader and writer for in and out
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

        // if encoding to hex
        if (param == 0) {
            int c;

            while ((c = reader.read()) != -1) {
                // Regex formatting into hexadecimal
                String hex = String.format("%02x", c);
                writer.write(hex);
            }
        }
        // if decoding to ascii
        else if (param == 1) {

            StringBuilder sb = new StringBuilder();

            String hex = reader.readLine();
            System.out.println(hex);

            for (int i = 0; i < hex.length() - 1; i += 2) {

                // grab the hex in pairs
                String output = hex.substring(i, (i + 2));
                // convert hex to decimal
                int decimal = Integer.parseInt(output, 16);
                // convert the decimal to character
                sb.append((char) decimal);
            }

            writer.write(sb.toString());

            /*
             * int c1 = 0;
             * int c2 = 0;
             * 
             * // We can read a second character inside the while loop since it will always
             * be
             * // non-null due to eacha ascii character needing 2 hex digits
             * try {
             * while ((c1 = reader.read()) != -1) {
             * 
             * if ((c2 = reader.read()) == -1) {
             * continue;
             * }
             * 
             * // Create a string with both characters (we trim it because read() works
             * weird
             * // sometimes
             * String str = ("" + (char) c1 + (char) c2).trim();
             * 
             * // Check if the string is empty, if not, parse it as a character using radix
             * 16
             * // (base16 aka hexadecimal)
             * if (!str.equals("")) {
             * writer.write((char) Integer.parseInt(str, 16));
             * }
             * }
             * } catch (Exception e) {
             * System.out.println(c1);
             * System.out.println(c2);
             * e.printStackTrace();
             * return;
             * }
             */

        } else {
            System.out.println("Usage: HexCoder {0|1}");
            System.out.println("0:  Encodes Standard input stream into Hexadecimal");
            System.out.println("1:  Decodes Standard input stream from Hexadecimal into bytes");
        }

        // Be a tidy kiwi!
        writer.close();
        reader.close();
    }
}
