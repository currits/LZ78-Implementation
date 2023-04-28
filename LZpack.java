import java.io.*;
public class LZpack {
    
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: LZpack <maxPhrases>");
            System.out.println("maxPhrases:  The maximum number of phrases that the packer needs to account for");
            return;
        }

        // Get number of phrases
        int i = Integer.parseInt(args[0]);
        // Calculate the ceiling of bits to encode that many phrases (log base 2 of phrase count)
        int phraseBitCount = (int)Math.ceil((Math.log(i) / Math.log(2)));
        // Mismatched phrase is two hex digits, 8 bits
        int mismatchedBitCount = 8;
        // To track which rightmost bit we are packing from next (little endian)
        int bitPosition = 0;

        try {
            // Using an outputstream to write bytes directly
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            BufferedOutputStream writer = new BufferedOutputStream(System.out);
            
            // Byte buffer to store anumber of bytes to write out
            byte[] buffer = new byte[64];
            // Index tracker, to store where to write into the buffer
            int bufferIndex = 0;
            // String variables for reading in tuples
            String line = "";
            String tuple[];
            // Integer variables for storing and manipulating the bits for phrases and mismatched characters
            int phrase;
            int mismatch;
            // The working 'output' feed of 32 bits to write out from, one byte at a time
            int output = 0;

            // Loop while there is input
            while (line != null){

                // If the byte buffer is near full, write it to output
                if (bufferIndex >= 62){
                    writer.write(buffer, 0, bufferIndex);
                    bufferIndex = 0;
                }

                // while bit space available in output (32 - bitPos) is greater than bits needed to pack in a tuple (bitcount's sum)
                while ((32 - bitPosition) >= (phraseBitCount + mismatchedBitCount)){
                    System.err.println("read loop");
                    // Read in a tuple
                    line = reader.readLine();
                    // If we have read in nothing, or the null character signifying end of file, break
                    if (line == null || (line.compareTo("\n") == 0)){
                        break;
                    }
                    // Split the tuple arround white space
                    tuple = line.split("\\s+");
                    // Extract the bit value of the phrase number
                    phrase = Integer.parseInt(tuple[0]);
                    System.err.println(phrase);
                    // And the mismatched character
                    mismatch = Integer.parseInt(tuple[1]);
                    System.err.println(mismatch);

                    // Get the phrase into bit position
                    phrase = phrase << bitPosition;
                    // OR it in
                    output = output | phrase;
                    // Shift bit position tracker by the number of bits reserved for phrases
                    bitPosition += phraseBitCount;
                    // Get the mismatched char into position
                    mismatch = mismatch << bitPosition;
                    // OR it in
                    output = output | mismatch;
                    // Shift bit position tracker by the number of bits reserved for mismatched chars (8)
                    bitPosition += mismatchedBitCount;
                    // Then loop again
                }

                // While there is enough useful bits (bitPos >= 8) and buffer is not near full, store some number of bytes into buffer
                while (bufferIndex <= 62 && bitPosition >= 8){
                    System.err.println("Write loop");
                    // Extract lower order 8 bits 
                    // AND mask to ensure lower order bits only, then cast to byte
                    byte out = (byte)(output & 0xFF);
                    // Write it to buffer
                    buffer[bufferIndex] = out;
                    System.err.println(out);
                    // Increment buffer index tracker
                    bufferIndex++;
                    // Reduce the bit position by the number of written bits (a single byte)
                    bitPosition -= 8;
                    // Shift output bits accordingly
                    output = output >>> 8;
                }
            }

            // Final write loop repeat to write all remaining bits in output feed
            while(bitPosition > 0){
                System.err.println("Write loop");
                // Extract lower order 8 bits 
                // AND mask to ensure lower order bits only, then cast to byte
                byte out = (byte)(output & 0xFF);
                // Write it to buffer
                buffer[bufferIndex] = out;
                System.err.println(out);
                // Increment buffer index tracker
                bufferIndex++;
                // Reduce the bit position by the number of written bits (a single byte)
                bitPosition -= 8;
                // Shift output bits accordingly
                output = output >>> 8;
            }
            // Write the remaining bytes out after looping
            writer.write(buffer, 0, bufferIndex);
            writer.flush();
            System.err.println("Bits left: " + bitPosition);
            System.err.println("Finished");
            // Close IO
            writer.close();
            reader.close();
        }
        catch (Exception e){
            System.out.println("Pack error: ");
            e.printStackTrace();
        }

    }
    
}
