package mainproject.entity;

import mainproject.GamePanel;
import mainproject.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Classe représentant le joueur dans le jeu.
 * Elle gère sa position dans le monde, sa direction, son déplacement,
 * les collisions avec les tuiles, et les animations (marche et idle).
 */
public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;

    // Position fixe du joueur à l'écran (le monde bouge autour de lui)
    public final int screenX; // indique ou dessiné le joueur sur l'axe des X
    public final int screenY; // indique ou dessiné le joueur sur l'axe des y


    /**
     * Constructeur du joueur.
     * Initialise la position à l'écran, la hitbox, les valeurs par défaut et charge les images.
     *
     * @param gp   Référence au panneau principal du jeu.
     * @param keyH Gestionnaire des touches clavier.
     */
    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - gp.tileSize / 2;
        screenY = gp.screenHeight / 2 - gp.tileSize / 2;

        // Définir la hitbox pour les collisions :
        hitbox = new Rectangle();
        hitbox.x = 8;
        hitbox.y = 16;
        hitbox.width = 16;
        hitbox.height = 16;

        setDefaultValues();
        getPlayerImage();
    }

    /**
     * Initialise la position de départ du joueur dans le monde, sa vitesse et sa direction initiale.
     */
    public void setDefaultValues() {
        worldx = gp.tileSize * 23;
        worldy = gp.tileSize * 21;
        speed = 4;
        direction = "bas";
    }

    /**
     * Charge tous les sprites d'animation du joueur (marche et idle) pour chaque direction.
     */
    public void getPlayerImage() {

        try {
            // Haut
            haut1 = ImageIO.read(getClass().getResourceAsStream("/player/row-6-column-1.png"));
            haut2 = ImageIO.read(getClass().getResourceAsStream("/player/row-6-column-2.png"));
            haut3 = ImageIO.read(getClass().getResourceAsStream("/player/row-6-column-3.png"));
            haut4 = ImageIO.read(getClass().getResourceAsStream("/player/row-6-column-4.png"));
            haut5 = ImageIO.read(getClass().getResourceAsStream("/player/row-6-column-5.png"));
            haut6 = ImageIO.read(getClass().getResourceAsStream("/player/row-6-column-6.png"));

            // Bas
            bas1  = ImageIO.read(getClass().getResourceAsStream("/player/row-4-column-1.png"));
            bas2  = ImageIO.read(getClass().getResourceAsStream("/player/row-4-column-2.png"));
            bas3  = ImageIO.read(getClass().getResourceAsStream("/player/row-4-column-3.png"));
            bas4  = ImageIO.read(getClass().getResourceAsStream("/player/row-4-column-4.png"));
            bas5  = ImageIO.read(getClass().getResourceAsStream("/player/row-4-column-5.png"));
            bas6  = ImageIO.read(getClass().getResourceAsStream("/player/row-4-column-6.png"));

            // Gauche
            gauche1 = ImageIO.read(getClass().getResourceAsStream("/player/row-5-column-1-gauche.png"));
            gauche2 = ImageIO.read(getClass().getResourceAsStream("/player/row-5-column-2-gauche.png"));
            gauche3 = ImageIO.read(getClass().getResourceAsStream("/player/row-5-column-3-gauche.png"));
            gauche4 = ImageIO.read(getClass().getResourceAsStream("/player/row-5-column-4-gauche.png"));
            gauche5 = ImageIO.read(getClass().getResourceAsStream("/player/row-5-column-5-gauche.png"));
            gauche6 = ImageIO.read(getClass().getResourceAsStream("/player/row-5-column-6-gauche.png"));

            // Droite
            droite1 = ImageIO.read(getClass().getResourceAsStream("/player/row-5-column-1.png"));
            droite2 = ImageIO.read(getClass().getResourceAsStream("/player/row-5-column-2.png"));
            droite3 = ImageIO.read(getClass().getResourceAsStream("/player/row-5-column-3.png"));
            droite4 = ImageIO.read(getClass().getResourceAsStream("/player/row-5-column-4.png"));
            droite5 = ImageIO.read(getClass().getResourceAsStream("/player/row-5-column-5.png"));
            droite6 = ImageIO.read(getClass().getResourceAsStream("/player/row-5-column-6.png"));

            // Sprites idle :

            // idle haute
            idleHaut1 = ImageIO.read(getClass().getResourceAsStream("/player/idle-haut-1.png"));
            idleHaut2 = ImageIO.read(getClass().getResourceAsStream("/player/idle-haut-2.png"));
            idleHaut3 = ImageIO.read(getClass().getResourceAsStream("/player/idle-haut-3.png"));
            idleHaut4 = ImageIO.read(getClass().getResourceAsStream("/player/idle-haut-4.png"));
            idleHaut5 = ImageIO.read(getClass().getResourceAsStream("/player/idle-haut-5.png"));
            idleHaut6 = ImageIO.read(getClass().getResourceAsStream("/player/idle-haut-6.png"));

            // idle bas
            idleBas1 = ImageIO.read(getClass().getResourceAsStream("/player/idle-bas-1.png"));
            idleBas2 = ImageIO.read(getClass().getResourceAsStream("/player/idle-bas-2.png"));
            idleBas3 = ImageIO.read(getClass().getResourceAsStream("/player/idle-bas-3.png"));
            idleBas4 = ImageIO.read(getClass().getResourceAsStream("/player/idle-bas-4.png"));
            idleBas5 = ImageIO.read(getClass().getResourceAsStream("/player/idle-bas-5.png"));
            idleBas6 = ImageIO.read(getClass().getResourceAsStream("/player/idle-bas-6.png"));

            // idle gauche
            idleGauche1 = ImageIO.read(getClass().getResourceAsStream("/player/idle-gauche-1.png"));
            idleGauche2 = ImageIO.read(getClass().getResourceAsStream("/player/idle-gauche-2.png"));
            idleGauche3 = ImageIO.read(getClass().getResourceAsStream("/player/idle-gauche-3.png"));
            idleGauche4 = ImageIO.read(getClass().getResourceAsStream("/player/idle-gauche-4.png"));
            idleGauche5 = ImageIO.read(getClass().getResourceAsStream("/player/idle-gauche-5.png"));
            idleGauche6 = ImageIO.read(getClass().getResourceAsStream("/player/idle-gauche-6.png"));

            //idle droite
            idleDroite1 = ImageIO.read(getClass().getResourceAsStream("/player/idle-droite-1.png"));
            idleDroite2 = ImageIO.read(getClass().getResourceAsStream("/player/idle-droite-2.png"));
            idleDroite3 = ImageIO.read(getClass().getResourceAsStream("/player/idle-droite-3.png"));
            idleDroite4 = ImageIO.read(getClass().getResourceAsStream("/player/idle-droite-4.png"));
            idleDroite5 = ImageIO.read(getClass().getResourceAsStream("/player/idle-droite-5.png"));
            idleDroite6 = ImageIO.read(getClass().getResourceAsStream("/player/idle-droite-6.png"));


        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Met à jour la position du joueur, détecte les collisions et gère les animations.
     * Appelé automatiquement à chaque frame.
     */
    public void update() {
        isMoving = false; // Par défaut, on suppose que le joueur est immobile

        // Si le joueur appuie sur une touche directionnelle
        if (keyH.haut == true || keyH.bas == true || keyH.droite == true || keyH.gauche == true) {
            isMoving = true;

            // Mettre à jour la direction
            if (keyH.haut) {

                direction = "haut";
            }
            else if (keyH.bas) {

                direction = "bas";
            }
            else if (keyH.gauche) {

                direction = "gauche";
            }
            else if (keyH.droite) {

                direction = "droite";
            }

            // Vérification des collisions avec la carte
            collisionOn = false;
            gp.collisionChecker.checkTile(this);

            // Déplacer le joueur si aucune collision
            if(collisionOn == false) {

                switch(direction) {
                    case "haut":
                        worldy -= speed;
                        break;
                    case "bas":
                        worldy += speed;
                        break;
                    case "gauche":
                        worldx -= speed;
                        break;
                    case "droite":
                        worldx += speed;
                        break;
                }
            }

        }
        // Toujours faire tourner l'animation, même immobile
        updateAnimation();
    }

    /**
     * Dessine le joueur à l'écran avec le bon sprite selon sa direction et son état (marche/immobile).
     *
     * @param g2 Contexte graphique utilisé pour le dessin.
     */
    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        if (isMoving) {
            // Afficher sprite de déplacement
            switch(direction) {
                case "haut":
                    if(spriteNum == 1) {
                        image = haut1;
                    }
                    if(spriteNum == 2) {
                        image = haut2;
                    }
                    if(spriteNum == 3) {
                        image = haut3;
                    }
                    if(spriteNum == 4) {
                        image = haut4;
                    }
                    if(spriteNum == 5) {
                        image = haut5;
                    }
                    if(spriteNum == 6) {
                        image = haut6;
                    }
                    break;
                case "bas":
                    if(spriteNum == 1) {
                        image = bas1;
                    }
                    if(spriteNum == 2) {
                        image = bas2;
                    }
                    if(spriteNum == 3) {
                        image = bas3;
                    }
                    if(spriteNum == 4) {
                        image = bas4;
                    }
                    if(spriteNum == 5) {
                        image = bas5;
                    }
                    if(spriteNum == 6) {
                        image = bas6;
                    }
                    break;
                case "gauche":
                    if(spriteNum == 1) {
                        image = gauche1;
                    }
                    if(spriteNum == 2) {
                        image = gauche2;
                    }
                    if(spriteNum == 3) {
                        image = gauche3;
                    }
                    if(spriteNum == 4) {
                        image = gauche4;
                    }
                    if(spriteNum == 5) {
                        image = gauche5;
                    }
                    if(spriteNum == 6) {
                        image = gauche6;
                    }
                    break;
                case "droite":
                    if(spriteNum == 1) {
                        image = droite1;
                    }
                    if(spriteNum == 2) {
                        image = droite2;
                    }
                    if(spriteNum == 3) {
                        image = droite3;
                    }
                    if(spriteNum == 4) {
                        image = droite4;
                    }
                    if(spriteNum == 5) {
                        image = droite5;
                    }
                    if(spriteNum == 6) {
                        image = droite6;
                    }
                    break;
            }
        }else{
            // Afficher sprite idle
            switch(direction) {
                case "haut":
                    if(spriteNum == 1) {
                        image = idleHaut1;
                    }
                    if(spriteNum == 2) {
                        image = idleHaut2;
                    }
                    if(spriteNum == 3) {
                        image = idleHaut3;
                    }
                    if(spriteNum == 4) {
                        image = idleHaut4;
                    }
                    if(spriteNum == 5) {
                        image = idleHaut5;
                    }
                    if(spriteNum == 6) {
                        image = idleHaut6;
                    }
                    break;
                case "bas":
                    if(spriteNum == 1) {
                        image = idleBas1;
                    }
                    if(spriteNum == 2) {
                        image = idleBas2;
                    }
                    if(spriteNum == 3) {
                        image = idleBas3;
                    }
                    if(spriteNum == 4) {
                        image = idleBas4;
                    }
                    if(spriteNum == 5) {
                        image = idleBas5;
                    }
                    if(spriteNum == 6) {
                        image = idleBas6;
                    }
                    break;
                case "gauche":
                    if(spriteNum == 1) {
                        image = idleGauche1;
                    }
                    if(spriteNum == 2) {
                        image = idleGauche2;
                    }
                    if(spriteNum == 3) {
                        image = idleGauche3;
                    }
                    if(spriteNum == 4) {
                        image = idleGauche4;
                    }
                    if(spriteNum == 5) {
                        image = idleGauche5;
                    }
                    if(spriteNum == 6) {
                        image = idleGauche6;
                    }
                    break;
                case "droite":
                    if(spriteNum == 1) {
                        image = idleDroite1;
                    }
                    if(spriteNum == 2) {
                        image = idleDroite2;
                    }
                    if(spriteNum == 3) {
                        image = idleDroite3;
                    }
                    if(spriteNum == 4) {
                        image = idleDroite4;
                    }
                    if(spriteNum == 5) {
                        image = idleDroite5;
                    }
                    if(spriteNum == 6) {
                        image = idleDroite6;
                    }
                    break;
            }
        }

        // Dessiner le joueur à l’écran (toujours centré à screenX/screenY)
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        System.out.println("DRAW - isMoving = " + isMoving + ", direction = " + direction + ", spriteNum = " + spriteNum);

    }

    /**
     * Gère l’animation du joueur.
     * Incrémente un compteur pour changer de sprite toutes les 12 frames.
     */
    public void updateAnimation() {
        spriteCounter++;

        if (spriteCounter >= 12) { // délai entre les frames
            spriteNum++;
            if (spriteNum > 6) {
                spriteNum = 1; // boucle de 1 à 6
            }
            spriteCounter = 0;
        }
        System.out.println("spriteNum = " + spriteNum);
    }
}