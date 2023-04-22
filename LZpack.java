import java.io.*;
public class LZpack {
    
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: LZpack <maxPhrases>");
            System.out.println("maxPhrases:  The maximum number of phrases that the packer needs to account for");
            return;
        }

        //get number of phrases
        int i = Integer.parseInt(args[0]);
        //calculate the ceiling of bits to encode that many phrases (log base of phrase count)
        int phraseBitCount = (int)Math.ceil((Math.log(i) / Math.log(2)));
        //mismatched phrase is two hex digits, 8 bits
        int mismatchedBitCount = 8;
        //to track which order bit we are packing from next
        int bitPosition = 0;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
            
            String line;
            String tuple[];
            int phrase;
            int mismatch;
            int output = 0;

            while (true){
                System.err.println("Outer loop");
                line = reader.readLine();
                if (line == null){
                    break;
                }

                tuple = line.split("\\s+");
                phrase = Integer.parseInt(tuple[0]);
                mismatch = Integer.parseInt(tuple[1]);

                //write out some number of bytes
                while ((bitPosition / 8) >= 1){
                    System.err.println("Write loop");
                    byte out = (byte) output;
                    writer.write(out);
                    //record until we are out of whole bytes to write
                    bitPosition -= 8;
                    //shift output accordingly
                    output = output >>> 8;
                }
                // this loop will leave at most 7 bits remaining in output that still need to be written
                // this means that there can at most 25 bits available at any given time, excluding first loop iteration
                // so (25bits - 8chars) = 17 bits = 2^17 = 131 072 phrases maximum can be packed using this method
                // if we switch to four bit nibbles, that becomes 2^21 =  2 097 152 maximum phrases

                //get the phrase into bit position
                phrase = phrase << bitPosition;
                //OR it in
                output = output | phrase;
                //shift bit position tracker by the number of bits reserved for phrases
                bitPosition += phraseBitCount;
                //get the mismatches char into position
                mismatch = mismatch << bitPosition;
                //OR it in
                output = output | mismatch;
                //shift bit position tracker by the number of bits reserved for mismatched chars (8)
                bitPosition += mismatchedBitCount;
                //then loop again

                //logic checking here
                //remove after confident in code
                if (bitPosition > 32){
                    System.out.println("Bitpacker issue, bitPos exceeded 32");
                    break;
                }
            }
            System.err.println("Finished");
            writer.close();
            reader.close();
        }
        catch (Exception e){
            System.out.println("Pack error: ");
            e.printStackTrace();
        }

    }
    
}
