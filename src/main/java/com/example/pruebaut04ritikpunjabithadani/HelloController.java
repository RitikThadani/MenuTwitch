package com.example.pruebaut04ritikpunjabithadani;

import com.example.pruebaut04ritikpunjabithadani.ConexionControl.Control;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Clase que controla la interfaz inicial.
 */

public class HelloController implements Initializable {

    @FXML
    private TextField barraBusqueda;

    @FXML
    private TextArea lista,datos;

    String nombreBuscado="";

    String busqueda="";

    /**
     * Método que inicializa otros métodos al iniciar la interfaz.
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Control.connection();
        lista.setText(Control.mostrarSeguidos());
    }

    /**
     * Método para usar otro método de la clase Control para buscar y mostrar los datos de un Streamer pasado mediante
     * un TextField al método.
     * @throws SQLException
     */

    public void buscar() throws SQLException {
        busqueda=barraBusqueda.getText();
        datos.setText(Control.mostrarStreamer(busqueda));
        nombreBuscado=busqueda;
    }

    /**
     * Método para usar el método seguir de la clase Control para buscar el id del usuario buscado anteriormente
     * y seguirlo con el seguidor.
     * @throws SQLException
     */

    public void seguirStreamer() throws SQLException {
        Control.seguir(nombreBuscado);
        lista.setText(Control.mostrarSeguidos());
        datos.setText(Control.mostrarStreamer(busqueda));
    }

    /**
    * Método para usar el método dejarDeSeguir de la clase Control para buscar el id del usuario buscado anteriormente
    * y dejarlo de seguir con el seguidor.
    * @throws SQLException
    */

    public void dejarStreamer() throws SQLException {
        Control.dejarDeSeguir(nombreBuscado);
        lista.setText(Control.mostrarSeguidos());
        datos.setText(Control.mostrarStreamer(busqueda));
    }

}