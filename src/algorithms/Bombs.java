package algorithms;


import bot.BotState;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class Bombs {
    public boolean inBombExpArea(BotState state, ArrayList<Point> bombHazard) {
        return bombHazard.contains(state.getField().getMyPosition());
    }

    public boolean canPlantTheBomb(BotState state, ArrayList<Point> bombHazard, BFS bfs) {
        Point safe = saveField(state, bombHazard, bfs);
        return !safe.equals(new Point(-1, -1)) && bfs.getDistance()[safe.x][safe.y] + 1 < 3;
    }

    public Point saveField(BotState state, ArrayList<Point> bombHazard, BFS bfs) {
        LinkedList<Point> queue = new LinkedList<>();
        Boolean[][] visited = new Boolean[bfs.getWidth()][bfs.getHeight()];

        for (int i = 0; i < bfs.getWidth(); i++) {
            for (int j = 0; j < bfs.getHeight(); j++) {
                visited[i][j] = false;
            }
        }

        bfs.markBugs(visited, state);

        queue.push(state.getField().getMyPosition());
        Double distance = Double.POSITIVE_INFINITY;
        Point closestSafe = new Point(-1, -1);

        while (!queue.isEmpty()) {
            Point point = queue.pop();
            for (Point ngh : bfs.getNeighbours(point, state)) {
                if (bombHazard.contains(ngh)) {
                    if (!visited[ngh.x][ngh.y]) {
                        queue.add(ngh);
                        visited[ngh.x][ngh.y] = true;
                    }
                } else if (bfs.getDistance()[ngh.x][ngh.y] < distance) {
                    distance = bfs.getDistance()[ngh.x][ngh.y];
                    closestSafe = ngh;
                }
            }
        }
        System.err.println("safe "+closestSafe);
        return closestSafe;
    }

    public ArrayList<Point> markBombField(BotState state) {
        ArrayList<Point> fields = new ArrayList<>();
        ArrayList<Point> activeBombField = state.getField().getTickingBombPositions();
        if (activeBombField.size() < 1)
            return fields;

        for (Point bombField : activeBombField) {
            int bmX = bombField.x;
            int bmY = bombField.y;

            fields.add(bombField);

            boolean dir1 = false, dir2 = false, dir3 = false, dir4 = false;

            for (int i = 1; !dir1 || !dir2 || !dir3 || !dir4; i++) {
                if (state.getField().isPointValid(new Point(bmX + i, bmY)) && !dir1) {
                    fields.add(new Point(bmX + i, bmY));
                } else
                    dir1 = true;
                if (state.getField().isPointValid(new Point(bmX - i, bmY)) && !dir2) {
                    fields.add(new Point(bmX - i, bmY));
                } else
                    dir2 = true;
                if (state.getField().isPointValid(new Point(bmX, bmY + i)) && !dir3) {
                    fields.add(new Point(bmX, bmY + i));
                } else
                    dir3 = true;
                if (state.getField().isPointValid(new Point(bmX, bmY - i)) && !dir4) {
                    fields.add(new Point(bmX, bmY - i));
                } else
                    dir4 = true;
            }
        }
        return fields;
    }
}
