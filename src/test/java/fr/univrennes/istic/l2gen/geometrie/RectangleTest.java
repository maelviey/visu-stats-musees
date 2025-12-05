package fr.univrennes.istic.l2gen.geometrie;

import org.junit.Test;
import static org.junit.Assert.*;

public class RectangleTest {

    @Test
    public void RectangleConstructorTest() {
        Rectangle recTest1 = new Rectangle(5, 6, 8, 9);
        Rectangle recTest2 = new Rectangle(new Point(5, 6), 8, 9);
        assertTrue(recTest1.centre().x() == recTest2.centre().x());
        assertTrue(recTest1.centre().y() == recTest2.centre().y());
        assertTrue(recTest1.largeur() == recTest2.largeur());
        assertTrue(recTest1.hauteur() == recTest2.hauteur());
        assertEquals(recTest1.color, recTest2.color);
    }

    @Test
    public void centreTest() {
        Point centreTest = new Point(5, 6);
        Rectangle recTest = new Rectangle(5, 6, 8, 9);
        assertTrue(centreTest.x() == recTest.centre().x());
        assertTrue(centreTest.y() == recTest.centre().y());
    }

    @Test
    public void deplacerTest() {
        Rectangle recTest = new Rectangle(5, 6, 8, 9);
        recTest.deplacer(3, 4);
        Point expectedCentre = new Point(8, 10);
        assertEquals(expectedCentre, recTest.centre());
    }

    @Test
    public void dupliquerTestSame() {
        Rectangle recTest = new Rectangle(5, 6, 8, 9);
        Rectangle duplicatedRec = (Rectangle) recTest.dupliquer();
        assertNotSame(recTest, duplicatedRec);
    }

    @Test
    public void testDupliquerRectangleVide() {
        Rectangle recVide = new Rectangle(5, 6, 0, 0);
        Rectangle recCopie = (Rectangle) recVide.dupliquer();
        assertEquals("Un rectangle vide dupliqué doit rester vide", 0, recCopie.largeur(), 0.001);
        assertEquals("Un rectangle vide dupliqué doit rester vide", 0, recCopie.hauteur(), 0.001);
    }

    @Test
    public void redimensionnerTest() {
        Rectangle recTest = new Rectangle(5, 6, 8, 9);
        recTest.redimensionner(2, 3);
        assertTrue(16 == recTest.largeur());
        assertTrue(27 == recTest.hauteur());
    }

    @Test
    public void testCoordonneesExtremes() {
        Rectangle recExtreme = new Rectangle(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
        assertEquals(Double.MAX_VALUE, recExtreme.largeur(), 0.01);
        assertEquals(Double.MAX_VALUE, recExtreme.hauteur(), 0.01);
    }

    @Test
    public void enSVGTest() {
        Rectangle recTest = new Rectangle(5, 6, 8, 9);
        String expectedSVG = "<rect x=\"1.0\" y=\"1.5\" width=\"8.0\" height=\"9.0\" fill=\"black\" stroke=\"black\"/>";
        assertEquals(expectedSVG, recTest.enSVG());
    }

    @Test
    public void colorierTest() {
        Rectangle recTest = new Rectangle(5, 6, 8, 9);
        recTest.colorier("red");
        assertEquals("red", recTest.color);
    }

    @Test
    public void testHauteur() {
        Rectangle recTest1 = new Rectangle(5, 6, 8, 9);
        Rectangle recTest2 = new Rectangle(new Point(5, 6), 8, 9);
        assertEquals(9, recTest1.hauteur(), 1);
        assertEquals(9, recTest2.hauteur(), 1);
    }

    @Test
    public void testLargeur() {
        Rectangle recTest1 = new Rectangle(5, 6, 8, 9);
        Rectangle recTest2 = new Rectangle(new Point(5, 6), 8, 9);
        assertEquals(8, recTest1.largeur(), 1);
        assertEquals(8, recTest2.largeur(), 1);

    }

    @Test
    public void testTourner() {
        Rectangle recTest = new Rectangle(5, 6, 8, 9);
        recTest.tourner(90);
        String aTester = recTest.enSVG();
        assertEquals(
                "<rect x=\"1.0\" y=\"1.5\" width=\"8.0\" height=\"9.0\" fill=\"black\" transform=\"rotate(90,5.0,6.0)\" stroke=\"black\"/>",
                aTester);
    }

   

    @Test
    public void testTourner2() {
        Rectangle recTest = new Rectangle(5, 6, 8, 9);
        recTest.tourner(270);
        String aTester = recTest.enSVG();
        assertEquals(
                "<rect x=\"1.0\" y=\"1.5\" width=\"8.0\" height=\"9.0\" fill=\"black\" transform=\"rotate(270,5.0,6.0)\" stroke=\"black\"/>",
                aTester);
    }

    @Test
    public void testTourner3() {
        Rectangle recTest = new Rectangle(5, 6, 8, 9);
        recTest.tourner(0);
        String aTester = recTest.enSVG();
        assertEquals("<rect x=\"1.0\" y=\"1.5\" width=\"8.0\" height=\"9.0\" fill=\"black\" stroke=\"black\"/>",
                aTester);
    }

    @Test
    public void testAligner() {
        Rectangle recTest = new Rectangle(5, 6, 8, 9);
        recTest.aligner(Alignement.HAUT, 10);
        assertEquals(5.5, recTest.centre().y, 1);
    }

    @Test
    public void testAligner2() {
        Rectangle recTest = new Rectangle(5, 6, 8, 9);
        recTest.aligner(Alignement.BAS, 10);
        assertEquals(14.5, recTest.centre().y, 1);
    }

    @Test
    public void testAligner3() {
        Rectangle recTest = new Rectangle(5, 6, 8, 9);
        recTest.aligner(Alignement.DROITE, 10);
        assertEquals(6, recTest.centre().x, 1);
    }

    @Test
    public void testAligner4() {
        Rectangle recTest = new Rectangle(5, 6, 8, 9);
        recTest.aligner(Alignement.GAUCHE, 10);
        assertEquals(14, recTest.centre().x, 1);
    }

}
