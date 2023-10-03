package ru.savrey.lection01;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsWindow extends JFrame{
    private static final int WINDOW_HEIGHT = 350;
    private static final int WINDOW_WIDTH = 350;
    private static final int WIN_POS_X = 880;
    private static final int WIN_POS_Y = 405;
    private static final String FIELD_SIZE_PREFIX = "Установленный размер поля: ";
    private static final int MIN_FIELD_SIZE = 3;
    private static final int MAX_FIELD_SIZE = 10;
    private static final String WIN_LENGTH_PREFIX = "Установленная длина: ";
    private static final int MIN_WIN_LENGTH = 3;

    JButton btnStart = new JButton("Start new game");
    SettingsWindow(GameWindow gameWindow) {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocation(WIN_POS_X, WIN_POS_Y);

        setLayout(new GridLayout(10, 1));
        add(new JLabel("Выберите режим игры"));
        ButtonGroup bg = new ButtonGroup();
        JRadioButton pvc = new JRadioButton("Игрок против компьютера");
        JRadioButton pvp = new JRadioButton("Игрок против игрока");
        bg.add(pvc);
        bg.add(pvp);
        add(pvc);
        add(pvp);
        JLabel lbFieldSize = new JLabel(FIELD_SIZE_PREFIX + MIN_FIELD_SIZE);
        JLabel lbWinLength = new JLabel(WIN_LENGTH_PREFIX + MIN_WIN_LENGTH);
        JSlider slideFieldSize = new JSlider(MIN_FIELD_SIZE, MAX_FIELD_SIZE, MIN_FIELD_SIZE);
        JSlider slideWinLen = new JSlider(MIN_WIN_LENGTH, MAX_FIELD_SIZE, MIN_WIN_LENGTH);
        slideFieldSize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int currentValue = slideFieldSize.getValue();
                lbFieldSize.setText(FIELD_SIZE_PREFIX + currentValue);
                slideWinLen.setMaximum(currentValue);
            }
        });
        slideWinLen.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                lbWinLength.setText(WIN_LENGTH_PREFIX + slideWinLen.getValue());
            }
        });
        add(new JLabel("Выберите размеры поля"));
        add(lbFieldSize);
        add(slideFieldSize);
        add(new JLabel("Выберите длину для победы"));
        add(lbWinLength);
        add(slideWinLen);

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int gameMode;
                if (pvc.isSelected()) {
                    gameMode = Map.MODE_PvAI;
                } else if (pvp.isSelected()) {
                    gameMode = Map.MODE_PvP;
                } else {
                    throw new RuntimeException("Unknown game mode");
                }
                gameWindow.startNewGame(gameMode, slideFieldSize.getValue(),
                        slideFieldSize.getValue(), slideWinLen.getValue());
                setVisible(false);
            }
        });
        add(btnStart);
    }
}
