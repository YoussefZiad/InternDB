package database;

import classes.Intern;
import classes.Project;

import java.sql.*;
import java.util.HashMap;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

public class DBLayer {

    private SessionFactory sessionFactory;

    public void setUp(){
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    public void tearDown(){
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
    }

    public Session startSession(){
        if(sessionFactory != null){
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            return session;
        }
        return null;
    }

    public void commitTransaction(Session session){
        session.getTransaction().commit();
        session.close();
    }

    public DBLayer() throws SQLException {
        setUp();
    }

    public void addInternToDB(Intern intern) throws SQLException {

    }

    public void addProjectToDB(Project project) throws SQLException {

    }

    public void deleteInternFromDB(int id) throws SQLException {

    }

    public void updateInternAcceptanceInDB(int id, boolean accept) throws SQLException {
        Session session = startSession();
        Query query = session.createQuery("update Intern set accepted = :accepted where id = :id");
        query.setParameter("accepted",accept?1:0);
        query.setParameter("id",id);
        commitTransaction(session);
    }

    public ResultSet getAllInterns() throws SQLException {
        return null;
    }

    public ResultSet getProjectsByInternID(int id) throws SQLException{
        return null;
    }

    public ResultSet getInternsByUni(String university) throws SQLException{
        return null;
    }

    public ResultSet getInternsByUniAndTrack(String university, String desiredTrack) throws SQLException {
        return null;
    }

    public HashMap<String, Integer> getInternColumnBoundaries() throws SQLException {
        return null;
    }

    public HashMap<String, Integer> getProjectColumnBoundaries() throws SQLException{
        return null;
    }

    public ResultSet getInternByID(int id) throws SQLException{
        return null;
    }


}
