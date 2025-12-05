package fr.univrennes.istic.l2gen.geometrie;

public class Triangle implements IForme {
    double ax; // coordonée x du point a du triangle
    double ay; // ... y du point a
    double bx; // etc
    double by;
    double cx;
    double cy;
    private Point a;
    private Point b;
    private Point c;
    public String color;
    private int angle;

    /**
     * @param a1 coordonée x du point a
     * @param a2 coordonée y du point a
     * @param b1 coordonée x du point b
     * @param b2 coordonée y du point b
     * @param c1 coordonée x du point c
     * @param c2 coordonée y du point y
     *           Constructeur de triangle n°1
     */
    public Triangle(double a1, double a2, double b1, double b2, double c1, double c2) {
        this.ax = a1;
        this.ay = a2;
        this.bx = b1;
        this.by = b2;
        this.cx = c1;
        this.cy = c2;
        this.a = new Point(a1, a2);
        this.b = new Point(b1, b2);
        this.c = new Point(c1, c2);
        this.color = "black";
        this.angle = 0;
    }

    /**
     * @param a     Point a du triangle
     * @param b     Point b du triangle
     * @param c     Point c du triangle
     * @param color la couleur
     *              Constructeur de triangle n°2
     */
    public Triangle(Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.ax = a.x();
        this.ay = a.y();
        this.bx = b.x();
        this.by = b.y();
        this.cx = c.x();
        this.cy = c.y();
        this.color = "black";
        this.angle = 0;
    }

    /**
     * @param color la couleur
     */
    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public Point centre() {
        return new Point((ax + bx + cx) / 3, (ay + by + cy) / 3);
    }

    @Override
    public String description(int indentation) {
        String result = "";
        for (int i = 0; i <= indentation; i++) { //Ajout de l'indentation
            result += "  ";
        }
        return result += "Triangle " + ax + ", " + ay + result + bx + ", " + by + result + cx + ", " + cy; //Ajout de la description
    }

    @Override
    public double hauteur() {
        double minY = Math.min(a.y, Math.min(b.y, c.y)); // on prend le plus petit y
        double maxY = Math.max(a.y, Math.max(b.y, c.y)); // on prend le plus grand y
        // on soustrait le plus petit y du plus grand y pour avoir la hauteur
        return maxY - minY;
    }

    @Override
    public double largeur() {
        double minX = Math.min(a.x, Math.min(b.x, c.x)); // on prend le plus petit x
        double maxX = Math.max(a.x, Math.max(b.x, c.x)); // on prend le plus grand x
        // on soustrait le plus petit x du plus grand x pour avoir la largeur
        return maxX - minX;
    }

    @Override
    public void deplacer(double x, double y) {
        // on déplace le triangle en ajoutant les coordonées passées en paramètre
        this.ax += x;
        this.ay += y;
        this.bx += x;
        this.by += y;
        this.cx += x;
        this.cy += y;
    }

    @Override
    public IForme dupliquer() {
        IForme newT = new Triangle(new Point(ax, ay), new Point(bx, by), new Point(cx, cy));
        newT.tourner(this.angle);
        return newT;
    }

    @Override
    public String enSVG() {
        String svg = "<polygon points=\"" + this.ax + " " + this.ay + " " + this.bx + " " + this.by + " " + this.cx
                + " " + this.cy + "\"\n" + //
                "fill=\"" + this.color + "\" stroke=\"black\"";
        if (angle != 0) { //Si l'angle est différent de 0, on applique la rotation
            svg += "\ntransform=\"rotate(" + this.angle + ")\"/>";
        } else { //Sinon, on ajoute la balise de fermeture
            svg += "/>";
        }
        return svg;
    }

    @Override
    public void redimensionner(double a, double b) {
        // on multiplie les coordonées par les coefficients passés en paramètre
        this.ax *= a; 
        this.ay *= b;
        this.bx *= a;
        this.by *= b;       
        this.cx *= a;
        this.cy *= b;
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
        double delta;

        switch (alignement) {
            case HAUT:
                // Calcule la différence entre la cible et le point le plus haut du rectangle
                delta = cible - Math.max(ay, Math.max(by, cy));

                // Déplace le rectangle verticalement pour l'aligner avec la cible
                this.deplacer(0, delta);
                break;

            case BAS:
                // Calcule la différence entre la cible et le point le plus bas du rectangle
                delta = cible - Math.min(ay, Math.min(by, cy));
                this.deplacer(0, delta);
                break;

            case DROITE:
                // Calcule la différence entre la cible et le point le plus à droite du rectangle
                delta = cible - Math.max(ax, Math.max(bx, cx));
                this.deplacer(delta, 0);
                break;

            case GAUCHE:
                // Calcule la différence entre la cible et le point le plus à gauche du rectangle
                delta = cible - Math.min(ax, Math.min(bx, cx));
                this.deplacer(delta, 0);
                break;
    }
}
}
