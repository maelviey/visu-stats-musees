package fr.univrennes.istic.l2gen.visustats;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.After;
import fr.univrennes.istic.l2gen.geometrie.Point;
import org.junit.Test;

public class CamembertTest extends TestCase {
    private Camembert cam;

    @Before
    public void setUp() {
        cam = new Camembert(0, 0, 10);
    }

    @After
    public void tearDown() {
        cam = null;
    }

    @Test
    public void testCentre() {
        Point p = new Point(0, 0);
        assertEquals(p, cam.centre());
    }

    @Test
    public void testColorier() {
        String color = "white";
        cam.colorier(color);
        assertEquals(color, cam.couleur);
    }

    @Test
    public void testDeplacer() {
        Point p = new Point(10, 10);
        cam.deplacer(10, 10);
        assertEquals(p, cam.centre);
    }

    @Test
    public void testDupliquer() {
        Camembert copie = (Camembert) cam.dupliquer(); // Duplique le camembert.
        assertEquals(cam, copie); // Compare les objets à travers la méthode surchargée equals.
    }

    @Test
    public void testHauteur() {
        double reference = 20;
        assertEquals(reference, cam.hauteur());
    }

    @Test
    public void testLargeur() {
        double reference = 20;
        assertEquals(reference, cam.largeur());
    }

    @Test
    public void testTourner() {
        cam.tourner(45);
        String svg = cam.enSVG();
        assertTrue(svg.contains("rotate(45.0 0.0 0.0)"));
    }

    @Test
    public void testRedimensionner() {
        assertEquals("<circle cx=\"0.0\" cy=\"0.0\" r=\"10.0\" fill=\"white\" transform=\"rotate(0.0 0.0 0.0)\" /><g>\n" + //
                        "</g>", cam.enSVG());
    }
    @Test
    public void testDescription() {
        assertEquals("", "");
    }

    @Test
    public void testEnSVG() {
        String svg = cam.enSVG();
        assertTrue(svg.startsWith("<circle"));
        assertTrue(svg.contains("cx=\"0.0\""));
        assertTrue(svg.contains("cy=\"0.0\""));
        assertTrue(svg.contains("r=\"10.0\""));
        assertTrue(svg.contains("fill=\"white\""));
    }

    @Test
    public void testAjouterSecteur() {
        cam.ajouterSecteur("blue", 5);
        Camembert caam = new Camembert(0, 0, 10);
        caam.ajouterSecteur("blue", 5);
        assertEquals(cam, caam);
    }
}