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

    private JLabel player, robot;
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


    }

    private Game initGame() {
        JPanel initialisation= new JPanel();
        String gridEmpty = JOptionPane.showInputDialog(initialisation, "Voulez vous utiliser un plateau vide ? (true/false)");

        if(gridEmpty.equals("true")){
            String length = JOptionPane.showInputDialog(initialisation, "Avec quelle taille de plateau voulez vous jouer ?");
            return new Game(Integer.parseInt(length), gameType(initialisation));
        }else{
            String nameFile = JOptionPane.showInputDialog(initialisation, "Rentrez le nom du fichier sans l'extension .");
            Read file = new Read(nameFile+".txt");
            return new Game(file.getWidthFile(), file.getGridFile(), gameType(initialisation));
        }

    }
    private boolean gameType(JPanel initialisation){
        String gameType = JOptionPane.showInputDialog(initialisation, "Voulez vous jouer avec les regles Brave Caméléon ? Sinon les regles Téméraires seront appliquées!");

        return gameType.equals("true");
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
                button.setBackground(this.game.grid.getSmallRegion(i, j).getBoxColor(i, j));
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


    public void setTabButton(Integer posI, Integer posJ) {
        Boolean win = game.play(posI, posJ);

        for (int i = 0; i < game.getWidth(); i++) {
            for (int j = 0; j < game.getWidth(); j++) {

                tabButton.get(i*game.getWidth()+j).setBackground(game.grid.getSmallRegion(i+1, j+1).getBoxColor(i+1, j+1));
            }
        }
        this.player.setText("Score player:   " + this.game.scoreCalculation(Color.RED));
        this.robot.setText("Score ia:   " + this.game.scoreCalculation(Color.BLUE));

        if (win)win();


    }

    void error() {
        JOptionPane.showMessageDialog(this.panel, "Vous ne pouvez pas jouer sur cette case !");
    }

    private void win(){
        JOptionPane.showMessageDialog(this.panel, game.win());
        exit(0);
    }

}
