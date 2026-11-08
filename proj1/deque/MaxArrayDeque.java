package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private final Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> comp) {
        this.comparator = comp;
    }

    public T max() {
        return max(this.comparator);
    }

    public T max(Comparator<T> comp) {
        if (isEmpty()) {
            return null;
        }
        T max = get(0);
        for (int i = 0; i < size(); i++) {
            if (comp.compare(max, get(i)) < 0) {
                max = get(i);
            }
        }
        return max;
    }
}
