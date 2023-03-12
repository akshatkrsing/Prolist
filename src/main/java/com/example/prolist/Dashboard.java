package com.example.prolist;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.LightBase;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dashboard implements DAO {
    public Dashboard(Stage stage,String database) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1018, 467);
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    TableView<Items> table = new TableView<>();
    @FXML
    ListView<String> list = new ListView<>();
    @FXML
    Label label = new Label();
    @FXML
    TextField text = new TextField();
    @FXML
    TextField search_bar = new TextField();
    @FXML
    Button add_col = new Button("Add Column");
    @FXML
    Button add_table = new Button("Add Table");

    ObservableList<String> tables = FXCollections.observableArrayList();
    FilteredList<String> filteredData = new FilteredList<>(tables,s->true);

    @FXML
    public void onEnter(ActionEvent ae){
        if(text.getText().isEmpty()){
            label.setText("Empty field !");
            return;
        }
        adding_table(text.getText());
        text.clear();
        label.setText("");
    }
    @FXML
    public void adding_column(String column){


    }
    @FXML
    public void adding_table(String table){
        adding_to_list(table);
        String sql="CREATE TABLE "+table+"(id INT UNSIGNED NOT NULL AUTO_INCREMENT, PRIMARY KEY (id));";
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);

            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public void adding_to_list(String value){

            list.setEditable(true);
            tables.add(value);

        list.setItems(tables);
        list.scrollTo(list.getItems().size()-1);

//        list.layout();
//        list.edit(list.getItems().size()-1);

    }

    @FXML
    public void adding_column() {
    }
    @FXML
    public void display_table(){
        String cell = list.getSelectionModel().getSelectedItem();
        String sql = "SELECT * from "+cell+";";
        ResultSet rs;
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);

            Statement stmt = con.createStatement();
            rs =stmt.executeQuery(sql);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
    @FXML
    public void on_Search() {
        String search = search_bar.getText();
        if(search==null || search.length()==0){
            filteredData.setPredicate(s->true);
        }
        else{
            filteredData.setPredicate(s->s.contains(search));
        }
        list.setItems(filteredData);
    }
}
