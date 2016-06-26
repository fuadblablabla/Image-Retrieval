/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageretrieval;

import java.io.BufferedReader;
import javafx.scene.paint.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Fuad
 */
public class ImageRetrievalController implements Initializable {

    @FXML
    private Label label;

    private Parent root;
    private Image img;
    private File file;    
    private ConnectDatabase connectDatabase;

    private HashMap<Integer, Double> colorBinR;
    private HashMap<Integer, Double> colorBinG;
    private HashMap<Integer, Double> colorBinB;
    private int jumlahPixel;
    private ArrayList<Integer> level = new ArrayList<Integer>();

    @FXML
    private Button button;
    @FXML
    private ImageView imageSource;
    @FXML
    private Button buttonProcess;
    @FXML
    private Button buttonSave;
    @FXML
    private Button buttonSearch;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        System.out.println("You clicked me!");
        label.setText("Hello World!");

        root = FXMLLoader.load(getClass().getResource("ImageRetrievalFXML.fxml"));

        Stage stage = new Stage();

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        file = chooser.showOpenDialog(stage);
        System.out.println(file.toURI().toURL().toString());
        img = new Image(file.toURI().toURL().toString());
        imageSource.setImage(img);

        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO                
        connectDatabase = new ConnectDatabase();
        level.add(0);
        level.add(16);
        level.add(32);
        level.add(48);
        level.add(64);
        level.add(80);
        level.add(96);
        level.add(112);
        level.add(128);
        level.add(144);
        level.add(160);
        level.add(176);
        level.add(192);
        level.add(208);
        level.add(224);
        level.add(240);
        level.add(256);
    }

    private void emptyMetaData() {
        colorBinR = new HashMap<Integer, Double>();
        colorBinG = new HashMap<Integer, Double>();
        colorBinB = new HashMap<Integer, Double>();
        jumlahPixel = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < level.size(); j++) {
                switch (i) {
                    case 0:
                        colorBinR.put(level.get(j), 0.0);
                        break;
                    case 1:
                        colorBinG.put(level.get(j), 0.0);
                        break;
                    case 2:
                        colorBinB.put(level.get(j), 0.0);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @FXML
    private void processImage(ActionEvent event) throws IOException {
        emptyMetaData();
        PixelReader reader = img.getPixelReader();
        int width = (int) img.getWidth();
        int height = (int) img.getHeight();

        double red = 0;
        double green = 0;
        double blue = 0;
        double alpha = 0;

        WritableImage dest = new WritableImage(width, height);
        PixelWriter writer = dest.getPixelWriter();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                jumlahPixel = jumlahPixel + 1;
                Color color = reader.getColor(i, j);
                double r = color.getRed();
                double g = color.getGreen();
                double b = color.getBlue();
                double a = color.getOpacity();

                int[] T1 = new int[3];
                T1[0] = (int) (r * 255);
                T1[1] = (int) (g * 255);
                T1[2] = (int) (b * 255);

                //int xg = (int) ((T1[0] + T1[1] + T1[2]) /3);
                //int xk = 16 * (int) (xg / 16);
                int newR = 16 * (int) (T1[0] / 16);
                int newG = 16 * (int) (T1[1] / 16);
                int newB = 16 * (int) (T1[2] / 16);

                /*System.out.println("r: " + T1[0] + " g: " + T1[1] + " b: " + T1[2]);
                System.out.println("xg " + xg);
                System.out.println("xk " + xk);*/
                //colorBin.put(xk, colorBin.get(xk)+1);
                colorBinR.put(newR, colorBinR.get(newR) + 1);
                colorBinG.put(newG, colorBinG.get(newG) + 1);
                colorBinB.put(newB, colorBinB.get(newB) + 1);

                /*Color newColor = Color.color(Double.valueOf(xk) / 255, Double.valueOf(xk) / 255, Double.valueOf(xk) / 255, a);*/
                Color newColor = Color.color(Double.valueOf(newR) / 255, Double.valueOf(newG) / 255, Double.valueOf(newB) / 255, a);
                writer.setColor(i, j, newColor);
            }
        }
        imageSource.setImage(dest);
        
        System.out.println("jumlah pixel: " + jumlahPixel);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < level.size(); j++) {
                switch (i) {
                    case 0:
                        colorBinR.put(level.get(j), colorBinR.get(level.get(j))/jumlahPixel);
                        break;
                    case 1:
                        colorBinG.put(level.get(j), colorBinG.get(level.get(j))/jumlahPixel);
                        break;
                    case 2:
                        colorBinB.put(level.get(j), colorBinB.get(level.get(j))/jumlahPixel);
                        break;
                    default:
                        break;
                }
            }
        }

        System.out.println("color bin red: " + colorBinR);
        System.out.println("color bin green: " + colorBinG);
        System.out.println("color bin blue: " + colorBinB);        
    }

    @FXML
    private void saveImage(ActionEvent event) throws MalformedURLException {
        Warna warna = new Warna(colorBinR, colorBinG, colorBinB, 
                file.toURI().toURL().toString());
        connectDatabase.insert(warna, level);
    }

    @FXML
    private void searchImage(ActionEvent event) {
        ObservableList<Warna> listColorBinR = FXCollections.observableArrayList();
        listColorBinR = connectDatabase.listColorBinR(level);
        System.out.println("list color bin r: " + listColorBinR.get(0).getColorBinR());
    }

}
