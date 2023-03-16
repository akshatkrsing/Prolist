package com.example.prolist;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.util.Callback;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dashboard extends Login {
    @FXML
    TableView<Items> table = new TableView<>();
    @FXML
    ListView<String> list = user_list;
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
    @FXML
    Button clear_button = new Button();

    ObservableList<String> tables = FXCollections.observableArrayList();
    FilteredList<String> filteredData = new FilteredList<>(tables,s->true);
    private String DB = DB_URL + database;
    private String cell;

    @FXML
    public void clear(){
        cell = "";
        table.getItems().clear();
    }
    @FXML
    public void onEnter(ActionEvent ae){
        if(text.getText().isEmpty()){
            label.setText("Empty field !");
            return;
        }
        adding_table();
        text.clear();
        label.setText("");
    }
    @FXML
    public void adding_table(){
        String table = text.getText().trim();
        adding_to_list(table);
        String sql="CREATE TABLE "+table+"(id INT UNSIGNED NOT NULL AUTO_INCREMENT, PRIMARY KEY (id));";
        String save_list = "INSERT INTO "+user+"(topic) VALUES(\""+user+"\")";
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB,USER, PASS);
            Connection con2 = DriverManager.getConnection(DB_LIST,USER,PASS);

            Statement stmt = con.createStatement();
            Statement stmt2 = con2.createStatement();
            stmt.executeUpdate(sql);
            stmt2.executeUpdate(save_list);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public void adding_to_list(String value){

            list.setEditable(true);
            tables.add(value);

        list.setItems(tables);
        list.scrollTo(list.getItems().size()-1);

    }

    @FXML
    public void adding_column() {
        String adds_col = text.getText().trim();
        String sql = "ALTER TABLE "+cell+"ADD COLUMN"+adds_col+";";
        ResultSet rs;
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB, USER, PASS);

            Statement stmt = con.createStatement();
            rs =stmt.executeQuery(sql);

            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>, ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                table.getColumns().addAll(col);
                System.out.println("Column ["+i+"] ");
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    public void display_table(){
        table.getItems().clear();
        cell = list.getSelectionModel().getSelectedItem();
        String sql = "SELECT * from "+cell+";";
        ResultSet rs;
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB, USER, PASS);

            Statement stmt = con.createStatement();
            rs =stmt.executeQuery(sql);

            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>, ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                table.getColumns().addAll(col);
                System.out.println("Column ["+i+"] ");
            }

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
