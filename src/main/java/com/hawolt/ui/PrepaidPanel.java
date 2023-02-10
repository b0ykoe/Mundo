package com.hawolt.ui;

import com.hawolt.Main;
import com.hawolt.core.IStore;

import javax.swing.*;
import java.awt.*;

/**
 * Created: 10/02/2023 07:09
 * Author: Twitter @hawolt
 **/

public class PrepaidPanel extends JPanel {
    private JTextField code;
    private JLabel label;
    private IStore store;

    public PrepaidPanel() {
        this.setLayout(new BorderLayout(5, 0));
        this.setBorder(BorderFactory.createTitledBorder("Prepaid"));
        this.add(code = new JTextField());
        this.setPreferredSize(new Dimension(500, 100));
        JButton rename = new JButton("Redeem");
        rename.addActionListener(listener -> {
            rename.setEnabled(false);
            Main.service.execute(() -> {
                boolean status = store.redeemPrepaidCode(code.getText());
                label.setText((status ? "Successfully" : "Failed to") + " redeemed code");
                rename.setEnabled(true);
            });
        });
        add(code, BorderLayout.CENTER);
        add(rename, BorderLayout.EAST);
        add(label = new JLabel("", SwingConstants.CENTER), BorderLayout.SOUTH);
        this.label.setPreferredSize(new Dimension(0, 30));
    }

    public void setStore(IStore store) {
        this.store = store;
    }
}