/*
 * BoxProducer.java
 *
 * Version: 1.0: BoxProducer.java,v 1.0 10/25/2015 08:45:03
 *
 * Revisions: 1.0 initial version
 */

/**
 * The class BoxProducer will "produce" a box and will store it
 * in an storage (array) named boxes. The capacity of array is
 * 6 boxes.
 *
 * This class acts as producer for BoxConsumer class.
 * @author Pratik kulkarni
 */

public class BoxProducer extends Thread {
    Box[] boxes;    // Storage area for boxes
    static int size = 0;
    // static int to keep track of boxes

    /**
     * Constructor of BoxProducer class. It sets boxes array.
     * @param boxes    array to store boxes.
     */
    public BoxProducer(Box[] boxes) {
        this.boxes = boxes;
    }

    /**
     * This method adds a box in the storage boxes.
     * @param box    the box to add
     */
    public void addBox(Box box) {
        boxes[size] = box;
        size++;
    }

    /**
     * This method return current size of the boxes array.
     * @return    current size of the boxes array
     */
    public static int getSize() {
        return size;
    }

    /**
     * The run method.
     * This method will produces a box and stores it in storage assigned.
     * In case storage is completely filled it will wait till consumer consumes
     * a box.
     */
    public void run() {
        while (true) {
            synchronized (boxes) {
                while (getSize() == boxes.length) {
                    try {
                        boxes.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                addBox(new Box());
                boxes.notifyAll();
            }
        }
    }
}
