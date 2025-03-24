package mainproject.entity;

import java.awt.image.BufferedImage;

public class Entity {

    public int x, y;
    public int speed;

    public BufferedImage haut1, haut2, bas1, bas2, gauche1, gauche2, droite1, droite2;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

}
