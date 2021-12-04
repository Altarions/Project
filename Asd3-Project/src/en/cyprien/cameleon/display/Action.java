package en.cyprien.cameleon.display;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * @className : Action
 * @role : represents all button
 * @version :  1.0.0
 * @date : 29/11/2021
 * @author : GARNIER Cyprien
 */
public class Action implements ActionListener {

    JButton button;
    Integer i;
    Integer j;
    Display display;


    /**
     * @role : Constructor of Action..
     * @param display:
     * @param button: button.
     * @param i : line of the game board.
     * @param j : column of the game board.
     */
    public Action(Display display, JButton button, Integer i, Integer j){
        this.display = display;
        this.button = button;
        this.i = i;
        this.j = j;
    }


    /**
     * @role : supports user action when he clicks a button.
     * @param e : action event.
     */
    public void actionPerformed(ActionEvent e) {
        if(button.getBackground().equals(Color.WHITE)){
            display.play(i,j);
        }else{
            display.error();
        }

    }
}
