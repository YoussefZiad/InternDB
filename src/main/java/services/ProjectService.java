package services;

import classes.Project;
import database.DBLayer;
import ui.table.Column;
import ui.table.Table;
import ui.table.TableBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ProjectService {

    private DBLayer db;

    public ProjectService(DBLayer db){
        this.db = db;
    }

    public void addProjectToDB(Project project) throws SQLException {
        db.addProjectToDB(project);
    }

    public Table listProjectsByInternID(int id) throws SQLException {
        ResultSet contents = db.getProjectsByInternID(id);
        return prepareDetailsTable(contents);
    }

    public Table prepareDetailsTable(ResultSet contents) throws SQLException {
        HashMap<String, Integer> boundaries = db.getProjectColumnBoundaries();
        Column idCol = new Column("ID",boundaries.get("ID"), Integer.class, 1);
        Column nameCol = new Column("Name",boundaries.get("Name"), String.class, 2);
        Column trackCol = new Column("Description",boundaries.get("Description"), String.class, 3);
        return new TableBuilder()
                .resultSet(contents)
                .column(idCol)
                .column(nameCol)
                .column(trackCol)
                .buildTable();
    }

}
