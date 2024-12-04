package Pong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Pong_Panel extends JPanel implements ActionListener, KeyListener {
    private final static Color BACKGROUND_COLOUR = Color.BLACK;
    private final static int TIMER_DELAY = 5;

    public Pong_Panel() {
        setBackground(BACKGROUND_COLOUR);
        Timer timer = new Timer(TIMER_DELAY, this);
            timer.start();
    }

    void update(){

    }

     @Override
     public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(20, 20, 100, 100);
         }

    @Override
    public void actionPerformed(ActionEvent event) {

    }

    @Override
    public void keyTyped(KeyEvent event) {

    }

    @Override
    public void keyPressed(KeyEvent event) {

    }

    @Override
    public void keyReleased(KeyEvent event) {

    }



}
