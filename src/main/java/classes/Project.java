package classes;

public class Project {

    private int id;
    private String name;
    private String description;
    private int internID;

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
