/*
im gonna quickfire what each import does, i dont want to write them all separately

imports the date and time utility, allows for time to actually be update in real time to local device time
imports the arrays utilities, allow SIGNIFICANTLY easier array work, thank fuck
imports utilities that allow you to directly deal with lists, maps or sets and more (collections themselves
being groups of classes, as far as i understand, i might be wrong, if i am ill find out later when i get smth
wrong)
imports list work utilities (idk how else to word it ngl)
 */
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Fixed data used by Task 2 so all three trees contain exactly the same events. */
//basically dont mess with the code here
public final class EventData {
    private EventData() { }

    //here we store all the event results to be called in our tests
    private static Performance p(String id, String title, int hour, int minute, int audience) {
        return new Performance(id, title,
                LocalDateTime.of(2026, 7, 18, hour, minute),
                45, audience, "Main Stage", "Artist " + id);
    }

    //i am truly curious as to how these band names were reached
    //stores all the events in a list with theur event id, band name, what time it takes place at and how
    //long it should last
    //i was hoping to see foo fighters or linkin park or smth but i presume we fear copyright or smth
    public static List<FestivalEvent> chronological() {
        return List.of(
                p("E1", "Amber Sky", 10, 0, 120),
                p("E2", "Brass Lines", 10, 30, 160),
                p("E3", "Cloud Atlas", 11, 0, 90),
                p("E4", "Driftwood", 11, 30, 220),
                p("E5", "Echo Room", 12, 0, 180),
                p("E6", "Firelight", 12, 30, 140),
                p("E7", "Green Static", 13, 0, 200),
                p("E8", "Harbour", 13, 30, 110),
                p("E9", "Indigo", 14, 0, 175),
                p("E10", "Juniper", 14, 30, 130),
                p("E11", "Kite String", 15, 0, 210),
                p("E12", "Lanterns", 15, 30, 150),
                p("E13", "Moon Taxi", 16, 0, 250),
                p("E14", "Northbound", 16, 30, 190),
                p("E15", "Open Water", 17, 0, 230)
        );
    }

    /** Same events, ordered so that the resulting tree is relatively shallow. */
    //so this creates our tree
    public static List<FestivalEvent> orderingB() {
        List<FestivalEvent> e = chronological();
        int[] order = {7, 3, 11, 1, 5, 9, 13, 0, 2, 4, 6, 8, 10, 12, 14};
        List<FestivalEvent> result = new ArrayList<>();
        for (int i : order) result.add(e.get(i));
        return result;
    }

    /** Same events in a deterministic shuffled order. */
    //and here its the same as the other 3 sections but are shuffeled, well not the same as both
    //since what im getting from this is that this is another tree
    public static List<FestivalEvent> orderingC() {
        List<FestivalEvent> result = new ArrayList<>(chronological());
        Collections.shuffle(result, new java.util.Random(93));
        return result;
    }

    /**
     * Task 5: n events in chronological order, five minutes apart from 08:00 on the first
     * day of the festival (large n runs into the following days). Every event has a
     * different start time. Titles and expected audiences are fixed, so every call with
     * the same n returns the same events.
     */
    //so for this one i gotta ensure the program can display events in a consistent order
    //tbh the whole point is better explained in the tasks file
    public static List<FestivalEvent> generated(int n) {
        if (n < 0) throw new IllegalArgumentException("n cannot be negative");
        LocalDateTime first = LocalDateTime.of(2026, 7, 18, 8, 0);
        List<FestivalEvent> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String id = String.format("G%04d", i + 1);
            int audience = 50 + (i * 37) % 450;
            result.add(new Performance(id, "Event " + id, first.plusMinutes(5L * i),
                    30, audience, "Main Stage", "Artist " + id));
        }
        return result;
    }

    /** Task 5: the same events as generated(n), in a deterministic shuffled order. */
    //so the same thing as the other deterministic shuffeled order section of code, but this time
    //the results i get from above
    public static List<FestivalEvent> generatedShuffled(int n) {
        List<FestivalEvent> result = new ArrayList<>(generated(n));
        Collections.shuffle(result, new java.util.Random(93));
        return result;
    }
}
