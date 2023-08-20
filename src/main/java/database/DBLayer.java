package database;

import classes.Intern;
import classes.Project;

import java.sql.*;
import java.util.HashMap;

public class DBLayer {

    private static Connection connection;

    public DBLayer() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/internship_schema","root","password");
    }

    public void addInternToDB(Intern intern) throws SQLException {
        Statement stmt=connection.createStatement();
        stmt.execute("insert into intern (name,gpa,cvURL,university,desiredTrack) values('"
                +intern.getName()+"',"+intern.getGpa()+",'"+intern.getCvURL()+"','"+intern.getUniversity()+"','"+intern.getDesiredTrack()+"')");
    }

    public void addProjectToDB(Project project) throws SQLException {
        Statement stmt=connection.createStatement();
        stmt.execute("insert into project (name,description,intern_id) values('"+project.getName()+"','"+project.getDescription()+"',"+project.getInternID()+")");
    }

    public void deleteInternFromDB(int id) throws SQLException{
        Statement stmt=connection.createStatement();
        stmt.execute("delete from intern where id = '"+id+"'");
    }

    public void updateInternAcceptanceInDB(int id, boolean accept) throws SQLException {
        Statement stmt=connection.createStatement();
        stmt.execute("update intern set isAccepted = "+(accept?1:0)+" where id = '"+id+"'");
    }

    public ResultSet getAllInterns() throws SQLException {
        Statement stmt = connection.createStatement();
        return stmt.executeQuery("select id, name, desiredTrack from intern");
    }

    public ResultSet getProjectsByInternID(int id) throws SQLException{
        Statement stmt=connection.createStatement();
        return stmt.executeQuery("select id, name, description from project where intern_id = '"+id+"'");
    }

    public ResultSet getInternsByUni(String university) throws SQLException{
        Statement stmt = connection.createStatement();
        return stmt.executeQuery("select id, name, desiredTrack from intern where university = '"+university+"' order by gpa desc");
    }

    public ResultSet getInternsByUniAndTrack(String university, String desiredTrack) throws SQLException {
        Statement stmt=connection.createStatement();
        return stmt.executeQuery("select id, name, desiredTrack from intern where university = '"+university+"' and desiredTrack = '"+desiredTrack+"' and gpa > 3.0");
    }

    public HashMap<String, Integer> getInternColumnBoundaries() throws SQLException {
        HashMap<String, Integer> bounds = new HashMap<String, Integer>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT MAX(id), MAX(LENGTH(name)), MAX(LENGTH(cvURL)), MAX(LENGTH(university)), MAX(LENGTH(desiredTrack)) FROM intern");
        rs.next();
        bounds.put("ID",rs.getInt(1));
        bounds.put("Name",rs.getInt(2));
        bounds.put("GPA",1);
        bounds.put("CV",rs.getInt(3));
        bounds.put("University",rs.getInt(4));
        bounds.put("Track",rs.getInt(5));
        return bounds;
    }

    public HashMap<String, Integer> getProjectColumnBoundaries() throws SQLException{
        HashMap<String, Integer> bounds = new HashMap<String, Integer>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT MAX(id), MAX(LENGTH(name)), MAX(LENGTH(description)) FROM project");
        rs.next();
        bounds.put("ID",rs.getInt(1));
        bounds.put("Name",rs.getInt(2));
        bounds.put("Description",rs.getInt(3));
        return bounds;
    }

    public ResultSet getInternByID(int id) throws SQLException{
        Statement stmt = connection.createStatement();
        return stmt.executeQuery("select id, name, gpa, cvURL, university, desiredTrack from intern where id = '"+id+"'");
    }


}
