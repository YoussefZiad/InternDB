package ui.table;

import org.hibernate.query.Query;

import java.sql.ResultSet;
import java.util.ArrayList;

public class TableBuilder {

    private ArrayList<Column> _columns;
    private Query _query;

    public TableBuilder(){
        _columns = new ArrayList<>();
    }

    public Table buildTable(){
        return new Table(_columns, _query);
    }

    public TableBuilder column(Column _column){
        _columns.add(_column);
        return this;
    }

    public TableBuilder query(Query _query) {
        this._query = _query;
        return this;
    }
}
