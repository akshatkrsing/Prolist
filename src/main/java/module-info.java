module com.example.prolist {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens com.example.prolist to javafx.fxml;
    exports com.example.prolist;
}