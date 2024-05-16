package socket_2;

import java.io.*;
import java.net.*;
import java.time.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class timeClient extends JFrame {
    private JLabel timeLabel;
    private Timer timer;

    public timeClient() {
        setTitle("Clock Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);

        timeLabel = new JLabel("Waiting for server...");
        timeLabel.setHorizontalAlignment(JLabel.CENTER);
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 24));

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(timeLabel, BorderLayout.CENTER);

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getTimeFromServer();
            }
        });
        timer.start();
    }

    private void getTimeFromServer() {
        try {
            Socket socket = new Socket("localhost", 8000);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println("time");
            String timeStr = in.readLine();
            LocalTime currentTime = LocalTime.parse(timeStr);
            timeLabel.setText(currentTime.toString());

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new timeClient().setVisible(true);
            }
        });
    }
}