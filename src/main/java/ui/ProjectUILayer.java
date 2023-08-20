package ui;

import classes.Project;
import services.InternService;
import services.ProjectService;
import ui.table.Column;
import ui.table.Table;
import ui.table.TableBuilder;
import ui.util.InterfacePrinter;
import ui.util.UserScanner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProjectUILayer {

    private ProjectService projectService;
    private InternService internService;

    public ProjectUILayer(ProjectService projectService, InternService internService) {
        this.projectService = projectService;
        this.internService = internService;
    }

    public void addProject() throws SQLException {
        System.out.println("Adding a new project to the database...");

        Project project = scanProjectDetails();

        if(project == null)
            return;

        Column nameCol = new Column("Name",project.getName().length(),String.class,2);
        nameCol.setDefaultValue(project.getName());
        Column descCol = new Column("Description",project.getDescription().length(),String.class,3);
        descCol.setDefaultValue(project.getDescription());

        Table table = new TableBuilder()
                .column(nameCol)
                .column(descCol)
                .buildTable();

        System.out.println("Please confirm that you want to add a project with these details:");
        InterfacePrinter.printTable(table);


        if(!UserScanner.scanConfirmation()){
            System.out.print("Insertion Aborted.");
            return;
        }

        projectService.addProjectToDB(project);

        System.out.println("Inserted Project Successfully!");
    }

    public void listProjects() throws SQLException {
        int id = scanIDAndConfirm();
        if(id == 0)
            return;
        System.out.println("List of assigned projects:");
        Table table = projectService.listProjectsByInternID(id);
        InterfacePrinter.printTable(table);

    }

    public Project scanProjectDetails(){
        int id = scanIDAndConfirm();

        if(id == 0)
            return null;

        String projectName = UserScanner.scanString("Enter Project name:");

        String desc = UserScanner.scanString("Enter Project Description:");

        return new Project(projectName, desc, id);
    }

    public int scanIDAndConfirm(){
        int id;
        while(true) {
            try {
                id = UserScanner.scanID();
                Table table = internService.listInternDetailsByID(id);
                System.out.println("Is this the correct intern?");
                InterfacePrinter.printTable(table);

                if(!UserScanner.scanConfirmation()){
                    System.out.print("Insertion Aborted.");
                    return 0;
                }

                break;
            }
            catch(SQLException e){
                System.out.println("An intern with this ID does not exist. Please enter a valid ID.");
            }
        }
        return id;
    }


}
