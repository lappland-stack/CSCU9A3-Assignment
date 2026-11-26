//this imports the test utils
import java.time.LocalDateTime;

//used abstract class to have a super class that both workshop and venue can call upon, since they held the same data twice with
//a single difference
public abstract class AbstractFestivalEvent implements FestivalEvent{

    private final String id;
    private final String title;
    private final LocalDateTime startTime;
    private final int durationMinutes;
    private final int expectedAudience;
    private final String venue;

public AbstractFestivalEvent(String id, String title, LocalDateTime startTime,
                             int durationMinutes, int expectedAudience,
                             String venue){

    this.id = id;
    this.title = title;
    this.startTime = startTime;
    this.durationMinutes = durationMinutes;
    this.expectedAudience = expectedAudience;
    this.venue = venue;

}
    @Override
    public String getId() {
        return id;
    }
    @Override
    public String getTitle() {
        return title;
    }
    @Override
    public LocalDateTime getStartTime() {
        return startTime;
    }

    @Override
    public int getDurationMinutes() {
        return durationMinutes;
    }

    @Override
    public int getExpectedAudience() {
        return expectedAudience;
    }

    @Override
    public String getVenue() {
        return venue;
    }
}
