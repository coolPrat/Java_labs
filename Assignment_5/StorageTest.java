/*
 * StorageTest.java
 *
 * Version: 1.0: StorageTest.java,v 1.0 9/26/2015 10:19:23
 *
 * Revisions: 1.0 initial version
 */

import java.util.Random;

public class StorageTest {
    /**
     * This class tests the storageFixed and storageDynamic classes
     *
     *
     * @author      Pratik kulkarni
     */
    Storage<String, String> aStorageString = new StorageFixed<>();
    // object of StorageFixed with E = V = String
    Storage<Integer, String> aStorageInteger = new StorageFixed<>();
    // object of StorageFixed with E = Integer, V = String
    Storage<String, String> aDynamicStorageString = new StorageDynamic<>();
    // object of StorageDynamic with E = V = String
    Storage<Integer, String> aDynamicStorageInteger = new StorageDynamic<>();
    // object of StorageDynamic with E = Integer, V = String

    /**
     * Test for add methods.
     * This will test add(E element), addElement(E element), add(int index, E element)
     * and addElement(E element, V value)
     */
    public void addTest(){

        // testing add methods of StorageFixed<String, String>
        for (int index = 0; index <= 25; index++) {
            aStorageString.add("String " + index + " part 0");
            aStorageString.addElement("String " + index + "part 1");
            aStorageString.add(index+1, "String " + index + "part 2");
            aStorageString.addElement("String " + index + " part 3", "String " + index + " part 3");
        }
        if (aStorageString.add("100")) {
            System.out.println("addTest Failed!");
        } else {
            System.out.println("addTest successful for aStorageString.");
        }

        // testing add methods of StorageFixed<Integer, String>
        for (int index = 0; index <= 99; index += 4) {
            aStorageInteger.add(index);
            aStorageInteger.addElement(index + 1);
            aStorageInteger.add(index + 1, index + 2);
            aStorageInteger.addElement(index + 3, String.valueOf(index + 3));
        }
        if (aStorageInteger.add(100)) {
            System.out.println("addTest Failed!");
        } else {
            System.out.println("addTest successful for aStorageInteger.");
        }

        // testing add methods of StorageDynamic<String, String>
        for (int index = 0; index <= 25; index++) {
            aDynamicStorageString.add("String " + index + " part 0");
            aDynamicStorageString.addElement("String " + index + " part 1");
            aDynamicStorageString.add(index + 1, "String " + index + " part 2");
            aDynamicStorageString.addElement("String " + index + " part 3", "String " + index + " part 3");
        }

        if (aDynamicStorageString.add("100")) {
            System.out.println("addTest successful for aDynamicStorageString.");
        } else {
            System.out.println("addTest Failed!");
        }
        // testing add methods of StorageDynamic<Integer, String>
        for (int index = 0; index <= 99; index += 4) {
            aDynamicStorageInteger.add(index);
            aDynamicStorageInteger.addElement(index + 1);
            aDynamicStorageInteger.add(index + 1, index + 2);
            aDynamicStorageInteger.addElement(index + 3, String.valueOf(index + 3));
        }
        if (aDynamicStorageInteger.add(100)) {
            System.out.println("addTest successful for aDynamicStorageInteger.");
        } else {
            System.out.println("addTest Failed!");
        }
    }


    /**
     * Test for capacity() and clone() functions
     */
    public void testClearAndCapacity() {
        // testing methods of StorageFixed<String, String>
        System.out.println("Before clear() -> Fixed Storage capacity: " + aStorageString.capacity());
        aStorageString.clear();
        System.out.println("After clear() -> Fixed Storage capacity: " + aStorageString.capacity());
        if (aStorageString.capacity() == 0) {
            System.out.println("testClearAndCapacity successful for aStorageString.");
        } else {
            System.out.println("testClearAndCapacity failed for aStorageString.");
        }

        // testing methods of StorageFixed<Integer, String>
        System.out.println("Before clear() -> Fixed Storage capacity: " + aStorageInteger.capacity());
        aStorageInteger.clear();
        System.out.println("After clear() -> Fixed Storage capacity: " + aStorageInteger.capacity());
        if (aStorageInteger.capacity() == 0) {
            System.out.println("testClearAndCapacity successful for aStorageInteger.");
        } else {
            System.out.println("testClearAndCapacity failed for aStorageInteger.");
        }

        // testing methods of StorageDynamic<String, String>
        System.out.println("Before clear() -> Dynamic Storage capacity: " + aDynamicStorageString.capacity());
        aDynamicStorageString.clear();
        System.out.println("After clear() -> Dynamic Storage capacity: " + aDynamicStorageString.capacity());
        if (aDynamicStorageString.capacity() == 0) {
            System.out.println("testClearAndCapacity successful for aDynamicStorageString.");
        } else {
            System.out.println("testClearAndCapacity failed for aDynamicStorageString.");
        }

        // testing methods of StorageDynamic<Integer, String>
        System.out.println("Before clear() -> Dynamic Storage capacity: " + aDynamicStorageInteger.capacity());
        aDynamicStorageInteger.clear();
        System.out.println("After clear() -> Dynamic Storage capacity: " + aDynamicStorageInteger.capacity());
        if (aDynamicStorageInteger.capacity() == 0) {
            System.out.println("testClearAndCapacity successful for aDynamicStorageInteger.");
        } else {
            System.out.println("testClearAndCapacity failed for aDynamicStorageInteger.");
        }
    }

    /**
     * Test for get(), first(), last() methods
     */
    public void testFirstGetLast() {
        int index = 57;

        // testing methods of StorageFixed<String, String>
        boolean first = aStorageString.firstElement().equals("String 0 part 0");
        boolean get = aStorageString.get(index).equals("String 10 part 3");
        boolean last = aStorageString.lastElement().equals("String 24 part 3");
        if (first && get && last) {
            System.out.println("testFirstGetLast successful for aDynamicStorageString.");
        } else {
            System.out.println("testFirstGetLast failed for aDynamicStorageString.");
        }

        // testing methods of StorageFixed<Integer, String>
        first = aStorageInteger.firstElement() == 0;
        get = aStorageInteger.get(index) == 58;
        last = aStorageInteger.lastElement() == 99;
        if (first && get && last) {
            System.out.println("testFirstGetLast successful for aDynamicStorageInteger.");
        } else {
            System.out.println("testFirstGetLast failed for aDynamicStorageInteger.");
        }

        // testing methods of StorageDynamic<String, String>
        first = aDynamicStorageString.firstElement().equals("String 0 part 0");
        get = aDynamicStorageString.get(index).equals("String 10 part 1");
        last = aDynamicStorageString.lastElement().equals("100");
        if (first && get && last) {
            System.out.println("testFirstGetLast successful for aDynamicStorageString.");
        } else {
            System.out.println("testFirstGetLast failed for aDynamicStorageString.");
        }

        // testing methods of StorageDynamic<Integer, String>
        first = aDynamicStorageInteger.firstElement() == 0;
        get = aDynamicStorageInteger.get(index) == 58;
        last = aDynamicStorageInteger.lastElement() == 100;
        if (first && get && last) {
            System.out.println("testFirstGetLast successful for aDynamicStorageInteger.");
        } else {
            System.out.println("testFirstGetLast failed for aDynamicStorageInteger.");
        }
    }

    /**
     * Test for clone method
     */
    @SuppressWarnings("unchecked")
    public void testClone() {
        Storage<String, String> newStorageString = (Storage<String, String>)aStorageString.clone();
        Storage<Integer, String> newStorageInteger = (Storage<Integer, String>)aStorageInteger.clone();
        boolean first,  get, last;
        int randomInt = new Random().nextInt(100);
        first = aStorageString.firstElement().equals(newStorageString.firstElement());
        last = aStorageString.lastElement().equals(newStorageString.lastElement());
        get = aStorageString.get(randomInt).equals(newStorageString.get(randomInt));
        if (first && get && last) {
            System.out.println("testClone successful for aDynamicStorageString.");
        } else {
            System.out.println("testClone failed for aDynamicStorageString.");
        }

        first = aStorageInteger.firstElement().equals(newStorageInteger.firstElement());
        last = aStorageInteger.lastElement().equals(newStorageInteger.lastElement());
        get = aStorageInteger.get(randomInt).equals(newStorageInteger.get(randomInt));
        if (first && get && last) {
            System.out.println("testClone successful for aDynamicStorageInteger.");
        } else {
            System.out.println("testClone failed for aDynamicStorageInteger.");
        }

        Storage<String, String> newDynamicStorageString = (Storage<String, String>) aDynamicStorageString.clone();
        Storage<Integer, String> newDynamicStorageInteger = (Storage<Integer, String>) aDynamicStorageInteger.clone();

        first = aDynamicStorageString.firstElement().equals(newDynamicStorageString.firstElement());
        last = aDynamicStorageString.lastElement().equals(newDynamicStorageString.lastElement());
        get = aDynamicStorageString.get(randomInt).equals(newDynamicStorageString.get(randomInt));
        if (first && get && last) {
            System.out.println("testClone successful for aDynamicStorageString.");
        } else {
            System.out.println("testClone failed for aDynamicStorageString.");
        }

        first = aDynamicStorageInteger.firstElement().equals(newDynamicStorageInteger.firstElement());
        last = aDynamicStorageInteger.lastElement().equals(newDynamicStorageInteger.lastElement());
        get = aDynamicStorageInteger.get(randomInt).equals(newDynamicStorageInteger.get(randomInt));
        if (first && get && last) {
            System.out.println("testClone successful for aDynamicStorageInteger.");
        } else {
            System.out.println("testClone failed for aDynamicStorageInteger.");
        }
    }

    /**
     * The main program.
     * @param args    (ignored)
     */
    public static void main(String args[])	{
        StorageTest storageTest = new StorageTest();
        storageTest.addTest();
        System.out.println("-------------------------------------");
        storageTest.testFirstGetLast();
        System.out.println("-------------------------------------");
        storageTest.testClone();
        System.out.println("-------------------------------------");
        storageTest.testClearAndCapacity();

    }
}
