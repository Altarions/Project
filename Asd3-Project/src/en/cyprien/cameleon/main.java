package en.cyprien.cameleon;

import en.cyprien.cameleon.display.Display;

public class main {
    public static void main( String[] args ) {

        Game game = new Game();
        game.init();
        Display display = new Display(game);

    }
}




