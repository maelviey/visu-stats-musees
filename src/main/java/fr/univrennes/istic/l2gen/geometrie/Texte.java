package fr.univrennes.istic.l2gen.geometrie;

public class Texte implements IForme{
    public String txt;
    public Point center;
    public double fontSize;
    public String color;
    public int angle;

    /**
     * @param txt une chaîne de caractères
     * @param center le centre de l'objet Texte
     * @param fontsize la taille de police d'écriture
     * Constructeur n°1
     */
    public Texte(String txt, Point center, double fontsize){
        this.txt = txt;
        this.center = center;
        this.fontSize=fontsize;
        this.color = "white";
    }

    /**
     * @param txt une chaîne de caractères
     * @param x la coordonée x du centre de l'objet Texte
     * @param y la coordonée y du centre de l'objet Texte
     * @param fontsize la taille de police d'écriture
     * Constructeur n°2
     */
    public Texte(String txt, double x, double y, double fontsize){
        this.txt = txt;
        this.center = new Point(x,y);
        this.fontSize=fontsize;
        this.color = "white";
    }

    /**
     * @param color la couleur
     */
    public void setColor(String color){
        this.color = color;
    }

    @Override
    public Point centre(){
        return this.center;
    }

    @Override
    public String description(int incr){
        String result = "";
        for(int i=0;i<=incr;i++){
            result+="  ";
        }
        result+=this.txt;
        return result;
    }

    @Override
    public void deplacer(double x, double y){
        this.center= new Point(x,y);
    }

    @Override
    public IForme dupliquer(){
        IForme newTxt = new Texte(this.txt, this.center, this.fontSize);
        newTxt.tourner(this.angle);
        return newTxt;
    }

    @Override
    public double hauteur(){
        return this.hauteur();
    }

    @Override
    public double largeur(){
        return this.fontSize*this.txt.length();
    }

    @Override
    public void redimensionner(double x, double y){
        this.fontSize=this.fontSize*x;
    }

    @Override
    public String enSVG(){
        String svg = "<text x = \""+this.center.x()+"\" y=\""+this.center.y()+"\" font-size=\""+
        this.fontSize+"\" text-anchor= \"middle\""+ //
        " fill=\""+this.color+"\" stroke=\"black\">"+this.txt;
        if(angle!=0){ //Si l'angle est différent de 0, on applique une rotation 
            svg+="\ntransform=\"rotate("+this.angle+")\"</text>";
        } else { //Sinon, on ajoute la balise de fermeture
            svg+="</text>";
        }
        return svg;
    }

    @Override
    public void colorier(String ... couleur){
        this.color = couleur[0];
    }

    @Override
    public void tourner(int angle){
        this.angle = angle;
    }

    @Override
    public void aligner(Alignement alignement, double cible){
    }
}
