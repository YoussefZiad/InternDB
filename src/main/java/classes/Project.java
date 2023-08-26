package classes;

import javax.persistence.*;

@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;

    @Column(name = "intern_id")
    private int internID;

    public Project(){

    }

    public Project(int id, String name, String description, int internID) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.internID = internID;
    }

    public Project(String name, String description, int internID) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.internID = internID;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getInternID() {
        return internID;
    }
}
