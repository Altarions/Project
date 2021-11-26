package en.cyprien.cameleon.display;

import en.cyprien.cameleon.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Action implements ActionListener {

    JButton button;
    Integer i,j;
    Display display;

    public Action(Display display, JButton button, Integer i, Integer j){
        this.display = display;
        this.button = button;
        this.i = i;
        this.j = j;
    }

    public void actionPerformed(ActionEvent e) {
        if(button.getBackground().equals(Color.WHITE)){
            display.setTabButton(i,j);
        }else{
            display.error();
        }

    }
}
