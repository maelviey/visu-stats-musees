package fr.univrennes.istic.l2gen.geometrie;

public class Secteur  implements IForme{
    public Point centre;
    public double rayon;
    public double angle;
    public double arc;
    public String color;

    /**
     * @param centre coordonées du centre représenté en Point
     * @param rayon rayon en double
     * @param angle angle en double
     * @param arc arc en double
     * Constructeur n°1 de Secteur (de Cercle)
     */
    public Secteur(Point centre, double rayon, double angle, double arc){
        this.centre = centre;
        this.rayon = rayon;
        this.angle = angle;
        this.arc = arc;
        this.color = "white";
    }

    /**
     * @param x coordonée x du centre
     * @param y coordonée y du centre
     * @param rayon rayon en double
     * @param angle angle en double
     * @param arc arc en double
     * Constructeur n°1 de Secteur (de Cercle)
     */
    public Secteur(double x, double y, double rayon, double angle, double arc){
        this.centre = new Point(x,y);
        this.rayon = rayon;
        this.angle = angle;
        this.arc = arc;
        this.color = "white";
    }

    @Override
    public Point centre(){
        return this.centre;
    }

    /**
     * @return l'angle
     */
    public double getAngle(){
        return this.angle;
    }

    /**
     * @return l'arc
     */
    public double getArc(){
        return this.arc;
    }

    @Override
    public double hauteur() {
        return Math.abs(centre.y() - yLePlusHaut());

    }

    @Override
    public double largeur() {
        return Math.abs(this.xLePlusADroite()-this.xLePlusAGauche());
    }


    /**
     * @return L'ordonnée du point le plus bas du secteur
     */
    public double yLePlusBas() {
        double minY = centre.y(); // Initialise avec l'ordonnée du centre
        // Parcours des points à l'angle de départ et l'angle de départ + arc
        for (double angle2 = angle; angle2 <= angle + arc; angle2++) {
            double y = getPointAtAngle(angle2).y();
            if (y < minY) {
                minY = y;
            }
        }
        return minY;
    }

    /**
     * @return L'ordonnée du point le plus haut du secteur
     */
    public double yLePlusHaut() {
        double maxY = centre.y(); // Initialise avec l'ordonnée du centre
        // Parcours des points à l'angle de départ et l'angle de départ + arc
        for (double angle2 = angle; angle2 <= angle + arc; angle2++) {
            double y = getPointAtAngle(angle2).y();
            if (y > maxY) {
                maxY = y;
            }
        }
        return maxY;
    }

    /**
     * @return L'abscisse du point le plus à droite du secteur
     */
    public double xLePlusADroite() {
        double maxX = centre.x(); // Initialise avec l'abscisse du centre
        // Parcours des points à l'angle de départ et l'angle de départ + arc
        for (double angle2 = angle; angle2 <= angle + arc; angle2++) {
            double x = getPointAtAngle(angle2).x();
            if (x > maxX) {
                maxX = x;
            }
        }
        return maxX;
    }

    /**
     * @return L'abscisse du point le plus à gauche du secteur
     */
    public double xLePlusAGauche() {
        double minX = centre.x(); // Initialise avec l'abscisse du centre
        // Parcours des points à l'angle de départ et l'angle de départ + arc
        for (double angle2 = angle; angle2 <= angle + arc; angle2++) {
            double x = getPointAtAngle(angle2).x();
            if (x < minX) {
                minX = x;
            }
        }
        return minX;
    }

    @Override
    public void deplacer(double x, double y){
        this.centre= new Point(x,y);
    }

    @Override
    public void redimensionner(double a, double b) {
        this.rayon *=a;
    }

    @Override
    public String description(int incr){
        String result = "";
        for(int i=0;i<=incr;i++){
            result+="  ";
        }
        result+="secteur centre="+this.centre.x()+","+this.centre.y()+" r="+this.rayon+" Angle="+this.angle+" Arc="+this.arc;
        return result;
    }

    /**
     * @param angle L'angle en degrés
     * @return Le point sur le bord du cercle à l'angle spécifié
     */
    public Point getPointAtAngle(double angle) {
        // Conversion degrés en radians
        double radianAngle = Math.toRadians(angle);
        // Calcul des coordonnées du point sur le bord du cercle
        double x = centre.x() + rayon * Math.cos(radianAngle);
        double y = centre.y() + rayon * Math.sin(radianAngle);

        return new Point(x, y);
    }

    @Override
    public String enSVG() {
        // Calcul des coordonnées du point de départ de l'arc
        Point startPoint = getPointAtAngle(this.angle);

        // Calcul des coordonnées du point de fin de l'arc
        Point endPoint = getPointAtAngle(this.angle + arc);

        // Construction de la chaîne SVG
        return "<path d=\"" +
                "M " + centre.x() + " " + centre.y() + " " + // Déplacer au centre
                "L " + startPoint.x() + " " + startPoint.y() + " " + // Dessiner une ligne vers le point de départ de l'arc
                "A " + rayon + " " + rayon + " 0 " + // Arc de cercle avec le rayon du secteur
                (arc > 180 ? "1" : "0") + // Grand ou petit arc
                " 1 " + // Sens de rotation (1 pour sens horaire)
                endPoint.x() + " " + endPoint.y() + " " + // Dessiner une ligne vers le point de fin de l'arc
                "Z" + // Fermer le chemin
                "\" fill=\""+this.color+"\" stroke=\"black\"/>";
    }


    @Override
    public IForme dupliquer(){
        Secteur newSecteur = new Secteur(this.centre, this.rayon, this.angle, this.arc);
        return newSecteur;
    }

    @Override
    public void colorier(String ... couleur){
        this.color = couleur[0];
    }

    @Override
    public void tourner(int angle){
        this.angle = angle;
    }
    
}
