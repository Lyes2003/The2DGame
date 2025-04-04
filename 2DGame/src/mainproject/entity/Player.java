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
 * les collisions avec les tuiles, et les animations (marche, idle et attaque).
 */
public class Player extends Entity {
    private GamePanel gp;

    public GamePanel getGp() {
        return gp;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    private KeyHandler keyH;

    // Position fixe du joueur à l'écran
    public final int screenX;
    public final int screenY;

    // Attributs pour gérer l'attaque
    private String attackDirection = "droite"; // Direction de l'attaque
    private int attackSpeed = 8; // Vitesse spéciale pour l'attaque
    private int attackCounter = 0; // Compteur pour suivre les mouvements d'attaque
    private boolean isAttacking = false; // État pour indiquer si le joueur est en train d'attaquer

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

        // Calcul de la position fixe à l'écran
        screenX = gp.screenWidth / 2 - gp.tileSize / 2;
        screenY = gp.screenHeight / 2 - gp.tileSize / 2;

        // Définir la hitbox pour les collisions
        hitbox = new Rectangle(8, 16, 16, 16);

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
     * Charge tous les sprites d'animation du joueur (marche, idle et attaque) pour chaque direction.
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
            bas1 = ImageIO.read(getClass().getResourceAsStream("/player/row-4-column-1.png"));
            bas2 = ImageIO.read(getClass().getResourceAsStream("/player/row-4-column-2.png"));
            bas3 = ImageIO.read(getClass().getResourceAsStream("/player/row-4-column-3.png"));
            bas4 = ImageIO.read(getClass().getResourceAsStream("/player/row-4-column-4.png"));
            bas5 = ImageIO.read(getClass().getResourceAsStream("/player/row-4-column-5.png"));
            bas6 = ImageIO.read(getClass().getResourceAsStream("/player/row-4-column-6.png"));

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

            // Idle
            idleHaut1 = ImageIO.read(getClass().getResourceAsStream("/player/idle-haut-1.png"));
            idleHaut2 = ImageIO.read(getClass().getResourceAsStream("/player/idle-haut-2.png"));
            idleHaut3 = ImageIO.read(getClass().getResourceAsStream("/player/idle-haut-3.png"));
            idleHaut4 = ImageIO.read(getClass().getResourceAsStream("/player/idle-haut-4.png"));
            idleHaut5 = ImageIO.read(getClass().getResourceAsStream("/player/idle-haut-5.png"));
            idleHaut6 = ImageIO.read(getClass().getResourceAsStream("/player/idle-haut-6.png"));

            idleBas1 = ImageIO.read(getClass().getResourceAsStream("/player/idle-bas-1.png"));
            idleBas2 = ImageIO.read(getClass().getResourceAsStream("/player/idle-bas-2.png"));
            idleBas3 = ImageIO.read(getClass().getResourceAsStream("/player/idle-bas-3.png"));
            idleBas4 = ImageIO.read(getClass().getResourceAsStream("/player/idle-bas-4.png"));
            idleBas5 = ImageIO.read(getClass().getResourceAsStream("/player/idle-bas-5.png"));
            idleBas6 = ImageIO.read(getClass().getResourceAsStream("/player/idle-bas-6.png"));

            idleGauche1 = ImageIO.read(getClass().getResourceAsStream("/player/idle-gauche-1.png"));
            idleGauche2 = ImageIO.read(getClass().getResourceAsStream("/player/idle-gauche-2.png"));
            idleGauche3 = ImageIO.read(getClass().getResourceAsStream("/player/idle-gauche-3.png"));
            idleGauche4 = ImageIO.read(getClass().getResourceAsStream("/player/idle-gauche-4.png"));
            idleGauche5 = ImageIO.read(getClass().getResourceAsStream("/player/idle-gauche-5.png"));
            idleGauche6 = ImageIO.read(getClass().getResourceAsStream("/player/idle-gauche-6.png"));

            idleDroite1 = ImageIO.read(getClass().getResourceAsStream("/player/idle-droite-1.png"));
            idleDroite2 = ImageIO.read(getClass().getResourceAsStream("/player/idle-droite-2.png"));
            idleDroite3 = ImageIO.read(getClass().getResourceAsStream("/player/idle-droite-3.png"));
            idleDroite4 = ImageIO.read(getClass().getResourceAsStream("/player/idle-droite-4.png"));
            idleDroite5 = ImageIO.read(getClass().getResourceAsStream("/player/idle-droite-5.png"));
            idleDroite6 = ImageIO.read(getClass().getResourceAsStream("/player/idle-droite-6.png"));

            // Attaque
            attackHaut1 = ImageIO.read(getClass().getResourceAsStream("/player/row-9-column-1.png"));
            attackHaut2 = ImageIO.read(getClass().getResourceAsStream("/player/row-9-column-2.png"));
            attackHaut3 = ImageIO.read(getClass().getResourceAsStream("/player/row-9-column-3.png"));
            attackHaut4 = ImageIO.read(getClass().getResourceAsStream("/player/row-9-column-4.png"));

            attackBas1 = ImageIO.read(getClass().getResourceAsStream("/player/row-7-column-1.png"));
            attackBas2 = ImageIO.read(getClass().getResourceAsStream("/player/row-7-column-2.png"));
            attackBas3 = ImageIO.read(getClass().getResourceAsStream("/player/row-7-column-3.png"));
            attackBas4 = ImageIO.read(getClass().getResourceAsStream("/player/row-7-column-4.png"));

            attackGauche1 = ImageIO.read(getClass().getResourceAsStream("/player/row-7-column-1.png"));
            attackGauche2 = ImageIO.read(getClass().getResourceAsStream("/player/row-7-column-2.png"));
            attackGauche3 = ImageIO.read(getClass().getResourceAsStream("/player/row-7-column-3.png"));
            attackGauche4 = ImageIO.read(getClass().getResourceAsStream("/player/row-7-column-4.png"));

            attackDroite1 = ImageIO.read(getClass().getResourceAsStream("/player/row-8-column-1.png"));
            attackDroite2 = ImageIO.read(getClass().getResourceAsStream("/player/row-8-column-2.png"));
            attackDroite3 = ImageIO.read(getClass().getResourceAsStream("/player/row-8-column-3.png"));
            attackDroite4 = ImageIO.read(getClass().getResourceAsStream("/player/row-8-column-4.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Met à jour la position du joueur, détecte les collisions et gère les animations.
     * Appelé automatiquement à chaque frame.
     */
    public void update() {
        isMoving = false;

        // Variables pour gérer les mouvements indépendants sur chaque axe
        boolean movingUp = keyH.haut;
        boolean movingDown = keyH.bas;
        boolean movingLeft = keyH.gauche;
        boolean movingRight = keyH.droite;

        // Gestion de l'attaque
        if (keyH.attackDroite && !isAttacking) {
            isAttacking = true;
            attackCounter = 0;
            attackDirection = direction; // La direction de l'attaque correspond à la direction actuelle
        }

        if (isAttacking) {
            if (attackCounter < 15) { // Limiter à 15 mouvements
                speed = attackSpeed;
                attackCounter++;
            } else {
                isAttacking = false;
                keyH.attackDroite = false;
                attackCounter = 0;
                speed = 4;
            }
        }

        double speedX = 0, speedY = 0;
        double diagonalSpeed = speed / Math.sqrt(2); // Réduction de la vitesse pour les diagonales

        // Déterminer les mouvements sur chaque axe
        if (movingUp && !movingDown) speedY -= speed;
        if (movingDown && !movingUp) speedY += speed;
        if (movingLeft && !movingRight) speedX -= speed;
        if (movingRight && !movingLeft) speedX += speed;

        // Ajustement pour les mouvements diagonaux
        if ((movingUp || movingDown) && (movingLeft || movingRight)) {
            speedX *= diagonalSpeed / speed;
            speedY *= diagonalSpeed / speed;
        }

        // Vérification des collisions avec la carte
        collisionOn = false;
        int originalWorldX = worldx;
        int originalWorldY = worldy;

        if (speedX != 0) {
            worldx += speedX;
            gp.collisionChecker.checkTile(this);
            if (collisionOn) worldx = originalWorldX;
        }

        if (speedY != 0) {
            worldy += speedY;
            gp.collisionChecker.checkTile(this);
            if (collisionOn) worldy = originalWorldY;
        }

        // Mettre à jour la direction principale pour l'animation
        if (movingUp && !movingDown) direction = "haut";
        else if (movingDown && !movingUp) direction = "bas";
        if (movingLeft && !movingRight) direction = "gauche";
        else if (movingRight && !movingLeft) direction = "droite";

        isMoving = movingUp || movingDown || movingLeft || movingRight || isAttacking;
        updateAnimation();
    }

    /**
     * Dessine le joueur à l'écran avec le bon sprite selon sa direction et son état (marche, idle ou attaque).
     *
     * @param g2 Contexte graphique utilisé pour le dessin.
     */
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        if (isAttacking) {
            switch (attackDirection) {
                case "haut":
                    switch (attackCounter / 6) {
                        case 0: image = attackHaut1; break;
                        case 1: image = attackHaut2; break;
                        case 2: image = attackHaut3; break;
                        case 3: image = attackHaut4; break;
                        default: image = attackHaut1; break;
                    }
                    break;
                case "bas":
                    switch (attackCounter / 6) {
                        case 0: image = attackBas1; break;
                        case 1: image = attackBas2; break;
                        case 2: image = attackBas3; break;
                        case 3: image = attackBas4; break;
                        default: image = attackBas1; break;
                    }
                    break;
                case "gauche":
                    switch (attackCounter / 6) {
                        case 0: image = attackGauche1; break;
                        case 1: image = attackGauche2; break;
                        case 2: image = attackGauche3; break;
                        case 3: image = attackGauche4; break;
                        default: image = attackGauche1; break;
                    }
                    break;
                case "droite":
                    switch (attackCounter / 6) {
                        case 0: image = attackDroite1; break;
                        case 1: image = attackDroite2; break;
                        case 2: image = attackDroite3; break;
                        case 3: image = attackDroite4; break;
                        default: image = attackDroite1; break;
                    }
                    break;
            }
        } else if (isMoving) {
            switch (direction) {
                case "haut":
                    image = getMovingSprite(haut1, haut2, haut3, haut4, haut5, haut6);
                    break;
                case "bas":
                    image = getMovingSprite(bas1, bas2, bas3, bas4, bas5, bas6);
                    break;
                case "gauche":
                    image = getMovingSprite(gauche1, gauche2, gauche3, gauche4, gauche5, gauche6);
                    break;
                case "droite":
                    image = getMovingSprite(droite1, droite2, droite3, droite4, droite5, droite6);
                    break;
            }
        } else {
            switch (direction) {
                case "haut":
                    image = getIdleSprite(idleHaut1, idleHaut2, idleHaut3, idleHaut4, idleHaut5, idleHaut6);
                    break;
                case "bas":
                    image = getIdleSprite(idleBas1, idleBas2, idleBas3, idleBas4, idleBas5, idleBas6);
                    break;
                case "gauche":
                    image = getIdleSprite(idleGauche1, idleGauche2, idleGauche3, idleGauche4, idleGauche5, idleGauche6);
                    break;
                case "droite":
                    image = getIdleSprite(idleDroite1, idleDroite2, idleDroite3, idleDroite4, idleDroite5, idleDroite6);
                    break;
            }
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }

    /**
     * Retourne le sprite correspondant à l'animation de marche.
     */
    private BufferedImage getMovingSprite(BufferedImage sprite1, BufferedImage sprite2, BufferedImage sprite3, BufferedImage sprite4, BufferedImage sprite5, BufferedImage sprite6) {
        switch (spriteNum) {
            case 1: return sprite1;
            case 2: return sprite2;
            case 3: return sprite3;
            case 4: return sprite4;
            case 5: return sprite5;
            case 6: return sprite6;
            default: return sprite1;
        }
    }

    /**
     * Retourne le sprite correspondant à l'animation idle.
     */
    private BufferedImage getIdleSprite(BufferedImage sprite1, BufferedImage sprite2, BufferedImage sprite3, BufferedImage sprite4, BufferedImage sprite5, BufferedImage sprite6) {
        switch (spriteNum) {
            case 1: return sprite1;
            case 2: return sprite2;
            case 3: return sprite3;
            case 4: return sprite4;
            case 5: return sprite5;
            case 6: return sprite6;
            default: return sprite1;
        }
    }

    /**
     * Gère l’animation du joueur.
     * Incrémente un compteur pour changer de sprite toutes les 12 frames.
     */
    public void updateAnimation() {
        spriteCounter++;
        if (spriteCounter >= 12) {
            spriteNum++;
            if (spriteNum > 6) spriteNum = 1;
            spriteCounter = 0;
        }
    }
}