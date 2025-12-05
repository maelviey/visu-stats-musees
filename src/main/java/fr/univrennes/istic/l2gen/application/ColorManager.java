package fr.univrennes.istic.l2gen.application;

public class ColorManager {
    /**
     * Cette méthode produit des couleurs visuellement distinctes qui sont adaptées
     * pour représenter différentes catégories dans les graphiques et visualisations.
     * 
     * @param n Le nombre de couleurs distinctes à générer
     * @return Un tableau de chaînes contenant les codes hexadécimaux des couleurs générées
     */
    
    public static String[] generateHuePalette(int n) {
        // Initialisation du tableau qui contiendra les codes de couleurs
        String[] colors = new String[n];
        
        // Génération de n couleurs en variant la teinte de manière uniforme
        for (int i = 0; i < n; i++) {
            // Calcul de la teinte (hue) répartie équitablement entre 0 et 1
            float hue = (float) i / n;
            
            // Valeurs fixes pour saturation et luminosité pour garantir des couleurs visibles
            float saturation = 0.65f;
            float brightness = 0.90f;
            
            // Conversion des valeurs HSB en RGB
            int rgb = java.awt.Color.HSBtoRGB(hue, saturation, brightness);
            
            // Formatage de la valeur RGB en code hexadécimal (ex: "#FF8800")
            String hex = String.format("#%06X", (0xFFFFFF & rgb));
            
            // Stockage de la couleur générée dans le tableau
            colors[i] = hex;
        }
        
        return colors;
    }
}