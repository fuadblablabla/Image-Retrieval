/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageretrieval;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Fuad
 */
public class ConnectDatabase {

    private Connection connection = null;    

    public void loadDriver() {
        try {
            // The newInstance() call is a work around for some
            // broken Java implementations

            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            // handle the error
            ex.printStackTrace();
        }
    }

    public void connected() {
        loadDriver();
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/image_retrieval?"
                    + "user=root&password=root");
            System.out.println("CONNECTED!");
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public void closed() {
        try {
            connection.close();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public void insert(Warna warna, ArrayList<Integer> level) {
        try {
            connected();
            System.out.println("URL: " + warna.getUrl());
            String sql = "Insert into color_bin_r (level_0,level_16,level_32,level_48,level_64,level_80,level_96,level_112,level_128,level_144,level_160,level_176,level_192,level_208,level_224,level_240,level_256,url)"
                    + "values(" + warna.getColorBinR().get(level.get(0))
                    + "," + warna.getColorBinR().get(level.get(1))
                    + "," + warna.getColorBinR().get(level.get(2))
                    + "," + warna.getColorBinR().get(level.get(3))
                    + "," + warna.getColorBinR().get(level.get(4))
                    + "," + warna.getColorBinR().get(level.get(5))
                    + "," + warna.getColorBinR().get(level.get(6))
                    + "," + warna.getColorBinR().get(level.get(7))
                    + "," + warna.getColorBinR().get(level.get(8))
                    + "," + warna.getColorBinR().get(level.get(9))
                    + "," + warna.getColorBinR().get(level.get(10))
                    + "," + warna.getColorBinR().get(level.get(11))
                    + "," + warna.getColorBinR().get(level.get(12))
                    + "," + warna.getColorBinR().get(level.get(13))
                    + "," + warna.getColorBinR().get(level.get(14))
                    + "," + warna.getColorBinR().get(level.get(15))
                    + "," + warna.getColorBinR().get(level.get(16))
                    + ",'" + warna.getUrl() + "')";
            System.out.println(sql);
            connection.prepareStatement(sql).executeUpdate(sql);

            String sql2 = "Insert into color_bin_g (level_0,level_16,level_32,level_48,level_64,level_80,level_96,level_112,level_128,level_144,level_160,level_176,level_192,level_208,level_224,level_240,level_256,url)"
                    + "values(" + warna.getColorBinG().get(level.get(0))
                    + "," + warna.getColorBinG().get(level.get(1))
                    + "," + warna.getColorBinG().get(level.get(2))
                    + "," + warna.getColorBinG().get(level.get(3))
                    + "," + warna.getColorBinG().get(level.get(4))
                    + "," + warna.getColorBinG().get(level.get(5))
                    + "," + warna.getColorBinG().get(level.get(6))
                    + "," + warna.getColorBinG().get(level.get(7))
                    + "," + warna.getColorBinG().get(level.get(8))
                    + "," + warna.getColorBinG().get(level.get(9))
                    + "," + warna.getColorBinG().get(level.get(10))
                    + "," + warna.getColorBinG().get(level.get(11))
                    + "," + warna.getColorBinG().get(level.get(12))
                    + "," + warna.getColorBinG().get(level.get(13))
                    + "," + warna.getColorBinG().get(level.get(14))
                    + "," + warna.getColorBinG().get(level.get(15))
                    + "," + warna.getColorBinG().get(level.get(16))
                    + ",'" + warna.getUrl() + "')";
            System.out.println(sql2);
            connection.prepareStatement(sql2).executeUpdate(sql2);

            String sql3 = "Insert into color_bin_b (level_0,level_16,level_32,level_48,level_64,level_80,level_96,level_112,level_128,level_144,level_160,level_176,level_192,level_208,level_224,level_240,level_256,url)"
                    + "values(" + warna.getColorBinB().get(level.get(0))
                    + "," + warna.getColorBinB().get(level.get(1))
                    + "," + warna.getColorBinB().get(level.get(2))
                    + "," + warna.getColorBinB().get(level.get(3))
                    + "," + warna.getColorBinB().get(level.get(4))
                    + "," + warna.getColorBinB().get(level.get(5))
                    + "," + warna.getColorBinB().get(level.get(6))
                    + "," + warna.getColorBinB().get(level.get(7))
                    + "," + warna.getColorBinB().get(level.get(8))
                    + "," + warna.getColorBinB().get(level.get(9))
                    + "," + warna.getColorBinB().get(level.get(10))
                    + "," + warna.getColorBinB().get(level.get(11))
                    + "," + warna.getColorBinB().get(level.get(12))
                    + "," + warna.getColorBinB().get(level.get(13))
                    + "," + warna.getColorBinB().get(level.get(14))
                    + "," + warna.getColorBinB().get(level.get(15))
                    + "," + warna.getColorBinB().get(level.get(16))
                    + ",'" + warna.getUrl() + "')";
            System.out.println(sql3);
            connection.prepareStatement(sql3).executeUpdate(sql3);
            closed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Warna> listColorBinR(ArrayList<Integer> level) {
        try {
            connected();
            ObservableList<Warna> list = FXCollections.observableArrayList();
            String sql = "Select * from color_bin_r";
            ResultSet rs = connection.prepareStatement(sql).executeQuery(sql);
            while (rs.next()) {
                Warna warna = new Warna();
                HashMap<Integer, Double> tempList = new HashMap<>();
                warna.setId(rs.getInt(1));
                for (int i = 2; i <= 18; i++) {
                    tempList.put(level.get(i-2), rs.getDouble(i));
                }
                warna.setColorBinR(tempList);
                list.add(warna);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            closed();
        }
    }
}
