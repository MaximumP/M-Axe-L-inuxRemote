package remote.runjetty.ui.listener;

import remote.runjetty.server.JettyServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by max on 18.09.15.
 *
 * ActionListener tp start and stop the jetty server
 */
public class ServerStartStopActionListener implements ActionListener{

    private final JettyServer jettyServer;

    public ServerStartStopActionListener(JettyServer jettyServer) {
        this.jettyServer = jettyServer;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JButton startStopBtn = (JButton) actionEvent.getSource();

        if (jettyServer.isRunning()) {
            startStopBtn.setText("Stopping...");
            startStopBtn.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            try {
                jettyServer.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
            startStopBtn.setText("Start");
            startStopBtn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } else if (jettyServer.isStopped()){
            startStopBtn.setText("Starting...");
            startStopBtn.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            try {
                jettyServer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
            startStopBtn.setText("Stop");
            startStopBtn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
}
