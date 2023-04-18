import java.io.*;
public class HexCoder {
    static int mask = 0x0F;
    public static void main(String[] args){
        // We must decide if we want our byte-to-hex ouput to be raw hex data or written as strings mapping to hex ie 1-F
        // current writes raw hex values seperated by spaces, change the delimiter as needed     
        // and proccesses input as if there is a character (a space) in between each wanted character
        if (args.length == 0) {
            System.out.println("Usage: HexCoder {0|1}");
            System.out.println("0:  Encodes Standard input stream into Hexadecimal");
            System.out.println("1:  Decodes Standard input stream from Hexadecimal into bytes");
            return;
        }
        try {
            int param = Integer.parseInt(args[0]);
            //if encoding to hex
            if (param == 0){
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
                    int hexIn = 0;
                    int nibbleUpper;
                    int nibbleLower;
                    while (true){
                        hexIn = reader.read();
                        if (hexIn == -1)
                            break;
                        nibbleLower = hexIn & mask;
                        //logical shift
                        hexIn = hexIn >>> 4;
                        nibbleUpper = hexIn & mask;
                        writer.write(nibbleUpper);
                        writer.write(nibbleLower);
                    }
                    writer.flush();
                    writer.close();
                    reader.close();
                }
                catch(Exception e){
                    System.out.print("HexCode error: ");
                    e.printStackTrace();
                }
            }
            //if decoding to ascii
            else if (param == 1){
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
                    byte byteOut;
                    int hexIn1 = 0;
                    int hexIn2 = 0;
                    while (true){
                        //reads assuming a space character seperating every hex value
                        hexIn1 = reader.read();
                        //skip the space after each read character
                        hexIn2 = reader.read();
                        if (hexIn2 != -1){
                            //bitwise or to write incoming hex values as a merged byte
                            byteOut = (byte) ((hexIn1 << 4) | hexIn2);
                        }
                            //otherwise, end of stream and only one bit left, so output that
                        else if (hexIn1 != -1){
                            byteOut = (byte)hexIn1;
                        }
                        else{
                            break;
                        }
                        writer.write(byteOut);
                    }
                    writer.flush();
                    writer.close();
                    reader.close();
                }
                catch(Exception e){
                    System.out.print("HexCode error: ");
                    e.printStackTrace();
                }
            }
            else
                return;
        }
        // Otherwise, print an error and return
        catch (Exception e) {
            System.out.println("The parameter entered was not an integer");
            return;
        }
    }
}
