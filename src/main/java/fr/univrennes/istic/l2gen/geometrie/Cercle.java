package fr.univrennes.istic.l2gen.geometrie;

public class Cercle implements IForme {
    public Point center;
    public double radius; // L'on considère les coordonées du centre du cercle sous forme de Point ainsi
                          // qu'un rayon sous forme de double.
    public String color;
    public int angle;

    /**
     * @param x      la coordonée x du point centre du cercle à créer
     * @param y      la coordonée x du point centre du cercle à créer
     * @param radius la longeur du rayon du cercle à créer
     *               Constructeur de cercle n°1
     */
    public Cercle(double x, double y, double radius) {
        this.center = new Point(x, y);
        this.radius = radius;
        this.color = "white";
        this.angle = 0;
    }

    /**
     * @param p      un point, centre du cercle à créer
     * @param radius la longeur du rayon du cercle à créer
     *               Constructeur de cercle n°2
     */
    public Cercle(Point p, double radius) {
        this.center = p;
        this.radius = radius;
        this.color = "white";
    }

    @Override
    public Point centre() {
        return this.center;
    }

    @Override
    public double hauteur() {
        return this.radius * 2;
    }

    @Override
    public double largeur() {
        return this.radius * 2;
    }

    @Override
    public String description(int incr) {
        String result = "";
        // On ajoute des espaces pour l'indentation
        for (int i = 0; i <= incr; i++) {
            result += "  ";
        }
        // On ajoute la description du cercle
        result += "Cercle centre=" + this.center.x() + "," + this.center.y() + " r=" + this.radius;
        return result;
    }

    @Override
    public void deplacer(double x, double y) {
        this.center = new Point(x, y);
    }

    @Override
    public void redimensionner(double a, double b) {
        double newRadius = this.radius * a;
        if (newRadius > 0) { // Vérification pour éviter un rayon négatif ou nul
            this.radius = newRadius;
        }
    }

    @Override
    public IForme dupliquer() {
        Cercle newCercle = new Cercle(this.center, this.radius);
        newCercle.tourner(this.angle);
        return newCercle;
    }

    @Override
    public String enSVG() {
        // On crée la chaîne de caractères SVG
        String svg = "<circle cx=\"" + this.center.x() + "\" cy=\"" + this.center.y() + "\" r=\"" + this.radius + "\"\n"
                + //
                "fill=\"" + this.color + "\" stroke=\"black\"";
        if (angle != 0) { // Si l'angle est différent de 0, on ajoute la rotation
            svg += "\ntransform=\"rotate(" + this.angle + ")\"/>";
        } else { // Sinon, on ne l'ajoute pas
            svg += "/>";
        }
        return svg;
    }

    @Override
    public void colorier(String... couleur) {
        this.color = couleur[0];
    }

    @Override
    public void tourner(int angle) {
        this.angle = angle;
    }

    @Override
    public void aligner(Alignement alignement, double cible) {

        switch (alignement) {
            case HAUT:
                this.deplacer(this.centre().x(), cible - this.radius);
                break;
            case BAS:
                this.deplacer(this.centre().x(), cible + this.radius);
                break;
            case DROITE:
                this.deplacer(cible + this.radius, this.centre().y());
                break;
            case GAUCHE:
                this.deplacer(cible - this.radius, this.centre().y());
                break;
        }
    }
}
