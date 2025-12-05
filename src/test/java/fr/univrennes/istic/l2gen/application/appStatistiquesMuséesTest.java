package fr.univrennes.istic.l2gen.application;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.lang.reflect.Method;

public class appStatistiquesMuséesTest {
    
    private appStatistiquesMusées app;
    private List<Musee> testMusees;
    
    @Before
    public void setUp() {
        app = new appStatistiquesMusées();
        
        testMusees = new ArrayList<>();
        
        Musee musee1 = new Musee();
        musee1.setNom("Musée du Louvre");
        musee1.setVille("Paris");
        musee1.setDepartment("Paris");
        musee1.setRegion("Ile-de-France");
        List<String> domaines1 = new ArrayList<>();
        domaines1.add("Art");
        musee1.setDomaineList(domaines1);
        List<String> categories1 = new ArrayList<>();
        categories1.add("National");
        musee1.setCategorieList(categories1);
        musee1.setAnneeCreation(1793);
        testMusees.add(musee1);
        
        Musee musee2 = new Musee();
        musee2.setNom("Musée d'Orsay");
        musee2.setVille("Paris");
        musee2.setDepartment("Paris");
        musee2.setRegion("Ile-de-France");
        List<String> domaines2 = new ArrayList<>();
        domaines2.add("Art");
        domaines2.add("Histoire");
        musee2.setDomaineList(domaines2);
        List<String> categories2 = new ArrayList<>();
        categories2.add("National");
        musee2.setCategorieList(categories2);
        musee2.setAnneeCreation(1900);
        testMusees.add(musee2);
        
        Musee musee3 = new Musee();
        musee3.setNom("Musée des Beaux-Arts");
        musee3.setVille("Lyon");
        musee3.setDepartment("Rhône");
        musee3.setRegion("Auvergne-Rhône-Alpes");
        List<String> domaines3 = new ArrayList<>();
        domaines3.add("Art");
        musee3.setDomaineList(domaines3);
        List<String> categories3 = new ArrayList<>();
        categories3.add("Municipal");
        musee3.setCategorieList(categories3);
        musee3.setAnneeCreation(1850);
        testMusees.add(musee3);
    }
    
    @Test
    public void testFiltrerMusees() throws Exception {
        Method method = appStatistiquesMusées.class.getDeclaredMethod("filtrerMusees", 
                String.class, String.class, String.class, String.class, String.class, String.class, 
                List.class, List.class);
        method.setAccessible(true);
        
        java.lang.reflect.Field museesField = appStatistiquesMusées.class.getDeclaredField("musees");
        museesField.setAccessible(true);
        museesField.set(app, testMusees);
        
        @SuppressWarnings("unchecked")
        List<Musee> result1 = (List<Musee>) method.invoke(app, null, null, null, null, "Paris", null, null, null);
        assertEquals(2, result1.size());
        
        List<String> regions = new ArrayList<>();
        regions.add("Auvergne-Rhône-Alpes");
        @SuppressWarnings("unchecked")
        List<Musee> result2 = (List<Musee>) method.invoke(app, null, null, null, null, null, null, regions, null);
        assertEquals(1, result2.size());
        assertEquals("Musée des Beaux-Arts", result2.get(0).getNom());
        
        @SuppressWarnings("unchecked")
        List<Musee> result3 = (List<Musee>) method.invoke(app, "Histoire", null, null, null, null, null, null, null);
        assertEquals(1, result3.size());
        assertEquals("Musée d'Orsay", result3.get(0).getNom());
    }
    
    @Test
    public void testGenererStatistiquesDomaine() throws Exception {
        Method method = appStatistiquesMusées.class.getDeclaredMethod("genererStatistiquesDomaine", List.class);
        method.setAccessible(true);
        
        @SuppressWarnings("unchecked")
        Map<String, Integer> result = (Map<String, Integer>) method.invoke(app, testMusees);
        
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(Integer.valueOf(3), result.get("Art"));
        assertEquals(Integer.valueOf(1), result.get("Histoire"));
    }
    
    @Test
    public void testGenererStatistiquesAge() throws Exception {
        Method method = appStatistiquesMusées.class.getDeclaredMethod("genererStatistiquesAge", List.class);
        method.setAccessible(true);
        
        @SuppressWarnings("unchecked")
        Map<String, Integer> result = (Map<String, Integer>) method.invoke(app, testMusees);
        
        assertNotNull(result);
        int total = result.values().stream().mapToInt(Integer::intValue).sum();
        assertEquals(testMusees.size(), total);
    }
    
    @Test
    public void testAjouterTableauStatistiques() throws Exception {
        Method method = appStatistiquesMusées.class.getDeclaredMethod("ajouterTableauStatistiques", 
                StringBuilder.class, Map.class, String.class, String.class);
        method.setAccessible(true);
        
        StringBuilder html = new StringBuilder();
        Map<String, Integer> stats = Map.of("Domaine1", 10, "Domaine2", 20);
        
        method.invoke(app, html, stats, "Domaine", "Nombre");
        
        String result = html.toString();
        assertTrue(result.contains("<table>"));
        assertTrue(result.contains("<th>Domaine</th>"));
        assertTrue(result.contains("<th>Nombre</th>"));
        assertTrue(result.contains("Domaine1"));
        assertTrue(result.contains("10"));
        assertTrue(result.contains("Domaine2"));
        assertTrue(result.contains("20"));
        assertTrue(result.contains("</table>"));
    }
}