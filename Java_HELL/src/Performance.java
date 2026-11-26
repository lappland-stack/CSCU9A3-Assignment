//imports all requiered utilities
import java.time.LocalDateTime;

//refactored Performance to call onto AbstractFestivalEvent to get the general data it needs and only store its specific artist data point
public class Performance extends AbstractFestivalEvent {

    private final String artist;

    public Performance(String id, String title, LocalDateTime startTime,
                       int durationMinutes, int expectedAudience,
                       String venue, String artist) {
        super(id, title, startTime, durationMinutes, expectedAudience, venue);

        this.artist = artist;
    }

    public String getArtist() {
        return artist;
    }

    @Override
    public String toString() {
        return getStartTime().toLocalTime() + " " + getTitle() + " performance with " + artist + ", " + getVenue();
    }
}
