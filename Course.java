package Entity;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Course implements Serializable {

    private String courseCode;
    private String courseTitle;
    private int numAUs;
    private String school;
    private IndexGroup[] indexList;
    private int maxLimit;
    private int vacancy;
    private int numStudentsRegistered;
    private String[] roster;
    private Lesson[] lecture;
    private int numTuts;
    private int numLabs;
    private int numLecs;

    /**
     * @param courseCode the unique code for the course
     * @param school the school by which the course is offered
     * @param indexList the list of index groups which the course is composed of
     * @param maxLimit the maximum number of students that can be enrolled in this course
     * @param vacancy the number of vacancies for the course
     * @param roster list of all the students that are enrolled in the course
     * @param hasLab boolean variable which indicates whether the course has labs
     * @param hasTut boolean variable which indicates whether the course has tutorials
     */

    public Course(String courseCode) {
        this.courseCode = courseCode;
    }

    public Course(String courseCode, String courseTitle, int numAUs, String school,
                  int maxLimit, int numTuts, int numLabs, int numLecs) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.numAUs = numAUs;
        this.school = school;
        this.indexList =  inputIndexList(courseCode, numTuts, numLabs);
        this.maxLimit = maxLimit;
        this.roster = new String[maxLimit];
        this.numStudentsRegistered = 0;
        this.vacancy = maxLimit;
        this.numTuts = numTuts;
        this.numLabs = numLabs;
        this.numLecs = numLecs;
        lecture = new Lesson[this.numLecs];
        setLecture();
    }

    public String getCourseCode() {
        return this.courseCode;
    }

    /**
     *
     * @param courseCode the unique code for the course
     */
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public int getAUs() {
        return this.numAUs;
    }

    public void setAUs(int numAUs) {
        this.numAUs = numAUs;
    }

    public String getSchool() {
        return this.school;
    }

    /**
     *
     * @param school the school by which the course is offered
     */
    public void setSchool(String school) {
        this.school = school;
    }

    public IndexGroup[] getIndexList() {
        return this.indexList;
    }

    /**
     *
     * @param indexList the list of index groups which the course is composed of
     */
    public void setIndex(IndexGroup[] indexList) {
        this.indexList = indexList;
    }

    public int getVacancy() {
        return this.vacancy;
    }

    /**
     *
     * @param vacancy the number of vacancies for the course
     */
    public void setVacancy(int vacancy) {
        this.vacancy = vacancy;
    }

    public void setLecture() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        Scanner sc = new Scanner(System.in);
        LocalTime startTime;
        LocalTime endTime;
        String day, venue;
        WeeklySchedule weeklySchedule;
        System.out.println("Setting of Lectures");
        for(int i = 0; i < numLecs; i++) {
            System.out.println("For lecture " + i);
            System.out.print("Enter Start time(hh:mm:ss): ");
            startTime = LocalTime.parse(sc.next(), timeFormatter);
            System.out.print("Enter End Time: ");
            endTime = LocalTime.parse(sc.next(), timeFormatter);
            System.out.print("Enter Day of the week: ");
            day = sc.next();
            sc.nextLine();
            System.out.println("Enter venue of lesson: ");
            venue = sc.nextLine();
            System.out.println("Choose whether the lesson is for ODD/EVEN/BOTH weeks.");
            weeklySchedule = WeeklySchedule.chooseWeek();
            lecture[i] = new Lesson(startTime, endTime, day, venue, weeklySchedule, LessonType.LECTURE);
        }
    }


    public int getMaxLimit() {
        return maxLimit;
    }

    /**
     *
     * @param maxLimit the maximum number of students that can be enrolled in this course
     */
    public void setMaxLimit(int maxLimit) {
        this.maxLimit = maxLimit;
    }

    public int getNumLabs() {
        return numLabs;
    }

    /**
     *
     * @param hasLab boolean variable which indicates whether the course has labs
     */
    public void setNumLabs(int numLabs) {
        this.numLabs = numLabs;
    }


    public int getNumTuts() {
        return numTuts;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    /**
     *
     * @param numTuts integer variable which indicates number of tutorials
     */
    public void setNumTuts(int numTuts) {
        this.numTuts = numTuts;
    }

    public String[] getRoster() {
        return roster;
    }

    public int getNumStudentRegistered() {
        return numStudentsRegistered;
    }

    public static Course downcast(Object object){
        return (Course)(object);
    }

    public void displayDetails(){
        System.out.printf("%-9s %20s %-9s %-9s %-9s %-15s %-15s %-15s\n", this.courseCode, this.courseTitle,
                this.numAUs, this.school, this.maxLimit, this.numTuts, this.numLabs, this.vacancy);
    }

    private IndexGroup[] inputIndexList(String courseCode, int numTuts, int numLabs) {
        Scanner sc = new Scanner(System.in);
        int numIndexes, maxLimit, indexNumber;

        System.out.print("Enter number of indexes: ");
        numIndexes = sc.nextInt();
        IndexGroup[] indexList = new IndexGroup[numIndexes];
        for(int i = 0; i < numIndexes; i ++) {
            System.out.print("Enter desired index number: ");
            indexNumber = sc.nextInt();
            System.out.print("Enter the max number of students for this index: ");
            maxLimit = sc.nextInt();
            indexList[i] = new IndexGroup(courseCode, indexNumber, maxLimit, numTuts, numLabs);
        }

        System.out.println("Done with inputting of indexes.");
        return indexList;
    }

    public boolean equals(Object c){
        return this.courseCode.equals(((Course)c).getCourseCode());
    }

    public Lesson[] getLectureLessons(){
        return lecture;
    }

    public void addStudent(String matricNum) {
        this.roster[this.numStudentsRegistered] = matricNum;
        this.numStudentsRegistered++;
        this.vacancy--;
    }

    public void removeStudent(String matricNum) {
        for(int i=0; i<numStudentsRegistered; ++i)
            if(roster[i].equals(matricNum)){
                List<String> studList = new ArrayList<>(Arrays.asList(roster));
                studList.remove(i);
                int counter = 0;
                for(String s: studList)
                    roster[counter++] = s;
                this.numStudentsRegistered--;
                this.vacancy++;
                break;
            }
    }

    public void swapStudents(Student s1, Student s2, int indexNum1, int indexNum2){
        IndexGroup ig1 = null, ig2 = null;
        for(IndexGroup ig: indexList){
            if(ig.getIndexNumber() == indexNum1)
                ig1 = ig;
            else if(ig.getIndexNumber() == indexNum2)
                ig2 = ig;
            if(ig1 != null && ig2 != null)
                break;
        }
        ig1.changeStudent(s1.getMatricNumber(), s2.getMatricNumber());
        ig2.changeStudent(s2.getMatricNumber(), s1.getMatricNumber());
    }

    public void displayEveryDetail() {
        System.out.println("Course Code: " + this.courseCode);
        System.out.println("Course Title: " + this.courseTitle);
        System.out.println("Number of AUs: " + this.numAUs);
        System.out.println("School: " + this.school);
        System.out.println("Max. Limit:" + this.maxLimit);
        System.out.println("No. of Tuts: " + this.numTuts);
        System.out.println("No. of Labs: " + this.numLabs);
        System.out.println("Vacancy: " + this.vacancy);
        System.out.println("No. of registered students: " + this.numStudentsRegistered);
        System.out.println("Registered Students:");
        for(int i=0; i<numStudentsRegistered; ++i){
            System.out.println(roster[i]);
        }
        System.out.println("Index groups details: ");
        for(int i=0; i<indexList.length; ++i){
            indexList[i].printEveryDetail();
        }
        System.out.println("Lecture details: ");
        Lesson[] lectures = getLectureLessons();
        for(int i=0; i<lectures.length; ++i){
            lectures[i].displayEveryDetail();
        }
    }
}
