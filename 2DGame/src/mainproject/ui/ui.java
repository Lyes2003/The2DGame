package mainproject.ui;

import mainproject.GamePanel;

import javax.sound.sampled.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ui {
    GamePanel gp;
    Graphics2D g2;
    Font titleFont;
    Font menuFont;
    private Clip musicClip;
    private BufferedImage background;
    private float volumeLevel = -10.0f; // Volume initial (-10 dB)
    private float brightnessLevel = 1.0f; // Luminosité (1.0 = max, 0.0 = noir)
    private boolean musicEnabled = true; // Musique activée par défaut
    private String language = "fr"; // Langue par défaut : français

    // Boutons et sliders (rectangles cliquables)
    private Rectangle settingsButton;
    private Rectangle backButton;
    private Rectangle volumeSlider;
    private Rectangle volumeSliderKnob;
    private Rectangle brightnessSlider;
    private Rectangle brightnessSliderKnob;
    private Rectangle musicToggleButton;
    private Rectangle languageButton;

    // Positions pour le slider
    private int volumeSliderMin = -80; // Volume min (dB)
    private int volumeSliderMax = 0; // Volume max (dB)
    private int brightnessSliderMin = 0; // Luminosité min (0%)
    private int brightnessSliderMax = 100; // Luminosité max (100%)
    private boolean draggingVolumeKnob = false;
    private boolean draggingBrightnessKnob = false;

    public ui(GamePanel gp) {
        this.gp = gp;
        loadFonts();
        if (titleFont == null) {
            titleFont = new Font("Arial", Font.BOLD, 72);
        }
        if (menuFont == null) {
            menuFont = new Font("Arial", Font.PLAIN, 24);
        }
        loadMusic();
        loadBackground();

        // Initialiser les boutons et sliders
        settingsButton = new Rectangle(gp.screenWidth / 2 - 100, gp.screenHeight * 3 / 4 + 40, 200, 40);
        backButton = new Rectangle(gp.screenWidth / 2 - 100, gp.screenHeight * 3 / 4 + 40, 200, 40);
        volumeSlider = new Rectangle(gp.screenWidth / 2 - 50, gp.screenHeight / 2 - 60, 100, 10);
        volumeSliderKnob = new Rectangle(0, 0, 16, 20);
        brightnessSlider = new Rectangle(gp.screenWidth / 2 - 50, gp.screenHeight / 2, 100, 10);
        brightnessSliderKnob = new Rectangle(0, 0, 16, 20);
        musicToggleButton = new Rectangle(gp.screenWidth / 2 - 100, gp.screenHeight / 2 + 60, 200, 40);
        languageButton = new Rectangle(gp.screenWidth / 2 - 100, gp.screenHeight / 2 + 120, 200, 40);
        updateSliderKnobs();
    }

    private void loadFonts() {
        try {
            Font baseFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/Minecraftia-Regular.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(baseFont);
            titleFont = baseFont.deriveFont(72f);
            menuFont = baseFont.deriveFont(24f);
            System.out.println("Police Minecraftia chargée avec succès");
        } catch (FontFormatException e) {
            System.err.println("Erreur de format de la police : " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la police : " + e.getMessage());
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("Le fichier /fonts/Minecraftia-Regular.ttf n'a pas été trouvé dans les ressources");
            e.printStackTrace();
        }
    }

    private void loadMusic() {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(
                    getClass().getResourceAsStream("/audio/Trap.wav")
            );
            System.out.println("Tentative de chargement de la musique : /audio/Trap.wav");
            musicClip = AudioSystem.getClip();
            musicClip.open(audioStream);
            updateVolume();
            System.out.println("Musique chargée avec succès");
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de la musique : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadBackground() {
        try {
            background = ImageIO.read(getClass().getResourceAsStream("/tiles/buisson.png"));
            System.out.println("Fond d'écran chargé avec succès");
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fond : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateVolume() {
        if (musicClip != null) {
            FloatControl gainControl = (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volumeLevel);
        }
    }

    private void updateSliderKnobs() {
        // Position du knob pour le volume
        float volumeRange = volumeSliderMax - volumeSliderMin;
        float volumePosition = (volumeLevel - volumeSliderMin) / volumeRange;
        int volumeKnobX = volumeSlider.x + (int) (volumePosition * (volumeSlider.width - volumeSliderKnob.width));
        volumeSliderKnob.x = volumeKnobX;
        volumeSliderKnob.y = volumeSlider.y - 5;

        // Position du knob pour la luminosité
        float brightnessRange = brightnessSliderMax - brightnessSliderMin;
        float brightnessPosition = ((brightnessLevel * 100) - brightnessSliderMin) / brightnessRange;
        int brightnessKnobX = brightnessSlider.x + (int) (brightnessPosition * (brightnessSlider.width - brightnessSliderKnob.width));
        brightnessSliderKnob.x = brightnessKnobX;
        brightnessSliderKnob.y = brightnessSlider.y - 5;
    }

    public void playMusic() {
        if (musicClip == null || !musicEnabled) {
            System.out.println("Musique désactivée ou clip non initialisé");
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
        } else if (gp.Game_state == gp.settings_screen) {
            drawSettingsScreen();
            playMusic();
        } else {
            stopMusic();
        }

        // Appliquer l'effet de luminosité (overlay)
        if (brightnessLevel < 1.0f) {
            g2.setColor(new Color(0, 0, 0, (int) ((1.0f - brightnessLevel) * 255)));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        }
    }

    public void drawTitleScreen() {
        if (background != null) {
            for (int y = 0; y < gp.screenHeight; y += gp.tileSize) {
                for (int x = 0; x < gp.screenWidth; x += gp.tileSize) {
                    g2.drawImage(background, x, y, gp.tileSize, gp.tileSize, null);
                }
            }
        } else {
            g2.setColor(new Color(50, 50, 50));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        }

        g2.setFont(titleFont);
        String title = language.equals("fr") ? "Gorilla" : "Gorilla";
        int x = getXforCenteredText(title);
        int y = gp.screenHeight / 4;

        g2.setColor(Color.BLACK);
        g2.drawString(title, x + 4, y + 4);

        g2.setColor(new Color(200, 200, 200));
        g2.drawString(title, x, y);

        g2.setFont(menuFont);
        String startText = language.equals("fr") ? "Appuyez sur ENTER pour jouer" : "Press ENTER to Play";
        x = getXforCenteredText(startText);
        y = gp.screenHeight * 3 / 4;

        g2.setColor(Color.BLACK);
        g2.drawString(startText, x + 2, y + 2);

        g2.setColor(new Color(220, 220, 220));
        g2.drawString(startText, x, y);

        // Dessiner le bouton "Paramètres"
        g2.setColor(new Color(150, 150, 150));
        g2.fill(settingsButton);
        g2.setColor(Color.BLACK);
        g2.draw(settingsButton);
        String settingsText = language.equals("fr") ? "Paramètres" : "Settings";
        g2.setFont(menuFont);
        x = getXforCenteredText(settingsText, settingsButton);
        y = settingsButton.y + (settingsButton.height + g2.getFontMetrics().getAscent()) / 2 - 5; // Centrer verticalement
        g2.setColor(Color.WHITE);
        g2.drawString(settingsText, x, y);
    }

    public void drawSettingsScreen() {
        if (background != null) {
            for (int y = 0; y < gp.screenHeight; y += gp.tileSize) {
                for (int x = 0; x < gp.screenWidth; x += gp.tileSize) {
                    g2.drawImage(background, x, y, gp.tileSize, gp.tileSize, null);
                }
            }
        } else {
            g2.setColor(new Color(50, 50, 50));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        }

        // Fond semi-transparent pour le menu
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRect(gp.screenWidth / 2 - 250, gp.screenHeight / 2 - 200, 500, 400);

        // Titre "Paramètres"
        g2.setFont(titleFont);
        String title = language.equals("fr") ? "Paramètres" : "Settings";
        int x = getXforCenteredText(title);
        int y = gp.screenHeight / 4;

        g2.setColor(Color.BLACK);
        g2.drawString(title, x + 4, y + 4);

        g2.setColor(new Color(200, 200, 200));
        g2.drawString(title, x, y);

        g2.setFont(menuFont);

        // Volume Slider
        String volumeText = language.equals("fr") ? "Volume :" : "Volume:";
        x = gp.screenWidth / 2 - 100;
        y = volumeSlider.y + 100;
        g2.setColor(Color.WHITE);
        g2.drawString(volumeText, x, y);
        g2.setColor(new Color(100, 100, 100));
        g2.fill(volumeSlider);
        g2.setColor(Color.BLACK);
        g2.draw(volumeSlider);
        g2.setColor(new Color(200, 200, 200));
        g2.fill(volumeSliderKnob);
        g2.setColor(Color.BLACK);
        g2.draw(volumeSliderKnob);

        // Brightness Slider
        String brightnessText = language.equals("fr") ? "Luminosité :" : "Brightness:";
        x = gp.screenWidth / 2 - 200;
        y = brightnessSlider.y + 5;
        g2.setColor(Color.WHITE);
        g2.drawString(brightnessText, x, y);
        g2.setColor(new Color(100, 100, 100));
        g2.fill(brightnessSlider);
        g2.setColor(Color.BLACK);
        g2.draw(brightnessSlider);
        g2.setColor(new Color(200, 200, 200));
        g2.fill(brightnessSliderKnob);
        g2.setColor(Color.BLACK);
        g2.draw(brightnessSliderKnob);

        // Music Toggle Button
        g2.setColor(new Color(150, 150, 150));
        g2.fill(musicToggleButton);
        g2.setColor(Color.BLACK);
        g2.draw(musicToggleButton);
        String musicToggleText = language.equals("fr") ? "Musique : " + (musicEnabled ? "ON" : "OFF") : "Music: " + (musicEnabled ? "ON" : "OFF");
        x = getXforCenteredText(musicToggleText, musicToggleButton);
        y = musicToggleButton.y + (musicToggleButton.height + g2.getFontMetrics().getAscent()) / 2 - 5; // Centrer verticalement
        g2.setColor(Color.WHITE);
        g2.drawString(musicToggleText, x, y);

        // Language Button
        g2.setColor(new Color(150, 150, 150));
        g2.fill(languageButton);
        g2.setColor(Color.BLACK);
        g2.draw(languageButton);
        String languageText = language.equals("fr") ? "Langue : Français" : "Language: English";
        x = getXforCenteredText(languageText, languageButton);
        y = languageButton.y + (languageButton.height + g2.getFontMetrics().getAscent()) / 2 - 5; // Centrer verticalement
        g2.setColor(Color.WHITE);
        g2.drawString(languageText, x, y);

        // Bouton "Retour"
        g2.setColor(new Color(150, 150, 150));
        g2.fill(backButton);
        g2.setColor(Color.BLACK);
        g2.draw(backButton);
        String backText = language.equals("fr") ? "Retour" : "Back";
        x = getXforCenteredText(backText, backButton);
        y = backButton.y + (backButton.height + g2.getFontMetrics().getAscent()) / 2 - 5; // Centrer verticalement
        g2.setColor(Color.WHITE);
        g2.drawString(backText, x, y);
    }

    public void handleMouseClick(int x, int y) {
        if (gp.Game_state == gp.Start_screen) {
            if (settingsButton.contains(x, y)) {
                gp.Game_state = gp.settings_screen;
            }
        } else if (gp.Game_state == gp.settings_screen) {
            if (volumeSliderKnob.contains(x, y)) {
                draggingVolumeKnob = true;
            } else if (brightnessSliderKnob.contains(x, y)) {
                draggingBrightnessKnob = true;
            } else if (musicToggleButton.contains(x, y)) {
                musicEnabled = !musicEnabled;
                if (musicEnabled) {
                    playMusic();
                } else {
                    stopMusic();
                }
            } else if (languageButton.contains(x, y)) {
                language = language.equals("fr") ? "en" : "fr";
            } else if (backButton.contains(x, y)) {
                gp.Game_state = gp.Start_screen;
            }
        }
    }

    public void handleMouseDragged(int x, int y) {
        if (draggingVolumeKnob) {
            // Ajuster le volume en fonction de la position de la souris
            int newX = Math.max(volumeSlider.x, Math.min(x - volumeSliderKnob.width / 2, volumeSlider.x + volumeSlider.width - volumeSliderKnob.width));
            volumeSliderKnob.x = newX;
            float position = (float) (newX - volumeSlider.x) / (volumeSlider.width - volumeSliderKnob.width);
            volumeLevel = volumeSliderMin + position * (volumeSliderMax - volumeSliderMin);
            updateVolume();
            System.out.println("Volume ajusté à : " + volumeLevel + " dB");
        }
        if (draggingBrightnessKnob) {
            // Ajuster la luminosité en fonction de la position de la souris
            int newX = Math.max(brightnessSlider.x, Math.min(x - brightnessSliderKnob.width / 2, brightnessSlider.x + brightnessSlider.width - brightnessSliderKnob.width));
            brightnessSliderKnob.x = newX;
            float position = (float) (newX - brightnessSlider.x) / (brightnessSlider.width - brightnessSliderKnob.width);
            brightnessLevel = (brightnessSliderMin + position * (brightnessSliderMax - brightnessSliderMin)) / 100.0f;
            System.out.println("Luminosité ajustée à : " + (brightnessLevel * 100) + "%");
        }
    }

    public void handleMouseReleased() {
        draggingVolumeKnob = false;
        draggingBrightnessKnob = false;
    }

    private int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }

    private int getXforCenteredText(String text, Rectangle rect) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return rect.x + (rect.width - length) / 2;
    }
}