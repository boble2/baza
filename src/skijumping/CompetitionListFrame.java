package skijumping;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CompetitionListFrame extends AppJFrame{
    JButton BAddNotStarted;
    JButton BEnterResults;
    JButton BWatchResults;
    void disableButtons(){
        BAddNotStarted.setVisible(false);
        BEnterResults.setVisible(false);
        BWatchResults.setVisible(false);

    }
    @Override
    void prepareToShow() {
        setSize(500, 500);
        setTitle("Konkursy");

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        ArrayList<String> columnNames = new ArrayList<>();
        columnNames.add("data");
        columnNames.add("miejsce");
        columnNames.add("termin zgloszen");
        columnNames.add("status");
        columnNames.add("id");
        columnNames.add("status number");
        String select = "select data, miejsce, termin_zgloszen, " +
                "case when status = 1 then 'niezaczety' when status = 2 then 'w trakcie' else 'zakonczony' end, id_kon, status from konkurs;";
        AppJTableFromSelect table = new AppJTableFromSelect(select, columnNames, c);

        JButton BAdd = new JButton("Dodaj");
        BAdd.addActionListener(e -> {
            CompetitionAddFrame t2 = new CompetitionAddFrame();
            this.startSubframe(t2, e1 -> {
                //System.out.println("organizer action");
                table.refreshData();
                SwingUtilities.updateComponentTreeUI(me);
                me.invalidate();
                me.validate();
                me.repaint();
            });
        });
        TableColumnModel tableColumnModel = table.getComponent().getColumnModel();
        tableColumnModel.removeColumn(tableColumnModel.getColumn(4));
        tableColumnModel.removeColumn(tableColumnModel.getColumn(4));
        table.getComponent().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListSelectionModel selectionModel = table.getComponent().getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //System.out.println(table.getComponent().getSelectedRow());
                String status = table.getValues().get(table.getComponent().getSelectedRow()).get(5);
                disableButtons();
                if (status.equals("1")){
                    BAddNotStarted.setVisible(true);
                }
                if (status.equals("2")){
                    BEnterResults.setVisible(true);
                }
                if (status.equals("3")){
                    BWatchResults.setVisible(true);
                }
            }
        });
        jPanel.add(table.getComponent().getTableHeader());
        jPanel.add(table.getComponent());
        jPanel.add(BAdd);


        BAddNotStarted = new JButton("Zmien Konkurs");
        BAddNotStarted.addActionListener(e -> {
            if(table.getComponent().getSelectedRow() < 0){
                return;
            }
            String id = table.getValues().get(table.getComponent().getSelectedRow()).get(4);
            String place = table.getValues().get(table.getComponent().getSelectedRow()).get(1);
            ModifyCompetitionFrame t2 = new ModifyCompetitionFrame(id, place);
            this.startSubframe(t2, e1 -> {
                //System.out.println("organizer action");
                table.refreshData();
                SwingUtilities.updateComponentTreeUI(me);
                me.invalidate();
                me.validate();
                me.repaint();
            });
        });
        jPanel.add(BAddNotStarted);


        BEnterResults = new JButton("Dodaj wynik");
        BEnterResults.addActionListener(e -> {
            if(table.getComponent().getSelectedRow() < 0){
                return;
            }
            String id = table.getValues().get(table.getComponent().getSelectedRow()).get(4);
            //System.out.println("id " + id);
            addResults(id, table);
            //String place = table.getValues().get(table.getComponent().getSelectedRow()).get(1);

        });
        jPanel.add(BEnterResults);


        BWatchResults = new JButton("Ogladaj wyniki");
        BWatchResults.addActionListener(e -> {
            if(table.getComponent().getSelectedRow() < 0){
                return;
            }
            String id = table.getValues().get(table.getComponent().getSelectedRow()).get(4);
            //String place = table.getValues().get(table.getComponent().getSelectedRow()).get(1);
            String name = " w " + table.getValues().get(table.getComponent().getSelectedRow()).get(1) +
                    " dnia " + table.getValues().get(table.getComponent().getSelectedRow()).get(0);
            WatchCompetitionResultsFrame t2 = new WatchCompetitionResultsFrame(id, name);
            this.startSubframe(t2, e1 -> {
                //System.out.println("organizer action");
                table.refreshData();
                SwingUtilities.updateComponentTreeUI(me);
                me.invalidate();
                me.validate();
                me.repaint();
            });
        });
        jPanel.add(BWatchResults);


        add(jPanel);
        disableButtons();


    }

    void addResults(String id_kon, AppJTableFromSelect table){
        String id_serii, id_zgl, dane;
        //while (true){
            try {
                String select = "SELECT aktualna_seria_id(" + id_kon + ");";
                Statement statement = c.createStatement();
                ResultSet rs = statement.executeQuery(select);

                rs.next();
                id_serii = rs.getString(1);
                statement.close();
                //System.out.println("idkon "+ id_kon);
                //System.out.println("idserii" + id_serii);
                if (id_serii == null) {

                    endCompetition(id_kon);
                    table.refreshData();
                    SwingUtilities.updateComponentTreeUI(me);
                    me.invalidate();
                    me.validate();
                    me.repaint();
                    return;
                    //break;
                }
                String select2 = "SELECT nastepny_zawodnik(" + id_kon + ");";
                Statement statement2 = c.createStatement();
                ResultSet rs2 = statement2.executeQuery(select2);

                rs2.next();
                id_zgl = rs2.getString(1);
                statement2.close();
                String select3 = "SELECT imie, nazwisko FROM zgloszenie NATURAL JOIN zawodnik where id_zgl = " + id_zgl +";";
                Statement statement3 = c.createStatement();
                ResultSet rs3 = statement3.executeQuery(select3);

                rs3.next();
                dane = rs3.getString(1) + " " + rs3.getString(2);
                statement3.close();
            }catch (Exception e){
                ErrorFrame t2 = new ErrorFrame(e.toString());
                startSubframe(t2, e2 -> {
                    //System.out.println("error");
                });
                return;
                //throw new RuntimeException(e);
            }

            ResultAddFrame t2 = new ResultAddFrame(id_kon, id_serii, id_zgl, dane);
            this.startSubframe(t2, e1 -> {
                //System.out.println("organizer action");
                table.refreshData();
                SwingUtilities.updateComponentTreeUI(me);
                me.invalidate();
                me.validate();
                me.repaint();
            });
        //}

    }
    void endCompetition(String id_kon){

        //System.out.println("End competition" + id_kon);
        ArrayList<String> columnNames = new ArrayList<>();
        columnNames.add("id_zaw");
        columnNames.add("id_kon");
        columnNames.add("suma");
        String select = "select id_zaw, id_kon, sum(punkty) from wynik natural join zgloszenie natural join seria where numer <> 0 and id_kon = "
                + id_kon +" group by id_zaw, id_kon order by sum(punkty) desc;";
        //System.out.println(select);
        //AppJTableFromSelect table = new AppJTableFromSelect(select, columnNames, c);
        try {
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery(select);

            int position = 1;
            while (rs.next()){
                String id_zaw = rs.getString(1);
                String points = rs.getString(3);
                Statement statement2 = c.createStatement();
                String update = "UPDATE zgloszenie SET wynik_zaw = " + points + ", miejsce_zaw = " + position + " where id_zaw = "+ id_zaw + " AND id_kon = " + id_kon + ";";
                //System.out.println(update);
                statement2.executeUpdate(update);
                statement2.close();
                position++;
            }
            statement.close();
            Statement statement3 = c.createStatement();
            String update = "UPDATE konkurs SET status = 3 where id_kon = " + id_kon + ";";
            //System.out.println(update);
            statement3.executeUpdate(update);
            statement3.close();

        } catch (SQLException e) {
            ErrorFrame t2 = new ErrorFrame(e.toString());
            startSubframe(t2, e2 -> {
                //System.out.println("error");
            });
            return;
            //throw new RuntimeException(e);
        }


    }
}
