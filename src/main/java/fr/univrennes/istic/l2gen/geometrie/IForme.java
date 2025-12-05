package fr.univrennes.istic.l2gen.geometrie;

public interface IForme {
    /**
     * Calcule le centre de la IForme.
     * 
     * @return Le point représentant le centre de la IForme.
     */
    Point centre();

    /**
     * Retourne une description de la IForme.
     * 
     * @param n l'indentation (2 caractères blancs pour un d'indentation) avant la
     *          description
     * @return Une chaîne de caractères décrivant la IForme.
     */
    String description(int n);

    /**
     * Calcule la hauteur de la IForme.
     * 
     * @return La hauteur de la IForme.
     */
    double hauteur();

    /**
     * Calcule la hauteur de la IForme.
     * 
     * @return La hauteur de la IForme.
     */
    double largeur();

    /**
     * Déplace la IForme selon les coordonnées spécifiées(x, y).
     *
     * @param x Deplacement sur l'axe X.
     * @param y Deplacement sur l'axe Y.
     */
    void deplacer(double x, double y);

    /**
     * Duplique la IForme.
     * 
     * @return Une nouvelle instance de IForme identique à celle-ci.
     */
    IForme dupliquer();

    /**
     * Redimensionne la IForme.
     * 
     * @param x Le facteur de redimensionnement en x.
     * @param y Le facteur de redimensionnement en y.
     */
    void redimensionner(double x, double y);

    /**
     * Retourne une représentation SVG de la IForme.
     * 
     * @return Une chaîne de caractères contenant la représentation SVG de la
     *         IForme.
     */
    String enSVG();

    /**
     * Colore la IForme.
     * 
     * @param couleur Les couleurs à appliquer à la IForme.
     */
    void colorier(String... couleur);

    /**
     * Applique une rotation à la IForme.
     * 
     * @param angle L'angle de rotation en degrés.
     */
    void tourner(int angle);

    /**
     * Aligne la IForme par rapport à un alignement donné et une cible.
     * 
     * @param alignement L'alignement à appliquer.
     * @param cible      La valeur cible pour l'alignement.
     */
    default void aligner(Alignement alignement, double cible) {
        if (alignement == Alignement.HAUT) {
            this.deplacer(0, cible - (this.hauteur() / 2) - this.centre().y());
        } else if (alignement == Alignement.BAS) {
            this.deplacer(0, cible + (this.hauteur() / 2) - this.centre().y());
        } else if (alignement == Alignement.DROITE) {
            this.deplacer(cible - (this.largeur() / 2) - this.centre().x(), 0);
        } else if (alignement == Alignement.GAUCHE) {
            this.deplacer(cible + (this.largeur() / 2) - this.centre().x(), 0);
        }
    }
}
