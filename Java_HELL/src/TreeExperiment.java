//imports all requiered utilities
import java.util.List;
import java.time.LocalDateTime;

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
        System.out.println(tree.sideways());            //aight bet

        List<FestivalEvent> chronological = EventData.chronological();
        LocalDateTime date = chronological.getFirst().getStartTime();

        runSearch(tree, "Amber Sky", date.withHour(10).withMinute(0));
        runSearch(tree, "Harbour", date.withHour(13).withMinute(30));
        runSearch(tree, "Open Water", date.withHour(17).withMinute(0));
        runSearch(tree, "Not Scheduled", date.withHour(17).withMinute(15));

    }

    private static void runSearch(EventTree tree, String title, LocalDateTime startTime) {

        EventTree.SearchResult result = tree.find(startTime, title);

        System.out.println("Search: " +title
        + " (" + startTime.toLocalTime() + ")"
        + " | Found: " + (result.event() != null)
        + " | Nodes Visited: " + result.nodesVisited());
    }
}
