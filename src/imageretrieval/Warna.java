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
public class Warna implements Comparable<Warna>{
    private int id;
    private String url;
    private HashMap<Integer, Double> colorBinR;
    private HashMap<Integer, Double> colorBinG;
    private HashMap<Integer, Double> colorBinB;
    private Double distanceR, distanceG, distanceB;

    public Warna(HashMap<Integer, Double> colorBinR, HashMap<Integer, Double> colorBinG, HashMap<Integer, Double> colorBinB, String url) {
        this.colorBinR = colorBinR;
        this.colorBinG = colorBinG;
        this.colorBinB = colorBinB;
        this.url = url;
    }
    
    public Warna(){
        
    }

    public Double getDistanceR() {
        return distanceR;
    }

    public void setDistanceR(double distanceR) {
        this.distanceR = distanceR;
    }

    public Double getDistanceG() {
        return distanceG;
    }

    public void setDistanceG(double distanceG) {
        this.distanceG = distanceG;
    }

    public Double getDistanceB() {
        return distanceB;
    }

    public void setDistanceB(double distanceB) {
        this.distanceB = distanceB;
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

    @Override
    public int compareTo(Warna o) {
        int x = distanceR.compareTo(o.getDistanceR());
        return x;
    }
    
}
