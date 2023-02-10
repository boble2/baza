package skijumping;

import javax.swing.*;
import java.util.ArrayList;

public class WatchCompetitorResultsFrame extends AppJFrame{
    String id_comp;
    String nazwa;
    WatchCompetitorResultsFrame(String id_comp, String nazwa){
        super();
        this.id_comp = id_comp;
        this.nazwa = nazwa;
    }
    @Override
    void prepareToShow() {
        setSize(500, 500);
        setTitle("Wyniki zawodnika " + nazwa);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

        ArrayList<String> columnNames = new ArrayList<>();
        columnNames.add("miejsce");
        columnNames.add("data");
        columnNames.add("pozycja");
        columnNames.add("punkty");
        String select = "SELECT miejsce, data, miejsce_zaw, wynik_zaw FROM Zgloszenie NATURAL JOIN Konkurs WHERE id_zaw = " + id_comp + " AND miejsce_zaw is not null order by data";
        AppJTableFromSelect table = new AppJTableFromSelect(select, columnNames, c);

        jPanel.add(table.getComponent().getTableHeader());
        jPanel.add(table.getComponent());

        //TODO tabela poszczgolnych skokow
        //ArrayList<String> columnNames2  = new ArrayList<>();
        //columnNames2.add()



        add(jPanel);


    }
}
