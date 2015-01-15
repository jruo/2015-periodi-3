package com.hiilimonoksidi.tiralabra;

import com.hiilimonoksidi.tiralabra.graph.Graph;
import com.hiilimonoksidi.tiralabra.graph.Path;
import com.hiilimonoksidi.tiralabra.misc.GraphBuilder;
import com.hiilimonoksidi.tiralabra.misc.Point;
import com.hiilimonoksidi.tiralabra.misc.Timer;
import com.hiilimonoksidi.tiralabra.pathfinding.AStar;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main {

    public static void main(String[] args) throws IOException {

        // Väliaikaista testauskoodia
        File imageFile = new File("test-images/maze.png");
        File solutionFile = new File("test-images/maze-path.png");

        BufferedImage image = ImageIO.read(imageFile);

        System.out.println("Building graph...");

        Graph graph = GraphBuilder.createFromImage(image);
        AStar aStar = new AStar(graph);

        Point start = new Point(2, 0);
        Point goal = new Point(267, 271);

        System.out.println("Search started.");

        Timer timer = new Timer();
        timer.start();
        Path path = aStar.search(start, goal);
        timer.stop();

        if (path != null) {
            System.out.println("Found path!");
        } else {
            System.out.println("No path found.");
        }

        System.out.println("Time elapsed: " + timer.getTime() + "ms");

        if (path == null) {
            return;
        }

        System.out.println("Drawing path...");

        int red = Color.RED.getRGB();
        for (Point point : path.getPoints()) {
            image.setRGB(point.x, point.y, red);
        }

        ImageIO.write(image, "png", solutionFile);
    }

}
