package en.cyprien.cameleon.region;

import java.awt.*;

/**
 * @className : BigRegion
 * @role : represents all branches of the tree
 * @version :  1.0.0
 * @date : 29/11/2021
 * @author : GARNIER Cyprien
 */
public class BigRegion {
    private BigRegion NO, NE, SO, SE;
    private SmallRegion smallRegion;

    private Boolean isEmpty;
    private Boolean acquired;

    private Integer centerI,centerJ;//i = line, j = column
    private Integer colorRegion;//1 = red, 0 = white and -1 = blue

    /**
     * @role : Constructor of BigRegion.
     * @param length :
     * @param centerI : center i = line of the region
     * @param centerJ : center j = column of the region
     */
    public BigRegion(Integer length, Integer centerI, Integer centerJ){
        this.acquired = false;
        this.colorRegion = 0;
        this.centerI = centerI;
        this.centerJ = centerJ;
        if(length>=1){
            Integer width = (int) (3*Math.pow(2,length)/4);
            this.isEmpty = true;
            this.NO = new BigRegion(length-1, centerI-width, centerJ-width);
            this.NE = new BigRegion(length-1, centerI-width, centerJ+width);
            this.SO = new BigRegion(length-1, centerI+width, centerJ-width);
            this.SE = new BigRegion(length-1, centerI+width, centerJ+width);
        }else{
            this.isEmpty = false;
            this.smallRegion = new SmallRegion();
        }

    }


    //------------------- GETTER & SETTER -------------------//


    /**
     * @role : return the leaves of trees for an given box.
     * @complexity : O(log(n)).
     * @param i : line of the game board.
     * @param j : column of the game board.
     * @return : SmallRegion.
     */
    public SmallRegion getSmallRegion(Integer i, Integer j){
        if(isEmpty){
            if(i<=centerI) {
                if(j<=centerJ) {
                    return this.NO.getSmallRegion(i, j);
                }else {
                    return this.NE.getSmallRegion(i, j);

                }
            }else {
                if(j<=centerJ) {
                    return this.SO.getSmallRegion(i, j);
                }else {
                    return this.SE.getSmallRegion(i, j);
                }
            }
        }else{
            return this.smallRegion;
        }
    }


    /**
     * @role : color all the sub-trees with the given colors.
     * @complexity : O(n).
     * @param color : change everything to this color.
     */
    public void setAllSmallRegion(Color color){
        if(this.isEmpty){
            this.NO.setAllSmallRegion(color);
            this.NE.setAllSmallRegion(color);
            this.SO.setAllSmallRegion(color);
            this.SE.setAllSmallRegion(color);
        }else{
            this.smallRegion.setColorList(color);
        }
    }


    //------------------- END GETTER & SETTER -------------------//


    /**
     * @role : see if the sub-trees are fully colored.
     * @complexity : O(n)
     * @return : boolean
     */
    public boolean isGridIsFull(){
        Color white = new Color(255, 255, 255);
        boolean result = true;
        if(this.isEmpty){
            result = this.NO.isGridIsFull();
            result = result && this.NE.isGridIsFull();
            result = result && this.SO.isGridIsFull();
            result = result && this.SE.isGridIsFull();
        }else{
            result = !this.smallRegion.getColorList().contains(white);
        }
        return result;
    }


    /**
     * @role :
     * @complexity :
     * @param lastColorPlay :
     */
    public void isAcquired(Color lastColorPlay){
        if(!acquired) {
            if (isEmpty) {
                this.NO.isAcquired(lastColorPlay);
                this.NE.isAcquired(lastColorPlay);
                this.SO.isAcquired(lastColorPlay);
                this.SE.isAcquired(lastColorPlay);

                if (NO.acquired && NE.acquired && SO.acquired && SE.acquired) {

                    Integer colorInt = NO.colorRegion + NE.colorRegion + SO.colorRegion + SE.colorRegion;

                    if (colorInt < 0) {
                        setAllSmallRegion(Color.BLUE);
                    } else {
                        if (colorInt > 0) {
                            setAllSmallRegion(Color.RED);
                        } else {
                            setAllSmallRegion(lastColorPlay);
                        }
                    }
                    this.acquired = true;
                }
            } else {
                if (smallRegion.getAcquired()) {
                    //if the small region is red, the value is 1 else -1 for blue.
                    this.colorRegion = (smallRegion.getBoxColor(1, 1) == Color.RED ? 1 : -1);
                    this.acquired = true;
                }
            }
        }
    }


}
