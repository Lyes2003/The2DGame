package mainproject.entity;

import mainproject.GamePanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * Classe représentant un PNJ (Personnage Non-Joueur).
 * Il peut se déplacer de manière autonome ou attaquer le joueur s'il entre dans un certain rayon.
 */
public class PNJ extends Entity {
    GamePanel gp;

    // Rayon d'aggro (distance à laquelle le PNJ détecte le joueur)
    private int aggroRange = 100; // En pixels (ajustez selon vos besoins)

    // Variables pour gérer l'attaque
    private boolean isAggro = false; // Indique si le PNJ est en mode aggro (poursuite/attaque)
    private int attackDamage = 10; // Dégâts infligés par attaque
    private int attackCooldown = 60; // Délai entre deux attaques (en frames, 60 frames = 1 seconde à 60 FPS)
    private int attackCounter = 0; // Compteur pour gérer le cooldown

    {
        this.hitbox = new Rectangle();
        this.hitbox.x = 8;
        this.hitbox.y = 16;
        this.hitbox.width = 16;
        this.hitbox.height = 16;
    }

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
     * Calcule la distance entre le PNJ et le joueur
     */
    private double getDistanceToPlayer() {
        double dx = worldx - gp.player.worldx;
        double dy = worldy - gp.player.worldy;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Vérifie si le joueur est dans le rayon d'aggro
     */
    private boolean isPlayerInAggroRange() {
        return getDistanceToPlayer() <= aggroRange;
    }

    /**
     * Fait attaquer le PNJ
     */
    private void attackPlayer() {
        // Vérifier si le cooldown de l'attaque est terminé
        if (attackCounter >= attackCooldown) {
            // Infliger des dégâts au joueur
            gp.player.takeDamage(attackDamage);
            System.out.println("PNJ attaque le joueur ! Dégâts infligés : " + attackDamage + ", Vie du joueur : " + gp.player.currentHealth);
            attackCounter = 0; // Réinitialiser le cooldown
        }
    }

    /**
     * Met à jour le PNJ (déplacement automatique ou attaque)
     */
    public void update() {
        // Incrémenter le compteur d'attaque
        attackCounter++;

        // Vérifier si le joueur est dans le rayon d'aggro
        isAggro = isPlayerInAggroRange();

        if (isAggro) {
            // Si le joueur est dans le rayon, attaquer
            attackPlayer();

            // Faire face au joueur
            double dx = gp.player.worldx - worldx;
            double dy = gp.player.worldy - worldy;
            if (Math.abs(dx) > Math.abs(dy)) {
                // Priorité à l'axe X
                if (dx > 0) {
                    direction = "droite";
                } else {
                    direction = "gauche";
                }
            } else {
                // Priorité à l'axe Y
                if (dy > 0) {
                    direction = "bas";
                } else {
                    direction = "haut";
                }
            }
        } else {
            // Si le joueur n'est pas dans le rayon, continuer le mouvement aléatoire
            spriteCounter++;

            if (spriteCounter > 100) { // Change de direction toutes les 100 frames
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
    }

    /**
     * Dessine le PNJ
     */
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        // Utiliser les sprites existants, même en mode aggro
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

            // Optionnel : Dessiner le rayon d'aggro pour le débogage
            if (isAggro) {
                g2.setColor(Color.RED);
                g2.drawOval(screenX + gp.tileSize / 2 - aggroRange, screenY + gp.tileSize / 2 - aggroRange, aggroRange * 2, aggroRange * 2);
            }
        }
    }
}