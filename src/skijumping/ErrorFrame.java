package skijumping;

import javax.swing.*;

public class ErrorFrame extends AppJFrame{
    String text;
    ErrorFrame(String text){
        this.text = text;
    }
    @Override
    void prepareToShow() {
        setSize(500, 500);
        setTitle("ERROR");
        JPanel jPanel = new JPanel();
        JLabel jLabel = new JLabel(text);
        jPanel.add(jLabel);
        add(jPanel);
    }
}
