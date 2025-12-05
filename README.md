# Visualisation de Statistiques & Formes Géométriques

## 1. Présentation du projet
> **Contexte :** Projet réalisé dans le cadre du cours d'**Génie Logiciel** enseigné par **Jean-Marc Jézéquel**, en **L2 ISTN** à l'**ISTIC - Université de Rennes**.
> **Note :** L'utilisation d'IA générative était autorisée et encouragée pour ce projet.
> **Équipe (Groupe J2-G5) :** Alexis TANGUY-NOBILET (Scrum Master), Toni TREBEL (Responsable communication), Maël VIEY, Nolan LE MAGUER, Nicolas MERRER, Léo RENAULT, Mamadou SALL YERO, Henry SOUL.

Ce projet implémente une application complète de **visualisation de données** (DataViz) en Java.
L'objectif est de récupérer les données ouvertes des **Musées de France**, de les structurer, et de générer dynamiquement des diagrammes statistiques vectoriels (SVG) via une interface graphique interactive.

## 2. Fonctionnalités & Modules
Le cœur du projet repose sur plusieurs modules interconnectés :

* **Extraction de Données :**
    * `JsonParser` : Téléchargement et parsing automatique du flux JSON depuis `data.gouv.fr`.
    * Filtrage multicritères (Région, Thème, Label, etc.).
* **Moteur Géométrique (Le "Noyau") :**
    * Système complet de formes vectorielles (`Cercle`, `Polygone`, `Ligne`) capable de s'auto-dessiner en SVG.
* **Visualisation Statistique :**
    * `DiagCamemberts` : Génération de Pie Charts.
    * `DiagBarres` / `DiagColonnes` : Histogrammes horizontaux et verticaux.
    * `genererRapport` : Création automatique d'un bilan HTML.

## 3. Architecture du Code
Le projet est structuré en **packages** distincts pour respecter le principe de séparation des responsabilités (MVC) :

* `fr.univrennes.istic.l2gen.application` :
    * `appStatistiquesMusées.java` : L'interface graphique (Vue/Contrôleur) en Swing.
    * `Musee.java` : Modèle de données représentant un musée.
* `fr.univrennes.istic.l2gen.geometrie` :
    * Contient les primitives graphiques (`IForme`, `Point`, `Cercle`...) indépendantes de l'application.
* `fr.univrennes.istic.l2gen.visustats` :
    * `IDataVisuliseur.java` : Interface pivot permettant de manipuler n'importe quel type de diagramme de façon générique.
* `src/test/java` : Suite de tests unitaires (JUnit 4) validant chaque forme géométrique et chaque type de diagramme.