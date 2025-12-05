package fr.univrennes.istic.l2gen.geometrie;

import static org.junit.Assert.*;
import org.junit.Test;

public class TriangleTest {

    Triangle triangle = new Triangle(2, 2, 4, 7, 3, 3);

    @Test
    public void testCentre() {
        double expectedX = 3; // (2+4+3)/3 = 3
        double expectedY = 4; // (2+7+3)/3 = 4

        Point expected = new Point(expectedX, expectedY);
        Point result = triangle.centre();

        // Vérification que la méthode retourne bien le bon point
        assertEquals(expected.x, result.x, 0.0001);
        assertEquals(expected.y, result.y, 0.0001);
    }

    @Test
    public void testColorier() {
        triangle.colorier("blue"); // Changement de couleur

        // Vérification que la méthode utilise bien la bonne couleur
        assertEquals("blue", triangle.color);
    }

    @Test
    public void testDeplacer() {
        Triangle triangle = new Triangle(3, 2, 4, 7, 3, 2);

        // Déplacement du triangle de (2,3)
        triangle.deplacer(2, 3);

        // Vérification des nouvelles coordonnées
        assertEquals(5, triangle.ax, 0.0001);
        assertEquals(5, triangle.ay, 0.0001);
        assertEquals(6, triangle.bx, 0.0001);
        assertEquals(10, triangle.by, 0.0001);
        assertEquals(5, triangle.cx, 0.0001);
        assertEquals(5, triangle.cy, 0.0001);
    }

    @Test
    public void testDescription() {
        Triangle triangle = new Triangle(3, 2, 4, 7, 3, 2);

        // Test avec une indentation de 2 (4 espaces)
        int indentation = 2;
        String expected = "      Triangle 3.0, 2.0      4.0, 7.0      3.0, 2.0"; // 4 espaces avant chaque bloc
        assertEquals(expected, triangle.description(indentation));

        // Test avec une indentation de 1 (2 espaces)
        expected = "  Triangle 3.0, 2.0  4.0, 7.0  3.0, 2.0";
        assertEquals(expected, triangle.description(0));
    }

    @Test
    public void testDupliquer() {
        // Création du triangle original avec des points et une couleur
        Triangle original = new Triangle(new Point(3, 2), new Point(4, 7), new Point(5, 6));

        // Duplication du triangle
        Triangle copie = (Triangle) original.dupliquer();

        // Vérification que les coordonnées sont bien copiées
        assertEquals(original.ax, copie.ax, 0.0001);
        assertEquals(original.ay, copie.ay, 0.0001);
        assertEquals(original.bx, copie.bx, 0.0001);
        assertEquals(original.by, copie.by, 0.0001);
        assertEquals(original.cx, copie.cx, 0.0001);
        assertEquals(original.cy, copie.cy, 0.0001);

        // Vérification que la couleur est copiée
        assertEquals(original.color, copie.color);

        // Vérification que ce sont bien des objets distincts (copie profonde)
        assertNotSame(original, copie);

        // Vérification que les points de la copie ne sont pas les mêmes objets que ceux
        // de l'original
        assertNotSame(original.ax, copie.ax);
        assertNotSame(original.ay, copie.ay);
        assertNotSame(original.bx, copie.bx);
        assertNotSame(original.by, copie.by);
        assertNotSame(original.cx, copie.cx);
        assertNotSame(original.cy, copie.cy);
    }

    @Test
    public void testHauteur() {
        Triangle triangle = new Triangle(new Point(3, 2), new Point(4, 7), new Point(5, 6));
        System.out.println(triangle.enSVG());
        // Calcul de la hauteur attendue (différence entre Y max et Y min)
        double hauteurAttendue = 7 - 2; // maxY (7) - minY (2)

        // Appel de la méthode hauteur() pour obtenir la hauteur calculée
        double hauteurGénérée = triangle.hauteur();

        // Vérification que la hauteur calculée est correcte
        assertEquals(hauteurAttendue, hauteurGénérée, 0.01);
    }

    @Test
    public void testLargeur() {
        Triangle triangle = new Triangle(new Point(3, 2), new Point(4, 7), new Point(5, 6));

        // Calcul de la hauteur attendue (différence entre Y max et Y min)
        double largeurAttendue = 5-3; // maxY (7) - minY (2)

        // Appel de la méthode hauteur() pour obtenir la hauteur calculée
        double largeurGénérée = triangle.largeur();

        // Vérification que la hauteur calculée est correcte
        assertEquals(largeurAttendue, largeurGénérée, 0.0001);
    }

    @Test
    public void testRedimensionner() {
        Triangle triangle = new Triangle(0, 0, 2, 2, 4, 1);

        // Redimensionner avec facteur 2 pour x et 3 pour y
        triangle.redimensionner(2, 3);

        // Vérification des nouvelles coordonnées
        assertEquals(0, triangle.ax, 0.001);
        assertEquals(0, triangle.ay, 0.001);
        assertEquals(4, triangle.bx, 0.001);
        assertEquals(6, triangle.by, 0.001);
        assertEquals(8, triangle.cx, 0.001);
        assertEquals(3, triangle.cy, 0.001);
    }

    

    @Test
    public void testAligner() {
        Triangle triangle = new Triangle(0, 0, 2, 2, 4, 1);

        // Test alignement en haut (y = 5)
        triangle.aligner(Alignement.HAUT, 5);
        assertEquals(5, Math.max(triangle.ay, Math.max(triangle.by, triangle.cy)), 0.001);

        // Test alignement en bas (y = 0)
        triangle.aligner(Alignement.BAS, 0);
        assertEquals(0, Math.min(triangle.ay, Math.min(triangle.by, triangle.cy)), 0.001);

        // Test alignement à droite (x = 10)
        triangle.aligner(Alignement.DROITE, 10);
        assertEquals(10, Math.max(triangle.ax, Math.max(triangle.bx, triangle.cx)), 0.001);

        // Test alignement à gauche (x = -3)
        triangle.aligner(Alignement.GAUCHE, -3);
        assertEquals(-3, Math.min(triangle.ax, Math.min(triangle.bx, triangle.cx)), 0.001);
    }

    @Test
    public void testEnSVG(){
        String svg = triangle.enSVG();
        System.out.println(svg);
        assertEquals(svg,"<polygon points=\"2.0 2.0 4.0 7.0 3.0 3.0\"\n" + "fill=\"black\" stroke=\"black\"/>");
    }

}
