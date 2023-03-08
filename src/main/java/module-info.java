module com.example.prolist {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.prolist to javafx.fxml;
    exports com.example.prolist;
}