package skijumping;

import javax.swing.*;

public class OrganizerActionFrame extends AppJFrame{
    @Override
    void prepareToShow() {
        setSize(500, 500);
        setTitle("Wybierz akcje");

        JPanel jPanel = new JPanel();

        JButton AddRep = new JButton("Reprezentacje");
        AddRep.addActionListener(e -> {
            RepresentationListFrame t2 = new RepresentationListFrame();
            this.startSubframe(t2, e1 -> {
                //System.out.println("replist");
            });
        });
        jPanel.add(AddRep);

        JButton AddCom = new JButton("Zawodnicy");
        AddCom.addActionListener(e -> {
            CompetitorListFrame t2 = new CompetitorListFrame();
            this.startSubframe(t2, e1 -> {
                //System.out.println("addcom");
            });
        });
        jPanel.add(AddCom);

        JButton AddCompetition = new JButton("Konkursy");
        AddCompetition.addActionListener(e -> {
            CompetitionListFrame t2 = new CompetitionListFrame();
            this.startSubframe(t2, e1 -> {
                //System.out.println("addcom2");
            });
        });
        jPanel.add(AddCompetition);



        add(jPanel);
    }
}
