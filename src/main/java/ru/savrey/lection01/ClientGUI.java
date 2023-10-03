package ru.savrey.lection01;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientGUI extends JFrame {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private final JTextArea log = new JTextArea();

    private final JPanel panelTop = new JPanel(new GridLayout(2, 3));
    private final JTextField tfIPAddress = new JTextField("127.0.0.1");
    private final JTextField tfPort = new JTextField("8189");
    private final JTextField tfLogin = new JTextField("andrey");
    private final JPasswordField tfPassword = new JPasswordField("123456");
    private final JButton btnLogin = new JButton("Login");

    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Send");

    ServerWindow server;

    ClientGUI() {
        server = new ServerWindow();
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(WIDTH, HEIGHT);
        setTitle("Chat Client");

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                log.append("Установлено соединение с сервером");
                String history = server.connectUser(tfLogin.getText());
                log.append(history);
            }
        });

        panelTop.add(tfLogin);
        panelTop.add(tfIPAddress);
        panelTop.add(tfPort);
        panelTop.add(tfPassword);
        panelTop.add(btnLogin);
        add(panelTop, BorderLayout.NORTH);

        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = String.format("%s -> %s\n", tfLogin.getText(), tfMessage.getText());
                log.append(message);
                server.incomingMessage(message);
                tfMessage.setText("");
            }
        });


        panelBottom.add(tfMessage, BorderLayout.CENTER);
        panelBottom.add(btnSend, BorderLayout.EAST);
        add(panelBottom, BorderLayout.SOUTH);

        log.setEditable(false);
        JScrollPane scrolling = new JScrollPane(log);
        add(scrolling);

        String[] users = {"Andrey", "Sergey", "Karl", "Maximus", "John"};
        JList<String> userList = new JList<String>();
        userList.setListData(users);
        userList.setVisibleRowCount(1);
        JScrollPane scrollUsers = new JScrollPane(userList);
        add(scrollUsers, BorderLayout.WEST);

        userList.setSelectedIndex(0);

        setVisible(true);
    }
}
