package fr.univrennes.istic.l2gen.geometrie;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

public class PointTest {

    private Point point;

    @Before
    public void setUp() {
        point = new Point(3.0, 4.0);

    }

    @After
    public void tearDown() {
        point = null;
    }

    @Test
    public void testConstructeur() {
        assertEquals(3.0, point.x(), 0.001);
        assertEquals(4.0, point.y(), 0.001);
    }

    @Test
    public void testEquals() {
        Point pointIdentique = new Point(3.0, 4.0);
        Point pointDifferent = new Point(5.0, 6.0);

        assertTrue(point.equals(pointIdentique));
        assertFalse(point.equals(pointDifferent));
        assertFalse(point.equals(null));
        assertFalse(point.equals(new Object()));
    }

    @Test
    public void testValeursExtremes() {
        Point pointExtremes = new Point(Double.MAX_VALUE, Double.MIN_VALUE);
        assertEquals(Double.MAX_VALUE, pointExtremes.x(), 0.001);
        assertEquals(Double.MIN_VALUE, pointExtremes.y(), 0.001);
    }

    @Test
    public void testHashCode() {
        Point pointIdentique = new Point(3.0, 4.0);
        assertEquals(point.hashCode(), pointIdentique.hashCode());
    }

    @Test
    public void testHashCodeApresModification() {
        int hashAvant = point.hashCode();
        point.plus(1.0, 1.0);
        int hashApres = point.hashCode();
        assertNotEquals(hashAvant, hashApres);
    }

    @Test
    public void testPlusAvecPoint() {
        point.plus(new Point(1.0, 2.0));
        assertEquals(4.0, point.x(), 0.001);
        assertEquals(6.0, point.y(), 0.001);
    }

    @Test
    public void testPlusAvecPointNul() {
        point.plus(new Point(0.0, 0.0));
        assertEquals(3.0, point.x(), 0.001);
        assertEquals(4.0, point.y(), 0.001);
    }

    @Test
    public void testPlusAvecValeursNegatives() {
        point.plus(-1.0, -2.0);
        assertEquals(2.0, point.x(), 0.001);
        assertEquals(2.0, point.y(), 0.001);
    }

    @Test
    public void testPlusAvecCoordonnees() {
        point.plus(2.0, 3.0);
        assertEquals(5.0, point.x(), 0.001);
        assertEquals(7.0, point.y(), 0.001);
    }
}
