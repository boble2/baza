package skijumping;

import javax.swing.*;

import java.util.Objects;

import static skijumping.SkiJumping.addTextField;

public class LogFrame extends AppJFrame{
    String password = "iks";
    @Override
    void prepareToShow() {
        setSize(500, 500);
        setTitle("Logowanie");

        JPanel jPanel = new JPanel();
        JTextField jTextField = addTextField(null, jPanel, "Haslo: ");
        jPanel.add(jTextField);

        JButton BCheck = new JButton("Ok");
        BCheck.addActionListener(e -> { //jak na razie dopuszcza kazde haslo
            String pass = jTextField.getText();
            if (!Objects.equals(pass, password)){
                ErrorFrame t2 = new ErrorFrame("Niepoprawne haslo");
                startSubframe(t2, e1 -> {
                    return;
                });
            }
            else{
                OrganizerActionFrame t2 = new OrganizerActionFrame();
                this.startSubframe(t2, e1 -> {
                    System.out.println("organizer action");
                });
            }

        });

        jPanel.add(BCheck);
        add(jPanel);
    }
}
