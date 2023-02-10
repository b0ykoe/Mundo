package com.hawolt.ui;

import com.hawolt.Main;
import com.hawolt.core.Account;
import com.hawolt.core.Player;
import com.hawolt.core.Store;
import com.hawolt.exceptions.NoLeagueAccountAssociatedException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created: 10/02/2023 03:36
 * Author: Twitter @hawolt
 **/

public class LandingPanel extends JPanel {

    public LandingPanel() {
        setLayout(new BorderLayout());
        UserCredentialsPanel userCredentialsPanel = new UserCredentialsPanel();
        TransactionPanel transactionPanel = new TransactionPanel();
        JPanel nest = new JPanel(new GridLayout(0, 1, 0, 0));
        PrepaidPanel prepaid = new PrepaidPanel();
        RenamePanel renamePanel = new RenamePanel();
        nest.add(prepaid);
        nest.add(renamePanel);
        userCredentialsPanel.getLogin().addActionListener(listener -> {
            userCredentialsPanel.getLogin().setEnabled(false);
            Main.service.execute(() -> {
                String username = userCredentialsPanel.getUsername();
                String password = userCredentialsPanel.getPassword();
                Account account = new Account(username, password);
                try {
                    account.login();
                    if (account.isLoggedIn()) {
                        Store store = account.getStore(userCredentialsPanel.getWalletPanel());
                        WalletPanel walletPanel = userCredentialsPanel.getWalletPanel();
                        Player player = store.getPlayer();
                        walletPanel.getBELabel().setText("BE: " + player.getIP());
                        walletPanel.getRPLabel().setText("RP: " + player.getRP());
                        transactionPanel.populate(store, store.getRecommendedRefunds());
                        renamePanel.setStore(store);
                        prepaid.setStore(store);
                    }
                } catch (IOException | NoLeagueAccountAssociatedException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                userCredentialsPanel.getLogin().setEnabled(true);
            });
        });
        add(userCredentialsPanel, BorderLayout.NORTH);
        add(transactionPanel, BorderLayout.CENTER);
        add(nest, BorderLayout.SOUTH);
    }

}
