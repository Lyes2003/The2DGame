package mainproject.tile;

import mainproject.GamePanel;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Gère toutes les tuiles du jeu :
 * - Chargement des images
 * - Chargement de la carte depuis un fichier texte
 * - Dessin des tuiles à l’écran (avec scrolling caméra)
 */
public class TileManager {

    public GamePanel gp;

    /** Tableau contenant tous les types de tuiles disponibles (grass, wall, etc.) */
    public Tile[] tileTypes;

    /**
     * Carte du monde (3D) (il y a trois couches de tuiles) :
     * [layer][colonne][ligne]
     * Permet d’avoir plusieurs couches superposées (sol, décor, objets hauts...).
     */
    public int[][][] mapTileNum;

    /**
     * Constructeur : initialise les tuiles et charge la carte.
     * @param gp Panneau de jeu principal
     */
    public TileManager(GamePanel gp) {

            this.gp = gp;
            tileTypes = new Tile[20];
        mapTileNum = new int[3][gp.maxWorldCol][gp.maxWorldRow]; // 3 couches (sol, décor, objets en hauteur)

            getTileImage(); // charge les images de chaque tuile

            loadMap("/maps/test6.txt"); // charge la carte
        }

    /**
     * Charge les images des différentes tuiles dans le tableau tileTypes[].
     * Chaque image est liée à un fichier .png dans le dossier /tiles/.
     */
    public void getTileImage() {
        try {

            tileTypes[0] = new Tile();
            tileTypes[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
            System.out.println("L'herbe a été importé avec succès ! ");

            tileTypes[1] = new Tile();
            tileTypes[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/mur-pierre-2.png"));
            tileTypes[1].collision = true;
            System.out.println("L'herbe a été importé avec succès ! ");

            tileTypes[2] = new Tile();
            tileTypes[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/mere-1.png"));
            tileTypes[2].collision = true;
            System.out.println("Mer importé avec succès ! ");

            tileTypes[3] = new Tile();
            tileTypes[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));
            System.out.println("Mer importé avec succès !");

            tileTypes[4] = new Tile();
            tileTypes[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/plante-type-1.png"));
            tileTypes[4].collision = false;
            System.out.println("Espace jaune importé avec succès ! ");

            tileTypes[5] = new Tile();
            tileTypes[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
            System.out.println("Buisson importé avec succès ! ");

            tileTypes[6] = new Tile();
            tileTypes[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/terre.png"));

            tileTypes[7] = new Tile();
            tileTypes[7].image = ImageIO.read(getClass().getResourceAsStream("/tiles/plante-type-1.png"));

            tileTypes[8] = new Tile();
            tileTypes[8].image = ImageIO.read(getClass().getResourceAsStream("/tiles/plante-type-2.png"));

            tileTypes[9] = new Tile();
            tileTypes[9].image = ImageIO.read(getClass().getResourceAsStream("/tiles/plante-type-3.png"));

            tileTypes[10] = new Tile();
            tileTypes[10].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));

            tileTypes[11] = new Tile();
            tileTypes[11].image = ImageIO.read(getClass().getResourceAsStream("/tiles/mur-pierre-2.png"));

            System.out.println("Image Tiles chargé avec succès ");
            System.out.println("***************** Chargement réussi *****************");

        } catch (IOException e) {
            System.out.println("Une image n'as pas été chargé ");
            e.printStackTrace();

        }
    }

    /**
     * Lit un fichier texte représentant la carte (plusieurs couches).
     * Chaque ligne du fichier contient des numéros de tuiles séparés par des espaces.
     *
     * @param mapFileLocation chemin vers le fichier de la carte (dans /resources)
     */
    public void loadMap(String mapFileLocation){

        try{
            InputStream is = getClass().getResourceAsStream(mapFileLocation);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for (int layer = 0; layer < 3; layer++) {
                for (int row = 0; row < gp.maxWorldRow; row++) {
                    String line = br.readLine();
                    String[] numbers = line.split(" ");
                    for (int col = 0; col < gp.maxWorldCol; col++) {
                        mapTileNum[layer][col][row] = Integer.parseInt(numbers[col]);
                    }
                }
            }
            br.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Dessine toutes les tuiles visibles à l'écran pour une couche donnée.
     * Utilise les coordonnées du joueur pour centrer la caméra.
     *
     * @param g2 contexte graphique
     * @param layer couche à dessiner (0 = sol, 1 = décor, 2 = éléments au-dessus du joueur)
     */
    public void draw(Graphics2D g2, int layer) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[layer][worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;

            int screenX = worldX - gp.player.worldx + gp.player.screenX;
            int screenY = worldY - gp.player.worldy + gp.player.screenY;

            // Dessiner uniquement les tuiles visibles dans l'écran
            if (worldX + gp.tileSize > gp.player.worldx - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldx + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldy - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldy + gp.player.screenY) {
                if (tileNum >= 0) {
                    g2.drawImage(tileTypes[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                }
            }

            worldCol++;
            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
