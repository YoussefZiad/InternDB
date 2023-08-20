package ui;

import classes.Intern;
import services.InternService;
import ui.table.Column;
import ui.table.Table;
import ui.table.TableBuilder;
import ui.util.InterfacePrinter;
import ui.util.UserScanner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InternUILayer {

    InternService service;

    public InternUILayer(InternService service){
        this.service = service;
    }

    public void addIntern() throws SQLException {
        System.out.println("Adding a new intern application to the database...");
        Intern intern = scanInternDetails();
        service.addIntern(intern);
        if(intern==null)
            return;

        System.out.println("Inserted Intern Application Successfully!");
    }

    public void listInterns() throws SQLException {
        System.out.println("List of all interns:");
        Table interns = service.listInterns();
        InterfacePrinter.printTable(interns);
    }

    public void listInternsUni() throws SQLException {
        String university = UserScanner.scanString("Enter Intern University");
        System.out.println("List of interns from "+university+":");
        Table table = service.listInternsByUni(university);
        InterfacePrinter.printTable(table);
    }

    public void listInternsTop() throws SQLException {
        String university = UserScanner.scanString("Enter Intern University");
        String desiredTrack = UserScanner.scanTrack();
        System.out.println("List of top interns from "+university+" and on the "+desiredTrack+" track:");
        Table table = service.listInternsByUniAndTrack(university, desiredTrack);
        InterfacePrinter.printTable(table);

    }

    public void deleteIntern() throws SQLException {
        System.out.println("Deleting an intern application from the database:");

        int id = UserScanner.scanID();

        Table table = service.listInternDetailsByID(id);
        System.out.println("Please confirm that this is the correct intern:");
        InterfacePrinter.printTable(table);

        if(!UserScanner.scanConfirmation()){
            System.out.println("Deletion Aborted.");
            return;
        }

        service.deleteIntern(id);

        System.out.println("Deleted Intern Application Successfully!");
    }

    public void AcceptOrRejectIntern(boolean accept) throws SQLException {
        if(accept)
            System.out.println("Accepting an intern application from the database:");
        else
            System.out.println("Rejecting an intern application from the database:");

        int id = UserScanner.scanID();

        Table table = service.listInternDetailsByID(id);
        System.out.println("Please confirm that this is the correct intern:");
        InterfacePrinter.printTable(table);

        if(!UserScanner.scanConfirmation()){
            System.out.print("Operation Aborted.");
            return;
        }

        service.acceptOrRejectIntern(id, accept);

        if(accept)
            System.out.println("Accepted Intern Application Successfully!");
        else
            System.out.println("Rejected Intern Application Successfully!");
    }

    public static Intern scanInternDetails() throws SQLException {
        String name = UserScanner.scanString("Enter Intern Name");
        double gpa = UserScanner.scanGPA();
        String cvURL = UserScanner.scanString("Enter link to Intern CV");
        String uni = UserScanner.scanString("Enter Intern University");
        String track = UserScanner.scanTrack();

        Column nameCol = new Column("Name", name.length(), String.class, 1);
        nameCol.setDefaultValue(name);
        Column gpaCol = new Column("GPA", 1, Double.class, 2);
        gpaCol.setDefaultValue(String.valueOf(gpa));
        Column cvCol = new Column("CV", cvURL.length(), String.class, 3);
        cvCol.setDefaultValue(cvURL);
        Column uniCol = new Column("University", uni.length(), String.class, 4);
        uniCol.setDefaultValue(uni);
        Column trackCol = new Column("Track", track.length(), String.class, 5);
        trackCol.setDefaultValue(track);

        Table table = new TableBuilder()
                .column(nameCol)
                .column(gpaCol)
                .column(cvCol)
                .column(uniCol)
                .column(trackCol)
                .buildTable();

        System.out.println("Please confirm that you want to insert an intern application with these details:");
        InterfacePrinter.printTable(table);

        if(!UserScanner.scanConfirmation()) {
            System.out.println("Insertion Aborted");
            return null;
        }

        return new Intern(name, gpa, cvURL, uni, track);
    }

}
