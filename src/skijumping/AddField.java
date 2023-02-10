package skijumping;

import java.sql.Connection;

public class AddField {
    private String fieldName;
    private String columnName;
    private char type; //domyslnie s - string, i - int, d - double, e - date, c - comboBox, a - default, wiem, ze brzydko, ale nie wiem jak inaczej
    Connection c;
    String select;
    String defaultValue;
    AddField(String fieldName, String columnName, char type){
        this.fieldName = fieldName;
        this.columnName = columnName;
        this.type = type;
    }

    AddField(String fieldName, String columnName, String select){
        this.fieldName = fieldName;
        this.columnName = columnName;
        this.type = 'c';
        //this.c = c;
        this.select = select;

    }

    AddField(String columnName, String defaultValue){
        this.columnName = columnName;
        this.defaultValue = defaultValue;
        this.type = 'a';
    }

    public String getColumnName() {
        return columnName;
    }

    public String getFieldName() {
        return fieldName;
    }
    public char getType(){
        return type;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
