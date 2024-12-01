package org.eido;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {

    static final int WIDTH = 800;
    static final int HEIGHT = 800;
    static final int TICK_SIZE = 50;
    static final int BOARD_SIZE = (WIDTH * HEIGHT) / (TICK_SIZE * TICK_SIZE);

    final Font font = new Font("Arial", Font.BOLD, 30);

    int[] snakePositionX = new int[BOARD_SIZE];
    int[] snakePositionY = new int[BOARD_SIZE];
    int snakeLength;

    Food food;
    int foodEaten;

    private Image berry;
    private Image body;
    private Image head;


    char direction = 'R';
    boolean isMoving = false;
    final Timer timer = new Timer(150, this);

    private void loadImages() {
        ImageIcon iid = new ImageIcon("src/main/resources/apple.png");
        berry = iid.getImage().getScaledInstance(TICK_SIZE, TICK_SIZE, Image.SCALE_SMOOTH);

        ImageIcon iia = new ImageIcon("src/main/resources/head.png");
        head = iia.getImage().getScaledInstance(TICK_SIZE, TICK_SIZE, Image.SCALE_SMOOTH);

        ImageIcon iib = new ImageIcon("src/main/resources/dot.png");
        body = iib.getImage().getScaledInstance(TICK_SIZE, TICK_SIZE, Image.SCALE_SMOOTH);
    }

    public Board() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (isMoving) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_LEFT:
                            if (direction != 'R') {
                                direction = 'L';
                            }
                            break;
                        case KeyEvent.VK_RIGHT:
                            if (direction != 'L') {
                                direction = 'R';
                            }
                            break;
                        case KeyEvent.VK_UP:
                            if (direction != 'D') {
                                direction = 'U';
                            }
                            break;
                        case KeyEvent.VK_DOWN:
                            if (direction != 'U') {
                                direction = 'D';
                            }
                            break;
                    }
                } else {
                    start();
                }
            }
        });

        loadImages();
        start();
    }

    protected void start() {
        snakePositionX = new int[BOARD_SIZE];
        snakePositionY = new int[BOARD_SIZE];
        snakeLength = 5;
        foodEaten = 0;
        direction = 'R';
        isMoving = true;
        spawnFood();
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (isMoving) {
            if (berry != null ) {
                g.drawImage(berry, food.getPostionX(), food.getPostionY(), this);
            }
            //g.setColor(Color.RED);
            //g.fillOval(food.getPostionX(), food.getPostionY(), TICK_SIZE, TICK_SIZE);

            g.setColor(Color.DARK_GRAY);
            for (int i = 0; i < snakeLength; i++) {
                if (i == 0) {
                    g.drawImage(head, snakePositionX[i], snakePositionY[i], this);
                } else {
                    g.drawImage(body, snakePositionX[i], snakePositionY[i], this);
                }
                //g.fillRect(snakePositionX[i], snakePositionY[i], TICK_SIZE, TICK_SIZE);
            }
        } else {
            String scoreText = String.format("Game Over! Score: %d... Press any key to play again!", foodEaten);
            g.setColor(Color.WHITE);
            g.setFont(font);
            g.drawString(scoreText, (WIDTH - getFontMetrics(g.getFont()).stringWidth(scoreText)) / 2, HEIGHT / 2);
        }
    }

    protected void move() {
        for (int i = snakeLength; i > 0; i--) {
            snakePositionX[i] = snakePositionX[i - 1];
            snakePositionY[i] = snakePositionY[i - 1];
        }

        switch (direction) {
            case 'U' -> snakePositionY[0] -= TICK_SIZE;
            case 'D' -> snakePositionY[0] += TICK_SIZE;
            case 'L' -> snakePositionX[0] -= TICK_SIZE;
            case 'R' -> snakePositionX[0] += TICK_SIZE;
        }
    }

    protected void spawnFood() {
        food = new Food();
    }

    protected void eatFood() {
        if ((snakePositionX[0] == food.getPostionX()) && (snakePositionY[0] == food.getPostionY())) {
            snakeLength++;
            foodEaten++;
            spawnFood();
        }
    }

    protected void collisionTest() {
        for (int i = snakeLength; i > 0; i--) {
            if ((snakePositionX[0] == snakePositionX[i]) && (snakePositionY[0] == snakePositionY[i])) {
                isMoving = false;
                break;
            }
        }

        if (snakePositionX[0] < 0 || snakePositionX[0] > WIDTH - TICK_SIZE || snakePositionY[0] < 0 || snakePositionY[0] > HEIGHT - TICK_SIZE) {
            isMoving = false;
        }

        if (!isMoving) {
            timer.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isMoving) {
            move();
            collisionTest();
            eatFood();
        }

        repaint();
    }

}
