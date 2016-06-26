/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageretrieval;

import java.util.HashMap;

/**
 *
 * @author Fuad
 */
public class Warna {
    private int id;
    private String url;
    private HashMap<Integer, Double> colorBinR;
    private HashMap<Integer, Double> colorBinG;
    private HashMap<Integer, Double> colorBinB;

    public Warna(HashMap<Integer, Double> colorBinR, HashMap<Integer, Double> colorBinG, HashMap<Integer, Double> colorBinB, String url) {
        this.colorBinR = colorBinR;
        this.colorBinG = colorBinG;
        this.colorBinB = colorBinB;
        this.url = url;
    }
    
    public Warna(){
        
    }

    public HashMap<Integer, Double> getColorBinR() {
        return colorBinR;
    }

    public HashMap<Integer, Double> getColorBinG() {
        return colorBinG;
    }

    public HashMap<Integer, Double> getColorBinB() {
        return colorBinB;
    }
        
    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setColorBinR(HashMap<Integer, Double> colorBinR) {
        this.colorBinR = colorBinR;
    }

    public void setColorBinG(HashMap<Integer, Double> colorBinG) {
        this.colorBinG = colorBinG;
    }

    public void setColorBinB(HashMap<Integer, Double> colorBinB) {
        this.colorBinB = colorBinB;
    }
    
}
