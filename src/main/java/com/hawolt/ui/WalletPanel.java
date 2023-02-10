package com.hawolt.ui;

import com.hawolt.core.IWalletUpdate;

import javax.swing.*;
import java.awt.*;

/**
 * Created: 10/02/2023 03:47
 * Author: Twitter @hawolt
 **/

public class WalletPanel extends JPanel implements IWalletUpdate {
    private final JLabel be, rp;

    public WalletPanel() {
        setLayout(new GridLayout(0, 2, 0, 5));
        add(be = new JLabel("BE: ?"));
        add(rp = new JLabel("RP: ?"));
    }

    public JLabel getBELabel() {
        return be;
    }

    public JLabel getRPLabel() {
        return rp;
    }

    @Override
    public void onUpdate(long ip, long rp) {
        this.be.setText("BE: " + ip);
        this.rp.setText("RP: " + rp);
    }
}
