package mainproject;

import mainproject.entity.PNJ;
import mainproject.ui.ui;
import mainproject.entity.Player;
import mainproject.tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * Panneau principal du jeu.
 * Gère l'affichage, les mises à jour, les dimensions, les événements et la boucle de jeu.
 */
public class GamePanel extends JPanel implements Runnable {

    // Paramètres de base des tuiles et de l'écran
    public final int originalTileSize = 16;    // taille d'origine en pixels
    public final int scale = 3;                // facteur d'agrandissement
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;        // nombre de colonnes à l'écran
    public final int maxScreenRow = 12;        // nombre de lignes à l'écran
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // Paramètres du monde (plus grand que l'écran)
    public final int maxWorldCol = 100;
    public final int maxWorldRow = 100;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;


    // États du jeu
    public final int Start_screen = 0;
    public final int game_is_running = 1;
    public final int settings_screen = 2;
    public int Game_state = 0;

    int fps = 60;

    // Composants du jeu
    public TileManager tileManager = new TileManager(this);
    Thread gameThread;
    KeyHandler keyH = new KeyHandler();
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public Player player = new Player(this, keyH);
    public PNJ pnj = new PNJ(this, player.worldx + tileSize, player.worldy);      // À droite du joueur
    public PNJ pnj1 = new PNJ(this, player.worldx - tileSize, player.worldy);     // À gauche du joueur
    public PNJ pnj2 = new PNJ(this, player.worldx, player.worldy + tileSize);

    ui ui = new ui(this);

    /**
     * Constructeur : initialise l’écran, les écouteurs de clavier et souris.
     */
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);  // améliore le rendu
        this.setFocusable(true);
        this.addKeyListener(keyH);
        pnj1.worldx = tileSize * 19;      // Position pour pnj1
        pnj1.worldy = tileSize * 14;
        pnj2.worldx = tileSize * 25;      // Position pour pnj2
        pnj2.worldy = tileSize * 28;

        // Gestion des clics de souris
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                ui.handleMouseClick(e.getX(), e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                ui.handleMouseReleased();
            }
        });

        // Gestion des mouvements de la souris
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                ui.handleMouseDragged(e.getX(), e.getY());
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                ui.handleMouseMoved(e.getX(), e.getY());
            }
        });
    }

    /**
     * Démarre la boucle du jeu dans un nouveau thread.
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Boucle principale du jeu : contrôle le timing pour 60 FPS, met à jour et redessine le jeu.
     */
    @Override
    public void run() {
        double drawInterval = 1000000000.0 / fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if (delta >= 1) {
                update();      // mise à jour logique du jeu
                repaint();     // redessin du jeu
                delta--;
                drawCount++;
            }

            // Affiche le nombre de FPS chaque seconde
            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    /**
     * Met à jour les éléments du jeu selon l'état courant.
     */
    public void update() {
        if (Game_state == Start_screen) {
            if (keyH.enterPressed) {
                Game_state = game_is_running;
            }
        } else if (Game_state == game_is_running) {
            player.update(); // Mettre à jour le joueur

            // Vérifier les collisions entre le joueur et chaque PNJ
            if (pnj != null && collisionChecker.checkCollision(player, pnj) && player.isAttacking()) {
                System.out.println("Collision avec PNJ !");
                pnj = null; // Supprimer le PNJ ou appeler une méthode die()
            }
            if (pnj1 != null && collisionChecker.checkCollision(player, pnj1) && player.isAttacking()) {
                System.out.println("Collision avec PNJ1 !");
                pnj1 = null; // Supprimer le PNJ ou appeler une méthode die()
            }
            if (pnj2 != null && collisionChecker.checkCollision(player, pnj2) && player.isAttacking()) {
                System.out.println("Collision avec PNJ2 !");
                pnj2 = null; // Supprimer le PNJ ou appeler une méthode die()
            }

            // Mettre à jour les PNJ s'ils existent encore
            if (pnj != null) pnj.update();
            if (pnj1 != null) pnj1.update();
            if (pnj2 != null) pnj2.update();
        }
    }

    /**
     * Dessine tous les éléments graphiques du jeu.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (Game_state == Start_screen || Game_state == settings_screen) {
            ui.draw(g2);
        } else {
            // Dessiner les couches de tuiles
            tileManager.draw(g2, 0); // couche sol
            tileManager.draw(g2, 1); // objets au sol

            // Créer un tableau d'objets Drawable
            Drawable[] drawables = new Drawable[4]; // Taille fixe pour le joueur + 3 PNJ
            int drawableCount = 0;

            // Ajouter le joueur au tableau
            drawables[drawableCount++] = player;

            // Ajouter les PNJ s'ils existent encore
            if (pnj != null) drawables[drawableCount++] = pnj;
            if (pnj1 != null) drawables[drawableCount++] = pnj1;
            if (pnj2 != null) drawables[drawableCount++] = pnj2;

            // Trier les objets par leur position verticale (worldY)
            for (int i = 0; i < drawableCount - 1; i++) {
                for (int j = i + 1; j < drawableCount; j++) {
                    if (drawables[i].getWorldY() > drawables[j].getWorldY()) {
                        // Échanger les deux objets
                        Drawable temp = drawables[i];
                        drawables[i] = drawables[j];
                        drawables[j] = temp;
                    }
                }
            }

            // Dessiner les objets dans l'ordre trié
            for (int i = 0; i < drawableCount; i++) {
                drawables[i].draw(g2);
            }

            // Dessiner les objets au-dessus (arbres, etc.)
            tileManager.draw(g2, 2);
        }

        g2.dispose(); // Libérer les ressources graphiques
    }}