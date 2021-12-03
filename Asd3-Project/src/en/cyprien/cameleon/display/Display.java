package en.cyprien.cameleon.display;

import en.cyprien.cameleon.Game;
import en.cyprien.cameleon.Read;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;

import static java.lang.System.exit;


public class Display extends JFrame {

    private ArrayList<JButton> tabButton;
    private Game game;

    private JLabel player;
    private JLabel robot;
    private JPanel res, panel;
    private Container container;


    public Display() {

        this.setTitle("Projet Caméléon");
        this.setSize(500, 500);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.container = this.getContentPane();

        this.game = initGame();
        initDisplay();
        this.game.basicDisplay();

    }

    private Game initGame() {
        JPanel initialisation= new JPanel();
        Boolean gameType = gameType(initialisation);
        Boolean strategy = (gameType || strategy(initialisation));
        String gridEmpty = JOptionPane.showInputDialog(initialisation, "Voulez vous utiliser un plateau vide? (true/false)");

        if(gridEmpty.equalsIgnoreCase("true")){
            String length = JOptionPane.showInputDialog(initialisation, "Avec quelle taille de plateau voulez vous jouer?");
            return new Game(Integer.parseInt(length), gameType, strategy);
        }else{
            String nameFile = JOptionPane.showInputDialog(initialisation, "Rentrez le nom du fichier sans l'extension.");
            Read file = new Read(nameFile+".txt");
            return new Game(file.getWidthFile(), file.getGridFile(), gameType, strategy);
        }

    }
    private boolean gameType(JPanel initialisation){
        String gameType = JOptionPane.showInputDialog(initialisation, "Voulez vous jouer avec les regles Brave Caméléon ? Sinon les regles Téméraires seront appliquées!");

        return gameType.equalsIgnoreCase("true");
    }

    private boolean strategy(JPanel initialisation){
        String greedyStrategy = JOptionPane.showInputDialog(initialisation, "Voulez vous que l'Ia est une stratégie gloutonne? Sinon elle jouera la strégie forte! (true/false)");

        return greedyStrategy.equalsIgnoreCase("true");
    }

    private void initDisplay(){

        this.player = new JLabel("Score player:   " + this.game.scoreCalculation(Color.RED), JLabel.CENTER);
        this.robot = new JLabel("Score ia:   " + this.game.scoreCalculation(Color.BLUE), JLabel.CENTER);
        this.player.setForeground(Color.RED);
        this.robot.setForeground(Color.BLUE);
        this.res = new JPanel();
        this.res.setLayout(new GridLayout());
        this.res.add(player);
        this.res.add(robot);

        this.panel = new JPanel();
        this.panel.setLayout(new GridLayout(game.getWidth(), game.getWidth()));

        this.tabButton = new ArrayList<>(game.getWidth()*game.getWidth());

        for (int i = 1; i <= game.getWidth(); i++) {
            for (int j = 1; j <= game.getWidth(); j++) {
                JButton button = new JButton();
                button.setOpaque(true);
                button.setBackground(this.game.board.getSmallRegion(i, j).getBoxColor(i, j));
                button.addActionListener(new Action(this, button, i, j));
                this.tabButton.add(button);
                this.panel.add(button);
            }
        }

        this.container.setLayout(new BorderLayout());
        this.container.add(this.panel, BorderLayout.CENTER);
        this.container.add(this.res, BorderLayout.NORTH);

        this.setVisible(true);
    }


    public void play(Integer i, Integer j){

        Boolean win = game.play(i, j, game.getPlayer1().color);
        setTabButton();
        if (win)win();

        //IA play
        Point player2 = game.getPlayer2().play();
        win = game.play((int)player2.getX(), (int)player2.getY(), game.getPlayer2().color);
        setTabButton();
        if (win)win();
    }


    public void setTabButton() {

        for (int i = 0; i < game.getWidth(); i++) {
            for (int j = 0; j < game.getWidth(); j++) {

                tabButton.get(i*game.getWidth()+j).setBackground(game.board.getSmallRegion(i+1, j+1).getBoxColor(i+1, j+1));
            }
        }
        this.player.setText("Score player:   " + this.game.scoreCalculation(Color.RED));
        this.robot.setText("Score ia:   " + this.game.scoreCalculation(Color.BLUE));

    }



    void error() {
        JOptionPane.showMessageDialog(this.panel, "Vous ne pouvez pas jouer sur cette case !");
    }

    private void win(){
        JOptionPane.showMessageDialog(this.panel, game.win());
        exit(0);
    }

}
