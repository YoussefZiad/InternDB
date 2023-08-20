package classes;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Intern {

    private int id;
    private String name;
    private double gpa;
    private String cvURL;
    private String university;
    private String desiredTrack;
    private boolean accepted;

    @Override
    public String toString() {
        return "Intern{" +
                "name='" + name + '\'' +
                ", gpa=" + gpa +
                ", cvURL='" + cvURL + '\'' +
                ", university='" + university + '\'' +
                ", desiredTrack='" + desiredTrack + '\'' +
                '}';
    }

    public static Intern mapToIntern(ResultSet rs) throws SQLException {
        return new Intern(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4),
                rs.getString(5), rs.getString(6), rs.getBoolean(7));
    }

    public String getName() {
        return name;
    }

    public double getGpa() {
        return gpa;
    }

    public String getCvURL() {
        return cvURL;
    }

    public String getUniversity() {
        return university;
    }

    public String getDesiredTrack() {
        return desiredTrack;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public Intern(int id, String name, double gpa, String cvURL, String university, String desiredTrack, boolean accepted) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
        this.cvURL = cvURL;
        this.university = university;
        this.desiredTrack = desiredTrack;
        this.accepted = accepted;
    }

    public Intern( String name, double gpa, String cvURL, String university, String desiredTrack) {

        this.name = name;
        this.gpa = gpa;
        this.cvURL = cvURL;
        this.university = university;
        this.desiredTrack = desiredTrack;

    }
}
