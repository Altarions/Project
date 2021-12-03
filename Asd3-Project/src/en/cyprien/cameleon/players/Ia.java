package en.cyprien.cameleon.players;

import en.cyprien.cameleon.Game;

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

    private final Boolean greedyStrategy;
    public final ArrayList<Point> allWhitePos;

    /**
     * @role :
     * @param game :
     * @param color :
     * @param greedyStrategy :
     */
    public Ia(Game game, Color color, Boolean greedyStrategy) {

        super(game, color);
        this.greedyStrategy = greedyStrategy;
        this.allWhitePos = this.game.getAllPosColor(Color.WHITE);
    }


    /**
     * @role :
     * @complexity :
     * @return : Point.
     */
    public Point play(){

        if(greedyStrategy){
            return greedyStrategy();
        }else{
            return strongStrategy();
        }
    }


    /**
     * @role : Look all the possibility and choose  the possibility with best value.
     * @complexity :
     * @return : Point.
     */
    private Point greedyStrategy(){

        Integer bestOption = -1;
        Point bestPoint = new Point();

        ArrayList<Color> original = this.game.getAllColor();


        for(Point x : allWhitePos){

            this.game.rule((int) x.getX(), (int) x.getY(), this.color);

            Integer newOption = this.game.scoreCalculation(this.color);

            if (newOption > bestOption) {
                bestOption = newOption;
                bestPoint = x;
            }
            this.game.restartGrid();
            this.game.playList(original);

        }

        return bestPoint;
    }


    /**
     * @role :
     * @complexity :
     * @return :
     */
    private Point strongStrategy(){
        Integer bestOption = -1;
        Point bestPoint = new Point();

        ArrayList<Color> original = this.game.getAllColor();


        for(Point x : allWhitePos){

            this.game.rule((int)x.getX(),(int)x.getY(), this.color);

            Integer newOption =  this.game.scoreCalculation(this.color);

            if (newOption > bestOption && whiteCase(x) != 2) {
                System.out.println(whiteCase(x));
                bestOption = newOption;
                bestPoint = x;
            }

            this.game.restartGrid();
            this.game.playList(original);

            if(whiteCase(x)<=1){
                bestPoint = x;
                break;
            }

        }

        return bestPoint;
    }

    private Integer whiteCase(Point x){
        ArrayList<Color> colorList = this.game.board.getSmallRegion((int)x.getX(), (int)x.getY()).getColorList();
        Integer white = 0;
        for(Color cl : colorList){
            if(cl.equals(Color.WHITE))white++;
        }
        return white;
    }

}
