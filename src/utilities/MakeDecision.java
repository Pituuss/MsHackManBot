package utilities;

import bot.BotState;
import move.Move;
import move.MoveType;
import player.Player;

import java.awt.*;
import java.util.ArrayList;

public class MakeDecision {
    private BFS playerBfs;
    private BombManager bombManager;
    private BugManager bugManager;
    private MakeMove makeMove;

    public MakeDecision() {
        this.playerBfs = new BFS(19, 15);
        this.makeMove = new MakeMove(playerBfs);
        this.bugManager = new BugManager(playerBfs.getWidth(), playerBfs.getHeight());
        this.bombManager = new BombManager();
    }

    public Move makeMoveDecision(BotState state, Player me) {
        MoveType moveType;
        Move move;

        ArrayList<Point> bombHazard = bombManager.markBombField(state);
        if (bombManager.inBombExpArea(state, bombHazard)) {
            moveType = makeMove.escapeBomb(state, bombHazard);
        } else if (me.getBombs() == 0 && state.getField().getBombPositions().size() > 2)
            moveType = makeMove.getBomb(state);
        else
            moveType = makeMove.getSnippet(state);

        moveType = bugManager.bugByYou(moveType, state);

        if (bugManager.bugAlert(state) && me.getBombs() > 0
                && bombManager.canPlantTheBomb(state, bombHazard, playerBfs)) {
            Point safePoint = bombManager.saveField(state, bombHazard, playerBfs);
            int ticks;
            if (playerBfs.getDistance()[safePoint.x][safePoint.y].intValue() < 2)
                ticks = 3;
            else
                ticks = playerBfs.getDistance()[safePoint.x][safePoint.y].intValue() + 2;

            move = new Move(moveType, ticks);
        } else
            move = new Move(moveType);

        return move;
    }
}