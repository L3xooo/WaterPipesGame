package sk.stuba.fei.uim.oop;

import sk.stuba.fei.uim.oop.board.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SideMenu extends JPanel implements ActionListener {
    private Board board;
    private JLabel label;
    private JButton restartButton;
    private JButton checkPipeButton;
    private JComboBox<String> comboBox;

    SideMenu() {
        setBackground(Color.blue);
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));


        this.label = new JLabel();

        Integer[] integerBoxSize = {8,10,12};
        String[] boxSize = {"8x8","10x10","12x12"};
        this.comboBox = new JComboBox<>(boxSize);
        comboBox.setPreferredSize(new Dimension(100,20));
        comboBox.addActionListener(this);

        this.restartButton = new JButton("Restart");
        restartButton.addActionListener(this);
        this.checkPipeButton = new JButton("Check");

        add(label);
        add(comboBox);
        add(restartButton);
        add(checkPipeButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == comboBox) {
            int selectedSize = comboBox.getSelectedIndex();


        }
        if (e.getSource() == restartButton) {
            System.out.println("Restarting game!");



        }
    }
}
