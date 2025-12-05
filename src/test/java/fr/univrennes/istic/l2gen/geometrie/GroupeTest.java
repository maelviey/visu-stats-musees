package fr.univrennes.istic.l2gen.geometrie;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

public class GroupeTest {

    private Groupe groupe;
    private IForme rectangle;
    private IForme cercle;
    private IForme ligne;

    @Before
    public void setUp() {
        // Initialisation des formes avant chaque test
        groupe = new Groupe();
        rectangle = new Rectangle(new Point(0, 0), 2, 2);
        cercle = new Cercle(new Point(5, 5), 3);
        ligne = new Ligne(0, 0, 2, 2);

        // Ajout des formes au groupe
        groupe.ajouter(rectangle);
        groupe.ajouter(cercle);
        groupe.ajouter(ligne);
    }

    @After
    public void tearDown() {
        // Nettoyage des ressources après chaque test
        groupe = null;
        rectangle = null;
        cercle = null;
        ligne = null;
    }

    @Test
    public void testConstructeur() {
        // Test du constructeur de la classe Groupe
        assertFalse(groupe.groupe.isEmpty());
    }

    @Test
    public void testConstructeurAvecFormes() {
        // Test du constructeur avec des formes
        assertEquals(3, groupe.groupe.size());
        assertTrue(groupe.groupe.contains(rectangle));
        assertTrue(groupe.groupe.contains(cercle));
        assertTrue(groupe.groupe.contains(ligne));
    }

    @Test
    public void testAjouter() {
        // Test de la méthode ajouter()
        groupe.ajouter(rectangle);
        assertEquals(4, groupe.groupe.size());
        assertTrue(groupe.groupe.contains(rectangle));
    }

    @Test
    public void testCentre() {
        // Test de la méthode centre()
        Point centre = groupe.centre();
        // Vérification du centre (à ajuster selon les formes)
        assertNotNull(centre);
    }

    @Test
    public void testCentreValeursNegatives() {
        // Cas avec des coordonnées négatives
        IForme formeNeg1 = new Rectangle(new Point(-4, -3), 2, 2);
        IForme formeNeg2 = new Rectangle(new Point(-6, -7), 4, 6);
        Groupe groupeNeg = new Groupe(formeNeg1, formeNeg2);

        Point centre = groupeNeg.centre();
        assertEquals(-4.0, centre.x(), 0.01);
        assertEquals(-5.0, centre.y(), 0.01);
    }

    @Test
    public void testCentreUnSeulElement() {
        // Cas avec un seul élément
        Groupe groupeUnique = new Groupe(rectangle);
        Point centre = groupeUnique.centre();
        assertEquals(rectangle.centre().x(), centre.x(), 0.01);
        assertEquals(rectangle.centre().y(), centre.y(), 0.01);
    }

    @Test
    public void testHauteur() {
        // Test de la méthode hauteur()
        double hauteur = groupe.hauteur();
        assertTrue(hauteur > 0);
    }

    @Test
    public void testHauteurValeursNegatives() {
        // Cas avec des coordonnées négatives
        IForme formeNeg1 = new Rectangle(new Point(-4, -3), 2, 2);
        IForme formeNeg2 = new Rectangle(new Point(-6, -7), 4, 6);
        Groupe groupeNeg = new Groupe(formeNeg1, formeNeg2);

        double hauteur = groupeNeg.hauteur();
        assertEquals(10.0, hauteur, 0.01);
    }

    @Test
    public void testHauteurUnSeulElement() {
        // Cas avec un seul élément
        Groupe groupeUnique = new Groupe(rectangle);
        double hauteur = groupeUnique.hauteur();
        assertEquals(rectangle.hauteur(), hauteur, 0.01);
    }

    @Test
    public void testLargeur() {
        // Test de la méthode largeur()
        double largeur = groupe.largeur();
        assertTrue(largeur > 0);
    }

    @Test
    public void testLargeurValeursNegatives() {
        // Cas avec des coordonnées négatives
        IForme formeNeg1 = new Rectangle(new Point(-4, -3), 2, 2);
        IForme formeNeg2 = new Rectangle(new Point(-8, -7), 4, 6);
        Groupe groupeNeg = new Groupe(formeNeg1, formeNeg2);

        double largeur = groupeNeg.largeur();
        assertEquals(10.0, largeur, 0.01);
    }

    @Test
    public void testLargeurUnSeulElement() {
        // Cas avec un seul élément
        Groupe groupeUnique = new Groupe(rectangle);
        double largeur = groupeUnique.largeur();
        assertEquals(rectangle.largeur(), largeur, 0.01);
    }

    @Test
    public void testDeplacer() {
        // Test de la méthode deplacer()
        Point centreRectangleAvant = rectangle.centre();

        groupe.deplacer(2, 3);

        Point centreRectangleApres = rectangle.centre();

        assertEquals(centreRectangleAvant.x() + 2, centreRectangleApres.x(), 0.01);
        assertEquals(centreRectangleAvant.y() + 3, centreRectangleApres.y(), 0.01);
    }

    @Test
    public void testDeplacerValeursNegatives() {
        // Test de la méthode deplacer() avec des valeurs négatives
        Point centreRectangleAvant = rectangle.centre();

        groupe.deplacer(-2, -3);

        Point centreRectangleApres = rectangle.centre();

        assertEquals(centreRectangleAvant.x() - 2, centreRectangleApres.x(), 0.01);
        assertEquals(centreRectangleAvant.y() - 3, centreRectangleApres.y(), 0.01);
    }

    @Test
    public void testDupliquer() {
        // Test de la méthode dupliquer()
        Groupe copie = (Groupe) groupe.dupliquer();

        assertNotSame(groupe, copie);
        assertEquals(groupe.groupe.size(), copie.groupe.size());

        for (int i = 0; i < groupe.groupe.size(); i++) {
            assertNotSame(groupe.groupe.get(i), copie.groupe.get(i));
            assertEquals(groupe.groupe.get(i).centre(), copie.groupe.get(i).centre());
        }
    }

    @Test
    public void testDupliquerGroupeVide() {
        Groupe groupeVide = new Groupe();
        Groupe copie = (Groupe) groupeVide.dupliquer();
        assertTrue(copie.groupe.isEmpty());
    }

    @Test
    public void testRedimensionner() {
        // Test de la méthode redimensionner()
        double largeurRectangleAvant = rectangle.largeur();
        double hauteurRectangleAvant = rectangle.hauteur();
        double rayonCercleAvant = ((Cercle) cercle).largeur();

        groupe.redimensionner(2, 2);

        assertEquals(largeurRectangleAvant * 2, rectangle.largeur(), 0.01);
        assertEquals(hauteurRectangleAvant * 2, rectangle.hauteur(), 0.01);
        assertEquals(rayonCercleAvant * 2, ((Cercle) cercle).largeur(), 0.01);
    }

    @Test
    public void testRedimensionnerValeurNegative() {
        groupe.redimensionner(-2, -2);
        assertTrue("Le redimensionnement ne doit pas rendre la largeur négative", groupe.largeur() > 0);
        assertTrue("Le redimensionnement ne doit pas rendre la hauteur négative", groupe.hauteur() > 0);
    }

    @Test
    public void testTourner() {
        // Test de la méthode tourner()
        groupe.tourner(90);

        // Vérification que toutes les formes ont tourné
        for (IForme forme : groupe.groupe) {
            if (forme instanceof Cercle) {
                assertEquals(90, ((Cercle) forme).angle, 0.01);
            } else if (forme instanceof Rectangle) {

            }
        }
    }

    @Test
    public void testVider() {
        // Test de la méthode vider()
        groupe.vider();
        assertTrue(groupe.groupe.isEmpty());
    }

    @Test
    public void testViderGroupeVide() {
        Groupe groupeVide = new Groupe();
        groupeVide.vider();
        assertTrue(groupeVide.groupe.isEmpty());
    }

    @Test
    public void testEnSVG() {
        // Test de la méthode EnSVG()
        String svg = groupe.enSVG();
        assertNotNull(svg);
        assertTrue(svg.contains("<g>"));
        assertTrue(svg.contains(rectangle.enSVG()));
        assertTrue(svg.contains(cercle.enSVG()));
        assertTrue(svg.contains(ligne.enSVG()));
    }
}