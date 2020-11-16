package Entity;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalTime;

public class Lesson implements Serializable {
    private LocalTime startTime;
    private LocalTime endTime;
    private Day day;
    private Duration duration;
    WeekSchedule weekT;
    private Venue Locate;

    private Venue vent;
    private WeekSchedule Weksc;
    
    public Lesson(LocalTime startTime, LocalTime endTime, String day){
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = Day.valueOf(day);
        duration = Duration.between(endTime, startTime);
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Duration getDuration() {
        return duration;
    }
    
    public WeekSchedule getweektype() {
        return weekT;
    }
    public Venue getlocation() {
        return Locate;
    }
    public void setWeekschedule(WeekSchedule Wek ) {
       this.weekT=Wek;
    }
    public void setlocation(Venue ve ) {
        this.Locate=ve;
     }
    
    

    public void setDuration(Duration duration) {
        duration = Duration.between(endTime, startTime);
    }
    public void displayEveryDetail(){
        System.out.println(startTime);
        System.out.println(endTime);
        System.out.println(day.toString());
    }
}
