package fr.univrennes.istic.l2gen.application;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import fr.univrennes.istic.l2gen.application.ColorManager;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

import fr.univrennes.istic.l2gen.visustats.DiagBarres;
import fr.univrennes.istic.l2gen.visustats.DiagCamemberts;
import fr.univrennes.istic.l2gen.visustats.DiagColonnes;
import fr.univrennes.istic.l2gen.visustats.IDataVisuliseur;

public class appStatistiquesMusées extends JFrame {
    private static final String DATA_URL = "https://www.data.gouv.fr/fr/datasets/r/a5c5d76e-979a-4073-ba0d-0844bb3c1398";
    private static final String DATA_FILE = "musees.json";

    private List<Musee> musees;
    private final JPanel panneauPrincipal;
    private final JComboBox<String> boiteGranularite;
    private final JPanel panneauCriteres;
    private final JPanel panneauStatistiques;
    private final JPanel panneauDiagramme;
    private final JButton boutonGenererRapport;

    private JComboBox<String> boiteDomaineThematique;
    private JComboBox<String> boiteCategorie;
    private JComboBox<String> boiteThemes;
    private JComboBox<String> boitePersonnage;
    private JComboBox<String> boiteVille;
    private JComboBox<String> boiteArtiste;

    private JList<String> listeRegions;
    private JList<String> listeDepartements;

    private JCheckBox caseStatistiquesDomaine;
    private JCheckBox caseStatistiquesRegion;
    private JCheckBox caseStatistiquesAge;
    private JCheckBox caseListerMusees;

    private JComboBox<String> boiteTypeDiagramme;

    public appStatistiquesMusées() {
        setTitle("Statistiques des Musées Français");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panneauPrincipal = new JPanel(new BorderLayout());
        setContentPane(panneauPrincipal);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        boiteGranularite = new JComboBox<>(new String[] { "Départements", "Régions" });
        controlPanel.add(new JLabel("Granularité:"));
        controlPanel.add(boiteGranularite);

        boutonGenererRapport = new JButton("Générer le Rapport");
        controlPanel.add(boutonGenererRapport);

        panneauPrincipal.add(controlPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        panneauCriteres = creerPanneauCriteres();
        centerPanel.add(panneauCriteres);

        panneauStatistiques = creerPanneauStatistiques();
        centerPanel.add(panneauStatistiques);

        panneauPrincipal.add(centerPanel, BorderLayout.CENTER);

        panneauDiagramme = new JPanel(new BorderLayout());
        panneauDiagramme.setBorder(BorderFactory.createTitledBorder("Visualisation"));
        JScrollPane scrollPane = new JScrollPane(panneauDiagramme);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        panneauPrincipal.add(scrollPane, BorderLayout.SOUTH);
        panneauDiagramme.setBorder(BorderFactory.createTitledBorder("Visualisation"));
        panneauDiagramme.setPreferredSize(new Dimension(800, 300));
        panneauDiagramme.setLayout(new BoxLayout(panneauDiagramme, BoxLayout.Y_AXIS));
        panneauPrincipal.add(panneauDiagramme, BorderLayout.SOUTH);

        chargerDonnees();

        setupListeners();
    }

    /**
     * Crée le panneau contenant les critères de sélection.
     * 
     * @return JPanel contenant les critères de sélection.
     */
    private JPanel creerPanneauCriteres() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Critères de Sélection"));

        JPanel fieldsPanel = new JPanel(new GridLayout(6, 2, 5, 5));

        fieldsPanel.add(new JLabel("Domaine thématique:"));
        boiteDomaineThematique = new JComboBox<>();
        fieldsPanel.add(boiteDomaineThematique);

        fieldsPanel.add(new JLabel("Catégorie:"));
        boiteCategorie = new JComboBox<>();
        fieldsPanel.add(boiteCategorie);

        fieldsPanel.add(new JLabel("Thèmes:"));
        boiteThemes = new JComboBox<>();
        fieldsPanel.add(boiteThemes);

        fieldsPanel.add(new JLabel("Personnage:"));
        boitePersonnage = new JComboBox<>();
        fieldsPanel.add(boitePersonnage);

        fieldsPanel.add(new JLabel("Ville:"));
        boiteVille = new JComboBox<>();
        fieldsPanel.add(boiteVille);

        fieldsPanel.add(new JLabel("Artiste:"));
        boiteArtiste = new JComboBox<>();
        fieldsPanel.add(boiteArtiste);

        panel.add(fieldsPanel, BorderLayout.NORTH);

        JPanel selectionPanel = new JPanel(new CardLayout());

        JPanel departmentsPanel = new JPanel(new BorderLayout());
        departmentsPanel.setBorder(BorderFactory.createTitledBorder("Départements"));
        listeDepartements = new JList<>();
        listeDepartements.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane departmentsScrollPane = new JScrollPane(listeDepartements);
        departmentsPanel.add(departmentsScrollPane, BorderLayout.CENTER);

        JPanel regionsPanel = new JPanel(new BorderLayout());
        regionsPanel.setBorder(BorderFactory.createTitledBorder("Régions"));
        listeRegions = new JList<>();
        listeRegions.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane regionsScrollPane = new JScrollPane(listeRegions);
        regionsPanel.add(regionsScrollPane, BorderLayout.CENTER);

        selectionPanel.add(departmentsPanel, "Départements");
        selectionPanel.add(regionsPanel, "Régions");

        panel.add(selectionPanel, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Crée le panneau contenant les critères de sélection.
     * 
     * @return JPanel contenant les critères de sélection.
     */
    private JPanel creerPanneauStatistiques() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Statistiques"));

        JPanel statsPanel = new JPanel(new GridLayout(4, 1, 5, 5));

        caseStatistiquesDomaine = new JCheckBox("Nombre de musées par domaine thématique");
        caseStatistiquesRegion = new JCheckBox("Nombre de musées par région/département");
        caseStatistiquesAge = new JCheckBox("Statistiques d'âge des musées");
        caseListerMusees = new JCheckBox("Liste des musées avec URL");

        statsPanel.add(caseStatistiquesDomaine);
        statsPanel.add(caseStatistiquesRegion);
        statsPanel.add(caseStatistiquesAge);
        statsPanel.add(caseListerMusees);

        panel.add(statsPanel, BorderLayout.NORTH);

        JPanel chartSelectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        chartSelectionPanel.add(new JLabel("Type de diagramme:"));
        boiteTypeDiagramme = new JComboBox<>(new String[] { "Camembert", "Barres", "Colonnes" });
        chartSelectionPanel.add(boiteTypeDiagramme);

        panel.add(chartSelectionPanel, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Charge les données des musées à partir du fichier JSON ou le télécharge si
     * nécessaire.
     */
    private void chargerDonnees() {
        try {
            File file = new File("musees.json");
            if (!file.exists()) {
                telechargerDonnees();
            }

            musees = JsonParser.parseMusees(file);

            Set<String> domains = musees.stream()
                    .flatMap(m -> m.getDomaineList().stream())
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            Set<String> categories = musees.stream()
                    .flatMap(m -> m.getCategorieList().stream())
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            Set<String> themes = musees.stream()
                    .flatMap(m -> m.getThemesList().stream())
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            Set<String> personnages = musees.stream()
                    .map(Musee::getPersonnage)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            Set<String> cities = musees.stream()
                    .map(Musee::getVille)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            Set<String> artistes = musees.stream()
                    .map(Musee::getArtiste)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            Set<String> departments = musees.stream()
                    .map(Musee::getDepartment)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            Set<String> regions = musees.stream()
                    .map(Musee::getRegion)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            ArrayList<String> sortedDomains = new ArrayList<>(domains);
            Collections.sort(sortedDomains);
            boiteDomaineThematique.addItem("Tous");
            sortedDomains.forEach(boiteDomaineThematique::addItem);

            ArrayList<String> sortedCategories = new ArrayList<>(categories);
            Collections.sort(sortedCategories);
            boiteCategorie.addItem("Toutes");
            sortedCategories.forEach(boiteCategorie::addItem);

            ArrayList<String> sortedThemes = new ArrayList<>(themes);
            Collections.sort(sortedThemes);
            boiteThemes.addItem("Tous");
            sortedThemes.forEach(boiteThemes::addItem);

            ArrayList<String> sortedPersonnages = new ArrayList<>(personnages);
            Collections.sort(sortedPersonnages);
            boitePersonnage.addItem("Tous");
            sortedPersonnages.forEach(boitePersonnage::addItem);

            ArrayList<String> sortedCities = new ArrayList<>(cities);
            Collections.sort(sortedCities);
            boiteVille.addItem("Tous");
            sortedCities.forEach(boiteVille::addItem);

            ArrayList<String> sortedArtistes = new ArrayList<>(artistes);
            Collections.sort(sortedArtistes);
            boiteArtiste.addItem("Tous");
            sortedArtistes.forEach(boiteArtiste::addItem);

            ArrayList<String> sortedDepartments = new ArrayList<>(departments);
            Collections.sort(sortedDepartments);
            listeDepartements.setListData(sortedDepartments.toArray(String[]::new));

            ArrayList<String> sortedRegions = new ArrayList<>(regions);
            Collections.sort(sortedRegions);
            listeRegions.setListData(sortedRegions.toArray(String[]::new));

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des données: " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Télécharge les données des musées depuis l'URL spécifiée.
     */
    private void telechargerDonnees() {
        try {
            @SuppressWarnings("deprecation")
            URL url = new URL(DATA_URL);
            StringBuilder jsonContent = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
                String ligne;
                while ((ligne = reader.readLine()) != null) {
                    jsonContent.append(ligne).append("\n");
                }
            }

            JsonParser.saveJsonWithCorrectEncoding(jsonContent.toString(), DATA_FILE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du téléchargement des données: " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Configure les listeners pour les composants de l'interface utilisateur.
     */
    private void setupListeners() {
        boiteGranularite.addActionListener(e -> {
            CardLayout cl = (CardLayout) (((JPanel) panneauCriteres.getComponent(1)).getLayout());
            cl.show((JPanel) panneauCriteres.getComponent(1), (String) boiteGranularite.getSelectedItem());
        });

        boutonGenererRapport.addActionListener(e -> genererRapport());
    }

    /**
     * Génère le rapport basé sur les critères sélectionnés et affiche les
     * statistiques.
     */
    private void genererRapport() {
        String granularity = (String) boiteGranularite.getSelectedItem();
        String thematicDomain = boiteDomaineThematique.getSelectedItem().equals("Tous") ? null
                : (String) boiteDomaineThematique.getSelectedItem();
        String category = boiteCategorie.getSelectedItem().equals("Toutes") ? null
                : (String) boiteCategorie.getSelectedItem();
        String themes = boiteThemes.getSelectedItem().equals("Tous") ? null : (String) boiteThemes.getSelectedItem();
        String personnage = boitePersonnage.getSelectedItem().equals("Tous") ? null
                : (String) boitePersonnage.getSelectedItem();
        String ville = boiteVille.getSelectedItem().equals("Tous") ? null : (String) boiteVille.getSelectedItem();
        String artiste = boiteArtiste.getSelectedItem().equals("Tous") ? null : (String) boiteArtiste.getSelectedItem();

        List<String> selectedRegions = listeRegions.getSelectedValuesList();
        List<String> selectedDepartments = listeDepartements.getSelectedValuesList();

        List<Musee> filteredMuseums = filtrerMusees(thematicDomain, category, themes, personnage, ville, artiste,
                granularity.equals("Régions") ? selectedRegions : null,
                granularity.equals("Départements") ? selectedDepartments : null);

        StringBuilder reportHtml = new StringBuilder();
        reportHtml.append("<html><head><title>Rapport Statistiques des Musées</title>");
        reportHtml.append("<style>body { font-family: Arial, sans-serif; margin: 20px; }")
                .append("h1, h2 { color: #333366; }")
                .append("table { border-collapse: collapse; width: 100%; margin-bottom: 20px; }")
                .append("th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }")
                .append("th { background-color: #f2f2f2; }</style></head><body>")
                .append("<h1>Rapport Statistiques des Musées</h1>")
                .append("<h2>Critères de sélection</h2><ul>")
                .append("<li>Granularité: ").append(granularity).append("</li>");

        if (thematicDomain != null)
            reportHtml.append("<li>Domaine thématique: ").append(thematicDomain).append("</li>");
        if (category != null)
            reportHtml.append("<li>Catégorie: ").append(category).append("</li>");
        if (themes != null)
            reportHtml.append("<li>Thèmes: ").append(themes).append("</li>");
        if (personnage != null)
            reportHtml.append("<li>Personnage: ").append(personnage).append("</li>");
        if (!selectedRegions.isEmpty() && granularity.equals("Régions"))
            reportHtml.append("<li>Régions: ").append(String.join(", ", selectedRegions)).append("</li>");
        if (!selectedDepartments.isEmpty() && granularity.equals("Départements"))
            reportHtml.append("<li>Départements: ").append(String.join(", ", selectedDepartments)).append("</li>");
        reportHtml.append("</ul>");

        boolean showDomainStats = caseStatistiquesDomaine.isSelected();
        boolean showRegionStats = caseStatistiquesRegion.isSelected();
        boolean showAgeStats = caseStatistiquesAge.isSelected();
        boolean showMuseumsList = caseListerMusees.isSelected();

        Map<String, Integer> domainsStats = showDomainStats ? genererStatistiquesDomaine(filteredMuseums) : null;
        Map<String, Integer> regionStats = showRegionStats ? genererStatistiquesRegion(filteredMuseums, granularity)
                : null;
        Map<String, Integer> ageStats = showAgeStats ? genererStatistiquesAge(filteredMuseums) : null;

        reportHtml.append("<h2>Résultats</h2>")
                .append("<p>Nombre total de musées: ").append(filteredMuseums.size()).append("</p>");

        if (showDomainStats && !domainsStats.isEmpty()) {
            reportHtml.append("<h3>Statistiques par domaine thématique</h3>");
            ajouterTableauStatistiques(reportHtml, domainsStats, "Domaine", "Nombre de musées");
        }

        if (showRegionStats && !regionStats.isEmpty()) {
            reportHtml.append("<h3>Statistiques par ").append(granularity.toLowerCase()).append("</h3>");
            ajouterTableauStatistiques(reportHtml, regionStats, granularity, "Nombre de musées");
        }

        if (showAgeStats && !ageStats.isEmpty()) {
            reportHtml.append("<h3>Statistiques par âge</h3>");
            ajouterTableauStatistiques(reportHtml, ageStats, "Tranche d'âge", "Nombre de musées");
        }

        if (showMuseumsList) {
            reportHtml.append("<h3>Liste des musées</h3><table>")
                    .append("<tr><th>Nom</th><th>Ville</th><th>").append(granularity).append("</th><th>URL</th></tr>");
            filteredMuseums.forEach(musee -> {
                reportHtml.append("<tr><td>").append(musee.getNom()).append("</td>")
                        .append("<td>").append(musee.getVille()).append("</td>")
                        .append("<td>")
                        .append(granularity.equals("Régions") ? musee.getRegion() : musee.getDepartment())
                        .append("</td>")
                        .append("<td>")
                        .append(musee.getUrl() != null ? "<a href='" + musee.getUrl() + "'>Lien</a>" : "N/A")
                        .append("</td></tr>");
            });
            reportHtml.append("</table>");
        }
        reportHtml.append("</body></html>");

        JFrame reportFrame = new JFrame("Rapport des Statistiques");
        reportFrame.setSize(1000, 800);
        reportFrame.setLocationRelativeTo(this);
        JEditorPane editorPane = new JEditorPane("text/html", reportHtml.toString());
        editorPane.setEditable(false);
        reportFrame.add(new JScrollPane(editorPane));
        reportFrame.setVisible(true);

        try {
            panneauDiagramme.removeAll();
            panneauDiagramme.setLayout(new GridLayout(0, 1, 5, 5));

            String typeDiag = (String) boiteTypeDiagramme.getSelectedItem();
            List<Map<String, Integer>> allStats = new ArrayList<>();

            if (showDomainStats && domainsStats != null && !domainsStats.isEmpty())
                allStats.add(domainsStats);
            if (showRegionStats && regionStats != null && !regionStats.isEmpty())
                allStats.add(regionStats);
            if (showAgeStats && ageStats != null && !ageStats.isEmpty())
                allStats.add(ageStats);

            for (Map<String, Integer> stats : allStats) {
                try {
                    IDataVisuliseur diagram;
                    String baseTitle = getTitreStat(stats, granularity);

                    switch (typeDiag) {
                        case "Colonnes":
                            diagram = new DiagColonnes(baseTitle, 800, 600);
                            stats.forEach((k, v) -> diagram.ajouterDonnees(k, v));
                            break;

                        case "Barres":
                            diagram = new DiagBarres(baseTitle, 800, 600);
                            stats.forEach((k, v) -> diagram.ajouterDonnees(k, v));
                            break;

                        case "Camembert":
                        default:
                            diagram = new DiagCamemberts(baseTitle, 800, 600);
                            stats.forEach((k, v) -> diagram.ajouterDonnees(k, v));
                            break;
                    }

                    diagram.agencer();
                    diagram.legender("Occurrences");

                    afficherDiagramme(diagram);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                            "Erreur avec le dataset: " + ex.getMessage(),
                            "Problème de données",
                            JOptionPane.WARNING_MESSAGE);
                }
            }

            panneauDiagramme.revalidate();
            panneauDiagramme.repaint();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erreur lors de la génération du diagramme : " + ex.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    /**
     * Affiche le diagramme dans le panneau de visualisation.
     * 
     * @param diagram Le diagramme à afficher.
     */
    private String getTitreStat(Map<String, Integer> stats, String granularity) {
        if (stats == null)
            return "";
        if (stats.containsKey("Moins de 50 ans"))
            return "Répartition par âge";
        if (stats.keySet().stream().anyMatch(k -> k.contains("Région")))
            return "Par région";
        if (stats.keySet().stream().anyMatch(k -> k.matches("\\d{2}")))
            return "Par département";
        return "Par domaine thématique";
    }

    /**
     * Ajoute un tableau HTML de statistiques au rapport.
     *
     * @param html      le StringBuilder dans lequel insérer le tableau
     * @param stats     map contenant les libellés et leurs valeurs numériques
     * @param labelCol1 libellé de la première colonne (ex. "Domaine", "Région",
     *                  "Tranche d'âge")
     * @param labelCol2 libellé de la deuxième colonne (ex. "Nombre de musées")
     */
    private void ajouterTableauStatistiques(StringBuilder html, Map<String, Integer> stats, String labelCol1,
            String labelCol2) {
        html.append("<table>");
        html.append("<tr><th>").append(labelCol1).append("</th><th>").append(labelCol2).append("</th></tr>");

        for (Map.Entry<String, Integer> entry : stats.entrySet()) {
            html.append("<tr>");
            html.append("<td>").append(entry.getKey()).append("</td>");
            html.append("<td>").append(entry.getValue()).append("</td>");
            html.append("</tr>");
        }

        html.append("</table>");
    }

    /**
     * Filtre une liste de musées en fonction de plusieurs critères.
     * 
     * @param domaineThematique Le domaine thématique du musée (peut être null).
     * @param categorie         La catégorie du musée (peut être null).
     * @param motsCles          Un tableau de mots-clés pour filtrer par thème.
     * @param personnage        Un personnage historique associé au musée (peut être
     *                          null).
     * @param regions           Une liste des régions à inclure dans les résultats
     *                          (peut être null ou vide).
     * @param departments       Une liste des départements à inclure dans les
     *                          résultats (peut être null ou vide).
     * @return Une liste filtrée de musées correspondant aux critères.
     */
    private List<Musee> filtrerMusees(String domaineThematique, String categorie, String themes, String personnage,
            String ville, String artiste, List<String> regions, List<String> departments) {
        return musees.stream()
                .filter(musee -> (domaineThematique == null || domaineThematique.equals("Tous")
                        || musee.getDomaineList().contains(domaineThematique)) &&
                        (categorie == null || categorie.equals("Toutes")
                                || musee.getCategorieList().contains(categorie))
                        &&
                        (themes == null || themes.equals("Tous") || musee.getThemesList().contains(themes)) &&
                        (personnage == null || personnage.equals("Tous") || musee.getPersonnage().contains(personnage))
                        &&
                        (ville == null
                                || ville.equals("Tous") || (musee.getVille() != null && ville.equals(musee.getVille())))
                        &&
                        (artiste == null || artiste.equals("Tous")
                                || (musee.getArtiste() != null && artiste.equals(musee.getArtiste())))
                        &&
                        (regions == null || regions.isEmpty()
                                || (musee.getRegion() != null && regions.contains(musee.getRegion())))
                        &&
                        (departments == null || departments.isEmpty()
                                || (musee.getDepartment() != null && departments.contains(musee.getDepartment()))))
                .collect(Collectors.toList());
    }

    /**
     * Génère des statistiques sur le nombre de musées par domaine thématique.
     * 
     * @param musees La liste des musées à analyser.
     * @return Une map contenant les domaines thématiques et le nombre de musées
     *         associés.
     */
    private Map<String, Integer> genererStatistiquesDomaine(List<Musee> musees) {
        Map<String, Integer> domaines = new HashMap<>();

        for (Musee musee : musees) {
            for (String domain : musee.getDomaineList()) {
                if (domain != null) {
                    domaines.put(domain, domaines.getOrDefault(domain, 0) + 1);
                }
            }
        }
        return domaines;
    }

    /**
     * Génère des statistiques sur le nombre de musées par région ou département.
     * 
     * @param musees      La liste des musées à analyser.
     * @param granularite La granularité (régions ou départements).
     * @return Une map contenant les régions ou départements et le nombre de musées
     *         associés.
     */
    private Map<String, Integer> genererStatistiquesRegion(List<Musee> musees, String granularite) {
        Map<String, Integer> regions = new HashMap<>();

        for (Musee musee : musees) {
            String regionOrDept = granularite.equals("Régions")
                    ? musee.getRegion()
                    : musee.getDepartment();
            if (regionOrDept != null) {
                regionOrDept = regionOrDept.trim();
                if (!regionOrDept.isEmpty()) {
                    regions.put(regionOrDept,
                            regions.getOrDefault(regionOrDept, 0) + 1);
                }
            }
        }

        return regions;
    }

    /**
     * Génère des statistiques sur l'âge des musées.
     * 
     * @param musees La liste des musées à analyser.
     * @return Une map contenant les tranches d'âge et le nombre de musées associés.
     */
    private Map<String, Integer> genererStatistiquesAge(List<Musee> musees) {
        Map<String, Integer> ages = new HashMap<>();
        int anneeCourante = Calendar.getInstance().get(Calendar.YEAR);

        for (Musee musee : musees) {
            Integer anneeCreation = musee.getAnneeCreation();
            if (anneeCreation != null) {
                int age = anneeCourante - anneeCreation;

                String trancheAge;
                if (age < 50) {
                    trancheAge = "Moins de 50 ans";
                } else if (age < 100) {
                    trancheAge = "50-100 ans";
                } else if (age < 200) {
                    trancheAge = "100-200 ans";
                } else if (age < 300) {
                    trancheAge = "200-300 ans";
                } else {
                    trancheAge = "Plus de 300 ans";
                }

                ages.put(trancheAge, ages.getOrDefault(trancheAge, 0) + 1);
            }
        }

        return ages;
    }

    /**
     * Affiche un diagramme dans l'interface graphique.
     * 
     * @param diagramme L'objet VisuStats à afficher.
     */
    private void afficherDiagramme(IDataVisuliseur diagramme) {
        try {
            String svgContent = diagramme.enSVG();

            if (svgContent == null || svgContent.trim().isEmpty()) {
                throw new RuntimeException("Aucun contenu SVG généré par le diagramme");
            }

            PNGTranscoder transcoder = new PNGTranscoder();
            transcoder.addTranscodingHint(PNGTranscoder.KEY_WIDTH, (float) diagramme.largeur());
            transcoder.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, (float) diagramme.hauteur());

            TranscoderInput input = new TranscoderInput(new StringReader(svgContent));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            transcoder.transcode(input, new TranscoderOutput(outputStream));
            byte[] pngData = outputStream.toByteArray();

            ImageIcon icon = new ImageIcon(pngData);
            JLabel imageLabel = new JLabel(icon);

            panneauDiagramme.removeAll();
            panneauDiagramme.setLayout(new BorderLayout());
            panneauDiagramme.add(new JScrollPane(imageLabel), BorderLayout.CENTER);
            panneauDiagramme.revalidate();
            panneauDiagramme.repaint();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur de rendu du diagramme:\n" + e.getMessage(),
                    "Erreur graphique",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}