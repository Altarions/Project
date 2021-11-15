package en.cyprien.cameleon.region;

import java.awt.*;

public class BigRegion {
    private BigRegion NO, NE, SO, SE;
    private Boolean isEmpty;
    private SmallRegion smallRegion;
    private Integer centerI,centerJ;//i = lign, j = colone
    private Boolean aquise;

    public BigRegion(Integer lenght, Integer centerI, Integer centerJ){
        this.aquise = false;
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

    public void setCase(Integer i, Integer j, Color color){
        if(isEmpty){
            if(i<=centerI) {
                if(j<=centerJ) {
                    this.NO.setCase(i, j, color);
                }else {
                    this.NE.setCase(i, j, color);

                }
            }else {
                if(j<=centerJ) {
                    this.SO.setCase(i, j, color);
                }else {
                    this.SE.setCase(i, j, color);
                }
            }
        }else{
            this.smallRegion.setCase(i,j, color);
        }
    }

    public Integer getCaseSmallRegion(Integer i, Integer j) {
        if(this.isEmpty){

            if(i<=centerI) {
                if(j<=centerJ) {
                    return this.NO.getCaseSmallRegion(i, j);
                }else {
                    return this.NE.getCaseSmallRegion(i, j);

                }
            }else {
                if(j<=centerJ) {
                    return this.SO.getCaseSmallRegion(i, j);
                }else {
                    return this.SE.getCaseSmallRegion(i, j);
                }
            }
        }
        return this.smallRegion.getColorTemporaire(i,j);
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

    public Color getCase(Integer i, Integer j){
        if(this.isEmpty){
            if(i<=centerI) {
                if(j<=centerJ) {
                    return this.NO.getCase(i, j);
                }else {
                    return this.NE.getCase(i, j);

                }
            }else {
                if(j<=centerJ) {
                    return this.SO.getCase(i, j);
                }else {
                    return this.SE.getCase(i, j);
                }
            }
        }

        return this.smallRegion.getCase(i,j);

    }

    public Boolean smallRegionIsAquise(Integer i, Integer j){
        if(this.isEmpty){
            if(i<=centerI) {
                if(j<=centerJ) {
                    return this.NO.smallRegionIsAquise(i, j);
                }else {
                    return this.NE.smallRegionIsAquise(i, j);

                }
            }else {
                if(j<=centerJ) {
                    return this.SO.smallRegionIsAquise(i, j);
                }else {
                    return this.SE.smallRegionIsAquise(i, j);
                }
            }
        }

        return this.smallRegion.getAquise();
    }

    public Boolean smallRegionIsFull(Integer i, Integer j){
        if(this.isEmpty){
            if(i<=centerI) {
                if(j<=centerJ) {
                    return this.NO.smallRegionIsFull(i, j);
                }else {
                    return this.NE.smallRegionIsFull(i, j);

                }
            }else {
                if(j<=centerJ) {
                    return this.SO.smallRegionIsFull(i, j);
                }else {
                    return this.SE.smallRegionIsFull(i, j);
                }
            }
        }

        return this.smallRegion.isFull();
    }

    public void setColorList(Integer i, Integer j, Color color){
        if(isEmpty){
            if(i<=centerI) {
                if(j<=centerJ) {
                    this.NO.setColorList(i, j, color);
                }else {
                    this.NE.setColorList(i, j, color);

                }
            }else {
                if(j<=centerJ) {
                    this.SO.setColorList(i, j, color);
                }else {
                    this.SE.setColorList(i, j, color);
                }
            }
        }else{
            this.smallRegion.setColorList(color);
        }
    }
    
}
