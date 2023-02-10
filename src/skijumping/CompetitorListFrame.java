package skijumping;

import javax.swing.*;
import java.util.ArrayList;

public class CompetitorListFrame extends AppJFrame{
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
        String select = "select imie, nazwisko, nazwa from zawodnik natural join reprezentacja order by nazwa, imie, nazwisko;";
        AppJTableFromSelect table = new AppJTableFromSelect(select, columnNames, c);

        JButton BAdd = new JButton("Dodaj");
        BAdd.addActionListener(e -> {
            CompetitorAddFrame t2 = new CompetitorAddFrame();
            this.startSubframe(t2, e1 -> {
                System.out.println("organizer action");
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
