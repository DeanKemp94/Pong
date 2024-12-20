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

    GameState gameState = GameState.INITIALISING;

    Ball ball;
    private final static int BALL_MOVEMENT_SPEED = 2;
    Paddle paddle1, paddle2;

    private final static int POINTS_TO_WIN = 3;
    int player1Score = 0, player2Score = 0;
    Player gameWinner;

    public Pong_Panel() {
        setBackground(BACKGROUND_COLOUR);
        Timer timer = new Timer(TIMER_DELAY, this);
            timer.start();
            addKeyListener(this);
            setFocusable(true);
    }

    public void createObject() {
        ball = new Ball(getWidth(), getHeight());
        paddle1 = new Paddle(Player.One, getWidth(), getHeight());
        paddle2 = new Paddle(Player.Two, getWidth(), getHeight());
    }

    private void update(){
        switch (gameState) {
            case INITIALISING: {
                createObject();
                gameState = GameState.PLAYING;
                ball.setXVelocity(BALL_MOVEMENT_SPEED);
                ball.setYVelocity(BALL_MOVEMENT_SPEED);

                break;
            }
            case PLAYING: {
                moveObject(paddle1);
                moveObject(paddle2);
                moveObject(ball);
                checkWallBounce();
                checkPaddleBounce();
                checkWin();
                break;
            }
            case GAMEOVER: {
                break;
            }
        }
    }

    private void paintSprite(Graphics g, Sprite sprite) {
        g.setColor(sprite.getColor());
        g.fillRect(sprite.getXPosition(), sprite.getYPosition(), sprite.getWidth(), sprite.getHeight());
    }

    private void paintDottedLine(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
            Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
            g2d.setStroke(dashed);
            g2d.setPaint(Color.WHITE);
            g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
            g2d.dispose();
        }

        private void paintScores(Graphics g) {
            int xPadding = 100;
            int yPadding = 100;
            int fontSize = 50;
            Font scoreFont = new Font("Serif", Font.BOLD, fontSize);
            String leftScore = Integer.toString(player1Score);
            String rightScore = Integer.toString(player2Score);
            g.setFont(scoreFont);
            g.drawString(leftScore, xPadding, yPadding);
            g.drawString(rightScore, getWidth()-xPadding, yPadding);
        }

        private void paintWinner(Graphics g) {
            int xPadding = 130;
            int yPadding = 50;
            int fontSize = 50;
            String winner = "WIN!";
            Font scoreFont = new Font("Serif", Font.BOLD, fontSize);
            String leftScore = winner;
            String rightScore = winner;
            g.setFont(scoreFont);
            if (gameWinner == Player.One) {
                g.drawString(leftScore, xPadding, yPadding);
            } else if (gameWinner == Player.Two) {
                g.drawString(rightScore, getWidth()-xPadding, yPadding);
            }

        }

     @Override
     public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintDottedLine(g);
        if(gameState !=GameState.INITIALISING) {
            paintSprite(g,ball);
            paintSprite(g, paddle1);
            paintSprite(g, paddle2);
            paintScores(g);
            paintWinner(g);
        }

    }

    private void moveObject(Sprite obj) {
        obj.setXPosition(obj.getXPosition() + obj.getXVelocity(),getWidth());
        obj.setYPosition(obj.getYPosition() + obj.getYVelocity(),getHeight());
    }

    public void checkWallBounce(){
        if(ball.getXPosition() <= 0) {
            //ball hit left of side of screen
            ball.setXVelocity(-ball.getXVelocity());
            addScore(Player.Two);
            resetBall();

        } else if (ball.getXPosition() >= getWidth() - ball.getWidth()) {
            //hit the right side of the screen
            ball.setXVelocity(-ball.getXVelocity());
            addScore(Player.One);
            resetBall();
        }
        if(ball.getYPosition() <= 0 || ball.getYPosition() >= getHeight() - ball.getHeight()) {
            //hit either the top or bottom of the screen
            ball.setYVelocity(-ball.getYVelocity());
        }
    }

    private void resetBall(){
        ball.resetToInitialPosition();

    }

    private void checkPaddleBounce() {
        if (ball.getXVelocity() < 0 && ball.getRectangle().intersects(paddle1.getRectangle())) {
            ball.setXVelocity(BALL_MOVEMENT_SPEED);
        }
        if (ball.getXVelocity() > 0 && ball.getRectangle().intersects(paddle2.getRectangle())) {
            ball.setXVelocity(-BALL_MOVEMENT_SPEED);
        }
    }

    public void addScore(Player player) {
        if(player == Player.One) {
            player1Score++;
        } else if (player == Player.Two) {
            player2Score++;
        }
    }

    public void checkWin() {
        if (player1Score >= POINTS_TO_WIN) {
            gameWinner = Player.One;
            gameState = GameState.GAMEOVER;
        } else if (player2Score >= POINTS_TO_WIN ) {
            gameWinner = Player.Two;
            gameState = GameState.GAMEOVER;
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        update();
        repaint();

    }

    @Override
    public void keyTyped(KeyEvent event) {

    }

    @Override
    public void keyPressed(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.VK_UP) {
            paddle2.setYVelocity(-1);
        } else if(event.getKeyCode() == KeyEvent.VK_DOWN) {
            paddle2.setYVelocity(1);
        }

        if(event.getKeyCode() == KeyEvent.VK_W) {
            paddle1.setYVelocity(-1);
        } else if(event.getKeyCode() == KeyEvent.VK_S) {
            paddle1.setYVelocity(1);
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_DOWN) {
            paddle2.setYVelocity(0);
        }

        if(event.getKeyCode() == KeyEvent.VK_W || event.getKeyCode() == KeyEvent.VK_S) {
            paddle1.setYVelocity(0);
        }
    }



}
