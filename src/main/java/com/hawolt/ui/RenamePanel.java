package com.hawolt.ui;

import com.hawolt.Main;
import com.hawolt.core.Currency;
import com.hawolt.core.IStore;

import javax.swing.*;
import java.awt.*;

/**
 * Created: 10/02/2023 06:41
 * Author: Twitter @hawolt
 **/

public class RenamePanel extends JPanel {
    private final JComboBox<Currency> currencyJComboBox = new JComboBox<>(Currency.values());
    private JTextField name;
    private JLabel label;
    private IStore store;

    public RenamePanel() {
        this.setLayout(new BorderLayout(5, 0));
        this.setBorder(BorderFactory.createTitledBorder("Rename"));
        this.add(name = new JTextField());
        this.setPreferredSize(new Dimension(500, 100));
        JButton rename = new JButton("Submit");
        rename.addActionListener(listener -> {
            rename.setEnabled(false);
            Main.service.execute(() -> {
                boolean status = store.purchaseSummonerNameChange(currencyJComboBox.getItemAt(currencyJComboBox.getSelectedIndex()), name.getText());
                label.setText((status ? "Successfully changed" : "Failed to change") + " name");
                rename.setEnabled(true);
            });
        });
        add(currencyJComboBox, BorderLayout.WEST);
        add(name, BorderLayout.CENTER);
        add(rename, BorderLayout.EAST);
        add(label = new JLabel("", SwingConstants.CENTER), BorderLayout.SOUTH);
        this.label.setPreferredSize(new Dimension(0, 30));
    }

    public void setStore(IStore store) {
        this.store = store;
    }
}
