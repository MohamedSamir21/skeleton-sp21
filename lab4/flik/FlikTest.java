package flik;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FlikTest {
    private static final int N1 = 128;
    private static final int N2 = -129;

    @Test
    public void testIsSameNumber() {
        assertTrue(Flik.isSameNumber(1, 1));
        assertTrue(Flik.isSameNumber(Integer.valueOf(1), Integer.valueOf(1)));
        assertFalse(Flik.isSameNumber(Integer.valueOf(1), Integer.valueOf(2)));
        assertTrue(Flik.isSameNumber(N1, N1));
        assertTrue(Flik.isSameNumber(N2, N2));
    }
}
