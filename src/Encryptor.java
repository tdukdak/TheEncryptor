public class Encryptor
{
    /** A two-dimensional array of single-character strings, instantiated in the constructor */
    private String[][] letterBlock;

    /** The number of rows of letterBlock, set by the constructor */
    private int numRows;

    /** The number of columns of letterBlock, set by the constructor */
    private int numCols;

    /** Constructor*/
    public Encryptor(int r, int c)
    {
        letterBlock = new String[r][c];
        numRows = r;
        numCols = c;
    }

    public String[][] getLetterBlock()
    {
        return letterBlock;
    }

    /** Places a string into letterBlock in row-major order.
     *
     *   @param str  the string to be processed
     *
     *   Postcondition:
     *     if str.length() < numRows * numCols, "A" in each unfilled cell
     *     if str.length() > numRows * numCols, trailing characters are ignored
     */
    public void fillBlock(String str)
    {
        String[] letters = str.split("");
        int place = 0;
        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++){
                if(place > str.length()-1){
                    letterBlock[i][j] = "A";
                }
                else{
                    letterBlock[i][j] = letters[place];
                }
                place++;
            }
        }
    }

    /** Extracts encrypted string from letterBlock in column-major order.
     *
     *   Precondition: letterBlock has been filled
     *
     *   @return the encrypted string from letterBlock
     */
    public String encryptBlock()
    {
        String crypt = "";
        for(int i = 0; i < numCols; i++){
            for(int j = 0; j < numRows; j++){
                crypt += letterBlock[j][i];
            }
        }
        return crypt;
    }

    /** Encrypts a message.
     *
     *  @param message the string to be encrypted
     *
     *  @return the encrypted message; if message is the empty string, returns the empty string
     */
    public String encryptMessage(String message)
    {
        String crypt = "";
        int place = 0;
        while(place < message.length()){
            fillBlock(message.substring(place));
            crypt += encryptBlock();
            place += numCols * numRows;
        }
        return crypt;
    }

    /**  Decrypts an encrypted message. All filler 'A's that may have been
     *   added during encryption will be removed, so this assumes that the
     *   original message (BEFORE it was encrypted) did NOT end in a capital A!
     *
     *   NOTE! When you are decrypting an encrypted message,
     *         be sure that you have initialized your Encryptor object
     *         with the same row/column used to encrypted the message! (i.e.
     *         the “encryption key” that is necessary for successful decryption)
     *         This is outlined in the precondition below.
     *
     *   Precondition: the Encryptor object being used for decryption has been
     *                 initialized with the same number of rows and columns
     *                 as was used for the Encryptor object used for encryption.
     *
     *   @param encryptedMessage  the encrypted message to decrypt
     *
     *   @return  the decrypted, original message (which had been encrypted)
     *
     *   TIP: You are encouraged to create other helper methods as you see fit
     *        (e.g. a method to decrypt each section of the decrypted message,
     *         similar to how encryptBlock was used)
     */
    public String decryptMessage(String encryptedMessage)
    {
        int blocks=encryptedMessage.length()/(numCols*numRows);
        String temp=encryptedMessage;
        String crypt = "";
        while (blocks > 0) {
            fill(temp);
            encryptBlock();
            for (String[] strings : letterBlock) {
                for (int i = 0; i < letterBlock[0].length; i++) {
                    if(strings[i].equals("A")  && (blocks - 1 == 0)){
                        crypt = crypt;
                    }
                    else{
                        crypt += strings[i];
                    }
                }
            }
            temp=temp.substring(numCols*numRows);
            blocks--;
        }
        return crypt;
    }
    public void fill(String message){
        int counter = 0;
        for(int i = 0; i < letterBlock[0].length; i++){
            for(int j = 0; j < letterBlock.length; j++){
                if(counter < message.length()) {
                    letterBlock[j][i] = message.substring(counter, counter + 1);
                    counter++;
                }
                else{
                    letterBlock[j][i] = "A";
                }
            }
        }
    }
}