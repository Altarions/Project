package en.cyprien.cameleon.region;

import java.awt.Color;
import java.util.ArrayList;

public class SmallRegion {
   private ArrayList<Color> colorList;
   private Boolean acquired;

   SmallRegion(){
       this.colorList = new ArrayList<>(9);
       this.acquired = false;
       initWhite();
   }

    public ArrayList<Color> getColorList() {
        return colorList;
    }

    public void initWhite(){
       Color white = Color.WHITE;
       for(int i=0; i<9; i++){
           this.colorList.add(white);
       }
    }

    public void setCase(Integer i, Integer j, Color color) {
       i = (i%3 != 0? i%3:3)-1;
       j = (j%3 != 0? j%3:3)-1;
       this.colorList.set(i*3+j,color);
       isAcquired();
    }

    public Color getCase(Integer i, Integer j){
       i = (i%3 != 0? i%3:3)-1;
       j = (j%3 != 0? j%3:3)-1;
       return this.colorList.get(i*3+j);
    }

    public Integer getColor(Integer i, Integer j) {

       Color color = getCase(i,j);

       if(color.equals(Color.RED))return 1;
       if(color.equals(Color.BLUE))return 2;
       return 0;
    }

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

    public void setColorList(Color color){
       for(int i=0; i<9; i++){
           colorList.set(i, color);
       }
       this.acquired = true;
    }

    public Boolean getAcquired() {
        return acquired;
    }
}
