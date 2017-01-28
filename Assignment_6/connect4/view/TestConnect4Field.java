/*
 * TestConnect4Field.java
 *
 * Version: 1: TestConnect4Field.java,v 1 10/4/2015 16:34:23
 *
 * Revisions: 1.0 initial version
 *
 */

package connect4.view;

public class TestConnect4Field {
    /**
     * This class will test the TestConnect4Field class of controller.
     */

    /**
     * The main program. This will test the model by calling testIt() method of
     * controller.TestConnect4Field class
     * @param args    Command line argument (ignored)
     */
    public static void main(String[] args) {
        connect4.controller.TestConnect4Field testClass = new
                connect4.controller.TestConnect4Field();
        testClass.testIt();
    }
}
