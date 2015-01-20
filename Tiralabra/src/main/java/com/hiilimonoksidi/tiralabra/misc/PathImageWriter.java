package com.hiilimonoksidi.tiralabra.misc;

import com.hiilimonoksidi.tiralabra.graph.Path;
import com.hiilimonoksidi.tiralabra.pathfinding.PathfindingAlgorithm;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Piirtää ja tallentaa polun tiedostoon.
 *
 * @author Janne Ruoho
 */
public class PathImageWriter {

    /**
     * Piirtää polun kuvan päälle ja tallentaa sen kansioon algoritmin nimellä.
     *
     * @param outputFolder Kansio johon tallennetaan
     * @param originalImage Kuva jonka päälle polku piirretään
     * @param path Polku
     * @param algorithm Algoritmi, jonka mukaan kuva nimetään
     */
    public static void write(File outputFolder, BufferedImage originalImage, Path path, PathfindingAlgorithm.Type algorithm) {
        if (!testOutput(outputFolder)) {
            return;
        }

        BufferedImage image = copyImage(originalImage);
        drawPath(path, image);
        saveImage(outputFolder, algorithm, image);
    }

    /**
     * Tallentaa kuvan tiedostoon
     *
     * @param outputFolder Tallennuskansio
     * @param algorithm Algoritmi, jonka mukaan kuva nimetään
     * @param image Itse kuva
     */
    private static void saveImage(File outputFolder, PathfindingAlgorithm.Type algorithm, BufferedImage image) {
        File outputFile = new File(outputFolder.getAbsolutePath() + File.separator + algorithm.name() + ".png");

        try {
            ImageIO.write(image, "png", outputFile);
            System.out.println("Saved output image to " + outputFile.getAbsoluteFile());
        } catch (IOException ex) {
            System.out.println("Could not write output image: " + ex.getMessage());
        }
    }

    /**
     * Piirtää polun kuvan päälle
     *
     * @param path Polku
     * @param image Kuva
     */
    private static void drawPath(Path path, BufferedImage image) {
        int red = Color.RED.getRGB();
        for (Point point : path.getPoints()) {
            image.setRGB(point.x, point.y, red);
        }
    }

    /**
     * Kopioi lähdekuvan uuteen kuvaan
     *
     * @param originalImage Alkuperäinen kuva
     * @return Kopioitu kuva
     */
    private static BufferedImage copyImage(BufferedImage originalImage) {
        BufferedImage image = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), originalImage.getType());
        Graphics g = image.getGraphics();
        g.drawImage(originalImage, 0, 0, null);
        g.dispose();
        return image;
    }

    /**
     * Tarkistaa, onko tallennuskansio validi.
     *
     * @param outputFolder Tallennuskansio
     * @return Tosi, jos tallennuskansion voidaan kirjoittaa.
     */
    private static boolean testOutput(File outputFolder) {
        if (outputFolder.exists()) {
            if (!outputFolder.isDirectory()) {
                System.out.println("Output directory is a file.");
                return false;
            }
        } else {
            outputFolder.mkdirs();
        }
        if (outputFolder.canWrite()) {
            return true;
        } else {
            System.out.println("Can't write to output directory.");
            return false;
        }
    }
}
