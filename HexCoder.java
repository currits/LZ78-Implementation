import java.io.*;

public class HexCoder {
    static int mask = 0x0F;

    public static void main(String[] args) {
        // We must decide if we want our byte-to-hex ouput to be raw hex data or written
        // as strings mapping to hex ie 1-F
        // current writes raw hex values, add delimiter if needed
        // currently proccesses input assuming no delimiter, can be changed to handle
        // delimiter
        if (args.length == 0) {
            System.out.println("Usage: HexCoder {0|1}");
            System.out.println("0:  Encodes Standard input stream into Hexadecimal");
            System.out.println("1:  Decodes Standard input stream from Hexadecimal into bytes");
            return;
        }
        try {
            int param = Integer.parseInt(args[0]);
            // if encoding to hex
            if (param == 0) {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
                    int c;

                    while ((c = reader.read()) != -1) {
                        String hex = String.format("%02x", c);
                        writer.write(hex);
                    }
                    writer.close();
                    reader.close();
                } catch (Exception e) {
                    System.out.print("HexCode error: ");
                    e.printStackTrace();
                }
            }
            // if decoding to ascii
            else if (param == 1) {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
                    byte byteOut;
                    int hexIn1 = 0;
                    int hexIn2 = 0;
                    while (true) {
                        // reads assuming a space character seperating every hex value
                        hexIn1 = reader.read();
                        // skip the space after each read character
                        hexIn2 = reader.read();
                        if (hexIn2 != -1) {
                            // bitwise or to write incoming hex values as a merged byte
                            byteOut = (byte) ((hexIn1 << 4) | hexIn2);
                        }
                        // otherwise, end of stream and only one bit left, so output that
                        else if (hexIn1 != -1) {
                            byteOut = (byte) hexIn1;
                        } else {
                            break;
                        }
                        writer.write(byteOut);
                    }
                    writer.flush();
                    writer.close();
                    reader.close();
                } catch (Exception e) {
                    System.out.print("HexCode error: ");
                    e.printStackTrace();
                }
            } else
                return;
        }
        // Otherwise, print an error and return
        catch (Exception e) {
            System.out.println("The parameter entered was not an integer");
            return;
        }
    }
}
