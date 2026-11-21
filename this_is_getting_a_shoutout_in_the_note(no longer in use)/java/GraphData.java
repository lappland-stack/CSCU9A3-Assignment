//imports all requiered utilities
import java.util.Random;

/**
 * Supplied graph data for Task 4.
 *
 * This class contains graph structure only. Visual layout is handled entirely
 * by GraphVisualiser.
 */
public class GraphData {

    public static SiteGraph graphA() {
        SiteGraph g = new SiteGraph();

        g.addPath("Entrance", "Info");
        g.addPath("Entrance", "Workshop");
        g.addPath("Info", "Food Court");
        g.addPath("Info", "First Aid");
        g.addPath("Workshop", "Craft Tent");
        g.addPath("Workshop", "First Aid");
        g.addPath("Food Court", "Main Stage");
        g.addPath("First Aid", "Acoustic Stage");
        g.addPath("First Aid", "Food Court");
        g.addPath("Acoustic Stage", "Main Stage");

        return g;
    }

    public static SiteGraph graphB() {
        SiteGraph g = new SiteGraph();

        // A longer dead-end branch makes the difference between BFS and DFS visible.
        g.addPath("Entrance", "Art Trail");
        g.addPath("Art Trail", "Sculpture");
        g.addPath("Sculpture", "Quiet Garden");

        g.addPath("Entrance", "Info Point");
        g.addPath("Info Point", "Food Court");
        g.addPath("Food Court", "Main Stage");
        g.addPath("Info Point", "Workshop");
        g.addPath("Workshop", "First Aid");
        g.addPath("First Aid", "Acoustic Stage");
        g.addPath("Acoustic Stage", "Main Stage");

        return g;
    }

    /**
     * Seeded generated graph used for the third experiment.
     *
     * The vertices remain L0...L17 so the supplied GraphExperiment can keep the
     * same start and goal. The seed varies sensible nearby connections rather
     * than producing arbitrary long crossing edges.
     */
    public static SiteGraph generatedGraph(long seed) {
        SiteGraph g = new SiteGraph();
        Random r = new Random(seed);

        // Add all 18 locations first.
        for (int i = 0; i < 18; i++) {
            g.addLocation("L" + i);
        }

        // The visualiser places these in a 6 x 3 staggered map.
        // Guaranteed horizontal backbone: ensures L0 can always reach L17.
        for (int row = 0; row < 3; row++) {
            int base = row * 6;
            for (int col = 0; col < 5; col++) {
                g.addPath("L" + (base + col), "L" + (base + col + 1));
            }
        }

        // Guaranteed links between the rows.
        g.addPath("L2", "L8");
        g.addPath("L8", "L14");
        g.addPath("L14", "L17");

        // Seeded vertical/diagonal paths between nearby locations.
        for (int col = 0; col < 6; col++) {
            maybeAdd(g, r, "L" + col, "L" + (6 + col), 0.65);
            maybeAdd(g, r, "L" + (6 + col), "L" + (12 + col), 0.65);
        }

        for (int col = 0; col < 5; col++) {
            maybeAdd(g, r, "L" + col, "L" + (7 + col), 0.35);
            maybeAdd(g, r, "L" + (6 + col), "L" + (13 + col), 0.35);
        }

        return g;
    }

    private static void maybeAdd(SiteGraph g, Random r,
                                 String a, String b, double probability) {
        if (r.nextDouble() < probability) {
            g.addPath(a, b);
        }
    }
}
