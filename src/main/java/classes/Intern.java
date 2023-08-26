package classes;

import javax.persistence.*;
import java.sql.ResultSet;
import java.sql.SQLException;

@Entity
@Table(name = "intern")
public class Intern {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double gpa;
    private String cvURL;
    private String university;
    private String desiredTrack;
    @Column(name = "isAccepted")
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


    public Intern(){

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
