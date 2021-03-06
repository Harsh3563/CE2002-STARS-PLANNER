package Entity;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TimeTable implements Serializable {
    private HashMap<Day, HashMap<String[], LocalTime[]>> timeTable = new HashMap<>();
    public TimeTable(){
        Day[] dayValues = Day.values();
        LocalTime[] defaultTimings = new LocalTime[2];
        defaultTimings[0] = LocalTime.of(0, 0, 0);
        defaultTimings[1] = LocalTime.of(0, 0, 0);
        for(int i=0; i < 7; ++i){
            this.timeTable.put(dayValues[i], new HashMap<>());
        }
    }
    public boolean addLesson(String courseCode, int indexNum, Lesson[] lessons, String status){
        for(Lesson l: lessons){
            //System.out.println(l);
            Day day = l.getDay();
            LocalTime startTime = l.getStartTime();
            LocalTime endTime = l.getEndTime();
            String[] key = new String[] {courseCode, Integer.toString(indexNum), status,
                    l.getLessonType().toString(), l.getWeeklySchedule().toString()};
            timeTable.get(day).put(key, new LocalTime[]{startTime, endTime});
        }
        return true;
    }
    public boolean removeLessons(String courseCode){
        Day[] dayValues = Day.values();
        for(int j = 0; j < 7; ++j) {
            this.timeTable.get(dayValues[j]).
                    entrySet().removeIf(l -> l.getKey()[0].equals(courseCode));
        }
        return true;
    }
    public boolean checkForClash(Lesson[] lessons, String newCourseCode){
        Day[] dayValues = Day.values();
        for(int j = 0; j < 7; ++j){
            Iterator<Map.Entry<String[], LocalTime[]>> it = this.timeTable.get(dayValues[j]).
                    entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<String[], LocalTime[]> l = it.next();
                LocalTime lessonStartTime = l.getValue()[0];
                LocalTime lessonEndTime = l.getValue()[1];
                String weeklySchedule = l.getKey()[4];
                for(int k = 0; k < lessons.length; ++k){
                    if(!l.getKey()[0].equals(newCourseCode)){
                        if(dayValues[j].toString().equals(lessons[k].getDay().toString())) {
                            LocalTime newCourseLessonStartTime = lessons[k].getStartTime();
                            LocalTime newCourseLessonEndTime = lessons[k].getEndTime();
                            String newCourseWeeklySchedule = lessons[k].getWeeklySchedule().toString();
                            if(weeklySchedule.equals("BOTH") || newCourseWeeklySchedule.equals("BOTH")
                                ||weeklySchedule.equals(newCourseWeeklySchedule)) {
                                if ((newCourseLessonStartTime.compareTo(lessonEndTime) < 0) &&
                                        (newCourseLessonEndTime.compareTo(lessonStartTime) > 0)) {
                                    System.out.println();
                                    System.out.println("Time table clash with course code " +
                                            l.getKey()[0]);
                                    System.out.println("Clashing Timings: New Course " +
                                            newCourseLessonStartTime + " - "
                                            + newCourseLessonEndTime + " and Old Course " +
                                            lessonStartTime + " - " + lessonEndTime);
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("No time table clash!");
        return true;
    }
    public void printTimeTable(){
        Day[] dayValues = Day.values();
        for(int i=0; i<7; ++i){
            Iterator<Map.Entry<String[], LocalTime[]>> it = this.timeTable.get(dayValues[i]).
                    entrySet().iterator();
            System.out.println(dayValues[i].toString() + ":");
            if(!it.hasNext()) {
                System.out.println("No classes on this day!");
                continue;
            }
            while(it.hasNext()) {
                Map.Entry<String[], LocalTime[]> l = it.next();
                LocalTime lessonStartTime = l.getValue()[0];
                LocalTime lessonEndTime = l.getValue()[1];
                String courseCode = l.getKey()[0];
                String indexNum = l.getKey()[1];
                String status = l.getKey()[2];
                String venue = l.getKey()[3];
                String weeklySchedule = l.getKey()[4];
                System.out.println("Course Code: " + l.getKey()[0] + ", Index Number: " + l.getKey()[1] +
                        ", Timings: " + lessonStartTime + " - " + lessonEndTime +
                        ", Status : " + l.getKey()[2] + ", Lesson Type : " + l.getKey()[3] +
                        ", Weeks : " + l.getKey()[4]);
            }
        }
    }

    public void changeWaitingToRegistered(String courseCode) {
        Day[] dayValues = Day.values();
        HashMap<Day, HashMap<String[], LocalTime[]>> temp = new HashMap<>();
        for(int i=0; i<7; ++i) {
            HashMap<String[], LocalTime[]> temp1 = new HashMap<>(timeTable.get(dayValues[i]));
            temp.put(dayValues[i], temp1);
        }
        for(int i=0; i<7; ++i){
            Iterator<Map.Entry<String[], LocalTime[]>> it = temp.get(dayValues[i]).
                    entrySet().iterator();
            while(it.hasNext()) {
                Map.Entry<String[], LocalTime[]> l = it.next();
                if(l.getKey()[0].equals(courseCode)){
                    LocalTime[] values = l.getValue();
                    String[] key = l.getKey();
                    System.out.println(Arrays.toString(key));
                    System.out.println(Arrays.toString(values));
                    this.timeTable.get(dayValues[i]).remove(key);
                    key[2] = "REGISTERED";
                    this.timeTable.get(dayValues[i]).put(key, values);
                    printTimeTable();
                }
            }
        }
    }
}
