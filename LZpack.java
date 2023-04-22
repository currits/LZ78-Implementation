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
            
            byte[] buffer = new byte[64];
            int bufferIndex = 0;
            String line = "";
            String tuple[];
            int phrase;
            int mismatch;
            int output = 0;

            while (line != null){
                //if the buffer is near full, write it to output
                if (bufferIndex >= 62){
                    for(int l = 0; l <= bufferIndex; l++){
                        writer.write(buffer[l]);
                    }
                    bufferIndex = 0;
                }

                //while bits available is greater than bits needed to pack in a tuple
                while ((32 - bitPosition) >= (phraseBitCount + mismatchedBitCount)){
                    System.err.println("read loop");
                    line = reader.readLine();
                    if (line == null || (line.compareTo("\n") == 0)){
                        break;
                    }
                    tuple = line.split("\\s+");
                    phrase = Integer.parseInt(tuple[0]);
                    mismatch = Integer.parseInt(tuple[1]);

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
                }

                //while there is enough useful bits and buffer is not near full, store some number of chars into buffer
                while (bufferIndex <= 62 && bitPosition >= 8){
                    System.err.println("Write loop");
                    //extract lower order 16 bits (single character)
                    byte out = (byte)output;
                    //write it
                    buffer[bufferIndex] = out;
                    bufferIndex++;
                    //track the bit position
                    bitPosition -= 8;
                    //shift output accordingly
                    output = output >>> 8;
                }
                // this loop will leave at most 7 bits remaining in output that still need to be written
                // this means that there can at most 25 bits available at any given time, excluding first loop iteration
                // so (25bits - 8chars) = 17 bits = 2^17 = 131 072 phrases maximum can be packed using this method
                // if we switch to four bit nibbles, that becomes 2^21 =  2 097 152 maximum phrases

                //logic checking here
                //remove after confident in code
                if (bitPosition > 32){
                    System.out.println("Bitpacker issue, bitPos exceeded 32");
                    break;
                }
            }
            //final write loop repeat to capture remaining bits
            while(bitPosition > 0){
                System.err.println("Write loop");
                //extract lower order 16 bits (single character)
                byte out = (byte)output;
                //write it
                buffer[bufferIndex] = out;
                bufferIndex++;
                //track the bit position
                bitPosition -= 8;
                //shift output accordingly
                output = output >>> 8;
            }
            //write the remaining bytes out after looping
            for (int k = 0; k <= bufferIndex; k++){
                writer.write(buffer[k]);
            }
            
            System.err.println("Bits left: " + bitPosition);
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
