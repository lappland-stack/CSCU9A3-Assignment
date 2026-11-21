import java.util.List;

public final class FestivalSorter {
    private FestivalSorter() { }

    /**
     * Task 5: sort in festival display order using recursive quicksort.
     * Do not replace this with List.sort(), Collections.sort(), streams.sorted(), etc.
     */
    public static void quickSort(List<FestivalEvent> events) {
        if (events == null) throw new IllegalArgumentException("events cannot be null");
        if (events.size() < 2) return;
        quickSort(events, 0, events.size() - 1);
    }

    private static void quickSort(List<FestivalEvent> events, int low, int high) {
        // TODO Task 5: recursive quicksort implementation.
        // The supplied partition() method below may be used as part of your solution.
    }

    private static int partition(List<FestivalEvent> events, int low, int high) {
        int middle = low + (high - low) / 2;
        FestivalEvent pivot = events.get(middle);

        // Move pivot to the end to make a conventional partition easy to implement.
        swap(events, middle, high);
        int store = low;

        for (int i = low; i < high; i++) {
            if (compareEvents(events.get(i), pivot) <= 0) {
                swap(events, i, store);
                store++;
            }
        }
        swap(events, store, high);
        return store;
    }

    /**
     * Required ordering:
     * 1. start time ascending
     * 2. for equal start times, expected audience descending
     * 3. if still equal, title alphabetically, ignoring case
     */
    static int compareEvents(FestivalEvent a, FestivalEvent b) {
        // TODO Task 5: implement the three-level comparison rule.
        return 0;
    }

    private static void swap(List<FestivalEvent> events, int i, int j) {
        FestivalEvent tmp = events.get(i);
        events.set(i, events.get(j));
        events.set(j, tmp);
    }
}
