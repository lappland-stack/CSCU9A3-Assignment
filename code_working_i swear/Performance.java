import java.time.LocalDateTime;

public class Performance implements FestivalEvent {
    private final String id;
    private final String title;
    private final LocalDateTime startTime;
    private final int durationMinutes;
    private final int expectedAudience;
    private final String venue;
    private final String artist;

    public Performance(String id, String title, LocalDateTime startTime,
                       int durationMinutes, int expectedAudience,
                       String venue, String artist) {
        this.id = id;
        this.title = title;
        this.startTime = startTime;
        this.durationMinutes = durationMinutes;
        this.expectedAudience = expectedAudience;
        this.venue = venue;
        this.artist = artist;
    }

    @Override public String getId() { return id; }
    @Override public String getTitle() { return title; }
    @Override public LocalDateTime getStartTime() { return startTime; }
    @Override public int getDurationMinutes() { return durationMinutes; }
    @Override public int getExpectedAudience() { return expectedAudience; }
    @Override public String getVenue() { return venue; }
    public String getArtist() { return artist; }

    @Override
    public String toString() {
        return startTime.toLocalTime() + " " + title + " (performance with " + artist + ", " + venue + ")";
    }
}
