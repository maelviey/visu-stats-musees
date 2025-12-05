package fr.univrennes.istic.l2gen.geometrie;

import java.util.LinkedList;
import java.util.List;

/**
 * Classe representant un polygone defini par une liste de sommets.
 */
public class Polygone implements IForme {
    private List<Point> sommets;
    public String color;
    public int rotation;

    /**
     * Constructeur creant un polygone a partir d'une liste de coordonnees.
     * 
     * @param points Liste de coordonnees (x, y) en alternance.
     */
    public Polygone(double... points) {
        this.sommets = new LinkedList<>();
        //
        for (int i = 0; i < points.length; i += 2) {
            this.ajouterSommet(points[i], points[i + 1]); // Ajout des points au polygone
        }
        this.color = "white";
        this.rotation = 0;
    }

    /**
     * Ajoute un sommet au polygone.
     * 
     * @param sommet Le point a ajouter.
     */
    public void ajouterSommet(Point sommet) {
        this.sommets.add(sommet);
    }

    /**
     * Ajoute un sommet au polygone avec des coordonnees donnees.
     * 
     * @param x Abscisse du sommet.
     * @param y Ordonnee du sommet.
     */
    public void ajouterSommet(double x, double y) {
        this.sommets.add(new Point(x, y));
    }

    @Override
    public Point centre() {
        // Verifie si le polygone a au moins 3 sommets
        if (sommets == null || sommets.size() < 3) {
            throw new IllegalArgumentException("A polygon must have at least 3 points");
        }

        double sumX = 0, sumY = 0;
        double signedArea = 0;
        int n = sommets.size();

        // Parcours des sommets pour calculer l'aire et le centre de gravite
        for (int i = 0; i < n; i++) {
            Point p1 = sommets.get(i);
            Point p2 = sommets.get((i + 1) % n);

            double cross = p1.x() * p2.y() - p2.x() * p1.y();
            signedArea += cross;
            sumX += (p1.x() + p2.x()) * cross;
            sumY += (p1.y() + p2.y()) * cross;
        }
        // Calcul de l'aire totale
        signedArea *= 0.5;

        // Calcul du centre de gravite
        double cx = sumX / (6 * signedArea);
        double cy = sumY / (6 * signedArea);
        return new Point(cx, cy);
    }

    @Override
    public String description(int indentation) {
        // Initialisation de la chaîne qui contiendra la description du polygone
        String s = "Polygone";

        // Parcours de chaque sommet du polygone
        for (Point elt : this.sommets) {
            // Boucle pour ajouter les espaces d'indentation avant chaque coordonnées
            for (int i = 0; i < indentation; i++) {
                // Ajout d'un espace pour chaque niveau d'indentation demandé
                s += " ";
            }

            // Ajout des coordonnées du sommet (en entier) à la chaîne de description
            s += ((int) elt.x()) + "," + ((int) elt.y()); // Cast des coordonnées en int pour afficher les valeurs sans
                                                          // décimales
        }

        // Retour de la chaîne finale contenant la description du polygone
        return s;
    }

    /**
     * Retourne la liste des sommets du polygone.
     * 
     * @return Liste des sommets.
     */
    public List<Point> getSommets() {
        return sommets;
    }

    public IForme dupliquer() {
        // Creation d'une nouvelle instance de Polygone
        Polygone nouveau = new Polygone();
        // Ajout de chaque sommet du polygone actuel au nouveau polygone
        for (Point point : this.sommets) {
            nouveau.ajouterSommet(point);
        }
        nouveau.color = this.color;
        return nouveau;
    }

    @Override
    public void deplacer(double a, double b) {
        // Parcours de chaque sommet du polygone et application de la translation
        for (Point point : this.sommets) {
            point.x = point.x() + a;
            point.y = point.y() + b;
        }
    }

    @Override
    public void redimensionner(double a, double b) {
        // Parcours de chaque sommet du polygone et application du redimensionnement
        for (Point point : this.sommets) {
            point.x = point.x() * a;
            point.y = point.y() * b;
        }
    }

    @Override
    public double hauteur() {
        // Initialisation des valeurs minimales et maximales avec le premier sommet
        double mini = sommets.get(0).y();
        double maxi = sommets.get(0).y();
        // Parcours de chaque sommet pour trouver les valeurs minimales et maximales
        for (Point point : sommets) {
            if (point.y() < mini) {
                mini = point.y();
            }
            if (point.y() > maxi) {
                maxi = point.y();
            }
        }
        // Retourne la différence entre la valeur maximale et minimale
        return maxi - mini;
    }

    @Override
    public double largeur() {
        // Initialisation des valeurs minimales et maximales avec le premier sommet
        double mini = sommets.get(0).x();
        double maxi = sommets.get(0).x();
        // Parcours de chaque sommet pour trouver les valeurs minimales et maximales
        for (Point point : sommets) {
            if (point.x() < mini) {
                mini = point.x();
            }
            if (point.x() > maxi) {
                maxi = point.x();
            }
        }
        // Retourne la différence entre la valeur maximale et minimale
        return maxi - mini;
    }

    public String enSVG() {
        // Initialisation de la chaîne SVG
        String s = "<polygon points=\"";
        String trans;
        // Vérification de la rotation. Si la rotation est nulle, on ne l'ajoute pas à
        // la chaîne SVG
        if (this.rotation == 0) {
            trans = "";
        } else {
            trans = "transform=\"rotate(" + this.rotation + ")\" ";
        }
        // Parcours de chaque sommet pour construire la chaîne SVG
        for (int i = 0; i < this.sommets.size() - 1; i++) {
            s += (this.sommets.get(i).x()) + " " + (this.sommets.get(i).y()) + " ";
        }
        // Ajout du dernier sommet pour fermer le polygone
        s += (this.sommets.getLast().x()) + " " + (this.sommets.getLast().y());
        // Ajout de la couleur et de la rotation à la chaîne SVG
        s += "\" fill=\"" + this.color + " \" " + trans + "stroke=\"black\"/>";
        // Retour de la chaîne SVG finale et ajout de l'entête
        return "< polygon points = \"" + s;
    }

    @Override
    public void colorier(String... couleur) {
        this.color = couleur[0];
    }

    /**
     * Fait tourner la forme d'un certain angle autour de son centre.
     * 
     * @param angle L'angle de rotation en degrés (sens anti-horaire).
     */
    public void tournicoter(int angle) {
        // Récupération du centre du polygone
        Point centre = this.centre();
        double cx = centre.x();
        double cy = centre.y();
        // Calcul de l'angle en radians
        double angleRad = Math.toRadians(angle);
        // Parcours de chaque sommet pour appliquer la rotation
        for (Point point : this.sommets) {
            double xM = point.x() - cx;
            double yM = point.y() - cy;
            double x = xM * Math.cos(angleRad) + yM * Math.sin(angleRad) + cx;
            double y = -xM * Math.sin(angleRad) + yM * Math.cos(angleRad) + cy;
            // Arrondi des coordonnées
            point.x = (Math.round(x));
            point.y = (Math.round(y));
        }
    }

    @Override
    public void tourner(int angle) {
        rotation = angle;
    }

    @Override
    public void aligner(Alignement alignement, double cible) {
        // On effectue une action en fonction du type d'alignement spécifié
        switch (alignement) {
            case GAUCHE:
                // Recherche du point avec la coordonnée X minimale (le plus à gauche)
                double minX = sommets.get(0).x();
                for (Point point : sommets) {
                    if (point.x() < minX) {
                        minX = point.x(); // Met à jour la coordonnée X minimale
                    }
                }
                // Déplace la forme de façon à ce que son côté gauche soit à la position cible
                deplacer(cible - minX, 0);
                break;

            case DROITE:
                // Recherche du point avec la coordonnée X maximale (le plus à droite)
                double maxX = sommets.get(0).x();
                for (Point point : sommets) {
                    if (point.x() > maxX) {
                        maxX = point.x(); // Met à jour la coordonnée X maximale
                    }
                }
                // Déplace la forme de façon à ce que son côté droit soit à la position cible
                deplacer(cible - maxX, 0);
                break;

            case BAS:
                // Recherche du point avec la coordonnée Y minimale (le plus bas)
                double minY = sommets.get(0).y();
                for (Point point : sommets) {
                    if (point.y() < minY) {
                        minY = point.y(); // Met à jour la coordonnée Y minimale
                    }
                }
                // Déplace la forme de façon à ce que son bas soit à la position cible
                deplacer(0, cible - minY);
                break;

            case HAUT:
                // Recherche du point avec la coordonnée Y maximale (le plus haut)
                double maxY = sommets.get(0).y();
                for (Point point : sommets) {
                    if (point.y() > maxY) {
                        maxY = point.y(); // Met à jour la coordonnée Y maximale
                    }
                }
                // Déplace la forme de façon à ce que son haut soit à la position cible
                deplacer(0, cible - maxY);
                break;
        }
    }
}