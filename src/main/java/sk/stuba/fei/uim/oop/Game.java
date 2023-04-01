package sk.stuba.fei.uim.oop;

import javax.swing.*;
import java.awt.*;

public class Game {
    public Game() {
        JFrame frame = new JFrame("WaterPipes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        frame.setResizable(false);
        frame.setFocusable(true);

        JPanel sideMenu = new JPanel();
        sideMenu.setBackground(Color.lightGray);
        sideMenu.setLayout(new GridLayout());

        String[] boxSize = {"8x8","10x10","12x12"};
        JComboBox<String> comboBox = new JComboBox<>(boxSize);
        JButton restartButton = new JButton("Restart");
        JButton checkPipeButton = new JButton("Check");
        sideMenu.add(comboBox);
        sideMenu.add(restartButton);
        sideMenu.add(checkPipeButton);
        frame.add(sideMenu);
        frame.setVisible(true);
    }
}
