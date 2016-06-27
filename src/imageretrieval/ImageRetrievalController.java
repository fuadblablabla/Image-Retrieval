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
import java.util.Comparator;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

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
    private TableColumn columnUrl;
    @FXML
    private TableView<Warna> tableImage;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        System.out.println("You clicked me!");
        label.setText("Now, Press Proses Button!");

        //root = FXMLLoader.load(getClass().getResource("ImageRetrievalFXML.fxml"));
        Stage stage = (Stage) button.getScene().getWindow();

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        file = chooser.showOpenDialog(stage);
        System.out.println(file.toURI().toURL().toString());
        img = new Image(file.toURI().toURL().toString());
        imageSource.setImage(img);

        //Scene scene = new Scene(root);
        //stage.setScene(scene);
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
        //columnUrl.setCellValueFactory(new PropertyValueFactory<Warna, String>("url"));                
        columnUrl.setCellValueFactory(new PropertyValueFactory<Warna, String>("url"));                
        columnUrl.setCellFactory(new Callback<TableColumn<Warna, String>, TableCell<Warna, String>>() {
            @Override
            public TableCell<Warna, String> call(TableColumn<Warna, String> p) {
                TableCell<Warna, String> cell = new TableCell<Warna, String>() {
                    ImageView imageview = new ImageView();
                    @Override
                    protected void updateItem(String t, boolean bln) {
                        if (t != null) {
                            HBox box = new HBox();
                            box.setSpacing(10);
                            VBox vbox = new VBox();
                                                        
                            imageview.setFitHeight(50);
                            imageview.setFitWidth(50);
                            imageview.setImage(new Image(t));
                            box.getChildren().addAll(imageview);
                            //SETTING ALL THE GRAPHICS COMPONENT FOR CELL
                            setGraphic(box);
                        }                        
                    }

                };
                System.out.println(cell.getIndex());              
                return cell;
            }

        });
        tableImage.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
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
        label.setText("Now you can either save or search from image that you choose, :*");
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
                        colorBinR.put(level.get(j), colorBinR.get(level.get(j)) / jumlahPixel);
                        break;
                    case 1:
                        colorBinG.put(level.get(j), colorBinG.get(level.get(j)) / jumlahPixel);
                        break;
                    case 2:
                        colorBinB.put(level.get(j), colorBinB.get(level.get(j)) / jumlahPixel);
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

        ObservableList<Warna> listColorBinG = FXCollections.observableArrayList();
        listColorBinG = connectDatabase.listColorBinG(level);
        System.out.println("list color bin g: " + listColorBinG.get(0).getColorBinG());

        ObservableList<Warna> listColorBinB = FXCollections.observableArrayList();
        listColorBinB = connectDatabase.listColorBinB(level);
        System.out.println("list color bin b: " + listColorBinB.get(0).getColorBinB());

        int sizeData = listColorBinB.size();

        ArrayList<Warna> warnaList = new ArrayList<>();

        for (int i = 0; i < sizeData; i++) {
            Warna tempWarna = new Warna(listColorBinR.get(i).getColorBinR(), listColorBinG.get(i).getColorBinG(), listColorBinB.get(i).getColorBinB(), listColorBinR.get(i).getUrl());
            warnaList.add(tempWarna);
            /*System.out.println("list color bin R: " + listColorBinR.get(i).getColorBinR());
            System.out.println("list color bin url: " + listColorBinR.get(i).getUrl());
            System.out.println("for");*/
        }
        System.out.println("warnalist awal: " + warnaList.get(0).getUrl());
        //for (int i = 0; i < 3; i++) {
        for (int j = 0; j < sizeData; j++) {
            double[] T = new double[level.size()];
            for (int k = 0; k < level.size(); k++) {
                T[k] = warnaList.get(j).getColorBinR().get(level.get(k)) - colorBinR.get(level.get(k));
            }
            double D = 0;
            for (int k = 0; k < level.size(); k++) {
                D = D + (T[k] * T[k]);
            }
            warnaList.get(j).setDistanceR(Math.sqrt(D));
        }
        //}
        Collections.sort(warnaList, new Comparator<Warna>() {
            @Override
            public int compare(Warna o1, Warna o2) {
                return o1.getDistanceR().compareTo(o2.getDistanceR());
            }

        });
        for (int i = 0; i < warnaList.size(); i++) {
            System.out.println("warna sort: " + warnaList.get(i).getUrl());
        }

        ObservableList<Warna> listOnTable = FXCollections.observableArrayList();
        for (int i = 0; i < warnaList.size(); i++) {
            /*Warna x = new Warna();
            x.setImageView(new Image(warnaList.get(i).getUrl()));
            listOnTable.add(x);
            System.out.println("x: " + x.image.toString());*/
            listOnTable.add(warnaList.get(i));
        }
        tableImage.setItems(listOnTable);
    }

}
