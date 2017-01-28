/*
 * BoxConsumer.java
 *
 * Version: 1.0: BoxConsumer.java,v 1.0 10/25/2015 08:45:03
 *
 * Revisions: 1.0 initial version
 */


/**
 * The class BoxConsumer will "consume" a wrapped-candy and it will store in a box.
 * A box can hold 4 wrapped-candies. Once a box is filled it will store the filled
 * box in a storage area (array) named filledBox. The capacity of array is
 * 12 boxes.
 *
 * This class acts as consumer for WrappedCandyProducer.
 *
 * @author Pratik kulkarni
 */

public class BoxConsumer extends Thread{
    Box[] boxes;    // Storage area for boxes
    WrappedCandy[] wrappedCandies;    // array where wrappedCandies will be stored
    static int size = 0;    //static int to keep track of WrappedCandies
    Box[] filledBox;    // array where filled-boxes will be stored
    Box box;    // Reference of box

    /**
     * constructor for BoxConsumer class. It will set boxes, wrappedCandies, and
     * filledBox.
     * @param boxes             array to store candies.
     * @param wrappedCandies    array to store wrappedCandies.
     * @param filledBox         array to store filledBoxes.
     */
    public BoxConsumer(Box[] boxes, WrappedCandy[] wrappedCandies, Box[] filledBox) {
        this.boxes = boxes;
        this.wrappedCandies = wrappedCandies;
        this.filledBox = filledBox;
    }

    /**
     * This method removes a WrappedCandy from wrapped-candies and returns it.
     * @return    a WerappedCandy object
     */
    WrappedCandy removeWrappedCandy() {
        WrappedCandy wrappedCandy = wrappedCandies[WrapperConsumer.size - 1];
        WrapperConsumer.size--;
        return wrappedCandy;
    }

    /**
     * This method removes a box from boxes and returns it.
     * @return    a box object
     */
    Box removeBox() {
        Box box = boxes[BoxProducer.size - 1];
        BoxProducer.size--;
        return box;
    }

    /**
     *
     */
    public void run() {
        while (size < 12) {
            synchronized (boxes) {
                while (BoxProducer.size == 0 ) {
                    try {
                        boxes.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                synchronized (wrappedCandies) {
                    while (WrapperConsumer.size == 0) {
                        try {
                            wrappedCandies.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (box == null) {
                        box = removeBox();
                    }
                    WrappedCandy wrappedCandy = removeWrappedCandy();
                    box.addCandy(wrappedCandy);
                    if (box.getSize() == 4) {
                        filledBox[size] = box;
                        System.out.println("Box " + size + " filled");
                        box = null;
                        size++;
                    }
                    wrappedCandies.notify();
                    boxes.notify();
                }
            }
        }
        System.exit(0);
    }
}
