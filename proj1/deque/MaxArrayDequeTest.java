package deque;


import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MaxArrayDequeTest {
    @Test
    public void maxIntegerTest() {
        MaxArrayDeque<Integer> numbers1 = new MaxArrayDeque<>(new IntegerComparator());

        numbers1.addFirst(100);
        numbers1.addFirst(23);
        numbers1.addFirst(89);
        numbers1.addFirst(123);
        numbers1.addFirst(399);
        numbers1.addFirst(200);

        assertEquals(399, (int) numbers1.max());
        assertEquals(399, (int) numbers1.max(Comparator.naturalOrder()));

        MaxArrayDeque<Integer> numbers2 = new MaxArrayDeque<>(new IntegerComparator());

        numbers2.addFirst(-100);
        numbers2.addFirst(-23);
        numbers2.addFirst(-89);
        numbers2.addFirst(-123);
        numbers2.addFirst(-399);
        numbers2.addFirst(-200);

        assertEquals(-23, (int) numbers2.max());
        assertEquals(-23, (int) numbers2.max(Comparator.naturalOrder()));

        MaxArrayDeque<Integer> numbers3 = new MaxArrayDeque<>(new IntegerComparator());

        assertNull(numbers3.max());
        assertNull(numbers3.max(Comparator.naturalOrder()));

        // To ensure that the comparator in max(c) is really used not the constructor's one
        MaxArrayDeque<Integer> numbers4 = new MaxArrayDeque<>(new IntegerComparator());

        numbers4.addFirst(100);
        numbers4.addFirst(23);
        numbers4.addFirst(89);
        numbers4.addFirst(123);
        numbers4.addFirst(399);
        numbers4.addFirst(200);

        assertEquals(23, (int) numbers4.max(Comparator.reverseOrder()));
    }

    @Test
    public void maxStringsTest() {
        MaxArrayDeque<String> fruits1 = new MaxArrayDeque<>(new StringComparator());

        fruits1.addFirst("Orange");
        fruits1.addFirst("Apple");
        fruits1.addFirst("Banana");
        fruits1.addFirst("Strawberry");
        fruits1.addFirst("Mango");
        fruits1.addFirst("Coconut");

        assertEquals("Strawberry", fruits1.max());
        assertEquals("Strawberry", fruits1.max(Comparator.naturalOrder()));


        MaxArrayDeque<String> fruits2 = new MaxArrayDeque<>(new StringComparator());

        fruits2.addFirst("Orange");
        fruits2.addFirst("Coconut");
        fruits2.addFirst("Banana");
        fruits2.addFirst("Apple");
        fruits2.addFirst("Mango");
        fruits2.addLast("Strawberry");

        assertEquals("Strawberry", fruits2.max());
        assertEquals("Strawberry", fruits2.max(Comparator.naturalOrder()));

        MaxArrayDeque<String> fruits3 = new MaxArrayDeque<>(new StringComparator());

        assertNull(fruits3.max());
        assertNull(fruits3.max(Comparator.naturalOrder()));

        // To ensure that the comparator in max(c) is really used not the constructor's one
        MaxArrayDeque<String> fruits4 = new MaxArrayDeque<>(new StringComparator());

        fruits4.addFirst("Orange");
        fruits4.addFirst("Apple");
        fruits4.addFirst("Banana");
        fruits4.addFirst("Strawberry");
        fruits4.addFirst("Mango");
        fruits4.addFirst("Coconut");

        assertEquals("Apple", fruits4.max(Comparator.reverseOrder()));
    }

}