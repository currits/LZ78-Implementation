import java.io.*;
public class HexCoder {
    static int mask = 0xF0;
    public static void main(String[] args){
        if (!(args.length == 0)) {
            System.out.println("Usage: HexCoder {0|1}");
            System.out.println("0:  Encodes Standard input stream into Hexadecimal");
            System.out.println("1:  Decodes Standard input stream from Hexadecimal into bytes");
            return;
        }
        //when back check for if & operation gives 1011 or 00001011 and if that matters
        //is hex hex if its more than four bits?
        //is all this right?
        
        try {
            int param = Integer.parseInt(args[0]);
            if (param == 0){
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
                    int hexIn = 0;
                    int nibble;
                    //implement check for when read returns <8
                    while (hexIn != -1){
                        hexIn = reader.read();
                        nibble = hexIn & mask;
                        writer.write(nibble);
                        writer.newLine();
                        hexIn = hexIn << 4;
                        nibble = hexIn & mask;
                        writer.write(nibble);
                        writer.newLine();
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
            else if (param == 1){
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
                    int byteIn = 0;
                    byte byteOut;
                    int hexIn1;
                    int hexIn2;
                    //implement check for when read returns <8
                    while (byteIn != -1){
                        byteIn = reader.read();
                        hexIn1 = byteIn & mask;
                        byteIn = byteIn << 4;
                        hexIn2 = byteIn & mask;
                        byteOut = (byte) ((hexIn1 << 4) | hexIn2);
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
