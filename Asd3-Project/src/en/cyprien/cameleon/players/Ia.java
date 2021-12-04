package en.cyprien.cameleon.players;

import en.cyprien.cameleon.Game;

import java.awt.*;
import java.util.ArrayList;


/**
 * @className : IA
 * @role : represent the leaves of the trees
 * @version :  1.1.0
 * @date : 30/11/2021
 * @author : GARNIER Cyprien
 */
public class Ia extends Player {

    private final Boolean greedyStrategy;
    public final ArrayList<Point> allWhitePos;

    /**
     * @role : Constructor of Ia.
     * @param game : game played.
     * @param color : color of the AI.
     * @param greedyStrategy : strategy played by the IA.
     */
    public Ia(Game game, Color color, Boolean greedyStrategy) {

        super(game, color);
        this.greedyStrategy = greedyStrategy;
        this.allWhitePos = this.game.getAllPosColor(Color.WHITE);
    }


    public Point play(){

        if(greedyStrategy){
            return greedyStrategy();
        }else{
            return strongStrategy();
        }
    }


    /**
     * @role : Look all the possibility and choose  the possibility with best value.
     * @complexity : O(n).
     * @return : Point.
     */
    private Point greedyStrategy(){

        Integer bestOption = -1;
        Point bestPoint = allWhitePos.get(0);

        ArrayList<Color> original = this.game.getAllColor();


        for(Point x : allWhitePos){

            this.game.rule((int) x.getX(), (int) x.getY(), this.color);

            Integer newOption = scoreCalculation();

            if (newOption > bestOption) {
                bestOption = newOption;
                bestPoint = x;
            }
            this.game.restartBoard();
            this.game.remplirTableau(original);

        }

        return bestPoint;
    }


    /**
     * @role : Look all the possibility and choose  the possibility with best value or best position.
     * @complexity : O(n).
     * @return : Point.
     */
    private Point strongStrategy(){
        Integer bestOption = -1;
        Point bestPoint = allWhitePos.get(0);

        ArrayList<Color> original = this.game.getAllColor();


        for(Point x : allWhitePos){

            Integer nbWhiteBox = this.game.board.getSmallRegion((int)x.getX(),(int)x.getY()).nbBox(Color.WHITE);

            this.game.rule((int)x.getX(),(int)x.getY(), this.color);

            Integer newOption =  scoreCalculation();


            System.out.println(nbWhiteBox);
            if (newOption > bestOption && nbWhiteBox != 2) {
                bestOption = newOption;
                bestPoint = x;
            }

            this.game.restartBoard();
            this.game.remplirTableau(original);

            if(nbWhiteBox<=1)return x;

        }

        return bestPoint;
    }


}
