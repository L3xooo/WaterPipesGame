package sk.stuba.fei.uim.oop;

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
        sideMenu.setLayout(new GridLayout(2,2));

        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(gameLogic);
        restartButton.setFocusable(false);

        JButton checkPipeButton = new JButton("Check pipes");
        checkPipeButton.addActionListener(gameLogic);
        checkPipeButton.setFocusable(false);

        JSlider slider = new JSlider(JSlider.HORIZONTAL,8,12,8);
        slider.setFocusable(false);
        slider.setMinorTickSpacing(2);
        slider.setMajorTickSpacing(2);
        slider.setSnapToTicks(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(gameLogic);

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
        sideMenu.add(slider);
        //sideMenu.add(gameLogic.getBoardSizeLabel());
        /*sideMenu.add(radioButton1);
        sideMenu.add(radioButton2);
        sideMenu.add(radioButton3);
        */sideMenu.add(restartButton);
        sideMenu.add(checkPipeButton);

        gameWindow.add(sideMenu,BorderLayout.SOUTH);

        System.out.println(gameWindow.getSize());
        System.out.println(sideMenu.getSize());
        gameWindow.setVisible(true);
    }
}
