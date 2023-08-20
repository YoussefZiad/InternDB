package ui.util;

import classes.Intern;
import classes.Project;
import ui.InternUILayer;
import ui.table.Column;
import ui.table.Table;
import ui.table.TableBuilder;

import java.sql.SQLException;
import java.util.Scanner;

public class UserScanner {

    static Scanner sc = new Scanner(System.in);

    public static int scanID(){
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
        return id;
    }

    public static String scanString(String message){
        System.out.println(message);

        return sc.nextLine();
    }

    public static double scanGPA(){
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
        return gpa;
    }

    public static String scanTrack(){
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
        return track;
    }

    public static boolean scanConfirmation(){
        System.out.println("Do you want to proceed? yes/no:");
        while(true) {
            String confirm = sc.nextLine();

            if (confirm.equals("no")) {
                return false;
            }
            else if(confirm.equals("yes")){
                return true;
            }

            System.out.println("Invalid response, enter \"yes\" or \"no\"");
        }
    }





}
