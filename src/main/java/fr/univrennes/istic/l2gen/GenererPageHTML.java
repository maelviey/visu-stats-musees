package fr.univrennes.istic.l2gen;
import fr.univrennes.istic.l2gen.geometrie.*;
import fr.univrennes.istic.l2gen.visustats.Camembert;
import fr.univrennes.istic.l2gen.visustats.DiagBarres;
import fr.univrennes.istic.l2gen.visustats.DiagColonnes;
import fr.univrennes.istic.l2gen.visustats.Faisceau;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GenererPageHTML {

    
    /**
     * Fonction générant une page HTML à partir des formes passées en paramètres
     * @param formes une ou plusieurs formes
     */
    public static void genererPageHTML(IForme ... formes) {
        //Création de la page HTML
        String headOfPage = """
                            <!doctype html>\r
                            <html lang="fr">\r
                            <head>\r
                              <meta charset="utf-8">\r
                              <title>Titre de la page</title>\r
                              <link rel="stylesheet" href="style.css">\r
                              <script src="script.js"></script>\r
                            </head>\r
                            <body>\r
                            """;
        String svgPartTop = """
                         <svg version="1.1" baseProfile="full" width="1200" height="8000" xmlns="http://www.w3.org/2000/svg">""";

        String svgPartBot = "\n</svg>";

        for(IForme forme :formes){
            svgPartTop += "\n" + forme.enSVG();
        }
        
        String bottomOfPage = """
                              </body>\r
                              </html>""";

        //Création et écriture du fichier
        Path path = Paths.get("./src/pageTest.html");
        try {
            String page = headOfPage + svgPartTop + svgPartBot + bottomOfPage;
            byte[] bs = page.getBytes();
            Path writtenFilePath = Files.write(path, bs);
            System.out.println("File Written");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws Exception {
        Faisceau forme = new Faisceau(1, 2, 3, 4, 5);
        forme.agencer(300, 500, 100, 20, false);
        forme.colorier("red","blue","green","yellow","purple");

        Faisceau forme2 = new Faisceau(1, 2, 3, 4, 5);
        forme2.agencer(1000, 500, 100, 20, true);
        forme2.colorier("red","blue","green","yellow","purple");

        Rectangle rec = new Rectangle(15.0,25.0,30.5,50.0);
        Cercle cer = new Cercle(50, 100, 45);
        Rectangle recx = new Rectangle(300,400,5,200);
        recx.setColor("red");
        Texte txtX = new Texte("Axe Y", 300, 400, 20);
        txtX.setColor("red");
        Rectangle recy = new Rectangle(200,500,200,5);
        recy.setColor("blue");
        Texte txtY = new Texte("Axe X", 200, 500, 20);
        txtY.setColor("blue");
        Texte labelFaisceau = new Texte("Faisceau Horizontal", 600, 520, 20);
        labelFaisceau.setColor("black");
        Texte labelFaisceau2 = new Texte("Faisceau Vertical", 1050, 520, 20);
        labelFaisceau2.setColor("black");
        Secteur sec = new Secteur(new Point(100, 200), 50, 0, 120);
        sec.colorier("green");
        System.out.println(sec.enSVG());
        Triangle tr = new Triangle(100, 200, 150, 250, 200, 200);
        tr.setColor("blue");

        Groupe grp = new Groupe(rec, cer, recx, recy, txtX, txtY, labelFaisceau, forme, labelFaisceau2, forme2, sec, tr);
        Camembert cam = new Camembert(new Point(300, 200), 50);
        cam.ajouterSecteur("red", 80);
        cam.ajouterSecteur("blue", 50);

        DiagBarres diag = new DiagBarres("Bojour", 1200, 800);
        diag.ajouterDonnees("2002",1,2,3,4);
        diag.ajouterDonnees("20102",1,24,35,42);
        diag.ajouterDonnees("20022",1,23,32,42);
        diag.ajouterDonnees("2002",1,23,32,42);
        diag.agencer();
        
        diag.colorier("blue","green","orange","red");    
        diag.legender("Ciel","cheval","cochon","vache");    
        
        grp.ajouter(cam);
        genererPageHTML(diag);

        
        
    }
}
