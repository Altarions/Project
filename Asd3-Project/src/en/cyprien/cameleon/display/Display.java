package en.cyprien.cameleon.display;

import en.cyprien.cameleon.Game;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import static java.lang.System.exit;


public class Display extends JFrame {

    // Attributs
    private ArrayList<JButton> tabButton;
    private Game game;

    private JLabel player, robot;
    private JPanel Res;
    private Container container;
    private JPanel panel;


    // Constructeur
    public Display(Game game) {

        this.setTitle("Projet Caméléon");
        this.setSize(500, 500);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.game = game;

        this.player = new JLabel("Score player:   " + this.game.scoreCalculation(Color.RED), JLabel.LEFT);
        this.robot = new JLabel("Score ia:   " + this.game.scoreCalculation(Color.BLUE), JLabel.LEFT);
        this.Res = new JPanel();
        this.Res.setLayout(new GridLayout());
        this.Res.add(player);
        this.Res.add(robot);

        this.panel = new JPanel();
        this.panel.setLayout(new GridLayout(game.getWidth(), game.getWidth()));

        this.tabButton = new ArrayList<>(game.getWidth()*game.getWidth());

        for (int i = 1; i <= game.getWidth(); i++) {
            for (int j = 1; j <= game.getWidth(); j++) {
                JButton button = new JButton();
                button.setOpaque(true);
                button.setBackground(this.game.grid.getSmallRegion(i, j).getCase(i, j));
                button.addActionListener(new Action(this, button, i, j));
                this.tabButton.add(button);
                this.panel.add(button);
            }
        }
        this.container = this.getContentPane();
        this.container.setLayout(new BorderLayout());
        this.container.add(this.panel, BorderLayout.CENTER);
        this.container.add(this.Res, BorderLayout.SOUTH);

        this.setVisible(true);

    }


    public void setTabButton(Integer posI, Integer posJ) {
        Boolean win = game.play(posI, posJ);

        for (int i = 0; i < game.getWidth(); i++) {
            for (int j = 0; j < game.getWidth(); j++) {

                tabButton.get(i*game.getWidth()+j).setBackground(game.grid.getSmallRegion(i+1, j+1).getCase(i+1, j+1));
            }
        }
        this.player.setText("Score player:   " + this.game.scoreCalculation(Color.RED));
        this.robot.setText("Score ia:   " + this.game.scoreCalculation(Color.BLUE));

        if (win) {
            JOptionPane.showMessageDialog(this.panel, game.win());
            exit(0);
        }


    }

    public void error() {
        JOptionPane.showMessageDialog(this.panel, "Vous ne pouvez pas jouer sur cette case !");
    }
}
