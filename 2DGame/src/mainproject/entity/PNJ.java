package mainproject.entity;

import mainproject.GamePanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * Classe représentant un PNJ (Personnage Non-Joueur).
 * Il peut se déplacer de manière autonome ou rester immobile.
 */
public class PNJ extends Entity {
    GamePanel gp;

    /**
     * Constructeur du PNJ
     */
    public PNJ(GamePanel gp, int startX, int startY) {
        this.gp = gp;
        this.worldx = startX;
        this.worldy = startY;
        this.speed = 1; // Vitesse par défaut
        this.direction = "bas"; // Direction par défaut
        getPNJImage(); // Charger les images
    }

    /**
     * Charge les images du PNJ
     */
    public void getPNJImage() {
        try {
            bas1 = ImageIO.read(getClass().getResourceAsStream("/pnj/bas1.png"));
            haut1 = ImageIO.read(getClass().getResourceAsStream("/pnj/haut1.png"));
            gauche1 = ImageIO.read(getClass().getResourceAsStream("/pnj/gauche1.png"));
            droite1 = ImageIO.read(getClass().getResourceAsStream("/pnj/droite1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Met à jour le PNJ (déplacement automatique ou statique)
     */
    public void update() {
        spriteCounter++;

        if (spriteCounter > 100) { // Change de direction toutes les 60 frames
            int random = (int) (Math.random() * 4); // Choisit une direction au hasard

            switch (random) {
                case 0:
                    direction = "haut";
                    break;
                case 1:
                    direction = "bas";
                    break;
                case 2:
                    direction = "gauche";
                    break;
                case 3:
                    direction = "droite";
                    break;
            }

            spriteCounter = 0;
        }

        // Déplacer le PNJ selon la direction choisie
        if (direction.equals("haut")) worldy -= speed;
        if (direction.equals("bas")) worldy += speed;
        if (direction.equals("gauche")) worldx -= speed;
        if (direction.equals("droite")) worldx += speed;
    }

    /**
     * Dessine le PNJ
     */
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "haut":
                image = haut1;
                break;
            case "bas":
                image = bas1;
                break;
            case "gauche":
                image = gauche1;
                break;
            case "droite":
                image = droite1;
                break;
        }

        int screenX = worldx - gp.player.worldx + gp.player.screenX;
        int screenY = worldy - gp.player.worldy + gp.player.screenY;

        // Dessiner uniquement si le PNJ est visible à l’écran
        if (screenX + gp.tileSize > 0 && screenX < gp.screenWidth &&
                screenY + gp.tileSize > 0 && screenY < gp.screenHeight) {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
