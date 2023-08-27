package ui.table;

import org.hibernate.query.Query;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Table {

    private ArrayList<Column> columns;
    private Query query;

    public Table(ArrayList<Column> columns, Query query) {
        this.columns = columns;
        this.query = query;
    }

    public ArrayList<Column> getColumns() {
        return columns;
    }

    public Query getQuery() {
        return query;
    }
}
