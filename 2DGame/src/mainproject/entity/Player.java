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
            haut1 = ImageIO.read(getClass().getResourceAsStream(null));
            haut2 = ImageIO.read(getClass().getResourceAsStream(null));
            bas1  = ImageIO.read(getClass().getResourceAsStream(null));
            bas2  = ImageIO.read(getClass().getResourceAsStream(null));
            gauche1 = ImageIO.read(getClass().getResourceAsStream(null));
            gauche2 = ImageIO.read(getClass().getResourceAsStream(null));
            droite1 = ImageIO.read(getClass().getResourceAsStream(null));
            droite2 = ImageIO.read(getClass().getResourceAsStream(null));

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
                    spriteNum = 1;
                }

                spriteCounter = 0;
        }


        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;


        switch(direction) {
            case "haut":
                if(spriteNum == 1) {
                    image = haut1;
                }
                if(spriteNum == 2) {
                    image = haut2;
                }
                break;
            case "bas":
                if(spriteNum == 1) {
                    image = bas1;
                }
                if(spriteNum == 2) {
                    image = bas2;
                }
                break;
            case "gauche":
                if(spriteNum == 1) {
                    image = gauche1;
                }
                if(spriteNum == 2) {
                    image = gauche2;
                }
                break;
            case "droite":
                if(spriteNum == 1) {
                    image = droite1;
                }
                if(spriteNum == 2) {
                    image = droite2;
                }
                break;
        }

        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}