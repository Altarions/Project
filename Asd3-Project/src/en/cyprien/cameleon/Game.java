package en.cyprien.cameleon;

import en.cyprien.cameleon.players.Ia;
import en.cyprien.cameleon.players.Player;
import en.cyprien.cameleon.region.BigRegion;

import java.awt.*;
import java.util.ArrayList;

public class Game {
    public BigRegion grid;
    private boolean gridIsFull;
    private Player player;
    private Ia robot;
    private boolean braveRule;
    private Integer width;

    public Game(Integer length, Boolean gameType){
        this.player = new Player(this, Color.RED);
        this.robot = new Ia(this, Color.BLUE);
        this.braveRule = gameType;
        this.width = (int) (3 * Math.pow(2, length));
        Integer center = width / 2;
        grid = new BigRegion(length,center,center);
    }

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

    public String win(){
        Integer redScore = player.scoreCalculation();
        Integer blueScore = robot.scoreCalculation();

        return (redScore>blueScore? "Victoire !\nVous avez Gagnez":"Defaite !\nVous avez perdu");
    }

    public Integer scoreCalculation(Color color){

        Integer result = 0;
        for(int i=1; i<=width; i++){
            for(int j=1; j<=width; j++){
                if(grid.getSmallRegion(i,j).getBoxColor(i,j).equals(color)) result++;
            }
        }
        return result;
    }

    public ArrayList<Point> getAllPosColor(Color color){
        ArrayList<Point> listPoint = new ArrayList<>();

        for(int i=1; i<=width; i++){
            for(int j=1; j<=width; j++){
                if(grid.getSmallRegion(i,j).getBoxColor(i,j).equals(color)) listPoint.add(new Point(i,j));
            }
        }
        return listPoint;
    }

    public ArrayList<Color> getAllColor(){
        ArrayList<Color> listColor = new ArrayList<>();

        for(int i=1; i<=width; i++){
            for(int j=1; j<=width; j++){
                listColor.add(grid.getSmallRegion(i,j).getBoxColor(i,j));
            }
        }
        return listColor;
    }

    public void playList(ArrayList<Color> liste){
        for(int i=1; i<=width; i++){
            for(int j=1; j<=width; j++){
                grid.getSmallRegion(i,j).setCase(i,j, liste.get((i-1)*width+(j-1)));
            }
        }
    }

    public void rule(Integer i, Integer j, Color color){

        if(this.braveRule) {
            braveRule(i,j,color);
        }else{
            temerityRule(i,j,color);
        }
    }

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

    public Integer getWidth(){
        return this.width;
    }


    public void restartGrid() {
        Integer length = (int) Math.sqrt(width/3);
        Integer center = width / 2;
        this.grid = new BigRegion(length,center,center);
    }
}
