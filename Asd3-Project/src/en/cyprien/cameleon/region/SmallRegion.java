package en.cyprien.cameleon.region;

import java.awt.Color;
import java.util.ArrayList;

public class SmallRegion {
   private ArrayList<Color> colorList;
   private Boolean aquise;

   SmallRegion(){
       this.colorList = new ArrayList<Color>(9);
       this.aquise = false;
       initWhite();
   }

    public ArrayList<Color> getColorList() {
        return colorList;
    }

    public void initWhite(){
       Color white = new Color(255, 255, 255);
       for(int i=0; i<9; i++){
           this.colorList.add(white);
       }
    }

    public void toStringColor(){
       for(Color x : colorList){
           System.out.print(x.getBlue());
       }
       System.out.println();
    }

    public void setCase(Integer i, Integer j, Color color) {
       i = (i%3 != 0? i%3:3)-1;
       j = (j%3 != 0? j%3:3)-1;
       this.colorList.set(i*3+j,color);
       isAquise();
    }

    public boolean sameColor(Color color){
       for(Color x: colorList){
           if(x != color)return false;
       }
       return true;
    }

    public Color getCase(Integer i, Integer j){
       i = (i%3 != 0? i%3:3)-1;
       j = (j%3 != 0? j%3:3)-1;
       return this.colorList.get(i*3+j);
    }

    public Integer getColorTemporaire(Integer i, Integer j) {

       Color color = getCase(i,j);

       if(color.equals(new Color(255, 0, 0)))return 1;
       if(color.equals(new Color(0, 0, 255)))return 2;
       return 0;
    }

    public void isAquise(){
        Color firstElm = colorList.get(0);
        if(!firstElm.equals(new Color(255,255,255))) {
            for (int i = 1; i < colorList.size(); i++) {
                if (firstElm.equals(colorList.get(i))) {
                    this.aquise = true;
                } else {
                    this.aquise = false;
                    return;
                }
            }
        }else{
            this.aquise = false;
        }
    }

    public boolean isFull(){
       return !colorList.contains(new Color(255,255,255));
    }

    public void setColorList(Color color){
       for(int i=0; i<9; i++){
           colorList.set(i, color);
       }
       this.aquise = true;
    }

    public Boolean getAquise() {
        return aquise;
    }
}
