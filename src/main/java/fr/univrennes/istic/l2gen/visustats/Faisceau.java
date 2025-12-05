package fr.univrennes.istic.l2gen.visustats;

import fr.univrennes.istic.l2gen.geometrie.Groupe;
import fr.univrennes.istic.l2gen.geometrie.IForme;
import fr.univrennes.istic.l2gen.geometrie.Rectangle;
import fr.univrennes.istic.l2gen.geometrie.Texte;

import java.util.Arrays;

public class Faisceau extends Groupe {
    String titre;
    double coordx;
    double coordy;
    double coordx2;
    double[] longueurs;
    String[] couleurs;
    Groupe grp;

    /**
     * @param titre     Le titre du faisceau
     * @param longueurs Les longueurs (=hauteurs) des différentes barres
     *                  Constructeur de faisceau. Les couleurs sont toutes à blanc
     *                  par défaut.
     *                  Le groupe contient les formes, le supergroupe contient le
     *                  texte et le groupe.
     */

    public Faisceau(double... longueurs) {
        this.longueurs = longueurs;
        this.couleurs = new String[this.longueurs.length];
        Arrays.fill(this.couleurs, "#000000");
        this.grp = new Groupe();
    }

    // TODO CHANGER LA FCT
    /**
     * @param axeX          L'axe d'alignement horizontal
     * @param axeY          L'axe d'alignement vertical
     * @param largeur       La largeur de chaque forme
     * @param echelle       L'échelle (coefficient affectant les axes, la longueur
     *                      et la hauteur de chaque forme)
     * @param verticalement Un booléen qui définit la méthode d'agencement (false =
     *                      horizontal ou true = vertical)
     *                      Méthode agencer permettant de construire correctement le
     *                      faisceau.
     */
    public void agencer(double axeX, double axeY, double largeur, double echelle, boolean verticalement) {
        double x;
        double y;
        double offset = 2;
        if (verticalement) {
            x = axeX + largeur;
            y = axeY +(this.longueurs.length / 2) ;
            for (double a : longueurs) {
                y -= a * echelle + (a * echelle) / 2;
            }
        } else {
            x = (axeX - ((largeur * this.longueurs.length) / 2)) - (largeur + 0.1 * echelle) / 2;
            y = axeY;

        }
        for (int i = this.longueurs.length - 1; i >= 0; i--) {
            Rectangle r = new Rectangle(x, y, largeur, this.longueurs[i] * echelle);
            r.setColor(this.couleurs[i]);
            this.grp.ajouter(r);
            coordx = x;
            coordy = y;
            if (verticalement) {
                y += this.longueurs[i] * echelle;
            } else {
                x += largeur + 0.1 * echelle + offset;
                offset = offset + echelle * (longueurs.length - 1 - i);
            }

        }
        coordx2 = x;
    }

    /**
     * @param couleurs Un ou plusieurs strings représentant une couleur
     *                 Méthode qui colorie une, plusieurs ou toutes les formes.
     */
    public void colorier(String... couleurs) {
        if (couleurs.length < 1 || couleurs.length > this.longueurs.length) {
            
            throw new IllegalArgumentException("More or less colours than elements");
        }

        String couleurUtilisee = couleurs[0];
        
        for (IForme forme : this.grp.getFormes()) {
            forme.colorier(couleurUtilisee);

        }
    }

    /**
     * @param titre une chaîne de caractères
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * @return une chaîne de caractères SVG contenant la représentation du faisceau
     */
    public String enSVG() {
        Texte text = new Texte(this.titre, this.coordx2+10, this.coordy + 20, 10);
        grp.ajouter(text);
        return "<g>" + this.grp.enSVG() + "</g>";

    }

    public static void main(String[] args) {
        // Faisceau fh = new Faisceau(" Exemple de Faisceau horizontal ", 100, 200,
        // 500);
        // fh.colorier(" blue ", " red ", " green ");
        // fh.agencer(20, 250, 100, 0.2, false);
        Faisceau fg = new Faisceau(100, 200, 500);
        fg.colorier(" cyan ", " purple ", " yellow ");
        fg.agencer(20, 250, 100, 0.2, true);

        System.out.println(fg.enSVG());
    }
}