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
        Session session = startSession();
        session.save(intern);
        commitTransaction(session);
    }

    public void addProjectToDB(Project project) throws SQLException {
        Session session = startSession();
        session.save(project);
        commitTransaction(session);
    }

    public void deleteInternFromDB(int id) throws SQLException {
        Session session = startSession();
        Query query = session.createQuery("delete from Intern where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        commitTransaction(session);
    }

    public void updateInternAcceptanceInDB(int id, boolean accept) throws SQLException {

    }

    public Query getAllInterns() throws SQLException {
        Session session = startSession();
        Query query = session.createQuery("select id, name, desiredTrack from Intern");
        return query;
    }

    public Query getProjectsByInternID(int id) throws SQLException{
        Session session = startSession();
        Query query = session.createQuery("select id, name, description from Project where intern_id = :id");
        query.setParameter("id",id);
        return query;
    }

    public Query getInternsByUni(String university) throws SQLException{
        Session session = startSession();
        Query query = session.createQuery("select id, name, desiredTrack from Intern where university = :uni order by gpa desc");
        query.setParameter("uni", university);
        return query;
    }

    public Query getInternsByUniAndTrack(String university, String desiredTrack) throws SQLException {
        Session session = startSession();
        Query query = session.createQuery("select id, name, desiredTrack from Intern where university = :uni and desiredTrack = :track and gpa > 3.0");
        query.setParameter("uni", university);
        query.setParameter("track", desiredTrack);
        return query;
    }

    public HashMap<String, Integer> getInternColumnBoundaries() throws SQLException {
        HashMap<String, Integer> bounds = new HashMap<String, Integer>();
        Query q = startSession().createQuery("SELECT MAX(id), MAX(LENGTH(name)), MAX(LENGTH(cvURL)), MAX(LENGTH(university)), MAX(LENGTH(desiredTrack)) FROM Intern");
        Object[] result = (Object[]) q.uniqueResult();
        bounds.put("ID",(Integer) result[0]);
        bounds.put("Name",(Integer) result[1]);
        bounds.put("GPA",1);
        bounds.put("CV",(Integer) result[2]);
        bounds.put("University",(Integer) result[3]);
        bounds.put("Track",(Integer) result[4]);
        return bounds;
    }

    public HashMap<String, Integer> getProjectColumnBoundaries() throws SQLException{
        HashMap<String, Integer> bounds = new HashMap<String, Integer>();
        Query q = startSession().createQuery("SELECT MAX(id), MAX(LENGTH(name)), MAX(LENGTH(description)) FROM Project");
        Object[] result = (Object[]) q.uniqueResult();
        bounds.put("ID",(Integer) result[0]);
        bounds.put("Name",(Integer) result[1]);
        bounds.put("Description",(Integer) result[2]);
        return bounds;
    }

    public Query getInternByID(int id) throws SQLException{
        Session session = startSession();
        Query query = session.createQuery("select id, name, gpa, cvURL, university, desiredTrack from Intern where id = :id");
        query.setParameter("id",id);
        return query;
    }


}
