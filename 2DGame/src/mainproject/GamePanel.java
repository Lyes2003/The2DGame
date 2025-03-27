package mainproject;

import mainproject.ui.ui;
import mainproject.entity.Player;
import mainproject.tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class GamePanel extends JPanel implements Runnable {
    public final int originalTileSize = 16;
    public final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // WORLD PARAMETRES
    public final int maxWorldCol = 100;
    public final int maxWorldRow = 100;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;



    public final int Start_screen = 0;
    public final int game_is_running = 1;
    public final int settings_screen = 2;
    public int Game_state = 0;

    int fps = 60;
    public TileManager tileManager = new TileManager(this);
    Thread gameThread;
    KeyHandler keyH = new KeyHandler();
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public Player player = new Player(this, keyH);

    ui ui = new ui(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(keyH);

        // Ajouter un MouseListener pour gérer les clics
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

        // Ajouter un MouseMotionListener pour gérer le glisser-déposer et le survol
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

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

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
                update();
                repaint();
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

    public void update() {
        if (Game_state == Start_screen) {
            if (keyH.enterPressed) {
                Game_state = game_is_running;
            }
        } else if (Game_state == game_is_running) {
            player.update();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (Game_state == Start_screen || Game_state == settings_screen) {
            ui.draw(g2);
        } else {
            tileManager.draw(g2, 0); // sol
            tileManager.draw(g2, 1); // objets au sol (optionnel)
            player.draw(g2);         // joueur
            tileManager.draw(g2, 2); // objets au-dessus (arbres)



        }

        g2.dispose();
    }
}