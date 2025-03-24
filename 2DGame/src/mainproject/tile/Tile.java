package mainproject.tile;

import java.awt.image.BufferedImage;

public class Tile {
    public BufferedImage image; // L'image de la tuile
    public boolean collision = false; // Indique si la tuile est "solide" (pour gérer les collisions plus tard)

    public Tile(BufferedImage image) {
        this.image = image;
    }

    public Tile(BufferedImage image, boolean collision) {
        this.image = image;
        this.collision = collision;
    }
}