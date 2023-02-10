package com.hawolt.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created: 10/02/2023 03:36
 * Author: Twitter @hawolt
 **/

public class UserCredentialsPanel extends JPanel {
    private final JTextField username;
    private final JPasswordField password;
    private final WalletPanel walletPanel;
    private final JButton login;

    public UserCredentialsPanel() {
        setBorder(BorderFactory.createTitledBorder("Login"));
        setPreferredSize(new Dimension(500, 130));
        setLayout(new GridLayout(0, 2, 5, 5));
        add(new JLabel("Username:"));
        add(username = new JTextField());
        add(new JLabel("Password:"));
        add(password = new JPasswordField());
        add(walletPanel = new WalletPanel());
        add(login = new JButton("Login"));
    }

    public WalletPanel getWalletPanel() {
        return walletPanel;
    }

    public JButton getLogin() {
        return login;
    }

    public String getUsername() {
        return username.getText();
    }

    public String getPassword() {
        return new String(password.getPassword());
    }
}
