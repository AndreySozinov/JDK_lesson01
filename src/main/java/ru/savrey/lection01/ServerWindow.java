package ru.savrey.lection01;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class ServerWindow extends JFrame{
    private static final int POS_X = 500;
    private static final int POS_Y = 550;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private final JButton btnStart = new JButton("Start");
    private final JButton btnStop = new JButton("Stop");
    private final JTextArea log = new JTextArea();
    private boolean isServerWorking;

    ServerWindow() {
        isServerWorking = false;

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isServerWorking) {
                    log.append("Server already working\n");
                } else {
                    isServerWorking = true;
                    log.append("Server started\n");
                    try(FileReader reader = new FileReader("logs.txt"))
                    {
                        char[] buf = new char[256];
                        int c;
                        while ((c = reader.read(buf)) > 0) {
                            if (c < 256) {
                                buf = Arrays.copyOf(buf, c);
                            }
                            log.append(String.valueOf(buf));
                        }
                    }
                    catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });

        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isServerWorking) {
                    log.append("Server not working\n");
                } else {
                    isServerWorking = false;
                    log.append("Server stopped\n");
                    try(FileWriter writer = new FileWriter("logs.txt", true)) {
                        writer.write(String.valueOf(log.getText()));
                        writer.flush();
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                    log.setText("");
                }
            }
        });

        log.setEditable(false);
        JScrollPane scrolling = new JScrollPane(log);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat server");
        setAlwaysOnTop(true);
        setLayout(new GridLayout(1, 2));

        add(btnStart);
        add(btnStop);
        add(scrolling);

        setVisible(true);
    }

    void incomingMessage(String message) {
        log.append(message);
    }

    String connectUser(String username) {
        log.append(username + " присоединился\n");
        return log.getText();
    }
}
