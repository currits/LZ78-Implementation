import java.io.*;
public class LZunpack {

    public static void main(String[] args){
        if (args.length == 0) {
            System.out.println("Usage: LZunpack <maxPhrases>");
            System.out.println("maxPhrases:  The maximum number of phrases that the unpacker needs to account for");
            return;
        }

        //get number of phrases
        int i = Integer.parseInt(args[0]);
        //calculate the ceiling of bits to encode that many phrases (log base 2 of phrase count)
        int phraseBitCount = (int)Math.ceil((Math.log(i) / Math.log(2)));
        System.err.println("Phrase bits: " + phraseBitCount);
        //mismatched phrase is two hex digits, 8 bits
        int mismatchedBitCount = 8;
        //to track which order bit we are unpacking from next
        int bitPosition = 0;
        //AND mask to extract #phraseBitCount lower order bits from a 32 bit int
        int phraseMask = ((int)Math.pow(2, phraseBitCount)) - 1; //2 ^ phraseBit count to get lower order AND mask
        System.err.println("Phrase mask: " + phraseMask);
        //AND mask to extract the mismatch character bits fron a 32 bit int
        int mismatchMask = 0xFF;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
            String line = reader.readLine();
            byte buffer[] = line.getBytes();
            int bufferIndex = 0;
            int bufferCap = buffer.length;
            int input = 0;

            //loop for as long as there is input
            while (bufferIndex < bufferCap){
                //populate the input with packed bits
                //only for as long there is space for at least a full byte
                while (bitPosition <= 24 && bufferIndex < bufferCap){
                    System.err.println("read loop");
                    //read in a byte
                    int in = buffer[bufferIndex];
                    //mark that byte as proccessed
                    bufferIndex++;
                    //shift the input into position to be written
                    in = in << bitPosition;
                    //OR it in place
                    input = input | in;
                    //move the bit position tracker over by a byte
                    bitPosition += 8;
                }

                //loop to proccess the input
                //for as long as there is at least enough useful bits totalling a phrase-mismatch tuple
                while ((bitPosition >= (phraseBitCount + mismatchedBitCount))){
                    System.err.println("write loop");
                    //extract phrase number with bit mask
                    int phrase = input & phraseMask;
                    //shift the input along
                    input = input >>> phraseBitCount;
                    //extract the mismatch character with bit mask
                    int mismatch = input & mismatchMask;
                    //shift the input along
                    input = input >>> mismatchedBitCount;
                    //write the tuple to output with formatting (space seperated strings, new line)
                    writer.write(phrase + " " + mismatch);
                    writer.newLine();
                    //shift the bit position tracker by the length of bits extracted (phrase + mismatch bit length)
                    bitPosition -= (phraseBitCount + mismatchedBitCount);
                }
            
            }
            
            //final repeat of write loop to capture remain bits (often just a phrase number and then a null character)
            while (bitPosition > 0){
                System.err.println("write loop");
                //extract phrase number with bit mask
                int phrase = input & phraseMask;
                //shift the input along
                input = input >>> phraseBitCount;
                //extract the mismatch character with bit mask
                int mismatch = input & mismatchMask;
                //shift the input along
                input = input >>> mismatchedBitCount;
                //write the tuple to output with formatting (space seperated strings, new line)
                writer.write(phrase + " " + mismatch);
                writer.newLine();
                //shift the bit position tracker by the length of bits extracted (phrase + mismatch bit length)
                bitPosition -= (phraseBitCount + mismatchedBitCount);
            }

            System.err.println("Bits left: " + bitPosition + ", Byte array length: " + bufferCap);
            System.err.println("Finished");
            writer.close();
            reader.close();

        }
        catch (Exception e){
            System.out.println("Unpack error: ");
            e.printStackTrace();
        }
    }
}
