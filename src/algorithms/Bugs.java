package algorithms;

import bot.BotState;
import move.MoveType;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class Bugs {
    private int width, height;
    private Boolean[][] visited = new Boolean[19][15];
    private Double[][] distance = new Double[19][15];

    public Bugs(int width, int height) {
        this.height = height;
        this.width = width;
    }

    public int nearbyBugsCount(BotState state) {
        int counter = 0;
        LinkedList<Point> queue = new LinkedList<>();

        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                distance[i][j] = Double.POSITIVE_INFINITY;
                visited[i][j] = false;
            }
        }
        Point pos = state.getField().getMyPosition();
        distance[pos.x][pos.y] = 0.0;
        visited[pos.x][pos.y] = true;

        queue.push(pos);
        while (!queue.isEmpty()) {
            Point point = queue.pop();
            for (Point ngh : bugGetNeighbours(point, state)) {
                if (!visited[ngh.x][ngh.y]) {
                    queue.add(ngh);
                    visited[ngh.x][ngh.y] = true;
                    distance[ngh.x][ngh.y] = distance[point.x][point.y] + 1;
                }
            }
        }

        for (Point bug : state.getField().getEnemyPositions()) {
            if (distance[bug.x][bug.y] < 3.0)
                counter += 1;
        }
        return counter;
    }

    private ArrayList<Point> bugGetNeighbours(Point position, BotState state) {
        ArrayList<Point> adjacentPoints = new ArrayList<>();

        int posX = position.x;
        int posY = position.y;

        Point upper = new Point(posX, posY - 1);
        Point bottom = new Point(posX, posY + 1);
        Point left = new Point(posX - 1, posY);
        Point right = new Point(posX + 1, posY);

        if (state.getField().isPointValid(upper)) adjacentPoints.add(upper);
        if (state.getField().isPointValid(bottom)) adjacentPoints.add(bottom);
        if (state.getField().isPointValid(left)) adjacentPoints.add(left);
        if (state.getField().isPointValid(right)) adjacentPoints.add(right);

        return adjacentPoints;
    }

    public boolean bugAlert(BotState state) {
        return nearbyBugsCount(state) > 0;
    }

    public MoveType bugByYou(MoveType move, BotState state) {
        Point pos = state.getField().getMyPosition();

        int posX = pos.x;
        int posY = pos.y;

        Point upper = new Point(posX, posY - 1);
        Point bottom = new Point(posX, posY + 1);
        Point left = new Point(posX - 1, posY);
        Point right = new Point(posX + 1, posY);

        if (move.equals(MoveType.PASS) && state.getField().getEnemyPositions().contains(upper))
            return MoveType.DOWN;
        if (move.equals(MoveType.PASS) && state.getField().getEnemyPositions().contains(bottom))
            return MoveType.UP;
        if (move.equals(MoveType.PASS) && state.getField().getEnemyPositions().contains(left))
            return MoveType.RIGHT;
        if (move.equals(MoveType.PASS) && state.getField().getEnemyPositions().contains(right))
            return MoveType.LEFT;

        return move;
    }
}
