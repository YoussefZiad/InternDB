package task;
import classes.Intern;
import database.DBLayer;
import services.InternService;
import services.ProjectService;
import ui.ProjectUILayer;
import ui.util.InterfacePrinter;
import ui.InternUILayer;
import ui.util.UserScanner;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class InternshipDBConsole {

    static Scanner sc;
    static InternUILayer internUI;
    static ProjectUILayer projectUI;

    public static void main(String[] args) throws SQLException {

        sc = new Scanner(System.in);
        DBLayer db = new DBLayer();
        InternService internService = new InternService(db);
        ProjectService projectService = new ProjectService(db);
        internUI = new InternUILayer(internService);
        projectUI = new ProjectUILayer(projectService, internService);

        try{


            InterfacePrinter.printTitleScreen();
            boolean exit = false;

            do {

                InterfacePrinter.printInstructions();

                String command = sc.nextLine();

                switch (command) {
                    case "1":
                        internUI.addIntern();
                        break;
                    case "2":
                        internUI.listInterns();
                        break;
                    case "3":
                        internUI.listInternsUni();
                        break;
                    case "4":
                        internUI.listInternsTop();
                        break;
                    case "5":
                        internUI.deleteIntern();
                        break;
                    case "6":
                        internUI.AcceptOrRejectIntern(true);
                        break;
                    case "7":
                        internUI.AcceptOrRejectIntern(false);
                        break;
                    case "8":
                        projectUI.addProject();
                        break;
                    case "9":
                        projectUI.listProjects();
                        break;
                    case "10":
                        exit = true;
                        break;
                    default:
                        System.out.println("This input is not valid, please enter the number of a valid operation.");
                }

            } while (!exit);
        }
        catch(Exception e){
            System.out.println(e);
        }


    }

}
