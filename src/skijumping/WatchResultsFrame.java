package skijumping;

import javax.swing.*;

public class WatchResultsFrame extends AppJFrame{

    @Override
    void prepareToShow() {
        setSize(500, 500);
        setTitle("Wybierz wyniki");

        JPanel jPanel = new JPanel();

        JButton CompetitorSel = new JButton("Wyniki zawodnika");
        CompetitorSel.addActionListener(e -> {
            SelectCompetitorFrame t2 = new SelectCompetitorFrame();
            this.startSubframe(t2, e1 -> {
                //System.out.println("replist");
            });
        });
        jPanel.add(CompetitorSel);

        JButton CompetitionSel = new JButton("Wyniki konkursu");
        CompetitionSel.addActionListener(e -> {
            SelectCompetitionFrame t2 = new SelectCompetitionFrame();
            this.startSubframe(t2, e1 -> {
                //System.out.println("addcom");
            });
        });
        jPanel.add(CompetitionSel);


        add(jPanel);
    }

}
