package mainproject.ui;

import mainproject.GamePanel;

import javax.sound.sampled.*;
import java.awt.*;
import java.io.IOException;

public class ui {
    GamePanel gp;
    Graphics2D g2;
    Font titleFont;
    private Clip musicClip;

    public ui(GamePanel gp) {
        this.gp = gp;
        titleFont = new Font("Arial", Font.BOLD, 96);
        loadMusic();
    }

    private void loadMusic() {
        try {
            // Charger le fichier audio depuis les ressources
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(
                    getClass().getResourceAsStream("/audio/Trap.wav")
            );
            System.out.println("Tentative de chargement de la musique : /audio/Trap.wav");
            musicClip = AudioSystem.getClip();
            musicClip.open(audioStream);
            FloatControl gainControl = (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f); // Réduit le volume de 10 dB
            System.out.println("Musique chargée avec succès");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Erreur lors du chargement de la musique : " + e.getMessage());
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("Le fichier /audio/Trap.wav n'a pas été trouvé dans les ressources");
            e.printStackTrace();
        }
    }

    public void playMusic() {
        if (musicClip == null) {
            System.out.println("Le clip audio n'a pas été initialisé");
            return;
        }
        if (!musicClip.isRunning()) {
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
            musicClip.start();
            System.out.println("Musique démarrée");
        }
    }

    public void stopMusic() {
        if (musicClip != null && musicClip.isRunning()) {
            musicClip.stop();
            musicClip.setFramePosition(0);
        }
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        if (gp.Game_state == gp.Start_screen) {
            drawTitleScreen();
            playMusic();
        } else {
            stopMusic();
        }
    }

    public void drawTitleScreen() {
        g2.setColor(new Color(70, 120, 80));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setFont(titleFont);
        String text = "Gorilla";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/3;

        g2.setColor(Color.gray);
        g2.drawString(text, x + 5, y + 5);

        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
        String startText = "Chargement du jeu a 40%";
        x = getXforCenteredText(startText);
        y = gp.screenHeight * 2/3;

        g2.setColor(Color.white);
        g2.drawString(startText, x, y);
    }

    private int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth/2 - length/2;
    }
}