package mainproject;

import mainproject.entity.Entity;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {

        int entityLeftWorldX = entity.worldx + entity.hitbox.x;
        int entityRightWorldX = entity.worldx + entity.hitbox.x + entity.hitbox.width;
        int entityTopWorldY = entity.worldy + entity.hitbox.y;
        int entityBottomWorldY = entity.worldy + entity.hitbox.y + entity.hitbox.height;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "haut": {
                int leftCol = entityLeftWorldX / gp.tileSize;
                int rightCol = entityRightWorldX / gp.tileSize;
                int topRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                if (isInBounds(leftCol, topRow) && isInBounds(rightCol, topRow)) {
                    for (int layer : new int[]{0, 2}) {
                        tileNum1 = gp.tileManager.mapTileNum[layer][leftCol][topRow];
                        tileNum2 = gp.tileManager.mapTileNum[layer][rightCol][topRow];
                        if ((tileNum1 >= 0 && gp.tileManager.tileTypes[tileNum1].collision) ||
                                (tileNum2 >= 0 && gp.tileManager.tileTypes[tileNum2].collision)) {
                            entity.collisionOn = true;
                        }

                    }
                }
                break;
            }
            case "bas": {
                int leftCol = entityLeftWorldX / gp.tileSize;
                int rightCol = entityRightWorldX / gp.tileSize;
                int bottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                if (isInBounds(leftCol, bottomRow) && isInBounds(rightCol, bottomRow)) {
                    for (int layer : new int[]{0, 2}) {
                        tileNum1 = gp.tileManager.mapTileNum[layer][leftCol][bottomRow];
                        tileNum2 = gp.tileManager.mapTileNum[layer][rightCol][bottomRow];
                        if ((tileNum1 >= 0 && gp.tileManager.tileTypes[tileNum1].collision) ||
                                (tileNum2 >= 0 && gp.tileManager.tileTypes[tileNum2].collision)) {
                            entity.collisionOn = true;
                        }

                    }
                }
                break;
            }
            case "gauche": {
                int topRow = entityTopWorldY / gp.tileSize;
                int bottomRow = entityBottomWorldY / gp.tileSize;
                int leftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                if (isInBounds(leftCol, topRow) && isInBounds(leftCol, bottomRow)) {
                    for (int layer : new int[]{0, 2}) {
                        tileNum1 = gp.tileManager.mapTileNum[layer][leftCol][topRow];
                        tileNum2 = gp.tileManager.mapTileNum[layer][leftCol][bottomRow];
                        if ((tileNum1 >= 0 && gp.tileManager.tileTypes[tileNum1].collision) ||
                                (tileNum2 >= 0 && gp.tileManager.tileTypes[tileNum2].collision)) {
                            entity.collisionOn = true;
                        }

                    }
                }
                break;
            }
            case "droite": {
                int topRow = entityTopWorldY / gp.tileSize;
                int bottomRow = entityBottomWorldY / gp.tileSize;
                int rightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                if (isInBounds(rightCol, topRow) && isInBounds(rightCol, bottomRow)) {
                    for (int layer : new int[]{0, 2}) {
                        tileNum1 = gp.tileManager.mapTileNum[layer][rightCol][topRow];
                        tileNum2 = gp.tileManager.mapTileNum[layer][rightCol][bottomRow];
                        if ((tileNum1 >= 0 && gp.tileManager.tileTypes[tileNum1].collision) ||
                                (tileNum2 >= 0 && gp.tileManager.tileTypes[tileNum2].collision)) {
                            entity.collisionOn = true;
                        }

                    }
                }
                break;
            }
        }
    }

    private boolean isInBounds(int col, int row) {
        return col >= 0 && col < gp.maxWorldCol && row >= 0 && row < gp.maxWorldRow;
    }
}
