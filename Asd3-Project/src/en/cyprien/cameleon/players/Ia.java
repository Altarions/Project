package en.cyprien.cameleon.players;

import en.cyprien.cameleon.Game;
import en.cyprien.cameleon.region.BigRegion;

import java.awt.*;
import java.util.ArrayList;


/**
 * @className : IA
 * @role : represent the leaves of the trees
 * @version :  1.0.0
 * @date : 30/11/2021
 * @author : GARNIER Cyprien
 */
public class Ia extends Player {


    /**
     * @role :
     * @param game :
     * @param color :
     */
    public Ia(Game game, Color color) {
        super(game, color);
    }


    /**
     * @role :
     * @complexity :
     * @return : Point.
     */
    public Point play(){
        Integer bestOption = 0;
        Point bestPoint = new Point();

        ArrayList<Color> original = this.game.getAllColor();
        ArrayList<Point> allWhitePos = this.game.getAllPosColor(new Color(255,255,255));

        for(Point x : allWhitePos){

            this.game.rule((int)x.getX(),(int)x.getY(), this.color);

            Integer newOption =  this.game.scoreCalculation(this.color);

            if(newOption > bestOption){
                bestOption = newOption;
                bestPoint = x;
            }
            this.game.restartGrid();
            this.game.playList(original);

        }

        return bestPoint;
    }


}
