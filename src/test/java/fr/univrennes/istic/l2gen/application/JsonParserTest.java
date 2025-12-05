package fr.univrennes.istic.l2gen.application;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class JsonParserTest {
    
    private File testFile;
    
    @Before
    public void setUp() throws IOException {
        String jsonContent = "{\"nom_officiel\":\"Musée du Louvre\",\"ville\":\"Paris\",\"departement\":\"75\",\"region\":\"Ile-de-France\",\"domaine_thematique\":[\"Art\",\"Histoire\"],\"themes\":[\"Peinture\",\"Sculpture\"],\"personnage_phare\":[\"Mona Lisa\"],\"url\":\"http://www.louvre.fr\",\"artiste\":\"Léonard de Vinci\",\"annee_creation\":\"1793\"}";
        testFile = File.createTempFile("test-musees", ".json");
        Files.write(Paths.get(testFile.getPath()), jsonContent.getBytes());
    }
    
    @Test
    public void testParseMusees() throws IOException {
        List<Musee> musees = JsonParser.parseMusees(testFile);
        
        assertNotNull(musees);
        assertFalse(musees.isEmpty());
        assertEquals(1, musees.size());
        
        Musee musee = musees.get(0);
        assertEquals("Musée du Louvre", musee.getNom());
        assertEquals("Paris", musee.getVille());
        assertEquals("75", musee.getDepartment());
        assertEquals("Ile-de-France", musee.getRegion());
        assertEquals("http://www.louvre.fr", musee.getUrl());
        assertEquals("Léonard de Vinci", musee.getArtiste());
        assertEquals(Integer.valueOf(1793), musee.getAnneeCreation());
        
        List<String> domaines = musee.getDomaineList();
        assertNotNull(domaines);
        assertEquals(2, domaines.size());
        assertTrue(domaines.contains("Art"));
        assertTrue(domaines.contains("Histoire"));
        
        List<String> themes = musee.getThemesList();
        assertNotNull(themes);
        assertEquals(2, themes.size());
        assertTrue(themes.contains("Peinture"));
        assertTrue(themes.contains("Sculpture"));
        
        List<String> personnages = musee.getPersonnageList();
        assertNotNull(personnages);
        assertEquals(1, personnages.size());
        assertTrue(personnages.contains("Mona Lisa"));
    }
    
    @Test
    public void testDecodeUnicodeEscapes() throws Exception {
        java.lang.reflect.Method method = JsonParser.class.getDeclaredMethod("decodeUnicodeEscapes", String.class);
        method.setAccessible(true);
        
        String input = "Mus\\u00e9e de la Guerre";
        String result = (String) method.invoke(null, input);
        assertEquals("Musée de la Guerre", result);
    }
    
    @Test
    public void testFindMatchingBrace() throws Exception {
        java.lang.reflect.Method method = JsonParser.class.getDeclaredMethod("findMatchingBrace", String.class, int.class);
        method.setAccessible(true);
        
        String json = "{\"nom\":\"Louvre\", \"details\":{\"ville\":\"Paris\"}}";
        int startIndex = 0;
        int result = (int) method.invoke(null, json, startIndex);
        assertEquals(json.length() - 1, result);
    }
    
    @Test
    public void testSaveJsonWithCorrectEncoding() throws IOException {
        String jsonContent = "{\"nom\":\"Mus\\u00e9e\"}";
        String outputPath = "test-output.json";
        
        JsonParser.saveJsonWithCorrectEncoding(jsonContent, outputPath);
        
        File outputFile = new File(outputPath);
        assertTrue(outputFile.exists());
        
        String content = new String(Files.readAllBytes(outputFile.toPath()));
        assertTrue(content.contains("Musée"));
        
        outputFile.delete();
    }
}