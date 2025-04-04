package mainproject;

import mainproject.entity.Entity;

import java.awt.*;

/**
 * Cette classe permet de vérifier si une entité (comme le joueur)
 * entre en collision avec des tuiles de la carte.
 * Elle prend en charge plusieurs couches de tuiles (ex: sol et objets hauts).
 */
public class CollisionChecker {

    GamePanel gp;

    /**
     * Constructeur du gestionnaire de collisions.
     * @param gp instance de GamePanel contenant les informations de la carte et du joueur.
     */
    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }
    //verifie la collision entre 2 entity
    /**
     * Vérifie si deux entités entrent en collision.
     *
     * @param entity1 Première entité (par exemple, le joueur).
     * @param entity2 Deuxième entité (par exemple, un PNJ).
     * @return true si les hitboxes des deux entités se chevauchent, sinon false.
     */
    public boolean checkCollision(Entity entity1, Entity entity2) {
        // Vérifier qu'aucune des entités n'est null
        if (entity1 == null || entity2 == null) {
            return false; // Pas de collision possible si une entité est null
        }

        // Calculer les rectangles de collision pour chaque entité
        Rectangle hitbox1 = new Rectangle(
                entity1.worldx + entity1.hitbox.x,
                entity1.worldy + entity1.hitbox.y,
                entity1.hitbox.width,
                entity1.hitbox.height
        );

        Rectangle hitbox2 = new Rectangle(
                entity2.worldx + entity2.hitbox.x,
                entity2.worldy + entity2.hitbox.y,
                entity2.hitbox.width,
                entity2.hitbox.height
        );

        // Vérifier si les rectangles se chevauchent
        boolean isColliding = hitbox1.intersects(hitbox2);
        if (isColliding) {
            System.out.println("Collision détectée entre " + entity1 + " et " + entity2);
        }

        return isColliding;
    }/**
     * Vérifie si l'entité est en collision avec une tuile sur la carte,
     * selon sa direction actuelle et sa vitesse.
     * La détection se fait sur les couches 0 (sol) et 2 (éléments hauts comme les arbres).
     * Si une tuile collisionnable est détectée, le flag collisionOn est activé.
     *
     * @param entity l'entité a vérifié (par exemple, le joueur).
     */
    public void checkTile(Entity entity) {

        // Coordonnées de la hitbox de l'entité dans le monde
        int entityLeftWorldX = entity.worldx + entity.hitbox.x;
        int entityRightWorldX = entity.worldx + entity.hitbox.x + entity.hitbox.width;
        int entityTopWorldY = entity.worldy + entity.hitbox.y;
        int entityBottomWorldY = entity.worldy + entity.hitbox.y + entity.hitbox.height;


        // Vérification selon la direction de déplacement
        switch (entity.direction) {
            case "haut": {
                int leftCol = entityLeftWorldX / gp.tileSize;
                int rightCol = entityRightWorldX / gp.tileSize;
                int topRow = (entityTopWorldY - entity.speed) / gp.tileSize;

                // Vérifie si les coordonnées sont dans les limites
                if (isInBounds(leftCol, topRow) && isInBounds(rightCol, topRow)) {
                    for (int layer : new int[]{0, 2}) { // 0 = sol, 2 = arbres ou murs hauts

                        // Vérifie s'il y a collision sur la gauche ou la droite
                        if (checkCollision(layer, leftCol, topRow) || checkCollision(layer, rightCol, topRow)) {
                            entity.collisionOn = true;
                            break; // inutile de continuer si une collision est détectée
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

                        if (checkCollision(layer, leftCol, bottomRow) || checkCollision(layer, rightCol, bottomRow)) {
                            entity.collisionOn = true;
                            break;
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

                        if (checkCollision(layer, leftCol, topRow) || checkCollision(layer, leftCol, bottomRow)) {
                            entity.collisionOn = true;
                            break;
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

                        if (checkCollision(layer, rightCol, topRow) || checkCollision(layer, rightCol, bottomRow)) {
                            entity.collisionOn = true;
                            break;
                        }


                    }
                }
                break;
            }
        }
    }

    /**
     * Vérifie si les coordonnées de colonne et ligne sont à l'intérieur des limites de la carte.
     * @param col colonne à vérifier
     * @param row ligne à vérifier
     * @return true si les coordonnées sont valides, sinon false
     */
    private boolean isInBounds(int col, int row) {
        return col >= 0 && col < gp.maxWorldCol && row >= 0 && row < gp.maxWorldRow;
    }

    /**
     * Vérifie si une tuile spécifique est collisionnable.
     * Elle ignore les tuiles avec un index négatif (-1).
     *
     * @param layer couche sur laquelle se trouve la tuile (0 = sol, 2 = objets hauts, etc.)
     * @param col colonne de la tuile
     * @param row ligne de la tuile
     * @return true si la tuile bloque le passage, false sinon
     */
    private boolean checkCollision(int layer, int col, int row) {
        int tileNum = gp.tileManager.mapTileNum[layer][col][row];
        return tileNum >= 0 && gp.tileManager.tileTypes[tileNum].collision;
    }

}
