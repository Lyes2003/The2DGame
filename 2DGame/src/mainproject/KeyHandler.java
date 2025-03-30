package mainproject;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Classe qui gère les entrées clavier du joueur.
 * Met à jour des flags booléens selon les touches pressées.
 */
public class KeyHandler implements KeyListener {

    public boolean haut, bas, gauche, droite;  // touches de déplacement
    public boolean enterPressed;               // touche de validation (menu)

    /**
     * Non utilisé ici, mais obligatoire à implémenter.
     */
    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * Appelé lorsqu'une touche est pressée.
     * Active les directions selon les touches ZQSD ou flèches.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) haut = true;
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) bas = true;
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) gauche = true;
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) droite = true;
        if (code == KeyEvent.VK_ENTER || code ==KeyEvent.VK_SPACE)enterPressed=true;

    }

    /**
     * Appelé lorsqu'une touche est relâchée.
     * Désactive les directions quand le joueur relâche les touches.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) haut = false;
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) bas = false;
        if (code == KeyEvent.VK_A ||  code == KeyEvent.VK_LEFT) gauche = false;
        if (code == KeyEvent.VK_D ||  code == KeyEvent.VK_RIGHT) droite = false;
    }
}