package task;
import java.io.PrintWriter;
import java.sql.*;
import java.text.ParseException;
import java.util.Scanner;

public class InternshipDBConsole {

    public static void addIntern( Scanner sc, Connection con) throws SQLException {
        System.out.println("Adding a new intern to the database...");
        System.out.println("Enter Intern Name:");
        
        String name = sc.nextLine();
        double gpa;
        while(true) {
            try {
                System.out.println("Enter Intern GPA:");

                gpa = Double.parseDouble(sc.nextLine());

                gpa = Math.round(gpa*10.0)/10.0;

                break;
            }
            catch(NumberFormatException e){
                System.out.println("GPA must be a number. Please enter a valid number.");
            }
        }
        System.out.println("Enter Link to Intern CV:");
        
        String cvURL = sc.nextLine();
        System.out.println("Enter Intern University:");
        
        String uni = sc.nextLine();
        String track;
        while(true) {
            System.out.println("Enter the number corresponding to the intern's desired track:");
            System.out.println("1- Backend");
            System.out.println("2- Frontend");
            System.out.println("3- Data");

            String choice = sc.nextLine();

            track = choice.equals("1") ? "Backend" : choice.equals("2") ? "Frontend" : choice.equals("3") ? "Data" : "Invalid";
            if(track.equals("Invalid")){
                System.out.println("The input is invalid. Please enter the number of a valid track.");
                continue;
            }
            break;
        }

        System.out.println("Please confirm that you want to insert an intern application with these details:");
        int detailLength = Math.max(name.length(),4) + 4 + Math.max(cvURL.length(),2) + Math.max(uni.length(),10) + Math.max(track.length(),5) + 18;
        System.out.println(new String(new char[detailLength]).replace('\0','_'));
        System.out.println("| Name" + (name.length()>4? new String(new char[name.length()-4]).replace('\0',' '):"")+
                " | GPA | CV"+(cvURL.length()>2? new String(new char[cvURL.length()-2]).replace('\0',' '):"")+
                " | University"+(uni.length()>10? new String(new char[uni.length()-10]).replace('\0',' '):"")
                +" | Track"+(track.length()> 5?new String(new char[track.length()-5]).replace('\0',' '):"")+" | ");

        System.out.println(new String(new char[detailLength]).replace('\0','_'));
        System.out.println("| "+name+(name.length()<4? new String(new char[4-name.length()]).replace('\0',' '):"")+
                " | "+gpa+
                " | "+cvURL+(cvURL.length()<2? new String(new char[2-cvURL.length()]).replace('\0',' '):"")+
                " | "+uni+(uni.length()<10? new String(new char[10-uni.length()]).replace('\0',' '):"")+
                " | "+track+(track.length()<5? new String(new char[5-track.length()]).replace('\0',' '):"")+
                " | ");
        System.out.println(new String(new char[detailLength]).replace('\0','_'));

        System.out.println();
        System.out.println("Do you want to proceed? yes/no:");
        String confirm = sc.nextLine();

        if(confirm.equals("no")){
            System.out.print("Insertion Aborted.");
            return;
        }


        Statement stmt=con.createStatement();
        stmt.execute("insert into intern (name,gpa,cvURL,university,desiredTrack) values('"+name+"',"+gpa+",'"+cvURL+"','"+uni+"','"+track+"')");

        System.out.println("Inserted Intern Application Successfully!");
    }

    public static void listInterns(Scanner sc, Connection con) throws SQLException {
        System.out.println("List of all interns:");
        Statement stmt=con.createStatement();
        Statement stmt2=con.createStatement();
        ResultSet rs = stmt.executeQuery("select id, name, desiredTrack from intern");
        ResultSet rs2 = stmt2.executeQuery("SELECT MAX(LENGTH(name)), MAX(LENGTH(desiredTrack)), COUNT(*) FROM intern");
        rs2.next();
        int nameLength = rs2.getInt(1);
        int trackLength = rs2.getInt(2);
        int detailLength = 2+Math.max(nameLength,4)+Math.max(trackLength,5)+12;
        int count = rs2.getInt(3);
        System.out.println(new String(new char[detailLength]).replace('\0','_'));
        System.out.println("| ID"+(Math.log10(count)>2? new String(new char[(int)Math.log10(count)-2]).replace('\0',' '):"")+
                " | Name" + (nameLength>4? new String(new char[nameLength-4]).replace('\0',' '):"")+
                " | Track"+(trackLength> 5?new String(new char[trackLength-5]).replace('\0',' '):"")+" | ");
        System.out.println(new String(new char[detailLength]).replace('\0','_'));
        int i = 1;
        while(rs.next()){
            String name = rs.getString(2);
            String track = rs.getString(3);
            System.out.println("| "+rs.getInt(1)+new String(new char[(int)(Math.log10(count)-Math.log10(i)+1)]).replace('\0',' ')+
                    " | "+name+(nameLength>name.length()? new String(new char[nameLength-name.length()]).replace('\0',' '):"")+
                    " | "+track+(trackLength>track.length()? new String(new char[trackLength-track.length()]).replace('\0',' '):"")+
                    " | ");

            i++;
        }
        System.out.println(new String(new char[detailLength]).replace('\0','_'));

    }

    public static void listInternsUni(Scanner sc, Connection con) throws SQLException {
        System.out.println("Enter University:");

        String university = sc.nextLine();
        System.out.println("List of interns from "+university+":");
        Statement stmt=con.createStatement();
        Statement stmt2=con.createStatement();
        ResultSet rs = stmt.executeQuery("select id, name, desiredTrack from intern where university = '"+university+"'");
        ResultSet rs2 = stmt2.executeQuery("SELECT MAX(LENGTH(name)), MAX(LENGTH(desiredTrack)), COUNT(*) FROM intern");
        rs2.next();
        int nameLength = rs2.getInt(1);
        int trackLength = rs2.getInt(2);
        int detailLength = 2+Math.max(nameLength,4)+Math.max(trackLength,5)+12;
        int count = rs2.getInt(3);
        System.out.println(new String(new char[detailLength]).replace('\0','_'));
        System.out.println("| ID"+(Math.log10(count)>2? new String(new char[(int)Math.log10(count)-2]).replace('\0',' '):"")+
                " | Name" + (nameLength>4? new String(new char[nameLength-4]).replace('\0',' '):"")+
                " | Track"+(trackLength> 5?new String(new char[trackLength-5]).replace('\0',' '):"")+" | ");
        System.out.println(new String(new char[detailLength]).replace('\0','_'));
        int i = 1;
        while(rs.next()){
            String name = rs.getString(2);
            String track = rs.getString(3);
            System.out.println("| "+rs.getInt(1)+new String(new char[(int)(Math.log10(count)-Math.log10(i)+1)]).replace('\0',' ')+
                    " | "+name+(nameLength>name.length()? new String(new char[nameLength-name.length()]).replace('\0',' '):"")+
                    " | "+track+(trackLength>track.length()? new String(new char[trackLength-track.length()]).replace('\0',' '):"")+
                    " | ");

            i++;
        }
        System.out.println(new String(new char[detailLength]).replace('\0','_'));

    }

    public static void deleteIntern( Scanner sc, Connection con) throws SQLException {
        System.out.println("Deleting an intern application from the database:");

        int id;
        while(true) {
            try {
                System.out.println("Enter Intern id:");

                id = Integer.parseInt(sc.nextLine());

                break;
            }
            catch(NumberFormatException e){
                System.out.println("ID must be a number. Please enter a valid number.");
            }
        }

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from intern where id = '"+id+"'");
        rs.next();
        String name = rs.getString(2);
        double gpa = rs.getDouble(3);
        String cvURL = rs.getString(4);
        String uni = rs.getString(5);
        String track = rs.getString(6);


        System.out.println("Please confirm that you want to DELETE an intern application with these details:");
        int detailLength = Math.max(name.length(),4) + 4 + Math.max(cvURL.length(),2) + Math.max(uni.length(),10) + Math.max(track.length(),5) + 18;
        System.out.println(new String(new char[detailLength]).replace('\0','_'));
        System.out.println("| Name" + (name.length()>4? new String(new char[name.length()-4]).replace('\0',' '):"")+
                " | GPA | CV"+(cvURL.length()>2? new String(new char[cvURL.length()-2]).replace('\0',' '):"")+
                " | University"+(uni.length()>10? new String(new char[uni.length()-10]).replace('\0',' '):"")
                +" | Track"+(track.length()> 5?new String(new char[track.length()-5]).replace('\0',' '):"")+" | ");

        System.out.println(new String(new char[detailLength]).replace('\0','_'));
        System.out.println("| "+name+(name.length()<4? new String(new char[4-name.length()]).replace('\0',' '):"")+
                " | "+gpa+
                " | "+cvURL+(cvURL.length()<2? new String(new char[2-cvURL.length()]).replace('\0',' '):"")+
                " | "+uni+(uni.length()<10? new String(new char[10-uni.length()]).replace('\0',' '):"")+
                " | "+track+(track.length()<5? new String(new char[5-track.length()]).replace('\0',' '):"")+
                " | ");
        System.out.println(new String(new char[detailLength]).replace('\0','_'));

        System.out.println();
        System.out.println("Do you want to proceed? yes/no:");
        String confirm = sc.nextLine();

        if(confirm.equals("no")){
            System.out.print("Deletion Aborted.");
            return;
        }


        Statement stmt2=con.createStatement();
        stmt2.execute("delete from intern where id = '"+id+"'");

        System.out.println("Deleted Intern Application Successfully!");
    }

    public static void AcceptOrRejectIntern( Scanner sc, Connection con, boolean accept) throws SQLException {
        if(accept)
            System.out.println("Accepting an intern application from the database:");
        else
            System.out.println("Rejecting an intern application from the database:");
        int id;
        while(true) {
            try {
                System.out.println("Enter Intern id:");

                id = Integer.parseInt(sc.nextLine());

                break;
            }
            catch(NumberFormatException e){
                System.out.println("ID must be a number. Please enter a valid number.");
            }
        }

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from intern where id = '"+id+"'");
        rs.next();
        String name = rs.getString(2);
        double gpa = rs.getDouble(3);
        String cvURL = rs.getString(4);
        String uni = rs.getString(5);
        String track = rs.getString(6);

        if(accept)
            System.out.println("Please confirm that you want to ACCEPT an intern application with these details:");
        else
            System.out.println("Please confirm that you want to REJECT an intern application with these details:");
        int detailLength = Math.max(name.length(),4) + 4 + Math.max(cvURL.length(),2) + Math.max(uni.length(),10) + Math.max(track.length(),5) + 18;
        System.out.println(new String(new char[detailLength]).replace('\0','_'));
        System.out.println("| Name" + (name.length()>4? new String(new char[name.length()-4]).replace('\0',' '):"")+
                " | GPA | CV"+(cvURL.length()>2? new String(new char[cvURL.length()-2]).replace('\0',' '):"")+
                " | University"+(uni.length()>10? new String(new char[uni.length()-10]).replace('\0',' '):"")
                +" | Track"+(track.length()> 5?new String(new char[track.length()-5]).replace('\0',' '):"")+" | ");

        System.out.println(new String(new char[detailLength]).replace('\0','_'));
        System.out.println("| "+name+(name.length()<4? new String(new char[4-name.length()]).replace('\0',' '):"")+
                " | "+gpa+
                " | "+cvURL+(cvURL.length()<2? new String(new char[2-cvURL.length()]).replace('\0',' '):"")+
                " | "+uni+(uni.length()<10? new String(new char[10-uni.length()]).replace('\0',' '):"")+
                " | "+track+(track.length()<5? new String(new char[5-track.length()]).replace('\0',' '):"")+
                " | ");
        System.out.println(new String(new char[detailLength]).replace('\0','_'));

        System.out.println();
        System.out.println("Do you want to proceed? yes/no:");
        String confirm = sc.nextLine();

        if(confirm.equals("no")){
            System.out.print("Operation Aborted.");
            return;
        }


        Statement stmt2=con.createStatement();
        stmt2.execute("update intern set isAccepted = "+(accept?1:0)+" where id = '"+id+"'");

        if(accept)
            System.out.println("Accepted Intern Application Successfully!");
        else
            System.out.println("Rejected Intern Application Successfully!");
    }

    public static void listInternsTop(Scanner sc, Connection con) throws SQLException {
        System.out.println("Enter University:");

        String university = sc.nextLine();
        System.out.println("Enter Track:");

        String desiredTrack = sc.nextLine();
        System.out.println("List of top interns from "+university+" and on the "+desiredTrack+" track:");
        Statement stmt=con.createStatement();
        Statement stmt2=con.createStatement();
        ResultSet rs = stmt.executeQuery("select id, name, desiredTrack from intern where university = '"+university+"' and desiredTrack = '"+desiredTrack+"' and gpa > 3.0");
        ResultSet rs2 = stmt2.executeQuery("SELECT MAX(LENGTH(name)), MAX(LENGTH(desiredTrack)), COUNT(*) FROM intern");
        rs2.next();
        int nameLength = rs2.getInt(1);
        int trackLength = rs2.getInt(2);
        int detailLength = 2+Math.max(nameLength,4)+Math.max(trackLength,5)+12;
        int count = rs2.getInt(3);
        System.out.println(new String(new char[detailLength]).replace('\0','_'));
        System.out.println("| ID"+(Math.log10(count)>2? new String(new char[(int)Math.log10(count)-2]).replace('\0',' '):"")+
                " | Name" + (nameLength>4? new String(new char[nameLength-4]).replace('\0',' '):"")+
                " | Track"+(trackLength> 5?new String(new char[trackLength-5]).replace('\0',' '):"")+" | ");
        System.out.println(new String(new char[detailLength]).replace('\0','_'));
        int i = 1;
        while(rs.next()){
            String name = rs.getString(2);
            String track = rs.getString(3);
            System.out.println("| "+rs.getInt(1)+new String(new char[(int)(Math.log10(count)-Math.log10(i)+1)]).replace('\0',' ')+
                    " | "+name+(nameLength>name.length()? new String(new char[nameLength-name.length()]).replace('\0',' '):"")+
                    " | "+track+(trackLength>track.length()? new String(new char[trackLength-track.length()]).replace('\0',' '):"")+
                    " | ");

            i++;
        }
        System.out.println(new String(new char[detailLength]).replace('\0','_'));

    }

    public static void addProject( Scanner sc, Connection con) throws SQLException {
        System.out.println("Adding a new project to the database...");
        String name;
        int id;
        while(true) {
            try {
                System.out.println("Enter Intern ID:");

                id = Integer.parseInt(sc.nextLine());
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select * from intern where id = '"+id+"'");
                rs.next();
                name = rs.getString(2);
                double gpa = rs.getDouble(3);
                String cvURL = rs.getString(4);
                String uni = rs.getString(5);
                String track = rs.getString(6);


                System.out.println("Is this the correct intern?");
                int detailLength = Math.max(name.length(),4) + 4 + Math.max(cvURL.length(),2) + Math.max(uni.length(),10) + Math.max(track.length(),5) + 18;
                System.out.println(new String(new char[detailLength]).replace('\0','_'));
                System.out.println("| Name" + (name.length()>4? new String(new char[name.length()-4]).replace('\0',' '):"")+
                        " | GPA | CV"+(cvURL.length()>2? new String(new char[cvURL.length()-2]).replace('\0',' '):"")+
                        " | University"+(uni.length()>10? new String(new char[uni.length()-10]).replace('\0',' '):"")
                        +" | Track"+(track.length()> 5?new String(new char[track.length()-5]).replace('\0',' '):"")+" | ");

                System.out.println(new String(new char[detailLength]).replace('\0','_'));
                System.out.println("| "+name+(name.length()<4? new String(new char[4-name.length()]).replace('\0',' '):"")+
                        " | "+gpa+
                        " | "+cvURL+(cvURL.length()<2? new String(new char[2-cvURL.length()]).replace('\0',' '):"")+
                        " | "+uni+(uni.length()<10? new String(new char[10-uni.length()]).replace('\0',' '):"")+
                        " | "+track+(track.length()<5? new String(new char[5-track.length()]).replace('\0',' '):"")+
                        " | ");
                System.out.println(new String(new char[detailLength]).replace('\0','_'));

                System.out.println();
                System.out.println("Do you want to proceed? yes/no:");
                String confirm = sc.nextLine();

                if(confirm.equals("no")){
                    System.out.print("Insertion Aborted.");
                    return;
                }

                break;
            }
            catch(NumberFormatException e){
                System.out.println("ID must be a number. Please enter a valid number.");
            }
            catch(SQLException e){
                System.out.println("An intern with this ID does not exist. Please enter a valid ID.");
            }
        }
        System.out.println("Enter Project name:");

        String projectName = sc.nextLine();
        System.out.println("Enter Project Description:");

        String desc = sc.nextLine();

        System.out.println("Please confirm that you want to add a project with these details to "+name+":");
        int detailLength = Math.max(projectName.length(),4) + Math.max(desc.length(),11) + 9;
        System.out.println(new String(new char[detailLength]).replace('\0','_'));
        System.out.println("| Name" + (projectName.length()>4? new String(new char[projectName.length()-4]).replace('\0',' '):"")+
                " | Description"+(desc.length()> 5?new String(new char[desc.length()-11]).replace('\0',' '):"")+" | ");

        System.out.println(new String(new char[detailLength]).replace('\0','_'));
        System.out.println("| "+projectName+(projectName.length()<4? new String(new char[4-projectName.length()]).replace('\0',' '):"")+
                " | "+desc+(desc.length()<5? new String(new char[11-desc.length()]).replace('\0',' '):"")+
                " | ");
        System.out.println(new String(new char[detailLength]).replace('\0','_'));

        System.out.println();
        System.out.println("Do you want to proceed? yes/no:");
        String confirm = sc.nextLine();

        if(confirm.equals("no")){
            System.out.print("Insertion Aborted.");
            return;
        }


        Statement stmt=con.createStatement();
        stmt.execute("insert into project (name,description,intern_id) values('"+projectName+"','"+desc+"',"+id+")");

        System.out.println("Inserted Project Successfully!");
    }

    public static void listProjects(Scanner sc, Connection con) throws SQLException {
        System.out.println("Enter Intern ID:");

        String name;
        int id;
        while(true) {
            try {
                System.out.println("Enter Intern ID:");

                id = Integer.parseInt(sc.nextLine());
                Statement stmt = con.createStatement();
                ResultSet rs3 = stmt.executeQuery("select * from intern where id = '"+id+"'");
                rs3.next();
                name = rs3.getString(2);
                double gpa = rs3.getDouble(3);
                String cvURL = rs3.getString(4);
                String uni = rs3.getString(5);
                String track = rs3.getString(6);


                System.out.println("Is this the correct intern?");
                int detailLength = Math.max(name.length(),4) + 4 + Math.max(cvURL.length(),2) + Math.max(uni.length(),10) + Math.max(track.length(),5) + 18;
                System.out.println(new String(new char[detailLength]).replace('\0','_'));
                System.out.println("| Name" + (name.length()>4? new String(new char[name.length()-4]).replace('\0',' '):"")+
                        " | GPA | CV"+(cvURL.length()>2? new String(new char[cvURL.length()-2]).replace('\0',' '):"")+
                        " | University"+(uni.length()>10? new String(new char[uni.length()-10]).replace('\0',' '):"")
                        +" | Track"+(track.length()> 5?new String(new char[track.length()-5]).replace('\0',' '):"")+" | ");

                System.out.println(new String(new char[detailLength]).replace('\0','_'));
                System.out.println("| "+name+(name.length()<4? new String(new char[4-name.length()]).replace('\0',' '):"")+
                        " | "+gpa+
                        " | "+cvURL+(cvURL.length()<2? new String(new char[2-cvURL.length()]).replace('\0',' '):"")+
                        " | "+uni+(uni.length()<10? new String(new char[10-uni.length()]).replace('\0',' '):"")+
                        " | "+track+(track.length()<5? new String(new char[5-track.length()]).replace('\0',' '):"")+
                        " | ");
                System.out.println(new String(new char[detailLength]).replace('\0','_'));

                System.out.println();
                System.out.println("Do you want to proceed? yes/no:");
                String confirm = sc.nextLine();

                if(confirm.equals("no")){
                    System.out.print("Insertion Aborted.");
                    return;
                }

                break;
            }
            catch(NumberFormatException e){
                System.out.println("ID must be a number. Please enter a valid number.");
            }
            catch(SQLException e){
                System.out.println("An intern with this ID does not exist. Please enter a valid ID.");
            }
        }
        System.out.println("List of projects assigned to "+name+":");
        Statement stmt=con.createStatement();
        Statement stmt2=con.createStatement();
        ResultSet rs = stmt.executeQuery("select id, name, description from project where intern_id = '"+id+"'");
        ResultSet rs2 = stmt2.executeQuery("SELECT MAX(LENGTH(name)), MAX(LENGTH(description)), COUNT(*) FROM project");
        rs2.next();
        int nameLength = rs2.getInt(1);
        int descLength = rs2.getInt(2);
        int detailLength = 2+Math.max(nameLength,4)+Math.max(descLength,5)+12;
        int count = rs2.getInt(3);
        System.out.println(new String(new char[detailLength]).replace('\0','_'));
        System.out.println("| ID"+(Math.log10(count)>2? new String(new char[(int)Math.log10(count)-2]).replace('\0',' '):"")+
                " | Name" + (nameLength>4? new String(new char[nameLength-4]).replace('\0',' '):"")+
                " | Description"+(descLength> 11?new String(new char[descLength-11]).replace('\0',' '):"")+" | ");
        System.out.println(new String(new char[detailLength]).replace('\0','_'));
        int i = 1;
        while(rs.next()){
            String projectName = rs.getString(2);
            String track = rs.getString(3);
            System.out.println("| "+rs.getInt(1)+new String(new char[(int)(Math.log10(count)-Math.log10(i)+1)]).replace('\0',' ')+
                    " | "+projectName+(nameLength>projectName.length()? new String(new char[nameLength-projectName.length()]).replace('\0',' '):"")+
                    " | "+track+(descLength>track.length()? new String(new char[descLength-track.length()]).replace('\0',' '):"")+
                    " | ");

            i++;
        }
        System.out.println(new String(new char[detailLength]).replace('\0','_'));

    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        try{
            Connection con=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/internship_schema","root","password");


            System.out.println("___________________________________________");
            System.out.println("|       Intern Application Database       |");
            System.out.println("___________________________________________");
            System.out.println();
            System.out.println("Welcome!");


            

            Scanner sc = new Scanner(System.in);
            boolean exit = false;

            while(true){

                System.out.println("Please enter the number of the operation you wish to perform and hit the Enter key:");
                System.out.println();
                System.out.println("1- Add an intern application to the database.");
                System.out.println("2- List all intern applications in the database.");
                System.out.println("3- List intern applications in a certain university.");
                System.out.println("4- List top intern applications in a certain university and track.");
                System.out.println("5- Delete an intern application.");
                System.out.println("6- Accept an application.");
                System.out.println("7- Reject an application.");
                System.out.println("8- Assign a project to an intern.");
                System.out.println("9- List all projects for a certain intern.");
                System.out.println("10- Exit the program.");

                String command = sc.nextLine();

                switch (command){
                    case "1": addIntern(sc, con); break;
                    case "2": listInterns(sc, con); break;
                    case "3": listInternsUni(sc, con); break;
                    case "4": listInternsTop(sc, con);break;
                    case "5": deleteIntern(sc, con); break;
                    case "6": AcceptOrRejectIntern(sc, con, true); break;
                    case "7": AcceptOrRejectIntern(sc, con, false); break;
                    case "8": addProject(sc, con); break;
                    case "9": listProjects(sc, con); break;
                    case "10": exit = true; break;
                    default: System.out.println("This input is not valid, please enter the number of a valid operation.");
                }

                if(exit)
                    break;
            }


            con.close();
        }
        catch(Exception e){
            System.out.println(e);
        }

        
    }

}
