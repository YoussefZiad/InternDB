package ui.table;

import java.sql.ResultSet;
import java.util.ArrayList;

public class TableBuilder {

    private ArrayList<Column> _columns;
    private ResultSet _rs;

    public TableBuilder(){
        _columns = new ArrayList<>();
    }

    public Table buildTable(){
        return new Table(_columns, _rs);
    }

    public TableBuilder column(Column _column){
        _columns.add(_column);
        return this;
    }

    public TableBuilder resultSet(ResultSet _rs){
        this._rs = _rs;
        return this;
    }

}
