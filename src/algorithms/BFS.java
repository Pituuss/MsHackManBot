package algorithms;


import bot.BotState;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class BFS {
    private Boolean[][] visited;
    private Point[][] parent;
    private Double[][] distance;
    private LinkedList<Point> queue;
    private int width;
    private int height;

    public BFS(int width, int height) {
        this.width = width;
        this.height = height;
        visited = new Boolean[width][height];
        parent = new Point[width][height];
        distance = new Double[width][height];
        queue = new LinkedList<>();
    }

    public void clear() {
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                distance[i][j] = Double.POSITIVE_INFINITY;
                parent[i][j] = null;
                visited[i][j] = false;
            }
        }
    }

    private ArrayList<Point> getNeighbours(Point position, BotState state) {
        ArrayList<Point> adjacentPoints = new ArrayList<>();

        int posX = position.x;
        int posY = position.y;

        Point upper = new Point(posX, posY - 1);
        Point bottom = new Point(posX, posY + 1);
        Point left = new Point(posX - 1, posY);
        Point right = new Point(posX + 1, posY);

        if (state.getField().getField()[posX][posY].contains("Gl"))
            adjacentPoints.add(new Point(width - 1, posY));
        if (state.getField().getField()[posX][posY].contains("Gr"))
            adjacentPoints.add(new Point(0, posY));
        if (isPointValid(upper, state)) adjacentPoints.add(upper);
        if (isPointValid(bottom, state)) adjacentPoints.add(bottom);
        if (isPointValid(left, state)) adjacentPoints.add(left);
        if (isPointValid(right, state)) adjacentPoints.add(right);

        return adjacentPoints;
    }

    private boolean isPointValid(Point point, BotState state) {
        int x = point.x;
        int y = point.y;

        return x >= 0 && x < this.width && y >= 0 && y < this.height &&
                !state.getField().getField()[x][y].contains("x");
    }


    public void startBFS(Point pos, BotState state) {

        distance[pos.x][pos.y] = 0.0;
        visited[pos.x][pos.y] = true;

        for (Point bugPos : state.getField().getEnemyPositions()) {
            visited[bugPos.x][bugPos.y] = true;
            if (bugPos.x-1 >= 0)
            visited[bugPos.x-1][bugPos.y] = true;
            if (bugPos.x+1 < this.width)
            visited[bugPos.x+1][bugPos.y] = true;
            if (bugPos.y-1 >= 0)
            visited[bugPos.x][bugPos.y-1] = true;
            if (bugPos.y+1 < this.height)
            visited[bugPos.x][bugPos.y+1] = true;
        }

        queue.push(pos);
        while (!queue.isEmpty()) {
            Point point = queue.pop();
            for (Point ngh : getNeighbours(point, state)) {
                if (!visited[ngh.x][ngh.y]) {
                    queue.add(ngh);
                    visited[ngh.x][ngh.y] = true;
                    parent[ngh.x][ngh.y] = point;
                    distance[ngh.x][ngh.y] = distance[point.x][point.y] + 1;
                }
            }
        }
    }

    public Point[][] getParent() {
        return parent;
    }

    public Double[][] getDistance() {
        return distance;
    }
}
