import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 400;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;
    private Image dot;
    private Image apple;
    private int apple_x;
    private int apple_y;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;


    public GameField() {
        setBackground(Color.BLACK);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void initGame() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 48;
        }
        timer = new Timer(500, this);
        timer.start();
        createApple();
    }

    public void createApple() {
        apple_x = new Random().nextInt(20) * DOT_SIZE;
        apple_y = new Random().nextInt(20) * DOT_SIZE;
        for (int i = 0; i < x.length; i++) {
            if (apple_x == x[i] && apple_y == y[i]) {
                apple_x = new Random().nextInt(20) * DOT_SIZE;
                apple_y = new Random().nextInt(20) * DOT_SIZE;
            }
        }
    }

    public void loadImages() {
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("dot2.png");
        dot = iid.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Font f = new Font("Arial", Font.BOLD, 12);
        g.setColor(Color.white);
        g.setFont(f);
        g.drawString("Apples: " + dots, 310, 15);

        if (inGame) {
            g.drawImage(apple, apple_x, apple_y, this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }
        } else {

            String gameOver = "Game Over";
            g.setColor(Color.white);
            g.setFont(f);

            g.drawString(gameOver, 125, 150);
            String record = "Your Record: " + dots;
            g.drawString(record, 120, 170);
        }
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (left) {
            x[0] -= DOT_SIZE;
        }
        if (right) {
            x[0] += DOT_SIZE;
        }
        if (up) {
            y[0] -= DOT_SIZE;
        }
        if (down) {
            y[0] += DOT_SIZE;
        }
    }

    public void checkApple() {
        if (x[0] == apple_x && y[0] == apple_y) {
            dots++;
            if (dots > 10 && dots <= 25) {
                timer.setDelay(250);
            } else if (dots > 25 && dots < 50) {
                timer.setDelay(175);
            } else if (dots > 50) {
                timer.setDelay(100);
            }
            createApple();
        }

    }

    public void checkCollisions() {
        for (int i = dots; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
            }
            if (x[0] > SIZE) {
                inGame = false;
            }
            if (x[0] < 0) {
                inGame = false;
            }
            if (y[0] > SIZE) {
                inGame = false;
            }
            if (y[0] < 0) {
                inGame = false;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollisions();
            move();

        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && !down) {
                left = false;
                up = true;
                right = false;
            }
            if (key == KeyEvent.VK_DOWN && !up) {
                left = false;
                down = true;
                right = false;
            }
        }
    }
}