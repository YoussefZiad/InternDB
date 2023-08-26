package services;

import classes.Intern;
import database.DBLayer;
import ui.table.Column;
import ui.table.Table;
import ui.table.TableBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class InternService {

    private DBLayer db;

    public InternService(DBLayer db){
        this.db =db;
    }

    public void addIntern(Intern intern) throws SQLException {
        db.addInternToDB(intern);
    }

    public void deleteIntern(int id) throws SQLException {
        db.deleteInternFromDB(id);
    }

    public void acceptOrRejectIntern(int id, boolean accept) throws SQLException{
        db.updateInternAcceptanceInDB(id, accept);
    }

    public Table prepareShorthandTable(ResultSet contents) throws SQLException {
        HashMap<String, Integer> boundaries = db.getInternColumnBoundaries();
        Column idCol = new Column("ID",boundaries.get("ID"), Integer.class, 1);
        Column nameCol = new Column("Name",boundaries.get("Name"), String.class, 2);
        Column trackCol = new Column("Track",boundaries.get("Track"), String.class, 3);
        return new TableBuilder()
                .resultSet(contents)
                .column(idCol)
                .column(nameCol)
                .column(trackCol)
                .buildTable();
    }

    public Table prepareDetailsTable(ResultSet contents) throws SQLException {
        HashMap<String, Integer> boundaries = db.getInternColumnBoundaries();
        Column idCol = new Column("ID",boundaries.get("ID"), Integer.class, 1);
        Column nameCol = new Column("Name",boundaries.get("Name"), String.class, 2);
        Column gpaCol = new Column("GPA",boundaries.get("GPA"), Double.class, 3);
        Column cvCol = new Column("CV",boundaries.get("CV"), String.class, 4);
        Column uniCol = new Column("University",boundaries.get("University"), String.class, 5);
        Column trackCol = new Column("Track",boundaries.get("Track"), String.class, 6);
        return new TableBuilder()
                .resultSet(contents)
                .column(idCol)
                .column(nameCol)
                .column(gpaCol)
                .column(cvCol)
                .column(uniCol)
                .column(trackCol)
                .buildTable();
    }

    public Table listInterns() throws SQLException {
        ResultSet contents = db.getAllInterns();
        return prepareShorthandTable(contents);
    }

    public Table listInternsByUni(String university) throws SQLException {
        ResultSet contents = db.getInternsByUni(university);
        return prepareShorthandTable(contents);
    }

    public Table listInternsByUniAndTrack(String university, String track) throws SQLException {
        ResultSet contents = db.getInternsByUniAndTrack(university, track);
        return prepareShorthandTable(contents);
    }

    public Table listInternDetailsByID(int id) throws SQLException {
        ResultSet contents = db.getInternByID(id);
        return prepareDetailsTable(contents);
    }

}
