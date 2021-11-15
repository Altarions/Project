package en.cyprien.cameleon;

import java.awt.*;

public class Player {

    public Color color;
    protected Game game;

    public Player(Game game, Color color){
        this.game = game;
        this.color = color;
    }

    public Integer scoreCalculation(){
        return game.scoreCalculation(this.color);
    }
}
