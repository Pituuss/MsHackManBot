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

package bot;

import java.util.Random;

import algorithms.MakeDecision;
import move.Move;
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
@SuppressWarnings("ALL")
public class BotStarter {

    private Random random;
    private MakeDecision makeDecision;

    public BotStarter() {
        this.makeDecision = new MakeDecision();
    }

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
        return makeDecision.makeMoveDecision(state, me);
    }

    public static void main(String[] args) {
        BotParser parser = new BotParser(new BotStarter());
        parser.run();
    }
}
