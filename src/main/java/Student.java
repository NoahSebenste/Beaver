/**
 * This class represents a student from the excel workbooks.
 */
public class Student {

    private int id;
    private String major;
    private String gender;
    private int score;

    public Student(int id, String major, String gender, int score) {
        this.id = id;
        this.major = major;
        this.gender = gender;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
