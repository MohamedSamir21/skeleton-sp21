package deque;


public class LinkedListDeque<T> {
    private static class Node<T> {
        private T item;
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

    public void addFirst(T item) {
        Node<T> oldFirst = sentinel.next;
        Node<T> newFirst = new Node<>(sentinel, item, oldFirst);
        sentinel.next = newFirst;
        oldFirst.prev = newFirst;
        size++;
    }

    public void addLast(T item) {
        Node<T> oldLast = sentinel.prev;
        Node<T> newLast = new Node<>(oldLast, item, sentinel);
        sentinel.prev = newLast;
        oldLast.next = newLast;
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

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

    public int size() {
        return size;
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < size();
    }

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

    public void printDeque() {
        Node<T> node = sentinel.next;
        for (int index = 0; isValidIndex(index); index++) {
            System.out.print(node.item + " ");
            node = node.next;
        }
        System.out.println();
    }
}
