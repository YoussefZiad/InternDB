package ui.table;

public class Column {

    private final String columnHead;
    private final int MaxColumnLength;
    private final Class columnType;
    private final int resultSetIndex;
    private String defaultValue;

    public Column(String columnHead, int maxColumnLength, Class columnType, int resultSetIndex) {
        this.columnHead = columnHead;
        MaxColumnLength = maxColumnLength;
        this.columnType = columnType;
        this.resultSetIndex = resultSetIndex;
    }

    public String getColumnHead() {
        return columnHead;
    }

    public int getMaxColumnLength() {
        return MaxColumnLength;
    }

    public Class getColumnType() {
        return columnType;
    }

    public int getResultSetIndex() {
        return resultSetIndex;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue){
        this.defaultValue = defaultValue;
    }
}
