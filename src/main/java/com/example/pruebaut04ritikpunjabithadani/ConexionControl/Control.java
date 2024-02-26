package com.example.pruebaut04ritikpunjabithadani.ConexionControl;

import java.sql.*;

/**
 * Clase Control para realizar las principales acciones de nuestro programa y la conexión.
 */

public class Control {
    static String url = "jdbc:postgresql://localhost:5432/twitch";
    static String usuario = "postgres";
    static String password = "1234";
    private static Connection connect = null;

    /**
     * Método para realizar la conexión con nuestra base de datos.
     * @return
     */

    public static boolean connection() {
        try {
            connect = DriverManager.getConnection(url, usuario, password);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    /**
     * Método que nos muestra el nombre de los Streamers seguidos.
     * @return
     */

    public static String mostrarSeguidos() {
        String datos = "";
        try {
            Statement statement = connect.createStatement();
            ResultSet results = statement.executeQuery("Select (usuario).nombreUsuario from streamer inner join seguidor_streamer on seguidor_streamer.idStreamer=streamer.idStreamer;");
            while (results.next()) {
                datos += "Nombre: " + results.getString("nombreUsuario") + "\n" + "\n";
            }
        } catch (SQLException sqle) {

        }
        return datos;
    }

    /**
     * Método que nos muestra los datos de todos de un streamer buscado por nombre de usuario.
     * @return
     */

    public static String mostrarStreamer(String nombre) throws SQLException {
        String consulta = "Select (usuario).nombreUsuario,apodo,numeroSeguidores,ingresoAcumulado from streamer where (usuario).nombreUsuario=?;";
        String datos="";
        try (PreparedStatement preparedStatement = connect.prepareStatement(consulta)) {
            /*
            Establecer valores en la consulta
             */
            preparedStatement.setString(1, nombre);
            ResultSet results=preparedStatement.executeQuery();
            while (results.next()) {
                datos += "Nombre: " + results.getString("nombreUsuario") + "\n" + "\n"+
                        "Apodo: " + results.getString("apodo") + "\n" + "\n"+
                        "Numero de Seguidores: " + results.getString("numeroSeguidores") + "\n" + "\n"+
                        "Ingreso Acumulado: " + results.getString("ingresoAcumulado") + "\n" + "\n";
            }
        }
        return datos;
    }

    /**
     * Método que busca el id de un streamer recolectado anteriormente y vincula en la tabla seguir_streamer para
     * que el seguidor siga al streamer.
     * @param nombre
     * @throws SQLException
     */

    public static boolean seguir(String nombre) throws SQLException {
        boolean booleana=false;
        /*
        Búsqueda del id del Streamer dado su nombre.
         */
        String consulta = "select idStreamer from streamer where (usuario).nombreUsuario=?";
        int id=0;
        try (PreparedStatement preparedStatement = connect.prepareStatement(consulta)) {
            /*
            Establecer valores en la consulta
             */
            preparedStatement.setString(1, nombre);
            ResultSet results=preparedStatement.executeQuery();
            while (results.next()) {
                id+=results.getInt("idStreamer");
            }
            booleana=true;
        }
        /*
        Insersión del seguimiento al insertar en la tabla seguidor_streamer con el id.
         */
        String consulta2 = "insert into seguidor_streamer(idSeguidor,idStreamer) values (1,?)";
        try (PreparedStatement preparedStatement2 = connect.prepareStatement(consulta2)) {
            /*
            Establecer valores en la consulta
             */
            preparedStatement2.setInt(1, id);
            preparedStatement2.executeUpdate();
            booleana=true;
        }
        return booleana;
    }

    /**
     * Método que busca el id de un streamer recolectado anteriormente y lo elimina en la tabla seguir_streamer para
     * que el seguidor deje de seguir al streamer.
     * @param nombre
     * @throws SQLException
     */

    public static void dejarDeSeguir(String nombre) throws SQLException {
        /*
        Búsqueda del id del Streamer dado su nombre.
         */
        String consulta = "select idStreamer from streamer where (usuario).nombreUsuario=?";
        int id = 0;
        try (PreparedStatement preparedStatement = connect.prepareStatement(consulta)) {
            /*
            Establecer valores en la consulta
             */
            preparedStatement.setString(1, nombre);
            ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                id += results.getInt("idStreamer");
            }
        }
        /*
        Eliminación del seguimiento al borrar de la tabla seguidor_streamer con el id.
         */
        String consulta2 = "delete from seguidor_streamer where idStreamer=?";
        try (PreparedStatement preparedStatement2 = connect.prepareStatement(consulta2)) {
            /*
            Establecer valores en la consulta
             */
            preparedStatement2.setInt(1, id);
            preparedStatement2.executeUpdate();
        }
    }
}
