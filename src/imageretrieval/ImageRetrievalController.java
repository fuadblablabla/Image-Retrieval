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
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    Parent root;
    Image img;
    File file;
    static private Vector<Vector<Double>> state;
    static private Vector<Double> item;
    static private Vector<Double> ecludianDistance = new Vector<Double>();
    static private int maxLen = 3;
    static private int banyakData;
    static private Vector<Integer> metaData = new Vector<Integer>();
    ConnectDatabase connectDatabase;

    @FXML
    private Button button;
    @FXML
    private ImageView imageSource;
    @FXML
    private Button buttonProcess;
    @FXML
    private Button buttonSave;

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
        state = new Vector<Vector<Double>>();
        FileReader fr = null;
        try {
            fr = new FileReader("d:/KULIAH/LJ/KNN/src/knn/dataWarna.txt");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImageRetrievalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedReader br = new BufferedReader(fr);

        String[] strArr = null;
        String str = null;
        try {
            while ((str = br.readLine()) != null) {
                item = new Vector<Double>();
                strArr = str.split("\t");
                for (int i = 0; i < maxLen; i++) {
                    item.add(i, Double.valueOf(strArr[i]));
                }
                state.add(item);
            }
        } catch (IOException ex) {
            Logger.getLogger(ImageRetrievalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(state);
        banyakData = state.size();

        connectDatabase = new ConnectDatabase();        
    }

    private void emptyMetaData() {
        metaData = new Vector<Integer>();
        for (int i = 0; i < banyakData; i++) {
            metaData.add(0);
        }
        System.out.println("metaData " + metaData);
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
                Color color = reader.getColor(i, j);
                double r = color.getRed();
                double g = color.getGreen();
                double b = color.getBlue();
                double a = color.getOpacity();

                double[] T1 = new double[maxLen];
                T1[0] = r * 255;
                T1[1] = g * 255;
                T1[2] = b * 255;
                ecludianDistance = new Vector<Double>();

                //System.out.println(r*255);
                for (int k = 0; k < banyakData; k++) {
                    double[] T = new double[maxLen];
                    for (int l = 0; l < maxLen; l++) {
                        double number = state.get(k).get(l);
                        T[l] = number - T1[l];
                    }
                    double D = 0;
                    for (int l = 0; l < maxLen; l++) {
                        D = D + (T[l] * T[l]);
                    }
                    ecludianDistance.add(Math.sqrt(D));
                }

                double nearestColor = Collections.min(ecludianDistance);
                int nearestColorIndex = 100;
                for (int k = 0; k < banyakData; k++) {
                    if (ecludianDistance.get(k) == nearestColor) {
                        nearestColorIndex = k;
                        break;
                    }
                }
                metaData.set(nearestColorIndex, metaData.get(nearestColorIndex) + 1);

                Color newColor = Color.color(state.get(nearestColorIndex).get(0) / 255, state.get(nearestColorIndex).get(1) / 255, state.get(nearestColorIndex).get(2) / 255, a);
                writer.setColor(i, j, newColor);
            }
            imageSource.setImage(dest);
        }
        System.out.println("meta data: " + metaData);
        Warna warna = new Warna(metaData.get(0),
                metaData.get(1),
                metaData.get(2),
                metaData.get(3),
                metaData.get(4),
                metaData.get(5),
                metaData.get(6),
                metaData.get(7),
                metaData.get(8),
                metaData.get(9),
                metaData.get(10),
                metaData.get(11),
                metaData.get(12),
                metaData.get(13),
                metaData.get(14),
                metaData.get(15),
                file.toURI().toURL().toString());
        connectDatabase.insert(warna);
    }

    @FXML
    private void saveImage(ActionEvent event) {
    }

}
