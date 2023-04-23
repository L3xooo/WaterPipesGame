package sk.stuba.fei.uim.oop.gui;

import sk.stuba.fei.uim.oop.controls.GameLogic;

import javax.swing.*;
import java.awt.*;

public class Game{
    public Game() {
        JFrame gameWindow = new JFrame("WaterPipes");
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setSize(790,900);
        gameWindow.setResizable(false);
        gameWindow.setFocusable(true);
        gameWindow.setLocationRelativeTo(null);
        gameWindow.getContentPane().setBackground(Color.CYAN);

        GameLogic gameLogic = new GameLogic(gameWindow);
        gameWindow.addKeyListener(gameLogic);
        gameWindow.requestFocusInWindow();

        JPanel sideMenu = new JPanel();
        sideMenu.setBackground(Color.LIGHT_GRAY);
        sideMenu.setLayout(new BoxLayout(sideMenu,BoxLayout.Y_AXIS));
        sideMenu.setLayout(new GridLayout(2,3));

        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(gameLogic);
        restartButton.setFocusable(false);
        JButton checkPipeButton = new JButton("Check");
        checkPipeButton.addActionListener(gameLogic);
        checkPipeButton.setFocusable(false);

        JSlider slider = new JSlider(JSlider.HORIZONTAL,8,16,8);
        slider.setFocusable(false);
        slider.setMinorTickSpacing(2);
        slider.setMajorTickSpacing(2);
        slider.setSnapToTicks(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(gameLogic);

        sideMenu.add(gameLogic.getBoardLabel());
        sideMenu.add(restartButton);
        sideMenu.add(slider);
        sideMenu.add(checkPipeButton);
        gameWindow.add(sideMenu,BorderLayout.SOUTH);
        gameWindow.setVisible(true);
    }
}