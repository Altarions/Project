package en.cyprien.cameleon.display;

import en.cyprien.cameleon.Game;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;



@SuppressWarnings("serial")
public class Display extends JFrame implements ActionListener {

    // Attributs
    private JButton[][] boutons;
    private Game game;

    private boolean clk;
    private JLabel ResultatJ1, ResultatJ2;
    private JPanel Res;
    private Container panel;
    private JPanel Jeu;


    // Constructeur
    public Display(Game game) {

        this.setTitle("Projet Caméléon");
        this.setSize(500, 500);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // Initialiser les composants.
        this.game = game;


        this.ResultatJ1 = new JLabel("Score player1:   " + this.game.scoreCalculation(Color.RED), JLabel.LEFT);
        this.ResultatJ2 = new JLabel("Score player2:   " + this.game.scoreCalculation(Color.BLUE), JLabel.LEFT);
        this.Res = new JPanel();
        this.Res.setLayout(new GridLayout());
        this.Res.add(ResultatJ1);
        this.Res.add(ResultatJ2);

        this.Jeu = new JPanel();
        this.Jeu.setLayout(new GridLayout(game.getWidth(), game.getWidth()));

        this.boutons = new JButton[game.getWidth()][game.getWidth()];

        for (int i = 0; i < game.getWidth(); i++) {
            for (int j = 0; j < game.getWidth(); j++) {

                this.boutons[i][j] = new JButton();
                this.boutons[i][j].setActionCommand(i + "," + j);
                this.boutons[i][j].setBackground(this.game.grid.getSmallRegion(i, j).getCase(i, j));
                this.Jeu.add(this.boutons[i][j]);
                this.boutons[i][j].addActionListener(this);
            }
        }

        this.panel = this.getContentPane();
        this.panel.setLayout(new BorderLayout());
        this.panel.add(this.Jeu, BorderLayout.CENTER);
        this.panel.add(this.Res, BorderLayout.SOUTH);

        this.clk = true;
        this.setVisible(true);
    }


    // Méthodes
    // Changer la couleur d'une case sur la graphique.
    public void setBackGround() {
        for (int i = 1; i <= this.game.getWidth(); i++) {
            for (int j = 1; j <= this.game.getWidth(); j++) {
                this.boutons[i][j].setBackground(this.game.grid.getSmallRegion(i, j).getCase(i, j));
            }
        }
    }



    /*
    // Appliquer le coloriage sur la gui selon les règles.
    public void Colorier_gui (int i, int j, char idJoueur) {

        // Appliquer le coloriage dans le tableau (back-end),
        if (this.p.Colorier(i, j, idJoueur)) { // Si vrai alors appliquer aussi sur la gui.

            // Changer le couleur de la case.
            this.colorier_case_gui(i, j);

            // Changer les couleur autour de la case séléctionnée.
            for (int indice1=-1; indice1<=1; indice1++) {
                for (int indice2=-1; indice2<=1; indice2++) {
                    if (this.p.estCaseExiste(i+indice1, j+indice2)) {

                        this.colorier_case_gui(i+indice1, j+indice2);
                    }
                }
            }
        }
        this.regler_plateau();
    }
    /*
    /**
     * role :
     *
    public void MessageGameOver() {

        if (this.p.CalculeScore() == (int) Math.pow(this.p.nb_cases, 2)) {
            String message = " ";
            if (this.p.J1.Score > this.p.J2.Score) {
                message = "GAME OVER: Player1 win!";
            } else if (this.p.J2.Score > this.p.J1.Score) {
                message = "GAME OVER: Player2 win!";
            } else {
                message = "GAME OVER: Player1 and Player2 are equal.";
            }
            System.out.println(message);
            JOptionPane.showInternalMessageDialog(this.panel, message);
        }
    }
    */

    /**************************************ACTION LISTENER*******************************************/
    public void actionPerformed(ActionEvent e) {
        /*
        // Traduire les commandes.
        String id_tmp = e.getActionCommand();
        String i_str = "", j_str = "";
        boolean b = false;

        // Chercher i et j dans la commande. (ex: commande = "i,j" -> resultats: "i" et "j").
        for (int indice=0; indice<id_tmp.length(); indice++) {
            // Chercher le virgule qui sépare i et j.
            if (id_tmp.charAt(indice) == ',') { b = true; continue; }
            // Construire i et j.
            if (!b) { i_str = i_str + id_tmp.charAt(indice); }
            else { j_str = j_str + id_tmp.charAt(indice); }
        }
        int i = Integer.parseInt(i_str);
        int j = Integer.parseInt(j_str);


        // Gérer le tour entre les joueurs.
        char idJoueur;
        if (this.clk) { idJoueur = this.p.J1.id; } else { idJoueur = this.p.J2.id; }


        // Contôle du clickage sur des cases déjà occupées.
        if (p.estCaseLibre(i, j)) {

            // Appliquer le coloriage.
            this.Colorier_gui(i, j, idJoueur); // Choisir le Jeu.

            // Mise à jour les scores.
            this.ResultatJ1.setText("Score player1:   " + this.p.J1.Score);
            this.ResultatJ2.setText("Score player2:   " + this.p.J2.Score);


            // Régler le tour.
            this.clk = !this.clk;
        }
        // Message: GAME OVER
        this.MessageGameOver();*/
    }
}
    /*******************************************MAIN*************************************************/

    /*public static void main(String[] args) {

        char caseVide = 'O';
        char idJ1 = 'R';
        char idJ2 = 'B';
        int n = 2;
        boolean initVide = true;

        Plateau p;

        //p = new Brave (n, initVide, caseVide, idJ1, idJ2);
        p = new Temeraire (n, initVide, caseVide, idJ1, idJ2);

        @SuppressWarnings("unused")
        Display gui = new Display(p);


    } // Fin main.

} // Fin GUI.
*/