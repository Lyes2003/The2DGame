/**
 * ╔══════════════════════════════════════════════════════╗
 * ║                   CLASSE UI_TEST                     ║
 * ╚══════════════════════════════════════════════════════╝
 * Cette classe est dédiée aux tests unitaires de la classe ui.
 * Elle teste toutes les fonctionnalités principales de l'interface utilisateur,
 * y compris le chargement des ressources, les interactions utilisateur, les animations,
 * et les dessins des écrans (titre et paramètres).
 *
 * La classe inclut :
 * - Des méthodes de test pour chaque fonctionnalité clé de ui.
 * - Une méthode main pour exécuter tous les tests.
 */
package mainproject.ui;

import mainproject.GamePanel;
import mainproject.KeyHandler;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ui_test {
    // ╔══════════════════════════════════════╗
    // ║           CHAMPS PRINCIPAUX          ║
    // ╚══════════════════════════════════════╝
    // Ces champs sont utilisés pour configurer l'environnement de test.

    /** Instance de GamePanel simulée pour les tests. */
    private static GamePanel gp;

    /** Instance de KeyHandler simulée pour les tests (nécessaire pour ui). */
    private static KeyHandler keyH;

    /** Instance de ui à tester. */
    private static ui uiInstance;

    // ╔══════════════════════════════════════╗
    // ║           MÉTHODES DE TEST           ║
    // ╚══════════════════════════════════════╝
    // Ces méthodes testent chaque fonctionnalité clé de la classe ui.

    /**
     * ╔══════════════════════╗
     * ║    TEST : loadFonts  ║
     * ╚══════════════════════╝
     * Teste la méthode loadFonts() pour vérifier si les polices sont chargées correctement.
     * Vérifie que titleFont et menuFont ne sont pas null après le chargement.
     */
    public void testLoadFonts() {
        System.out.println("┌──── TEST : ui.loadFonts ────┐");

        // Appelle la méthode loadFonts via réflexion (car elle est privée).
        try {
            Method loadFontsMethod = ui.class.getDeclaredMethod("loadFonts");
            loadFontsMethod.setAccessible(true);
            loadFontsMethod.invoke(uiInstance);

            // Récupère les champs titleFont et menuFont via réflexion.
            Field titleFontField = ui.class.getDeclaredField("titleFont");
            titleFontField.setAccessible(true);
            Font titleFont = (Font) titleFontField.get(uiInstance);

            Field menuFontField = ui.class.getDeclaredField("menuFont");
            menuFontField.setAccessible(true);
            Font menuFont = (Font) menuFontField.get(uiInstance);

            // Vérifie que les polices ne sont pas null.
            if (titleFont != null && menuFont != null) {
                System.out.println("✅ Test réussi : Polices chargées correctement");
            } else {
                System.out.println("❌ Test échoué : Polices non chargées (titleFont ou menuFont est null)");
            }
        } catch (Exception e) {
            System.out.println("❌ Test échoué : Erreur lors du chargement des polices - " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("└───────────────────────────────────────────┘");
    }

    /**
     * ╔══════════════════════╗
     * ║    TEST : loadMusic  ║
     * ╚══════════════════════╝
     * Teste la méthode loadMusic() pour vérifier si la musique est chargée correctement.
     * Vérifie que musicClip n'est pas null après le chargement.
     */
    public void testLoadMusic() {
        System.out.println("┌──── TEST : ui.loadMusic ────┐");

        // Appelle la méthode loadMusic via réflexion.
        try {
            Method loadMusicMethod = ui.class.getDeclaredMethod("loadMusic");
            loadMusicMethod.setAccessible(true);
            loadMusicMethod.invoke(uiInstance);

            // Récupère le champ musicClip via réflexion.
            Field musicClipField = ui.class.getDeclaredField("musicClip");
            musicClipField.setAccessible(true);
            Clip musicClip = (Clip) musicClipField.get(uiInstance);

            // Vérifie que le clip audio n'est pas null.
            if (musicClip != null) {
                System.out.println("✅ Test réussi : Musique chargée correctement");
            } else {
                System.out.println("❌ Test échoué : Musique non chargée (musicClip est null)");
            }
        } catch (Exception e) {
            System.out.println("❌ Test échoué : Erreur lors du chargement de la musique - " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("└───────────────────────────────────────────┘");
    }

    /**
     * ╔══════════════════════╗
     * ║    TEST : loadBackground  ║
     * ╚══════════════════════╝
     * Teste la méthode loadBackground() pour vérifier si les images de fond sont chargées correctement.
     * Vérifie que backgroundSky, backgroundMountains, et backgroundGround ne sont pas null.
     */
    public void testLoadBackground() {
        System.out.println("┌──── TEST : ui.loadBackground ────┐");

        // Appelle la méthode loadBackground via réflexion.
        try {
            Method loadBackgroundMethod = ui.class.getDeclaredMethod("loadBackground");
            loadBackgroundMethod.setAccessible(true);
            loadBackgroundMethod.invoke(uiInstance);

            // Récupère les champs des images de fond via réflexion.
            Field backgroundSkyField = ui.class.getDeclaredField("backgroundSky");
            backgroundSkyField.setAccessible(true);
            BufferedImage backgroundSky = (BufferedImage) backgroundSkyField.get(uiInstance);

            Field backgroundMountainsField = ui.class.getDeclaredField("backgroundMountains");
            backgroundMountainsField.setAccessible(true);
            BufferedImage backgroundMountains = (BufferedImage) backgroundMountainsField.get(uiInstance);

            Field backgroundGroundField = ui.class.getDeclaredField("backgroundGround");
            backgroundGroundField.setAccessible(true);
            BufferedImage backgroundGround = (BufferedImage) backgroundGroundField.get(uiInstance);

            // Vérifie que toutes les images de fond ne sont pas null.
            if (backgroundSky != null && backgroundMountains != null && backgroundGround != null) {
                System.out.println("✅ Test réussi : Images de fond chargées correctement");
            } else {
                System.out.println("❌ Test échoué : Une ou plusieurs images de fond sont null");
            }
        } catch (Exception e) {
            System.out.println("❌ Test échoué : Erreur lors du chargement des images de fond - " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("└───────────────────────────────────────────┘");
    }

    /**
     * ╔══════════════════════╗
     * ║    TEST : updateVolume  ║
     * ╚══════════════════════╝
     * Teste la méthode updateVolume() pour vérifier si le volume est correctement appliqué.
     * Simule un changement de volume et vérifie que le clip audio est mis à jour.
     */
    public void testUpdateVolume() {
        System.out.println("┌──── TEST : ui.updateVolume ────┐");

        try {
            // Récupère le champ musicClip via réflexion.
            Field musicClipField = ui.class.getDeclaredField("musicClip");
            musicClipField.setAccessible(true);
            Clip musicClip = (Clip) musicClipField.get(uiInstance);

            // Simule un clip audio non null (si nécessaire).
            if (musicClip == null) {
                System.out.println("⚠️ Test sauté : musicClip est null (chargez d'abord la musique)");
                System.out.println("└───────────────────────────────────────────┘");
                return;
            }

            // Change le volume à une valeur spécifique.
            Field volumeLevelField = ui.class.getDeclaredField("volumeLevel");
            volumeLevelField.setAccessible(true);
            volumeLevelField.setFloat(uiInstance, -20.0f);

            // Appelle la méthode updateVolume via réflexion.
            Method updateVolumeMethod = ui.class.getDeclaredMethod("updateVolume");
            updateVolumeMethod.setAccessible(true);
            updateVolumeMethod.invoke(uiInstance);

            // Vérifie que le volume a été appliqué (on ne peut pas vérifier directement le gain, mais on s'assure qu'il n'y a pas d'erreur).
            System.out.println("✅ Test réussi : Volume mis à jour sans erreur");
        } catch (Exception e) {
            System.out.println("❌ Test échoué : Erreur lors de la mise à jour du volume - " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("└───────────────────────────────────────────┘");
    }

    /**
     * ╔══════════════════════╗
     * ║    TEST : updateSliderKnobs  ║
     * ╚══════════════════════╝
     * Teste la méthode updateSliderKnobs() pour vérifier si les positions des poignées des sliders
     * sont correctement mises à jour en fonction des valeurs de volume et de luminosité.
     */
    public void testUpdateSliderKnobs() {
        System.out.println("┌──── TEST : ui.updateSliderKnobs ────┐");

        try {
            // Change les valeurs de volumeLevel et brightnessLevel.
            Field volumeLevelField = ui.class.getDeclaredField("volumeLevel");
            volumeLevelField.setAccessible(true);
            volumeLevelField.setFloat(uiInstance, -40.0f); // Milieu de la plage (-80 à 0).

            Field brightnessLevelField = ui.class.getDeclaredField("brightnessLevel");
            brightnessLevelField.setAccessible(true);
            brightnessLevelField.setFloat(uiInstance, 0.5f); // Milieu de la plage (0 à 1).

            // Appelle la méthode updateSliderKnobs via réflexion.
            Method updateSliderKnobsMethod = ui.class.getDeclaredMethod("updateSliderKnobs");
            updateSliderKnobsMethod.setAccessible(true);
            updateSliderKnobsMethod.invoke(uiInstance);

            // Récupère les positions des poignées.
            Field volumeSliderKnobField = ui.class.getDeclaredField("volumeSliderKnob");
            volumeSliderKnobField.setAccessible(true);
            Rectangle volumeSliderKnob = (Rectangle) volumeSliderKnobField.get(uiInstance);

            Field brightnessSliderKnobField = ui.class.getDeclaredField("brightnessSliderKnob");
            brightnessSliderKnobField.setAccessible(true);
            Rectangle brightnessSliderKnob = (Rectangle) brightnessSliderKnobField.get(uiInstance);

            // Vérifie que les positions des poignées sont cohérentes (approximativement au milieu).
            if (volumeSliderKnob.x > 0 && brightnessSliderKnob.x > 0) {
                System.out.println("✅ Test réussi : Positions des poignées mises à jour correctement");
            } else {
                System.out.println("❌ Test échoué : Positions des poignées incorrectes");
            }
        } catch (Exception e) {
            System.out.println("❌ Test échoué : Erreur lors de la mise à jour des sliders - " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("└───────────────────────────────────────────┘");
    }

    /**
     * ╔══════════════════════╗
     * ║    TEST : playMusic  ║
     * ╚══════════════════════╝
     * Teste la méthode playMusic() pour vérifier si la musique est jouée correctement.
     * Vérifie que le clip audio est en cours de lecture après l'appel.
     */
    public void testPlayMusic() {
        System.out.println("┌──── TEST : ui.playMusic ────┐");

        try {
            // S'assure que la musique est activée.
            Field musicEnabledField = ui.class.getDeclaredField("musicEnabled");
            musicEnabledField.setAccessible(true);
            musicEnabledField.setBoolean(uiInstance, true);

            // Appelle la méthode playMusic.
            uiInstance.playMusic();

            // Récupère le champ musicClip.
            Field musicClipField = ui.class.getDeclaredField("musicClip");
            musicClipField.setAccessible(true);
            Clip musicClip = (Clip) musicClipField.get(uiInstance);

            // Vérifie si le clip est en cours de lecture (ou au moins que l'appel n'a pas généré d'erreur).
            if (musicClip != null) {
                System.out.println("✅ Test réussi : Musique jouée sans erreur");
            } else {
                System.out.println("❌ Test échoué : Clip audio non initialisé");
            }
        } catch (Exception e) {
            System.out.println("❌ Test échoué : Erreur lors de la lecture de la musique - " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("└───────────────────────────────────────────┘");
    }

    /**
     * ╔══════════════════════╗
     * ║    TEST : stopMusic  ║
     * ╚══════════════════════╝
     * Teste la méthode stopMusic() pour vérifier si la musique est arrêtée correctement.
     * Vérifie que le clip audio n'est plus en cours de lecture après l'appel.
     */
    public void testStopMusic() {
        System.out.println("┌──── TEST : ui.stopMusic ────┐");

        try {
            // Appelle la méthode stopMusic.
            uiInstance.stopMusic();

            // Récupère le champ musicClip.
            Field musicClipField = ui.class.getDeclaredField("musicClip");
            musicClipField.setAccessible(true);
            Clip musicClip = (Clip) musicClipField.get(uiInstance);

            // Vérifie que le clip n'est pas en cours de lecture.
            if (musicClip != null && !musicClip.isRunning()) {
                System.out.println("✅ Test réussi : Musique arrêtée correctement");
            } else {
                System.out.println("❌ Test échoué : Musique non arrêtée ou clip non initialisé");
            }
        } catch (Exception e) {
            System.out.println("❌ Test échoué : Erreur lors de l'arrêt de la musique - " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("└───────────────────────────────────────────┘");
    }

    /**
     * ╔══════════════════════╗
     * ║    TEST : updateParallax  ║
     * ╚══════════════════════╝
     * Teste la méthode updateParallax() pour vérifier si les décalages des couches de fond
     * sont correctement mis à jour.
     */
    public void testUpdateParallax() {
        System.out.println("┌──── TEST : ui.updateParallax ────┐");

        try {
            // Récupère les valeurs initiales des décalages.
            Field skyOffsetField = ui.class.getDeclaredField("skyOffset");
            skyOffsetField.setAccessible(true);
            double initialSkyOffset = skyOffsetField.getDouble(uiInstance);

            Field mountainsOffsetField = ui.class.getDeclaredField("mountainsOffset");
            mountainsOffsetField.setAccessible(true);
            double initialMountainsOffset = mountainsOffsetField.getDouble(uiInstance);

            Field groundOffsetField = ui.class.getDeclaredField("groundOffset");
            groundOffsetField.setAccessible(true);
            double initialGroundOffset = groundOffsetField.getDouble(uiInstance);

            // Appelle la méthode updateParallax via réflexion.
            Method updateParallaxMethod = ui.class.getDeclaredMethod("updateParallax");
            updateParallaxMethod.setAccessible(true);
            updateParallaxMethod.invoke(uiInstance);

            // Récupère les nouvelles valeurs des décalages.
            double newSkyOffset = skyOffsetField.getDouble(uiInstance);
            double newMountainsOffset = mountainsOffsetField.getDouble(uiInstance);
            double newGroundOffset = groundOffsetField.getDouble(uiInstance);

            // Vérifie que les décalages ont été mis à jour (diminués).
            if (newSkyOffset < initialSkyOffset && newMountainsOffset < initialMountainsOffset && newGroundOffset < initialGroundOffset) {
                System.out.println("✅ Test réussi : Décalages parallax mis à jour correctement");
            } else {
                System.out.println("❌ Test échoué : Décalages parallax non mis à jour correctement");
            }
        } catch (Exception e) {
            System.out.println("❌ Test échoué : Erreur lors de la mise à jour du parallax - " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("└───────────────────────────────────────────┘");
    }

    /**
     * ╔══════════════════════╗
     * ║    TEST : updateParticles  ║
     * ╚══════════════════════╝
     * Teste la méthode updateParticles() pour vérifier si les particules sont correctement mises à jour.
     * Vérifie que les positions des particules changent et qu'elles sont réinitialisées si elles sortent de l'écran.
     */
    public void testUpdateParticles() {
        System.out.println("┌──── TEST : ui.updateParticles ────┐");

        try {
            // Récupère la liste des particules via réflexion.
            Field particlesField = ui.class.getDeclaredField("particles");
            particlesField.setAccessible(true);
            java.util.List<?> particles = (java.util.List<?>) particlesField.get(uiInstance);

            // Récupère la première particule pour tester.
            Object particle = particles.get(0);
            Field yField = particle.getClass().getDeclaredField("y");
            yField.setAccessible(true);
            float initialY = yField.getFloat(particle);

            // Appelle la méthode updateParticles via réflexion.
            Method updateParticlesMethod = ui.class.getDeclaredMethod("updateParticles");
            updateParticlesMethod.setAccessible(true);
            updateParticlesMethod.invoke(uiInstance);

            // Récupère la nouvelle position Y de la particule.
            float newY = yField.getFloat(particle);

            // Vérifie que la position Y a changé (la particule a bougé vers le bas).
            if (newY > initialY) {
                System.out.println("✅ Test réussi : Particules mises à jour correctement");
            } else {
                System.out.println("❌ Test échoué : Position des particules non mise à jour");
            }
        } catch (Exception e) {
            System.out.println("❌ Test échoué : Erreur lors de la mise à jour des particules - " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("└───────────────────────────────────────────┘");
    }

    /**
     * ╔══════════════════════╗
     * ║    TEST : updateTitleShake  ║
     * ╚══════════════════════╝
     * Teste la méthode updateTitleShake() pour vérifier si l'effet de tremblement du titre
     * est correctement mis à jour.
     */
    public void testUpdateTitleShake() {
        System.out.println("┌──── TEST : ui.updateTitleShake ────┐");

        try {
            // Récupère les valeurs initiales des décalages de tremblement.
            Field titleShakeXField = ui.class.getDeclaredField("titleShakeX");
            titleShakeXField.setAccessible(true);
            int initialTitleShakeX = titleShakeXField.getInt(uiInstance);

            Field titleShakeYField = ui.class.getDeclaredField("titleShakeY");
            titleShakeYField.setAccessible(true);
            int initialTitleShakeY = titleShakeYField.getInt(uiInstance);

            // Force le timer à déclencher un tremblement.
            Field titleShakeTimerField = ui.class.getDeclaredField("titleShakeTimer");
            titleShakeTimerField.setAccessible(true);
            titleShakeTimerField.setDouble(uiInstance, 1.0);

            // Appelle la méthode updateTitleShake via réflexion.
            Method updateTitleShakeMethod = ui.class.getDeclaredMethod("updateTitleShake");
            updateTitleShakeMethod.setAccessible(true);
            updateTitleShakeMethod.invoke(uiInstance);

            // Récupère les nouvelles valeurs des décalages.
            int newTitleShakeX = titleShakeXField.getInt(uiInstance);
            int newTitleShakeY = titleShakeYField.getInt(uiInstance);

            // Vérifie que les décalages ont changé.
            if (newTitleShakeX != initialTitleShakeX || newTitleShakeY != initialTitleShakeY) {
                System.out.println("✅ Test réussi : Effet de tremblement mis à jour correctement");
            } else {
                System.out.println("❌ Test échoué : Effet de tremblement non mis à jour");
            }
        } catch (Exception e) {
            System.out.println("❌ Test échoué : Erreur lors de la mise à jour de l'effet de tremblement - " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("└───────────────────────────────────────────┘");
    }

    /**
     * ╔══════════════════════╗
     * ║    TEST : handleMouseClick  ║
     * ╚══════════════════════╝
     * Teste la méthode handleMouseClick() pour vérifier si les clics sur les boutons
     * déclenchent les actions correctes (changement d'état, bascule de la musique, etc.).
     */
    public void testHandleMouseClick() {
        System.out.println("┌──── TEST : ui.handleMouseClick ────┐");

        try {
            // Test 1 : Clic sur le bouton "Paramètres" dans l'écran titre.
            gp.Game_state = gp.Start_screen;
            Field settingsButtonField = ui.class.getDeclaredField("settingsButton");
            settingsButtonField.setAccessible(true);
            Rectangle settingsButton = (Rectangle) settingsButtonField.get(uiInstance);

            // Simule un clic au centre du bouton "Paramètres".
            int clickX = settingsButton.x + settingsButton.width / 2;
            int clickY = settingsButton.y + settingsButton.height / 2;
            uiInstance.handleMouseClick(clickX, clickY);

            // Vérifie que l'état du jeu a changé à settings_screen.
            if (gp.Game_state == gp.settings_screen) {
                System.out.println("✅ Test réussi : Clic sur le bouton Paramètres fonctionne");
            } else {
                System.out.println("❌ Test échoué : Clic sur le bouton Paramètres n'a pas changé l'état");
            }

            // Test 2 : Clic sur le bouton "Retour" dans l'écran des paramètres.
            Field backButtonField = ui.class.getDeclaredField("backButton");
            backButtonField.setAccessible(true);
            Rectangle backButton = (Rectangle) backButtonField.get(uiInstance);

            // Simule un clic au centre du bouton "Retour".
            clickX = backButton.x + backButton.width / 2;
            clickY = backButton.y + backButton.height / 2;
            uiInstance.handleMouseClick(clickX, clickY);

            // Vérifie que l'état du jeu est revenu à Start_screen.
            if (gp.Game_state == gp.Start_screen) {
                System.out.println("✅ Test réussi : Clic sur le bouton Retour fonctionne");
            } else {
                System.out.println("❌ Test échoué : Clic sur le bouton Retour n'a pas changé l'état");
            }
        } catch (Exception e) {
            System.out.println("❌ Test échoué : Erreur lors de la gestion des clics - " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("└───────────────────────────────────────────┘");
    }

    /**
     * ╔══════════════════════╗
     * ║    TEST : handleMouseDragged  ║
     * ╚══════════════════════╝
     * Teste la méthode handleMouseDragged() pour vérifier si le déplacement des sliders
     * met à jour correctement les valeurs de volume et de luminosité.
     */
    public void testHandleMouseDragged() {
        System.out.println("┌──── TEST : ui.handleMouseDragged ────┐");

        try {
            // Active le mode de déplacement des sliders.
            Field draggingVolumeKnobField = ui.class.getDeclaredField("draggingVolumeKnob");
            draggingVolumeKnobField.setAccessible(true);
            draggingVolumeKnobField.setBoolean(uiInstance, true);

            Field draggingBrightnessKnobField = ui.class.getDeclaredField("draggingBrightnessKnob");
            draggingBrightnessKnobField.setAccessible(true);
            draggingBrightnessKnobField.setBoolean(uiInstance, true);

            // Simule un déplacement de la souris.
            uiInstance.handleMouseDragged(500, 500);

            // Récupère les nouvelles valeurs de volumeLevel et brightnessLevel.
            Field volumeLevelField = ui.class.getDeclaredField("volumeLevel");
            volumeLevelField.setAccessible(true);
            float newVolumeLevel = volumeLevelField.getFloat(uiInstance);

            Field brightnessLevelField = ui.class.getDeclaredField("brightnessLevel");
            brightnessLevelField.setAccessible(true);
            float newBrightnessLevel = brightnessLevelField.getFloat(uiInstance);

            // Vérifie que les valeurs ont été mises à jour.
            if (newVolumeLevel != -10.0f && newBrightnessLevel != 1.0f) {
                System.out.println("✅ Test réussi : Sliders mis à jour correctement");
            } else {
                System.out.println("❌ Test échoué : Sliders non mis à jour");
            }
        } catch (Exception e) {
            System.out.println("❌ Test échoué : Erreur lors du déplacement des sliders - " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("└───────────────────────────────────────────┘");
    }

    /**
     * ╔══════════════════════╗
     * ║    TEST : handleMouseMoved  ║
     * ╚══════════════════════╝
     * Teste la méthode handleMouseMoved() pour vérifier si l'état de survol du bouton
     * "Paramètres" est correctement mis à jour.
     */
    public void testHandleMouseMoved() {
        System.out.println("┌──── TEST : ui.handleMouseMoved ────┐");

        try {
            // Récupère le bouton "Paramètres".
            Field settingsButtonField = ui.class.getDeclaredField("settingsButton");
            settingsButtonField.setAccessible(true);
            Rectangle settingsButton = (Rectangle) settingsButtonField.get(uiInstance);

            // Simule un mouvement de la souris sur le bouton.
            int mouseX = settingsButton.x + settingsButton.width / 2;
            int mouseY = settingsButton.y + settingsButton.height / 2;
            uiInstance.handleMouseMoved(mouseX, mouseY);

            // Récupère l'état de survol.
            Field settingsButtonHoveredField = ui.class.getDeclaredField("settingsButtonHovered");
            settingsButtonHoveredField.setAccessible(true);
            boolean settingsButtonHovered = settingsButtonHoveredField.getBoolean(uiInstance);

            // Vérifie que l'état de survol est true.
            if (settingsButtonHovered) {
                System.out.println("✅ Test réussi : Survol du bouton Paramètres détecté");
            } else {
                System.out.println("❌ Test échoué : Survol du bouton Paramètres non détecté");
            }
        } catch (Exception e) {
            System.out.println("❌ Test échoué : Erreur lors de la gestion du survol - " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("└───────────────────────────────────────────┘");
    }

    /**
     * ╔══════════════════════╗
     * ║    TEST : SetPlayerWalkingToTheRight  ║
     * ╚══════════════════════╝
     * Teste la méthode SetPlayerWalkingToTheRight() pour vérifier si les sprites du personnage
     * sont correctement chargés et passés au PlayerCharacter.
     */
    public void testSetPlayerWalkingToTheRight() {
        System.out.println("┌──── TEST : ui.SetPlayerWalkingToTheRight ────┐");

        try {
            // Appelle la méthode SetPlayerWalkingToTheRight.
            uiInstance.SetPlayerWalkingToTheRight();

            // Récupère le champ playerCharacter via réflexion.
            Field playerCharacterField = ui.class.getDeclaredField("playerCharacter");
            playerCharacterField.setAccessible(true);
            Object playerCharacter = playerCharacterField.get(uiInstance);

            // Récupère les sprites du PlayerCharacter.
            Field spritesField = playerCharacter.getClass().getDeclaredField("sprites");
            spritesField.setAccessible(true);
            BufferedImage[] sprites = (BufferedImage[]) spritesField.get(playerCharacter);

            // Vérifie que les sprites ont été chargés (tableau non null et de longueur 6).
            if (sprites != null && sprites.length == 6) {
                System.out.println("✅ Test réussi : Sprites du personnage chargés correctement");
            } else {
                System.out.println("❌ Test échoué : Sprites du personnage non chargés correctement");
            }
        } catch (Exception e) {
            System.out.println("❌ Test échoué : Erreur lors du chargement des sprites - " + e.getMessage());
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
     * Point d'entrée principal pour exécuter tous les tests de la classe ui.
     * Configure l'environnement de test et appelle chaque méthode de test.
     *
     * @param args Arguments de la ligne de commande (non utilisés).
     */
    public static void main(String[] args) {
        System.out.println("══════ DÉBUT DES TESTS DE LA CLASSE UI ══════");

        // Configure l'environnement de test.
        gp = new GamePanel();

        keyH = new KeyHandler(); // Crée une instance de KeyHandler.

        // Crée une instance de ui pour les tests.
        uiInstance = new ui(gp);

        // Crée une instance de ui_test pour exécuter les tests.
        ui_test tester = new ui_test();

        // Exécute tous les tests.
        tester.testLoadFonts();
        tester.testLoadMusic();
        tester.testLoadBackground();
        tester.testUpdateVolume();
        tester.testUpdateSliderKnobs();
        tester.testPlayMusic();
        tester.testStopMusic();
        tester.testUpdateParallax();
        tester.testUpdateParticles();
        tester.testUpdateTitleShake();
        tester.testHandleMouseClick();
        tester.testHandleMouseDragged();
        tester.testHandleMouseMoved();
        tester.testSetPlayerWalkingToTheRight();

        System.out.println("══════ FIN DES TESTS DE LA CLASSE UI ══════");
    }
}