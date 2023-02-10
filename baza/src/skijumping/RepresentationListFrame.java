package skijumping;

import javax.swing.*;
import java.util.ArrayList;

public class RepresentationListFrame extends AppJFrame{
    AppJFrame me = this;
    @Override
    void prepareToShow() {
        setSize(500, 500);
        setTitle("Reprezentacje");

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

        ArrayList<String> columnNames = new ArrayList<>();
        columnNames.add("nazwa");
        String select = "SELECT nazwa FROM Reprezentacja order by nazwa";
        AppJTableFromSelect table = new AppJTableFromSelect(select, columnNames, c);


        JButton BAdd = new JButton("Dodaj");
        BAdd.addActionListener(e -> {
            RepresentationAddFrame t2 = new RepresentationAddFrame();
            this.startSubframe(t2, e1 -> {
                //System.out.println("organizer action");
                table.refreshData();
                SwingUtilities.updateComponentTreeUI(me);
                me.invalidate();
                me.validate();
                me.repaint();
            });
        });
        jPanel.add(table.getComponent().getTableHeader());
        jPanel.add(table.getComponent());
        jPanel.add(BAdd);
        add(jPanel);
    }
}
