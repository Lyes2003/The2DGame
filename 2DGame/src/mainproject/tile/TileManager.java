package mainproject.tile;

import mainproject.GamePanel;
import mainproject.utils.TileSetLoader;
import java.awt.Graphics2D;

public class TileManager {
    private GamePanel gp;
    private Tile[] tileTypes; // Liste des types de tuiles (sol, mer, buisson, etc.)
    private int[][] mapTileNum; // Tableau 2D pour la map

    public TileManager(GamePanel gp) {
        this.gp = gp;

        // Charger les ressources (images) depuis TilesetLoader
        TileSetLoader.TileResources resources = new TileSetLoader.TileResources();

        // Définir les types de tuiles
        tileTypes = new Tile[10]; // 10 types de tuiles max (tu peux augmenter si besoin)
        tileTypes[0] = new Tile(resources.solVert); // Sol vert
        tileTypes[1] = new Tile(resources.merTiles[0]); // Mer (première tuile de mer2x4)
        tileTypes[2] = new Tile(resources.buisson, true); // Buisson (avec collision)
        tileTypes[3] = new Tile(resources.petiteEspaceJaune); // Petit espace jaune

        // Définir la map (16 colonnes x 12 lignes pour correspondre à l'écran)
        mapTileNum = new int[gp.maxScreenRow][gp.maxScreenCol];
        loadMap();
    }

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
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}  // Ligne 12
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