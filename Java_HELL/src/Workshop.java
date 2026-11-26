//imports all requiered utilities
import java.time.LocalDateTime;

//refactored Workshop to call onto AbstractFestivalEvent to get the general data it needs and only store its specific facilitator data point
public class Workshop extends AbstractFestivalEvent {
    private final String facilitator;

    public Workshop(String id, String title, LocalDateTime startTime,
                    int durationMinutes, int expectedAudience,
                    String venue, String facilitator) {

        super(id, title, startTime, durationMinutes, expectedAudience, venue);
        this.facilitator = facilitator;
    }

    public String getFacilitator() {
        return facilitator;
    }

    @Override
    public String toString() {
        return getStartTime().toLocalTime() + " " + getTitle() + " workshop with " + facilitator + ", " + getVenue();
    }
}
