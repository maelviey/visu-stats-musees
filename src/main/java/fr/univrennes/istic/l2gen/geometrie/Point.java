package fr.univrennes.istic.l2gen.geometrie;

import java.util.Objects;

public class Point {
    double x;   //soit les coordonées x et y d'un point
    double y;

    /**
     * @param x un double
     * @param y un double
     * constructeur créant une instance de Point de par les coordonées passées en paramètre
     */
    public Point(double x, double y){
        this.x = x; this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        // Vérifie si les deux objets sont identiques
        if (this == obj) return true; 
        if (obj == null || getClass() != obj.getClass()) return false; 
        Point point = (Point) obj;
        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * @param p un Point
     * ajoute les valeurs du point p au point sur lequel est effectué la fonction
     */
    public void plus(Point p){
        this.x += p.x;
        this.y += p.y;
    }

    /**
     * @param x une coordonée x
     * @param y une coordonée y
     * similaire à la fonction précédente, seulement à la place d'un objet Point,
     * l'on additione directement les coordonées
     */
    public void plus(double x, double y){
        this.x += x;
        this.y += y;
    }

    /**
     * @return la valeur x du Point
     */
    public double x(){
        return x;
    }

    /**
     * @return la valeur y du Point
     */
    public double y(){
        return y;
    }
}
