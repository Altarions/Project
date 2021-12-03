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
    public BigRegion board;

    private final Player player1;
    private final Ia  player2;

    private boolean gridIsFull;
    private final boolean braveRule;
    private final Integer width; // number of boxes wide


    /**
     * @role : game class constructor for user input.
     * @param length :
     * @param gameType :
     */
    public Game(Integer length, Boolean gameType, Boolean strategy){
        this.player1 = new Player(this, Color.RED);
        this.player2 = new Ia(this, Color.BLUE, strategy);
        this.braveRule = gameType;
        this.width = (int) (3 * Math.pow(2, length));
        Integer center = width / 2;
        board = new BigRegion(length,center,center);

    }


    /**
     * @role : constructor of the game class for a txt entry.
     * @param width :
     * @param gridFile :
     * @param gameType :
     */
    public Game(Integer width, ArrayList<Color> gridFile, Boolean gameType, Boolean strategy){
        this.player1 = new Player(this, Color.RED);
        this.player2 = new Ia(this, Color.BLUE, strategy);
        this.braveRule = gameType;
        this.width = width;

        Integer length = (int) Math.round(Math.sqrt(width/3));//on prend l'entier le plus proche
        Integer center = width / 2;
        board = new BigRegion(length,center,center);
        playList(gridFile);

    }


    //------------------- GETTER & SETTER -------------------//

    public Player getPlayer1(){
        return player1;
    }

    public Ia getPlayer2(){
        return player2;
    }



    /**
     * @role : returns the list of all the points corresponding to the given color.
     * @complexity : O(n^2).
     * @param color :
     * @return : ArrayList<Point>.
     */
    public ArrayList<Point> getAllPosColor(Color color){
        ArrayList<Point> listPoint = new ArrayList<>();

        for(int i=1; i<=width; i++){
            for(int j=1; j<=width; j++){
                if(board.getSmallRegion(i,j).getBoxColor(i,j).equals(color)) listPoint.add(new Point(i,j));
            }
        }
        return listPoint;
    }


    /**
     * @role : returns the list of all the colors in the board, ordered from box 1 to n^2.
     * @complexity : O(n^2).
     * @return : ArrayList<Color>.
     */
    public ArrayList<Color> getAllColor(){
        ArrayList<Color> listColor = new ArrayList<>();

        for(int i=1; i<=width; i++){
            for(int j=1; j<=width; j++){
                listColor.add(board.getSmallRegion(i,j).getBoxColor(i,j));
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
     * @complexity : O(1).
     * @param i : line of the game board.
     * @param j : column of the game board.
     * @return : boolean.
     */
    public boolean play(Integer i, Integer j, Color colorPlayer){

            Point posPlayer = new Point(i, j);
            System.out.println("Position jouée : ("+i+";"+j+")");
            rule((int) posPlayer.getX(), (int) posPlayer.getY(), colorPlayer);

            basicDisplay();
            gridIsFull = board.isGridIsFull();


            if (gridIsFull) {
                basicDisplay();
                System.out.println(win());
                return true;
            } else {
                return false;
            }
    }

    /**
     * @role :  display of the tray in the terminal.
     * @complexity : O(n^2)
     */
    public void basicDisplay(){

        for(int i=1; i<=width; i++){
            for(int j=1; j<=width; j++){
                System.out.print(board.getSmallRegion(i,j).getBoxOwner(i,j));

                if(j%3 == 0)System.out.print(" ");

            }
            System.out.println();
            if(i%3 == 0)System.out.println();

        }

    }


    /**
     * @role : Calculate the score of the given color.
     * @complexity : O(n^2).
     * @param color : player color.
     * @return : Integer.
     */
    public Integer scoreCalculation(Color color){

        Integer result = 0;
        for(int i=1; i<=width; i++){
            for(int j=1; j<=width; j++){
                if(board.getSmallRegion(i,j).getBoxColor(i,j).equals(color)) result++;
            }
        }
        return result;
    }


    /**
     * @role : plays an ordered color list from cell 1 to n^2 of the array.
     * @complexity : O(n^2).
     * @param colorList : list of colors that must be played.
     */
    public void playList(ArrayList<Color> colorList){
        for(int i=1; i<=width; i++){
            for(int j=1; j<=width; j++){
                board.getSmallRegion(i,j).setCase(i,j, colorList.get((i-1)*width+(j-1)));
            }
        }
    }


    /**
     * @role : orientates towards the rules chosen by the user.
     * @complexity : O(1).
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
     * @role : apply brave rule.
     * @complexity : O(1).
     * @param i : line of the game board.
     * @param j : column of the game board.
     * @param color : player color.
     */
    private void braveRule(Integer i, Integer j, Color color){
        Color white = Color.WHITE;
        //rule 1
        board.getSmallRegion(i,j).setCase(i, j, color);
        //rule 2
        //look at the 8 adjacent squares
        for(int x=-1; x<=1; x++){
            for(int y=-1; y<=1; y++){
                //look that we do not leave the board
                if(i+x>0 && j+y>0 && i+x<=width && j+y<=width) {
                    Color getColor = board.getSmallRegion(i+x,j+y).getBoxColor(i + x, j + y);
                    //change the color box if it is not the same color and not white
                    if (!color.equals(getColor) && !getColor.equals(white)) {
                        board.getSmallRegion(i+x,j+y).setCase(i + x, j + y, color);
                    }
                }
            }
        }
    }


    /**
     * @role : apply temerity rules.
     * @complexity : O(1).
     * @param i : line of the game board.
     * @param j : column of the game board.
     * @param color : player color.
     */
    private void temerityRule(Integer i, Integer j, Color color) {
        Color white = Color.WHITE;
        //rule 1
        board.getSmallRegion(i,j).setCase(i, j, color);
        //rule 2
        //look at the 8 adjacent squares
        for(int x=-1; x<=1; x++){
            for(int y=-1; y<=1; y++){
                //look that we do not leave the board
                if(i+x>0 && j+y>0 && i+x<=width && j+y<=width) {
                    Color getColor = board.getSmallRegion(i+x,j+y).getBoxColor(i + x, j + y);
                    //change the color box if it is not the same color, not white and the region is not acquired
                    if (!color.equals(getColor) && !getColor.equals(white) && !board.getSmallRegion(i+x,j+y).getAcquired()) {
                        board.getSmallRegion(i+x,j+y).setCase(i + x, j + y, color);
                    }
                }

            }
        }
        //rule 3
        if(board.getSmallRegion(i,j).isFull()) board.getSmallRegion(i,j).setColorList(color);
        //rule 4/5
        board.isAcquired(color);
    }

    /**
     * @role : resets the board to zero with the initial settings.
     * @complexity : O(1).
     */
    public void restartGrid() {
        Integer length = (int) Math.round(Math.sqrt(width/3));
        Integer center = width / 2;

        this.board = new BigRegion(length,center,center);
    }


    /**
     * @role : victory display in terminal.
     * @complexity : O(1).
     * @return : String.
     */
    public String win(){
        Integer redScore = player1.scoreCalculation();
        Integer blueScore = player2.scoreCalculation();

        return (redScore>blueScore? "Victoire !\nVous avez Gagnez":"Defaite !\nVous avez perdu");
    }
}
