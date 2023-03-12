package com.example.prolist;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

public class Items {
    List<SimpleStringProperty> property = new ArrayList<>();
    public int add_Property(String property){
        SimpleStringProperty prop = new SimpleStringProperty(property);
        this.property.add(prop);
        return this.property.size()-1;
    }


}
