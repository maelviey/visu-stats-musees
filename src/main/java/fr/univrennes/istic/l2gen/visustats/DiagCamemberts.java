package fr.univrennes.istic.l2gen.visustats;

import java.util.ArrayList;
import java.util.Arrays;

import fr.univrennes.istic.l2gen.application.ColorManager;
import fr.univrennes.istic.l2gen.geometrie.Groupe;
import fr.univrennes.istic.l2gen.geometrie.IForme;
import fr.univrennes.istic.l2gen.geometrie.Point;
import fr.univrennes.istic.l2gen.geometrie.Rectangle;
import fr.univrennes.istic.l2gen.geometrie.Texte;

public class DiagCamemberts implements IDataVisuliseur {

    /** Liste des camemberts composant le diagramme */
    public ArrayList<Camembert> camemberts;
    
    /** Titre du diagramme */
    private String titre;
    
    /** Largeur du diagramme en pixels */
    private double largeur = 800.0;
    
    /** Hauteur du diagramme en pixels */
    private double hauteur = 600.0;
    
    /** Liste des libellés des données à représenter */
    private ArrayList<String> donnees;
    
    /** Liste des valeurs numériques associées aux données */
    private ArrayList<Double> valeurs;
    
    /** Tableau des couleurs à utiliser pour les secteurs */
    private String[] couleurs;
    
    /** Liste des éléments de la légende */
    private ArrayList<String> legende;
    
    /** Groupe contenant les éléments graphiques (légende, etc.) */
    private Groupe groupe;
    
    /** Somme totale des valeurs (utilisée pour calculer les proportions) */
    private double totalValeurs = 0;

    /**
     * Construit un diagramme en camembert vide.
     *
     * @param titre   Titre du graphique qui sera affiché en haut
     * @param largeur Largeur du SVG en pixels
     * @param hauteur Hauteur du SVG en pixels
     */
    public DiagCamemberts(String titre, double largeur, double hauteur) {
        camemberts = new ArrayList<>();
        this.titre = titre;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.donnees = new ArrayList<>();
        this.valeurs = new ArrayList<>();
        this.legende = new ArrayList<>();
        this.groupe = new Groupe();
    }

    /**
     * Retourne le groupe contenant les éléments graphiques du diagramme.
     * Ce groupe peut être utilisé pour manipuler collectivement tous les
     * éléments graphiques.
     *
     * @return Le groupe d'éléments graphiques
     */
    public Groupe getGroupe() {
        return this.groupe;
    }

    /**
     * Dispose et génère les secteurs et la légende en fonction des données
     * précédemment ajoutées. Cette méthode doit être appelée après avoir
     * ajouté toutes les données et avant d'exporter ou d'afficher le diagramme.
     * 
     * La méthode calcule également les proportions basées sur la somme totale
     * des valeurs.
     */
    public void agencer() {
        camemberts.clear();
        groupe = new Groupe();

        // Définir la taille du camembert en fonction de la plus petite dimension
        double rayon = Math.min(this.largeur, this.hauteur) * 0.4;
        Point centre = new Point(this.largeur / 2, this.hauteur / 2);

        // Calculer la somme totale des valeurs pour les proportions
        totalValeurs = valeurs.stream().mapToDouble(Double::doubleValue).sum();

        // Créer le camembert principal
        Camembert principal = new Camembert(centre, rayon);
        camemberts.add(principal);

        if (totalValeurs > 0 && !valeurs.isEmpty()) {
            // Générer des couleurs si elles ne sont pas définies ou insuffisantes
            if (couleurs == null || couleurs.length < valeurs.size()) {
                couleurs = ColorManager.generateHuePalette(valeurs.size());
            }
            
            // Ajouter les secteurs au camembert avec les couleurs appropriées
            for (int i = 0; i < valeurs.size(); i++) {
                double valeur = valeurs.get(i);
                // Calcul de l'angle du secteur en degrés
                double arc = (valeur / totalValeurs) * 360.0;
                principal.ajouterSecteur(couleurs[i], arc);
            }
        }

        // Ajouter la légende après la création des secteurs
        ajouterLegendes();
    }

    /**
     * Ajoute les légendes à droite du camembert.
     */
    private void ajouterLegendes() {
        // Position de départ pour la légende

        double startX = this.largeur * 0.85;

        double startY = this.hauteur * 0.2;
        double squareSize = 20;  // Taille du carré de couleur
        double spacing = 30;     // Espacement vertical entre les entrées
        
        // Créer une entrée de légende pour chaque donnée
        for (int i = 0; i < donnees.size(); i++) {
            // Créer un rectangle coloré pour représenter la catégorie
            Rectangle rect = new Rectangle(startX, startY + i * spacing, squareSize, squareSize);
            String col = (couleurs != null && i < couleurs.length) ? couleurs[i] : "#CCCCCC";
            rect.colorier(col);
            groupe.ajouter(rect);

            // Calculer le pourcentage et créer le texte de la légende
            double pourcentage = (valeurs.get(i) / totalValeurs) * 100;
            String texteLeg = donnees.get(i) + " : " + Math.round(pourcentage) + "%";
            Texte texte = new Texte(texteLeg,
                    new Point(startX + squareSize + 10, startY + i * spacing + 15),
                    12);
            texte.colorier(col);
            groupe.ajouter(texte);
        }
    }

    /**
     * Ajoute une donnée à représenter.
     *
     * @param donnee Libellé de la catégorie à afficher dans la légende
     * @param valeurs Tableau de valeurs numériques (seule la première valeur est utilisée)
     */
    @Override
    public void ajouterDonnees(String donnee, double... valeurs) {
        this.donnees.add(donnee);
        if (valeurs.length > 0) {
            this.valeurs.add(valeurs[0]);
        }
    }

    /**
     * Définit les libellés de la légende (ex : catégories ou occurrences).
     * Ces libellés sont utilisés pour décrire les données dans la légende.
     *
     * @param legende Tableau des libellés à placer dans la légende
     */
    @Override
    public void legender(String... legende) {
        this.legende.clear();
        this.legende.addAll(Arrays.asList(legende));
    }

    /**
     * Configure des options spécifiques pour le diagramme.
     * Cette méthode n'est pas implémentée dans cette version.
     *
     * @param options Tableau de chaînes décrivant les options à appliquer
     */
    @Override
    public void setOptions(String... options) {
        // Non implémenté dans cette version
    }

    /**
     * Renvoie le point central du diagramme.
     * Ce point est calculé comme le centre géométrique de la zone définie
     * par largeur et hauteur.
     *
     * @return Point représentant le centre du diagramme
     */
    @Override
    public Point centre() {
        return new Point(this.largeur / 2, this.hauteur / 2);
    }

    /**
     * Applique une palette de couleurs sur tous les camemberts du diagramme.
     * Chaque secteur recevra une couleur spécifique de la palette.
     *
     * @param couleur Tableau de chaînes hexadécimales représentant les couleurs (ex: "#FF0000")
     */
    @Override
    public void colorier(String... couleur) {
        this.couleurs = couleur;
        for (Camembert c : camemberts) {
            c.colorier(couleurs);
        }
    }

    /**
     * Déplace le diagramme entier vers une nouvelle position.
     * Cette méthode déplace tous les camemberts et le groupe d'éléments.
     *
     * @param x Déplacement sur l'axe X
     * @param y Déplacement sur l'axe Y
     */
    @Override
    public void deplacer(double x, double y) {
        for (Camembert c : camemberts) {
            c.deplacer(x, y);
        }
        groupe.deplacer(x, y);
    }

    /**
     * Renvoie une description textuelle (debug) du diagramme.
     * Cette méthode est utile pour le débogage ou l'inspection du contenu.
     *
     * @param n Niveau d'indentation pour la mise en forme de la sortie
     * @return Chaîne décrivant le contenu et la configuration du diagramme
     */
    @Override
    public String description(int n) {
        StringBuilder result = new StringBuilder();
        // Ajouter l'indentation
        for (int i = 0; i < n; i++) {
            result.append("  ");
        }

        result.append("DiagCamemberts: ").append(this.titre).append("\n");

        // Ajouter la description de chaque camembert
        for (Camembert c : camemberts) {
            result.append(c.description(n + 1)).append("\n");
        }

        return result.toString();
    }

    /**
     * Crée une copie du diagramme.
     *
     * @return Nouvelle instance de DiagCamemberts avec les mêmes données
     */
    @Override
    public IForme dupliquer() {
        DiagCamemberts newCamDiag = new DiagCamemberts(this.titre, this.largeur, this.hauteur);
        // Dupliquer toutes les données
        for (int i = 0; i < this.donnees.size(); i++) {
            newCamDiag.ajouterDonnees(this.donnees.get(i), this.valeurs.get(i));
        }
        // Dupliquer les couleurs si elles existent
        if (this.couleurs != null) {
            newCamDiag.colorier(this.couleurs);
        }
        return newCamDiag;
    }

    /**
     * Génère le code SVG complet représentant le diagramme.
     * Ce code inclut le titre, les secteurs des camemberts et la légende.
     *
     * @return Chaîne contenant le code SVG complet du diagramme
     */
    @Override
    public String enSVG() {
        StringBuilder svg = new StringBuilder();

        // En-tête SVG
        svg.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        svg.append("<svg xmlns=\"http://www.w3.org/2000/svg\" ");
        svg.append("width=\"").append(this.largeur).append("\" ");
        svg.append("height=\"").append(this.hauteur).append("\" ");
        svg.append("viewBox=\"0 0 ").append(this.largeur).append(" ").append(this.hauteur).append("\">\n");

        // Ajouter le titre centré en haut
        svg.append("<text x=\"").append(this.centre().x()).append("\" y=\"").append(30).append("\" ");
        svg.append("font-size=\"20\" text-anchor=\"middle\">").append(this.titre).append("</text>\n");

        // Ajouter le code SVG de chaque camembert
        for (Camembert c : camemberts) {
            svg.append(c.enSVG()).append("\n");
        }

        // Ajouter le code SVG du groupe (légende, etc.)
        svg.append(groupe.enSVG()).append("\n");

        // Fermer le tag SVG
        svg.append("</svg>");

        return svg.toString();
    }

    /**
     * Retourne la hauteur du diagramme en pixels.
     *
     * @return Hauteur du diagramme
     */
    @Override
    public double hauteur() {
        return this.hauteur;
    }

    /**
     * Retourne la largeur du diagramme en pixels.
     *
     * @return Largeur du diagramme
     */
    @Override
    public double largeur() {
        return this.largeur;
    }

    /**
     * Redimensionne le diagramme aux nouvelles dimensions spécifiées.
     * Note: Cette méthode change uniquement les dimensions. Il faut appeler
     * agencer() pour recalculer le placement des éléments graphiques.
     *
     * @param x Nouvelle largeur en pixels
     * @param y Nouvelle hauteur en pixels
     */
    @Override
    public void redimensionner(double x, double y) {
        this.largeur = x;
        this.hauteur = y;
    }

    /**
     * Applique une rotation à tous les camemberts du diagramme.
     * Cette rotation est appliquée autour du centre de chaque camembert.
     *
     * @param angle Angle de rotation en degrés
     */
    @Override
    public void tourner(int angle) {
        for (Camembert c : camemberts) {
            c.tourner(angle);
        }
    }
}