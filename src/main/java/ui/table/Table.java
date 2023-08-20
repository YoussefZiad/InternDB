package ui.table;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Table {

    private ArrayList<Column> columns;
    private ResultSet resultSet;

    public Table(ArrayList<Column> columns, ResultSet resultSet) {
        this.columns = columns;
        this.resultSet = resultSet;
    }

    public ArrayList<Column> getColumns() {
        return columns;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }
}
