package com.hawolt.ui;

import com.hawolt.Main;
import com.hawolt.core.IStore;
import com.hawolt.core.Transaction;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

/**
 * Created: 10/02/2023 03:37
 * Author: Twitter @hawolt
 **/

public class TransactionPanel extends JPanel {

    private final JComboBox<Transaction> transactionJComboBox = new JComboBox<>();
    private IStore store;

    public TransactionPanel() {
        this.setLayout(new BorderLayout(5, 0));
        this.setBorder(BorderFactory.createTitledBorder("Transactions"));
        this.add(transactionJComboBox);
        JButton refund = new JButton("Refund");
        refund.addActionListener(listener -> {
            refund.setEnabled(false);
            Main.service.execute(() -> {
                Transaction transaction = transactionJComboBox.getItemAt(transactionJComboBox.getSelectedIndex());
                if (transaction != null) {
                    try {
                        if (store.refund(transaction)) {
                            transactionJComboBox.removeItem(transaction);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                refund.setEnabled(true);
            });
        });
        add(transactionJComboBox, BorderLayout.CENTER);
        add(refund, BorderLayout.EAST);
    }

    public void populate(IStore store, List<Transaction> list) {
        this.store = store;
        this.transactionJComboBox.removeAllItems();
        for (Transaction transaction : list) {
            this.transactionJComboBox.addItem(transaction);
        }
        this.revalidate();
    }
}
