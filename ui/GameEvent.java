package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import go.Main;

public class GameEvent implements ActionListener {

    private int index;

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source instanceof JButton) {
            int index = (((JButton) source).getY() / 60) * Main.largeur + ((((JButton) source).getX() / 60));
            setIndex(index);
            Main.buttonPressed = true;
        }
    }

}
