package mainproject.tile;

import java.awt.image.BufferedImage;

/**
 * Représente une seule tuile de la carte.
 * Chaque tuile contient une image et une option de collision.
 */
public class Tile {

    /** L'image associée à cette tuile (grass, water, etc.) */
    public BufferedImage image;

    /**
     * Indique si cette tuile est "collisionnable" (true = mur, eau, arbre, etc.)
     * false = tuile traversable (ex: herbe, sable...).
     */
    public boolean collision = false; // Indique si la tuile est "solide" (pour gérer les collisions plus tard)

}