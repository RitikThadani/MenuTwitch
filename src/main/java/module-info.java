module com.example.pruebaut04ritikpunjabithadani {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.pruebaut04ritikpunjabithadani to javafx.fxml;
    exports com.example.pruebaut04ritikpunjabithadani;
}