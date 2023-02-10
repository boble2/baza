package skijumping;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ModifyCompetitionFrame extends AppJFrame{
    String idcomp;
    String place;

    ModifyCompetitionFrame(String id, String place){
        idcomp = id;
        this.place = place;
    }

    void startCompetition(){
        try {
            Statement statement = c.createStatement();
            String select = "SELECT rozgrywana_jest_kwalifikacyjna(" + idcomp + ");";
            ResultSet rs = statement.executeQuery(select);

            rs.next();
            String result = rs.getString(1);
            //System.out.println(result);
            if (!result.equals("f")){
                select = "INSERT INTO SERIA (id_kon, numer) VALUES ( " + idcomp + " ,0);";
                statement.executeUpdate(select);

            }
            select = "INSERT INTO SERIA (id_kon, numer) VALUES ( " + idcomp + " ,1);";
            statement.executeUpdate(select);

            select = "INSERT INTO SERIA (id_kon, numer) VALUES ( " + idcomp + " ,2);";
            statement.executeUpdate(select);
            select = "UPDATE KONKURS SET status = 2 WHERE id_kon = " + idcomp + ";";
            statement.executeUpdate(select);
            statement.close();
            //System.out.println("OK");
        }
        catch (Exception e){
            ErrorFrame t2 = new ErrorFrame(e.toString());
            startSubframe(t2, e2 -> {
                //System.out.println("error");
            });
            return;
            //throw new RuntimeException(e);
        }
    }
    @Override
    void prepareToShow() {
        setSize(500, 500);
        setTitle("Modyfikacja konkursu " + place);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        ArrayList<String> columnNamesRep = new ArrayList<>();
        columnNamesRep.add("nazwa");
        columnNamesRep.add("kwota bazowa");
        //columnNames.add("reprezentacja");
        String selectRep = "select nazwa, kwota_bazowa from reprezentacja_konkurs natural join reprezentacja where id_kon = " + idcomp + ";";
        AppJTableFromSelect tableRep = new AppJTableFromSelect(selectRep, columnNamesRep, c);


        //JButton BAddRep = new JButton("Dodaj");
        JButton BAddRep = new JButton("Dodaj reprezentacje");
        BAddRep.addActionListener(e -> {
            AddReprezentationToCompetitionFrame t2 = new AddReprezentationToCompetitionFrame(idcomp, place);
            this.startSubframe(t2, e1 -> {
                //System.out.println("organizer action");
                tableRep.refreshData();
                SwingUtilities.updateComponentTreeUI(me);
                me.invalidate();
                me.validate();
                me.repaint();
            });
        });
        jPanel.add(tableRep.getComponent().getTableHeader());
        jPanel.add(tableRep.getComponent());
        jPanel.add(BAddRep);




        ArrayList<String> columnNamesCom = new ArrayList<>();
        columnNamesCom.add("imie");
        columnNamesCom.add("nazwisko");
        columnNamesCom.add("reprezentacja");
        String selectCom = "select imie, nazwisko, nazwa from zgloszenie natural join zawodnik natural join reprezentacja where id_kon = " + idcomp + ";";
        AppJTableFromSelect tableCom = new AppJTableFromSelect(selectCom, columnNamesCom, c);


        JButton BAddCom = new JButton("Dodaj zawodnika");
        BAddCom.addActionListener(e -> {
            AddCompetitorToCompetitionFrame t2 = new AddCompetitorToCompetitionFrame(idcomp, place);
            this.startSubframe(t2, e1 -> {
                //System.out.println("logFrame");
                tableCom.refreshData();
                SwingUtilities.updateComponentTreeUI(me);
                me.invalidate();
                me.validate();
                me.repaint();
            });
        });

        jPanel.add(tableCom.getComponent().getTableHeader());
        jPanel.add(tableCom.getComponent());
        jPanel.add(BAddCom);

        JButton BStart = new JButton("Rozpocznij konkurs");
        BStart.addActionListener(e -> {
            startCompetition();
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

        });
        jPanel.add(BStart);

        add(jPanel);
    }
}
