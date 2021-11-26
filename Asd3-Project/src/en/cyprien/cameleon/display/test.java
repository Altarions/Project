package en.cyprien.cameleon.display;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Main {
    public static void main(String[] args) {
        FenetrePrincipaleVue frame = new FenetrePrincipaleVue();
    }
}

class FenetrePrincipaleVue extends JFrame {

    private JPanel pan = new JPanel();
    private JButton bouton1 = new JButton("Bouton 1");
    private JButton bouton2 = new JButton("Bouton 2");

    public FenetrePrincipaleVue() {

        this.setTitle("Accueil");
        this.setSize(400, 150);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        pan.add(bouton1);
        pan.add(bouton2);
        this.add(pan);

        this.setVisible(true);

        bouton1.addActionListener(new choixDuJeux("1", bouton1));
        bouton2.addActionListener(new choixDuJeux("2", bouton2));
    }

}

class choixDuJeux implements ActionListener {

    String choix;
    JButton button;
    boolean state = true;

    public choixDuJeux(String choix, JButton button) {
        this.choix = choix;
        this.button = button;

    }

    public void actionPerformed(ActionEvent e) {
        System.out.println(choix);
        change(button, !state);
    }

    public void change(JButton button2, boolean etat) {
        state = !state;
        System.out.println(etat);
        if (etat == true) {
            button2.setForeground(new Color(127, 180, 5));
            button2.setText("ON");
        } else {
            button2.setForeground(new Color(202, 37, 71));
            button2.setText("OFF");
        }

    }
}
