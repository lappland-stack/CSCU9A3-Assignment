import java.util.List;

public class TreeExperiment {
    public static void main(String[] args) {
        runExperiment("Ordering A - chronological", EventData.chronological());
        runExperiment("Ordering B", EventData.orderingB());
        runExperiment("Ordering C", EventData.orderingC());

        // Small examples of the supplied operations. You may adapt this code for your experiment.
        EventTree demo = buildTree(EventData.orderingC());
        FestivalEvent extra = new Performance("X1", "Late Addition",
                EventData.chronological().getFirst().getStartTime().plusMinutes(15),
                30, 80, "Acoustic Stage", "Guest Artist");
        demo.add(extra);
        demo.remove(extra);
    }

    private static EventTree buildTree(List<FestivalEvent> events) {
        EventTree tree = new EventTree();
        for (FestivalEvent event : events) tree.add(event);
        return tree;
    }

    private static void runExperiment(String label, List<FestivalEvent> events) {
        EventTree tree = buildTree(events);
        System.out.println("\n=== " + label + " ===");
        System.out.println("Size: " + tree.size());
        System.out.println("Height: " + tree.height()); // TODO will be meaningful after Task 2a
        System.out.println(tree.sideways());

        // TODO Task 2b/2c:
        // choose the searches requested in the assignment, record nodesVisited(),
        // and organise the output so that the three trees are easy to compare.
        FestivalEvent target = EventData.chronological().getLast();
        EventTree.SearchResult result = tree.find(target.getStartTime(), target.getTitle());
        System.out.println("Example search for " + target.getTitle() +
                ": nodes visited = " + result.nodesVisited());
    }
}
