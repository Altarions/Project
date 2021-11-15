package en.cyprien.cameleon;

import java.awt.*;
import java.util.ArrayList;

public class Ia extends Player {

    public Ia(Game game, Color color) {
        super(game, color);
    }

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

            this.game.playList(original);

        }

        return bestPoint;
    }


}
