package com.example.prolist;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.*;

public class Login extends Application implements DAO{
    @FXML
    Label prompt = new Label();
    @FXML
    Button reenter = new Button();
    @FXML
    Button sign_up = new Button();
    @FXML
    TextField username = new TextField("");
    @FXML
    PasswordField password = new PasswordField();
    private Scene scene;
    private String user;
    private String pass;

    @FXML
    public void authenticate(){
        user = username.getText();
        pass = password.getText();

        if(user==null || pass==null){
            prompt.setText("Incomplete Credentials !");
        }
        else{
            String sql ="SELECT * FROM logged WHERE username ="+user+" AND password ="+pass+";";

            try{Class.forName(DRIVER);
                Connection con = DriverManager.getConnection(DB_LOGIN_URL, USER, PASS);

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                if(rs.next()){



                }
                else{
                    prompt.setText("Invalid Credentials !");
                    username.clear();
                    password.clear();
                }

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }


        }


    }
    @FXML
    public void on_sign_up() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("signup.fxml"));
        scene = new Scene(fxmlLoader.load(), 532, 304);
        Stage stage = (Stage)sign_up.getScene().getWindow();
        stage.setScene(scene);
        stage.show();


    }
    public void enter_dashboard() throws IOException, SQLException {
        Stage stage = (Stage)reenter.getScene().getWindow();
        String sql ="SELECT * FROM logged WHERE username ="+user+" AND password ="+pass+";";
        ResultSet rs;
        try{Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_LOGIN_URL, USER, PASS);

            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            }catch(SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
            }
        String database = rs.getString("db");
        Dashboard dash = new Dashboard(stage,database);

    }


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        scene = new Scene(fxmlLoader.load(), 527, 302);
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String args[]){
        launch();
    }
}
