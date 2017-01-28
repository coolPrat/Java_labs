/*
 * StorageIterator.java
 *
 * Version: 1.0: StorageIterator.java,v 1.0 9/26/2015 10:19:23
 *
 * Revisions: 1.0 initial version
 */
import java.util.Iterator;

@SuppressWarnings("rawtypes")
public class StorageIterator implements Iterator<Element> {
    /**
     * Iterator for class Element. This is used in Storage to iterate through
     * elements for linkedist.
     *
     *
     * @author      Pratik kulkarni
     */
    private Storage storage; // instance of Storage.
    private Element head; // head of the list
    private Element currentElement; // pointer to current element
    private Element previousElement; // pointer for previous element accessed

    /**
     * sets the head of the linkedlist to specified element.
     * @param head    An element thats new head.
     */
    public void setHead(Element head) {
        this.head = head;
    }

    /**
     * sets the current element of the iterator to specified element.
     * @param currentElement    An element that needs to set.
     */
    public void setCurrentElement(Element currentElement) {
        this.currentElement = currentElement;
    }

    /**
     * Constructor for StorageIterator. This sets the storage object for iterator
     * @param storage    Storage object
     */
    public StorageIterator(Storage storage) {
        this.storage = storage;
        this.previousElement = null;
    }

    /**
     * This method returns true if the list has next element.
     * @return    true if next element if present else false
     */
    @Override
    public boolean hasNext() {
        if (currentElement == null) {
            if (head == null) {
                return false;
            } else {
                return true;
            }
        } else {
            return currentElement.getNextElement() != null;
        }
    }

    /**
     * This method return the next element from the list
     * @return    next element
     */
    @Override
    public Element next() {
        if (hasNext()) {
            if (currentElement == null) {
                currentElement = head;
                return head;
            }
            Element e = currentElement.getNextElement();
            previousElement = currentElement;
            currentElement = e;
            return e;
        } else {
            return null;
        }
    }

    /**
     * dummy method (not because its never used in the program)
     */
    @Override
    public void remove() {
    }
}
