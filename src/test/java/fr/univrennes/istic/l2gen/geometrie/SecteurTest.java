package fr.univrennes.istic.l2gen.geometrie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import org.junit.Test;

public class SecteurTest {
    @Test
    public void testCentre() {
        // Création d'un point pour le centre
        Point expectedCentre = new Point(5, 5);

        // Création d'un secteur avec ce centre
        Secteur secteur = new Secteur(expectedCentre, 10, 45, 90);

        // Vérification que la méthode centre() retourne bien le bon point
        assertEquals(expectedCentre, secteur.centre());
    }

    @Test
    public void testColorier() {
        // Création d'un secteur avec une couleur par défaut
        Secteur secteur = new Secteur(5, 5, 10, 45, 90);
        assertEquals("white", secteur.color);

        // Application d'une nouvelle couleur
        secteur.colorier("red");
        assertEquals("red", secteur.color);

        // Test avec plusieurs couleurs (seule la première doit être prise en compte)
        secteur.colorier("blue", "green", "yellow");
        assertEquals("blue", secteur.color);
    }

    @Test
    public void testDeplacer() {
        // Création d'un secteur avec un centre initial (5,5)
        Secteur secteur = new Secteur(5, 5, 10, 45, 90);

        // Vérification de la position initiale
        assertEquals(5, secteur.centre().x(), 0.0001);
        assertEquals(5, secteur.centre().y(), 0.0001);

        // Déplacement du secteur vers (10,15)
        secteur.deplacer(10, 15);

        // Vérification de la nouvelle position
        assertEquals(10, secteur.centre().x(), 0.0001);
        assertEquals(15, secteur.centre().y(), 0.0001);
    }

    @Test
    public void testDescription() {
        // Création d'un secteur avec des valeurs spécifiques
        Secteur secteur = new Secteur(5, 5, 10, 45, 90);

        // Génération de la description avec une indentation de 2
        String expectedDescription = "      secteur centre=5.0,5.0 r=10.0 Angle=45.0 Arc=90.0";
        String actualDescription = secteur.description(2);

        // Vérification que la description correspond à l'attendu
        assertEquals(expectedDescription, actualDescription);
    }

    @Test
    public void testDupliquer() {
        // Création d'un secteur original
        Secteur secteurOriginal = new Secteur(5, 5, 10, 45, 90);

        // Duplication du secteur
        Secteur secteurDuplique = (Secteur) secteurOriginal.dupliquer();

        // Vérification que l'objet dupliqué n'est pas le même mais a les mêmes valeurs
        assertNotSame(secteurOriginal, secteurDuplique);
        assertEquals(secteurOriginal.centre().x(), secteurDuplique.centre().x(), 0.0001);
        assertEquals(secteurOriginal.centre().y(), secteurDuplique.centre().y(), 0.0001);
        assertEquals(secteurOriginal.rayon, secteurDuplique.rayon, 0.0001);
        assertEquals(secteurOriginal.angle, secteurDuplique.angle, 0.0001);
        assertEquals(secteurOriginal.arc, secteurDuplique.arc, 0.0001);
        assertEquals(secteurOriginal.color, secteurDuplique.color);
    }

    @Test
    public void testEnSVG() {
        // Création d'un secteur avec des valeurs spécifiques
        Secteur secteur = new Secteur(5, 5, 10, 45, 90);
        secteur.colorier("red"); // On change la couleur pour vérifier qu'elle est bien prise en compte

        // Génération du code SVG
        String svg = secteur.enSVG();

        // Vérification que le SVG contient les bonnes valeurs
        assertNotNull(svg, "Le code SVG ne doit pas être null.");
        assertEquals(svg,"<path d=\"M 5.0 5.0 L 12.071067811865476 12.071067811865476 A 10.0 10.0 0 0 1 -2.0710678118654746 12.071067811865476 Z\" fill=\"red\" stroke=\"black\"/>");
    }

    @Test
    public void testGetAngle() {
        // Création d'un secteur avec un angle spécifique
        Secteur secteur = new Secteur(5, 5, 10, 45, 90);

        // Vérification que getAngle() retourne bien la bonne valeur
        assertEquals(45, secteur.getAngle(), 0.0001);
    }

    @Test
    public void testGetArc() {
        // Création d'un secteur avec un angle spécifique
        Secteur secteur = new Secteur(5, 5, 10, 45, 90);

        // Vérification que getAngle() retourne bien la bonne valeur
        assertEquals(45, secteur.getAngle(), 0.0001);
    }

    @Test
    public void testGetPointAtAngle() {
        // Création d'un secteur avec un centre (0,0) et un rayon de 10
        Secteur secteur = new Secteur(0, 0, 10, 0, 90);

        // Vérification pour un angle de 0° (doit être à (10,0))
        Point point0 = secteur.getPointAtAngle(0);
        assertEquals(10, point0.x(), 0.0001);
        assertEquals(0, point0.y(), 0.0001);

        // Vérification pour un angle de 90° (doit être à (0,10))
        Point point90 = secteur.getPointAtAngle(90);
        assertEquals(0, point90.x(), 0.0001);
        assertEquals(10, point90.y(), 0.0001);

        // Vérification pour un angle de 180° (doit être à (-10,0))
        Point point180 = secteur.getPointAtAngle(180);
        assertEquals(-10, point180.x(), 0.0001);
        assertEquals(0, point180.y(), 0.0001);

        // Vérification pour un angle de 270° (doit être à (0,-10))
        Point point270 = secteur.getPointAtAngle(270);
        assertEquals(0, point270.x(), 0.0001);
        assertEquals(-10, point270.y(), 0.0001);
    }

    @Test
    public void testHauteur() {
        // Création d'un secteur avec un centre (5,5) et un rayon de 10
        Secteur secteur = new Secteur(5, 5, 10, 0, 180);

        // Calcul attendu : hauteur = distance entre le point le plus bas et le point le
        // plus haut
        double hauteurAttendue = secteur.yLePlusHaut() - secteur.yLePlusBas();

        // Vérification de la hauteur
        assertEquals(hauteurAttendue, secteur.hauteur(), 0.0001);
    }

    @Test
    public void testLargeur() {
        // Création d'un secteur avec un centre (5,5) et un rayon de 10
        Secteur secteur = new Secteur(5, 5, 10, 0, 180);

        // Calcul attendu : largeur = distance entre le point le plus à gauche et le
        // point le plus à droite
        double largeurAttendue = secteur.xLePlusADroite() - secteur.xLePlusAGauche();

        // Vérification de la largeur
        assertEquals(largeurAttendue, secteur.largeur(), 0.0001);
    }

    @Test
    public void testRedimensionner() {
        // Création d'un secteur avec un centre (5,5) et un rayon de 10
        Secteur secteur = new Secteur(5, 5, 10, 45, 90);

        // Redimensionnement avec un facteur de 2 sur l'axe x (b n'est pas utilisé)
        secteur.redimensionner(2, 0);

        // Vérification que le rayon a bien été multiplié par 2
        assertEquals(20, secteur.rayon, 0.0001);

        // Test avec un facteur de 0.5 (réduction de moitié)
        secteur.redimensionner(0.5, 0);
        assertEquals(10, secteur.rayon, 0.0001);
    }

    @Test
    public void testSetColor() {
        // Création d'un secteur avec des valeurs initiales
        Secteur secteur = new Secteur(5, 5, 10, 45, 90);

        // Vérification de la couleur initiale (doit être "white" par défaut)
        assertEquals("white", secteur.color);

        // Modification de la couleur en "blue"
        secteur.colorier("blue");
        assertEquals("blue", secteur.color);

        // Modification de la couleur en "green"
        secteur.colorier("green");
        assertEquals("green", secteur.color);
    }

    @Test
    public void testTourner() {
        // Création d'un secteur avec des valeurs initiales
        Secteur secteur = new Secteur(5, 5, 10, 45, 90);

        // Appel de la méthode tourner
        secteur.tourner(90);
        // Vérification de la méthode tourner
        assertEquals(90, secteur.angle, 0.0001);
    }

    @Test
    public void testXLePlusADroite() {
        Secteur secteur = new Secteur(new Point(0, 0), 5, 0, 90);
        assertEquals(5, secteur.xLePlusADroite(), 0.01);
    }

    @Test
    public void testXLePlusAGauche() {
        Secteur secteur = new Secteur(new Point(0, 0), 5, 180, 90);
        assertEquals(-5, secteur.xLePlusAGauche(), 0.01);
    }

    @Test
    public void testYLePlusHaut() {
        Secteur secteur = new Secteur(new Point(0, 0), 5, 90, 90);
        assertEquals(5, secteur.yLePlusHaut(), 0.01);
    }

    @Test
    public void testYLePlusBas() {
        Secteur secteur = new Secteur(new Point(0, 0), 5, 270, 90);
        assertEquals(-5, secteur.yLePlusBas(), 0.01);
    }
}
