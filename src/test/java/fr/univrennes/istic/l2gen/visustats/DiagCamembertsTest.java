package fr.univrennes.istic.l2gen.visustats;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.lang.reflect.Field;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univrennes.istic.l2gen.geometrie.Groupe;
import fr.univrennes.istic.l2gen.geometrie.IForme;
import fr.univrennes.istic.l2gen.geometrie.Rectangle;
import fr.univrennes.istic.l2gen.geometrie.Texte;
import fr.univrennes.istic.l2gen.geometrie.Point;

public class DiagCamembertsTest {
    private DiagCamemberts diagCamemberts;

    @Before
    public void setUp() {
        diagCamemberts = new DiagCamemberts("Test Diagram", 800, 600);
    }

    @After
    public void tearDown() {
        diagCamemberts = null;
    }

    @Test
    public void testAgencer() {
        // Ajout des données pour le diagramme : deux catégories avec leurs valeurs
        diagCamemberts.ajouterDonnees("Catégorie A", 40);
        diagCamemberts.ajouterDonnees("Catégorie B", 60);

        // Attribution des couleurs aux différentes parties du camembert
        diagCamemberts.colorier("red", "blue");

        // Appel de la méthode agencer() qui génère l'agencement du diagramme en
        // camembert
        diagCamemberts.agencer();

        // Vérification que le nombre de camemberts créés est bien 1
        assertEquals("Le nombre de camemberts générés doit être 1",
                1, diagCamemberts.camemberts.size());
    }

    @Test
    public void testAjouterLegendes() {
        // Ajoute des données représentant différentes catégories avec leurs valeurs
        // respectives
        diagCamemberts.ajouterDonnees("Catégorie A", 40);
        diagCamemberts.ajouterDonnees("Catégorie B", 60);

        // Applique des couleurs aux différentes catégories du camembert
        diagCamemberts.colorier("red", "blue");

        // Organise les données et génère le diagramme en camembert
        diagCamemberts.agencer();

        // Récupère la description du premier groupe de formes du diagramme
        String description = diagCamemberts.getGroupe().description(0);
        System.out.println("Description du groupe : " + description);

        // Vérifie que la description contient bien les informations ajoutées
        assertTrue(description.contains("Catégorie A")); // Confirme que la catégorie A est bien présente
        assertTrue(description.contains("40%")); // Vérifie que la valeur associée est correcte
    }

    @Test
    public void testAjouterDonnees() {
        // Ajout des données représentant différentes catégories avec leurs valeurs
        // respectives
        diagCamemberts.ajouterDonnees("Catégorie A", 40);
        diagCamemberts.ajouterDonnees("Catégorie B", 60);

        // Attribution des couleurs aux différentes catégories du camembert
        diagCamemberts.colorier("red", "blue");

        // Génération et organisation du diagramme en camembert
        diagCamemberts.agencer();

        // Conversion du diagramme en format SVG pour affichage ou exportation
        String svg = diagCamemberts.enSVG();
        System.out.println("SVG généré :\n" + svg);

        // Vérification que le fichier SVG contient bien les informations attendues
        assertTrue(svg.contains("Catégorie A")); // Vérifie que "Catégorie A" est bien présente
        assertTrue(svg.contains("40%")); // Vérifie que la valeur de 40% est bien intégrée

        assertTrue(svg.contains("Catégorie B")); // Vérifie que "Catégorie B" est bien présente
        assertTrue(svg.contains("60%")); // Vérifie que la valeur de 60% est bien intégrée
    }

    @Test
    public void testLegender() throws NoSuchFieldException, IllegalAccessException {
        // Ajoute des légendes à l'objet diagCamemberts
        diagCamemberts.legender("Légende 1", "Légende 2");

        // Accède au champ privé "legende" de la classe DiagCamemberts pour vérifier son
        // contenu
        Field legendeField = DiagCamemberts.class.getDeclaredField("legende");
        legendeField.setAccessible(true); // Permet d'accéder à un champ privé

        // Récupère la valeur du champ "legende" et la stocke sous forme de liste
        @SuppressWarnings("unchecked")
        java.util.ArrayList<String> legende = (java.util.ArrayList<String>) legendeField.get(diagCamemberts);

        // Vérifie que la liste des légendes correspond bien aux valeurs initiales
        assertEquals((List<String>) Arrays.asList("Légende 1", "Légende 2"), legende);

        // Ajoute une nouvelle légende pour remplacer les précédentes
        diagCamemberts.legender("Nouvelle Légende");

        // Vérifie que la liste des légendes contient uniquement la nouvelle légende
        // ajoutée
        assertEquals(Arrays.asList("Nouvelle Légende"), legende);
    }

    @Test
    public void testCentre() {
        // Récupère le point central du diagramme en camembert
        Point centre = diagCamemberts.centre();

        // Vérifie que la coordonnée X du centre correspond bien à 400.0 avec une
        // tolérance de 0.0001
        assertEquals(400.0, centre.x(), 0.0001);

        // Vérifie que la coordonnée Y du centre correspond bien à 300.0 avec une
        // tolérance de 0.0001
        assertEquals(300.0, centre.y(), 0.0001);
    }

    @Test
    public void testColorier() throws NoSuchFieldException, IllegalAccessException {
        // Applique une palette de couleurs au diagramme en camembert
        diagCamemberts.colorier("red", "blue", "green");

        // Accède au champ privé "couleurs" de la classe DiagCamemberts pour vérifier
        // son contenu
        Field couleursField = DiagCamemberts.class.getDeclaredField("couleurs");
        couleursField.setAccessible(true); // Permet d'accéder à un champ privé

        // Récupère les couleurs enregistrées dans le diagramme
        String[] couleurs = (String[]) couleursField.get(diagCamemberts);

        // Vérifie que la liste des couleurs attribuées correspond bien aux valeurs
        // spécifiées
        assertArrayEquals(new String[] { "red", "blue", "green" }, couleurs);
    }

    @Test
    public void testDeplacer() throws NoSuchFieldException, IllegalAccessException {
        // Ajoute des données représentant différentes catégories avec leurs valeurs
        // respectives
        diagCamemberts.ajouterDonnees("Catégorie A", 40);
        diagCamemberts.ajouterDonnees("Catégorie B", 60);

        // Applique des couleurs aux différentes catégories du camembert
        diagCamemberts.colorier("red", "blue");

        // Génère et organise le diagramme en camembert
        diagCamemberts.agencer();

        // Déplace le diagramme en camembert aux coordonnées (50, 100)
        diagCamemberts.deplacer(50, 100);

        // Récupère la description du diagramme après déplacement
        String descriptionApresDeplacement = diagCamemberts.description(0);
        System.out.println("Position après déplacement : " + descriptionApresDeplacement);

        // Vérifie que la description contient bien les nouvelles coordonnées du centre
        // après déplacement
        assertTrue(descriptionApresDeplacement.contains("Centre : 50.0,100.0"));
    }

    @Test
    public void testDescriptionTexte() {
        // Création d'un objet Texte avec un contenu, une position (x, y) et une taille
        // de police
        Texte txt = new Texte("Hello", 10, 10, 12);

        // Récupération de la description du texte avec un niveau d'indentation donné
        // (2)
        String description = txt.description(2);

        // Affichage de la description obtenue pour vérification visuelle
        System.out.println("Description obtenue : " + description);

        // Vérifie que la description commence bien avec une indentation (espaces)
        assertTrue(description.startsWith("    "));

        // Vérifie que la description contient bien le texte "Hello"
        assertTrue(description.contains("Hello"));

        // Vérifie que la description ne contient pas de tabulation ("\t")
        assertFalse(description.contains("\t"));
    }

    @Test
    public void testDuplicationTexte() {
        // Création d'un objet Texte avec du contenu, une position (x, y) et une taille
        // de police
        Texte txt = new Texte("Test", 5, 5, 14);

        // Duplication de l'objet Texte pour obtenir une copie indépendante
        IForme copie = txt.dupliquer();

        // Vérifie que la description de l'original et de la copie sont identiques
        assertEquals(txt.description(0), copie.description(0));
    }

    @Test
    public void testDupliquer() {
        // Création d'un objet DiagCamemberts avec un titre, une taille et des données
        DiagCamemberts original = new DiagCamemberts("Test Camembert", 800, 600);

        // Ajout de données représentant différentes catégories avec leurs valeurs
        original.ajouterDonnees("Catégorie A", 40);
        original.ajouterDonnees("Catégorie B", 60);

        // Attribution de couleurs aux différentes catégories du camembert
        original.colorier("red", "blue");

        // Duplication de l'objet original pour créer une copie indépendante
        DiagCamemberts copie = (DiagCamemberts) original.dupliquer();

        // Vérifie que l'objet copié est bien distinct de l'original
        assertNotSame(original, copie);

        // Vérifie que la description du diagramme original et de la copie sont
        // identiques après duplication
        assertEquals(original.description(0), copie.description(0));

        // Déplace la copie vers une nouvelle position
        copie.deplacer(100, 200);

        // Vérifie que la modification de la copie n'affecte pas la description de
        // l'original
        assertEquals(original.description(0), copie.description(0));
    }

    @Test
    public void testEnSVG() {
        // Ajoute des données représentant différentes catégories avec leurs valeurs
        // respectives
        diagCamemberts.ajouterDonnees("Catégorie A", 40);
        diagCamemberts.ajouterDonnees("Catégorie B", 60);

        // Applique des couleurs aux différentes catégories du camembert
        diagCamemberts.colorier("red", "blue");

        // Convertit le diagramme en camembert en format SVG
        String svg = diagCamemberts.enSVG();

        // Affiche le SVG généré pour vérification
        System.out.println("SVG généré :\n" + svg);

        // Vérifie que le code SVG généré contient bien la balise principale <svg>
        assertTrue(svg.contains("<svg"));

        // Vérifie que le SVG inclut du texte, ce qui est essentiel pour l'affichage des
        // données
        assertTrue(svg.contains("<text"));
    }

    @Test
    public void testHauteur() {
        // Création de deux objets DiagCamemberts avec des tailles différentes
        DiagCamemberts diag1 = new DiagCamemberts("Camembert 1", 800, 600);
        DiagCamemberts diag2 = new DiagCamemberts("Camembert 2", 1024, 768);

        // Vérifie que la hauteur de diag1 correspond bien à 600 avec une tolérance de
        // 0.0001
        assertEquals(600.0, diag1.hauteur(), 0.0001);

        // Vérifie que la hauteur de diag2 correspond bien à 768 avec une tolérance de
        // 0.0001
        assertEquals(768.0, diag2.hauteur(), 0.0001);
    }

    @Test
    public void testLargeur() {
        // Création de deux objets DiagCamemberts avec des dimensions différentes
        DiagCamemberts diag1 = new DiagCamemberts("Camembert 1", 800, 600);
        DiagCamemberts diag2 = new DiagCamemberts("Camembert 2", 1024, 768);

        // Vérifie que la largeur de diag1 correspond bien à 800 avec une tolérance de
        // 0.0001
        assertEquals(800.0, diag1.largeur(), 0.0001);

        // Vérifie que la largeur de diag2 correspond bien à 1024 avec une tolérance de
        // 0.0001
        assertEquals(1024.0, diag2.largeur(), 0.0001);
    }

    @Test
    public void testRedimensionner() {
        // Redimensionne le diagramme avec de nouvelles dimensions (largeur: 1200,
        // hauteur: 900)
        diagCamemberts.redimensionner(1200, 900);

        // Vérifie que la largeur du diagramme a bien été mise à jour
        assertEquals(1200.0, diagCamemberts.largeur(), 0.0001);

        // Vérifie que la hauteur du diagramme a bien été mise à jour
        assertEquals(900.0, diagCamemberts.hauteur(), 0.0001);

        // Redimensionne à nouveau le diagramme avec des dimensions plus petites
        // (largeur: 500, hauteur: 300)
        diagCamemberts.redimensionner(500, 300);

        // Vérifie que la largeur a bien été mise à jour après le second
        // redimensionnement
        assertEquals(500.0, diagCamemberts.largeur(), 0.0001);

        // Vérifie que la hauteur a bien été mise à jour après le second
        // redimensionnement
        assertEquals(300.0, diagCamemberts.hauteur(), 0.0001);
    }

}
