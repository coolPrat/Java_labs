/*
 * StorageDynamic.java
 *
 * Version: 1.0: StorageDynamic.java,v 1.0 9/26/2015 10:19:23
 *
 * Revisions: 1.0 initial version
 */
public class StorageDynamic<E, V> implements Storage<E, V> {
    /**
     * The class StorageDynamic can store unlimited elements of type Element which
     * has two parts of type E and V respectively. The class provides methods to
     * add an element in the existing storage either at particular index or at
     * the end. We can retrieve an element by specifying its index. This class
     * also provides a method to clear all the elements stored making it an empty
     * data-structure.
     *
     * Underlying data-structure used by this class is a LinedList.
     *
     * @author      Pratik kulkarni
     */

    private Element<E, V> head;  // Reference pointing to head of the list
    private Element<E, V> lastAdded; // reference of last element.
    private int elementCount = 0; // total number of elements added
    private StorageIterator iterator = new StorageIterator(this);
    // Iterator used to traverse the list


    /**
     * sets the head of the linkedlist to specified element.
     * @param head    An element thats new head.
     */
    public void setHead(Element<E, V> head) {
        this.head = head;
    }

    /**
     * Sets the iterator used to iterate through the list used.
     * @param iterator    Iterator that can be used to iterate the list.
     */
    public void setIterator(StorageIterator iterator) {
        this.iterator = iterator;
    }

    /**
     * This method adds the element of type E to the list.
     * For this it will fist create a Element that holds the object of e that we
     * want to add. Then we check if we have room for the element, If it can be
     * added, we add the Element to the list. We also update the next
     * link of the previous element to point to new element.
     *
     * @param element    object of type E to be added into the list
     * @return           boolean true if it can be added or else false
     */
    @Override
    public boolean add(E element) {
        Element<E, V> newElement = new Element<>(element);
        if (elementCount == 0) {
            head = newElement;
            lastAdded = newElement;
            this.iterator.setHead(head);
            ++elementCount;
        } else {
            this.lastAdded.setNextElement(newElement);
            this.lastAdded = newElement;
            ++elementCount;
        }
        return true;
    }

    /**
     * This method adds and element at specified index. As we are using a list we
     * can not iterate to a index that is not been accessed yet.
     *
     * @param index
     * @param element
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean add(int index, E element) {
        Element<E, V> newElement = new Element<>(element);
        Element<E, V> temporaryElement = null;
        if (index > 0) {
            int counter = -1;
            while (counter < index - 1) {
                if (iterator.hasNext()) {
                    temporaryElement = iterator.next();
                    counter++;
                } else {
                    return false;
                }
            }
            newElement.setNextElement(temporaryElement.getNextElement());
            temporaryElement.setNextElement(newElement);
            elementCount++;
            iterator.setCurrentElement(null);
            return true;
        } else if (index == 0) {
            temporaryElement = head;
            head = newElement;
            head.setNextElement(temporaryElement);
            elementCount++;
            iterator.setHead(head);
            iterator.setCurrentElement(null);
            return true;
        }
        return false;
    }

    /**
     * This method is same as add(E element) method only difference being it does
     * not return boolean value indicating success of addition operation.
     *
     * @param element    element that needs to be added to list
     */
    @Override
    public void addElement(E element) {
        add(element);
    }

    /**
     * This method adds an element that has both the types of values (E and V).
     * This will first add a element with value of type E and then update it
     * by adding value of V to the element.
     *
     * @param obj    object of E
     * @param elem   object of V
     */
    @Override
    public void addElement(E obj, V elem) {
        boolean addNewElement = add(obj);
        if (addNewElement) {
            this.lastAdded.setValue(elem);
        }
    }

    /**
     * Returns number of elements stored in the storage.
     * @return    number of elements stored
     */
    @Override
    public int capacity() {
        return elementCount;
    }

    /**
     * This method will clear entire list setting number of elements stored to 0.
     * This is achieved by setting null as head of the list. AS we are not able
     * to point to first element of the list whole list becomes un-accessible.
     *
     */
    @Override
    public void clear() {
        head = null;
        elementCount = 0;
    }

    /**
     * This method returns the E part of first element of the list(head).
     * @return    value of E part of first element
     */
    @Override
    public E firstElement() {
        return head.getElement();
    }

    /**
     * This method returns the element at specified index
     * @param index    the integer index
     * @return         Element at specified index
     */
    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        if (index < 0 || index > elementCount) {
            return null;
        } else if (index == 0) {
            return head.getElement();
        }
        int count = 0;
        while(count < index) {
            if (iterator.hasNext()) {
                iterator.next();
                count++;
            } else {
                return null;
            }
        }
        Element<E, V> element = iterator.next();
        iterator.setCurrentElement(null);
        return element.getElement();
    }

    /**
     * This method returns the E part of last element of the list(head).
     * @return    value of E part of last element
     */
    @Override
    public E lastElement() {
        return lastAdded.getElement();
    }

    /**
     * Returns the clone of the current object.
     * @return    clone of current object
     */
    @SuppressWarnings("unchecked")
    public Object clone() {
        StorageDynamic<E, V> storage = new StorageDynamic<>();
        StorageIterator iterator = new StorageIterator(storage);
        storage.setIterator(iterator);
        Element<E, V> element;
        int count = 0;
        while(this.iterator.hasNext()) {
            element = this.iterator.next();
            storage.addElement(element.getElement(), element.getValue());
            if(count == 0) {
                storage.setHead(element);
                count++;
            }
        }
        this.iterator.setCurrentElement(null);
        iterator.setCurrentElement(null);
        return storage;
    }
}
