package algorithms;

import bot.BotState;
import move.MoveType;

import java.awt.*;
import java.util.ArrayList;

public class Pathfinder {

    public MoveType nextMove(BotState state, Point[][] parent, Point pos) {
        Point playerCurrentPosition = state.getField().getMyPosition();

        while (!parent[pos.x][pos.y].equals(playerCurrentPosition)) {
            pos = parent[pos.x][pos.y];
        }

        int prX = pos.x;
        int prY = pos.y;

        Point up = new Point(prX, prY + 1);
        Point down = new Point(prX, prY - 1);
        Point left = new Point(prX + 1, prY);
        Point right = new Point(prX - 1, prY);
        Point gateRight = new Point(state.getField().getWidth() - 1, prY);
        Point gateLeft = new Point(0, prY);

        MoveType nextMov;
        if (playerCurrentPosition.equals(up)) nextMov = MoveType.UP;
        else if (playerCurrentPosition.equals(down)) nextMov = MoveType.DOWN;
        else if (playerCurrentPosition.equals(left)) nextMov = MoveType.LEFT;
        else if (playerCurrentPosition.equals(right)) nextMov = MoveType.RIGHT;
        else if (playerCurrentPosition.equals(gateLeft)) nextMov = MoveType.LEFT;
        else if (playerCurrentPosition.equals(gateRight)) nextMov = MoveType.RIGHT;
        else nextMov = MoveType.PASS;

        return nextMov;
    }
}
