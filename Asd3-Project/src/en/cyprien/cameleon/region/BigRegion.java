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
    private Integer width;
    private Integer colorRegion;//1 = red, 0 = white and -1 = blue

    /**
     * @role : Constructor of BigRegion.
     * @param length :
     * @param width : number of boxes on a length.
     * @param centerI : center i = line of the region.
     * @param centerJ : center j = column of the region.
     */
    public BigRegion(Integer length, Integer width, Integer centerI, Integer centerJ){
        this.acquired = false;
        this.colorRegion = 0;
        this.centerI = centerI;
        this.centerJ = centerJ;
        this.width = width;

        if(length>=1){

            this.isEmpty = true;
            this.NO = new BigRegion(length-1, width/2,centerI-width/4, centerJ-width/4);
            this.NE = new BigRegion(length-1, width/2,centerI-width/4, centerJ+width/4);
            this.SO = new BigRegion(length-1, width/2, centerI+width/4, centerJ-width/4);
            this.SE = new BigRegion(length-1, width/2, centerI+width/4, centerJ+width/4);
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
     * @complexity : O(n log n)
     * @return : boolean
     */
    public boolean isGridIsFull(){
        boolean result = true;
        if(!this.acquired) {
            if (this.isEmpty) {
                result = this.NO.isGridIsFull();
                result = result && this.NE.isGridIsFull();
                result = result && this.SO.isGridIsFull();
                result = result && this.SE.isGridIsFull();
            } else {
                result = (smallRegion.nbBox(Color.WHITE) == 0);
            }
        }
        return result;
    }


    /**
     * @role : calculate the number of box acquired for a given color.
     * @complexity : O(n log n).
     * @param color : the score for that color.
     * @return : Integer.
     */
    public Integer scoreCalculation(Color color){
        
        Integer score = 0;

        if(!this.acquired){
            if(this.isEmpty) {
                score += this.NO.scoreCalculation(color);
                score += this.NE.scoreCalculation(color);
                score += this.SO.scoreCalculation(color);
                score += this.SE.scoreCalculation(color);
            }else{
                score += smallRegion.nbBox(color);
            }
        }else{
            Integer colorValue = color.equals(Color.RED) ? 1: (color.equals(Color.BLUE)? -1 : 0);

            if(colorRegion == colorValue)score += width*width;

        }

        return score;
    }


    /**
     * @role :verify region acquisition, continues as long as regions are acquired.
     * @complexity : O(n log n).
     * @param lastColorPlay : last color play.
     */
    public void isAcquired(Color lastColorPlay){
        if(!acquired) {
            if (isEmpty) {
                this.NO.isAcquired(lastColorPlay);
                this.NE.isAcquired(lastColorPlay);
                this.SO.isAcquired(lastColorPlay);
                this.SE.isAcquired(lastColorPlay);

                if (NO.acquired && NE.acquired && SO.acquired && SE.acquired) {
                    subTreeFullyAcquired(lastColorPlay);
                }
            } else {
                if (smallRegion.getAcquired()) {
                    //if the small region is red, the value is 1 else -1 for blue.
                    this.colorRegion = (smallRegion.getBoxColor(1, 1).equals(Color.RED) ? 1 : -1);
                    this.acquired = true;
                    removeSubTree();
                }
            }
        }
    }


    /**
     * @role : takes care of the acquisition of this region when all the sub-trees are acquired.
     * @complexity : O(1).
     * @param lastColorPlay : last color play.
     */
    private void subTreeFullyAcquired(Color lastColorPlay){

        Integer colorInt = NO.colorRegion + NE.colorRegion + SO.colorRegion + SE.colorRegion;

        //if the majority of subTree is BLUE
        if (colorInt < 0) {

            setAllSmallRegion(Color.BLUE);
            this.colorRegion = -1;

        } else {
            //if the majority of subTree is RED
            if (colorInt > 0) {

                setAllSmallRegion(Color.RED);
                this.colorRegion = 1;

            } else {
                //equality
                //if colorInt == 0 the region take the last color play
                setAllSmallRegion(lastColorPlay);
                this.colorRegion = (lastColorPlay.equals(Color.RED) ? 1: -1);
            }
        }
        this.acquired = true;
    }

    /**
     * @role : remove all subTree.
     * @complexity : O(1).
     */
    private void removeSubTree() {
        this.NO = null;
        this.NE = null;
        this.SO = null;
        this.SE = null;
    }

}
