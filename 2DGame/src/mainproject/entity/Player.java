package mainproject.entity;

import mainproject.GamePanel;
import mainproject.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;

    public int x, y;        // Position du joueur
    public int speed;       // Vitesse du joueur

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "bas";
    }

    public void getPlayerImage() {

        try {
            haut1 = ImageIO.read(getClass().getResourceAsStream("/player/row-6-column-1.png"));
            haut2 = ImageIO.read(getClass().getResourceAsStream("/player/row-6-column-2.png"));
            haut3 = ImageIO.read(getClass().getResourceAsStream("/player/row-6-column-3.png"));
            haut4 = ImageIO.read(getClass().getResourceAsStream("/player/row-6-column-4.png"));
            haut5 = ImageIO.read(getClass().getResourceAsStream("/player/row-6-column-5.png"));
            haut6 = ImageIO.read(getClass().getResourceAsStream("/player/row-6-column-6.png"));
            bas1  = ImageIO.read(getClass().getResourceAsStream("/player/row-4-column-1.png"));
            bas2  = ImageIO.read(getClass().getResourceAsStream("/player/row-4-column-2.png"));
            bas3  = ImageIO.read(getClass().getResourceAsStream("/player/row-4-column-3.png"));
            bas4  = ImageIO.read(getClass().getResourceAsStream("/player/row-4-column-4.png"));
            bas5  = ImageIO.read(getClass().getResourceAsStream("/player/row-4-column-5.png"));
            bas6  = ImageIO.read(getClass().getResourceAsStream("/player/row-4-column-6.png"));
            gauche1 = ImageIO.read(getClass().getResourceAsStream("/player/row-5-column-1.png"));
            gauche2 = ImageIO.read(getClass().getResourceAsStream("/player/row-5-column-2.png"));
            gauche3 = ImageIO.read(getClass().getResourceAsStream("/player/row-5-column-3.png"));
            gauche4 = ImageIO.read(getClass().getResourceAsStream("/player/row-5-column-4.png"));
            gauche5 = ImageIO.read(getClass().getResourceAsStream("/player/row-5-column-5.png"));
            gauche6 = ImageIO.read(getClass().getResourceAsStream("/player/row-5-column-6.png"));
            droite1 = ImageIO.read(getClass().getResourceAsStream("/player/row-5-column-1-droite.png"));
            droite2 = ImageIO.read(getClass().getResourceAsStream("/player/row-5-column-2-droite.png"));
            droite3 = ImageIO.read(getClass().getResourceAsStream("/player/row-5-column-3-droite.png"));
            droite4 = ImageIO.read(getClass().getResourceAsStream("/player/row-5-column-4-droite.png"));
            droite5 = ImageIO.read(getClass().getResourceAsStream("/player/row-5-column-5-droite.png"));
            droite6 = ImageIO.read(getClass().getResourceAsStream("/player/row-5-column-6-droite.png"));

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        if (keyH.haut == true || keyH.bas == true || keyH.droite == true || keyH.gauche == true) {

            if (keyH.haut) {

                direction = "haut";
                y -= speed;
            }
            else if (keyH.bas) {

                direction = "bas";
                y += speed;
            }
            else if (keyH.gauche) {

                direction = "gauche";
                x -= speed;
            }
            else if (keyH.droite) {

                direction = "droite";
                x += speed;
            }

            spriteCounter++;
            if(spriteCounter >= 12) {
                if(spriteNum == 1) {
                    spriteNum = 2;
                }
                else if (spriteNum == 2) {
                    spriteNum = 3;
                }else if (spriteNum == 3) {
                    spriteNum = 4;
                }else if (spriteNum == 4) {
                    spriteNum = 5;
                }else if (spriteNum == 5) {
                    spriteNum = 6;
                }else if (spriteNum == 6) {
                    spriteNum = 1;
                }

                spriteCounter = 0;
            }


        }
    }

    public void draw(Graphics2D g2) {
        //.setColor(Color.white);
        // g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;


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

        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}