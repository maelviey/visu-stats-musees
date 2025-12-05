package fr.univrennes.istic.l2gen.geometrie;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

public class CercleTest {

    private Cercle cercle;

    @Before
    public void setUp() {
        // Initialisation d'un cercle avant chaque test
        cercle = new Cercle(0, 0, 10);
    }

    @After
    public void tearDown() {
        // Nettoyage après chaque test
        cercle = null;
    }

    @Test
    public void testCentre() {
        Point expected = new Point(0, 0);
        assertEquals("Le centre du cercle devrait être (0,0)", expected, cercle.centre());
    }

    @Test
    public void testHauteur() {
        assertEquals("La hauteur du cercle devrait être le double du rayon", 20, cercle.hauteur(), 0.001);
    }

    @Test
    public void testLargeur() {
        assertEquals("La largeur du cercle devrait être le double du rayon", 20, cercle.largeur(), 0.001);
    }

    @Test
    public void testDeplacer() {
        cercle.deplacer(5, 5);
        Point expected = new Point(5, 5);
        assertEquals("Le centre du cercle après déplacement devrait être (5,5)", expected, cercle.centre());
    }

    @Test
    public void testRedimensionner() {
        cercle.redimensionner(2, 2);
        assertEquals("La hauteur du cercle après redimensionnement devrait être 40", 40, cercle.hauteur(), 0.001);
        assertEquals("La largeur du cercle après redimensionnement devrait être 40", 40, cercle.largeur(), 0.001);
    }

    @Test
    public void testRedimensionnerExtreme() {
        cercle.redimensionner(0, 0);
        assertEquals("Le rayon ne doit pas devenir zéro", 10, cercle.radius, 0.001);

        cercle.redimensionner(-2, -2);
        assertEquals("Le rayon ne doit pas être négatif", 10, cercle.radius, 0.001);
    }

    @Test
    public void testDupliquer() {
        Cercle duplicate = (Cercle) cercle.dupliquer();
        assertEquals("Le centre du cercle dupliqué devrait être identique", cercle.centre(), duplicate.centre());
        assertEquals("La hauteur du cercle dupliqué devrait être identique", cercle.hauteur(), duplicate.hauteur(),
                0.001);
        assertEquals("La largeur du cercle dupliqué devrait être identique", cercle.largeur(), duplicate.largeur(),
                0.001);
        assertEquals("La description du cercle dupliqué devrait être identique", cercle.description(0),
                duplicate.description(0));
    }

    @Test
    public void testEnSVG() {
        String expected = "<circle cx=\"0.0\" cy=\"0.0\" r=\"10.0\"\nfill=\"white\" stroke=\"black\"/>";
        assertEquals("La représentation SVG du cercle n'est pas correcte", expected, cercle.enSVG());
    }

    @Test
    public void testSVG_AvecRotation() {
        cercle.tourner(45);
        String expected = "<circle cx=\"0.0\" cy=\"0.0\" r=\"10.0\"\nfill=\"white\" stroke=\"black\"\ntransform=\"rotate(45)\"/>";
        assertEquals("La représentation SVG après rotation n'est pas correcte", expected, cercle.enSVG());
    }

    @Test
    public void testTourner() {
        cercle.tourner(45);
        assertEquals(45, cercle.angle, 0.01);

    }

    @Test
    public void testAligner() {

        cercle.aligner(Alignement.HAUT, 10);
        assertEquals(10 - cercle.radius, cercle.center.y(), 0.01);

        cercle.aligner(Alignement.BAS, -10);
        assertEquals(-10 + cercle.radius, cercle.center.y(), 0.01);

        cercle.aligner(Alignement.DROITE, 10);
        assertEquals(10 + cercle.radius, cercle.center.x(), 0.01);

        cercle.aligner(Alignement.GAUCHE, -10);
        assertEquals(-10 - cercle.radius, cercle.center.x(), 0.01);
    }

}