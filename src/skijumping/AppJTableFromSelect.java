package skijumping;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AppJTableFromSelect {
    private JTable component;
    String select;
    ArrayList<String> columnNames;
    Connection c;
    ArrayList<ArrayList<String>> values;
    TableModel dataModel;

    AppJTableFromSelect(String select, ArrayList<String> columnNames, Connection c){
        this.select = select;
        this.columnNames = columnNames;
        this.c = c;
        values = new ArrayList<>();
    }
    private void createComponent(){
        //DefaultTableColumnModel defaultTableColumnModel = new DefaultTableColumnModel();

        dataModel = new AbstractTableModel() {
            public int getColumnCount() { return columnNames.size(); }
            public String getColumnName(int index) {
                return columnNames.get(index);
            }
            public int getRowCount() { return values.size();}
            public Object getValueAt(int row, int col) { return values.get(row).get(col); }
        };
        component = new JTable(dataModel);
        fetchData();
        //component.getTableHeader().setDefaultRenderer(new );
    }

    private void fetchData(){
        try {
            values.clear();
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery(select);
            while (rs.next()){
                ArrayList<String> row = new ArrayList<>();
                for (int i = 0; i < columnNames.size(); i++){
                    row.add(rs.getString(i+1));
                }
                values.add(row);
            }
            System.out.println("OK");
            component.repaint();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public JTable getComponent() {
        if(component == null){
            createComponent();
        }
        return component;
    }

    public ArrayList<ArrayList<String>> getValues() {
        return values;
    }

    public String getSelected(){
        return null;
    }

    void refreshData(){
        fetchData();

    }
}
