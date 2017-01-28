/*
 * NonBlockingIO.java
 *
 * Version: 1.0: NonBlockingIO.java,v 2.0 11/1/2015 10:19:23
 *
 * Revisions: 1.0 initial version
 */
import java.io.*;

/**
 * This class is a Non-blocking IO.
 * It has a buffer that saves 10000 bytes from the file in the compressed form.
 * when reader calls read method it returns bytes from buffer nad re-fills the buffer.
 *
 * @author      Pratik kulkarni
 */
public class NonBlockingIO extends Thread {

    private String compressedString;
    String[] dictionary = new String[1000000]; // dictonary for compression
    int dictionaryLength;
    InputStream input;
    byte[] myBuffer = new byte[10000]; // buffer
    byte[] tempBuffer = new byte[1000]; // temporary buffer
    byte[] tempBuffer2 = new byte[10000];
    byte[] compressedBuffer;
    int size = 0;
    int from = 0, to = 0, position = 0, end = -1, readTill = 0;
    boolean eofFound = false, breakPresent = false, stopReading = false;
    Object o = new Object();
    long startTime, endTime;
    static int bufferSize = 1000;

    /**
     * Constructor of NonBlockingIO class. It sets the IOStream.
     * @param input    IOStream to use.
     */
    public NonBlockingIO(InputStream input) {
        this.input = input;
    }

    /**
     * This method is called before reading the data. It fills the buffer
     * for the first time.
     * @throws IOException
     */
    public void open() throws IOException {
        try {
                this.input.read(tempBuffer2, 0, 10000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        compressedString = compress(tempBuffer2);
        compressedBuffer = compressedString.getBytes();
        for (int i = 0; i < compressedBuffer.length; i++) {
            myBuffer[from + i] = compressedBuffer[i];
        }
        this.start();
    }

    /**
     * The read method fills the buffer and returns number of bytes read
     * @param buffer    buffer to fill with data
     * @return          number of bytes read
     * @throws InterruptedException
     */
    public int read(byte[] buffer) throws InterruptedException {
            int counter = 0;
            synchronized (myBuffer) {
                startTime = System.currentTimeMillis();
                from = position;
                int length = buffer.length;
                if (from + length >= 10000) {
                    breakPresent = true;
                }
                readTill = from + length;
                while (position < readTill && !stopReading && position != end) {
                    if (breakPresent) {
                        if (position < 10000) {
                            buffer[counter] = myBuffer[position];
                            counter++;
                            position++;
                        } else {
                            readTill = readTill - position;
                            position = 0;
                            breakPresent = false;
                            buffer[counter] = myBuffer[position];
                            counter++;
                            position++;
                        }
                    } else {
                        buffer[counter] = myBuffer[position];
                        counter++;
                        position++;
                    }
                        myBuffer.notify();
                }
                if (position == end) {
                    stopReading = true;
                } else {
                    to = position - 1;
                    size -= length;
                }
                endTime =System.currentTimeMillis();
                if ((endTime - startTime) < 20) {
                    synchronized (o) {
                        o.wait(20 - (endTime - startTime));
                    }
                }
            }
        return counter;
        }

    /**
     * The run method.
     * this will keep filling the buffer so user can read without
     * waiting for actual IO.
     */
    public void run() {
        int bytesRead = 0;
        while (!eofFound) {
            synchronized (myBuffer) {
                try {
                    if (from == 10000)
                        from = 0;
                    //bytesRead = this.input.read(myBuffer, from, bufferSize);
                    bytesRead = this.input.read(tempBuffer, 0, bufferSize);
                    compressedString = compress(tempBuffer);
                    compressedBuffer = compressedString.getBytes();
                    for (int i = 0; i < compressedBuffer.length; i++) {
                        myBuffer[from + i] = compressedBuffer[i];
                    }
                    size += bytesRead;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (bytesRead != bufferSize) {
                    eofFound = true;
                    end = from + bytesRead;
                }
                try {
                    myBuffer.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     *  In this method, we are compressing file line by line, so it takes file line as input
     *  compress the line using LZW compression algorithm and then return compressed line.
     *
     *  @param        buffer         uncompressed file line.
     *
     *  @return       writeLine        compressed file line.
     */
    public String compress(byte[] buffer){
        String fileLine = new String(buffer);
        boolean found, foundWord;
        String[] lines = fileLine.split("\\n");
        String writeLine="";
        for(String line: lines) {
            String[] wordList = line.split(" ");
            for (String eachWord : wordList) {

						/* If the given word is all digits, then we are replacing
						 * it with #digit format, to differentiate encoded
						 * String and the digit string, otherwise if the given
						 * word is not already in dictionary then it will add
						 * it to the dictionary.*/
                if (eachWord.matches("\\d+")) {
                    writeLine = writeLine + eachWord.replaceAll("\\d+", "#$0") + " ";
                } else {
                    found = false;
                    //checking if word is in dictionary or not.
                    for (int counter = 0; counter < dictionaryLength; counter++) {
                        if ((eachWord.equals(dictionary[counter]))) {
                            found = true;
                            writeLine = writeLine + String.valueOf(counter) + " ";
                            break;
                        }
                    }
                    //if word is not in dictionary then add it to dictionary.
                    if (!found) {
                        writeLine = writeLine + eachWord + " ";
                        for (int charCounter = 1; charCounter <= eachWord.length(); charCounter++) {
                            foundWord = false;
                            for (int counter = 0; counter < dictionaryLength; counter++) {
                                if (eachWord.substring(0, charCounter).equals(dictionary[counter])) {
                                    foundWord = true;
                                    break;
                                }
                            }
                            if (!foundWord) {
                                dictionary[dictionaryLength] = eachWord.substring(0, charCounter);
                                dictionaryLength++;
                            }

                        }
                    }
                }
            }
            writeLine += "\n";
        }
        return writeLine.trim();
    }
}
