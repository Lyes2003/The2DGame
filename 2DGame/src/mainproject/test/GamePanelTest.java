package mainproject.test;

/**
 * ╔══════════════════════════════════════════════════════╗
 * ║                   CLASSE GAMEPANEL_TEST              ║
 * ╚══════════════════════════════════════════════════════╝
 * Cette classe est dédiée aux tests unitaires de la classe GamePanel.
 * Elle teste les fonctionnalités principales du panneau de jeu,
 * y compris l'initialisation, la boucle de jeu, la gestion des états,
 * les interactions utilisateur, et le dessin.
 *
 * La classe inclut :
 * - Des méthodes de test pour chaque fonctionnalité clé de GamePanel.
 * - Une méthode main pour exécuter tous les tests.
 */
import java.awt.image.BufferedImage;
import mainproject.GamePanel;
import mainproject.ui.ui;
import mainproject.entity.Player;
import mainproject.tile.TileManager;

import mainproject.KeyHandler;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class GamePanelTest {
    // ╔══════════════════════════════════════╗
    // ║           CHAMPS PRINCIPAUX          ║
    // ╚══════════════════════════════════════╝
    // Ces champs sont utilisés pour configurer l'environnement de test.

    /** Instance de GamePanel à tester. */
    private static GamePanel gp;

    // ╔══════════════════════════════════════╗
    // ║           MÉTHODES DE TEST           ║
    // ╚══════════════════════════════════════╝
    // Ces méthodes testent chaque fonctionnalité clé de la classe GamePanel.

    /**
     * ╔═══════════════════════╗
     * ║TEST : Initialization  ║
     * ╚═══════════════════════╝
     * Teste l'initialisation des dimensions et des constantes de GamePanel.
     * Vérifie que screenWidth, screenHeight, tileSize, et les états sont correctement définis.
     */
    public void testInitialization() {
        System.out.println("┌──── TEST : GamePanel.Initialization ────┐");

        try {
            // Vérifie les dimensions et constantes
            if (gp.screenWidth == 768 && gp.screenHeight == 576 && gp.tileSize == 48) {
                System.out.println("✅ Test réussi : Dimensions correctement initialisées");
            } else {
                System.out.println("❌ Test échoué : Dimensions incorrectes");
            }

            // Vérifie les états du jeu
            if (gp.Start_screen == 0 && gp.game_is_running == 1 && gp.settings_screen == 2 && gp.Game_state == 0) {
                System.out.println("✅ Test réussi : États du jeu correctement initialisés");
            } else {
                System.out.println("❌ Test échoué : États du jeu incorrects");
            }

            // Vérifie les composants (TileManager, KeyHandler, Player, ui)
            Field tileManagerField = GamePanel.class.getDeclaredField("tileManager");
            tileManagerField.setAccessible(true);
            TileManager tileManager = (TileManager) tileManagerField.get(gp);

            Field keyHField = GamePanel.class.getDeclaredField("keyH");
            keyHField.setAccessible(true);
            KeyHandler keyH = (KeyHandler) keyHField.get(gp);

            Field playerField = GamePanel.class.getDeclaredField("player");
            playerField.setAccessible(true);
            Player player = (Player) playerField.get(gp);

            Field uiField = GamePanel.class.getDeclaredField("ui");
            uiField.setAccessible(true);
            ui ui = (ui) uiField.get(gp);

            if (tileManager != null && keyH != null && player != null && ui != null) {
                System.out.println("✅ Test réussi : Composants (tileManager, keyH, player, ui) correctement initialisés");
            } else {
                System.out.println("❌ Test échoué : Un ou plusieurs composants sont null");
            }
        } catch (Exception e) {
            System.out.println("❌ Test échoué : Erreur lors de l'initialisation - " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("└───────────────────────────────────────────┘");
    }

    /**
     * ╔══════════════════════╗
     * ║    TEST : StartGameThread  ║
     * ╚══════════════════════╝
     * Teste la méthode startGameThread() pour vérifier si le thread de jeu est correctement démarré.
     */
    public void testStartGameThread() {
        System.out.println("┌──── TEST : GamePanel.startGameThread ────┐");

        try {
            // Démarre le thread de jeu
            gp.startGameThread();

            // Récupère le champ gameThread
            Field gameThreadField = GamePanel.class.getDeclaredField("gameThread");
            gameThreadField.setAccessible(true);
            Thread gameThread = (Thread) gameThreadField.get(gp);

            // Vérifie que le thread est démarré
            if (gameThread != null && gameThread.isAlive()) {
                System.out.println("✅ Test réussi : Thread de jeu démarré correctement");

                // Arrête le thread pour éviter une boucle infinie pendant les tests
                gameThreadField.set(gp, null);
            } else {
                System.out.println("❌ Test échoué : Thread de jeu non démarré");
            }
        } catch (Exception e) {
            System.out.println("❌ Test échoué : Erreur lors du démarrage du thread - " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("└───────────────────────────────────────────┘");
    }

    /**
     * ╔══════════════════════╗
     * ║    TEST : Update  ║
     * ╚══════════════════════╝
     * Teste la méthode update() pour vérifier la gestion des transitions d'état et la mise à jour du joueur.
     */
    public void testUpdate() {
        System.out.println("┌──── TEST : GamePanel.update ────┐");

        try {
            // Test 1 : Transition de Start_screen à game_is_running
            gp.Game_state = gp.Start_screen;

            // Simule l'appui sur la touche Enter
            Field keyHField = GamePanel.class.getDeclaredField("keyH");
            keyHField.setAccessible(true);
            KeyHandler keyH = (KeyHandler) keyHField.get(gp);
            Field enterPressedField = KeyHandler.class.getDeclaredField("enterPressed");
            enterPressedField.setAccessible(true);
            enterPressedField.setBoolean(keyH, true);

            // Appelle la méthode update
            gp.update();

            // Vérifie que l'état a changé
            if (gp.Game_state == gp.game_is_running) {
                System.out.println("✅ Test réussi : Transition de Start_screen à game_is_running");
            } else {
                System.out.println("❌ Test échoué : Échec de la transition de Start_screen à game_is_running");
            }

            // Test 2 : Mise à jour du joueur dans l'état game_is_running
            Field playerField = GamePanel.class.getDeclaredField("player");
            playerField.setAccessible(true);
            Player player = (Player) playerField.get(gp);

            // Simule un appel à update() dans l'état game_is_running
            gp.Game_state = gp.game_is_running;
            gp.update();

            // Note : Sans connaître la méthode Player.update(), on vérifie simplement qu'aucune exception n'est levée
            System.out.println("✅ Test réussi : Mise à jour du joueur dans game_is_running sans erreur");
        } catch (Exception e) {
            System.out.println("❌ Test échoué : Erreur lors de la mise à jour - " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("└───────────────────────────────────────────┘");
    }

    /**
     * ╔══════════════════════╗
     * ║    TEST : MouseEvents  ║
     * ╚══════════════════════╝
     * Teste la gestion des événements de souris (clics, survol, glisser-déposer).
     * Simule des événements de souris et vérifie que les méthodes de ui sont appelées.
     */
    public void testMouseEvents() {
        System.out.println("┌──── TEST : GamePanel.MouseEvents ────┐");

        try {
            // Récupère l'instance de ui
            Field uiField = GamePanel.class.getDeclaredField("ui");
            uiField.setAccessible(true);
            ui ui = (ui) uiField.get(gp);

            // Test 1 : Simule un clic de souris
            Method handleMouseClickMethod = ui.getClass().getDeclaredMethod("handleMouseClick", int.class, int.class);
            handleMouseClickMethod.setAccessible(true);
            handleMouseClickMethod.invoke(ui, 400, 300); // Simule un clic au centre de l'écran

            System.out.println("✅ Test réussi : Clic de souris géré sans erreur");

            // Test 2 : Simule un survol de souris
            Method handleMouseMovedMethod = ui.getClass().getDeclaredMethod("handleMouseMoved", int.class, int.class);
            handleMouseMovedMethod.setAccessible(true);
            handleMouseMovedMethod.invoke(ui, 400, 300); // Simule un survol au centre de l'écran

            // Vérifie que settingsButtonHovered a été mis à jour (si le bouton est survolé)
            Field settingsButtonHoveredField = ui.getClass().getDeclaredField("settingsButtonHovered");
            settingsButtonHoveredField.setAccessible(true);
            boolean settingsButtonHovered = settingsButtonHoveredField.getBoolean(ui);

            if (settingsButtonHovered) {
                System.out.println("✅ Test réussi : Survol de souris détecté");
            } else {
                System.out.println("❌ Test échoué : Survol de souris non détecté");
            }

            // Test 3 : Simule un glisser-déposer
            Method handleMouseDraggedMethod = ui.getClass().getDeclaredMethod("handleMouseDragged", int.class, int.class);
            handleMouseDraggedMethod.setAccessible(true);
            handleMouseDraggedMethod.invoke(ui, 400, 300); // Simule un glisser-déposer

            System.out.println("✅ Test réussi : Glisser-déposer géré sans erreur");

            // Test 4 : Simule un relâchement de souris
            Method handleMouseReleasedMethod = ui.getClass().getDeclaredMethod("handleMouseReleased");
            handleMouseReleasedMethod.setAccessible(true);
            handleMouseReleasedMethod.invoke(ui);

            System.out.println("✅ Test réussi : Relâchement de souris géré sans erreur");
        } catch (Exception e) {
            System.out.println("❌ Test échoué : Erreur lors de la gestion des événements de souris - " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("└───────────────────────────────────────────┘");
    }

    /**
     * ╔══════════════════════╗
     * ║    TEST : PaintComponent  ║
     * ╚══════════════════════╝
     * Teste la méthode paintComponent() pour vérifier le dessin dans différents états.
     * Simule un contexte graphique et appelle la méthode.
     */
    public void testPaintComponent() {
        System.out.println("┌──── TEST : GamePanel.paintComponent ────┐");

        try {
            // Crée un contexte graphique simulé
            BufferedImage image = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = image.createGraphics();

            // Test 1 : Dessin dans l'état Start_screen
            gp.Game_state = gp.Start_screen;
            gp.paintComponent(g2);

            System.out.println("✅ Test réussi : Dessin dans Start_screen sans erreur");

            // Test 2 : Dessin dans l'état game_is_running
            gp.Game_state = gp.game_is_running;
            gp.paintComponent(g2);

            System.out.println("✅ Test réussi : Dessin dans game_is_running sans erreur");

            // Test 3 : Dessin dans l'état settings_screen
            gp.Game_state = gp.settings_screen;
            gp.paintComponent(g2);

            System.out.println("✅ Test réussi : Dessin dans settings_screen sans erreur");

            // Libère le contexte graphique
            g2.dispose();
        } catch (Exception e) {
            System.out.println("❌ Test échoué : Erreur lors du dessin - " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("└───────────────────────────────────────────┘");
    }

    // ╔══════════════════════════════════════╗
    // ║           MÉTHODE MAIN               ║
    // ╚══════════════════════════════════════╝
    // Cette méthode exécute tous les tests définis ci-dessus.

    /**
     * ╔══════════════════════╗
     * ║    MÉTHODE : main    ║
     * ╚══════════════════════╝
     * Point d'entrée principal pour exécuter tous les tests de la classe GamePanel.
     * Configure l'environnement de test et appelle chaque méthode de test.
     *
     * @param args Arguments de la ligne de commande (non utilisés).
     */
    public static void main(String[] args) {
        System.out.println("══════ DÉBUT DES TESTS DE LA CLASSE GAMEPANEL ══════");

        // Configure l'environnement de test
        gp = new GamePanel();

        // Crée une instance de GamePanelTest pour exécuter les tests
        GamePanelTest tester = new GamePanelTest();

        // Exécute tous les tests
        tester.testInitialization();
        tester.testStartGameThread();
        tester.testUpdate();
        tester.testMouseEvents();
        tester.testPaintComponent();

        System.out.println("══════ FIN DES TESTS DE LA CLASSE GAMEPANEL ══════");
    }
}