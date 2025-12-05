package fr.univrennes.istic.l2gen.application;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.ArrayList;

import org.junit.Test;

public class museeTest {

    @Test
    public void testNom() {
        Musee musee = new Musee();
        musee.setNom("Louvre");
        assertEquals("Louvre", musee.getNom());
    }

    @Test
    public void testVille() {
        Musee musee = new Musee();
        musee.setVille("Paris");
        assertEquals("Paris", musee.getVille());
    }

    @Test
    public void testDepartment() {
        Musee musee = new Musee();
        musee.setDepartment("75");
        assertEquals("75", musee.getDepartment());
    }

    @Test
    public void testRegion() {
        Musee musee = new Musee();
        musee.setRegion("Ile-de-France");
        assertEquals("Ile-de-France", musee.getRegion());
    }

    @Test
    public void testDomaine() {
        Musee musee = new Musee();
        musee.setDomaine("Art");
        assertEquals("Art", musee.getDomaine());
    }

    @Test
    public void testDomaineList() {
        Musee musee = new Musee();
        List<String> domaines = new ArrayList<>();
        domaines.add("Art");
        domaines.add("Histoire");
        musee.setDomaineList(domaines);
        assertEquals(domaines, musee.getDomaineList());
    }

    @Test
    public void testCategorie() {
        Musee musee = new Musee();
        musee.setCategorie("National");
        assertEquals("National", musee.getCategorie());
    }

    @Test
    public void testCategorieListe() {
        Musee musee = new Musee();
        List<String> categories = new ArrayList<>();
        categories.add("National");
        categories.add("Régional");
        musee.setCategorieList(categories);
        assertEquals(categories, musee.getCategorieList());
    }

    @Test
    public void testThemesList() {
        Musee musee = new Musee();
        List<String> themes = new ArrayList<>();
        themes.add("Peinture");
        themes.add("Sculpture");
        musee.setThemesList(themes);
        assertEquals(themes, musee.getThemesList());
    }

    @Test
    public void testPersonnage() {
        Musee musee = new Musee();
        musee.setPersonnage("Ricky Ngomege Kepseu Souleymane");
        assertEquals("Ricky Ngomege Kepseu Souleymane", musee.getPersonnage());
    }

    @Test
    public void testPersonnageList() {
        Musee musee = new Musee();
        List<String> personnages = new ArrayList<>();
        personnages.add("Monet");
        personnages.add("Van Gogh");
        musee.setPersonnageList(personnages);
        assertEquals(personnages, musee.getPersonnageList());
    }

    @Test
    public void testArtiste() {
        Musee musee = new Musee();
        musee.setArtiste("Yakin Ngomege Kepseu Souleymane");
        assertEquals("Yakin Ngomege Kepseu Souleymane", musee.getArtiste());
    }

    @Test
    public void testUrl() {
        Musee musee = new Musee();
        musee.setUrl("http://example.com");
        assertEquals("http://example.com", musee.getUrl());
    }

    @Test
    public void testAnneeCreation() {
        Musee musee = new Musee();
        musee.setAnneeCreation(2023);
        assertEquals(Integer.valueOf(2023), Integer.valueOf(musee.getAnneeCreation()));
    }
}
