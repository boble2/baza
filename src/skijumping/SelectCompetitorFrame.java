package skijumping;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.util.ArrayList;

public class SelectCompetitorFrame extends AppJFrame{
    @Override
    void prepareToShow() {
        setSize(500, 500);
        setTitle("Zawodnicy");

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        ArrayList<String> columnNames = new ArrayList<>();
        columnNames.add("imie");
        columnNames.add("nazwisko");
        columnNames.add("reprezentacja");
        columnNames.add("id");
        String select = "select imie, nazwisko, " +
                "nazwa, id_zaw from zawodnik natural join reprezentacja;";
        AppJTableFromSelect table = new AppJTableFromSelect(select, columnNames, c);


        TableColumnModel tableColumnModel = table.getComponent().getColumnModel();
        tableColumnModel.removeColumn(tableColumnModel.getColumn(3));
        //tableColumnModel.removeColumn(tableColumnModel.getColumn(4));
        table.getComponent().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jPanel.add(table.getComponent().getTableHeader());
        jPanel.add(table.getComponent());


        JButton BChoose = new JButton("Wybierz");
        BChoose.addActionListener(e -> {
            if (table.getComponent().getSelectedRow() < 0) {
                return;
            }
            String id = table.getValues().get(table.getComponent().getSelectedRow()).get(3);
            String nazwa = table.getValues().get(table.getComponent().getSelectedRow()).get(0) + " " +
                    table.getValues().get(table.getComponent().getSelectedRow()).get(1);
            //String place = table.getValues().get(table.getComponent().getSelectedRow()).get(1);
            WatchCompetitorResultsFrame t2 = new WatchCompetitorResultsFrame(id, nazwa);
            this.startSubframe(t2, e1 -> {
                //System.out.println("organizer action");
                table.refreshData();
                SwingUtilities.updateComponentTreeUI(me);
                me.invalidate();
                me.validate();
                me.repaint();
            });
        });
        jPanel.add(BChoose);
        add(jPanel);
    }
}
