//imports all requiered utilities
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;

/**
 * Simple supplied visualiser for Task 4.
 *
 * Layout is deliberately kept here rather than in SiteGraph or GraphData.
 */
public class GraphVisualiser extends JPanel {

    private final SiteGraph graph;
    private final SearchResult result;

    private static final int NODE_RADIUS = 22;
    private static final int PADDING = 60;

    public GraphVisualiser(SiteGraph graph, SearchResult result) {
        this.graph = graph;
        this.result = result;

        setPreferredSize(new Dimension(950, 650));
        setBackground(new Color(245, 247, 240));
    }

    /**
     * Kept deliberately simple for GraphExperiment:
     * GraphVisualiser.show(graph, result, title)
     */
    public static void show(SiteGraph graph, SearchResult result, String title) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(title);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setContentPane(new GraphVisualiser(graph, result));
            frame.pack();
            frame.setLocationByPlatform(true);
            frame.setVisible(true);
        });
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        Map<String, Point2D.Double> screen = screenPositions();

        drawSiteBackground(g2);
        drawEdges(g2, screen);
        drawNodes(g2, screen);
        drawSummary(g2);

        g2.dispose();
    }

    private void drawSiteBackground(Graphics2D g2) {
        int w = getWidth();
        int h = getHeight();

        g2.setColor(new Color(232, 238, 223));
        g2.fillRoundRect(25, 25, w - 50, h - 90, 35, 35);

        g2.setColor(new Color(220, 228, 211));
        g2.fillRoundRect(
                (int)(w * 0.06), (int)(h * 0.57),
                (int)(w * 0.25), (int)(h * 0.22), 25, 25);

        g2.setColor(new Color(225, 233, 218));
        g2.fillRoundRect(
                (int)(w * 0.65), (int)(h * 0.15),
                (int)(w * 0.27), (int)(h * 0.27), 25, 25);
    }

    private void drawEdges(Graphics2D g2, Map<String, Point2D.Double> screen) {
        Set<String> routeEdges = routeEdges();
        Set<String> drawnEdges = new HashSet<>();

        for (String from : graph.getLocations()) {
            Point2D.Double a = screen.get(from);
            if (a == null) continue;

            for (String to : graph.getNeighbours(from)) {
                Point2D.Double b = screen.get(to);
                if (b == null) continue;

                String canonical = canonicalEdge(from, to);
                if (!drawnEdges.add(canonical)) continue;

                if (routeEdges.contains(canonical)) {
                    g2.setStroke(new BasicStroke(
                            5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g2.setColor(new Color(70, 100, 165));
                } else {
                    g2.setStroke(new BasicStroke(
                            2.3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g2.setColor(new Color(120, 125, 115));
                }

                g2.draw(edgeShape(from, to, a, b, screen));
            }
        }
    }

    /**
     * A straight line, unless that line would pass through another location, in which case
     * a curve that bends around it. Without this, an edge such as L14-L17 in the generated
     * graph is drawn straight through L15 and L16 and looks like a route through them.
     */
    private Shape edgeShape(String from, String to, Point2D.Double a, Point2D.Double b,
                            Map<String, Point2D.Double> screen) {
        Line2D.Double line = new Line2D.Double(a, b);
        if (!passesThroughOtherLocation(line, from, to, screen)) return line;

        double dx = b.x - a.x, dy = b.y - a.y;
        double length = Math.hypot(dx, dy);
        double nx = -dy / length, ny = dx / length; // unit perpendicular to the edge
        double midX = (a.x + b.x) / 2, midY = (a.y + b.y) / 2;

        QuadCurve2D.Double fallback = null;
        for (double bend : new double[]{-55, 55, -95, 95, -140, 140}) {
            QuadCurve2D.Double curve = new QuadCurve2D.Double(
                    a.x, a.y, midX + nx * bend * 2, midY + ny * bend * 2, b.x, b.y);
            if (fallback == null) fallback = curve;
            if (curveIsClear(curve, from, to, screen)) return curve;
        }
        return fallback;
    }

    private boolean passesThroughOtherLocation(Line2D.Double line, String from, String to,
                                               Map<String, Point2D.Double> screen) {
        for (Map.Entry<String, Point2D.Double> e : screen.entrySet()) {
            if (e.getKey().equals(from) || e.getKey().equals(to)) continue;
            if (line.ptSegDist(e.getValue()) < NODE_RADIUS + 4) return true;
        }
        return false;
    }

    /** Samples the curve and checks it keeps clear of other locations and their labels. */
    private boolean curveIsClear(QuadCurve2D.Double c, String from, String to,
                                 Map<String, Point2D.Double> screen) {
        for (int i = 1; i < 40; i++) {
            double t = i / 40.0, u = 1 - t;
            double x = u * u * c.x1 + 2 * u * t * c.ctrlx + t * t * c.x2;
            double y = u * u * c.y1 + 2 * u * t * c.ctrly + t * t * c.y2;
            if (x < 0 || y < 0 || x > getWidth() || y > getHeight() - 70) return false;
            for (Map.Entry<String, Point2D.Double> e : screen.entrySet()) {
                if (e.getKey().equals(from) || e.getKey().equals(to)) continue;
                Point2D.Double p = e.getValue();
                if (p.distance(x, y) < NODE_RADIUS + 6) return false;
                if (Math.abs(x - p.x) < 28 && Math.abs(y - (p.y + NODE_RADIUS + 14)) < 10) return false;
            }
        }
        return true;
    }

    private void drawNodes(Graphics2D g2, Map<String, Point2D.Double> screen) {
        Set<String> visited = result == null
                ? Collections.emptySet()
                : new HashSet<>(result.visitedOrder());

        Set<String> route = result == null
                ? Collections.emptySet()
                : new HashSet<>(result.route());

        String start = null;
        String destination = null;
        if (result != null && !result.route().isEmpty()) {
            start = result.route().getFirst();
            destination = result.route().getLast();
        }

        for (String name : graph.getLocations()) {
            Point2D.Double p = screen.get(name);
            if (p == null) continue;

            Color fill = new Color(250, 250, 247);

            if (visited.contains(name)) fill = new Color(245, 218, 140);
            if (route.contains(name)) fill = new Color(162, 196, 240);
            if (Objects.equals(name, start)) fill = new Color(158, 214, 168);
            if (Objects.equals(name, destination)) fill = new Color(235, 161, 161);

            Ellipse2D.Double circle = new Ellipse2D.Double(
                    p.x - NODE_RADIUS,
                    p.y - NODE_RADIUS,
                    NODE_RADIUS * 2.0,
                    NODE_RADIUS * 2.0);

            g2.setColor(fill);
            g2.fill(circle);

            g2.setStroke(new BasicStroke(1.8f));
            g2.setColor(new Color(65, 70, 65));
            g2.draw(circle);

            drawLabel(g2, name, p.x, p.y + NODE_RADIUS + 18);
        }
    }

    private void drawLabel(Graphics2D g2, String text, double x, double y) {
        Font old = g2.getFont();
        g2.setFont(old.deriveFont(Font.BOLD, 12f));

        FontMetrics fm = g2.getFontMetrics();
        int width = fm.stringWidth(text);

        g2.setColor(new Color(35, 40, 35));
        g2.drawString(text, (float)(x - width / 2.0), (float)y);

        g2.setFont(old);
    }

    private void drawSummary(Graphics2D g2) {
        int y = getHeight() - 42;

        String summary;
        if (result == null) {
            summary = "No search result";
        } else {
            summary = "Visited: " + result.verticesVisited()
                    + "    Route length: " + result.routeLengthInEdges()
                    + "    Route: " + String.join(" -> ", result.route());
        }

        g2.setColor(new Color(40, 45, 40));
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 13f));
        g2.drawString(summary, 35, y);
    }

    private Map<String, Point2D.Double> screenPositions() {
        Map<String, double[]> known = knownPositions();
        Map<String, Point2D.Double> screen = new LinkedHashMap<>();

        int usableW = Math.max(1, getWidth() - 2 * PADDING);
        int usableH = Math.max(1, getHeight() - 2 * PADDING - 55);

        for (String vertex : graph.getLocations()) {
            double[] p = known.get(vertex);
            if (p != null) {
                screen.put(vertex, new Point2D.Double(
                        PADDING + p[0] * usableW,
                        PADDING + p[1] * usableH));
            }
        }

        // Calm staggered-grid fallback for any unfamiliar vertex names.
        List<String> missing = new ArrayList<>();
        for (String vertex : graph.getLocations()) {
            if (!screen.containsKey(vertex)) missing.add(vertex);
        }

        int cols = 4;
        for (int i = 0; i < missing.size(); i++) {
            int row = i / cols;
            int col = i % cols;
            double x = 0.12 + col * 0.24 + (row % 2 == 1 ? 0.06 : 0.0);
            double y = 0.18 + row * 0.24;

            screen.put(missing.get(i), new Point2D.Double(
                    PADDING + x * usableW,
                    PADDING + y * usableH));
        }

        return screen;
    }

    /**
     * Presentation-only coordinates.
     */
    private Map<String, double[]> knownPositions() {
        Map<String, double[]> p = new HashMap<>();

        // Curated festival graphs.
        p.put("Entrance",       pos(0.08, 0.82));
        p.put("Info",           pos(0.25, 0.69));
        p.put("Workshop",       pos(0.24, 0.88));
        p.put("Craft Tent",     pos(0.45, 0.90));
        p.put("First Aid",      pos(0.46, 0.67));
        p.put("Food Court",     pos(0.55, 0.48));
        p.put("Acoustic Stage", pos(0.72, 0.69));
        p.put("Main Stage",     pos(0.84, 0.43));

        p.put("Art Trail",      pos(0.19, 0.57));
        p.put("Sculpture",      pos(0.29, 0.33));
        p.put("Quiet Garden",   pos(0.42, 0.15));
        p.put("Info Point",     pos(0.31, 0.79));

        // Generated graph L0...L17: three staggered rows of six.
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 6; col++) {
                int index = row * 6 + col;
                double x = 0.08 + col * 0.16 + (row == 1 ? 0.04 : 0.0);
                double y = 0.20 + row * 0.31;
                p.put("L" + index, pos(x, y));
            }
        }

        return p;
    }

    private static double[] pos(double x, double y) {
        return new double[]{x, y};
    }

    private Set<String> routeEdges() {
        Set<String> edges = new HashSet<>();
        if (result == null) return edges;

        List<String> route = result.route();
        for (int i = 0; i < route.size() - 1; i++) {
            edges.add(canonicalEdge(route.get(i), route.get(i + 1)));
        }

        return edges;
    }

    private String canonicalEdge(String a, String b) {
        return a.compareTo(b) <= 0
                ? a + "\u0000" + b
                : b + "\u0000" + a;
    }
}
