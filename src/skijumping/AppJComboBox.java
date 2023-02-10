package skijumping;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AppJComboBox {
    String[] valuesList(){

        String[] kraje = {"Polska", "Niemcy"};
        return kraje;
    }
    JComboBox component;
    Connection c;
    String select;
    ArrayList<String> idList;
    ArrayList<String> descriptionList;
    AppJComboBox(Connection c, String select){
        this.c = c;
        this.select = select;
        idList = new ArrayList<>();
        descriptionList = new ArrayList<>();

    }
    String getSelectedID(){
        if (component.getSelectedIndex() < 0){
            return null;
        }
        return idList.get(component.getSelectedIndex());
    }
    void createComponent() throws SQLException {
        Statement statement = null;
        try {
            statement = c.createStatement();
            ResultSet rs = statement.executeQuery(select);
            while (rs.next()){
                //ArrayList<String> row = new ArrayList<>();

                idList.add(rs.getString(1));
                descriptionList.add(rs.getString(2));
            }
            component = new JComboBox<>(descriptionList.toArray());
            System.out.println("OK");
            component.repaint();
            rs.close();
        } catch (SQLException e) {
            throw e;
        }

    }

    JComboBox getComponent() throws SQLException {
        if (component == null){
            createComponent();
        }
        return component;
    }

}
