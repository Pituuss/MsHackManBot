package algorithms;

import bot.BotState;
import move.MoveType;
import player.Player;

import java.awt.*;

public class Brain {
    private BFS bfs;
    private Pathfinder pathfinder;

    public Brain(BFS bfs, Pathfinder pathfinder) {
        this.bfs = bfs;
        this.pathfinder = pathfinder;
    }

    public MoveType makeMoveDecison(BotState state, Player me) {
        MoveType move;
        bfs.clear();
        bfs.startBFS(state.getField().getMyPosition(),state);
//me.getBombs() == 0 && state.getField().getEnemyPositions().size() > 3
        if (state.getField().getBombPositions().size() != 0)
            move = getBomb(state);
        else
            move = getSnippet(state);

        return move;
    }

    private MoveType getBomb(BotState state) {
        BFS localBFS = new BFS(19, 15);
        Double[][] distance = bfs.getDistance();

        double closestDistanceBomb = Double.POSITIVE_INFINITY;
        Point clsBombPos = new Point(-1, -1);
        for (Point point : state.getField().getBombPositions()) {
            if (distance[point.x][point.y] < closestDistanceBomb) {
                closestDistanceBomb = distance[point.x][point.y];
                clsBombPos = point;
            }
        }
        // a może opłaca mi się przejść przez jakiegoś snippeta bo jest po drodze

        double closestDistanceSnipp = Double.POSITIVE_INFINITY;
        Point clsSnippPos = new Point(-1, -1);
        for (Point point : state.getField().getBombPositions()) {
            if (distance[point.x][point.y] < closestDistanceSnipp) {
                closestDistanceSnipp = distance[point.x][point.y];
                clsSnippPos = point;
            }
        }
        localBFS.clear();
        localBFS.startBFS(clsSnippPos, state);

        if (closestDistanceSnipp < closestDistanceBomb && localBFS.getDistance()[clsBombPos.x][clsBombPos.y] < 5) {
            return pathfinder.nextMove(state, bfs.getParent(), clsSnippPos);
        }
        return pathfinder.nextMove(state, bfs.getParent(), clsBombPos);
    }

    private MoveType getSnippet(BotState state) {
        Double[][] distance = bfs.getDistance();

        double closestDistanceSnipp = Double.POSITIVE_INFINITY;
        Point clsSnippPos = new Point(-1, -1);

        for (Point point : state.getField().getSnippetPositions()) {
            if (distance[point.x][point.y] < closestDistanceSnipp) {
                closestDistanceSnipp = distance[point.x][point.y];
                clsSnippPos = point;
            }
        }
        return pathfinder.nextMove(state, bfs.getParent(), clsSnippPos);
    }
}
