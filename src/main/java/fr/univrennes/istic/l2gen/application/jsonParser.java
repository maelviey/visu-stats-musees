package fr.univrennes.istic.l2gen.application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class JsonParser {

    /**
     * Analyse un fichier JSON contenant des données de musées et retourne une liste
     * d'objets Musee.
     * 
     * @param file Le fichier JSON à analyser.
     * @return Une liste d'objets Musee.
     * @throws IOException Si une erreur se produit lors de la lecture du fichier.
     */
    public static List<Musee> parseMusees(File file) throws IOException {
        // Liste pour stocker les objets Musee extraits
        List<Musee> musees = new ArrayList<>();
        
        // Lecture du contenu du fichier JSON en tant que chaîne
        String jsonContent = new String(java.nio.file.Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);

        // Décodage des séquences Unicode dans le contenu JSON
        jsonContent = decodeUnicodeEscapes(jsonContent);

        int i = 0;
        // Parcours du contenu JSON pour trouver les objets JSON individuels
        while ((i = jsonContent.indexOf("{", i)) != -1) {
            try {
                // Trouve l'accolade fermante correspondante pour un objet JSON
                int endIndex = findMatchingBrace(jsonContent, i);
                if (endIndex == -1)
                    break;

                // Extrait l'objet JSON et le parse en un objet Musee
                String museeJson = jsonContent.substring(i, endIndex + 1);
                Musee musee = parseMusee(museeJson);
                if (musee != null) {
                    musees.add(musee); // Ajoute l'objet Musee à la liste
                }

                i = endIndex + 1; // Passe à l'objet suivant
            } catch (Exception e) {
                i++; // Ignore les erreurs et continue
            }
        }

        return musees; // Retourne la liste des musées
    }

    /**
     * Convertit les séquences "uXXXX" en caractères réels.
     * 
     * @param input La chaîne contenant des séquences Unicode.
     * @return La chaîne avec les séquences Unicode décodées.
     */
    private static String decodeUnicodeEscapes(String input) {
        StringBuilder result = new StringBuilder();
        int i = 0;
        // Parcours de la chaîne pour détecter et convertir les séquences Unicode
        while (i < input.length()) {
            if (i + 5 < input.length() && 
                input.charAt(i) == '\\' && 
                input.charAt(i + 1) == 'u') {
                
                String hex = input.substring(i + 2, i + 6);
                try {
                    int code = Integer.parseInt(hex, 16);
                    result.append((char) code); // Ajoute le caractère décodé
                    i += 6;
                } catch (NumberFormatException e) {
                    result.append(input.charAt(i)); // Ajoute le caractère brut en cas d'erreur
                    i++;
                }
            } else {
                result.append(input.charAt(i)); // Ajoute les caractères normaux
                i++;
            }
        }
        return result.toString();
    }

    /**
     * Trouve l'accolade fermante correspondante pour un objet JSON, en tenant
     * compte des objets et des chaînes imbriqués.
     * 
     * @param json La chaîne JSON à analyser.
     * @param indiceDebut L'indice de l'accolade ouvrante.
     * @return L'indice de l'accolade fermante correspondante, ou -1 si non trouvé.
     */
    private static int findMatchingBrace(String json, int indiceDebut) {
        int nombreAccolade = 0;
        boolean inString = false;
        char[] chars = json.toCharArray();

        // Parcours des caractères pour trouver l'accolade fermante
        for (int i = indiceDebut; i < chars.length; i++) {
            char c = chars[i];

            if (c == '"' && (i == 0 || chars[i - 1] != '\\')) {
                inString = !inString; // Gère les chaînes de caractères
            } else if (!inString) {
                if (c == '{') {
                    nombreAccolade++; // Incrémente pour chaque accolade ouvrante
                } else if (c == '}') {
                    nombreAccolade--; // Décrémente pour chaque accolade fermante
                    if (nombreAccolade == 0) {
                        return i; // Retourne l'indice si toutes les accolades sont fermées
                    }
                }
            }
        }

        return -1; // Retourne -1 si aucune accolade fermante correspondante n'est trouvée
    }

    /**
     * Extrait la valeur associée à une clé donnée dans une chaîne JSON.
     * 
     * @param json La chaîne JSON.
     * @param cle La clé dont on veut extraire la valeur.
     * @return La valeur associée à la clé, ou null si non trouvée.
     */
    private static String extractValue(String json, String cle) {
        // Expression régulière pour trouver la clé et sa valeur
        String pattern = "\"" + cle + "\"\\s*:\\s*\"([^\"]*)\"";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(json);
        if (m.find()) {
            return m.group(1); // Retourne la valeur trouvée
        } else {
            return null; // Retourne null si la clé n'est pas trouvée
        }
    }
    
    /**
     * Extrait les valeurs d'un tableau JSON associé à une clé donnée.
     * 
     * @param json La chaîne JSON.
     * @param cle La clé associée au tableau.
     * @return Une liste de valeurs extraites, ou null si non trouvée.
     */
    private static List<String> extractArrayValues(String json, String cle) {
        // Expression régulière pour trouver un tableau JSON
        String pattern = "\"" + cle + "\"\\s*:\\s*\\[(.*?)\\]";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(json);
        if (m.find()) {
            String arrayContent = m.group(1); // Contenu du tableau
            List<String> values = new ArrayList<>();
            // Expression régulière pour extraire les éléments du tableau
            Pattern elementPattern = Pattern.compile("\"([^\"]*?)\"");
            Matcher elementMatcher = elementPattern.matcher(arrayContent);
            while (elementMatcher.find()) {
                values.add(elementMatcher.group(1)); // Ajoute chaque élément à la liste
            }
            return values;
        }
        return null; // Retourne null si le tableau n'est pas trouvé
    }

    /**
     * Analyse une chaîne JSON représentant un musée et retourne un objet Musee.
     * 
     * @param json La chaîne JSON à analyser.
     * @return Un objet Musee contenant les données du musée.
     */
    private static Musee parseMusee(String json) {
        Musee musee = new Musee();

        // Extraction des différentes propriétés du musée
        musee.setNom(extractValue(json, "nom_officiel"));
        musee.setVille(extractValue(json, "ville"));
        musee.setDepartment(extractValue(json, "departement"));
        musee.setRegion(extractValue(json, "region"));

        // Gestion des domaines thématiques
        List<String> domaines = extractArrayValues(json, "domaine_thematique");
        if (domaines != null) {
            musee.setDomaineList(domaines);
        } else {
            String domaine = extractValue(json, "domaine_thematique");
            if (domaine != null) {
                musee.setDomaineList(Collections.singletonList(domaine));
            }
        }

        // Gestion des thèmes
        List<String> themes = extractArrayValues(json, "themes");
        if (themes != null) {
            musee.setThemesList(themes);
        } else {
            String themesStr = extractValue(json, "themes");
            if (themesStr != null) {
                musee.setThemesList(Collections.singletonList(themesStr));
            }
        }

        // Gestion des personnages phares
        List<String> personnages = extractArrayValues(json, "personnage_phare");
        if (personnages != null) {
            musee.setPersonnageList(personnages);
        } else {
            String personnage = extractValue(json, "personnage_phare");
            if (personnage != null) {
                musee.setPersonnageList(Collections.singletonList(personnage));
            }
        }

        // Extraction des autres propriétés
        musee.setUrl(extractValue(json, "url"));
        musee.setArtiste(extractValue(json, "artiste"));

        // Gestion de l'année de création
        String anneeCreation = extractValue(json, "annee_creation");
        if (anneeCreation != null && !anneeCreation.isEmpty()) {
            try {
                musee.setAnneeCreation(Integer.valueOf(anneeCreation));
            } catch (NumberFormatException e) {
                // Ignore les erreurs de conversion
            }
        }

        // Gestion des catégories
        String categorie = extractValue(json, "categorie");
        if (categorie != null) {
            String[] categories = categorie.split(";");
            List<String> categorieList = new ArrayList<>();
    
            for (String cat : categories) {
                String trimmedCat = cat.trim();
                if (!trimmedCat.isEmpty() && !categorieList.contains(trimmedCat)) {
                    categorieList.add(trimmedCat);
                }
            }
    
            musee.setCategorieList(categorieList);
        } else {
            musee.setCategorieList(new ArrayList<>());
        }

        return musee; // Retourne l'objet Musee
    }

    /**
     * Télécharge et enregistre le fichier JSON avec un encodage correct.
     * 
     * @param jsonContent Le contenu JSON à enregistrer.
     * @param filePath Le chemin du fichier où enregistrer le contenu.
     * @throws IOException Si une erreur se produit lors de l'écriture du fichier.
     */
    public static void saveJsonWithCorrectEncoding(String jsonContent, String filePath) throws IOException {
        // Décodage des séquences Unicode avant l'enregistrement
        String decodedJson = decodeUnicodeEscapes(jsonContent);
        
        // Écriture du contenu décodé dans le fichier
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(filePath, StandardCharsets.UTF_8))) {
            writer.write(decodedJson);
        }
    }
}