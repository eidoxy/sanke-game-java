package org.eido;

import javax.swing.*;

public class Game extends JFrame {

    public Game() {
        this.add(new Board());
        this.setTitle("Snake Game Java");
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

}
