package controller;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import beans.Usuarios;
import connection.DBConnection;

public class UsuarioController implements IUsuarioController {

    //INICIAR SESIÓN - USUARIO
    @Override
    public String login(String username, String contrasena) {

        //Crear un objeto de la clase Gson
        Gson gson = new Gson();

        //Crear un objeto de la clase DBConnection para onectarnos con la base de datos
        DBConnection conn = new DBConnection();

        //Sentencia SQL
        String sql = "SELECT * FROM usuarios "
                + "WHERE username = '" + username
                + "' AND + contrasena = '" + contrasena + "'";

        //Cuando se establece la conexión
        try {

            Statement st = conn.getConnection().createStatement();

            ResultSet rs = st.executeQuery(sql);

            //Proceso de buscar dentro de la conexión
            while (rs.next()) {
                //parametroEntrada = campoDB 
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String documento = rs.getString("documento");
                String email = rs.getString("email");
                String departamento = rs.getString("departamento");
                String ciudad = rs.getString("ciudad");
                double saldo = rs.getDouble("saldo");
                boolean premium = rs.getBoolean("premium");

                //Crear un objeto de la clase Usuarios que almacenara toda la info
                Usuarios usuario = new Usuarios(username, contrasena, nombre, apellido, documento, email, departamento, ciudad, saldo, premium);

                //Retornar los datos
                return gson.toJson(usuario);

            }

        } //Error en el proceso de conexión
        catch (Exception ex) {
            System.out.println("¡ERROR!: " + ex.getMessage());
        } //Finalizar el proceso de conexión
        finally {
            conn.desconectar();
        }

        return "false";

    }

    //CREAR UN NUEVO USUARIO
    @Override
    public String register(String username, String contrasena, String nombre, String apellido, String documento, String email, String departamento, String ciudad,
            double saldo, boolean premium) {

        Gson gson = new Gson();

        DBConnection conn = new DBConnection();

        String sql = "INSERT INTO usuarios VALUES('" + username + "', '" + contrasena + "', '" + nombre
                + "', '" + apellido + "', '" + documento + "', '" + email + "', '" + departamento + "', '" + ciudad + "', " + saldo + ", " + premium + ")";

        try {
            Statement st = conn.getConnection().createStatement();
            st.executeUpdate(sql);

            Usuarios usuario = new Usuarios(username, contrasena, nombre, apellido, documento, email, departamento, ciudad, saldo, premium);

            st.close();

            return gson.toJson(usuario);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            conn.desconectar();
        }

        return "false";

    }

    //PEDIR
    @Override
    public String pedir(String username) {

        Gson gson = new Gson();

        DBConnection conn = new DBConnection();

        String sql = "SELECT * FROM usuarios WHERE username = '" + username + "'";

        try {

            Statement st = conn.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                String contrasena = rs.getString("contrasena");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String documento = rs.getString("documento");
                String email = rs.getString("email");
                String departamento = rs.getString("departamento");
                String ciudad = rs.getString("ciudad");
                double saldo = rs.getDouble("saldo");
                boolean premium = rs.getBoolean("premium");

                Usuarios usuario = new Usuarios(username, contrasena, nombre, apellido, documento, email, departamento, ciudad, saldo, premium);

                return gson.toJson(usuario);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            conn.desconectar();
        }

        return "false";
    }

    //RESTAR DINERO
    @Override
    public String restarDinero(String username, double nuevoSaldo) {

        DBConnection conn = new DBConnection();
        String sql = "UPDATE usuarios SET saldo = " + nuevoSaldo + " WHERE username = '" + username + "'";

        try {

            Statement st = conn.getConnection().createStatement();
            st.executeUpdate(sql);

            return "true";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            conn.desconectar();
        }

        return "false";
    }
        
    //MODIFICAR USUARIOS - PROFILE
    @Override
    public String modificar(String username, String nuevaContrasena,
            String nuevoNombre, String nuevosApellidos, String nuevoDocumento, String nuevoEmail,
            String nuevoDepartamento, String nuevoCiudad, double nuevoSaldo, boolean nuevoPremium) {

        DBConnection conn = new DBConnection();

        String sql = "UPDATE usuarios SET contrasena = '" + nuevaContrasena + "', "
                + "nombre = '" + nuevoNombre + "', "
                + "apellido = '" + nuevosApellidos + "', "
                + "documento = '" + nuevoDocumento + "', "
                + "email = '" + nuevoEmail + "', "
                + "departamento = '" + nuevoDepartamento + "', "
                + "ciudad = '" + nuevoCiudad + "', "
                + "saldo = " + nuevoSaldo + ", premium = ";

        if (nuevoPremium == true) {
            sql += " 1 ";
        } else {
            sql += " 0 ";
        }

        sql += " WHERE username = '" + username + "'";

        try {

            Statement st = conn.getConnection().createStatement();
            st.executeUpdate(sql);

            return "true";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            conn.desconectar();
        }

        return "false";

    }
    
    //VER COPIAS
    @Override
    public String verCopias(String username) {

        DBConnection conn = new DBConnection();
        
        String sql = "SELECT id_ofertante,COUNT(*) AS num_copias FROM reservas WHERE username = '" 
                + username + "' GROUP BY id_ofertante;";

        Map<Integer, Integer> horas_disponibles = new HashMap<Integer, Integer>();

        try {

            Statement st = conn.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int id_ofertante = rs.getInt("id_ofertante");
                int num_copias = rs.getInt("num_copias");

                horas_disponibles.put(id_ofertante, num_copias);
            }

            devolverOfertantes(username, horas_disponibles);

            return "true";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            conn.desconectar();
        }

        return "false";

    }

    //DEVOLVER OFERTANTES
    @Override
    public String devolverOfertantes(String username, Map<Integer, Integer> horas_disponibles) {

        DBConnection conn = new DBConnection();

        try {
            for (Map.Entry<Integer, Integer> ofertante : horas_disponibles.entrySet()) {
                int id_ofertante = ofertante.getKey();
                int num_copias = ofertante.getValue();

                String sql = "UPDATE ofertantes SET horas_disponibles = (SELECT horas_disponibles + " + num_copias +
                        " FROM ofertantes WHERE id_ofertante = " + id_ofertante + ") WHERE id_ofertante = " + id_ofertante;
                
                Statement st = conn.getConnection().createStatement();
                st.executeUpdate(sql);

            }

            this.eliminar(username);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            conn.desconectar();
        }
        return "false";
    }

    //ELIMINAR USUARIO Y RESERVAS REALIZADAS
    public String eliminar(String username) {

        DBConnection conn = new DBConnection();

        String sql1 = "DELETE FROM reservas WHERE username = '" + username + "'";
        String sql2 = "DELETE FROM usuarios WHERE username = '" + username + "'";

        try {
            Statement st = conn.getConnection().createStatement();
            st.executeUpdate(sql1);
            st.executeUpdate(sql2);

            return "true";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            conn.desconectar();
        }

        return "false";
    }
    
}
