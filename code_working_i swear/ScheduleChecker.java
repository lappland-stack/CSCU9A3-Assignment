import java.util.List;

public class ScheduleChecker {
    /**
     * Returns true when the candidate clashes with another event in the same venue.
     *
     * WARNING: this method contains a deliberate bug for Task 3.
     * Diagnose it using the interactive debugger before changing it.
     */
    public boolean hasClash(FestivalEvent candidate, List<FestivalEvent> scheduled) {
        for (FestivalEvent existing : scheduled) {
            if (!existing.getVenue().equals(candidate.getVenue())) continue;
            if (existing.getId().equals(candidate.getId())) continue;

            if (!candidate.getStartTime().isBefore(existing.getStartTime())
                    && candidate.getStartTime().isBefore(existing.getEndTime())) {
                return true;
            }
        }
        return false;
    }
}
