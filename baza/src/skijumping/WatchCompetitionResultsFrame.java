package skijumping;

import javax.swing.*;
import java.util.ArrayList;

public class WatchCompetitionResultsFrame extends AppJFrame{
    String id_comp;
    String name;
    WatchCompetitionResultsFrame(String id_comp, String name){
        super();
        this.id_comp = id_comp;
        this.name = name;
    }
    @Override
    void prepareToShow() {
        setSize(500, 500);
        setTitle("Wyniki konkursu" + name);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

        ArrayList<String> columnNames = new ArrayList<>();
        columnNames.add("pozycja");
        columnNames.add("imie");
        columnNames.add("nazwisko");
        columnNames.add("reprezentacja");
        columnNames.add("punkty");
        String select = "SELECT miejsce_zaw, imie, nazwisko, nazwa, wynik_zaw FROM Zgloszenie NATURAL JOIN zawodnik NATURAL JOIN reprezentacja WHERE id_kon = " + id_comp + " order by miejsce_zaw";
        AppJTableFromSelect table = new AppJTableFromSelect(select, columnNames, c);

        jPanel.add(table.getComponent().getTableHeader());
        jPanel.add(table.getComponent());

        //TODO tablica skokow
        //ArrayList<String> columnNames2  = new ArrayList<>();
        //columnNames2.add()



        add(jPanel);


    }
}
