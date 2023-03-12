package com.example.prolist;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.awt.*;
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
        String user = set_username.getText();
        String pass = set_password.getText();
        String conf = confirm_password.getText();
        if(user ==null || pass == null || conf == null){
            mismatch.setText("Incomplete Entries !");
        }
        else if(!pass.equals(conf)){
            mismatch.setText("Password did not match !");
            confirm_password.clear();
        }
        else{
            String sql = "INSERT INTO logged(username,password)VALUES("+user+","+pass+");";
            try {
                Class.forName(DRIVER);
                Connection con = DriverManager.getConnection(DB_LOGIN_URL, USER, PASS);

                Statement stmt = con.createStatement();
                stmt.executeUpdate(sql);

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }



        }


    }



}
