/*
 * IOTest.java
 *
 * Version: 1.0: IOTest.java,v 2.0 11/1/2015 10:19:23
 *
 * Revisions: 1.0 initial version
 */

import java.io.*;

/**
 * This class tests NonBlockingIO Class.
 * The test reads a file and compresses it and store it in another file.
 *
 * @author      Pratik kulkarni
 */

public class IOTest {

    static byte[] buf = new byte[1000];
    static String output = "text2.txt";

    public static void main(String[] args) throws IOException, InterruptedException {
        NonBlockingIO nonBlockingIO = new NonBlockingIO(new FileInputStream("text.txt"));
        FileOutputStream f = new FileOutputStream(output);
        nonBlockingIO.open();
        while (nonBlockingIO.read(buf) > 0) {
            nonBlockingIO.read(buf);
            f.write(buf);
        }
    }
}
