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

    public ArrayList<Point> getNeighbours(Point position, BotState state) {
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
        if (state.getField().isPointValid(upper)) adjacentPoints.add(upper);
        if (state.getField().isPointValid(bottom)) adjacentPoints.add(bottom);
        if (state.getField().isPointValid(left)) adjacentPoints.add(left);
        if (state.getField().isPointValid(right)) adjacentPoints.add(right);

        return adjacentPoints;
    }

    public void startBFS(Point pos, BotState state) {

        distance[pos.x][pos.y] = 0.0;
        visited[pos.x][pos.y] = true;

        markBugs(visited, state);

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

    public void markBugs(Boolean[][] visited, BotState state) {
        for (Point bugPos : state.getField().getEnemyPositions()) {
            visited[bugPos.x][bugPos.y] = true;
            if (bugPos.x - 1 >= 0)
                visited[bugPos.x - 1][bugPos.y] = true;
            if (bugPos.x + 1 < this.width)
                visited[bugPos.x + 1][bugPos.y] = true;
            if (bugPos.y - 1 >= 0)
                visited[bugPos.x][bugPos.y - 1] = true;
            if (bugPos.y + 1 < this.height)
                visited[bugPos.x][bugPos.y + 1] = true;
        }
    }

    public Point[][] getParent() {
        return parent;
    }

    public Double[][] getDistance() {
        return distance;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
