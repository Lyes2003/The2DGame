package mainproject.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TileSetLoader {
    // Méthode pour charger un tileset (déjà présente)
    public static BufferedImage[] loadTileset(String path, int tileWidth, int tileHeight, int rows, int cols) throws IOException {
        BufferedImage tileset = ImageIO.read(new File(path));
        BufferedImage[] tiles = new BufferedImage[rows * cols];

        int index = 0;
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                tiles[index] = tileset.getSubimage(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
                index++;
            }
        }
        return tiles;
    }

    // Méthode pour charger une image unique
    public static BufferedImage loadSingleImage(String path) throws IOException {
        return ImageIO.read(new File(path));
    }

    // Classe pour stocker toutes les ressources chargées
    public static class TileResources {
        public BufferedImage[] merTiles;
        public BufferedImage solVert;
        public BufferedImage buisson;
        public BufferedImage petiteEspaceJaune;

        public TileResources() {
            try {
                // Charger le tileset mer2x4.png
                this.merTiles = loadTileset("GameProject/res/tiles/mer2x4.png", 16, 16, 2, 4);

                // Charger les images uniques
                this.solVert = loadSingleImage("GameProject/res/tiles/sol_vert.png");
                this.buisson = loadSingleImage("GameProject/res/tiles/buisson.png");
                this.petiteEspaceJaune = loadSingleImage("GameProject/res/tiles/petite_espace_jaune.png");

            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Erreur lors du chargement des ressources : " + e.getMessage());
            }
        }
    }

    // Méthode pour tester (facultatif)
    public static void main(String[] args) {
        TileResources resources = new TileResources();
        System.out.println("Ressources chargées :");
        System.out.println("Mer tiles : " + resources.merTiles.length + " tuiles");
        System.out.println("Sol vert : " + (resources.solVert != null ? "Chargé" : "Erreur"));
        System.out.println("Buisson : " + (resources.buisson != null ? "Chargé" : "Erreur"));
        System.out.println("Petite espace jaune : " + (resources.petiteEspaceJaune != null ? "Chargé" : "Erreur"));
    }
}