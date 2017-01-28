
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

// todo
// get a function that gives different values
// use getlocation in iterator, remove, contains
// so make that function good
// think about mean object


public class HashSetNew extends HashSet {
    private int size = 0;
    private Element[] set;
    private int sizeSeed = 4;
    private int maxSize = 1 << 4;
    private int prime = getNextPrime(maxSize);
    public HashSetNew() {
        this.set = new Element[maxSize];
    }


    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean contains(Object o) {
        boolean present = false;
        int index = getLocation(o);
        Element element = this.set[index];
        if (element == null) {
            present = false;
        } else {
            Element e = this.objectLocationInList(element, o);
            if (!(e == null) && e.value.equals(o)) {
                present = true;
            }
        }
        return present;
    }

    @Override
    public boolean add(Object o) {
        if (this.size == this.maxSize) {
            rehash();
        }
        int index = getLocation(o);
        if (addAtLocation(index, o)) {
            this.size++;
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        int index = getLocation(o);
        boolean removed = false;
        Element element = this.set[index];
        if (element == null) {
            removed = false;
        } else {
            Element e = this.objectLocationInList(element, o);
            if (e.value.equals(o)) {
                if (e.previous == null) {
                    set[index] = e.next;
                } else {
                    e.previous.next = e.next;
                    e.next = null;
                }
                this.size--;
                removed = true;
            }
        }
        return removed;
    }


    private int getLocation(Object o) {
        return getHash(o) & this.maxSize - 1;
//        int index = getHash(o) % (this.maxSize - 1);
//        return (index < 0) ?  index * -1 :  index;
    }

    private int getHash(Object o) {
        return ((o.hashCode() * this.prime));
    }

//    public void printIt() throws IOException {
//        FileOutputStream f = new FileOutputStream("result.txt");
//        for (int i = 0; i < set.length; i++) {
//            Element e = set[i];
//            int count = 0;
//            while (e != null) {
//                count++;
//                e = e.next;
//            }
//            f.write( (i + " : " + count).getBytes());
//        }
//    }

    private boolean addAtLocation(int index, Object o) {
        boolean added = false;
        Element element = this.set[index];
        if (element == null) {
            this.set[index] = new Element(o);
            added = true;
        } else {
            Element e = objectLocationInList(element, o);
            if (!(e == null || e.value.equals(o))) {
                Element newElement = new Element(o);
                e.next = newElement;
                newElement.previous = e;
                added = true;
            }
        }
        return added;
    }

    private Element objectLocationInList(Element element, Object o) {
        do {
            if (element.value.equals(o)) {
                return element;
            }
            element = (element.next == null) ? element : element.next;
        } while (element.next != null);
        return element;
    }

    private int getNextPrime(int n) {
        for (int i = n; i < n * 10; i++) {
            if (isPrime(i)) {
                return i;
            }
        }
        return 0;
    }

    private boolean isPrime(int i) {
        for (int j = 2; j < (Math.sqrt(i)); j++) {
            if (i % j == 0) {
                return false;
            }
        }
        return true;
    }

    public void clear() {
        this.size = 0;
        this.sizeSeed = 4;
        this.maxSize = 1 << sizeSeed;
        this.set = new Element[this.maxSize];
    }

    @Override
    public Iterator iterator() {
        return new Iterator();
    }

    private void rehash() {
        Element[] setNew = this.set;
        this.sizeSeed++;
        this.maxSize = 1 << this.sizeSeed;
        this.set = new Element[this.maxSize];
        this.size = 0;
        this.prime = getNextPrime(this.maxSize);
        int index = 0;
        Element e;
        for (int i = 0; i < setNew.length; i++) {
            e = setNew[i];
            while (e != null) {
                this.add(e.value);
                e = e.next;
            }
        }
    }

    class Element {
        Object value;
        Element next;
        Element previous;

        Element(Object o) {
            this.value = o;
            this.next = null;
            this.previous = null;
        }
    }

    class Iterator implements java.util.Iterator {
        Element current;
        int index;

        Iterator() {
            current = getFirstElement();
            index = 0;
        }

        @Override
        public boolean hasNext() {
            return !(current == null);
        }

        @Override
        public Object next() {
            Element e = current;
            setCurrent();
            return e.value;
        }

        @Override
        public void remove() {

        }

        private void setCurrent() {
            if (!(current == null)) {
                if (current.next == null) {
                    index++;
                    current = (index < maxSize) ? set[index] : null;
                } else {
                    current = current.next;
                }
            }
        }

        private Element getFirstElement() {
            int index = 0;
            if (size() == 0) {
                return null;
            }
            Element e = set[index];
            while (e == null) {
                index++;
                e = set[index];
            }
            return e;
        }
    }
}
