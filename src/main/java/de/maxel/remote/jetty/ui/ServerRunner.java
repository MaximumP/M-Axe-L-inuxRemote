package de.maxel.remote.jetty.ui;

import de.maxel.remote.jetty.server.JettyServer;
import de.maxel.remote.jetty.ui.listener.ServerStartStopActionListener;

import javax.swing.*;
import java.awt.*;

/**
 * Created by max on 18.09.15.
 *
 * Swing ui to start and stop the server
 */
public class ServerRunner extends JFrame{

    private JButton startStopBtn = new JButton();

    public ServerRunner(final JettyServer server) {
        super("Start/Stop Server");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        startStopBtn.setText("Start");
        startStopBtn.addActionListener(new ServerStartStopActionListener(server));
        add(startStopBtn, BorderLayout.CENTER);
        setSize(300, 300);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                if (server.isRunning()) {
                    try {
                        server.stop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "Stop Jetty Hook"));
        setVisible(true);
    }
}