import java.io.*;
public class LZunpack {

    public static void main(String[] args){
        if (args.length == 0) {
            System.out.println("Usage: LZunpack <maxPhrases>");
            System.out.println("maxPhrases:  The maximum number of phrases that the unpacker needs to account for");
            return;
        }

        // Get number of phrases
        int i = Integer.parseInt(args[0]);
        // Calculate the ceiling of bits to encode that many phrases (log base 2 of phrase count)
        int phraseBitCount = (int)Math.ceil((Math.log(i) / Math.log(2)));
        System.err.println("Phrase bits: " + phraseBitCount);
        // Mismatched phrase is two hex digits, 8 bits
        int mismatchedBitCount = 8;
        // To track which rightmost bit we are packing from next (little endian)
        int bitPosition = 0;
        // AND mask to extract phrase bits bits from a 32 bit int
        int phraseMask = ((int)Math.pow(2, phraseBitCount)) - 1;
        System.err.println("Phrase mask: " + phraseMask);
        // AND mask to extract the mismatched character bits fron a 32 bit int
        int mismatchMask = 0xFF;

        try {
            // Writer for writing to system.out
            // Inputstream for reading in bytes
            // Array Output stream for building a byte array from an input stream of unknown length
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
            BufferedInputStream reader = new BufferedInputStream(System.in);
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

            // Buffer to read bytes into from inputstream
            byte[] data = new byte[32];
            // To store # of bytes read in at a time
            int read = 0;
            // Read up to 32 bytes from input
            while ((read = reader.readNBytes(data, 0, data.length)) > 0){
                //write them to the byteoutput stream
                byteStream.write(data, 0, read);
            }
            // Flush the bytestream
            byteStream.flush();
            // Then store the byte stream as an array to work with
            // Now we have a fixed length byte array to work with, made using any length of input
            byte[] inputStream = byteStream.toByteArray();
            // Input array index counter
            int streamIndex = 0;
            // Array length
            int streamCap = inputStream.length;
            System.err.println("Inputstream length: " + streamCap);
            // The working 'input' feed to insert bytes into, then extract phrase-mismatch tuples from
            int input = 0;
            // Looping condition
            boolean loop = true;

            // Loop for as long as there is input
            while (loop){

                // Populate the input with packed bits
                // Only for as long there is space for at least a full byte
                while (bitPosition <= 24){
                    System.err.println("read loop");
                    // Check for end of input
                    if (streamIndex == streamCap-1){
                        loop = false;
                        break;
                    }
                    // Read in a byte
                    int in = inputStream[streamIndex];
                    System.err.println(in);
                    // Mark it as proccessed by incrementing array index counter
                    streamIndex++;
                    // Left shift the input into position to be written into input
                    // AND 0xFF mask to ensure only lower order 8 bits are inserted, not integer equivalent value of byte (byte value -40 != int value -40, bitwise)
                    in = (in & 0xFF) << bitPosition;
                    // OR it into place
                    input = input | in;
                    // Move the bit position tracker over by a byte
                    bitPosition += 8;
                }

                // Loop to extract phrases and mismatched chars from input bits
                // For as long as there is at least enough useful bits totalling a phrase-mismatch tuple
                while ((bitPosition >= (phraseBitCount + mismatchedBitCount))){
                    System.err.println("write loop");
                    // Extract phrase number with bit mask from lower most bits
                    int phrase = input & phraseMask;
                    // Shift the input along by phraseBits
                    input = input >>> phraseBitCount;
                    // Extract the mismatched character with bit mask from lower order bits
                    int mismatch = input & mismatchMask;
                    // Shift the input along by mismatchBits
                    input = input >>> mismatchedBitCount;
                    // Write the tuple to output with formatting (space seperated strings, new line)
                    writer.write(phrase + " " + mismatch);
                    writer.newLine();
                    // Shift the bit position tracker by the length of bits extracted (phrase + mismatch bit length)
                    bitPosition -= (phraseBitCount + mismatchedBitCount);
                }
            
            }
            
            // Final repeat of write loop to capture remain bits (often just a phrase number and then a null character)
            while (bitPosition > 0){
                System.err.println("write loop");
                // Extract phrase number with bit mask from lower most bits
                int phrase = input & phraseMask;
                // Shift the input along by phraseBits
                input = input >>> phraseBitCount;
                // Extract the mismatched character with bit mask from lower order bits
                int mismatch = input & mismatchMask;
                // Shift the input along by mismatchBits
                input = input >>> mismatchedBitCount;
                // Write the tuple to output with formatting (space seperated strings, new line)
                writer.write(phrase + " " + mismatch);
                writer.newLine();
                // Shift the bit position tracker by the length of bits extracted (phrase + mismatch bit length)
                bitPosition -= (phraseBitCount + mismatchedBitCount);
            }

            System.err.println("Bits left: " + bitPosition);
            System.err.println("Finished");
            // Clost IO
            writer.close();
            reader.close();

        }
        catch (Exception e){
            System.out.println("Unpack error: ");
            e.printStackTrace();
        }
    }
}
