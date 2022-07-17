import java.awt.*;
import java.time.LocalDateTime;

public class FlightStop {

    private Point location;
    private String city;
    private LocalDateTime time;

    // Constructor
    FlightStop(double latitude, double longitude, String city, LocalDateTime time) {
        Point location = new Point(0,0);
        location.setLocation(latitude,longitude);
        this.location = location;
        this.city = city;
        this.time = time;
    }

    // Getters and setters
    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
