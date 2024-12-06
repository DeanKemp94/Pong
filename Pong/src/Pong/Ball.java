package Pong;

import java.awt.*;
import java.util.Random;

class Ball extends Sprite {
    private final static Color BALL_COLOR = Color.white;
    private final static int BALL_WIDTH = 25;
    private final static int BALL_HEIGHT = 25;

    public Ball (int panelWidth, int panelHeight) {
        setWidth(BALL_WIDTH);
        setHeight(BALL_HEIGHT);
        setColor(BALL_COLOR);
        setInitialPosition(panelWidth / 2 - (getWidth() / 2), panelHeight / 2 - (getHeight() / 2));
        resetToInitialPosition();
    }
}
