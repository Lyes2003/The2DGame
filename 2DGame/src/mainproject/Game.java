package mainproject;

import javax.swing.*;

/**
 * Classe principale du jeu.
 * Contient la méthode main qui initialise la fenêtre (JFrame) et démarre le jeu.
 */
public class Game {

    public static void main(String[] args) {

        // Crée une nouvelle fenêtre (fenêtre de jeu)
        JFrame window = new JFrame();

        // Fermer complètement l'application lorsqu'on clique sur la croix ( X )
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Empêche de redimensionner la fenêtre
        window.setResizable(true);

        // Titre de la fenêtre
        window.setTitle("test");

        // Création du panneau de jeu principal
        GamePanel gamePanel = new GamePanel();

        // Ajout du panneau de jeu à la fenêtre
        window.add(gamePanel);

        // Ajuste automatiquement la taille de la fenêtre selon le contenu (GamePanel)
        window.pack();

        // Centre la fenêtre sur l'écran
        window.setLocationRelativeTo(null);

        // Affiche la fenêtre
        window.setVisible(true);

        // Lance la boucle principale du jeu dans un thread séparé
        gamePanel.startGameThread();
    }
}