package skijumping;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.util.ArrayList;

public class SelectCompetitionFrame extends AppJFrame{

    @Override
    void prepareToShow() {
        setSize(500, 500);
        setTitle("Konkursy");

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        ArrayList<String> columnNames = new ArrayList<>();
        columnNames.add("data");
        columnNames.add("miejsce");
        //columnNames.add("termin zgloszen");
        //columnNames.add("status");
        columnNames.add("id");
        //columnNames.add("status number");
        String select = "select data, miejsce, " +
                "id_kon from konkurs where status = 3;";
        AppJTableFromSelect table = new AppJTableFromSelect(select, columnNames, c);


        TableColumnModel tableColumnModel = table.getComponent().getColumnModel();
        tableColumnModel.removeColumn(tableColumnModel.getColumn(2));
        //tableColumnModel.removeColumn(tableColumnModel.getColumn(4));
        table.getComponent().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jPanel.add(table.getComponent().getTableHeader());
        jPanel.add(table.getComponent());


        JButton BChoose = new JButton("Wybierz");
        BChoose.addActionListener(e -> {
            if (table.getComponent().getSelectedRow() < 0) {
                return;
            }
            String id = table.getValues().get(table.getComponent().getSelectedRow()).get(2);
            String name = " w " + table.getValues().get(table.getComponent().getSelectedRow()).get(1) +
                    " dnia " + table.getValues().get(table.getComponent().getSelectedRow()).get(0);
            //String place = table.getValues().get(table.getComponent().getSelectedRow()).get(1);
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
        jPanel.add(BChoose);
        add(jPanel);
    }
}
