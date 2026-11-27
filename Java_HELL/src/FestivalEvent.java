//imports all requiered utilities
import java.time.LocalDateTime;

/**
 * Common view of something scheduled in the festival programme.
 *
 * Task 1 deliberately starts with duplication between Performance and Workshop.
 * You may change the class structure, but the rest of the project should continue to
 * be able to work with FestivalEvent values.
 */
//oh so this is where task 1 is, fair eough, and its explained for me too
//no nothing is explained its just some reminders for task 1, but what is here is quite self explanatory

public interface FestivalEvent {
    String getId();
    String getTitle();
    LocalDateTime getStartTime();
    int getDurationMinutes();
    int getExpectedAudience();
    String getVenue();

    default LocalDateTime getEndTime() {
        return getStartTime().plusMinutes(getDurationMinutes());
    }
}
