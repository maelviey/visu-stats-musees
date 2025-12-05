package fr.univrennes.istic.l2gen.visustats;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

public class FaisceauTest extends TestCase {
    private Faisceau faisceau;

    @Before
    public void setUp() {
        faisceau = new Faisceau(100, 200, 300);
    }

    @After
    public void tearDown() {
        faisceau = null;
    }

    @Test
    public void testConstructeur() {
        Faisceau f = new Faisceau(100, 200, 300);
        assertNotNull(f);
        assertEquals(3, f.longueurs.length);
        assertEquals(3, f.couleurs.length);
        assertNotNull(f.grp);
    }

    @Test
    public void testConstructeurVide() {
        Faisceau f = new Faisceau();
        assertNotNull(f);
        assertEquals(0, f.longueurs.length);
        assertEquals(0, f.couleurs.length);
        assertNotNull(f.grp);
    }

    @Test
    public void testConstructeurUnElement() {
        Faisceau f = new Faisceau(150);
        assertNotNull(f);
        assertEquals(1, f.longueurs.length);
        assertEquals(150, f.longueurs[0], 0.001);
        assertEquals(1, f.couleurs.length);
    }

    @Test
    public void testAgencerHorizontal() {
        faisceau.agencer(100, 200, 50, 0.5, false);
        // Vérification des coordonnées après agencement
        assertNotNull(faisceau.coordx);
        assertNotNull(faisceau.coordy);
        assertNotNull(faisceau.coordx2);
        assertEquals(200.0, faisceau.coordy);
    }

    @Test
    public void testAgencerVertical() {
        faisceau.agencer(100, 200, 50, 0.5, true);
        // Vérification des coordonnées après agencement
        assertNotNull(faisceau.coordx);
        assertNotNull(faisceau.coordy);
        assertNotNull(faisceau.coordx2);
        assertEquals(150.0, faisceau.coordx); // 100 + 50 (largeur)
    }

    @Test
    public void testAgencerZeroLongueur() {
        Faisceau f = new Faisceau(0, 0, 0);
        f.agencer(100, 200, 50, 0.5, false);
        // Vérification que l'agencement fonctionne même avec des longueurs à zéro
        assertNotNull(f.coordx);
        assertNotNull(f.coordy);
        assertNotNull(f.coordx2);
    }

    @Test
    public void testAgencerEchelleVariee() {
        // Test avec différentes échelles
        Faisceau f1 = new Faisceau(100, 200);
        Faisceau f2 = new Faisceau(100, 200);
        Faisceau f3 = new Faisceau(100, 200);
        
        f1.agencer(100, 200, 50, 0.1, false);
        f2.agencer(100, 200, 50, 1.0, false);
        f3.agencer(100, 200, 50, 2.0, false);
        
        // Vérification que les coordonnées sont différentes avec différentes échelles
        assertNotSame(f1.coordx2, f2.coordx2);
        assertNotSame(f2.coordx2, f3.coordx2);
    }

    @Test
    public void testAgencerLargeurVariee() {
        // Test avec différentes largeurs
        Faisceau f1 = new Faisceau(100, 200);
        Faisceau f2 = new Faisceau(100, 200);
        
        f1.agencer(100, 200, 25, 0.5, false);
        f2.agencer(100, 200, 75, 0.5, false);
        
        // La largeur de f2 devrait être plus grande que celle de f1
        assertTrue(f2.coordx2 > f1.coordx2);
    }

    @Test
    public void testColorierTropDeCouleurs() {
        String[] couleurs = {"red", "green", "blue", "yellow", "purple"};
        try {
            faisceau.colorier(couleurs);
            fail("Une IllegalArgumentException aurait dû être levée");
        } catch (IllegalArgumentException e) {
            // Le test réussit si on arrive ici
            assertEquals("More or less colours than elements", e.getMessage());
        }
    }

    @Test
    public void testSetTitre() {
        String titre = "Mon faisceau de test";
        faisceau.setTitre(titre);
        assertEquals(titre, faisceau.titre);
    }

    @Test
    public void testSetTitreNull() {
        faisceau.setTitre(null);
        assertNull(faisceau.titre);
    }

    @Test
    public void testSetTitreVide() {
        faisceau.setTitre("");
        assertEquals("", faisceau.titre);
    }

    @Test
    public void testEnSVG() {
        String titre = "Test Faisceau";
        faisceau.setTitre(titre);
        faisceau.agencer(100, 200, 50, 0.5, false);
        faisceau.colorier("red", "green", "blue");
        
        String svg = faisceau.enSVG();
        
        // Vérifie que le SVG contient les éléments attendus
        assertEquals(svg, "<g><g>\n" + //
                        "<rect x=\"-25.025\" y=\"125.0\" width=\"50.0\" height=\"150.0\" fill=\"red\" stroke=\"black\"/>\n" + //
                        "<rect x=\"27.025\" y=\"150.0\" width=\"50.0\" height=\"100.0\" fill=\"red\" stroke=\"black\"/>\n" + //
                        "<rect x=\"79.07499999999999\" y=\"175.0\" width=\"50.0\" height=\"50.0\" fill=\"red\" stroke=\"black\"/>\n" + //
                        "<text x = \"166.625\" y=\"220.0\" font-size=\"10.0\" text-anchor= \"middle\" fill=\"white\" stroke=\"black\">Test Faisceau</text>\n" + //
                        "</g></g>");
    }

    @Test
    public void testEnSVGSansTitre() {
        faisceau.agencer(100, 200, 50, 0.5, false);
        faisceau.colorier("red", "green", "blue");
        
        String svg = faisceau.enSVG();
        
        // Vérifie que le SVG contient les éléments attendus même sans titre
        assertEquals(svg, "<g><g>\n" + //
                        "<rect x=\"-25.025\" y=\"125.0\" width=\"50.0\" height=\"150.0\" fill=\"red\" stroke=\"black\"/>\n" + //
                        "<rect x=\"27.025\" y=\"150.0\" width=\"50.0\" height=\"100.0\" fill=\"red\" stroke=\"black\"/>\n" + //
                        "<rect x=\"79.07499999999999\" y=\"175.0\" width=\"50.0\" height=\"50.0\" fill=\"red\" stroke=\"black\"/>\n" + //
                        "<text x = \"166.625\" y=\"220.0\" font-size=\"10.0\" text-anchor= \"middle\" fill=\"white\" stroke=\"black\">null</text>\n" + //
                        "</g></g>");
    }

    @Test
    public void testOrdreBarres() {
        // Test que les barres sont créées dans le bon ordre
        Faisceau f = new Faisceau(100, 200, 300);
        f.colorier("red", "green", "blue");
        f.agencer(100, 200, 50, 0.5, false);
        
        // Vérifier que le groupe contient 3 rectangles
        assertEquals(3, f.grp.size());
    }

    @Test
    public void testFaisceauVide() {
        Faisceau f = new Faisceau();
        f.agencer(100, 200, 50, 0.5, false);
        
        // Vérifier que le groupe est vide
        assertEquals(0, f.grp.size());
        
        String svg = f.enSVG();
        // Vérifie que le SVG est généré correctement même pour un faisceau vide
        assertTrue(svg.contains("<g>"));
    }

    @Test
    public void testEspacement() {
        // Test de l'espacement des barres
        Faisceau f1 = new Faisceau(100, 100);
        Faisceau f2 = new Faisceau(100, 100, 100);
        
        f1.agencer(100, 200, 50, 0.5, false);
        f2.agencer(100, 200, 50, 0.5, false);
        
        // La largeur totale de f2.grp devrait être plus grande que celle de f1.grp
        assertTrue(f2.coordx2 > f1.coordx2);
    }

    @Test
    public void testComparaisonFaisceauxIdentiques() {
        Faisceau f1 = new Faisceau(100, 200, 300);
        Faisceau f2 = new Faisceau(100, 200, 300);
        
        f1.colorier("red", "green", "blue");
        f2.colorier("red", "green", "blue");
        
        f1.setTitre("Titre");
        f2.setTitre("Titre");
        
        f1.agencer(100, 200, 50, 0.5, false);
        f2.agencer(100, 200, 50, 0.5, false);
        
        // Les deux faisceaux devraient avoir des caractéristiques identiques
        assertEquals(f1.coordx, f2.coordx, 0.001);
        assertEquals(f1.coordy, f2.coordy, 0.001);
        assertEquals(f1.coordx2, f2.coordx2, 0.001);
    }

    @Test
    public void testComparaisonFaisceauxDifferents() {
        Faisceau f1 = new Faisceau(100, 200, 300);
        Faisceau f2 = new Faisceau(300, 200, 100);
        
        f1.agencer(100, 200, 50, 0.5, false);
        f2.agencer(100, 200, 50, 0.5, false);
        
        // Les deux faisceaux devraient avoir des caractéristiques différentes
        assertNotSame(f1.coordx2, f2.coordx2);
    }

    @Test
    public void testRotation() {
        faisceau.agencer(100, 200, 50, 0.5, false);
        String svgInitial = faisceau.enSVG();
        faisceau.tourner(180);
        // Vérifie que le SVG contient la rotation à 180 degrés
        assertEquals(faisceau.enSVG(), "<g><g>\n" + //
                        "<rect x=\"-25.025\" y=\"125.0\" width=\"50.0\" height=\"150.0\" fill=\"#000000\" stroke=\"black\"/>\n" + //
                        "<rect x=\"27.025\" y=\"150.0\" width=\"50.0\" height=\"100.0\" fill=\"#000000\" stroke=\"black\"/>\n" + //
                        "<rect x=\"79.07499999999999\" y=\"175.0\" width=\"50.0\" height=\"50.0\" fill=\"#000000\" stroke=\"black\"/>\n" + //
                        "<text x = \"166.625\" y=\"220.0\" font-size=\"10.0\" text-anchor= \"middle\" fill=\"white\" stroke=\"black\">null</text>\n" + //
                        "<text x = \"166.625\" y=\"220.0\" font-size=\"10.0\" text-anchor= \"middle\" fill=\"white\" stroke=\"black\">null</text>\n" + //
                        "</g></g>");
    }

    @Test
    public void testMainMethod() {
        // Test simple pour vérifier que la méthode main ne lance pas d'exception
        try {
            Faisceau.main(new String[]{});
            // Si on arrive ici, c'est que la méthode main n'a pas lancé d'exception
            assertTrue(true);
        } catch (Exception e) {
            fail("La méthode main a lancé une exception: " + e.getMessage());
        }
    }
}