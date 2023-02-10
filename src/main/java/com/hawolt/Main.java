package com.hawolt;

import com.hawolt.ui.LandingPanel;
import okhttp3.OkHttpClient;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created: 10/02/2023 03:35
 * Author: Twitter @hawolt
 **/

public class Main {
    public static final ExecutorService service = Executors.newSingleThreadExecutor();
    public static final OkHttpClient httpClient = new OkHttpClient();

    public static void main(String[] args) {
        setUIFont(new FontUIResource(new Font("Arial", Font.PLAIN, 20)));
        JFrame frame = new JFrame("Mundo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = frame.getContentPane();
        container.setPreferredSize(new Dimension(500, 400));
        container.setLayout(new BorderLayout());
        container.add(new LandingPanel(), BorderLayout.CENTER);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void setUIFont(javax.swing.plaf.FontUIResource f) {
        java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, f);
            }
        }
    }
}
