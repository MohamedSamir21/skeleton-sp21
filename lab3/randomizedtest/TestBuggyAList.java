package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> noResizing = new AListNoResizing<>();
        BuggyAList<Integer> buggyAList = new BuggyAList<>();
        for (int i = 4; i < 7; i++) {
            noResizing.addLast(i);
            buggyAList.addLast(i);
        }
        for (int i = 4; i < 7; i++) {
            assertEquals(noResizing.removeLast(), buggyAList.removeLast());
        }
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> B = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                B.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int sizeL = L.size();
                int sizeB = B.size();
                assertEquals(sizeL, sizeB);
            } else if (operationNumber == 2) {
                if (L.size() > 0) {
                    int lastL = L.getLast();
                    int lastB = B.getLast();
                    assertEquals(lastL, lastB);
                }
            } else if (operationNumber == 3) {
                if (L.size() > 0) {
                    int removedL = L.removeLast();
                    int removedB = B.removeLast();
                    assertEquals(removedL, removedB);
                }
            }
        }
    }
}
