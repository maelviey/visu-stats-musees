package fr.univrennes.istic.l2gen.geometrie;

import java.util.ArrayList;

public class Ligne implements IForme {

    protected ArrayList<Point> ligne;
    protected String color;
    protected int angle;

    /** Constructeur de la classe Ligne avec des coordonnées.
     * 
     * @param coordonnes Les coordonnées des sommets de la ligne.
     */
    public Ligne(double... coordonnes) {
        // Initialisation de la liste des points de la ligne
        this.ligne = new ArrayList<Point>();
        double temp = 0;
        boolean b = false;

        // Parcours des coordonnées fournies en paramètre
        for (double a : coordonnes) {
            if (b == false) {
                // Si b est faux, on stocke la coordonnée temporairement
                temp = a;
                b = true;
            } else {
                // Si b est vrai, on crée un nouveau point avec les coordonnées stockées
                ligne.add(new Point(temp, a));
                b = false;
            }
        }

        // Initialisation de la couleur de la ligne à "black"
        this.color = "black";
        // Initialisation de l'angle de rotation de la ligne à 0
        this.angle = 0;
    }

    /** Constructeur de la classe Ligne avec des points.
     * 
     * @param points Les points de la ligne.
     */
    public Ligne(Point ... points) {
        // Initialisation de la liste des points de la ligne
        this.ligne = new ArrayList<Point>();
        for (Point p : points) { // Ajout des points fournis en paramètre à la liste de la ligne
            ligne.add(p);
        }
        this.color = "black";
        this.angle = 0;
    }

    /** Constructeur de la classe Ligne vide.
     *       
     */
    public Ligne() {
        this.ligne = new ArrayList<Point>();
        this.color = "black";
        this.angle = 0;
    }

    @Override
    public Point centre() {
        // Test si la ligne est vide
        if (ligne.isEmpty()) {
            return new Point(0, 0);
        }

        // Calcul du centre de la ligne en prenant la moyenne des coordonnées x et y des
        // points
        double sumX = 0, sumY = 0;
        for (Point p : ligne) {
            sumX += p.x();
            sumY += p.y();
        }

        return new Point(sumX / ligne.size(), sumY / ligne.size());
    }

    @Override
    public String description(int n) {
        // Création de l'indentation
        String description = "";
        for (int i = 0; i < n; i++) {
            description += "  ";
        }

        // Ajout de l'indentation au début suivi de "Ligne "
        description = description + "Ligne ";

        // Ajout de tous les points
        for (int j = 0; j < ligne.size(); j++) {
            Point coord = ligne.get(j);
            description += coord.x() + "," + coord.y();

            // Ajout d'un espace entre les points sauf pour le dernier
            if (j < ligne.size() - 1) {
                description += " ";
            }
        }

        return description;
    }

    @Override
    public double hauteur() {
        // Initialisation tempmax et tempmin avec la première coordonnée y de la ligne
        Double tempmax = ligne.get(0).y();
        Double tempmin = ligne.get(0).y();

        // Parcours de la ligne pour trouver les coordonnées y max et min
        for (Point p : ligne) {
            tempmax = Math.max(tempmax, p.y());
            tempmin = Math.min(tempmin, p.y());
        }
        return Math.abs(tempmax - tempmin);
    }

    @Override
    public double largeur() {
        // Initialisation tempmax et tempmin avec la première coordonnée y de la ligne
        Double tempmax = ligne.get(0).x();
        Double tempmin = ligne.get(0).x();

        // Parcours de la ligne pour trouver les coordonnées y max et min
        for (Point p : ligne) {
            tempmax = Math.max(tempmax, p.x());
            tempmin = Math.min(tempmin, p.x());
        }
        return tempmax - tempmin;
    }

    /** Ajoute un sommet à la ligne.
     * 
     * @param p Le point à ajouter comme sommet.
     */
    public void ajouterSommet(Point p) {
        this.ligne.add(p);
    }

    /** Ajoute un sommet à la ligne.
     * 
     * @param x La coordonnée x du sommet.
     * @param y La coordonnée y du sommet.
     */
    public void ajouterSommet(double x, double y) {
        this.ligne.add(new Point(x, y));
    }

    /** Retourne la liste des sommets de la ligne.
     * 
     * @return La liste des sommets de la ligne.
     */
    public ArrayList<Point> getSommets() {
        return this.ligne;
    }

    @Override
    public void deplacer(double x, double y) {
        // Parcours de la liste des points de la ligne et mise à jour de leurs
        // coordonnées en ajoutant les valeurs de déplacement x et y
        for (int i = 0; i < ligne.size(); i++) {
            Point p = ligne.get(i);
            ligne.set(i, new Point(p.x() + x, p.y() + y));
        }
    }

    @Override
    public IForme dupliquer() {
        // Création d'une nouvelle Ligne
        Ligne l = new Ligne();

        // Copie des points de la ligne actuelle dans la nouvelle ligne
        for (Point p : ligne) {
            l.ajouterSommet(p);
        }
        return l;
    }

    @Override
    public void redimensionner(double x, double y) {
        // Création d'un point centre qui représente le centre de la ligne
        Point centre = centre();

        // Parcours de la ligne pour redimensionner chaque point en multipliant les
        // coordonnées par les facteurs x et y
        for (int i = 0; i < ligne.size(); i++) {
            Point p = ligne.get(i);
            double newX = centre.x() + (p.x() - centre.x()) * x;
            double newY = centre.y() + (p.y() - centre.y()) * y;
            ligne.set(i, new Point(newX, newY));
        }
    }

    @Override
    public String enSVG() {
        // Vérifie si la ligne est vide avant de générer le SVG
        if (ligne.isEmpty()) {
            return "";
        }
        // Crée une chaîne de caractères pour représenter la ligne en SVG. La chaîne
        // commence par la balise <polyline> avec les points de la ligne
        StringBuilder svg = new StringBuilder("<polyline points=\"");
        // Parcours de la liste des points de la ligne et ajout de leurs coordonnées à
        // la chaîne SVG
        for (int i = 0; i < ligne.size(); i++) {
            Point coord = ligne.get(i);
            svg.append(coord.x()).append(",").append(coord.y());
            if (i < ligne.size() - 1) {
                svg.append(" "); // Ajouter un espace entre les points
            }
        }
        // Ajout de la fermeture de la balise <polyline> et des attributs de style
        svg.append("\" fill=\"black\" stroke=\"").append(this.color);
        svg.append("\" transform=\"rotate(" + angle + ")").append("\" />");
        return svg.toString();
    }

    @Override
    public void colorier(String... couleur) {
        this.color = couleur[0];
    }

    @Override
    public void tourner(int angle) {
        this.angle = angle;
    }

    /** Tourne la ligne d'un certain angle autour de son centre.
     * 
     * @param angle L'angle de rotation en degrés.
     */
    public void tournerPlusPlus(int angle) {
        Point pivotLigne = centre();
        double angleRad = Math.toRadians(angle); // Conversion de l'angle en radians
        for (int i = 0; i < ligne.size(); i++) {
            Point p = ligne.get(i);
            // Calcul de la position relative du point par rapport au pivot
            double dx = p.x() - pivotLigne.x();
            double dy = p.y() - pivotLigne.y();
            // Application des formules de rotation
            double newX = pivotLigne.x() + (dx * Math.cos(angleRad) - dy * Math.sin(angleRad));
            double newY = pivotLigne.y() + (dx * Math.sin(angleRad) + dy * Math.cos(angleRad));
            // Mise à jour du point dans la liste
            ligne.set(i, new Point(Math.round(newX), Math.round(newY)));
        }
    }

    @Override
    public void aligner(Alignement alignement, double cible) {
        if (ligne.isEmpty())
            return;

        switch (alignement) {
            case HAUT:
                // Trouver le point le plus haut (ayant la plus petite valeur de y)
                double minY = ligne.stream().mapToDouble(p -> p.y).min().orElse(cible);
                double decalageHaut = cible - minY;
                for (Point point : ligne) {
                    point.y += decalageHaut;
                }
                break;

            case BAS:
                // Trouver le point le plus bas (ayant la plus grande valeur de y)
                double maxY = ligne.stream().mapToDouble(p -> p.y).max().orElse(cible);
                double decalageBas = cible - maxY;
                for (Point point : ligne) {
                    point.y += decalageBas;
                }
                break;

            case GAUCHE:
                // Trouver le point le plus à gauche (ayant la plus petite valeur de x)
                double minX = ligne.stream().mapToDouble(p -> p.x).min().orElse(cible);
                double decalageGauche = cible - minX;
                for (Point point : ligne) {
                    point.x += decalageGauche;
                }
                break;

            case DROITE:
                // Trouver le point le plus à droite (ayant la plus grande valeur de x)
                double maxX = ligne.stream().mapToDouble(p -> p.x).max().orElse(cible);
                double decalageDroite = cible - maxX;
                for (Point point : ligne) {
                    point.x += decalageDroite;
                }
                break;
        }
    }

    // Exemples d'utilisation de la classe Ligne
    public static void main(String[] args) {
        System.out.println("=== Exemples d'utilisation de la classe Ligne ===");

        // Création d'une ligne initiale
        Ligne l = new Ligne(0, 0, 1, 1);
        System.out.println("Ligne initiale : " + l.description(10));

        // Ajout d'un sommet
        // l.ajouterSommet(6, 6);
        System.out.println("Après ajout d'un sommet : " + l.description(3));

        // Affichage des propriétés de la ligne
        System.out.println("Centre de la ligne : (" + l.centre().x() + ", " + l.centre().y() + ")");
        System.out.println("Hauteur de la ligne : " + l.hauteur());
        System.out.println("Largeur de la ligne : " + l.largeur());

        // Déplacement de la ligne
        // l.deplacer(2, -1);
        System.out.println("Après déplacement : " + l.description(3));

        // Description actuelle de la ligne
        System.out.println("Description de la ligne : " + l.description(3));

        // Redimensionnement de la ligne
        // l.redimensionner(1.5, 0.5);
        System.out.println("Après redimensionnement : " + l.description(3));

        // Duplication de la ligne
        Ligne copie = (Ligne) l.dupliquer();
        System.out.println("Copie de la ligne : " + copie.description(3));

        System.out.println();

        // Création d'une forme utilisant l'interface IForme
        // IForme f = new Ligne("black", 128, 128, 128, 256, 256, 128, 256, 256);
        System.out.println("Représentation SVG de la ligne :");
        // System.out.println(f.enSVG());

        // Déplacement de la forme
        // f.deplacer(3, 3);
        System.out.println("Représentation SVG après déplacement :");
        // System.out.println(f.enSVG());

        // Rotation de la ligne
        System.out.println("Avant rotation : " + l.description(2));
        l.tourner(90); // Appliquer une rotation de 45 degrés autour du centre de la ligne
        System.out.println("Après rotation de 45° : " + l.description(2));
    }
}
