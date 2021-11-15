package en.cyprien.cameleon.display;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowClose extends WindowAdapter {

    public void windowClosing(WindowEvent e) {
        System.exit(0);
    } 
}
