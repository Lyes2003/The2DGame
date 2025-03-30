package mainproject.entity;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Classe de base représentant une entité du jeu.
 * Elle peut être héritée par des classes comme Player, PNJ, ennemis, etc.
 * Contient toutes les propriétés communes comme position, vitesse, direction, hitbox et animation.
 */
public class Entity {

    /** Coordonnées de l'entité dans le monde (et non sur l'écran) */
    public int worldx, worldy;

    /** Vitesse de déplacement de l'entité */
    public int speed;

    /** Sprites de déplacement (haut, bas, gauche, droite) — 6 frames par direction */
    public BufferedImage haut1, haut2, haut3, haut4, haut5, haut6, bas1, bas2, bas3, bas4, bas5, bas6, gauche1, gauche2, gauche3, gauche4, gauche5, gauche6,
            droite1, droite2, droite3, droite4, droite5, droite6;

    /** Sprites d'animation pour l'état immobile (idle) */
    BufferedImage idleHaut1, idleHaut2, idleHaut3, idleHaut4, idleHaut5, idleHaut6;
    BufferedImage idleBas1, idleBas2, idleBas3, idleBas4, idleBas5, idleBas6;
    BufferedImage idleGauche1, idleGauche2, idleGauche3, idleGauche4, idleGauche5, idleGauche6;
    BufferedImage idleDroite1, idleDroite2, idleDroite3, idleDroite4, idleDroite5, idleDroite6;

    /** Indique si l'entité est actuellement en mouvement */
    boolean isMoving;

    /** Direction actuelle de l'entité : "haut", "bas", "gauche" ou "droite" */
    public String direction;

    /** Compteur pour gérer la fréquence de changement de sprite (animation) */
    public int spriteCounter = 0;

    /** Numéro du sprite courant (de 1 à 6 pour l’animation) */
    public int spriteNum = 1;

    /** Hitbox de l'entité pour la gestion des collisions */
    public Rectangle hitbox;

    /** Si true, l'entité est en collision avec une tuile ou un objet */
    public boolean collisionOn = false;

}
