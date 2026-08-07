package fr.asenka.visu.shared;

import fr.asenka.visu.model.Node;

/**
 * Représente un vecteur dans un espace à deux dimensions (2D).
 * Ce record est immuable et fournit les opérations mathématiques de base
 * pour les vecteurs et les distances.
 *
 * @param x La coordonnée horizontale du vecteur.
 * @param y La coordonnée verticale du vecteur.
 */
public record Vector2D(double x, double y) {

    /**
     * Représente le vecteur origine (0, 0).
     */
    public static final Vector2D ORIGIN = new Vector2D(0d, 0d);

    /**
     * Constructeur permettant de créer un vecteur à partir d'un objet {@link Node}.
     *
     * @param node Le nœud dont on extrait les coordonnées x et y.
     */
    public Vector2D(Node node) {
        this(node.x(), node.y());
    }

    /**
     * Alias for x()
     */
    public double first() {
        return x();
    }

    /**
     * Alias for y()
     */
    public double second() {
        return y();
    }

    /**
     * Alias for x()
     */
    public double left() {
        return x();
    }

    /**
     * Alias for y()
     */
    public double right() {
        return y();
    }

    /**
     * Ajoute un autre vecteur à celui-ci.
     *
     * @param other Le vecteur à ajouter.
     * @return Un nouveau vecteur résultant de l'addition.
     */
    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    /**
     * Soustrait un autre vecteur de celui-ci.
     *
     * @param other Le vecteur à soustraire.
     * @return Un nouveau vecteur résultant de la soustraction.
     */
    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    /**
     * Multiplie le vecteur par un scalaire.
     *
     * @param scalar La valeur scalaire de multiplication.
     * @return Un nouveau vecteur multiplié par le scalaire.
     */
    public Vector2D multiply(double scalar) {
        return new Vector2D(this.x * scalar, this.y * scalar);
    }

    /**
     * Calcule la norme (longueur) du vecteur.
     *
     * @return La magnitude (ou norme) du vecteur (i.e. sa longueur)
     */
    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Retourne un vecteur unitaire (de même direction mais de norme 1).
     * Si la magnitude est nulle, retourne le vecteur origine.
     *
     * @return Un vecteur normalisé ou le vecteur origine si la magnitude est 0.
     */
    public Vector2D normalize() {
        double mag = magnitude();
        return mag == 0 ? new Vector2D(0, 0) : multiply(1.0 / mag);
    }

    /**
     * Calcule la distance euclidienne entre ce vecteur et les coordonnées (x, y).
     *
     * @param x La coordonnée x de destination.
     * @param y La coordonnée y de destination.
     * @return La distance entre les deux points.
     */
    public double distance(double x, double y) {
        return Math.sqrt(distanceSq(x, y));
    }

    /**
     * Calcule le carré de la distance euclidienne entre ce vecteur et les coordonnées (x, y).
     * Plus performant que {@link #distance(double, double)} car évite l'opération de racine carrée.
     *
     * @param x La coordonnée x de destination.
     * @param y La coordonnée y de destination.
     * @return Le carré de la distance.
     */
    public double distanceSq(double x, double y) {
        final double dx = x - this.x;
        final double dy = y - this.y;
        return (dx * dx + dy * dy);
    }

    /**
     * Calcule la distance euclidienne entre ce vecteur et un autre vecteur.
     *
     * @param other Le vecteur cible.
     * @return La distance entre les deux vecteurs.
     * @throws IllegalArgumentException si le vecteur autre est nul.
     */
    public double distance(Vector2D other) {
        if (other == null) {
            throw new IllegalArgumentException("other vector is null");
        }
        return distance(other.x, other.y);
    }

    /**
     * Calcule le carré de la distance euclidienne entre ce vecteur et un autre vecteur.
     * Plus performant que {@link #distance(Vector2D)} car évite l'opération de racine carrée.
     *
     * @param other Le vecteur cible.
     * @return Le carré de la distance.
     * @throws IllegalArgumentException si le vecteur autre est nul.
     */
    public double distanceSq(Vector2D other) {
        if (other == null) {
            throw new IllegalArgumentException("other vector is null");
        }
        return distanceSq(other.x, other.y);
    }

}
