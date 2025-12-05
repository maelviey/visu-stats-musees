package fr.univrennes.istic.l2gen.visustats;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.After;
import fr.univrennes.istic.l2gen.geometrie.Point;
import org.junit.Test;

public class DiagColonnesTest extends TestCase {
    private DiagColonnes diag;

    @Before
    public void setUp() {
        diag = new DiagColonnes("Test Diagram", 800, 600);
    }

    @After
    public void tearDown() {
        diag = null;
    }

    @Test
    public void testCentre() {
        Point p = new Point(400, 300);
        assertEquals(p.x(), diag.centre().x());
        assertEquals(p.y(), diag.centre().y());
    }

    @Test
    public void testColorier() {
        // Ajouter d'abord les données puis colorer
        diag.ajouterDonnees("Test", 10.0, 20.0, 30.0);
        String[] colors = {"red", "blue", "green"};
        diag.colorier(colors);
        
        // Vérifier via la description
        String desc = diag.description(0);
        assertTrue(desc.contains("Couleurs:"));
        
        // Ou tester indirectement via le SVG généré
        String svg = diag.enSVG();
        assertTrue(svg.length() > 0);
    }

    @Test
    public void testHauteur() {
        double reference = 600;
        assertEquals(reference, diag.hauteur());
    }

    @Test
    public void testLargeur() {
        double reference = 800;
        assertEquals(reference, diag.largeur());
    }

    @Test
    public void testRedimensionner() {
        diag.redimensionner(1000, 800);
        assertEquals(1000.0, diag.largeur());
        assertEquals(800.0, diag.hauteur());
    }

    @Test
    public void testEnSVG() {
        // Ajouter des données pour que le SVG soit généré
        diag.ajouterDonnees("Série 1", 10.0, 20.0, 30.0);
        diag.colorier("red", "blue", "green"); // Définir les couleurs avant de générer le SVG
        
        String svg = diag.enSVG();
        assertEquals(svg, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + //
                        "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"800.0\" height=\"600.0\" viewBox=\"0 0 800.0 600.0\">\n" + //
                        "<text x=\"50%\" y=\"30\" font-size=\"20\" text-anchor=\"middle\">Test Diagram</text>\n" + //
                        "<g><g>\n" + //
                        "<text x = \"10.0\" y=\"20.0\" font-size=\"10.0\" text-anchor= \"middle\" fill=\"white\" stroke=\"black\">null</text>\n" + //
                        "</g></g>\n" + //
                        "<g>\n" + //
                        "</g>\n" + //
                        "</svg>");
    }

    @Test
    public void testAjouterDonnees() {
        diag.ajouterDonnees("Série 1", 10.0, 20.0, 30.0);
        assertEquals(1, diag.faisceau.size());
        
        // Vérifier indirectement via la description
        String desc = diag.description(0);
        assertTrue(desc.contains("Série 1"));
        
        // Vérifier que le SVG est généré correctement
        String svg = diag.enSVG();
        assertTrue(svg.contains("<svg"));
    }
    
    @Test
    public void testAgencer() {
        diag.ajouterDonnees("Série 1", 10.0, 20.0, 30.0);
        diag.ajouterDonnees("Série 2", 15.0, 25.0, 35.0);
        diag.colorier("red", "blue", "green");

        diag.agencer(); // Si une exception se produit ici, le test échouera automatiquement

        assertNotNull(diag.faisceau);
        assertEquals(2, diag.faisceau.size());
}

}