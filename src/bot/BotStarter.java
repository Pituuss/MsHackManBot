/*
 * Copyright 2017 riddles.io (developers@riddles.io)
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 *     For the full copyright and license information, please view the LICENSE
 *     file that was distributed with this source code.
 */

/*
settings player_names player0,player1
settings your_bot player0
settings timebank 2000
settings time_per_move 100
settings your_botid 0
settings field_width 19
settings field_height 15
settings max_rounds 250
action character 2000
update game round 108
update game field S,.,.,x,.,.,.,.,.,.,.,.,.,.,.,x,.,.,S,.,x,.,x,.,x,x,x,x,.,x,x,x,x,.,x,.,x,C,C,x,.,.,C,x,.,.,.,.,.,.,.,x,.,.,.,x,.,.,x,x,x,.,x,.,x,x,x,x,x,.,x,.,x,x,x,.,C,x,.,.,.,x,.,.,.,.,.,.,.,x,.,.,.,x,.,.,.,.,x,.,x,.,x,x,.,x,x,.,x,.,x,.,.,.,x,C,x,x,.,.,.,x,x,.,x,x,.,.,.,x,x,.,x,Gl,.,x,x,.,x,x,x,x,.,x,x,x,x,.,x,x,E0;E1,P0;P1;Gr,x,.,x,x,.,.,.,.,.,.,.,.,.,.,.,x,x,.,x,B,.,.,x,.,x,x,x,x,x,x,x,x,x,.,x,.,.,.,.,x,.,.,.,.,.,.,x,x,x,.,.,.,.,.,.,x,.,.,x,.,x,x,.,x,.,.,.,.,.,x,.,x,x,.,x,.,.,x,.,x,x,.,x,x,x,x,x,x,x,.,x,x,.,x,.,.,x,.,x,x,.,x,.,.,.,.,.,x,.,x,x,.,x,.,S,.,.,.,.,.,.,.,x,x,x,.,.,.,.,.,.,.,S
update player0 snippets 4
update player0 bombs 0
update player1 snippets 6
update player1 bombs 0
action move 2000

update game round 0
update game field S,.,.,x,.,.,.,.,.,.,.,.,.,.,.,x,.,.,S,.,x,.,x,C,x,x,x,x,.,x,x,x,x,.,x,.,x,.,.,x,.,.,.,x,.,.,.,.,.,.,.,x,.,.,.,x,.,.,x,x,x,.,x,.,x,x,x,x,x,.,x,.,x,x,x,.,.,x,.,.,.,x,.,.,.,.,.,.,.,x,.,.,.,x,.,.,.,.,x,.,x,.,x,x,.,x,x,.,x,.,x,.,.,.,x,.,x,x,.,.,.,x,x,.,x,x,.,.,.,x,x,.,x,Gl,.,x,x,P0,x,x,x,x,.,x,x,x,x,P1,x,x,.,Gr,x,.,x,x,.,.,.,.,.,.,.,.,.,.,.,x,x,.,x,.,.,.,x,.,x,x,x,x,x,x,x,x,x,.,x,.,.,.,.,x,.,.,.,.,.,.,x,x,x,.,.,.,.,.,.,x,.,.,x,.,x,x,.,x,.,.,.,C,.,x,.,x,x,.,x,.,.,x,.,x,x,.,x,x,x,x,x,x,x,.,x,x,.,x,.,.,x,.,x,x,.,x,.,.,.,.,.,x,.,x,x,.,x,.,S,.,.,.,.,.,.,.,x,x,x,.,.,.,.,.,.,.,S
update player0 snippets 0
update player0 bombs 0
update player1 snippets 0
update player1 bombs 0
action move 2000
*/

package bot;

import java.util.Arrays;
import java.util.Random;

import algorithms.BFS;
import algorithms.Brain;
import algorithms.Pathfinder;
import move.Move;
import move.MoveType;
import player.CharacterType;
import player.Player;

/**
 * bot.BotStarter
 * <p>
 * Magic happens here. You should edit this file, or more specifically
 * the doMove() method to make your bot do more than random moves.
 * <p>
 * /
 *
 * @author Jim van Eeden - jim@riddles.io
 */
public class BotStarter {

    private Random random;
    private BFS bfs;
    private Pathfinder pathfinder;
    private Brain brain;
    private BFS oponentBFS;

    private BotStarter() {
        this.random = new Random();
        this.bfs = new BFS(19, 15);
        this.pathfinder = new Pathfinder();
        this.oponentBFS = new BFS(19, 15);
        this.brain = new Brain(this.bfs, this.oponentBFS, this.pathfinder);
    }

    /**
     * Return a random character to play as
     *
     * @return A random character
     */
    public CharacterType getCharacter() {
        CharacterType[] characters = CharacterType.values();
        return characters[this.random.nextInt(characters.length)];
    }

    /**
     * Does a move action. Edit this to make your bot smarter.
     *
     * @param state The current state of the game
     * @return A Move object
     */
    public Move doMove(BotState state) {
        Player me = state.getPlayers().get(state.getMyName());
        Move nextMove = brain.makeMoveDecision(state, me);

//        for (Double[] Row : bfs.getDistance())
//            System.err.println(Arrays.toString(Row));
//        System.err.println("\n\n");
//
//        for (String[] Row : state.getField().getField())
//            System.err.println(Arrays.toString(Row));
//        System.err.println("\n\n");


        return nextMove;// Drop bomb if available
    }

    public static void main(String[] args) {
        BotParser parser = new BotParser(new BotStarter());
        parser.run();
    }
}
