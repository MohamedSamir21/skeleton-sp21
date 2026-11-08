package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        nextFirst = items.length - 1;
    }

    /**
     * Whether items is full or not. It's not full if it has at least one empty slot.
     */
    private boolean isFull() {
        return size == items.length;
    }

    /**
     * Copy items from source to destination. It doesn't matter whether the length of source
     * > or == or < the length of destination for copyTo() to work.
     * invariants:
     * # destination could fit all non-null items in source.
     * # between nextFirst and nextLast, there are (size) items
     *
     */
    private void copyTo(T[] source, T[] destination) {
        int destinationIndex = 0;
        for (int i = 0, nextIndex = plusOne(nextFirst); i < size; i++) {
            T item = source[nextIndex];
            destination[destinationIndex++] = item;
            nextIndex = (++nextIndex % items.length);
        }
    }

    private void resetNextFirst() {
        nextFirst = items.length - 1;
    }

    private void resetNextLast() {
        nextLast = size;
    }

    private void resize(int capacity) {
        T[] temp = (T[]) new Object[capacity];
        copyTo(items, temp);
        items = temp;
        resetNextFirst();
        resetNextLast();
    }

    @Override
    public void addFirst(T item) {
        if (isFull()) {
            resize(size * 2);
        }
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size++;
    }

    @Override
    public void addLast(T item) {
        if (isFull()) {
            resize(size * 2);
        }
        items[nextLast] = item;
        nextLast = plusOne(nextLast);
        size++;
    }

    private double usage() {
        return (size * 1.0) / items.length;
    }

    private int plusOne(int index) {
        return (index + 1) % items.length;
    }

    private int minusOne(int index) {
        return (index - 1 + items.length) % items.length;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        nextFirst = plusOne(nextFirst);
        T item = items[nextFirst];
        items[nextFirst] = null;
        size--;
        if (items.length >= 16 && usage() < .25) {
            resize(items.length / 2);
        }
        return item;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        nextLast = minusOne(nextLast);
        T item = items[nextLast];
        items[nextLast] = null;
        size--;
        if (items.length >= 16 && usage() < .25) {
            resize(items.length / 2);
        }
        return item;
    }

    @Override
    public T get(int index) {
        if (index >= 0 && index < size) {
            return items[plusOne(nextFirst + index)];
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Deque)) {
            return false;
        }
        Deque<T> oDeque = (Deque<T>) o;
        if (oDeque.size() != this.size()) {
            return false;
        }
        for (int i = 0; i < size(); i++) {
            if (!get(i).equals(oDeque.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int pos;

        @Override
        public boolean hasNext() {
            return pos < size();
        }

        @Override
        public T next() {
            return get(pos++);
        }
    }
}
