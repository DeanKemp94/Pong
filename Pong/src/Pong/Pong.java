package Pong;

import javax.swing.JFrame;


public class Pong extends JFrame {
    final static String window_Title = "Paddle Pals";
    final static int window_Width = 800;
    final static int window_Height = 600;

    public Pong() {
        setTitle(window_Title);
        setSize(window_Width, window_Height);
        setResizable(false);
        setVisible(true);
        add(new Pong_Panel());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {

                new Pong();

            }

        });

    }
}
