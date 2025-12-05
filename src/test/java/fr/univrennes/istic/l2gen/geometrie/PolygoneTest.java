package fr.univrennes.istic.l2gen.geometrie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class PolygoneTest {

    @Test
    public void testAjouterSommet1() {
        Polygone polygone = new Polygone();
        polygone.ajouterSommet(new Point(1.0, 2.0));
        polygone.ajouterSommet(new Point(3.0, 4.0));
        polygone.ajouterSommet(new Point(5.0, 6.0));
        List<Point> sommets = polygone.getSommets();
        assertEquals(3, sommets.size());
        assertTrue(new Point(1.0, 2.0).equals(sommets.get(0)));
        assertTrue(new Point(3.0, 4.0).equals(sommets.get(1)));
        assertTrue(new Point(5.0, 6.0).equals(sommets.get(2)));
    }

    @Test
    public void testAjouterSommet2() {
        Polygone polygone = new Polygone();
        polygone.ajouterSommet(1.0, 2.0);
        polygone.ajouterSommet(3.0, 4.0);
        polygone.ajouterSommet(5.0, 6.0);
        List<Point> sommets = polygone.getSommets();
        assertEquals(3, sommets.size());
        assertTrue(new Point(1.0, 2.0).equals(sommets.get(0)));
        assertTrue(new Point(3.0, 4.0).equals(sommets.get(1)));
        assertTrue(new Point(5.0, 6.0).equals(sommets.get(2)));
    }

    @Test
    public void testCoordonneesExtremes() {
        Polygone polygone = new Polygone(Double.MAX_VALUE, Double.MAX_VALUE, Double.MIN_VALUE, Double.MIN_VALUE);
        assertEquals(Double.MAX_VALUE, polygone.largeur(), 0.01);
        assertEquals(Double.MAX_VALUE, polygone.hauteur(), 0.01);
    }

    @Test
    public void testCentre() {
        Polygone polygone = new Polygone();
        polygone.ajouterSommet(1.0, 2.0);
        polygone.ajouterSommet(3.0, 2.0);
        polygone.ajouterSommet(3.0, 4.0);
        polygone.ajouterSommet(1.0, 4.0);
        Point centre = polygone.centre();
        assertTrue(new Point(2.0, 3.0).equals(centre));
    }

    @Test
    public void testColorier() {
        Polygone polygone = new Polygone();
        polygone.ajouterSommet(1.0, 2.0);
        polygone.colorier("yellow");
        assertEquals("yellow", polygone.color);
    }

    @Test
    public void testDeplacer() {
        Polygone polygone = new Polygone();
        polygone.ajouterSommet(1.0, 2.0);
        polygone.ajouterSommet(3.0, 4.0);
        polygone.ajouterSommet(5.0, 6.0);
        polygone.deplacer(1.0, 2.0);
        List<Point> sommets = polygone.getSommets();
        assertEquals(3, sommets.size());
        assertTrue(new Point(2.0, 4.0).equals(sommets.get(0)));
        assertTrue(new Point(4.0, 6.0).equals(sommets.get(1)));
        assertTrue(new Point(6.0, 8.0).equals(sommets.get(2)));
    }

    @Test
    public void testDescription() {
        Polygone polygone = new Polygone();
        polygone.ajouterSommet(1.0, 2.0);
        polygone.ajouterSommet(3.0, 4.0);
        polygone.ajouterSommet(5.0, 6.0);
        assertEquals("Polygone  1,2  3,4  5,6", polygone.description(2));
    }

    @Test
    public void testDupliquer() {
        Polygone original = new Polygone(0, 0, 1, 1, 2, 0);
        Polygone copie = (Polygone) original.dupliquer();
        // corriger la ligne suivante
        assertTrue(original != copie); // La copie doit être unn objet différent
        assertTrue(original.getSommets().size() == copie.getSommets().size());
        assertTrue(original.color == copie.color);

        for (int i = 0; i < original.getSommets().size(); i++) {
            assertTrue(original.getSommets().get(i).x() == copie.getSommets().get(i).x());
            assertTrue(original.getSommets().get(i).y() == copie.getSommets().get(i).y());
        }
    }

    @Test
    public void testDupliquerPolygoneVide() {
        Polygone polygoneVide = new Polygone();
        Polygone copie = (Polygone) polygoneVide.dupliquer();
        assertTrue("Un polygone vide dupliqué doit rester vide", copie.getSommets().isEmpty());
    }

    @Test
    public void testEnSVG() {
        Polygone polygone = new Polygone(0, 0, 10, 0, 10, 10, 0, 10);
        String svg = polygone.enSVG();

        assertTrue(svg.contains("<polygon points=\""));
        assertEquals(svg,
                "< polygon points = \"<polygon points=\"0.0 0.0 10.0 0.0 10.0 10.0 0.0 10.0\" fill=\"white \" stroke=\"black\"/>");
    }

    @Test
    public void testGetSommets() {
        Polygone polygone = new Polygone(0, 0, 10, 0, 10, 10);
        List<Point> sommets = polygone.getSommets();

        assertTrue(3 == sommets.size());
        assertTrue(0 == sommets.get(0).x());
        assertTrue(0 == sommets.get(0).y());
        assertTrue(10 == sommets.get(1).x());
        assertTrue(0 == sommets.get(1).y());
        assertTrue(10 == sommets.get(2).x());
        assertTrue(10 == sommets.get(2).y());
    }

    @Test
    public void testHauteur() {
        Polygone polygone = new Polygone(0, 0, 5, 10, 10, 5);
        assertTrue(10 == polygone.hauteur());
    }

    @Test
    public void testLargeur() {
        Polygone polygone = new Polygone(0, 0, 5, 10, 10, 5);
        assertTrue(10 == polygone.largeur());
    }

    @Test
    public void testRedimensionner() {
        Polygone polygone = new Polygone(1, 1, 2, 2, 3, 3);
        polygone.redimensionner(2.0, 2.0);

        List<Point> sommets = polygone.getSommets();
        assertTrue(2.0 == sommets.get(0).x());
        assertTrue(2.0 == sommets.get(0).y());
        assertTrue(4.0 == sommets.get(1).x());
        assertTrue(4.0 == sommets.get(1).y());
        assertTrue(6.0 == sommets.get(2).x());
        assertTrue(6.0 == sommets.get(2).y());
    }

    @Test
    public void testTournicoter() {
        Polygone polygone = new Polygone(2, 2, 2, 4, 4, 4, 4, 2);
        polygone.tournicoter(180);

        List<Point> sommets = polygone.getSommets();
        assertTrue(4 == sommets.get(0).x());
        assertTrue(4 == sommets.get(0).y());
        assertTrue(4 == sommets.get(1).x());
        assertTrue(2 == sommets.get(1).y());
        assertTrue(2 == sommets.get(2).x());
        assertTrue(2 == sommets.get(2).y());
        assertTrue(2 == sommets.get(3).x());
        assertTrue(4 == sommets.get(3).y());
    }

    @Test
    public void testAligner() {
        Polygone polygone = new Polygone(2, 2, 2, 4, 4, 4, 4, 2);
        polygone.aligner(Alignement.BAS, 5);

        List<Point> sommets = polygone.getSommets();
        assertTrue(2 == sommets.get(0).x());
        assertTrue(5 == sommets.get(0).y());
        assertTrue(2 == sommets.get(1).x());
        assertTrue(7 == sommets.get(1).y());
        assertTrue(4 == sommets.get(2).x());
        assertTrue(7 == sommets.get(2).y());
        assertTrue(4 == sommets.get(3).x());
        assertTrue(5 == sommets.get(3).y());

        polygone.aligner(Alignement.DROITE, 7);

        List<Point> sommets2 = polygone.getSommets();
        assertTrue(5 == sommets2.get(0).x());
        assertTrue(5 == sommets2.get(0).y());
        assertTrue(5 == sommets2.get(1).x());
        assertTrue(7 == sommets2.get(1).y());
        assertTrue(7 == sommets2.get(2).x());
        assertTrue(7 == sommets2.get(2).y());
        assertTrue(7 == sommets2.get(3).x());
        assertTrue(5 == sommets2.get(3).y());

    }
}
