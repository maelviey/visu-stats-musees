package fr.univrennes.istic.l2gen.visustats;

import fr.univrennes.istic.l2gen.geometrie.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {

    /**
     * Fonction générant une page HTML à partir des formes passées en paramètres
     * 
     * @param formes une ou plusieurs formes
     */
    public static void genererPageHTML(IForme... formes) {
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
                """; // En-tête de la page HTML : méta-données HTML, titre de la page
        String svgPartTop = """
                <svg version="1.1" baseProfile="full" width="300" height="200" xmlns="http://www.w3.org/2000/svg">""";
        // Précision de la version et du profil SVG, création de la zone de dessin aux
        // dimensions personnalisables

        String svgPartBot = "\n</svg>"; // Saut de ligne puis fermeture de la balise svg

        for (IForme forme : formes) {
            svgPartTop += "\n" + forme.enSVG(); // Boucle qui ajoute les différentes formes passées en paramètres grâce
                                                // à leur implémentation de la méthode enSVG d'IForme
        }

        String bottomOfPage = """
                </body>\r
                </html>"""; // Fermeture des balises body et html, fin du fichier

        // Création et écriture du fichier
        Path path = Paths.get("./src/pageTest.html"); // Définition du chemin d'accès pour le fichier de tests
        try { // Tentative d'écriture du fichier HTML avec gestion des erreurs
            String page = headOfPage + svgPartTop + svgPartBot + bottomOfPage; // Création de la page HTML complète
                                                                               // grâce aux différentes parties définies
            byte[] bs = page.getBytes();
            Path writtenFilePath = Files.write(path, bs); // Ecriture du fichier
            System.out.println("File Written"); // Confirmation d'écriture du fichier
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Exemple de génération d'une page HTML contenant des formes graphiques
    public static void main(String[] args) throws Exception {
        Faisceau forme = new Faisceau(1, 2, 3, 4, 5); // Création d'un faisceau
        Rectangle rec = new Rectangle(15.0, 25.0, 30.5, 50.0); // Création d'un rectangle
        Cercle cer = new Cercle(50, 100, 45); // Création d'un cercle

        forme.agencer(300, 600, 100, 100, false); // Disposition des formes grâce à la méthode agencer de Faisceau
        forme.colorier("red", "blue", "green", "yellow", "purple"); // Coloration des formes
        genererPageHTML(rec, cer);

    }
}
