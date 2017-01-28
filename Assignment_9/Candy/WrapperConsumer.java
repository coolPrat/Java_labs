/*
 * WrapperConsumer.java
 *
 * Version: 1.0: WrapperConsumer.java,v 1.0 10/25/2015 08:45:03
 *
 * Revisions: 1.0 initial version
 */

/**
 * The class WrapperConsumer will "consume" a candy and a wrapping-paper
 * and will "produce" a wrapped-candy. It will store the wrapped-candy in a storage
 * area (array) named wrappedCandies. The capacity of array is
 * 24 wrappedCandies.
 *
 * This class acts as producer for BoxConsumer class and consumer for
 * CandyProducer and CandyWrappingPaperProducer.
 *
* @author Pratik kulkarni
 */
public class WrapperConsumer extends Thread {
    Candy[] candies;    // array where candies are stored
    WrappingPaper[] wrappingPapers;    // array where wrapping-paper are stored
    WrappedCandy[] wrappedCandies;    // array where wrappedCandies will be stored
    Candy candy;    // Reference for candy
    WrappingPaper paper;    // Reference for Wrapping paper
    static int size = 0;    // static int to keep track of WrappedCandies


    /**
     * Constructor for WrapperConsumer.
     * This will set candies, wrappingPapers, wrappedCandies arrays.
     *
     * @param candies           array to store candies.
     * @param wrappingPapers    array to store wrappingPapers.
     * @param wrappedCandies    array to store wrappedCandies.
     */
    public WrapperConsumer(Candy[] candies, WrappingPaper[] wrappingPapers, WrappedCandy[] wrappedCandies) {
        this.candies = candies;
        this.wrappingPapers = wrappingPapers;
        this.wrappedCandies = wrappedCandies;
    }

    /**
     * This method removes a candy from candies and returns it.
     * @return    a candy object
     */
    public Candy removeCandy() {
        Candy candy = candies[CandyProducer.size - 1];
        candies[CandyProducer.size - 1] = null;
        CandyProducer.size--;
        return candy;
    }

    /**
     * This method removes a wrapping-paper from wrappingPapers and returns it.
     * @return    a WrappingPaper object
     */
    public WrappingPaper removePaper() {
        WrappingPaper paper = wrappingPapers[CandyWrappingPaperProducer.size -1 ];
        wrappingPapers[CandyWrappingPaperProducer.size -1 ] = null;
        CandyWrappingPaperProducer.size--;
        return paper;
    }

    /**
     * Thr run method.
     * This will wait till we have a box and a wrapped-candy to consume.
     * once we have the required things it will remove them from their storage
     * and will place a wrappedCandy in a box and store it in filledBox.
     */
    public void run() {
        while(true) {
            synchronized (candies) {
                while (CandyProducer.getSize() == 0) {
                    try {
                        candies.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                synchronized (wrappingPapers) {
                    while (CandyWrappingPaperProducer.getSize() == 0) {
                        try {
                            wrappingPapers.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    synchronized (wrappedCandies) {
                        while (size == wrappedCandies.length) {
                            try {
                                wrappedCandies.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        candy = removeCandy();
                        paper = removePaper();
                        wrappedCandies[size] = new WrappedCandy(candy, paper);
                        size++;
                        wrappedCandies.notify();
                        candies.notifyAll();
                        if (CandyWrappingPaperProducer.size < wrappingPapers.length - 3)
                            wrappingPapers.notify();
                    }
                }
            }
        }
    }
}
