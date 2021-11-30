package en.cyprien.cameleon;

import en.cyprien.cameleon.players.Ia;
import en.cyprien.cameleon.players.Player;
import en.cyprien.cameleon.region.BigRegion;

import java.awt.*;
import java.util.ArrayList;
/**
 * @className : Game
 * @role : manages all the back end of the game
 * @version :  1.0.0
 * @date : 29/11/2021
 * @author : GARNIER Cyprien
 */
public class Game {
    public BigRegion grid;

    private Player player;
    private Ia robot;

    private boolean gridIsFull;
    private boolean braveRule;
    private Integer width;

    /**
     * @role :
     * @param length :
     * @param gameType :
     */
    public Game(Integer length, Boolean gameType){
        this.player = new Player(this, Color.RED);
        this.robot = new Ia(this, Color.BLUE);
        this.braveRule = gameType;
        this.width = (int) (3 * Math.pow(2, length));
        Integer center = width / 2;
        grid = new BigRegion(length,center,center);
    }


    /**
     * @role :
     * @param width :
     * @param gridFile :
     * @param gameType :
     */
    public Game(Integer width, ArrayList<Color> gridFile, Boolean gameType){
        this.player = new Player(this, Color.RED);
        this.robot = new Ia(this, Color.BLUE);
        this.braveRule = gameType;
        this.width = width;
        Integer length = (int) Math.sqrt(width/3);
        Integer center = width / 2;
        grid = new BigRegion(length,center,center);
        playList(gridFile);
    }


    //------------------- GETTER & SETTER -------------------//

    /**
     * @role :
     * @complexity :
     * @param color :
     * @return : ArrayList<Point>.
     */
    public ArrayList<Point> getAllPosColor(Color color){
        ArrayList<Point> listPoint = new ArrayList<>();

        for(int i=1; i<=width; i++){
            for(int j=1; j<=width; j++){
                if(grid.getSmallRegion(i,j).getBoxColor(i,j).equals(color)) listPoint.add(new Point(i,j));
            }
        }
        return listPoint;
    }


    /**
     * @role :
     * @complexity :
     * @return : ArrayList<Color>.
     */
    public ArrayList<Color> getAllColor(){
        ArrayList<Color> listColor = new ArrayList<>();

        for(int i=1; i<=width; i++){
            for(int j=1; j<=width; j++){
                listColor.add(grid.getSmallRegion(i,j).getBoxColor(i,j));
            }
        }
        return listColor;
    }

    public Integer getWidth(){
        return this.width;
    }


    //------------------- END GETTER & SETTER -------------------//

    /**
     * @role :
     * @complexity :
     * @param i :
     * @param j :
     * @return : boolean.
     */
    public boolean play(Integer i, Integer j){
        System.out.println(braveRule);
        Point posPlayer = new Point(i,j);
        Point posRobot;


        rule((int)posPlayer.getX(), (int)posPlayer.getY(), player.color);
        System.out.println("Player");
        basicDisplay();
        gridIsFull = grid.isGridIsFull();

        if(!gridIsFull) {
            posRobot = robot.play();
            rule((int) posRobot.getX(), (int) posRobot.getY(), robot.color);
            System.out.println("IA");
            basicDisplay();
            gridIsFull = grid.isGridIsFull();

        }
        if(gridIsFull) {
            basicDisplay();
            System.out.println(win());
            return true;
        }else{
            return false;
        }
    }

    /**
     * @role :
     * @complexity :
     */
    void basicDisplay(){

        for(int i=1; i<=width; i++){
            for(int j=1; j<=width; j++){
                System.out.print(grid.getSmallRegion(i,j).getBoxOwner(i,j));

                if(j%3 == 0)System.out.print(" ");

            }
            System.out.println();
            if(i%3 == 0)System.out.println();

        }

    }

    /**
     * @role :
     * @complexity :
     * @return : String.
     */
    public String win(){
        Integer redScore = player.scoreCalculation();
        Integer blueScore = robot.scoreCalculation();

        return (redScore>blueScore? "Victoire !\nVous avez Gagnez":"Defaite !\nVous avez perdu");
    }

    /**
     * @role :
     * @complexity :
     * @param color :
     * @return : Integer.
     */
    public Integer scoreCalculation(Color color){

        Integer result = 0;
        for(int i=1; i<=width; i++){
            for(int j=1; j<=width; j++){
                if(grid.getSmallRegion(i,j).getBoxColor(i,j).equals(color)) result++;
            }
        }
        return result;
    }


    /**
     * @role :
     * @complexity :
     * @param colorList :
     */
    public void playList(ArrayList<Color> colorList){
        for(int i=1; i<=width; i++){
            for(int j=1; j<=width; j++){
                grid.getSmallRegion(i,j).setCase(i,j, colorList.get((i-1)*width+(j-1)));
            }
        }
    }


    /**
     * @role :
     * @complexity :
     * @param i : line of the game board.
     * @param j : column of the game board.
     * @param color : player color.
     */
    public void rule(Integer i, Integer j, Color color){

        if(this.braveRule) {
            braveRule(i,j,color);
        }else{
            temerityRule(i,j,color);
        }
    }


    /**
     * @role :
     * @complexity :
     * @param i : line of the game board.
     * @param j : column of the game board.
     * @param color : player color.
     */
    private void braveRule(Integer i, Integer j, Color color){
        Color white = Color.WHITE;
        //rule 1
        grid.getSmallRegion(i,j).setCase(i, j, color);
        //rule 2
        for(int x=-1; x<=1; x++){
            for(int y=-1; y<=1; y++){
                if(i+x>0 && j+y>0 && i+x<=width && j+y<=width) {
                    Color getColor = grid.getSmallRegion(i+x,j+y).getBoxColor(i + x, j + y);
                    if (!color.equals(getColor) && !getColor.equals(white)) {
                        grid.getSmallRegion(i+x,j+y).setCase(i + x, j + y, color);
                    }
                }
            }
        }
    }


    /**
     * @role :
     * @complexity :
     * @param i : line of the game board.
     * @param j : column of the game board.
     * @param color : player color.
     */
    private void temerityRule(Integer i, Integer j, Color color) {
        Color white = Color.WHITE;
        //rule 1
        grid.getSmallRegion(i,j).setCase(i, j, color);
        //rule 2
        for(int x=-1; x<=1; x++){
            for(int y=-1; y<=1; y++){
                if(i+x>0 && j+y>0 && i+x<=width && j+y<=width) {
                    Color getColor = grid.getSmallRegion(i+x,j+y).getBoxColor(i + x, j + y);

                    if (!color.equals(getColor) && !getColor.equals(white) && !grid.getSmallRegion(i+x,j+y).getAcquired()) {
                        grid.getSmallRegion(i+x,j+y).setCase(i + x, j + y, color);
                    }
                }
            }
        }
        //rule 3
        if(grid.getSmallRegion(i,j).isFull())grid.getSmallRegion(i,j).setColorList(color);
        //rule 4
        grid.isAcquired(color);
    }

    /**
     * @role :
     */
    public void restartGrid() {
        Integer length = (int) Math.sqrt(width/3);
        Integer center = width / 2;
        this.grid = new BigRegion(length,center,center);
    }
}
