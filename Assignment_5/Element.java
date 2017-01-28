/*
 * Element.java
 *
 * Version: 1.0: Element.java,v 1.0 9/26/2015 10:19:23
 *
 * Revisions: 1.0 initial version
 */
public class Element<E, V> {
    /**
     * The Element class holds two values of type E and V.
     * Also the pointer to next element in the string.
     *
     * @author      Pratik kulkarni
     */
    private E element; // Placeholder for element of E
    private V value; // Placeholder for element of V
    private Element<E, V> nextElement; // Pointer to next element

    /**
     * Constructor for Element class, This takes element of type E as argument
     * @param element    element that needs to be added
     */
    public Element(E element) {
        this.element = element;
    }

    /**
     * Constructor of Element class.
     * @param element    element of E that needs to be added
     * @param value      element of V that needs to be added
     */
    public Element(E element, V value) {
        this.element = element;
        this.value = value;
    }

    /**
     * This method sets the value of element
     * @param element    element of E that needs to be added
     */
    public void setElement(E element) {
        this.element = element;
    }

    /**
     * Setter for value of type V.
     * @param value    element of V that needs to be added
     */
    public void setValue(V value) {
        this.value = value;
    }

    /**
     * Setter pointer for next element.
     * @param e    next element
     */
    public void setNextElement(Element<E, V> e) {
        this.nextElement = e;
    }

    /**
     * Getter for next element's pointer
     * @return    Element that points to next element
     */
    public Element<E, V> getNextElement() {
        return this.nextElement;
    }

    /**
     * Getter for element E
     * @return    element of type E
     */
    public E getElement() {
        return element;
    }

    /**
     * Getter for element V
     * @return    element of type V
     */
    public V getValue() {
        return value;
    }
}
