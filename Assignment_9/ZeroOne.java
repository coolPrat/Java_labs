/*
 * ZeroOne.java
 *
 * Version: 2.0: ZeroOne.java,v 2.0 10/26/2015 10:19:23
 *
 * Revisions: 1.0 initial version
 *            2.0 Updated to work with 99 threads
 */


/**
 * The class ZeroOne creates 99 threads each having a unique ID from 0 to 98.
 * These threads run in a synchronized fashion to print their ID's.
 *
 * Output will be like:
 * ........01234567891011121314151617181920.....95969798012345678910......
 *
 * @author      Pratik kulkarni
 */
public class ZeroOne extends Thread	{
    int id;    // The unique int ID of each thread
    static Object[] objects = new Object[99];
    // Objects used for inter-thread communication
    private Object myObject;
    // Object used to notify current thread
    private Object nextObject;
    // Object used to notify next thread
    static int counter = 0; // counter to count number of threads created


    /**
     * Constructor of ZeroOne class. It sets id, object and object to notify
     * next thread
     * @param id           unique integer ID of the thread
     * @param myObject     Object used to notify current thread
     * @param nextObject   Object used to notify next thread
     */
    public ZeroOne (int id, Object myObject, Object nextObject) {
        this.id    = id;
        this.myObject = myObject;
        this.nextObject = nextObject;
    }


    /**
     * Run method.
     * This method will run infinitely and print threads ID.
     * While running it first checks if we have created 99 threads. If not
     * it creates the thread and assigns required objects and ID to it.
     *
     * Every thread after printing its ID notifies next thread using nextObject
     * and waits on myObject till the thread before it notifies it.
     */
    public void run () {
        int newCount;
        while ( true ) {
            synchronized (myObject) {
                synchronized (nextObject) {
                    System.out.print(id);
                    if (counter < 99) {
                        newCount = ++counter;
                        if (newCount == 98) {
                            (new ZeroOne(newCount, objects[newCount], objects[0])).start();
                        } else {
                            if (counter < 99)
                                (new ZeroOne(newCount, objects[newCount], objects[newCount + 1])).start();
                        }
                    }
                    nextObject.notify();
                }
                try {
                    Thread.sleep(300);
                    myObject.wait();
                } catch (Exception e) {
                }
            }
        }
    }


    /**
     * The main program.
     * It runs first thread which then creates and runs other threads.
     *
     * @param args    Command line arguments (ignored)
     */
    public static void main (String args []) {
        for (int i = 0; i< 99; i++) {
            objects[i] = new Object();
        }
        new ZeroOne(0, objects[0], objects[1]).start();
    }
}
