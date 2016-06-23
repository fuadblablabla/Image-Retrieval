/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageretrieval;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Fuad
 */
public class ConnectDatabase {

    private Connection connection = null;
    private Statement statement = null;

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

    public void insert(Warna warna) {
        try {
            connected();
            System.out.println("URL: " + warna.getUrl());
            String sql = "Insert into warna (black,white,red,lime,blue,yellow,cyan,magenta,silver,gray,maroon,olive,green,purple,teal,navy,url)values(" + warna.getC1() + "," 
                    + warna.getC2() + "," 
                    + warna.getC3() + "," 
                    + warna.getC4() + "," 
                    + warna.getC5() + "," 
                    + warna.getC6() + "," 
                    + warna.getC7() + "," 
                    + warna.getC8() + "," 
                    + warna.getC9() + "," 
                    + warna.getC10() + "," 
                    + warna.getC11() + "," 
                    + warna.getC12() + "," 
                    + warna.getC13() + "," 
                    + warna.getC14() + "," 
                    + warna.getC15() + "," 
                    + warna.getC16() + ", '" 
                    + warna.getUrl() + "')";
            //statement.executeQuery(sql);
            System.out.println(sql);
            connection.prepareStatement(sql).executeUpdate(sql);
            closed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
