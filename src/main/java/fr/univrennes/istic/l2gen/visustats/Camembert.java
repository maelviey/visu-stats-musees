package fr.univrennes.istic.l2gen.visustats;

import fr.univrennes.istic.l2gen.geometrie.IForme;
import fr.univrennes.istic.l2gen.geometrie.Point;
import fr.univrennes.istic.l2gen.geometrie.Secteur;

import java.util.ArrayList;
import java.util.List;

import fr.univrennes.istic.l2gen.geometrie.Groupe;

public class Camembert implements IForme {
    public double rayon;
    public Point centre;
    public String couleur;
    public double angle;
    public Groupe groupeSecteur;
    private double lastSectorAngle;

    /**
     * @param centre centre du camembert
     * @param rayon  rayon du camembert
     *               Constructeur n1
     */
    public Camembert(Point centre, double rayon) {
        this.centre = centre;
        this.rayon = rayon;
        this.couleur = "white";
        this.angle = 0;
        this.groupeSecteur = new Groupe(); // Initialisation du groupe de secteurs
    }

    /**
     * @param x     coordonée x du centre du camembert
     * @param y     coordonée y du centre du camembert
     * @param rayon rayon du camembert
     *              Constructeur n2
     */
    public Camembert(double x, double y, double rayon) {
        this.centre = new Point(x, y);
        this.rayon = rayon;
        this.couleur = "white";
        this.angle = 0;
        this.groupeSecteur = new Groupe(); // Initialisation du groupe de secteurs
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true; // Si les références sont identiques.
        if (obj == null || getClass() != obj.getClass())
            return false; // Vérifie la nullité ou si les classes diffèrent.
        Camembert that = (Camembert) obj;
        return this.centre.equals(that.centre) && this.rayon == that.rayon;
        // Compare les attributs, comme le centre et le rayon.
    }

    /**
     * @return le centre du camembert
     */
    public Point centre() {
        return this.centre;
    }

    /**
     * @param couleur la couleur à assigner au camembert
     * @return le camembert en question
     */
    public void colorier(String... couleur) {
        this.couleur = couleur[0];
    }

    /**
     * @param x coordonnée x du nouveau centre
     * @param y coordonnée y du nouveau centre
     */
    public void deplacer(double x, double y) {
        this.centre = new Point(x, y);
    }

    /**
     * @return une copie du camembert
     */
    public IForme dupliquer() {
        Camembert newFromage = new Camembert(this.centre, this.rayon);
        return newFromage;
    }

    /**
     * @return la hauteur (diamètre, soit rayon*2) du camembert
     */
    public double hauteur() {
        return this.rayon * 2;
    }

    /**
     * @return la largeur (diamètre, soit rayon*2) du camembert
     */
    public double largeur() {
        return this.rayon * 2;
    }

    /**
     * @param angle l'angle de rotation
     */
    public void tourner(int angle) {
        this.angle = angle;
    }

    /**
     * @param a un coeff de redimension
     * @param b un coeff de redimension (INUTILE!!!!!!!!!car un camembert est un
     *          cercle.)
     */
    public void redimensionner(double a, double b) {
        this.rayon = rayon * a;
    }

    /**
     * @param incr l'indentation (2 caractères blancs pour un d'indentation) avant
     *             la description
     * @return une chaîne de caractères contenant la description du cercle
     */
    public String description(int in) {
        String result = "";
        // Ajout d'espaces pour l'indentation
        for (int i = 0; i <= in; i++) {
            result += "  ";
        }
        result += "Camembert :\n";
        result += "  Centre : " + this.centre.x() + "," + this.centre.y() + ", ";  
        result += "  Rayon : " + this.rayon + ", ";
        result += "  Couleur : " + this.couleur + ", ";
        result += "  Angle : " + this.angle + ", ";
        result += "  Secteurs : " + this.groupeSecteur.size() + "\n"; // Ajout de la taille du groupe de secteurs
        // Ajout de la description du groupe de secteurs
        result += this.groupeSecteur.description(in + 1); // Appel de la méthode description du groupe de secteurs
        return result;
    }

    /**
     * @return une chaîne de caractères SVG permettant d'afficher le camembert
     */
    public String enSVG() {
        return "<circle cx=\"" + this.centre.x() + "\" cy=\"" + this.centre.y() + "\" r=\"" + this.rayon + "\" fill=\""
                + this.couleur + "\" transform=\"rotate(" + this.angle + " " + this.centre.x() + " " + this.centre.y()
                + ")\" />"
                + this.groupeSecteur.enSVG(); // Ajout de la représentation SVG des secteurs
    }

    /**
     * @param couleur la couleur du secteur à ajouter
     * @param a la longueur de l'arc du sectuer à ajouter
     * @return le camembert mis à jour
     */
    public Camembert ajouterSecteur(String couleur, double arc) {
        if(arc <= 0) return this; // Protection contre les arcs invalides
        
        Secteur sect;
        if(this.groupeSecteur.isEmpty()) {
            sect = new Secteur(this.centre, this.rayon, 0, arc);
        } else {
            sect = new Secteur(centre, this.rayon, lastSectorAngle, arc);
        }
        
        sect.colorier(couleur != null ? couleur : "#CCCCCC"); // Couleur par défaut explicite
        this.groupeSecteur.ajouter(sect);
        lastSectorAngle += arc;
        
        return this;
    }

    /**
     * @return la liste des secteurs du camembert
     */
    public List<Secteur> getSecteurs() {
        List<Secteur> secteurs = new ArrayList<>();
        for(IForme forme : groupeSecteur.getFormes()) {
            if(forme instanceof Secteur) {
                secteurs.add((Secteur) forme);
            }
        }
        return secteurs;
    }

}
