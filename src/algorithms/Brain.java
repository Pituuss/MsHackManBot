package algorithms;

import bot.BotState;
import move.Move;
import move.MoveType;
import player.Player;

import java.awt.*;
import java.util.ArrayList;

public class Brain {
    private BFS bfs;
    private BFS antagonistBFS;
    private Pathfinder pathfinder;
    private Bombs bombManager;
    private Bugs bugsManager; //XD

    public Brain(BFS bfs, BFS antagonistBFS, Pathfinder pathfinder) {
        this.bfs = bfs;
        this.antagonistBFS = antagonistBFS;
        this.pathfinder = pathfinder;
        this.bombManager = new Bombs();
        this.bugsManager = new Bugs(bfs.getWidth(), bfs.getHeight());
    }

    public Move makeMoveDecision(BotState state, Player me) {
        MoveType moveType;
        Move move;
        bfs.clear();
        bfs.startBFS(state.getField().getMyPosition(), state);

        antagonistBFS.clear();
        antagonistBFS.startBFS(state.getField().getOpponentPosition(), state);

//        ArrayList<Point> bombHarazd = bombManager.markBombField(state);
//        if (bombManager.inBombExpArea(state, bombHarazd)) {
//            moveType = escapeBomb(state, bombHarazd);
//        }
//        else
        moveType = getSnippet(state);

        moveType = bugsManager.bugByYou(moveType, state);

        if (bugsManager.bugAlert(state) && me.getBombs() > 0) {
//                && bombManager.canPlantTheBomb(state, bombHarazd, bfs)) {
//            Point safe = bombManager.saveField(state, bombHarazd, bfs);
//            int ticks;
//            if (bfs.getDistance()[safe.x][safe.y].intValue() < 2)
//                ticks = 3;
//            else
//                ticks = bfs.getDistance()[safe.x][safe.y].intValue() + 2;

            move = new Move(moveType, 3);
        } else
            move = new Move(moveType);

        return move;
    }

    private MoveType escapeBomb(BotState state, ArrayList<Point> bombHarazd) {
        return pathfinder.nextMove(state, bfs.getParent(), bombManager.saveField(state, bombHarazd, bfs));
    }


//    private MoveType getBomb(BotState state) {
//        System.err.println("get bomb");
//        BFS localBFS = new BFS(19, 15);
//        Double[][] distance = bfs.getDistance();
//
//        double closestDistanceBomb = Double.POSITIVE_INFINITY;
//        Point clsBombPos = new Point(-1, -1);
//
//        for (Point point : state.getField().getBombPositions()) {
//            if (distance[point.x][point.y] < closestDistanceBomb) {
//                closestDistanceBomb = distance[point.x][point.y];
//                clsBombPos = point;
//            }
//        }
//
//        if (clsBombPos.equals(new Point(-1, -1)))
//            return pathfinder.nextMove(state, bfs.getParent(), clsBombPos);
//
//        double closestDistanceSnipp = Double.POSITIVE_INFINITY;
//        Point clsSnippPos = new Point(-1, -1);
//        for (Point point : state.getField().getBombPositions()) {
//            if (distance[point.x][point.y] < closestDistanceSnipp) {
//                closestDistanceSnipp = distance[point.x][point.y];
//                clsSnippPos = point;
//            }
//        }
//
//        if (clsSnippPos.equals(new Point(-1, -1)))
//            return pathfinder.nextMove(state, bfs.getParent(), clsBombPos);
//
//        localBFS.clear();
//        localBFS.startBFS(clsSnippPos, state);
//
//        if (closestDistanceSnipp < closestDistanceBomb + localBFS.getDistance()[clsSnippPos.x][clsSnippPos.y] + 9) {
//            System.err.println("or not snipp better");
//            return pathfinder.nextMove(state, bfs.getParent(), clsSnippPos);
//        } else
//            return pathfinder.nextMove(state, bfs.getParent(), clsBombPos);
//    }

    private MoveType getSnippet(BotState state) {
        System.err.println("get snippet");
        Double[][] distance = bfs.getDistance();
        double closestDistanceSnipp = Double.POSITIVE_INFINITY;
        Point clsSnippPos = new Point(-1, -1);

        for (Point point : state.getField().getSnippetPositions()) {
            if (distance[point.x][point.y] < closestDistanceSnipp) {
                closestDistanceSnipp = distance[point.x][point.y];
                clsSnippPos = point;
            }
        }
        if (clsSnippPos.equals(new Point(-1, -1)))
            return pathfinder.nextMove(state, bfs.getParent(), clsSnippPos);

        if (state.getField().getSnippetPositions().size() > 1 && antagonistBFS.getDistance()[clsSnippPos.x][clsSnippPos.y] > closestDistanceSnipp) {

            double TMPClosestDistanceSnipp = Double.POSITIVE_INFINITY;
            Point TMPClsSnippPos = new Point(-1, -1);

            for (Point point : state.getField().getSnippetPositions()) {
                if (distance[point.x][point.y] < TMPClosestDistanceSnipp && !TMPClsSnippPos.equals(clsSnippPos)) {
                    TMPClosestDistanceSnipp = distance[point.x][point.y];
                    TMPClsSnippPos = point;
                }
            }
            clsSnippPos = TMPClsSnippPos;
        }
        return pathfinder.nextMove(state, bfs.getParent(), clsSnippPos);
    }
}
