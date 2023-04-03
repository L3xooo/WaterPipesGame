package sk.stuba.fei.uim.oop;

import sk.stuba.fei.uim.oop.controls.GameLogic;

import javax.swing.*;
import java.awt.*;

public class Game{
    public Game() {

        JFrame gameWindow = new JFrame("WaterPipes");
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setSize(800,658+36);
        gameWindow.setResizable(false);
        gameWindow.setFocusable(true);
        gameWindow.getContentPane().setBackground(Color.CYAN);


        GameLogic gameLogic = new GameLogic(gameWindow);
        gameWindow.addKeyListener(gameLogic);
        gameWindow.requestFocusInWindow();

        JPanel sideMenu = new JPanel();



        sideMenu.setBackground(Color.LIGHT_GRAY);
        sideMenu.setLayout(new BoxLayout(sideMenu,BoxLayout.Y_AXIS));

        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(gameLogic);
        restartButton.setFocusable(false);

        JButton checkPipeButton = new JButton("Check pipes");
        checkPipeButton.addActionListener(gameLogic);
        checkPipeButton.setFocusable(false);

        ButtonGroup boardSizeBtnGroup = new ButtonGroup();
        JRadioButton radioButton1 = new JRadioButton("8x8");
        JRadioButton radioButton2 = new JRadioButton("10x10");
        JRadioButton radioButton3 = new JRadioButton("12x12");
        boardSizeBtnGroup.add(radioButton1);
        boardSizeBtnGroup.add(radioButton2);
        boardSizeBtnGroup.add(radioButton3);
        radioButton1.addActionListener(gameLogic);
        radioButton2.addActionListener(gameLogic);
        radioButton3.addActionListener(gameLogic);
        radioButton1.setSelected(true);

        sideMenu.add(gameLogic.getCurrentLevelLabel());
        sideMenu.add(gameLogic.getBoardSizeLabel());
        sideMenu.add(radioButton1);
        sideMenu.add(radioButton2);
        sideMenu.add(radioButton3);
        sideMenu.add(restartButton);
        sideMenu.add(checkPipeButton);

        gameWindow.add(sideMenu,BorderLayout.EAST);

        System.out.println(gameWindow.getSize());
        System.out.println(sideMenu.getSize());
        gameWindow.setVisible(true);
    }
}
