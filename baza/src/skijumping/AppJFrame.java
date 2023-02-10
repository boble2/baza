package skijumping;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;

public class AppJFrame extends JFrame {
    static Connection c;
    AppJFrame me =  this;
    AppJFrame(String string){
        super(string);
    }
    AppJFrame(){

    }
    Connection getConnection(){
        return c;
    }

    void setConnection(Connection c){
        this.c = c;
    }
    void prepareToShow(){

    }
    void afterShow(){

    }

    void startSubframe(AppJFrame subframe, ActionListener afterClose){
        AppJFrame me = this;
        subframe.addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e){
                        me.setEnabled(true);
                        me.setVisible(true);
                        afterClose.actionPerformed(null);
                    }
                }
        );
        subframe.setConnection(getConnection());
        subframe.prepareToShow();
        subframe.setLocationRelativeTo(this);
        this.setEnabled(false);
        this.setVisible(false);
        subframe.setVisible(true);
        subframe.afterShow();
    }
    void startMainframe(Connection c){
        this.c = c;
        prepareToShow();
        setVisible(true);
        afterShow();
    }

}