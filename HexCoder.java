
///Name:
///ID:
///Name: Ethyn Gillies
///ID: 1503149
import java.io.*;

public class HexCoder {

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
        BufferedInputStream reader = new BufferedInputStream(System.in);
        BufferedOutputStream writer = new BufferedOutputStream(System.out);

        // if encoding to hex
        if (param == 0) {
            int digit;

            while ((digit = reader.read()) != -1) {
                // String format into a decimal string, will always have 2 characters
                String hex = String.format("%02X", digit);

                // Spliting the two characters up into hex digits
                String s1 = hex.substring(0, 1);
                String s2 = hex.substring(1);

                // Writing each hex digit as a byte, while the bytes will be 8 bits long, only 4
                // bits are used for each byte since they only will ever take up 4 bits of space
                // It essentially splits the hex in half!
                writer.write(Integer.parseInt(s1, 16));
                writer.write(Integer.parseInt(s2, 16));
            }
        }
        // if decoding to bytes
        else if (param == 1) {
            int digit1;
            int digit2;

            // We can grab both digits as if the first digit is readable, the second always
            // will be
            // This is because we ensured that 2 hex digits were always printed in the
            // encoding step
            while ((digit1 = reader.read()) != -1) {

                digit2 = reader.read();

                try {
                    String s1 = Integer.toHexString(digit1);
                    String s2 = Integer.toHexString(digit2);

                    writer.write(Integer.parseInt(s1 + s2, 16));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            /*
             * StringBuilder sb = new StringBuilder();
             * 
             * String hex = reader.readLine();
             * 
             * for (int i = 0; i < hex.length() - 1; i += 2) {
             * 
             * // Grab the hex in pairs
             * String output = hex.substring(i, (i + 2));
             * // Convert hex to int
             * int decimal = Integer.parseInt(output, 16);
             * // Convert and add to string
             * sb.append((char) decimal);
             * }
             * 
             * writer.write(sb.toString());
             */
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
