package mainproject;
import mainproject.ui.ui;
import mainproject.entity.Player;
import mainproject.tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // 1. TAILLE DE L'ÉCRAN
    public final int originalTileSize = 16; // Taille de base du tile (16x16)
    public final int scale = 3;             // Zoom

    public final int tileSize = originalTileSize * scale; // 48x48 pixels
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
    public final int Start_screen = 0; // ecran si le jeu n'est toujours pas lancé
    public int Game_state = 0; // le jeu n'est pas lancé par defaut
    public final int game_is_running = 1; // le jeu est lancé
    ui ui=new ui(this);


    int fps = 60; // Frames par seconde
    TileManager tileManager = new TileManager(this);

    // 2. BOUCLE DE JEU
    Thread gameThread;
    KeyHandler keyH = new KeyHandler(); // Gestionnaire des entrées clavier
    Player player = new Player(this, keyH); // Joueur du jeu
    // initialisation de la map


    // 3. Constructeur
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // améliore les performances de rendu
        this.setFocusable(true); // pour recevoir les entrées clavier
        this.addKeyListener(keyH); // ajouter le gestionnaire d'entrées clavier
    }

    // 4. Démarrer le thread de jeu
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // 5. Boucle principale du jeu
    @Override
    public void run() {
        double drawInterval = 1000000000.0 / fps; // 60 FPS
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
                update();      // Logique du jeu
                repaint();     // Dessin
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    // 6. Mettre à jour le jeu
    public void update() {
        if (Game_state == Start_screen) {
            if (keyH.enterPressed) {
                Game_state = game_is_running;
            }
        } else {
            player.update();
        }
    }

    // 7. Dessiner à l'écran
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (Game_state == Start_screen) {
            ui.draw(g2);
        } else {
            // Dessiner la map
            tileManager.draw(g2);
            // dessiner le joueur
            player.draw(g2);
        }

        g2.dispose();
    }
}
