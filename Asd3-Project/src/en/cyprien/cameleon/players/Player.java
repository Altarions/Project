package en.cyprien.cameleon.players;

import en.cyprien.cameleon.Game;

import java.awt.*;


/**
 * @className : Player
 * @role : represent the players
 * @version :  1.0.0
 * @date : 29/11/2021
 * @author : GARNIER Cyprien
 */
public class Player {

    public final Color color;
    protected Game game;


    /**
     * @role : constructor of player class.
     * @param game :
     * @param color : color of the player
     */
    public Player(Game game, Color color){
        this.game = game;
        this.color = color;
    }

    /**
     * @role : get the score.
     * @return : Integer.
     */
    public Integer scoreCalculation(){
        return game.getScore(this.color);
    }
}
