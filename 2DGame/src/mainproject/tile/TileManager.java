package mainproject.tile;

import mainproject.GamePanel;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class TileManager {
    public BufferedImage[] tabtemp; // tableau temporaire

    public GamePanel gp;
    public Tile[] tileTypes; // Liste des types de tuiles (sol, mer, buisson, etc.)
    public int[][] mapTileNum; // Tableau 2D pour la map

    public TileManager(GamePanel gp) {

            this.gp = gp;
            tabtemp = new BufferedImage[4]; // Par exemple, 4 éléments si tu charges 4 images
            getTileImage(tabtemp);
            tileTypes = new Tile[10];
            tileTypes[0] = new Tile(tabtemp[3]); // Sol vert
            tileTypes[1] = new Tile(tabtemp[1]); // Mer
            tileTypes[2] = new Tile(tabtemp[0], true); // Buisson (avec collision)
            tileTypes[3] = new Tile(tabtemp[2]); // Petit espace jaune

            mapTileNum = new int[gp.maxScreenRow][gp.maxScreenCol];
            loadMap();
        }



    // Charger les ressources (images) depuis TilesetLoader

    public void getTileImage(BufferedImage[] tab) {
        try {
            tab[0] = ImageIO.read(getClass().getResourceAsStream("/tiles/buisson.png"));
            System.out.println("Le buisson a été importé avec succès ! [ 1/4 ]");
            tab[1] = ImageIO.read(getClass().getResourceAsStream("/tiles/mer2x4.png"));
            System.out.println("Mer importé avec succès ! [ 2/4 ]");
            tab[2] = ImageIO.read(getClass().getResourceAsStream("/tiles/petite_espace_jaune.png"));
            System.out.println("Mer importé avec succès ! [ 3/4 ]");
            tab[3] = ImageIO.read(getClass().getResourceAsStream("/tiles/sol_vert.png"));
            System.out.println("Mer importé avec succès ! [ 4/4 ]");
            System.out.println("Image Tiles chargé avec succès ");
            System.out.println("***************** Chargement réussi *****************");

        } catch (IOException e) {
            System.out.println("Une image n'as pas été chargé ");
            e.printStackTrace();

        }
    }

    // création du tableau des images :


    private void loadMap() {
        // Exemple de map : 0 = sol vert, 1 = mer, 2 = buisson, 3 = petit espace jaune
        mapTileNum = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // Ligne 1
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, // Ligne 2
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, // Ligne 3
                {0, 1, 0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, // Ligne 4
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, // Ligne 5
                {0, 1, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, // Ligne 6
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, // Ligne 7
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, // Ligne 8
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, // Ligne 9
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, // Ligne 10
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, // Ligne 11
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}// Ligne 12
        };
    }

    public void draw(Graphics2D g2) {
        // Dessiner la map
        for (int y = 0; y < mapTileNum.length; y++) {
            for (int x = 0; x < mapTileNum[y].length; x++) {
                int tileId = mapTileNum[y][x];
                if (tileTypes[tileId] != null) {
                    g2.drawImage(tileTypes[tileId].image, x * gp.tileSize, y * gp.tileSize, gp.tileSize, gp.tileSize, null);
                }
            }
        }
    }
}
