package fr.univrennes.istic.l2gen.visustats;

import java.util.ArrayList;

import fr.univrennes.istic.l2gen.application.ColorManager;
import fr.univrennes.istic.l2gen.geometrie.Groupe;
import fr.univrennes.istic.l2gen.geometrie.IForme;
import fr.univrennes.istic.l2gen.geometrie.Point;
import fr.univrennes.istic.l2gen.geometrie.Rectangle;
import fr.univrennes.istic.l2gen.geometrie.Texte;

public class DiagBarres implements IDataVisuliseur {
    public ArrayList<Faisceau> faisceau;
    private String titre;
    private double largeur = 800.0;
    private double hauteur = 600.0;
    private ArrayList<String> donnees; 
    private ArrayList<ArrayList<Double>> valeurs;
    private String[] couleurs;
    private ArrayList<String> legende;
    private Groupe groupe;
    private double maxval = 0;
    private int rotation;

    /**
     * Construit un diagramme en barres vide.
     *
     * @param titre   Titre du graphique
     * @param largeur Largeur du SVG en pixels
     * @param hauteur Hauteur du SVG en pixels
     */
    public DiagBarres(String titre, double largeur, double hauteur) {
        faisceau = new ArrayList<>();
        this.titre = titre;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.donnees = new ArrayList<>();
        this.valeurs = new ArrayList<>();
        this.legende = new ArrayList<>();
        groupe = new Groupe();
    }

    public String getTitre() {
        return titre;
    }

    public double getLargeur() {
        return largeur;
    }

    public double getHauteur() {
        return hauteur;
    }

    public ArrayList<String> getDonnees() {
        return donnees;
    }

    public double getMaxval() {
        return maxval;
    }

    /**
     * Calcule positions, échelle et dimensions de chaque barre
     * puis leur applique couleur et titre.
     */
    @Override
    public void agencer() {


        int i=0;
        double j = 0;
        int n = faisceau.size();
         if (couleurs == null || couleurs.length < n) {
            couleurs = ColorManager.generateHuePalette(n);
        }
         for(Faisceau f : faisceau){
            f.agencer(0+j, maxval*donnees.size()+50, this.largeur/(faisceau.size()*valeurs.get(0).size()), 1, true);
            f.setTitre(donnees.get(i));
            f.colorier(couleurs[i]);
            i++;
            j=+i*(this.largeur/faisceau.size());
            
            

    }
    }
    /**
     * Ajoute une ou plusieurs valeurs pour une barre.
     * Seule la première valeur est utilisée pour la taille.
     *
     * @param donnes  Libellé de la barre
     * @param valeurs Valeurs numériques
     */
    @Override
    public void ajouterDonnees(String donnes, double... valeurs) {
        double temp = 0;
        for (double v : valeurs) {
            if (v > temp) {
                temp = v; // Trouve la valeur maximale
            }
        }
        maxval = temp; // Met à jour la valeur maximale globale
        this.donnees.add(donnes); // Ajoute le libellé
        ArrayList<Double> val = new ArrayList<>();
        for (double v : valeurs) {
            val.add(v); // Ajoute les valeurs
        }
        faisceau.add(new Faisceau(valeurs)); // Crée un nouveau faisceau (barre)
        this.valeurs.add(val); // Ajoute les valeurs à la liste globale
    }

    /**
     * Affiche la légende en bas du graphique, carrés colorés + textes.
     *
     * @param legende Labels à afficher sous forme de légende
     */
    @Override
    public void legender(String... legende) {
        this.legende.clear(); // Réinitialise la légende
        for (String l : legende)
            this.legende.add(l); // Ajoute chaque élément de la légende

        // Position de départ pour la légende
        double startX = 20;
        double startY = this.hauteur - 100;
        double squareSize = 15; // Taille des carrés colorés
        double spacing = 25; // Espacement entre les éléments

        // Ajoute chaque élément de la légende au groupe
        for (int i = 0; i < this.legende.size(); i++) {
            String l = this.legende.get(i);
            String col = (i < couleurs.length ? couleurs[i] : "#000000"); // Couleur associée

            // Ajoute un carré coloré
            Rectangle rect = new Rectangle(startX, startY + i * spacing, squareSize, squareSize);
            rect.colorier(col);
            groupe.ajouter(rect);

            // Ajoute le texte correspondant
            Texte texte = new Texte(
                    l,
                    new Point(startX + squareSize + 10, startY + i * spacing + squareSize / 2),
                    12);
            texte.colorier(col);
            groupe.ajouter(texte);
        }
    }

    /**
     * Définit les options du graphique.
     *
     * @param options Options à appliquer
     */
    @Override
    public void setOptions(String... options) {
    }

    /**
     * Retourne le centre du graphique.
     *
     * @return Point représentant le centre
     */
    @Override
    public Point centre() {
        double x = this.largeur / 2;
        double y = this.hauteur / 2;
        return new Point(x, y);
    }

    /**
     * Assigne les couleurs aux faisceaux (barres).
     *
     * @param couleur Tableau de couleurs hexadécimales
     */
    @Override
    public void colorier(String... couleur) {
        this.couleurs = couleur;
        
    }

    /**
     * Déplace le graphique de x et y.
     *
     * @param x Déplacement en x
     * @param y Déplacement en y
     */
    @Override
    public void deplacer(double x, double y) {
        for (Faisceau c : this.faisceau) {
            c.deplacer(x, y);
        }
        this.groupe.deplacer(x, y);
    }

    /**
     * Description textuelle du diagramme en barres.
     *
     * @param n Indentation (nombre de groupes de 2 espaces)
     * @return Chaîne descriptive
     */
    @Override
    public String description(int n) {
        StringBuilder sb = new StringBuilder();
        sb.append("Titre: ").append(this.titre).append("\n");
        sb.append("Largeur: ").append(this.largeur).append("\n");
        sb.append("Hauteur: ").append(this.hauteur).append("\n");
        sb.append("Données: ").append(this.donnees).append("\n");
        sb.append("Valeurs: ").append(this.valeurs).append("\n");
        sb.append("Couleurs: ").append(this.couleurs).append("\n");
        sb.append("Légende: ").append(this.legende).append("\n");

        return sb.toString();
    }

    /**
     * Duplique le diagramme en barres.
     *
     * @return Nouvelle instance de DiagBarres
     */
    @Override
    public IForme dupliquer() {
        DiagBarres ret = new DiagBarres(this.titre, this.largeur, this.hauteur);
        String couleurs;
        for (String x : this.couleurs) {
            ret.colorier(x);
        }
        return ret;
    }

    /**
     * Génère le SVG complet incluant les faisceaux et la légende.
     *
     * @return Chaîne SVG
     */
    @Override
    public String enSVG() {
        StringBuilder svg = new StringBuilder();

        svg.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        svg.append("<svg xmlns=\"http://www.w3.org/2000/svg\" ")
                .append("width=\"").append(this.largeur).append("\" ")
                .append("height=\"").append(this.hauteur).append("\" ")
                .append("viewBox=\"0 0 ").append(this.largeur).append(" ").append(this.hauteur).append("\">\n");

        svg.append("<text x=\"50%\" y=\"30\" font-size=\"20\" text-anchor=\"middle\">")
                .append(this.titre)
                .append("</text>\n");

        for (Faisceau f : faisceau) {
            svg.append(f.enSVG()).append("\n");
        }

        svg.append(groupe.enSVG()).append("\n");

        svg.append("</svg>");
        return svg.toString();
    }

    /**
     * Retourne la rotation actuelle du graphique.
     *
     * @return Angle de rotation
     */
    @Override
    public double hauteur() {
        return this.hauteur;
    }

    /**
     * Retourne la largeur actuelle du graphique.
     *
     * @return Largeur du graphique
     */
    @Override
    public double largeur() {
        return this.largeur;
    }

    /**
     * Redimensionne le graphique.
     *
     * @param x Facteur de redimensionnement en x
     * @param y Facteur de redimensionnement en y
     */
    @Override
    public void redimensionner(double x, double y) {
        this.largeur += x;
        this.hauteur += y;
        this.agencer();

    }

    /**
     * Applique une rotation au graphique.
     *
     * @param angle Angle de rotation
     */
    @Override
    public void tourner(int angle) {
        if (angle <= 360 && angle >= 0) {
            this.rotation = angle; // Définit l'angle de rotation
            System.out.println("Angle spécifié appliqué");
        } else {
            System.out.println("Angle spécifié incorrect");
        }
    }

}
