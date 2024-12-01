package org.eido;

import java.util.Random;

public class Food {

    private final int PostionX;
    private final int PostionY;

    public Food() {
        PostionX = generatePosition(Board.WIDTH);
        PostionY = generatePosition(Board.HEIGHT);
    }

    private int generatePosition(int size) {
        Random random = new Random();
        return random.nextInt(size / Board.TICK_SIZE) * Board.TICK_SIZE;
    }

    public int getPostionX() {
        return PostionX;
    }

    public int getPostionY() {
        return PostionY;
    }
}
