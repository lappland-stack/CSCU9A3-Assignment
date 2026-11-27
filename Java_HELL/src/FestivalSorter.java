//imports all requiered utilities
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public final class FestivalSorter {
    private FestivalSorter() { }

    /**
     * Task 5: sort in festival display order using recursive quicksort.
     * Do not replace this with List.sort(), Collections.sort(), streams.sorted(), etc.
     */
    private static long comparisonCount = 0;

    public static void quickSort(List<FestivalEvent> events) {
        if (events == null) {
            throw new IllegalArgumentException("events cannot be null");
        }
        if (events.size() < 2) return;
        quickSort(events, 0, events.size() - 1);
    }

    private static void quickSort(List<FestivalEvent> events, int low, int high) {
        // The supplied partition() method below may be used as part of your solution.
        if (low < high) {
            int pivotIndex = partition(events, low, high);

            quickSort(events, low, pivotIndex - 1);
            quickSort(events, pivotIndex + 1, high);
        }
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
       comparisonCount++;

       int result = a.getStartTime().compareTo(b.getStartTime());
       if (result != 0) return result;

       result = Integer.compare(b.getExpectedAudience(), a.getExpectedAudience());

       if (result != 0) return result;

       return a.getTitle().compareToIgnoreCase(b.getTitle());

    }

    public static void resetComparisonCount() {
        comparisonCount = 0;
    }

    public static long getComparisonCount() {
        return comparisonCount;
    }

    private static void swap(List<FestivalEvent> events, int i, int j) {
        FestivalEvent tmp = events.get(i);
        events.set(i, events.get(j));
        events.set(j, tmp);
    }

    //Runs quicksort on a dataset and prints how many comparisons it made.
    private static void runExperiment(String label, List<FestivalEvent> events) {
        resetComparisonCount();
        quickSort(events);

        System.out.println(label + ": " + getComparisonCount() + " comparisons");

    }

    //Runs what task 5 needs
    public static void main (String[] args) {
        //Og chronoligical dataset
        runExperiment("Chronological", new ArrayList<>(EventData.chronological()));

        //Revers of the chronoligical dataset
        List<FestivalEvent> reversed = new ArrayList<>(EventData.chronological());
        Collections.reverse(reversed);
        runExperiment("Reverse chronological", reversed);


        //Shuffled ordering of C
        runExperiment("Ordering C",
                new ArrayList<>(EventData.orderingC()));

        // Generated datasets with 200 events.
        runExperiment("Generated 200 (sorted)",
                new ArrayList<>(EventData.generated(200)));

        runExperiment("Generated 200 (shuffled)",
                new ArrayList<>(EventData.generatedShuffled(200)));

        // Generated datasets with 1000 events.
        runExperiment("Generated 1000 (sorted)",
                new ArrayList<>(EventData.generated(1000)));

        runExperiment("Generated 1000 (shuffled)",
                new ArrayList<>(EventData.generatedShuffled(1000)));
    }
}
