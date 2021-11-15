package en.cyprien.cameleon.display;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Display {
    public Display() {

        final JFrame frame = new JFrame();
        frame.setLayout(new GridLayout(2, 2));


        //*****************************************
        //             Delta Interface
        //*****************************************
        final JPanel imagesDelta = new JPanel();
        imagesDelta.setLayout(new GridLayout(0, 1));
        final JScrollPane scrollImagesDelta = new JScrollPane(imagesDelta);
        scrollImagesDelta.getVerticalScrollBar().setUnitIncrement(16);

        JPanel interfaceDelta = new JPanel();
        interfaceDelta.setLayout(new GridLayout(3, 3));


        final JSlider sliderDelta = new JSlider(0, 192);

        final JTextArea textAreaDelta = new JTextArea(String.valueOf(sliderDelta.getValue()));

        // Action on slider Delta move
        sliderDelta.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                textAreaDelta.setText(String.valueOf(sliderDelta.getValue()));
            }
        });




        JButton buttonDelta1 = new JButton("1");
        JButton buttonDelta2 = new JButton("2");
        JButton buttonDelta3 = new JButton("3");
        JButton buttonDelta4 = new JButton("4");
        JButton buttonDelta5 = new JButton("5");
        JButton buttonDelta6 = new JButton("6");
        JButton buttonDelta7 = new JButton("7");
        JButton buttonDelta8 = new JButton("8");
        JButton buttonDelta9 = new JButton("9");
        buttonDelta1.setBackground(Color.RED);
        // Action on Button Delta press
        buttonDelta1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try  {

                } catch (Exception ex) {
                    System.out.println(ex);
                }

                frame.setVisible(true);
                System.out.println("------------------------------");
            }
        });
        interfaceDelta.add(buttonDelta1);
        interfaceDelta.add(buttonDelta2);
        interfaceDelta.add(buttonDelta3);
        interfaceDelta.add(buttonDelta4);
        interfaceDelta.add(buttonDelta5);
        interfaceDelta.add(buttonDelta6);
        interfaceDelta.add(buttonDelta7);
        interfaceDelta.add(buttonDelta8);
        interfaceDelta.add(buttonDelta9);



        //*****************************************
        //             Phi Interface
        //*****************************************
        final JPanel imagesPhi = new JPanel();
        imagesPhi.setLayout(new GridLayout(0, 1));
        final JScrollPane scrollImagesPhi = new JScrollPane(imagesPhi);
        scrollImagesPhi.getVerticalScrollBar().setUnitIncrement(16);
        JPanel interfacePhi = new JPanel();
        interfacePhi.setLayout(new GridLayout(2, 2));




        JButton buttonPhi = new JButton("Compress Phi");
        // Action on Button Phi press
        buttonPhi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    } catch (Exception ex) {
                    System.out.println(ex);
                }

                frame.setVisible(true);
                System.out.println("------------------------------");
            }
        });

        frame.add(interfaceDelta);
        frame.add(interfacePhi);
        frame.add(scrollImagesDelta);
        frame.add(scrollImagesPhi);
        frame.setSize(1000, 700);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}
