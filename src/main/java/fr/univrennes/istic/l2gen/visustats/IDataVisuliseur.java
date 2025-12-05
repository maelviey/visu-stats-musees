package fr.univrennes.istic.l2gen.visustats;

import fr.univrennes.istic.l2gen.geometrie.IForme;
/*
 * Classe interface pour les diagramme
 */

public interface IDataVisuliseur extends IForme{

/* 
 * Organise et prépare les données pour la visualisation
 * @return une instance IDataVisualiseur configurée pour visualisation 
 */
    public void agencer();

    /**Ajout des données à la visualisation
     * @param donnes la description ou le label des données
     * @param valeurs une ou plusieurs valeurs de données à ajouter
     * @return l'instance actuelle de IDataVisualiseur pour permettre l'enchaînement
     *         des appels
     */
    public void ajouterDonnees(String donnes, double... valeurs);
    
    /** Ajoute des légendes à la visualisation
     * @param legende une ou plusieurs légendes à ajouter
     * @return l'instance actuelle de IDataVisualiseur pour permettre l'enchaînement
     *         des appels
     */
    public void legender(String... legende);

    /**Définit des options pour la visualisation
     * @param options une ou plusieurs options à définir
     * @return l'instance actuelle de IDataVisualiseur pour permettre l'enchaînement
     *         des appels
     */
    public void setOptions(String... options);
}
