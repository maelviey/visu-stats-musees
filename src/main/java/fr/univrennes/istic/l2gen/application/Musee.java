package fr.univrennes.istic.l2gen.application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe représentant un musée avec ses informations détaillées.
 * 
 * Cette classe contient des propriétés liées à un musée, comme son nom, sa ville, son département, etc.
 * Elle fournit également des méthodes pour accéder et modifier ces propriétés.
 */
class Musee {
    private String nom;
    private String ville;
    private String department;
    private String region;
    private List<String> domaineList = new ArrayList<>();
    private String categorie;
    private List<String> themesList = new ArrayList<>();
    private List<String> personnageList = new ArrayList<>();
    private String artiste;
    private String url;
    private Integer anneeCreation;
    
    /**
     * Constructeur par défaut.
     */
    public Musee() {
    }
    
    /**
     * Obtient le nom du musée.
     * 
     * @return Le nom du musée.
     */
    public String getNom() {
        return nom;
    }
    
    /**
     * Définit le nom du musée.
     * 
     * @param nom Le nom à définir pour le musée.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    /**
     * Obtient la ville où se situe le musée.
     * 
     * @return La ville du musée.
     */
    public String getVille() {
        return ville;
    }
    
    /**
     * Définit la ville où se situe le musée.
     * 
     * @param ville La ville à définir pour le musée.
     */
    public void setVille(String ville) {
        this.ville = ville;
    }
    
    /**
     * Obtient le département où se situe le musée.
     * 
     * @return Le département du musée.
     */
    public String getDepartment() {
        return department;
    }
    
    /**
     * Définit le département où se situe le musée.
     * 
     * @param department Le département à définir pour le musée.
     */
    public void setDepartment(String department) {
        this.department = department;
    }
    
    /**
     * Obtient la région où se situe le musée.
     * 
     * @return La région du musée.
     */
    public String getRegion() {
        return region;
    }
    
    /**
     * Définit la région où se situe le musée.
     * 
     * @param region La région à définir pour le musée.
     */
    public void setRegion(String region) {
        this.region = region;
    }
    
    /**
     * Obtient le domaine thématique du musée.
     * 
     * @return Le domaine thématique du musée.
     */
    public String getDomaine() {
        return domaineList.isEmpty() ? null : String.join(", ", domaineList);
    }

    /**
     * Définit le domaine thématique du musée.
     * 
     * @param domaine Le domaine à définir pour le musée.
     */
    public void setDomaine(String domaine) {
        this.domaineList = new ArrayList<>();
        if (domaine != null && !domaine.isEmpty()) {
            this.domaineList.add(domaine);
        }
    }

    /**
     * Obtient la liste des domaines du musée.
     * 
     * @return La liste des domaines du musée.
     */
    public List<String> getDomaineList() { 
        return domaineList; 
    }
    
    /**
     * Obtient le domaine du musée.
     * 
     * @return Le domaine du musée.
     */
    public void setDomaineList(List<String> domaineList) {
        this.domaineList = domaineList != null ? domaineList : new ArrayList<>();
    }

    /**
     * Obtient la catégorie du musée.
     * 
     * @return La catégorie du musée.
     */
    public String getCategorie() {
        return categorie;
    }
    
    /**
     * Définit la catégorie du musée.
     * 
     * @param categorie La catégorie à définir pour le musée.
     */
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    /**
     * Définit la liste des catégories du musée.
     * 
     * @param categorieList La liste des catégories à définir pour le musée.
     */
    public void setCategorieList(List<String> categorieList) {
        this.categorie = String.join(". ", categorieList);
    }

    /**
     * Obtient la liste des catégories du musée.
     * 
     * @return La liste des catégories du musée.
     */
    public List<String> getCategorieList() {
        return categorie != null ? Arrays.asList(categorie.split("\\.\\s*")) : new ArrayList<>();
    }

    /**
     * Obtient les thèmes du musée.
     * 
     * @return Les thèmes du musée.
     */
    public String getThemes() {
        return themesList.isEmpty() ? null : String.join(", ", themesList);
    }

    /**
     * Obtient la liste des thèmes du musée.
     * 
     * @return La liste des thèmes du musée.
     */
    public List<String> getThemesList() {
        return themesList;
    }

    /**
     * Définit les thèmes du musée.
     * 
     * @param themes Les thèmes à définir pour le musée.
     */
    public void setThemesList(List<String> themesList) {
        this.themesList = themesList != null ? themesList : new ArrayList<>();
    }

    /**
     * Obtient le personnage phare du musée.
     * 
     * @return Le personnage phare du musée.
     */
    public String getPersonnage() {
        return personnageList.isEmpty() ? null : String.join(", ", personnageList);
    }

    /**
     * Définit le personnage phare du musée.
     * 
     * @param personnage Le personnage à définir pour le musée.
     */
    public void setPersonnage(String personnage) {
        this.personnageList = new ArrayList<>();
        if (personnage != null && !personnage.isEmpty()) {
            this.personnageList.add(personnage);
        }
    }

    /**
     * Obtient la liste des personnages du musée.
     * 
     * @return La liste des personnages du musée.
     */
    public List<String> getPersonnageList() {
        return personnageList;
    }

    /**
     * Définit la liste des personnages du musée.
     * 
     * @param personnageList La liste des personnages à définir pour le musée.
     */
    public void setPersonnageList(List<String> personnageList) {
        this.personnageList = personnageList != null ? personnageList : new ArrayList<>();
    }
    
    /**
     * Obtient l'artiste associé au musée.
     * 
     * @return L'artiste du musée.
     */
    public String getArtiste() {
        return artiste;
    }

    /**
     * Définit l'artiste associé au musée.
     * 
     * @param artiste L'artiste à définir pour le musée.
     */
    public void setArtiste(String artiste) {
        this.artiste = artiste;
    }
    
    /**
     * Obtient l'URL du musée.
     * 
     * @return L'URL du musée.
     */
    public String getUrl() {
        return url;
    }
    
    /**
     * Définit l'URL du musée.
     * 
     * @param url L'URL à définir pour le musée.
     */
    public void setUrl(String url) {
        this.url = url;
    }
    
    /**
     * Obtient l'année de création du musée.
     * 
     * @return L'année de création du musée.
     */
    public Integer getAnneeCreation() {
        return anneeCreation;
    }
    
    /**
     * Définit l'année de création du musée.
     * 
     * @param anneeCreation L'année de création à définir pour le musée.
     */
    public void setAnneeCreation(Integer anneeCreation) {
        this.anneeCreation = anneeCreation;
    }

}