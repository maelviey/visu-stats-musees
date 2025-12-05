package fr.univrennes.istic.l2gen.geometrie;

/**
 * Représente un rectangle dans un plan avec un centre, une largeur, une hauteur
 * et une couleur.
 * Fournit des méthodes pour déplacer, dupliquer, redimensionner, et générer des
 * informations sur le rectangle.
 * Le rectangle est défini par son centre, sa hauteur et sa largeur, et ses
 * quatre coins sont calculés en conséquence.
 */
public class Rectangle implements IForme {

    private Point centre;
    private double hauteur;
    private double largeur;
    private Point supGauche;
    private Point supDroite;
    private Point infGauche;
    private Point infDroite;
    public String color;
    private int rotation;

    /**
     * Constructeur d'un rectangle avec des coordonnées pour le centre, la largeur,
     * la hauteur et la couleur.
     * La couleur par défaut est "black" (noir).
     * 
     * @param x       La coordonnée x du centre du rectangle.
     * @param y       La coordonnée y du centre du rectangle.
     * @param largeur La largeur du rectangle.
     * @param hauteur La hauteur du rectangle.
     */
    public Rectangle(double x, double y, double largeur, double hauteur) {
        this.centre = new Point(x, y);
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.color = "black";
        createCorners(); // Calcule les positions des 4 coins
        this.rotation = 0; // Initialise la rotation à 0 degré
    }

    /**
     * Modifie la couleur du rectangle.
     * 
     * @param color La nouvelle couleur à appliquer au rectangle.
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Constructeur d'un rectangle avec un centre spécifié, la largeur et la
     * hauteur.
     * La couleur par défaut est "black" (noir).
     * 
     * @param centre  Le centre du rectangle sous forme de point.
     * @param largeur La largeur du rectangle.
     * @param hauteur La hauteur du rectangle.
     */
    public Rectangle(Point centre, double largeur, double hauteur) {
        this.centre = centre;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.color = "black";
        createCorners(); // Calcule les positions des 4 coins
        this.rotation = 0; // Initialise la rotation à 0 degré
    }

    /**
     * Crée les 4 coins du rectangle en fonction de son point central, largeur et
     * hauteur.
     */
    public void createCorners() {
        this.supGauche = new Point(this.centre.x() - (this.largeur / 2), this.centre.y() + (this.hauteur / 2));
        this.supDroite = new Point(this.centre.x() + (this.largeur / 2), this.centre.y() + (this.hauteur / 2));
        this.infGauche = new Point(this.centre.x() - (this.largeur / 2), this.centre.y() - (this.hauteur / 2));
        this.infDroite = new Point(this.centre.x() + (this.largeur / 2), this.centre.y() - (this.hauteur / 2));
    }

    @Override
    public Point centre() {
        return this.centre;
    }

    @Override
    public void deplacer(double dx, double dy) {
        this.centre = new Point(this.centre.x() + dx, this.centre.y() + dy);
        createCorners(); // Recalcule les coins après déplacement
    }

    @Override
    public IForme dupliquer() {
        Rectangle ret = new Rectangle(this.centre(), this.largeur, this.hauteur);
        ret.setColor(this.color);
        return ret;
    }

    @Override
    public String description(int i) {
        String ret = "";
        for (int x = 0; x < i; x++) {
            ret += "  "; // Ajoute deux espaces pour chaque niveau d'indentation
        }
        ret += "Rectangle Centre=" + this.centre.x() + "," + this.centre.y() + " L=" + this.largeur + " H="
                + this.hauteur + " couleur=" + this.color;
        return ret;
    }

    @Override
    public double hauteur() {
        return this.hauteur;
    }

    @Override
    public double largeur() {
        return this.largeur;
    }

    @Override
    public void redimensionner(double px, double py) {
        this.largeur = this.largeur * px;
        this.hauteur = this.hauteur * py;
        createCorners(); // Recalcule les coins après redimensionnement
    }

    @Override
    public void tourner(int angle) {
        this.rotation = angle;
    }

    @Override
    public String enSVG() {
        String transform = "";
        if (this.rotation != 0) {
            // Apply rotation if needed
            transform = "transform=\"rotate(" + this.rotation + "," + this.centre.x() + "," + this.centre.y() + ")\" ";
        }
    
        // Calculate the correct x and y coordinates (top-left corner of rectangle)
        double x = this.centre.x() - (this.largeur / 2);
        double y = this.centre.y() - (this.hauteur / 2);
    
        return "<rect x=\"" + x + "\" y=\"" + y + "\" width=\"" + this.largeur +
           "\" height=\"" + this.hauteur + "\" fill=\"" + this.color + "\" " + transform + "stroke=\"black\"/>";
    }

    @Override
    public void colorier(String... couleur) {
        this.color = couleur[0];
    }

    public static void main(String[] args) throws Exception {
        Rectangle recTest = new Rectangle(0, 0, 10, 10);
        System.out.println(recTest.description(0));
        recTest.deplacer(10, 10);
        System.out.println(recTest.description(0));
        System.out.println(recTest.enSVG());
        recTest.redimensionner(2, 2);
        System.out.println(recTest.description(0));
        System.out.println(recTest.enSVG());
        recTest.tourner(90);
        System.out.println(recTest.description(0));
        System.out.println(recTest.enSVG());
        recTest.aligner(Alignement.HAUT, 10);
        System.out.println(recTest.description(0));
        System.out.println(recTest.enSVG());
    }
}