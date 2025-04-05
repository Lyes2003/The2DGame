package mainproject;

import java.awt.*;

/**
 * Interface Drawable pour les objets qui peuvent être dessinés et ont une position verticale.
 */
public interface Drawable {
    /**
     * Dessine l'objet sur l'écran.
     *
     * @param g2 Contexte graphique pour le dessin.
     */
    void draw(Graphics2D g2);

    /**
     * Retourne la position verticale (worldY) de l'objet.
     *
     * @return La position verticale (worldY).
     */
    int getWorldY();
}
