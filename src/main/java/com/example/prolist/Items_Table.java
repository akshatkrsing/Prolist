package com.example.prolist;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.sql.*;


public class Items_Table implements DAO {
    private String create_tb;
    private TableView<Items> tabular;
    private String table;


    public void createTable(String table) throws SQLException, ClassNotFoundException {
        this.table = table;
        this.create_tb = "CREATE TABLE "+this.table+"(id INT UNSIGNED NOT NULL AUTO_INCREMENT, PRIMARY KEY (id));";
    }
    public  void add_col(String column) throws SQLException, ClassNotFoundException {
        TableColumn col = new TableColumn();
        col.setCellValueFactory(new PropertyValueFactory<Items,String>("column"));
        col.setCellFactory(TextFieldTableCell.forTableColumn());
        tabular.getColumns().add(col);
    }

    public TableView<Items> getTable() throws SQLException, ClassNotFoundException {
        return tabular;
    }


}
