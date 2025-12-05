package fr.univrennes.istic.l2gen.geometrie;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

public class LigneTest {

    private Ligne ligne;

    // Initialisation avant chaque test
    @Before
    public void setUp() {
        ligne = new Ligne(128, 128, 128, 256, 256, 128, 256, 256);
    }

    // Nettoyage après chaque test
    @After
    public void tearDown() {
        ligne = null;
    }

    @Test
    public void testConstructeur() {
        assertNotNull(ligne);
        assertFalse(ligne.ligne.isEmpty());
    }

    @Test
    public void testConstructeurVide() {
        ligne = new Ligne();
        assertNotNull(ligne);
        assertTrue(ligne.ligne.isEmpty());
        assertEquals("black", ligne.color);
    }

    @Test
    public void testConstructeurAvecUnPoint() {
        ligne = new Ligne(128, 128);
        assertNotNull(ligne);
        assertEquals(1, ligne.ligne.size());
    }

    @Test
    public void testConstructeurAvecPointsNegatifs() {
        ligne = new Ligne(-10, -10, -5, -5);
        assertNotNull(ligne);
        assertEquals(2, ligne.ligne.size());
    }

    @Test
    public void testCoordonneesExtremes() {
        ligne = new Ligne(10000, 10000, 20000, 20000);
        assertEquals(10000, ligne.largeur(), 0.01);
        assertEquals(10000, ligne.hauteur(), 0.01);
    }

    @Test
    public void testCentre() {
        Point centre = ligne.centre();
        assertNotNull(centre);
    }

    @Test
    public void testCentreLigneVide() {
        ligne = new Ligne();
        Point centre = ligne.centre();
        assertEquals(new Point(0, 0), centre);
    }

    @Test
    public void testCentreSurUneLigneHorizontale() {
        ligne = new Ligne(0, 0, 10, 0);
        assertEquals(new Point(5, 0), ligne.centre());
    }

    @Test
    public void testCentreSurUneLigneVerticale() {
        ligne = new Ligne(0, 0, 0, 10);
        assertEquals(new Point(0, 5), ligne.centre());
    }

    @Test
    public void testHauteur() {
        assertTrue(ligne.hauteur() >= 0);
    }

    @Test
    public void testHauteurAvecValeursNegatives() {
        ligne = new Ligne(-10, -10, -5, 5);
        assertEquals(15, ligne.hauteur(), 0.01);
    }

    @Test
    public void testHauteurAvecUneSeuleCoordonnee() {
        ligne = new Ligne(5, 5);
        assertEquals(0, ligne.hauteur(), 0.01);
    }

    @Test
    public void testLargeur() {
        assertTrue(ligne.largeur() >= 0);
    }

    @Test
    public void testLargeurAvecValeursNegatives() {
        ligne = new Ligne(-10, -10, 5, -10);
        assertEquals(15, ligne.largeur(), 0.01);
    }

    @Test
    public void testLargeurAvecUneSeuleCoordonnee() {
        ligne = new Ligne(5, 5);
        assertEquals(0, ligne.largeur(), 0.01);
    }

    @Test
    public void testDeplacer() {
        ligne.deplacer(10, 10);
        assertNotNull(ligne.ligne.get(0));
    }

    @Test
    public void testDeplacerLigneVide() {
        ligne = new Ligne();
        ligne.deplacer(5, 5);
        assertTrue(ligne.ligne.isEmpty());
    }

    @Test
    public void testDeplacerVersOrigine() {
        ligne = new Ligne(5, 5, 10, 10);
        ligne.deplacer(-5, -5);
        assertEquals(new Point(0, 0), ligne.ligne.get(0));
    }

    @Test
    public void testDeplacerAvecValeursNegatives() {
        ligne = new Ligne(10, 10, 20, 20);
        ligne.deplacer(-5, -5);
        assertEquals(new Point(5, 5), ligne.ligne.get(0));
    }

    @Test
    public void testDupliquer() {
        // Création d'une ligne originale
        Ligne ligneOriginale = new Ligne(0, 0, 10, 10);
        ligneOriginale.ajouterSommet(20, 20);

        // Duplication de la ligne
        Ligne ligneDupliquee = (Ligne) ligneOriginale.dupliquer();

        // Vérification que les deux lignes ont la même description
        assertEquals(ligneOriginale.description(1), ligneDupliquee.description(1));

        // Modification de la ligne dupliquée
        ligneDupliquee.ajouterSommet(30, 30);

        // Vérification que la ligne originale reste inchangée
        assertNotEquals(ligneOriginale.description(1), ligneDupliquee.description(1));
    }

    @Test
    public void testDupliquerLigneVide() {
        ligne = new Ligne();
        Ligne copie = (Ligne) ligne.dupliquer();
        assertTrue("Une ligne vide dupliquée doit rester vide", copie.ligne.isEmpty());
    }

    @Test
    public void testAjouterSommet() {
        ligne.ajouterSommet(300, 300);
        assertEquals("La ligne devrait contenir un sommet supplémentaire", 5, ligne.ligne.size());
    }

    @Test
    public void testRedimensionner() {
        ligne.redimensionner(2, 2);
        assertNotNull(ligne.ligne.get(0));
    }

    @Test
    public void testRedimensionnerLigneVide() {
        ligne = new Ligne();
        ligne.redimensionner(2, 2);
        assertTrue(ligne.ligne.isEmpty());
    }

    @Test
    public void testRedimensionnerValeursNegatives() {
        ligne.redimensionner(-2, -2);
        assertTrue("La ligne ne doit pas disparaître avec un facteur négatif", ligne.largeur() > 0);
        assertTrue("La ligne ne doit pas disparaître avec un facteur négatif", ligne.hauteur() > 0);
    }

    @Test
    public void testEnSVG() {
        IForme f = new Ligne(128, 128, 128, 256, 256, 128, 256, 256);
        assertEquals(
                "<polyline points=\"128.0,128.0 128.0,256.0 256.0,128.0 256.0,256.0\" fill=\"black\" stroke=\"black\" transform=\"rotate(0)\" />",
                f.enSVG());
    }

    @Test
    public void testEnSVGListeVide() {
        IForme f = new Ligne();
        assertEquals("", f.enSVG());
    }

    @Test
    public void testEnSVGListeAvecUnPoint() {
        IForme f = new Ligne(128, 128);
        assertEquals("<polyline points=\"128.0,128.0\" fill=\"black\" stroke=\"black\" transform=\"rotate(0)\" />",
                f.enSVG());
    }

    @Test
    public void tourner() {
        ligne.tourner(90);
        assertEquals(
                "<polyline points=\"128.0,128.0 128.0,256.0 256.0,128.0 256.0,256.0\" fill=\"black\" stroke=\"black\" transform=\"rotate(90)\" />",
                ligne.enSVG());
    }

    @Test
    public void tournerZero() {
        ligne.tourner(0);
        assertEquals(
                "<polyline points=\"128.0,128.0 128.0,256.0 256.0,128.0 256.0,256.0\" fill=\"black\" stroke=\"black\" transform=\"rotate(0)\" />",
                ligne.enSVG());
    }

    @Test
    public void testAlignerHaut() {
        ligne = new Ligne(0, 0, 1, 1);
        ligne.aligner(Alignement.HAUT, 100);
        assertEquals(100, ligne.getSommets().get(0).y(), 0.01);
        assertEquals(101, ligne.getSommets().get(1).y(), 0.01);
    }

    @Test
    public void testAlignerBas() {
        ligne = new Ligne(0, 0, 1, 1);
        ligne.aligner(Alignement.BAS, 200);
        assertEquals(199, ligne.getSommets().get(0).y(), 0.01);
        assertEquals(200, ligne.getSommets().get(1).y(), 0.01);
    }

    @Test
    public void testAlignerGauche() {
        ligne = new Ligne(0, 0, 1, 1);
        ligne.aligner(Alignement.GAUCHE, 50);
        assertEquals(50, ligne.getSommets().get(0).x(), 0.01);
        assertEquals(51, ligne.getSommets().get(1).x(), 0.01);
    }

    @Test
    public void testAlignerDroite() {
        ligne = new Ligne(0, 0, 1, 1);
        ligne.aligner(Alignement.DROITE, 150);
        assertEquals(149, ligne.getSommets().get(0).x(), 0.01);
        assertEquals(150, ligne.getSommets().get(1).x(), 0.01);
    }
}
