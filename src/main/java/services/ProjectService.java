package services;

import classes.Project;
import database.DBLayer;
import org.hibernate.query.Query;
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
        Query contents = db.getProjectsByInternID(id);
        return prepareDetailsTable(contents);
    }

    public Table prepareDetailsTable(Query contents) throws SQLException {
        HashMap<String, Integer> boundaries = db.getProjectColumnBoundaries();
        Column idCol = new Column("ID",boundaries.get("ID"), Integer.class, 1);
        Column nameCol = new Column("Name",boundaries.get("Name"), String.class, 2);
        Column trackCol = new Column("Description",boundaries.get("Description"), String.class, 3);
        return new TableBuilder()
                .query(contents)
                .column(idCol)
                .column(nameCol)
                .column(trackCol)
                .buildTable();
    }

}
