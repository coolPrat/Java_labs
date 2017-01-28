/*
 * OsName.java
 *
 * Version: 1: OsName.java,v 1 2015/08/31 22:15:00
 *
 * Revisions: 1
 */

/**
 * The class PrintProperty prints name of the operating system.
 *
 * @author      Pratik kulkarni
 */

public class OsName {

    /**
     * The main program. It prints name of the OS.
     *
     * @param   args    String array as command line input (ignored)
     */
    public static void main(String args []) {
             String osName = System.getProperty("os.name");
             System.out.println("OS name: " + osName);
    }
}