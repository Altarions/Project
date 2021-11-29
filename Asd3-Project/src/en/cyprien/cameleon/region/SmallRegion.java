package en.cyprien.cameleon.region;

import java.awt.Color;
import java.util.ArrayList;
/**
 * @className : SmallRegion
 * @role : represent the leaves of the trees
 * @version :  1.0.0
 * @date : 29/11/2021
 * @author : GARNIER Cyprien
 */
public class SmallRegion {
    private ArrayList<Color> colorList;
    private Boolean acquired;

    /**
     * @role : constructor of smallRegion class.
     */
    SmallRegion(){
        this.colorList = new ArrayList<>(9);
        this.acquired = false;
        initWhite();
    }


    //------------------- GETTER & SETTER -------------------//


    public ArrayList<Color> getColorList() {
        return colorList;
    }
    public Boolean getAcquired() {
        return acquired;
    }


    /**
     * @role : return the color of an given box
     * @complexity : O(1).
     * @param i : line of the game board.
     * @param j : column of the game board.
     * @return : Color.
     */
    public Color getBoxColor(Integer i, Integer j){
        i = (i%3 != 0? i%3:3)-1;
        j = (j%3 != 0? j%3:3)-1;
        return this.colorList.get(i*3+j);
    }


    /**
     * @role : return an integer designating the owner of an given box,
     *       1 for Blue, 2 for Red else 0 for White.
     * @complexity : O(1).
     * @param i : line of the game board.
     * @param j : column of the game board.
     * @return : Integer.
     */
    public Integer getBoxOwner(Integer i, Integer j) {

        Color color = getBoxColor(i,j);

        if(color.equals(Color.RED))return 1;
        if(color.equals(Color.BLUE))return 2;
        return 0;
    }


    /**
     * @role : change the color of an given box and call the function isAcquired().
     * @complexity : O(1).
     * @param i : line of the game board.
     * @param j : column of the game board.
     * @param color : player color.
     */
    public void setCase(Integer i, Integer j, Color color) {
        i = (i%3 != 0? i%3:3)-1;
        j = (j%3 != 0? j%3:3)-1;
        this.colorList.set(i*3+j,color);
        isAcquired();
    }


    /**
     * @role : change all the boxes in the given color.
     * @complexity : O(n).
     * @param color : the color you want to apply.
     */
    public void setColorList(Color color){
        for(int i=0; i<9; i++){
            colorList.set(i, color);
        }
        this.acquired = true;
    }


    //------------------- END GETTER & SETTER -------------------//


    /**
     * @role : initialize the boxes of the table, in white.
     * @complexity : O(n).
     */
    public void initWhite(){
        Color white = Color.WHITE;
        for(int i=0; i<9; i++){
            this.colorList.add(white);
        }
    }


    /**
     * @role : check if all the elements of the list are the same color and that it is not white.
     * @complexity : O(n).
     */
    public void isAcquired(){
        Color firstElm = colorList.get(0);
        if(!firstElm.equals(Color.WHITE)) {
            for (int i = 1; i < colorList.size(); i++) {
                if (firstElm.equals(colorList.get(i))) {
                    this.acquired = true;
                } else {
                    this.acquired = false;
                    return;
                }
            }
        }else{
            this.acquired = false;
        }
    }


    public boolean isFull(){
       return !colorList.contains(Color.WHITE);
    }


}
