package fr.univrennes.istic.l2gen.geometrie;
import java.util.ArrayList;

public class Groupe implements IForme {

    ArrayList<IForme> groupe;

    public Groupe(IForme ... formes){
        //Creation d'une arraylist de IForme pour stocker les formes
        this.groupe = new ArrayList<>();

        //Ajout de chaque forme passée en paramètre dans le groupe
        for (IForme forme:formes){
            this.ajouter(forme);
        }
    }

    /**
     * @param f une forme
     * @return le groupe de base possédant maintenant la forme passée en paramètre
     */
    public Groupe ajouter(IForme f){
        this.groupe.add(f);
        return this;
    }

    /**
     * @param formes une liste de formes
     * @return le groupe de base possédant maintenant les formes passées en paramètre
     */
    public Groupe ajouterMultiple(IForme ... formes){
        //Ajout de chaque forme passée en paramètre dans le groupe
        for (IForme forme:formes){
            this.ajouter(forme);
        }
        return this;
    }

    /**
     * @return les coordonées du point central du groupe
     */
    @Override
    public Point centre(){
        if (this.groupe.isEmpty()) {
            return new Point(0, 0);
        }
        // Initialisation des valeurs min et max pour les coordonnées x et y
        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE;

        // Parcours de chaque forme du groupe pour déterminer les coordonnées extrêmes
        for (IForme forme : groupe) {
            Point centreForme = forme.centre();
            double demiLargeur = forme.largeur() / 2;
            double demiHauteur = forme.hauteur() / 2;

            minX = Math.min(minX, centreForme.x() - demiLargeur);
            maxX = Math.max(maxX, centreForme.x() + demiLargeur);
            minY = Math.min(minY, centreForme.y() - demiHauteur);
            maxY = Math.max(maxY, centreForme.y() + demiHauteur);
        }
        // Calcul du centre du groupe en prenant la moyenne des coordonnées extrêmes et renvoi d'un nouveau point représentant le centre
        return new Point((minX + maxX) / 2, (minY + maxY) / 2);
    }

    /**
     * @param x coordonée x du point repère pour déplacer le groupe
     * @param y coordonée y du point repère pour déplacer le groupe
     */
    public void deplacer(double x, double y){
        //Parcour chaque forme du groupe et appelle la méthode deplacer de chaque forme
        for (IForme forme:groupe){
            forme.deplacer(x, y);
        }
    }

    /**
     * @param incr l'indentation (2 pour un) avant la description
     * @return une chaîne de caractères contenant la description du groupe
     */
    public String description(int incr){
        //Creation d'une chaîne de caractères contenant la description du groupe
        String result = "";

        //Ajout d'une indentation de 2 espaces pour chaque niveau d'imbrication
        for(int i=0;i<=incr;i++){
            result+=("\t");
        }
        //Ajout de la description du groupe
        result += "Groupe\n";

        //Ajout de la description de chaque forme du groupe
        for (IForme forme:groupe){
            result += forme.description(incr+1);
            result+= "\n";
        }
        return result;
    }

    /**
     * @return une copie profonde du groupe sur lequel la fonction est appelée
     */
    public IForme dupliquer(){
        //Création d'un nouveau groupe vide
        Groupe ret = new Groupe();

        //Parcours de chaque forme du groupe et ajout de la forme dupliquée au nouveau groupe en appelant la méthode dupliquer de chaque forme
        for(IForme forme : this.groupe){
            ret.ajouter(forme.dupliquer());
        }
        return ret;
    }

    /**
     * @return la hauteur totale du groupe
     */
    public double hauteur(){
        //Si le groupe est vide, on retourne 0
        if (this.groupe.isEmpty()) {
            return 0;
        }
    
        // Initialisation des valeurs min et max pour les coordonnées y
        double minY = Double.MAX_VALUE;
        double maxY = Double.MIN_VALUE;
    
        // Parcours de chaque forme du groupe pour déterminer les coordonnées extrêmes
        for (IForme forme : groupe) {
            Point centreForme = forme.centre();
            double demiHauteur = forme.hauteur() / 2;
    
            double formeMinY = centreForme.y() - demiHauteur;
            double formeMaxY = centreForme.y() + demiHauteur;
    
            minY = Math.min(minY, formeMinY);
            maxY = Math.max(maxY, formeMaxY);
        }
        // Calcul de la hauteur totale du groupe en prenant la différence entre les coordonnées y extrêmes
        return maxY - minY;
    }

    /**
     * @return la largeur totale du groupe
     */
    public double largeur(){
        //Si le groupe est vide, on retourne 0
        if (this.groupe.isEmpty()) {
            return 0;
        }
    
        // Initialisation des valeurs min et max pour les coordonnées x
        double minX = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
    
         // Parcours de chaque forme du groupe pour déterminer les coordonnées extrêmes
        for (IForme forme : groupe) {
            Point centreForme = forme.centre();
            double demiLargeur = forme.largeur() / 2;
    
            double formeMinX = centreForme.x() - demiLargeur;
            double formeMaxX = centreForme.x() + demiLargeur;
    
            minX = Math.min(minX, formeMinX);
            maxX = Math.max(maxX, formeMaxX);
        }
        
        // Calcul de la largeur totale du groupe en prenant la différence entre les coordonnées x extrêmes
        return maxX - minX;
    }

    /**
     * @param x un coefficient de redimension
     * @param y un coefficient de redimension
     * redimensionne chaque élément du groupe en fonction des coefficients passés en paramètre
     */
    public void redimensionner(double x, double y){
        //Parcour chaque forme du groupe et appelle la méthode redimensionner de chaque forme
        for (IForme forme:groupe){
            forme.redimensionner(x, y);
        }
    }

    /**
     * @return une chaîne de caractères SVG contenant les informations du groupe
     */
    public String enSVG(){
        //Creation d'une chaîne de caractères contenant les informations du groupe
        String result = "<g>\n";

        //Ajout de la description de chaque forme du groupe
        for (IForme forme : groupe){
            result += forme.enSVG();
            result+="\n";
        }

        //Ajout de la balise de fermeture du groupe
        result+="</g>";
        return result;
    }

    public void colorier(String ...couleur){
        int colno = 0;
        //Parcour chaque forme du groupe et appelle la méthode colorier de chaque forme
        for(IForme g : groupe){
            g.colorier(couleur[colno]);
            colno++;
            if (colno == couleur.length){colno = 0;} 
        }
    }
    @Override
    public void aligner(Alignement alignement, double cible) {
        //Parcour chaque forme du groupe et appelle la méthode aligner de chaque forme
        for(IForme g : groupe){
            g.aligner(alignement, cible);
        }
        
    }
    @Override
    public void tourner(int angle) {
        //Parcour chaque forme du groupe et appelle la méthode tourner de chaque forme
        for(IForme g : groupe){
            g.tourner(angle);
        }
        
    }

    /**
     * @return le groupe vide
     */
    public IForme vider() {
        //Vider le groupe en appelant la méthode clear de l'arraylist
        this.groupe.clear();
        return this;
    }

    /**
     * @param alignement vers où les formes doivent être alignées
     * @param cible la droite contre laquelle les éléments du groupe doivent être alignés
     * @return le groupe
     */
    public IForme alignerElements ( Alignement alignement , double cible ) {
         //Parcour chaque forme du groupe et appelle la méthode aligner de chaque forme
        for(IForme forme:groupe){
            forme.aligner(alignement, cible);
        }
        return this;
    }

    /**
     * @param alignement vers où empiler les formes du groupe
     * @param cible la droite sur laquelle les empiler
     * @param separation distance entre chaque éléments empilé
     * @return le groupe
     */
    public IForme empilerElements ( Alignement alignement , double cible , double separation ) {
        for(IForme forme:groupe){
            forme.aligner(alignement, cible);
            cible = cible + forme.hauteur()+separation;
        }
        return this;
    }

    public boolean isEmpty(){
        return this.groupe.isEmpty();
    }

    public int size() {
        //Retourne la taille du groupe
            return groupe.size();
    }

    public ArrayList<IForme> getFormes() {
        return this.groupe;
    }
    
}
