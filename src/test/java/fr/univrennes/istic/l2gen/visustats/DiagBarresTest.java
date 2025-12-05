package fr.univrennes.istic.l2gen.visustats;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

public class DiagBarresTest extends TestCase {
    private DiagBarres diagBarres;

    @Before
    public void setUp() {
        diagBarres = new DiagBarres("Test Diagram", 800, 600);
    }

    @After
    public void tearDown() {
        diagBarres = null;
    }

    @Test
    public void testConstructeur() {
        assertNotNull(diagBarres);
        assertEquals("Test Diagram", diagBarres.getTitre());
        assertEquals(800.0, diagBarres.getLargeur());
        assertEquals(600.0, diagBarres.getHauteur());
    }

    @Test
    public void testAjouterDonnees() {
        diagBarres.ajouterDonnees("Série 1", 100, 200, 300);
        
        assertEquals(1, diagBarres.faisceau.size());
        assertEquals(1, diagBarres.getDonnees().size());
        assertEquals("Série 1", diagBarres.getDonnees().get(0));
        assertEquals(300.0, diagBarres.getMaxval());
    }

    @Test
    public void testAjouterPlusieursDatasets() {
        diagBarres.ajouterDonnees("Série 1", 100, 200, 300);
        diagBarres.ajouterDonnees("Série 2", 150, 250, 350);
        
        assertEquals(2, diagBarres.faisceau.size());
        assertEquals(2, diagBarres.getDonnees().size());
        assertEquals(350.0, diagBarres.getMaxval());
    }

    @Test
    public void testAgencer() {
        diagBarres.ajouterDonnees("Série 1", 100, 200, 300);
        
        diagBarres.agencer();
        
        assertNotNull(diagBarres.faisceau.get(0).coordx);
        assertNotNull(diagBarres.faisceau.get(0).coordy);
        assertEquals("Série 1", diagBarres.faisceau.get(0).titre);
    }

    @Test
    public void testCentre() {
        assertEquals(400.0, diagBarres.centre().x());
        assertEquals(300.0, diagBarres.centre().y());
    }

    @Test
    public void testDescription() {
        diagBarres.ajouterDonnees("Série 1", 100, 200, 300);
        
        String description = diagBarres.description(0);
        
        assertTrue(description.contains("Test Diagram"));
        assertTrue(description.contains("Série 1"));
    }

    @Test
    public void testEnSVG() {
        diagBarres.ajouterDonnees("Série 1", 100, 200, 300);
        diagBarres.colorier("red", "green", "blue");
        diagBarres.agencer();
        
        String svg = diagBarres.enSVG();
        
        assertEquals(svg,"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + //
                        "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"800.0\" height=\"600.0\" viewBox=\"0 0 800.0 600.0\">\n" + //
                        "<text x=\"50%\" y=\"30\" font-size=\"20\" text-anchor=\"middle\">Test Diagram</text>\n" + //
                        "<g><g>\n" + //
                        "<rect x=\"-986.7266666666667\" y=\"360.0\" width=\"426.6666666666667\" height=\"360.0\" fill=\"red\" stroke=\"black\"/>\n" + //
                        "<rect x=\"-557.9399999999999\" y=\"420.0\" width=\"426.6666666666667\" height=\"240.0\" fill=\"red\" stroke=\"black\"/>\n" + //
                        "<rect x=\"-129.15333333333328\" y=\"480.0\" width=\"426.6666666666667\" height=\"120.0\" fill=\"red\" stroke=\"black\"/>\n" + //
                        "<text x = \"524.1666666666667\" y=\"560.0\" font-size=\"10.0\" text-anchor= \"middle\" fill=\"white\" stroke=\"black\">Série 1</text>\n" + //
                        "</g></g>\n" + //
                        "<g>\n" + //
                        "</g>\n" + //
                        "</svg>");
        assertTrue(svg.contains("Test Diagram"));
    }
}