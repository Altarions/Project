package en.cyprien.cameleon;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class Read {
    private File file;

    public Read(String nameFile){
        String path = System.getProperty("user.dir");
        this.file = new File(path+"/src/en/cyprien/cameleon/"+nameFile);
        if (!file.exists()) {
            System.out.print("Le fichier n'existe pas");
            return;
        }
    }

    public Integer getWidthFile(){
        Integer width = 0;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String data = bufferedReader.readLine();
            width = Integer.parseInt(data);
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return width;
    }

    public ArrayList<Color> getGridFile(){
        ArrayList<Color> grid = new ArrayList<Color>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String data;

            while ((data = bufferedReader.readLine()) != null) {
                for(int i=0; i<data.length(); i++){
                    char crt = data.charAt(i);
                    if(crt == 'R')grid.add(new Color(255,0,0));
                    if(crt == 'B')grid.add(new Color(0,0,255));
                    if(crt == 'A')grid.add(new Color(255,255,255));

                }
            }


            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return grid;
    }

}
