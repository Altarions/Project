package en.cyprien.cameleon.region;

import java.awt.*;

public class BigRegion {
    private BigRegion NO, NE, SO, SE;
    private Boolean isEmpty;
    private SmallRegion smallRegion;
    private Integer centerI,centerJ;//i = lign, j = colone
    private Boolean aquire;
    private Integer colorRegion;//1 = red, 0 = white and -1 = blue

    public BigRegion(Integer lenght, Integer centerI, Integer centerJ){
        this.aquire = false;
        this.colorRegion = 0;
        this.centerI = centerI;
        this.centerJ = centerJ;
        if(lenght>=1){
            int width = (int) (3*Math.pow(2,lenght)/4);
            this.isEmpty = true;
            this.NO = new BigRegion(lenght-1, centerI-width, centerJ-width);
            this.NE = new BigRegion(lenght-1, centerI-width, centerJ+width);
            this.SO = new BigRegion(lenght-1, centerI+width, centerJ-width);
            this.SE = new BigRegion(lenght-1, centerI+width, centerJ+width);
        }else{
            this.isEmpty = false;
            this.smallRegion = new SmallRegion();
        }

    }
    

    public boolean isGridIsFull(){
        Color white = new Color(255, 255, 255);
        boolean result = true;
        if(this.isEmpty){
            result = result && this.NO.isGridIsFull();
            result = result && this.NE.isGridIsFull();
            result = result && this.SO.isGridIsFull();
            result = result && this.SE.isGridIsFull();
        }else{
            result = result && !this.smallRegion.getColorList().contains(white);
        }
        return result;
    }

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

    public void isAquire(Color lastColorPlay){
        if(!aquire) {
            if (isEmpty) {
                this.NO.isAquire(lastColorPlay);
                this.NE.isAquire(lastColorPlay);
                this.SO.isAquire(lastColorPlay);
                this.SE.isAquire(lastColorPlay);
                System.out.println(NO.aquire+" "+NE.aquire+" "+SO.aquire+" "+SE.aquire);
                if (NO.aquire && NE.aquire && SO.aquire && SE.aquire) {

                    Integer colorInt = NO.colorRegion + NE.colorRegion + SO.colorRegion + SE.colorRegion;
                    System.out.println(colorInt);
                    if (colorInt < 0) {
                        setAllSmallRegion(Color.BLUE);
                    } else {
                        if (colorInt > 0) {
                            setAllSmallRegion(Color.RED);
                        } else {
                            setAllSmallRegion(lastColorPlay);
                        }
                    }
                    this.aquire = true;
                }
            } else {
                if (smallRegion.getAquire()) {
                    //if the small region is red, the value is 1 else -1 for blue.
                    this.colorRegion = (smallRegion.getCase(1, 1) == Color.RED ? 1 : -1);
                    this.aquire = true;
                }
            }
        }
    }

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

    public boolean getIsAquire(){
        return this.aquire;
    }
    
}
