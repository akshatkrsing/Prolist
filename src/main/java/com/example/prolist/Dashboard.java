package com.example.prolist;

import javafx.fxml.FXML;
import javafx.scene.LightBase;
import javafx.scene.control.*;

import java.util.HashMap;
import java.util.Map;

public class Dashboard {
@FXML
    TableView<AddToList> table = new TableView<>();
@FXML
    ListView<String> list = new ListView<>();
    @FXML
    Label label = new Label();
    @FXML
    TextField text = new TextField();
    @FXML
    Button add_col = new Button();
    @FXML
    Button add_table = new Button();

    Map<String,AddToList> map = new HashMap<>();
@FXML
    public void onClick(){

}

}
