package skijumping;

import javax.swing.*;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class StartFrame extends AppJFrame{

    @Override
    void prepareToShow() {
        setSize(500, 500);
        setTitle("Puchar Swiata");

        setLayout(new FlowLayout());

        JLayeredPane layers = new JLayeredPane();
        layers.setLayout(new FlowLayout());

        JPanel layerOne = new JPanel();
        layerOne.add(new JLabel("Zaloguj jako:"));
        layers.add(layerOne, Integer.valueOf(2));

        JPanel layerTwo = new JPanel();
        /*JButton button1 = new JButton("Organizator");
        layerTwo.add(button1);
        JButton button2 = new JButton("Kibic");
        layerTwo.add(button2);*/
        layers.add(layerTwo, Integer.valueOf(1));

        JButton Borganizator = new JButton("Organizator");
        Borganizator.addActionListener(e -> {
            LogFrame t2 = new LogFrame();
            this.startSubframe(t2, e1 -> {
                //System.out.println("logFrame");
            });
        });
        layerTwo.add(Borganizator);

        JButton Bkibic = new JButton("Kibic");
        Bkibic.addActionListener(e -> {
            WatchResultsFrame t3 = new WatchResultsFrame();
            this.startSubframe(t3, e1 -> {
                //System.out.println("watchResultsFrame");
            });
        });
        layerTwo.add(Bkibic);

        add(layers);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }
}
