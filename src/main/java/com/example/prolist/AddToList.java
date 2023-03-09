package com.example.prolist;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AddToList implements DAO {
    private String create_db;
    private String USER = "root";
    private String PASS = "XZMeE2M3v-Jno9P";
    private ResultSet rs ;
    private TableView<AddToList> tabular;
    private ObservableList data;

    public void createTable(String db) {
        this.create_db = "CREATE DATABASE " + db+";"+"USE " + db + "; CREATE TABLE table(id INT UNSIGNED NOT NULL AUTO_INCREMENT, PRIMARY KEY (id));";
    }
    public  String add_col(String column, String data_type) {
        return "ALTER TABLE table; ADD " + column + " " + data_type;
    }

    public void run_Query(String query) throws ClassNotFoundException, SQLException {
        String sql =query+ "Select * from contact order by name;";

        try{Class.forName(DRIVER);
        Connection con = DriverManager.getConnection
                (DB_URL, USER, PASS);

            Statement stmt = con.createStatement();
             rs = stmt.executeQuery(sql);

            while (rs.next()) {
                ObservableList row = FXCollections.observableArrayList();

                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                    System.out.println(row);
                }

                data.add(row);

            }
            tabular.setItems(data);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
