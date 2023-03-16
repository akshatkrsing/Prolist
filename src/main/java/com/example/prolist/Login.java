package com.example.prolist;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    protected static String user;
    private String pass;
    protected static String database;
    protected static ListView<String> user_list = new ListView<>();

    @FXML
    public void authenticate(){
        user = username.getText().trim();
        pass = password.getText().trim();


        if(user.isEmpty()|| pass.isEmpty()){
            prompt.setText("Incomplete Credentials !");
        }
        else{
            String sql ="SELECT * FROM logged WHERE username =\""+user+"\" AND password =\""+pass+"\";";

            try{Class.forName(DRIVER);
                Connection con = DriverManager.getConnection(DB_LOGIN_URL, USER, PASS);

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                if(rs.isBeforeFirst()){
                    enter_dashboard();

                }
                else{
                    prompt.setText("Invalid Credentials !");
                    username.clear();
                    password.clear();
                }

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
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
        stage.setResizable(false);
        stage.show();

    }
    public void enter_dashboard() throws IOException, SQLException {
        Stage stage = (Stage)reenter.getScene().getWindow();
        String sql ="SELECT * FROM logged WHERE username =\""+user+"\" AND password =\""+pass+"\";";
        String load_list ="SELECT * FROM "+user+";";

        ResultSet rs;
        try{Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_LOGIN_URL, USER, PASS);

            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            }catch(SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
            }
        rs.next();
        database = rs.getString("db");

        try{Class.forName(DRIVER);
            Connection con2 = DriverManager.getConnection(DB_LIST, USER, PASS);

            Statement stmt = con2.createStatement();
            rs = stmt.executeQuery(load_list);
            while(rs.next()){
                user_list.getItems().addAll(rs.getString("topic"));
            }
        }catch(SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        scene = new Scene(fxmlLoader.load(), 1018, 467);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        scene = new Scene(fxmlLoader.load(), 527, 302);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }
    public static void main(String args[]){
        launch();
    }
}
