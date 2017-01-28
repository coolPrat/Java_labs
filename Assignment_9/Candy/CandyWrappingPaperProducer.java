/*
 * CandyWrappingPaperProducer.java
 *
 * Version: 1.0: CandyWrappingPaperProducer.java,v 1.0 10/25/2015 08:45:03
 *
 * Revisions: 1.0 initial version
 */

/**
 * The class CandyWrappingPaperProducer will "produce" WrappingPaper and will store it
 * in an storage (array) named wrappingPapers. The capacity of array is
 * 24 wrappingPapers.
 *
 * This class acts as producer for WrapperConsumer class.
 * @author Pratik kulkarni
 */
public class CandyWrappingPaperProducer extends Thread {
    WrappingPaper[] wrappingPapers;    // Storage area for wrapping papers
    static int size = 0;
    // static int to keep track of wrapperPapers


    /**
     * Constructor for CandyWrappingPaperProducer. It sets the wrappingPapers
     * array.
     *
     * @param wrappingPapers    array to store wrappingPapers.
     */
    public CandyWrappingPaperProducer(WrappingPaper[] wrappingPapers) {
        this.wrappingPapers = wrappingPapers;
    }

    /**
     * This method produces a wrapping-paper and stores it in the storage.
     */
    public void addPaper() {
        wrappingPapers[size] = new WrappingPaper();
        size++;
    }

    /**
     * This method returns current size of the wrappingPapers array.
     *
     * @return    current size of the wrappingPapers array.
     */
    public static int getSize() {
        return size;
    }

    /**
     * The run method.
     * This method will produces 3 wrapping-papers and stores them in storage assigned.
     * In case storage is completely filled it will wait till consumer consumes
     * a wrapping-paper.
     */
    public void run() {
        while (true) {
            synchronized (wrappingPapers) {
                while (getSize() > wrappingPapers.length - 3) {
                    try {
                        wrappingPapers.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                addPaper();
                addPaper();
                addPaper();
                wrappingPapers.notify();
            }
        }
    }
}
