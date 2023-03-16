package com.example.prolist;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SignUp implements DAO{
    @FXML
    Label mismatch = new Label();
    @FXML
    TextField set_username = new TextField();
    @FXML
    PasswordField set_password = new PasswordField();
    @FXML
    PasswordField confirm_password = new PasswordField();
    @FXML
    Button enter = new Button();
    @FXML
    public void register(){
        String user = set_username.getText().trim();
        String pass = set_password.getText().trim();
        String conf = confirm_password.getText().trim();
        if(user.isEmpty() || pass.isEmpty() || conf.isEmpty()){
            mismatch.setText("Incomplete Entries !");
        }
        else if(!pass.equals(conf)){
            mismatch.setText("Password did not match !");
            confirm_password.clear();
        }
        else{
            String sql = "INSERT INTO logged(username,password,db)VALUES(\""+user+"\",\""+pass+"\",\""+user+"\");";
            String create_database = "CREATE DATABASE "+user+";CREATE TABLE CREATE TABLE "+user+"(id INT UNSIGNED NOT NULL auto_increment, topic VARCHAR(100), PRIMARY KEY(id));";

            try {
                Class.forName(DRIVER);
                Connection con = DriverManager.getConnection(DB_LOGIN_URL, USER, PASS);

                Statement stmt = con.createStatement();
                stmt.executeUpdate(sql);
                stmt.executeUpdate(create_database);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 527, 302);
                Stage stage = (Stage)enter.getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();


            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }


    }



}
