package en.cyprien.cameleon;

import en.cyprien.cameleon.players.Ia;
import en.cyprien.cameleon.players.Player;
import en.cyprien.cameleon.region.BigRegion;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    public BigRegion grid;
    private boolean gridIsFull;
    private Player player;
    private Ia robot;
    private boolean braveRule;
    private Integer width;

    public void start(){
        init();
        this.player = new Player(this, new Color(255,0,0));
        this.robot = new Ia(this, new Color(0,0,255));
        Point posPlayer;
        Point posRobot;

        basicDisplay();
        while(!gridIsFull){

            posPlayer = posPlayer();
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
        }
        basicDisplay();
        win();


    }

    private void init(){
        Scanner clavier = new Scanner(System.in);
        Integer length;
        Integer center;



        System.out.println("Voulez vous utiliser un tableau vide ? sinon il sera deja remplie");
        Boolean gridEmpty = clavier.nextBoolean();

        if(gridEmpty) {
            System.out.println("Taille du plateau : (int)");
            length = clavier.nextInt();
            this.width = (int) (3 * Math.pow(2, length));
            center = width / 2;
            grid = new BigRegion(length,center,center);
        }else{
            System.out.println("Rentrez le nom d'un fichier : ");
            String nameFile = clavier.next();
            Read file = new Read(nameFile+".txt");

            this.width = file.getWidthFile();
            length = (int) Math.sqrt(width/3);
            center = width / 2;
            grid = new BigRegion(length,center,center);
            playList(file.getGridFile());
        }
        System.out.println("Voulez vous faire les règles brave cameleon ? Sinon les règle temeraire seront appliqués ");
        this.braveRule = clavier.nextBoolean();


        gridIsFull = grid.isGridIsFull();

        //Display display = new Display(this);
    }

    private Point posPlayer(){
        int i = 0;
        int j = 0;
        boolean posValid = false;
        while(!posValid){
            Scanner clavier = new Scanner(System.in);
            System.out.println("Position i : (int)");
            i = clavier.nextInt();
            System.out.println("Position j : (int)");
            j = clavier.nextInt();
            if(i<=width &&
                    i>0 &&
                    j<=width &&
                    j>0 &&
                    grid.getSmallRegion(i,j).getCase(i,j).equals(new Color(255,255,255))){
                posValid = true;
            }else{
                System.out.println("Vous avez rentré une mauvaise position");
            }
        }

        return new Point(i,j);
    }

    void basicDisplay(){

        for(int i=1; i<=width; i++){
            for(int j=1; j<=width; j++){
                System.out.print(grid.getSmallRegion(i,j).getColor(i,j));

                if(j%3 == 0)System.out.print(" ");

            }
            System.out.println();
            if(i%3 == 0)System.out.println();

        }

    }

    void win(){
        Integer redScore = player.scoreCalculation();
        Integer blueScore = robot.scoreCalculation();

        System.out.println("Le joueur "+(redScore>blueScore? "Rouge":"Bleu"));
    }

    public Integer scoreCalculation(Color color){

        Integer result = 0;
        for(int i=1; i<=width; i++){
            for(int j=1; j<=width; j++){
                if(grid.getSmallRegion(i,j).getCase(i,j).equals(color)) result++;
            }
        }
        return result;
    }

    public ArrayList<Point> getAllPosColor(Color color){
        ArrayList<Point> listPoint = new ArrayList<>();

        for(int i=1; i<=width; i++){
            for(int j=1; j<=width; j++){
                if(grid.getSmallRegion(i,j).getCase(i,j).equals(color)) listPoint.add(new Point(i,j));
            }
        }
        return listPoint;
    }

    public ArrayList<Color> getAllColor(){
        ArrayList<Color> listColor = new ArrayList<>();

        for(int i=1; i<=width; i++){
            for(int j=1; j<=width; j++){
                listColor.add(grid.getSmallRegion(i,j).getCase(i,j));
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
        Color white = new Color(255, 255, 255);
        //rule 1
        grid.getSmallRegion(i,j).setCase(i, j, color);
        //rule 2
        for(int x=-1; x<=1; x++){
            for(int y=-1; y<=1; y++){
                if(i+x>0 && j+y>0 && i+x<=width && j+y<=width) {
                    Color getColor = grid.getSmallRegion(i+x,j+y).getCase(i + x, j + y);
                    if (!color.equals(getColor) && !getColor.equals(white)) {
                        grid.getSmallRegion(i+x,j+y).setCase(i + x, j + y, color);
                    }
                }
            }
        }
    }

    private void temerityRule(Integer i, Integer j, Color color) {
        Color white = new Color(255, 255, 255);
        //rule 1
        grid.getSmallRegion(i,j).setCase(i, j, color);
        //rule 2
        for(int x=-1; x<=1; x++){
            for(int y=-1; y<=1; y++){
                if(i+x>0 && j+y>0 && i+x<=width && j+y<=width) {
                    Color getColor = grid.getSmallRegion(i+x,j+y).getCase(i + x, j + y);

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
