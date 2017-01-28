/*
 * CandyFactory.java
 *
 * Version: 1.0: CandyFactory.java,v 1.0 10/25/2015 08:45:03
 *
 * Revisions: 1.0 initial version
 */

/**
 * The candy factory class is used to run all the producers-consumers and
 * generate boxes filled with candies.
 */
public class CandyFactory {
    static Candy[] candies = new Candy[24];
    static WrappingPaper[] wrappingPapers = new WrappingPaper[24];
    static WrappedCandy[] wrappedCandies = new WrappedCandy[24];
    static Box[] boxes = new Box[6];
    static Box[] filledBoxes = new Box[12];

    /**
     * The main method. It will start all the threads.
     * @param args    Command line argument(ignored)
     */
    public static void main(String[] args) {
        CandyProducer candyProducer = new CandyProducer(candies);
        CandyWrappingPaperProducer candyWrappingPaperProducer = new CandyWrappingPaperProducer(wrappingPapers);
        WrapperConsumer wrapperConsumer = new WrapperConsumer(candies,wrappingPapers, wrappedCandies);
        BoxProducer boxProducer = new BoxProducer(boxes);
        BoxConsumer boxConsumer = new BoxConsumer(boxes, wrappedCandies, filledBoxes);
        wrapperConsumer.start();
        candyProducer.start();
        boxProducer.start();
        candyWrappingPaperProducer.start();
        boxConsumer.start();
    }

}
