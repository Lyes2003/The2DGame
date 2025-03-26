package mainproject;

import mainproject.entity.Entity;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {

        int entityLeftWorldX = entity.worldx +entity.hitbox.x;
        int entityRightWorldX = entity.worldx + entity.hitbox.x + entity.hitbox.width;
        int entityTopWorldy = entity.worldy + entity.hitbox.y;
        int entityBottomWorldy = entity.worldy + entity.hitbox.y + entity.hitbox.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldy / gp.tileSize;
        int entityBottomRow = entityBottomWorldy / gp.tileSize;

        int tileNum1, tileNum2;

        switch(entity.direction){
            case "haut" :
                entityTopRow = (entityTopWorldy - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];
                if(gp.tileManager.tileTypes[tileNum1].collision == true || gp.tileManager.tileTypes[tileNum2].collision == true ){
                    entity.collisionOn = true;
                }
                break;
            case "bas" :
                entityBottomRow = (entityBottomWorldy + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];
                if(gp.tileManager.tileTypes[tileNum1].collision == true || gp.tileManager.tileTypes[tileNum2].collision == true ){
                    entity.collisionOn = true;
                }
                break;
            case "gauche" :
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                if(gp.tileManager.tileTypes[tileNum1].collision == true || gp.tileManager.tileTypes[tileNum2].collision == true ){
                    entity.collisionOn = true;
                }
                break;
            case "droite" :
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];
                if(gp.tileManager.tileTypes[tileNum1].collision == true || gp.tileManager.tileTypes[tileNum2].collision == true ){
                    entity.collisionOn = true;
                }
                break;
        }

    }
}
