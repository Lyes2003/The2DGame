package mainproject.ui;

import mainproject.GamePanel;

import javax.sound.sampled.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;

public class ui {
    GamePanel gp;
    Graphics2D g2;
    Font titleFont;
    Font menuFont;
    private Clip musicClip;
    private BufferedImage backgroundSky;
    private BufferedImage backgroundMountains;
    private BufferedImage backgroundGround;
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

    // Parallax scrolling
    private double skyOffset = 0;
    private double mountainsOffset = 0;
    private double groundOffset = 0;

    // Particules
    private List<Particle> particles = new ArrayList<>();
    private Random random = new Random();

    // Effet de survol
    private boolean settingsButtonHovered = false;

    // Effet de tremblement pour le titre
    private double titleShakeTimer = 0;
    private int titleShakeX = 0;
    private int titleShakeY = 0;

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
        settingsButton = new Rectangle(gp.screenWidth / 2 - 100, gp.screenHeight / 2 + 50, 200, 40);
        backButton = new Rectangle(gp.screenWidth / 2 - 100, gp.screenHeight / 2 + 180, 200, 40);
        volumeSlider = new Rectangle(gp.screenWidth / 2 + 50, gp.screenHeight / 2 - 60, 150, 10);
        volumeSliderKnob = new Rectangle(0, 0, 16, 20);
        brightnessSlider = new Rectangle(gp.screenWidth / 2 + 50, gp.screenHeight / 2 - 20, 150, 10);
        brightnessSliderKnob = new Rectangle(0, 0, 16, 20);
        musicToggleButton = new Rectangle(gp.screenWidth / 2 - 100, gp.screenHeight / 2 + 60, 200, 40);
        languageButton = new Rectangle(gp.screenWidth / 2 - 100, gp.screenHeight / 2 + 120, 200, 40);
        updateSliderKnobs();

        // Initialiser les particules
        for (int i = 0; i < 20; i++) {
            particles.add(new Particle());
        }
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
            backgroundSky = ImageIO.read(getClass().getResourceAsStream("/tiles/sky.png"));
            backgroundMountains = ImageIO.read(getClass().getResourceAsStream("/tiles/mountains.png"));
            backgroundGround = ImageIO.read(getClass().getResourceAsStream("/tiles/sol_vert.png"));
            System.out.println("Fonds d'écran chargés avec succès");
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des fonds : " + e.getMessage());
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
        float volumeRange = volumeSliderMax - volumeSliderMin;
        float volumePosition = (volumeLevel - volumeSliderMin) / volumeRange;
        int volumeKnobX = volumeSlider.x + (int) (volumePosition * (volumeSlider.width - volumeSliderKnob.width));
        volumeSliderKnob.x = volumeKnobX;
        volumeSliderKnob.y = volumeSlider.y - 5;

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

        // Mettre à jour les animations
        updateParallax();
        updateParticles();
        updateTitleShake();

        if (gp.Game_state == gp.Start_screen) {
            drawTitleScreen();
            playMusic();
        } else if (gp.Game_state == gp.settings_screen) {
            drawSettingsScreen();
            playMusic();
        } else {
            stopMusic();
        }

        if (brightnessLevel < 1.0f) {
            g2.setColor(new Color(0, 0, 0, (int) ((1.0f - brightnessLevel) * 255)));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        }
    }

    private void updateParallax() {
        skyOffset -= 0.1; // Défilement lent pour le ciel
        mountainsOffset -= 0.3; // Défilement moyen pour les montagnes
        groundOffset -= 0.5; // Défilement rapide pour le sol

        if (skyOffset < -gp.screenWidth) skyOffset = 0;
        if (mountainsOffset < -gp.screenWidth) mountainsOffset = 0;
        if (groundOffset < -gp.screenWidth) groundOffset = 0;
    }

    private void updateParticles() {
        for (Particle particle : particles) {
            particle.update();
            if (particle.y > gp.screenHeight) {
                particle.reset();
            }
        }
    }

    private void updateTitleShake() {
        titleShakeTimer += 0.1;
        if (titleShakeTimer >= 1) {
            titleShakeX = random.nextInt(5) - 2; // Tremblement entre -2 et 2 pixels
            titleShakeY = random.nextInt(5) - 2;
            titleShakeTimer = 0;
        }
    }

    public void drawTitleScreen() {
        // Dessiner le fond avec parallax
        if (backgroundSky != null) {
            g2.drawImage(backgroundSky, (int) skyOffset, 0, gp.screenWidth, gp.screenHeight, null);
            g2.drawImage(backgroundSky, (int) skyOffset + gp.screenWidth, 0, gp.screenWidth, gp.screenHeight, null);
        }
        if (backgroundMountains != null) {
            g2.drawImage(backgroundMountains, (int) mountainsOffset, gp.screenHeight / 2 - 100, gp.screenWidth, 200, null);
            g2.drawImage(backgroundMountains, (int) mountainsOffset + gp.screenWidth, gp.screenHeight / 2 - 100, gp.screenWidth, 200, null);
        }
        if (backgroundGround != null) {
            for (int x = (int) groundOffset; x < gp.screenWidth; x += gp.tileSize) {
                for (int y = gp.screenHeight - gp.tileSize; y < gp.screenHeight; y += gp.tileSize) {
                    g2.drawImage(backgroundGround, x, y, gp.tileSize, gp.tileSize, null);
                }
            }
            for (int x = (int) groundOffset + gp.screenWidth; x < gp.screenWidth + gp.tileSize; x += gp.tileSize) {
                for (int y = gp.screenHeight - gp.tileSize; y < gp.screenHeight; y += gp.tileSize) {
                    g2.drawImage(backgroundGround, x, y, gp.tileSize, gp.tileSize, null);
                }
            }
        }

        // Dessiner les particules
        for (Particle particle : particles) {
            particle.draw(g2);
        }

        // Dessiner le titre avec effet de tremblement
        g2.setFont(titleFont);
        String title = language.equals("fr") ? "Gorilla" : "Gorilla";
        int x = getXforCenteredText(title) + titleShakeX;
        int y = gp.screenHeight / 4 + titleShakeY;

        g2.setColor(Color.BLACK);
        g2.drawString(title, x + 4, y + 4);

        g2.setColor(new Color(200, 200, 200));
        g2.drawString(title, x, y);

        // Dessiner le texte "Appuyez sur ENTER pour jouer" (sans bouton, juste du texte)
        g2.setFont(menuFont);
        String startText = language.equals("fr") ? "Appuyez sur ENTER pour jouer" : "Press ENTER to Play";
        x = getXforCenteredText(startText);
        y = gp.screenHeight / 2;

        g2.setColor(Color.BLACK);
        g2.drawString(startText, x + 2, y + 2);

        g2.setColor(new Color(220, 220, 220));
        g2.drawString(startText, x, y);

        // Dessiner le bouton "Paramètres" avec effet de survol
        drawButton(settingsButton, language.equals("fr") ? "Paramètres" : "Settings", settingsButtonHovered);
    }

    public void drawSettingsScreen() {
        if (backgroundGround != null) {
            for (int y = 0; y < gp.screenHeight; y += gp.tileSize) {
                for (int x = 0; x < gp.screenWidth; x += gp.tileSize) {
                    g2.drawImage(backgroundGround, x, y, gp.tileSize, gp.tileSize, null);
                }
            }
        } else {
            g2.setColor(new Color(50, 50, 50));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        }

        // Fond semi-transparent pour le menu avec une bordure
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRect(gp.screenWidth / 2 - 300, gp.screenHeight / 2 - 200, 600, 400);
        g2.setColor(new Color(100, 100, 100));
        g2.drawRect(gp.screenWidth / 2 - 300, gp.screenHeight / 2 - 200, 600, 400);

        // Titre "Paramètres"
        g2.setFont(titleFont);
        String title = language.equals("fr") ? "Paramètres" : "Settings";
        int x = getXforCenteredText(title);
        int y = gp.screenHeight / 4 - 20;

        g2.setColor(Color.BLACK);
        g2.drawString(title, x + 4, y + 4);

        g2.setColor(new Color(200, 200, 200));
        g2.drawString(title, x, y);

        g2.setFont(menuFont);

        // Volume Slider
        String volumeText = language.equals("fr") ? "Volume :" : "Volume:";
        x = gp.screenWidth / 2 - 250;
        y = volumeSlider.y + 5;
        g2.setColor(Color.WHITE);
        g2.drawString(volumeText, x, y);
        drawSlider(volumeSlider, volumeSliderKnob);

        // Brightness Slider
        String brightnessText = language.equals("fr") ? "Luminosité :" : "Brightness:";
        x = gp.screenWidth / 2 - 250;
        y = brightnessSlider.y + 5;
        g2.setColor(Color.WHITE);
        g2.drawString(brightnessText, x, y);
        drawSlider(brightnessSlider, brightnessSliderKnob);

        // Music Toggle Button
        drawButton(musicToggleButton, language.equals("fr") ? "Musique : " + (musicEnabled ? "ON" : "OFF") : "Music: " + (musicEnabled ? "ON" : "OFF"), false);

        // Language Button
        drawButton(languageButton, language.equals("fr") ? "Langue : Français" : "Language: English", false);

        // Bouton "Retour"
        drawButton(backButton, language.equals("fr") ? "Retour" : "Back", false);
    }

    private void drawButton(Rectangle button, String text, boolean hovered) {
        // Effet 3D style Minecraft
        g2.setColor(new Color(120, 120, 120)); // Gris foncé (ombre)
        g2.fillRect(button.x + 2, button.y + 2, button.width, button.height);
        g2.setColor(hovered ? new Color(200, 200, 200) : new Color(180, 180, 180)); // Gris clair (base), plus clair si survol
        g2.fillRect(button.x, button.y, button.width, button.height);
        g2.setColor(new Color(100, 100, 100)); // Bordure
        g2.drawRect(button.x, button.y, button.width, button.height);

        // Texte centré
        g2.setFont(menuFont);
        int x = getXforCenteredText(text, button);
        int y = button.y + (button.height + g2.getFontMetrics().getAscent()) / 2 - 5;
        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 2, y + 2);
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
    }

    private void drawSlider(Rectangle slider, Rectangle knob) {
        g2.setColor(new Color(80, 80, 80)); // Gris foncé (ombre)
        g2.fillRect(slider.x + 2, slider.y + 2, slider.width, slider.height);
        g2.setColor(new Color(120, 120, 120)); // Gris moyen (base)
        g2.fillRect(slider.x, slider.y, slider.width, slider.height);
        g2.setColor(new Color(60, 60, 60)); // Bordure
        g2.drawRect(slider.x, slider.y, slider.width, slider.height);

        g2.setColor(new Color(150, 150, 150)); // Gris clair (ombre)
        g2.fillRect(knob.x + 2, knob.y + 2, knob.width, knob.height);
        g2.setColor(new Color(200, 200, 200)); // Gris très clair (base)
        g2.fillRect(knob.x, knob.y, knob.width, knob.height);
        g2.setColor(new Color(100, 100, 100)); // Bordure
        g2.drawRect(knob.x, knob.y, knob.width, knob.height);
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
            int newX = Math.max(volumeSlider.x, Math.min(x - volumeSliderKnob.width / 2, volumeSlider.x + volumeSlider.width - volumeSliderKnob.width));
            volumeSliderKnob.x = newX;
            float position = (float) (newX - volumeSlider.x) / (volumeSlider.width - volumeSliderKnob.width);
            volumeLevel = volumeSliderMin + position * (volumeSliderMax - volumeSliderMin);
            updateVolume();
            System.out.println("Volume ajusté à : " + volumeLevel + " dB");
        }
        if (draggingBrightnessKnob) {
            int newX = Math.max(brightnessSlider.x, Math.min(x - brightnessSliderKnob.width / 2, brightnessSlider.x + brightnessSlider.width - brightnessSliderKnob.width));
            brightnessSliderKnob.x = newX;
            float position = (float) (newX - brightnessSlider.x) / (brightnessSlider.width - brightnessSliderKnob.width);
            brightnessLevel = (brightnessSliderMin + position * (brightnessSliderMax - brightnessSliderMin)) / 100.0f;
            System.out.println("Luminosité ajustée à : " + (brightnessLevel * 100) + "%");
        }
    }

    public void handleMouseMoved(int x, int y) {
        settingsButtonHovered = settingsButton.contains(x, y);
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

    // Classe pour gérer les particules
    private class Particle {
        float x, y;
        float speedX, speedY;
        int size;

        Particle() {
            reset();
        }

        void reset() {
            x = random.nextInt(gp.screenWidth);
            y = -10;
            speedX = random.nextFloat() * 2 - 1; // Entre -1 et 1
            speedY = random.nextFloat() * 2 + 1; // Entre 1 et 3
            size = random.nextInt(5) + 3; // Taille entre 3 et 8
        }

        void update() {
            x += speedX;
            y += speedY;
        }

        void draw(Graphics2D g2) {
            g2.setColor(new Color(255, 255, 255, 150)); // Blanc semi-transparent
            g2.fillRect((int) x, (int) y, size, size);
        }
    }
}