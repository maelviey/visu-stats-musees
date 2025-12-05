package fr.univrennes.istic.l2gen.visustats;

import java.util.ArrayList;

import fr.univrennes.istic.l2gen.application.ColorManager;
import fr.univrennes.istic.l2gen.geometrie.Groupe;
import fr.univrennes.istic.l2gen.geometrie.IForme;
import fr.univrennes.istic.l2gen.geometrie.Point;
import fr.univrennes.istic.l2gen.geometrie.Rectangle;
import fr.univrennes.istic.l2gen.geometrie.Texte;

public class DiagColonnes implements IDataVisuliseur {
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
    private double echelle = 1;
    private int rotation;
    private double MARGE_GAUCHE = 60;
    private double MARGE_BASSE = 50;

    /**
     * Construit un diagramme en colonnes vide.
     *
     * @param titre   Titre du graphique
     * @param largeur Largeur du SVG en pixels
     * @param hauteur Hauteur du SVG en pixels
     */
    public DiagColonnes(String titre, double largeur, double hauteur) {
        faisceau = new ArrayList<>();
        this.titre = titre;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.donnees = new ArrayList<>();
        this.valeurs = new ArrayList<>();
        this.legende = new ArrayList<>();
        groupe = new Groupe();
    }

    /**
     * Calcule l’échelle, génère l’axe des ordonnées avec graduations,
     * et place chaque colonne à la bonne position.
     */
    @Override
    public void agencer() {
        int n = faisceau.size();
        if (couleurs == null || couleurs.length < n) {
            couleurs = ColorManager.generateHuePalette(n);
        }

        int i = 0;
        double j = 0;
        int space = 0;
        for (Faisceau f : faisceau) {
            f.agencer(
                    j,
                    maxval + 50,
                    this.largeur / (n * valeurs.get(0).size())
                            - (this.largeur / n) / (donnees.size() * valeurs.size()),
                    echelle,
                    false);
            f.setTitre(donnees.get(i));
            f.colorier(couleurs[i]);
            i++;
            space = 50 * i;
            j = i * (this.largeur / n) + space;
        }
        largeur = this.largeur + space;
    }

    /**
     * Ajoute une ou plusieurs valeurs pour une même catégorie en abscisse.
     *
     * @param donnes Libellé de la colonne
     * @param vals   Valeurs numériques
     */
    @Override
    public void ajouterDonnees(String donnes, double... valeurs) {
        double temp = 0;
        for (double v : valeurs) {
            if (v > temp) {
                temp = v;
            }
        }
        maxval = temp;
        this.donnees.add(donnes);
        ArrayList<Double> val = new ArrayList<>();
        for (double v : valeurs) {
            val.add(v);
        }
        faisceau.add(new Faisceau(valeurs));
        this.valeurs.add(val);
    }

    /**
     * Ajoute une légende à droite du graphique.
     *
     * @param legende Libellé de la légende
     */
    @Override
    public void legender(String... legende) {
        this.legende.clear();
        for (String l : legende)
            this.legende.add(l);

        double startX = 20;
        double startY = this.hauteur - 350;
        double squareSize = 15;
        double spacing = 25;

        for (int i = 0; i < this.legende.size(); i++) {
            String l = this.legende.get(i);
            String col = (i < couleurs.length ? couleurs[i] : "#000000");

            Rectangle rect = new Rectangle(startX, startY + i * spacing, squareSize, squareSize);
            rect.colorier(col);
            groupe.ajouter(rect);

            Texte texte = new Texte(
                    l,
                    new Point(startX + squareSize + 20, startY + i * spacing + squareSize / 2),
                    12);
            texte.colorier(col);
            groupe.ajouter(texte);
        }
    }

    @Override
    public void setOptions(String... options) {
    }

    /**
     * Définit les options du graphique.
     *
     * @param options Options à appliquer
     */
    @Override
    public Point centre() {
        double x = this.largeur / 2;
        double y = this.hauteur / 2;
        return new Point(x, y);
    }

    /**
     * Définit la couleur de chaque colonne.
     *
     * @param couleur Un ou plusieurs strings représentant une couleur
     */
    @Override
    public void colorier(String... couleur) {
        this.couleurs = couleur;
    }

    /**
     * @param x coordonnée x du nouveau centre
     * @param y coordonnée y du nouveau centre
     */
    @Override
    public void deplacer(double x, double y) {
        for (Faisceau c : this.faisceau) {
            c.deplacer(x, y);
        }
        this.groupe.deplacer(x, y);

    }

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
     * @return Nouvelle instance de DiagColonnes
     */
    @Override
    public IForme dupliquer() {
        DiagColonnes ret = new DiagColonnes(this.titre, this.largeur, this.hauteur);
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
     * @return la hauteur du diagramme
     */
    @Override
    public double hauteur() {
        return this.hauteur;
    }

    /**
     * @return la largeur du diagramme
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
        this.largeur = x;
        this.hauteur = y;

    }

    /**
     * Applique une rotation au graphique.
     *
     * @param angle Angle de rotation
     */
    @Override
    public void tourner(int angle) {
        if (angle <= 360 && angle >= 0) {
            this.rotation = angle;
            System.out.println("Angle spécifié appliqué");
        } else {
            System.out.println("Angle spécifié incorrect");
        }
    }

}
