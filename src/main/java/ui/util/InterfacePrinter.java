package ui.util;

import org.hibernate.query.Query;
import ui.table.Column;
import ui.table.Table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InterfacePrinter {

    public static void printTitleScreen(){
        System.out.println("___________________________________________");
        System.out.println("|       Intern Application Database       |");
        System.out.println("___________________________________________");
        System.out.println();
        System.out.println("Welcome!");
    }

    public static void printInstructions(){
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
    }

    public static void printTable(Table table) throws SQLException {
        ArrayList<Column> columns = table.getColumns();
        int detailLength = 0;
        int[] maxLengths = new int[columns.size()];
        for(int i = 0; i < columns.size(); i++){
            Column c = columns.get(i);
            maxLengths[i] = Math.max(c.getColumnHead().length(),c.getMaxColumnLength());
            detailLength += maxLengths[i]+3;
        }
        System.out.println(new String(new char[detailLength]).replace('\0','_'));
        System.out.print("| ");
        for(Column c: columns) {
            System.out.print(c.getColumnHead());
            System.out.print((c.getMaxColumnLength()>c.getColumnHead().length()?
                    new String(new char[c.getMaxColumnLength()-c.getColumnHead().length()]).replace('\0',' '):""));
            System.out.print(" | ");
        }
        System.out.println();
        System.out.println(new String(new char[detailLength]).replace('\0','_'));
        Query q = table.getQuery();
        if(q == null){
            System.out.print("| ");
            for(int i = 0; i < columns.size(); i++) {
                Column c = columns.get(i);
                System.out.print(c.getDefaultValue());
                System.out.print(maxLengths[i]>c.getDefaultValue().length()?
                        new String(new char[maxLengths[i]-c.getDefaultValue().length()]).replace('\0',' '):"");
                System.out.print(" | ");
            }
            System.out.println();
        }
        else {
            List<Object[]> list = q.list();
            for(Object[] row: list) {
                System.out.print("| ");
                for (int i = 0; i < columns.size(); i++) {
                    Column c = columns.get(i);
                    System.out.print(row[i]);
                    if (c.getColumnType().equals(Integer.class)) {
                        System.out.print(new String(new char[(int) (maxLengths[i] - Math.log10(i + 1) - 1)]).replace('\0', ' '));
                    } else if (c.getColumnType().equals(String.class)) {
                        System.out.print(maxLengths[i] > ((String) row[i]).length() ? new String(new char[maxLengths[i] - ((String) row[i]).length()]).replace('\0', ' ') : "");
                    } else if (c.getColumnType().equals(Double.class)) {
                        System.out.print(new String(new char[(int) (maxLengths[i] - Math.log10(i) - 3)]).replace('\0', ' '));
                    }
                    System.out.print(" | ");
                }
                System.out.println();
            }
        }
        System.out.println(new String(new char[detailLength]).replace('\0','_'));
    }

}
