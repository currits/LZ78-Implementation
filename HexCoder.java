
///Name:
///ID:
///Name: Ethyn Gillies
///ID: 1503149
import java.io.*;

public class HexCoder {

    public static void main(String[] args) throws IOException {

        // Checking if arg length is correct
        if (args.length == 0) {
            System.out.println("Usage: HexCoder {0|1}");
            System.out.println("0:  Encodes Standard input stream into Hexadecimal");
            System.out.println("1:  Decodes Standard input stream from Hexadecimal into bytes");
            return;
        }

        int param = -1;

        // Checking if paramater is of type int
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
                    // Converting each digit into a string so they can be combined
                    String s1 = Integer.toHexString(digit1);
                    String s2 = Integer.toHexString(digit2);

                    // Parsing the combined digits as a hex byte and writing to output
                    writer.write(Integer.parseInt(s1 + s2, 16));
                } catch (Exception e) {

                    // For some reason, a byte will appear occasionally that does not exist in the
                    // text data and cannot be parsed. We couldnt figure out where it was coming
                    // from, but if skipped, does not change the size of the outputted file. even
                    // when this error appears, the resulting file is identical to the input. Its
                    // almost like the byte is appearing out of thin air!
                    e.printStackTrace();
                }

            }
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
