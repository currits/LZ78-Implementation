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
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

            BufferedInputStream reader = new BufferedInputStream(System.in);
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            //buffer to read into fron inputstream
            byte[] data = new byte[32];
            int read = 0;
            //read bytes from input
            while ((read = reader.readNBytes(data, 0, data.length)) > 0){
                //write them to the byteoutput stream
                byteStream.write(data, 0, read);
            }
            //flush the bytestream
            byteStream.flush();
            //then store the byte stream as an array to work with
            byte[] inputStream = byteStream.toByteArray();
            //byte index counter and cap
            int streamIndex = 0;
            int streamCap = inputStream.length;
            System.err.println("Inputstream length: " + streamCap);
            int input = 0;
            boolean loop = true;

            //loop for as long as there is input
            while (loop){
                //populate the input with packed bits
                //only for as long there is space for at least a full byte
                while (bitPosition <= 24){
                    System.err.println("read loop");
                    //check for end of stream
                    if (streamIndex == streamCap-1){
                        loop = false;
                        break;
                    }
                    //read a byte
                    int in = inputStream[streamIndex];
                    System.err.println(in);
                    //mark it as proccessed
                    streamIndex++;
                    //shift the input into position to be written
                    //this here line of code consumed 26 hours of my life spread over 4 days
                    //beware software engineering, for there be monsters
                    in = (in & 0xFF) << bitPosition;
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

            System.err.println("Bits left: " + bitPosition);
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
