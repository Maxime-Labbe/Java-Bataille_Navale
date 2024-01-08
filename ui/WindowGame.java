package ui;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.FlowLayout;
import go.Main;

public class WindowGame extends JFrame {

    private String[] listPlateau;
    private JPanel p;
    private GameEvent gameEvent = new GameEvent();

    public WindowGame(String[] listPlateau) {
        this.setSize(60 * Main.longueur + 3 * Main.longueur, 60 * Main.largeur + 5 * Main.largeur);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Bataille Navale");
        this.listPlateau = listPlateau;

        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 0, 0);

        p = new JPanel();
        p.setLayout(flowLayout);
        createGrid(listPlateau, p);
        this.setContentPane(p);
    }

    public String[] getListPlateau() {
        return listPlateau;
    }

    public GameEvent getGameEvent() {
        return gameEvent;
    }

    public void setPlateau(String[] listPlateau) {
        this.listPlateau = listPlateau;
        p.removeAll();
        createGrid(listPlateau, p);
        this.setContentPane(p);
        revalidate();
    }

    public void createGrid(String[] ListPlateau, JPanel panel) {
        Dimension customSize = new Dimension(60, 60);
        for (int x = 0; x < Main.largeur; x++) {
            for (int y = 0; y < Main.longueur; y++) {
                JButton button = new JButton(ListPlateau[x * Main.longueur + y]);
                button.setPreferredSize(customSize);
                button.addActionListener(gameEvent);
                panel.add(button);
            }
        }
    }
}