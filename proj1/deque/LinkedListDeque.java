package deque;


import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {

    private static class Node<T> {
        private final T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private final Node<T> sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node<>(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    @Override
    public void addFirst(T item) {
        Node<T> oldFirst = sentinel.next;
        Node<T> newFirst = new Node<>(sentinel, item, oldFirst);
        sentinel.next = newFirst;
        oldFirst.prev = newFirst;
        size++;
    }

    @Override
    public void addLast(T item) {
        Node<T> oldLast = sentinel.prev;
        Node<T> newLast = new Node<>(oldLast, item, sentinel);
        sentinel.prev = newLast;
        oldLast.next = newLast;
        size++;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Node<T> removedFirst = sentinel.next;
        T item = removedFirst.item;
        Node<T> newFirst = removedFirst.next;
        sentinel.next = newFirst;
        newFirst.prev = sentinel;
        size--;
        return item;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        Node<T> removedLast = sentinel.prev;
        T item = removedLast.item;
        Node<T> newLast = removedLast.prev;
        sentinel.prev = newLast;
        newLast.next = sentinel;
        size--;
        return item;
    }

    @Override
    public int size() {
        return size;
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < size();
    }

    @Override
    public T get(int index) {
        if (!isValidIndex(index)) {
            return null;
        }
        Node<T> currentNode = sentinel.next;
        for (int currentIndex = 0; currentIndex < index; currentIndex++) {
            currentNode = currentNode.next;
        }
        return currentNode.item;
    }

    /**
     * Given itemIndex is a valid index of the Deque, we return its associated item
     *
     * @param itemIndex is a valid index for the item we want to get its value.
     */
    private T getRecursive(int itemIndex, int currentIndex, Node<T> currentNode) {
        if (currentIndex == itemIndex) {
            return currentNode.item;
        }
        return getRecursive(itemIndex, currentIndex + 1, currentNode.next);
    }

    public T getRecursive(int index) {
        if (isValidIndex(index)) {
            return getRecursive(index, 0, sentinel.next);
        }
        return null;
    }

    @Override
    public void printDeque() {
        Node<T> node = sentinel.next;
        for (int index = 0; isValidIndex(index); index++) {
            System.out.print(node.item + " ");
            node = node.next;
        }
        System.out.println();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Deque)) {
            return false;
        }
        Deque<T> oDeque = (Deque<T>) o;
        if (this.size() != oDeque.size()) {
            return false;
        }
        Iterator<T> iterator1 = iterator();
        Iterator<T> iterator2 = oDeque.iterator();
        while (iterator1.hasNext()) {
            if (!(iterator1.next().equals(iterator2.next()))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private Node<T> node = sentinel.next;
        private int pos;

        @Override
        public boolean hasNext() {
            return pos < size();
        }

        @Override
        public T next() {
            T item = node.item;
            node = node.next;
            pos++;
            return item;
        }
    }
}
