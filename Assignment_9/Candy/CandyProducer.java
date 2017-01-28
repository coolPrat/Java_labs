/*
 * CandyProducer.java
 *
 * Version: 1.0: CandyProduce.java,v 1.0 10/25/2015 08:45:03
 *
 * Revisions: 1.0 initial version
 */

/**
 * The class CandyProducer will "produce" Candy and will store it in an storage
 * (array) named candies. The capacity of array is 24 candies.
 *
 * This class acts as producer for WrapperConsumer class.
 *
 * @author      Pratik kulkarni
 */

public class CandyProducer extends Thread {
    static Candy[] candies;  // storage for storing candies
    static int size = 0;    // static int to keep track of candies produced

    /**
     * constructor of CandyProducer. It sets candies array.
     *
     * @param candiesArray    array to store candies.
     */
    public CandyProducer(Candy[] candiesArray) {
        candies = candiesArray;
    }

    /**
     * This method produces a candy and stores it in the storage candies.
     * @param candy    The candy to be added
     */
    public void addCandy(Candy candy) {
        candies[size] = candy;
        size++;
    }

    /**
     * This method returns current size of candies array.
     * @return    current size of candies array
     */
    public static int getSize() {
        return size;
    }

    /**
     * Run method.
     * This method will produce a candy and store it in storage assigned.
     * In case storage is completely filled it will wait till consumer consumes
     * a candy.
     */
    public void run() {
        while (true) {
            synchronized (candies) {
                while (size == candies.length) {
                    try {
                        candies.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                addCandy(new Candy());
                candies.notifyAll();
            }
        }
    }
}
