package deque;

import java.util.Iterator;

public interface Deque<T> {
    public void addFirst(T item);
    public void addLast(T item);
    public T removeFirst();
    public T removeLast();
    public int size();
    public default boolean isEmpty() {
        return size() == 0;
    }
    public T get(int index);
    public void printDeque();
    public Iterator<T> iterator();
}
