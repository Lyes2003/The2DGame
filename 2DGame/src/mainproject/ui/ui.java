/**
 * ╔══════════════════════════════════════════════════════╗
 * ║                   CLASSE UI                          ║
 * ╚══════════════════════════════════════════════════════╝
 * Cette classe gère l'interface utilisateur (UI) du jeu, incluant :
 * - L'écran titre (Start_screen) avec un fond parallax, des particules, un personnage animé, et des boutons.
 * - L'écran des paramètres (settings_screen) avec des sliders et des boutons.
 * - La gestion de la musique, des polices, et des interactions utilisateur (clics, survol).
 *
 * La classe utilise des éléments graphiques (Graphics2D), des ressources (images, sons, polices),
 * et des interactions utilisateur pour créer une interface immersive et interactive.
 */
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
    // ╔══════════════════════════════════════╗
    // ║           CHAMPS PRINCIPAUX          ║
    // ╚══════════════════════════════════════╝
    // Ces champs sont utilisés pour gérer les éléments de base de l'interface utilisateur.

    /** Référence au GamePanel principal pour accéder à ses propriétés (taille de l'écran, état du jeu, etc.). */
    GamePanel gp;

    /** Contexte graphique utilisé pour dessiner tous les éléments visuels de l'interface (boutons, texte, images, etc.). */
    Graphics2D g2;

    /** Police personnalisée pour le titre principal (ex. "Gorilla") avec une taille de 72. */
    Font titleFont;

    /** Police personnalisée pour les textes des menus (ex. "Appuyez sur ENTER pour jouer") avec une taille de 24. */
    Font menuFont;

    /** Clip audio pour la musique de fond, chargé depuis un fichier WAV. */
    private Clip musicClip;

    /** Image de fond pour le ciel, utilisée dans l'effet parallax (défilement lent). */
    private BufferedImage backgroundSky;

    /** Image de fond pour les montagnes, utilisée dans l'effet parallax (défilement moyen). */
    private BufferedImage backgroundMountains;

    /** Image de fond pour le sol, utilisée dans l'effet parallax (défilement rapide). */
    private BufferedImage backgroundGround;

    /** Niveau de volume initial de la musique, en décibels (dB). -10.0f correspond à un volume modéré. */
    private float volumeLevel = -10.0f;

    /** Niveau de luminosité de l'écran (1.0 = maximum, 0.0 = noir). Permet d'assombrir l'écran si nécessaire. */
    private float brightnessLevel = 1.0f;

    /** État de la musique (true = activée, false = désactivée). Par défaut, la musique est activée. */
    private boolean musicEnabled = true;

    /** Langue de l'interface ("fr" pour français, "en" pour anglais). Par défaut, français. */
    private String language = "fr";

    // ╔══════════════════════════════════════╗
    // ║           BOUTONS ET SLIDERS         ║
    // ╚══════════════════════════════════════╝
    // Ces champs définissent les zones interactives (boutons et sliders) sous forme de rectangles.

    /** Bouton "Paramètres" sur l'écran titre, cliquable pour passer à l'écran des paramètres. */
    private Rectangle settingsButton;

    /** Bouton "Retour" sur l'écran des paramètres, cliquable pour revenir à l'écran titre. */
    private Rectangle backButton;

    /** Slider pour ajuster le volume de la musique (barre horizontale). */
    private Rectangle volumeSlider;

    /** Poignée du slider de volume, déplaçable pour modifier le volume. */
    private Rectangle volumeSliderKnob;

    /** Slider pour ajuster la luminosité de l'écran (barre horizontale). */
    private Rectangle brightnessSlider;

    /** Poignée du slider de luminosité, déplaçable pour modifier la luminosité. */
    private Rectangle brightnessSliderKnob;

    /** Bouton pour activer/désactiver la musique (bascule ON/OFF). */
    private Rectangle musicToggleButton;

    /** Bouton pour changer la langue (bascule entre français et anglais). */
    private Rectangle languageButton;

    // ╔══════════════════════════════════════╗
    // ║           PARALLAX SCROLLING         ║
    // ╚══════════════════════════════════════╝
    // Ces champs gèrent les décalages des couches de fond pour l'effet parallax.

    /** Décalage du ciel (défilement lent vers la gauche). */
    private double skyOffset = 0;

    /** Décalage des montagnes (défilement moyen vers la gauche). */
    private double mountainsOffset = 0;

    /** Décalage du sol (défilement rapide vers la gauche). */
    private double groundOffset = 0;

    // ╔══════════════════════════════════════╗
    // ║           PARTICULES                 ║
    // ╚══════════════════════════════════════╝
    // Ces champs gèrent les particules flottantes pour un effet visuel d'ambiance.

    /** Liste des particules flottantes (carrés blancs semi-transparents). */
    private List<Particle> particles = new ArrayList<>();

    /** Générateur de nombres aléatoires pour les positions, vitesses et tailles des particules. */
    private Random random = new Random();

    // ╔══════════════════════════════════════╗
    // ║           EFFETS VISUELS             ║
    // ╚══════════════════════════════════════╝
    // Ces champs gèrent les effets visuels comme le survol et le tremblement.

    /** Indique si la souris survole le bouton "Paramètres" (true = survolé, false = non survolé). */
    private boolean settingsButtonHovered = false;

    /** Timer pour gérer la fréquence de l'effet de tremblement du titre. */
    private double titleShakeTimer = 0;

    /** Décalage en X pour l'effet de tremblement du titre (entre -2 et 2 pixels). */
    private int titleShakeX = 0;

    /** Décalage en Y pour l'effet de tremblement du titre (entre -2 et 2 pixels). */
    private int titleShakeY = 0;

    // ╔══════════════════════════════════════╗
    // ║           SLIDERS - PLAGES           ║
    // ╚══════════════════════════════════════╝
    // Ces champs définissent les plages de valeurs pour les sliders.

    /** Volume minimum du slider de volume, en décibels (dB). -80 dB = silence. */
    private int volumeSliderMin = -80;

    /** Volume maximum du slider de volume, en décibels (dB). 0 dB = volume maximum. */
    private int volumeSliderMax = 0;

    /** Luminosité minimum du slider de luminosité (0% = noir). */
    private int brightnessSliderMin = 0;

    /** Luminosité maximum du slider de luminosité (100% = pleine luminosité). */
    private int brightnessSliderMax = 100;

    /** Indique si la poignée du slider de volume est en train d'être déplacée (true = oui). */
    private boolean draggingVolumeKnob = false;

    /** Indique si la poignée du slider de luminosité est en train d'être déplacée (true = oui). */
    private boolean draggingBrightnessKnob = false;

    // ╔══════════════════════════════════════╗
    // ║           PERSONNAGE ANIMÉ           ║
    // ╚══════════════════════════════════════╝
    // Ce champ gère le personnage animé qui court sur les montagnes dans l'écran titre.

    /** Instance du personnage animé qui court de droite à gauche sur les montagnes. */
    private PlayerCharacter playerCharacter;

    /**
     * ╔══════════════════════════════════════╗
     * ║           CONSTRUCTEUR               ║
     * ╚══════════════════════════════════════╝
     * Initialise l'interface utilisateur avec tous ses éléments.
     *
     * @param gp Le GamePanel principal pour accéder à ses propriétés (taille de l'écran, état du jeu, etc.).
     */
    public ui(GamePanel gp) {
        // Assigne le GamePanel à la variable d'instance pour pouvoir accéder à ses propriétés.
        this.gp = gp;

        // Charge les polices personnalisées (Minecraftia) pour le titre et les menus.
        loadFonts();

        // Si le chargement de la police du titre échoue, utilise une police par défaut (Arial, gras, taille 72).
        if (titleFont == null) {
            titleFont = new Font("Arial", Font.BOLD, 72);
        }

        // Si le chargement de la police du menu échoue, utilise une police par défaut (Arial, normal, taille 24).
        if (menuFont == null) {
            menuFont = new Font("Arial", Font.PLAIN, 24);
        }

        // Charge la musique de fond depuis le fichier /audio/Trap.wav.
        loadMusic();

        // Charge les images de fond pour l'effet parallax (ciel, montagnes, sol).
        loadBackground();

        // Initialise les boutons et sliders avec leurs positions et tailles.
        // Bouton "Paramètres" : centré horizontalement, positionné à 50 pixels sous le centre vertical.
        settingsButton = new Rectangle(gp.screenWidth / 2 - 100, gp.screenHeight / 2 + 50, 200, 40);

        // Bouton "Retour" : centré horizontalement, positionné à 180 pixels sous le centre vertical.
        backButton = new Rectangle(gp.screenWidth / 2 - 100, gp.screenHeight / 2 + 180, 200, 40);

        // Slider de volume : positionné à droite du centre, 60 pixels au-dessus du centre vertical.
        volumeSlider = new Rectangle(gp.screenWidth / 2 + 50, gp.screenHeight / 2 - 60, 150, 10);

        // Poignée du slider de volume : initialisée avec une taille de 16x20 pixels.
        volumeSliderKnob = new Rectangle(0, 0, 16, 20);

        // Slider de luminosité : positionné à droite du centre, 20 pixels au-dessus du centre vertical.
        brightnessSlider = new Rectangle(gp.screenWidth / 2 + 50, gp.screenHeight / 2 - 20, 150, 10);

        // Poignée du slider de luminosité : initialisée avec une taille de 16x20 pixels.
        brightnessSliderKnob = new Rectangle(0, 0, 16, 20);

        // Bouton pour activer/désactiver la musique : centré horizontalement, 60 pixels sous le centre vertical.
        musicToggleButton = new Rectangle(gp.screenWidth / 2 - 100, gp.screenHeight / 2 + 60, 200, 40);

        // Bouton pour changer la langue : centré horizontalement, 120 pixels sous le centre vertical.
        languageButton = new Rectangle(gp.screenWidth / 2 - 100, gp.screenHeight / 2 + 120, 200, 40);

        // Met à jour les positions initiales des poignées des sliders en fonction des valeurs par défaut.
        updateSliderKnobs();

        // Initialise 20 particules flottantes pour l'effet visuel d'ambiance sur l'écran titre.
        for (int i = 0; i < 20; i++) {
            particles.add(new Particle());
        }

        // Initialise le personnage animé qui court sur les montagnes dans l'écran titre.
        playerCharacter = new PlayerCharacter();

        // Charge les sprites du personnage pour l'animation de marche.
        SetPlayerWalkingToTheRight();
    }

    /**
     * ╔══════════════════════════════════════╗
     * ║    MÉTHODE : SetPlayerWalkingToTheRight ║
     * ╚══════════════════════════════════════╝
     * Charge les sprites du personnage pour l'animation de marche vers la droite,
     * puis les passe au personnage pour qu'il les utilise (inversés pour marcher à gauche).
     * Affiche des messages de progression pour chaque sprite chargé.
     */
    public void SetPlayerWalkingToTheRight() {
        try {
            // Affiche un message indiquant le début du chargement du premier sprite.
            System.out.println("ECRAN D'ACCEUIL CHARGEMENT DU PERSONNAGE PRINCIPAL [ 1/6 ] ");

            // Charge le premier sprite depuis le fichier /player/row-5-column-1.png.
            BufferedImage gauche1 = ImageIO.read(getClass().getResourceAsStream("/player/row-5-column-1.png"));

            // Confirme que le chargement du premier sprite a réussi.
            System.out.println(" CHARGEMENT [ 1/6 ] ----------> SUCCES ");

            // Affiche un message pour le deuxième sprite.
            System.out.println("ECRAN D'ACCEUIL CHARGEMENT DU PERSONNAGE PRINCIPAL [ 2/6 ] ");

            // Charge le deuxième sprite.
            BufferedImage gauche2 = ImageIO.read(getClass().getResourceAsStream("/player/row-5-column-2.png"));
            System.out.println(" CHARGEMENT [ 2/6 ] ----------> SUCCES ");

            // Affiche un message pour le troisième sprite.
            System.out.println("ECRAN D'ACCEUIL CHARGEMENT DU PERSONNAGE PRINCIPAL [ 3/6 ] ");

            // Charge le troisième sprite.
            BufferedImage gauche3 = ImageIO.read(getClass().getResourceAsStream("/player/row-5-column-3.png"));
            System.out.println(" CHARGEMENT [ 3/6 ] ----------> SUCCES ");

            // Affiche un message pour le quatrième sprite.
            System.out.println("ECRAN D'ACCEUIL CHARGEMENT DU PERSONNAGE PRINCIPAL [ 4/6 ] ");

            // Charge le quatrième sprite.
            BufferedImage gauche4 = ImageIO.read(getClass().getResourceAsStream("/player/row-5-column-4.png"));
            System.out.println(" CHARGEMENT [ 4/6 ] ----------> SUCCES ");

            // Affiche un message pour le cinquième sprite.
            System.out.println("ECRAN D'ACCEUIL CHARGEMENT DU PERSONNAGE PRINCIPAL [ 5/6 ] ");

            // Charge le cinquième sprite.
            BufferedImage gauche5 = ImageIO.read(getClass().getResourceAsStream("/player/row-5-column-5.png"));
            System.out.println(" CHARGEMENT [ 5/6 ] ----------> SUCCES ");

            // Affiche un message pour le sixième sprite.
            System.out.println("ECRAN D'ACCEUIL CHARGEMENT DU PERSONNAGE PRINCIPAL [ 6/6 ] ");

            // Charge le sixième sprite.
            BufferedImage gauche6 = ImageIO.read(getClass().getResourceAsStream("/player/row-5-column-6.png"));
            System.out.println(" CHARGEMENT [ 6/6 ] ----------> SUCCES ");

            // Passe les six sprites chargés au personnage (ils seront inversés pour marcher à gauche).
            playerCharacter.setSprites(new BufferedImage[]{gauche1, gauche2, gauche3, gauche4, gauche5, gauche6});

        } catch (Exception e) {
            // En cas d'erreur (fichier non trouvé, etc.), affiche un message d'erreur et lève une exception.
            System.out.println("Les images n'ont pas été generé pour l'ecran d'acceuil");
            throw new RuntimeException(e);
        }
    }

    /**
     * ╔══════════════════════════════════════╗
     * ║    MÉTHODE : loadFonts               ║
     * ╚══════════════════════════════════════╝
     * Charge la police personnalisée "Minecraftia" depuis le fichier /fonts/Minecraftia-Regular.ttf.
     * La police est utilisée pour le titre (taille 72) et les menus (taille 24).
     */
    private void loadFonts() {
        try {
            // Charge la police depuis le fichier de ressources.
            Font baseFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/Minecraftia-Regular.ttf"));

            // Enregistre la police dans l'environnement graphique pour qu'elle soit utilisable.
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(baseFont);

            // Dérive la police pour le titre avec une taille de 72.
            titleFont = baseFont.deriveFont(72f);

            // Dérive la police pour les menus avec une taille de 24.
            menuFont = baseFont.deriveFont(24f);

            // Confirme que la police a été chargée avec succès.
            System.out.println("Police Minecraftia chargée avec succès");

        } catch (FontFormatException e) {
            // Si le format de la police est invalide, affiche une erreur.
            System.err.println("Erreur de format de la police : " + e.getMessage());
            e.printStackTrace();

        } catch (IOException e) {
            // Si le fichier de police n'est pas trouvé ou inaccessible, affiche une erreur.
            System.err.println("Erreur lors du chargement de la police : " + e.getMessage());
            e.printStackTrace();

        } catch (NullPointerException e) {
            // Si le fichier de police est introuvable dans les ressources, affiche une erreur.
            System.err.println("Le fichier /fonts/Minecraftia-Regular.ttf n'a pas été trouvé dans les ressources");
            e.printStackTrace();
        }
    }

    /**
     * ╔══════════════════════════════════════╗
     * ║    MÉTHODE : loadMusic               ║
     * ╚══════════════════════════════════════╝
     * Charge la musique de fond depuis le fichier /audio/Trap.wav.
     * La musique est stockée dans un Clip pour être jouée en boucle.
     */
    private void loadMusic() {
        try {
            // Affiche un message pour indiquer le début du chargement de la musique.
            System.out.println("Tentative de chargement de la musique : /audio/Trap.wav");

            // Charge le flux audio depuis le fichier WAV.
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(
                    getClass().getResourceAsStream("/audio/Trap.wav")
            );

            // Crée un clip audio pour jouer la musique.
            musicClip = AudioSystem.getClip();

            // Ouvre le flux audio dans le clip.
            musicClip.open(audioStream);

            // Met à jour le volume initial du clip en fonction de volumeLevel.
            updateVolume();

            // Confirme que la musique a été chargée avec succès.
            System.out.println("Musique chargée avec succès");

        } catch (Exception e) {
            // En cas d'erreur (fichier non trouvé, format invalide, etc.), affiche une erreur.
            System.err.println("Erreur lors du chargement de la musique : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * ╔══════════════════════════════════════╗
     * ║    MÉTHODE : loadBackground          ║
     * ╚══════════════════════════════════════╝
     * Charge les images de fond pour l'effet parallax (ciel, montagnes, sol).
     * Les images sont stockées dans les champs backgroundSky, backgroundMountains, et backgroundGround.
     */
    private void loadBackground() {
        try {
            // Charge l'image du ciel depuis /tiles/sky.png.
            backgroundSky = ImageIO.read(getClass().getResourceAsStream("/tiles/sky.png"));

            // Charge l'image des montagnes depuis /tiles/mountains.png.
            backgroundMountains = ImageIO.read(getClass().getResourceAsStream("/tiles/mountains.png"));

            // Charge l'image du sol depuis /tiles/sol_vert.png.
            backgroundGround = ImageIO.read(getClass().getResourceAsStream("/tiles/sol_vert.png"));

            // Confirme que les images de fond ont été chargées avec succès.
            System.out.println("Fonds d'écran chargés avec succès");

        } catch (IOException e) {
            // En cas d'erreur (fichier non trouvé, etc.), affiche une erreur.
            System.err.println("Erreur lors du chargement des fonds : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * ╔══════════════════════════════════════╗
     * ║    MÉTHODE : updateVolume            ║
     * ╚══════════════════════════════════════╝
     * Met à jour le volume de la musique en fonction de la valeur de volumeLevel.
     * Utilise un FloatControl pour ajuster le gain du clip audio.
     */
    private void updateVolume() {
        // Vérifie que le clip audio est initialisé.
        if (musicClip != null) {
            // Récupère le contrôle de gain (volume) du clip.
            FloatControl gainControl = (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);

            // Applique le volume actuel (volumeLevel) au clip.
            gainControl.setValue(volumeLevel);
        }
    }

    /**
     * ╔══════════════════════════════════════╗
     * ║    MÉTHODE : updateSliderKnobs       ║
     * ╚══════════════════════════════════════╝
     * Met à jour les positions des poignées des sliders (volume et luminosité)
     * en fonction de leurs valeurs actuelles (volumeLevel et brightnessLevel).
     */
    private void updateSliderKnobs() {
        // Calcule la position de la poignée du slider de volume.
        // volumeRange : plage totale des valeurs possibles pour le volume (de -80 à 0 dB).
        float volumeRange = volumeSliderMax - volumeSliderMin;

        // volumePosition : position relative de la poignée (entre 0 et 1) en fonction du volume actuel.
        float volumePosition = (volumeLevel - volumeSliderMin) / volumeRange;

        // volumeKnobX : position en pixels de la poignée sur le slider.
        int volumeKnobX = volumeSlider.x + (int) (volumePosition * (volumeSlider.width - volumeSliderKnob.width));

        // Met à jour la position X de la poignée.
        volumeSliderKnob.x = volumeKnobX;

        // Positionne la poignée légèrement au-dessus du slider (5 pixels au-dessus).
        volumeSliderKnob.y = volumeSlider.y - 5;

        // Calcule la position de la poignée du slider de luminosité.
        // brightnessRange : plage totale des valeurs possibles pour la luminosité (de 0 à 100%).
        float brightnessRange = brightnessSliderMax - brightnessSliderMin;

        // brightnessPosition : position relative de la poignée (entre 0 et 1) en fonction de la luminosité actuelle.
        float brightnessPosition = ((brightnessLevel * 100) - brightnessSliderMin) / brightnessRange;

        // brightnessKnobX : position en pixels de la poignée sur le slider.
        int brightnessKnobX = brightnessSlider.x + (int) (brightnessPosition * (brightnessSlider.width - brightnessSliderKnob.width));

        // Met à jour la position X de la poignée.
        brightnessSliderKnob.x = brightnessKnobX;

        // Positionne la poignée légèrement au-dessus du slider (5 pixels au-dessus).
        brightnessSliderKnob.y = brightnessSlider.y - 5;
    }

    /**
     * ╔══════════════════════════════════════╗
     * ║    MÉTHODE : playMusic               ║
     * ╚══════════════════════════════════════╝
     * Joue la musique de fond en boucle si elle est activée.
     * Vérifie d'abord si le clip est initialisé et si la musique est activée.
     */
    public void playMusic() {
        // Vérifie si le clip audio est null ou si la musique est désactivée.
        if (musicClip == null || !musicEnabled) {
            // Affiche un message et sort de la méthode si la musique ne peut pas être jouée.
            System.out.println("Musique désactivée ou clip non initialisé");
            return;
        }

        // Vérifie si la musique n'est pas déjà en cours de lecture.
        if (!musicClip.isRunning()) {
            // Configure le clip pour jouer en boucle infinie.
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);

            // Démarre la lecture de la musique.
            musicClip.start();

            // Confirme que la musique a démarré.
            System.out.println("Musique démarrée");
        }
    }

    /**
     * ╔══════════════════════════════════════╗
     * ║    MÉTHODE : stopMusic               ║
     * ╚══════════════════════════════════════╝
     * Arrête la musique et réinitialise sa position à zéro.
     * Vérifie d'abord si le clip est initialisé et en cours de lecture.
     */
    public void stopMusic() {
        // Vérifie si le clip audio est initialisé et en cours de lecture.
        if (musicClip != null && musicClip.isRunning()) {
            // Arrête la lecture de la musique.
            musicClip.stop();

            // Réinitialise la position du clip au début.
            musicClip.setFramePosition(0);
        }
    }

    /**
     * ╔══════════════════════════════════════╗
     * ║    MÉTHODE : draw                    ║
     * ╚══════════════════════════════════════╝
     * Dessine l'interface utilisateur en fonction de l'état du jeu.
     * Met à jour les animations (parallax, particules, titre, personnage) avant de dessiner.
     *
     * @param g2 Le contexte graphique pour dessiner.
     */
    public void draw(Graphics2D g2) {
        // Assigne le contexte graphique à la variable d'instance pour l'utiliser dans les méthodes de dessin.
        this.g2 = g2;

        // Met à jour les animations avant de dessiner.
        // Met à jour les décalages des couches de fond pour l'effet parallax.
        updateParallax();

        // Met à jour la position des particules flottantes.
        updateParticles();

        // Met à jour l'effet de tremblement du titre.
        updateTitleShake();

        // Met à jour la position et l'animation du personnage.
        playerCharacter.update();

        // Dessine l'écran approprié en fonction de l'état du jeu.
        if (gp.Game_state == gp.Start_screen) {
            // Si l'état est Start_screen, dessine l'écran titre et joue la musique.
            drawTitleScreen();
            playMusic();
        } else if (gp.Game_state == gp.settings_screen) {
            // Si l'état est settings_screen, dessine l'écran des paramètres et joue la musique.
            drawSettingsScreen();
            playMusic();
        } else {
            // Si l'état n'est ni Start_screen ni settings_screen, arrête la musique.
            stopMusic();
        }

        // Applique un effet de luminosité (assombrissement de l'écran) si brightnessLevel est inférieur à 1.0.
        if (brightnessLevel < 1.0f) {
            // Calcule l'opacité de l'assombrissement (0 = transparent, 255 = noir).
            g2.setColor(new Color(0, 0, 0, (int) ((1.0f - brightnessLevel) * 255)));

            // Dessine un rectangle noir semi-transparent sur tout l'écran.
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        }
    }

    /**
     * ╔══════════════════════════════════════╗
     * ║    MÉTHODE : updateParallax          ║
     * ╚══════════════════════════════════════╝
     * Met à jour les décalages des couches de fond pour l'effet parallax.
     * Chaque couche (ciel, montagnes, sol) défile à une vitesse différente pour créer une illusion de profondeur.
     */
    private void updateParallax() {
        // Défilement lent pour le ciel (0.1 pixel par frame vers la gauche).
        skyOffset -= 0.1;

        // Défilement moyen pour les montagnes (0.3 pixel par frame vers la gauche).
        mountainsOffset -= 0.3;

        // Défilement rapide pour le sol (0.5 pixel par frame vers la gauche).
        groundOffset -= 0.5;

        // Réinitialise les décalages si les couches sortent complètement de l'écran à gauche.
        // Cela crée un effet de boucle infinie.
        if (skyOffset < -gp.screenWidth) skyOffset = 0;
        if (mountainsOffset < -gp.screenWidth) mountainsOffset = 0;
        if (groundOffset < -gp.screenWidth) groundOffset = 0;
    }

    /**
     * ╔══════════════════════════════════════╗
     * ║    MÉTHODE : updateParticles         ║
     * ╚══════════════════════════════════════╝
     * Met à jour la position de chaque particule flottante.
     * Si une particule sort de l'écran par le bas, elle est réinitialisée en haut.
     */
    private void updateParticles() {
        // Parcourt toutes les particules dans la liste.
        for (Particle particle : particles) {
            // Met à jour la position de la particule en fonction de sa vitesse.
            particle.update();

            // Si la particule sort de l'écran par le bas, la réinitialise en haut.
            if (particle.y > gp.screenHeight) {
                particle.reset();
            }
        }
    }

    /**
     * ╔══════════════════════════════════════╗
     * ║    MÉTHODE : updateTitleShake        ║
     * ╚══════════════════════════════════════╝
     * Met à jour l'effet de tremblement du titre.
     * Toutes les secondes, génère un nouveau décalage aléatoire pour simuler un tremblement.
     */
    private void updateTitleShake() {
        // Incrémente le timer de tremblement.
        titleShakeTimer += 0.1;

        // Si le timer atteint ou dépasse 1 seconde, génère un nouveau tremblement.
        if (titleShakeTimer >= 1) {
            // Génère un décalage aléatoire en X entre -2 et 2 pixels.
            titleShakeX = random.nextInt(5) - 2;

            // Génère un décalage aléatoire en Y entre -2 et 2 pixels.
            titleShakeY = random.nextInt(5) - 2;

            // Réinitialise le timer pour le prochain tremblement.
            titleShakeTimer = 0;
        }
    }

    /**
     * ╔══════════════════════════════════════╗
     * ║    MÉTHODE : drawTitleScreen         ║
     * ╚══════════════════════════════════════╝
     * Dessine l'écran titre avec :
     * - Un fond parallax (ciel, montagnes, sol).
     * - Des particules flottantes.
     * - Un personnage animé qui court sur les montagnes.
     * - Un titre avec effet de tremblement.
     * - Un texte "Appuyez sur ENTER pour jouer".
     * - Un bouton "Paramètres" avec effet de survol.
     */
    public void drawTitleScreen() {
        // Dessine le ciel avec l'effet parallax.
        if (backgroundSky != null) {
            // Dessine l'image du ciel à la position actuelle (skyOffset).
            g2.drawImage(backgroundSky, (int) skyOffset, 0, gp.screenWidth, gp.screenHeight, null);

            // Dessine une deuxième instance de l'image juste à droite pour créer un défilement continu.
            g2.drawImage(backgroundSky, (int) skyOffset + gp.screenWidth, 0, gp.screenWidth, gp.screenHeight, null);
        }

        // Dessine les montagnes avec l'effet parallax.
        if (backgroundMountains != null) {
            // Positionne les montagnes à mi-hauteur de l'écran, ajustée de -100 pixels.
            g2.drawImage(backgroundMountains, (int) mountainsOffset, gp.screenHeight / 2 - 100, gp.screenWidth, 200, null);

            // Dessine une deuxième instance des montagnes pour le défilement continu.
            g2.drawImage(backgroundMountains, (int) mountainsOffset + gp.screenWidth, gp.screenHeight / 2 - 100, gp.screenWidth, 200, null);
        }

        // Dessine le sol avec l'effet parallax.
        if (backgroundGround != null) {
            // Dessine le sol en mosaïque (tuiles) à partir de la position groundOffset.
            for (int x = (int) groundOffset; x < gp.screenWidth; x += gp.tileSize) {
                for (int y = gp.screenHeight - gp.tileSize; y < gp.screenHeight; y += gp.tileSize) {
                    g2.drawImage(backgroundGround, x, y, gp.tileSize, gp.tileSize, null);
                }
            }

            // Dessine une deuxième instance du sol pour le défilement continu.
            for (int x = (int) groundOffset + gp.screenWidth; x < gp.screenWidth + gp.tileSize; x += gp.tileSize) {
                for (int y = gp.screenHeight - gp.tileSize; y < gp.screenHeight; y += gp.tileSize) {
                    g2.drawImage(backgroundGround, x, y, gp.tileSize, gp.tileSize, null);
                }
            }
        }

        // Dessine le personnage animé sur les montagnes.
        playerCharacter.draw(g2);

        // Dessine toutes les particules flottantes.
        for (Particle particle : particles) {
            particle.draw(g2);
        }

        // Dessine le titre avec l'effet de tremblement.
        g2.setFont(titleFont);
        String title = language.equals("fr") ? "Gorilla" : "Gorilla";

        // Calcule la position X pour centrer le titre, en ajoutant le décalage de tremblement.
        int x = getXforCenteredText(title) + titleShakeX;

        // Positionne le titre à 1/4 de la hauteur de l'écran, avec le décalage de tremblement.
        int y = gp.screenHeight / 4 + titleShakeY;

        // Dessine une ombre noire pour le titre (décalée de 4 pixels en bas à droite).
        g2.setColor(Color.BLACK);
        g2.drawString(title, x + 4, y + 4);

        // Dessine le titre principal en gris clair.
        g2.setColor(new Color(200, 200, 200));
        g2.drawString(title, x, y);

        // Dessine le texte "Appuyez sur ENTER pour jouer".
        g2.setFont(menuFont);
        String startText = language.equals("fr") ? "Appuyez sur ENTER pour jouer" : "Press ENTER to Play";

        // Centre le texte horizontalement.
        x = getXforCenteredText(startText);

        // Positionne le texte au centre vertical de l'écran.
        y = gp.screenHeight / 2;

        // Dessine une ombre noire pour le texte (décalée de 2 pixels en bas à droite).
        g2.setColor(Color.BLACK);
        g2.drawString(startText, x + 2, y + 2);

        // Dessine le texte principal en gris clair.
        g2.setColor(new Color(220, 220, 220));
        g2.drawString(startText, x, y);

        // Dessine le bouton "Paramètres" avec un effet de survol (change de couleur si survolé).
        drawButton(settingsButton, language.equals("fr") ? "Paramètres" : "Settings", settingsButtonHovered);
    }

    /**
     * ╔══════════════════════════════════════╗
     * ║    MÉTHODE : drawSettingsScreen      ║
     * ╚══════════════════════════════════════╝
     * Dessine l'écran des paramètres avec :
     * - Un fond (sol uniquement).
     * - Un panneau semi-transparent avec une bordure.
     * - Un titre "Paramètres".
     * - Un slider pour le volume.
     * - Un slider pour la luminosité.
     * - Un bouton pour activer/désactiver la musique.
     * - Un bouton pour changer la langue.
     * - Un bouton "Retour".
     */
    public void drawSettingsScreen() {
        // Dessine le fond (sol uniquement) en mosaïque.
        if (backgroundGround != null) {
            for (int y = 0; y < gp.screenHeight; y += gp.tileSize) {
                for (int x = 0; x < gp.screenWidth; x += gp.tileSize) {
                    g2.drawImage(backgroundGround, x, y, gp.tileSize, gp.tileSize, null);
                }
            }
        } else {
            // Si l'image du sol n'est pas chargée, dessine un fond gris par défaut.
            g2.setColor(new Color(50, 50, 50));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        }

        // Dessine un panneau semi-transparent pour le menu des paramètres.
        // Panneau noir avec une opacité de 200 (sur 255).
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRect(gp.screenWidth / 2 - 300, gp.screenHeight / 2 - 200, 600, 400);

        // Dessine une bordure grise autour du panneau.
        g2.setColor(new Color(100, 100, 100));
        g2.drawRect(gp.screenWidth / 2 - 300, gp.screenHeight / 2 - 200, 600, 400);

        // Dessine le titre "Paramètres".
        g2.setFont(titleFont);
        String title = language.equals("fr") ? "Paramètres" : "Settings";

        // Centre le titre horizontalement.
        int x = getXforCenteredText(title);

        // Positionne le titre à 1/4 de la hauteur de l'écran, ajusté de -20 pixels.
        int y = gp.screenHeight / 4 - 20;

        // Dessine une ombre noire pour le titre.
        g2.setColor(Color.BLACK);
        g2.drawString(title, x + 4, y + 4);

        // Dessine le titre principal en gris clair.
        g2.setColor(new Color(200, 200, 200));
        g2.drawString(title, x, y);

        // Utilise la police des menus pour les éléments suivants.
        g2.setFont(menuFont);

        // Dessine le texte et le slider de volume.
        String volumeText = language.equals("fr") ? "Volume :" : "Volume:";

        // Positionne le texte à gauche du slider.
        x = gp.screenWidth / 2 - 250;
        y = volumeSlider.y + 5;

        // Dessine le texte en blanc.
        g2.setColor(Color.WHITE);
        g2.drawString(volumeText, x, y);

        // Dessine le slider de volume avec sa poignée.
        drawSlider(volumeSlider, volumeSliderKnob);

        // Dessine le texte et le slider de luminosité.
        String brightnessText = language.equals("fr") ? "Luminosité :" : "Brightness:";

        // Positionne le texte à gauche du slider.
        x = gp.screenWidth / 2 - 250;
        y = brightnessSlider.y + 5;

        // Dessine le texte en blanc.
        g2.setColor(Color.WHITE);
        g2.drawString(brightnessText, x, y);

        // Dessine le slider de luminosité avec sa poignée.
        drawSlider(brightnessSlider, brightnessSliderKnob);

        // Dessine le bouton pour activer/désactiver la musique.
        drawButton(musicToggleButton, language.equals("fr") ? "Musique : " + (musicEnabled ? "ON" : "OFF") : "Music: " + (musicEnabled ? "ON" : "OFF"), false);

        // Dessine le bouton pour changer la langue.
        drawButton(languageButton, language.equals("fr") ? "Langue : Français" : "Language: English", false);

        // Dessine le bouton "Retour".
        drawButton(backButton, language.equals("fr") ? "Retour" : "Back", false);
    }

    /**
     * ╔══════════════════════════════════════╗
     * ║    MÉTHODE : drawButton              ║
     * ╚══════════════════════════════════════╝
     * Dessine un bouton avec un effet 3D et un effet de survol.
     * Le bouton change de couleur lorsqu'il est survolé par la souris.
     *
     * @param button  Le rectangle du bouton.
     * @param text    Le texte à afficher sur le bouton.
     * @param hovered Indique si la souris survole le bouton (true = survolé).
     */
    private void drawButton(Rectangle button, String text, boolean hovered) {
        // Dessine une ombre grise foncée pour un effet 3D (décalée de 2 pixels en bas à droite).
        g2.setColor(new Color(120, 120, 120));
        g2.fillRect(button.x + 2, button.y + 2, button.width, button.height);

        // Dessine la base du bouton.
        // Si survolé, utilise une couleur plus claire (200, 200, 200), sinon une couleur normale (180, 180, 180).
        g2.setColor(hovered ? new Color(200, 200, 200) : new Color(180, 180, 180));
        g2.fillRect(button.x, button.y, button.width, button.height);

        // Dessine une bordure grise autour du bouton.
        g2.setColor(new Color(100, 100, 100));
        g2.drawRect(button.x, button.y, button.width, button.height);

        // Dessine le texte centré sur le bouton.
        g2.setFont(menuFont);

        // Calcule la position X pour centrer le texte dans le bouton.
        int x = getXforCenteredText(text, button);

        // Calcule la position Y pour centrer le texte verticalement dans le bouton.
        int y = button.y + (button.height + g2.getFontMetrics().getAscent()) / 2 - 5;

        // Dessine une ombre noire pour le texte (décalée de 2 pixels en bas à droite).
        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 2, y + 2);

        // Dessine le texte principal en blanc.
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
    }

    /**
     * ╔══════════════════════════════════════╗
     * ║    MÉTHODE : drawSlider              ║
     * ╚══════════════════════════════════════╝
     * Dessine un slider avec sa poignée, avec un effet 3D.
     *
     * @param slider Le rectangle du slider (barre horizontale).
     * @param knob   Le rectangle de la poignée (déplaçable).
     */
    private void drawSlider(Rectangle slider, Rectangle knob) {
        // Dessine une ombre grise foncée pour le slider (décalée de 2 pixels en bas à droite).
        g2.setColor(new Color(80, 80, 80));
        g2.fillRect(slider.x + 2, slider.y + 2, slider.width, slider.height);

        // Dessine la base du slider en gris moyen.
        g2.setColor(new Color(120, 120, 120));
        g2.fillRect(slider.x, slider.y, slider.width, slider.height);

        // Dessine une bordure grise foncée autour du slider.
        g2.setColor(new Color(60, 60, 60));
        g2.drawRect(slider.x, slider.y, slider.width, slider.height);

        // Dessine une ombre grise claire pour la poignée (décalée de 2 pixels en bas à droite).
        g2.setColor(new Color(150, 150, 150));
        g2.fillRect(knob.x + 2, knob.y + 2, knob.width, knob.height);

        // Dessine la base de la poignée en gris très clair.
        g2.setColor(new Color(200, 200, 200));
        g2.fillRect(knob.x, knob.y, knob.width, knob.height);

        // Dessine une bordure grise autour de la poignée.
        g2.setColor(new Color(100, 100, 100));
        g2.drawRect(knob.x, knob.y, knob.width, knob.height);
    }

    /**
     * ╔══════════════════════════════════════╗
     * ║    MÉTHODE : handleMouseClick        ║
     * ╚══════════════════════════════════════╝
     * Gère les clics de souris sur les boutons et sliders.
     * Détermine l'action à effectuer en fonction de l'état du jeu et de la position du clic.
     *
     * @param x Coordonnée X du clic.
     * @param y Coordonnée Y du clic.
     */
    public void handleMouseClick(int x, int y) {
        // Si l'état du jeu est Start_screen (écran titre).
        if (gp.Game_state == gp.Start_screen) {
            // Vérifie si le clic est sur le bouton "Paramètres".
            if (settingsButton.contains(x, y)) {
                // Passe à l'écran des paramètres.
                gp.Game_state = gp.settings_screen;
            }
        }
        // Si l'état du jeu est settings_screen (écran des paramètres).
        else if (gp.Game_state == gp.settings_screen) {
            // Vérifie si le clic est sur la poignée du slider de volume.
            if (volumeSliderKnob.contains(x, y)) {
                // Active le déplacement de la poignée.
                draggingVolumeKnob = true;
            }
            // Vérifie si le clic est sur la poignée du slider de luminosité.
            else if (brightnessSliderKnob.contains(x, y)) {
                // Active le déplacement de la poignée.
                draggingBrightnessKnob = true;
            }
            // Vérifie si le clic est sur le bouton pour activer/désactiver la musique.
            else if (musicToggleButton.contains(x, y)) {
                // Bascule l'état de la musique (ON/OFF).
                musicEnabled = !musicEnabled;

                // Si la musique est activée, la joue.
                if (musicEnabled) {
                    playMusic();
                }
                // Sinon, l'arrête.
                else {
                    stopMusic();
                }
            }
            // Vérifie si le clic est sur le bouton pour changer la langue.
            else if (languageButton.contains(x, y)) {
                // Bascule entre français ("fr") et anglais ("en").
                language = language.equals("fr") ? "en" : "fr";
            }
            // Vérifie si le clic est sur le bouton "Retour".
            else if (backButton.contains(x, y)) {
                // Revient à l'écran titre.
                gp.Game_state = gp.Start_screen;
            }
        }
    }

    /**
     * ╔══════════════════════════════════════╗
     * ║    MÉTHODE : handleMouseDragged      ║
     * ╚══════════════════════════════════════╝
     * Gère le déplacement des poignées des sliders lorsque la souris est déplacée.
     * Met à jour les valeurs de volumeLevel et brightnessLevel en fonction de la position de la poignée.
     *
     * @param x Coordonnée X de la souris.
     * @param y Coordonnée Y de la souris.
     */
    public void handleMouseDragged(int x, int y) {
        // Si la poignée du slider de volume est en train d'être déplacée.
        if (draggingVolumeKnob) {
            // Calcule la nouvelle position X de la poignée, en s'assurant qu'elle reste dans les limites du slider.
            int newX = Math.max(volumeSlider.x, Math.min(x - volumeSliderKnob.width / 2, volumeSlider.x + volumeSlider.width - volumeSliderKnob.width));

            // Met à jour la position X de la poignée.
            volumeSliderKnob.x = newX;

            // Calcule la position relative de la poignée (entre 0 et 1).
            float position = (float) (newX - volumeSlider.x) / (volumeSlider.width - volumeSliderKnob.width);

            // Calcule la nouvelle valeur du volume en fonction de la position.
            volumeLevel = volumeSliderMin + position * (volumeSliderMax - volumeSliderMin);

            // Met à jour le volume de la musique.
            updateVolume();

            // Affiche la nouvelle valeur du volume dans la console.
            System.out.println("Volume ajusté à : " + volumeLevel + " dB");
        }

        // Si la poignée du slider de luminosité est en train d'être déplacée.
        if (draggingBrightnessKnob) {
            // Calcule la nouvelle position X de la poignée, en s'assurant qu'elle reste dans les limites du slider.
            int newX = Math.max(brightnessSlider.x, Math.min(x - brightnessSliderKnob.width / 2, brightnessSlider.x + brightnessSlider.width - brightnessSliderKnob.width));

            // Met à jour la position X de la poignée.
            brightnessSliderKnob.x = newX;

            // Calcule la position relative de la poignée (entre 0 et 1).
            float position = (float) (newX - brightnessSlider.x) / (brightnessSlider.width - brightnessSliderKnob.width);

            // Calcule la nouvelle valeur de la luminosité (entre 0 et 1).
            brightnessLevel = (brightnessSliderMin + position * (brightnessSliderMax - brightnessSliderMin)) / 100.0f;

            // Affiche la nouvelle valeur de la luminosité dans la console.
            System.out.println("Luminosité ajustée à : " + (brightnessLevel * 100) + "%");
        }
    }

    /**
     * ╔══════════════════════════════════════╗
     * ║    MÉTHODE : handleMouseMoved        ║
     * ╚══════════════════════════════════════╝
     * Gère le survol de la souris sur les boutons.
     * Met à jour l'état de survol du bouton "Paramètres".
     *
     * @param x Coordonnée X de la souris.
     * @param y Coordonnée Y de la souris.
     */
    public void handleMouseMoved(int x, int y) {
        // Met à jour l'état de survol du bouton "Paramètres" (true si la souris est dessus).
        settingsButtonHovered = settingsButton.contains(x, y);
    }

    /**
     * ╔══════════════════════════════════════╗
     * ║    MÉTHODE : handleMouseReleased     ║
     * ╚══════════════════════════════════════╝
     * Gère la fin du déplacement des poignées des sliders.
     * Désactive les indicateurs de déplacement (draggingVolumeKnob et draggingBrightnessKnob).
     */
    public void handleMouseReleased() {
        // Désactive le déplacement de la poignée du slider de volume.
        draggingVolumeKnob = false;

        // Désactive le déplacement de la poignée du slider de luminosité.
        draggingBrightnessKnob = false;
    }

    /**
     * ╔══════════════════════════════════════╗
     * ║    MÉTHODE : getXforCenteredText     ║
     * ╚══════════════════════════════════════╝
     * Calcule la position X pour centrer un texte à l'écran.
     *
     * @param text Le texte à centrer.
     * @return La position X pour centrer le texte.
     */
    private int getXforCenteredText(String text) {
        // Calcule la largeur du texte en pixels en utilisant les métriques de la police.
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

        // Retourne la position X pour centrer le texte (milieu de l'écran - moitié de la largeur du texte).
        return gp.screenWidth / 2 - length / 2;
    }

    /**
     * ╔══════════════════════════════════════╗
     * ║    MÉTHODE : getXforCenteredText (surchargée) ║
     * ╚══════════════════════════════════════╝
     * Calcule la position X pour centrer un texte dans un rectangle.
     *
     * @param text Le texte à centrer.
     * @param rect Le rectangle dans lequel centrer le texte.
     * @return La position X pour centrer le texte.
     */
    private int getXforCenteredText(String text, Rectangle rect) {
        // Calcule la largeur du texte en pixels.
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

        // Retourne la position X pour centrer le texte dans le rectangle.
        return rect.x + (rect.width - length) / 2;
    }

    /**
     * ╔══════════════════════════════════════╗
     * ║    CLASSE INTERNE : Particle         ║
     * ╚══════════════════════════════════════╝
     * Cette classe interne gère les particules flottantes affichées sur l'écran titre.
     * Les particules sont des carrés blancs semi-transparents qui tombent lentement,
     * créant un effet visuel d'ambiance (comme des flocons ou des pétales).
     */
    private class Particle {
        // ╔══════════════════════╗
        // ║    CHAMPS            ║
        // ╚══════════════════════╝

        /** Coordonnée X de la particule. */
        float x;

        /** Coordonnée Y de la particule. */
        float y;

        /** Vitesse de déplacement horizontale de la particule (peut être positive ou négative). */
        float speedX;

        /** Vitesse de déplacement verticale de la particule (toujours positive pour tomber). */
        float speedY;

        /** Taille de la particule (carré, en pixels). */
        int size;

        /**
         * ╔══════════════════════╗
         * ║    CONSTRUCTEUR      ║
         * ╚══════════════════════╝
         * Initialise une nouvelle particule en appelant la méthode reset().
         */
        Particle() {
            // Appelle reset() pour initialiser les valeurs de la particule.
            reset();
        }

        /**
         * ╔══════════════════════╗
         * ║    MÉTHODE : reset   ║
         * ╚══════════════════════╝
         * Réinitialise la particule à une position aléatoire en haut de l'écran,
         * avec une vitesse et une taille aléatoires.
         */
        void reset() {
            // Positionne la particule à une coordonnée X aléatoire sur toute la largeur de l'écran.
            x = random.nextInt(gp.screenWidth);

            // Positionne la particule juste au-dessus de l'écran (Y = -10) pour qu'elle apparaisse progressivement.
            y = -10;

            // Définit une vitesse horizontale aléatoire entre -1 et 1 (mouvement latéral léger).
            speedX = random.nextFloat() * 2 - 1;

            // Définit une vitesse verticale aléatoire entre 1 et 3 (chute lente).
            speedY = random.nextFloat() * 2 + 1;

            // Définit une taille aléatoire entre 3 et 8 pixels.
            size = random.nextInt(5) + 3;
        }

        /**
         * ╔══════════════════════╗
         * ║    MÉTHODE : update  ║
         * ╚══════════════════════╝
         * Met à jour la position de la particule en fonction de sa vitesse.
         */
        void update() {
            // Déplace la particule horizontalement en ajoutant sa vitesse X.
            x += speedX;

            // Déplace la particule verticalement en ajoutant sa vitesse Y (chute).
            y += speedY;
        }

        /**
         * ╔══════════════════════╗
         * ║    MÉTHODE : draw    ║
         * ╚══════════════════════╝
         * Dessine la particule à l'écran sous forme d'un carré blanc semi-transparent.
         *
         * @param g2 Le contexte graphique pour dessiner.
         */
        void draw(Graphics2D g2) {
            // Définit la couleur de la particule (blanc avec une opacité de 150/255).
            g2.setColor(new Color(255, 255, 255, 150));

            // Dessine un carré à la position de la particule avec la taille définie.
            g2.fillRect((int) x, (int) y, size, size);
        }
    }

    /**
     * ╔══════════════════════════════════════╗
     * ║    CLASSE INTERNE : PlayerCharacter  ║
     * ╚══════════════════════════════════════╝
     * Cette classe interne gère le personnage animé qui court vers la gauche
     * sur les montagnes dans l'écran titre. Le personnage utilise une série de sprites
     * pour l'animation de marche et se déplace en boucle de droite à gauche.
     */
    private class PlayerCharacter {
        // ╔══════════════════════╗
        // ║    CHAMPS            ║
        // ╚══════════════════════╝

        /** Tableau des sprites pour l'animation de marche du personnage. */
        private BufferedImage[] sprites;

        /** Index du sprite actuel dans l'animation (de 0 à sprites.length - 1). */
        private int spriteIndex = 0;

        /** Timer pour gérer la vitesse de l'animation (change de sprite toutes les 0.2 secondes). */
        private double animationTimer = 0;

        /** Position X du personnage sur l'écran. */
        private float x;

        /** Position Y du personnage sur l'écran. */
        private float y;

        /** Vitesse de déplacement du personnage (en pixels par frame). */
        private float speed = 2.0f;

        /**
         * ╔══════════════════════╗
         * ║    CONSTRUCTEUR      ║
         * ╚══════════════════════╝
         * Initialise le personnage avec une position de départ à droite de l'écran,
         * sur les montagnes.
         */
        public PlayerCharacter() {
            // Positionne le personnage à l'extrême droite de l'écran.
            x = gp.screenWidth;

            // Positionne le personnage sur les montagnes (ajusté pour la hauteur du sprite, 48 pixels).
            y = gp.screenHeight / 2 - 100 + 100 - 48;
        }

        /**
         * ╔══════════════════════╗
         * ║    MÉTHODE : setSprites ║
         * ╚══════════════════════╝
         * Définit les sprites du personnage et les inverse horizontalement
         * pour simuler une marche vers la gauche.
         *
         * @param sprites Les sprites originaux (orientés vers la droite).
         */
        public void setSprites(BufferedImage[] sprites) {
            // Crée un nouveau tableau pour stocker les sprites inversés.
            this.sprites = new BufferedImage[sprites.length];

            // Parcourt chaque sprite et l'inverse horizontalement.
            for (int i = 0; i < sprites.length; i++) {
                this.sprites[i] = createFlippedImage(sprites[i]);
            }
        }

        /**
         * ╔══════════════════════╗
         * ║    MÉTHODE : createFlippedImage ║
         * ╚══════════════════════╝
         * Inverse une image horizontalement pour simuler une marche vers la gauche.
         *
         * @param original L'image originale à inverser.
         * @return L'image inversée.
         */
        private BufferedImage createFlippedImage(BufferedImage original) {
            // Récupère la largeur et la hauteur de l'image originale.
            int width = original.getWidth();
            int height = original.getHeight();

            // Crée une nouvelle image vide avec les mêmes dimensions et un type ARGB (support de la transparence).
            BufferedImage flipped = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            // Parcourt chaque pixel de l'image et inverse sa position X.
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Copie le pixel de la position (x, y) à la position (width - 1 - x, y).
                    flipped.setRGB(width - 1 - x, y, original.getRGB(x, y));
                }
            }

            // Retourne l'image inversée.
            return flipped;
        }

        /**
         * ╔══════════════════════╗
         * ║    MÉTHODE : update  ║
         * ╚══════════════════════╝
         * Met à jour la position et l'animation du personnage.
         * Déplace le personnage vers la gauche et gère l'animation des sprites.
         */
        public void update() {
            // Déplace le personnage vers la gauche en soustrayant sa vitesse.
            x -= speed;

            // Si le personnage sort de l'écran à gauche (X < -48), le remet à droite.
            if (x < -48) { // 48 est la largeur du sprite.
                x = gp.screenWidth;
            }

            // Met à jour le timer d'animation.
            animationTimer += 0.1;

            // Si le timer atteint 0.2 secondes, change de sprite.
            if (animationTimer >= 0.2) {
                // Passe au sprite suivant (boucle sur la longueur du tableau).
                spriteIndex = (spriteIndex + 1) % sprites.length;

                // Réinitialise le timer.
                animationTimer = 0;
            }
        }

        /**
         * ╔══════════════════════╗
         * ║    MÉTHODE : draw    ║
         * ╚══════════════════════╝
         * Dessine le personnage à l'écran en utilisant le sprite actuel.
         *
         * @param g2 Le contexte graphique pour dessiner.
         */
        public void draw(Graphics2D g2) {
            // Vérifie que les sprites sont initialisés et que l'index est valide.
            if (sprites != null && spriteIndex < sprites.length) {
                // Dessine le sprite actuel à la position (x, y) avec une taille de 48x48 pixels.
                g2.drawImage(sprites[spriteIndex], (int) x, (int) y, 48, 48, null);
            }
        }
    }
}