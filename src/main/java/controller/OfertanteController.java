package controller;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import beans.Ofertantes;
import connection.DBConnection;

public class OfertanteController implements IOfertanteController {
    
    //LISTAR OFERTANTES
    @Override
    public String listar(boolean ordenar, String orden) {

        Gson gson = new Gson();

        DBConnection conn = new DBConnection();
        
        String sql = "SELECT * FROM ofertantes";

        if (ordenar == true) {
            sql += " ORDER BY nombre_servicio " + orden;
        }

        List<String> ofertantes = new ArrayList<String>();

        try {

            Statement st = conn.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                int id_ofertante = rs.getInt("id_ofertante");
                String of_nombre = rs.getString("of_nombre");
                String of_apellido = rs.getString("of_apellido");
                String of_email = rs.getString("of_email");
                String of_departamento = rs.getString("of_departamento");
                String of_ciudad = rs.getString("of_ciudad");
                String nombre_servicio = rs.getString("nombre_servicio");
                String descripcion_servicio = rs.getString("descripcion_servicio");
                double costo_servicio = rs.getDouble("costo_servicio");
                int horas_disponibles = rs.getInt("horas_disponibles");

                Ofertantes ofertante = new Ofertantes(id_ofertante, of_nombre, of_apellido, of_email, of_departamento, 
                        of_ciudad, nombre_servicio, descripcion_servicio, costo_servicio, horas_disponibles);

                ofertantes.add(gson.toJson(ofertante));

            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            conn.desconectar();
        }

        return gson.toJson(ofertantes);

    }
    
    //RESERVA
    @Override
    public String reservar(int id_ofertante, String username) {

        Timestamp fecha_reserva = new Timestamp(new Date().getTime());
        
        DBConnection conn = new DBConnection();
        
        String sql = "INSERT INTO reservas VALUES ('" + id_ofertante + "', '" + username + "', '" + fecha_reserva + "')";

        try {
            Statement st = conn.getConnection().createStatement();
            st.executeUpdate(sql);

            String modificar = modificar(id_ofertante);

            if (modificar.equals("true")) {
                return "true";
            }

        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            conn.desconectar();
        }
        return "false";
    }

    //MODIFICAR
    @Override
    public String modificar(int id_ofertante) {

        DBConnection conn = new DBConnection();
        String sql = "UPDATE ofertantes SET horas_disponibles = (horas_disponibles - 1) WHERE id_ofertante = " + id_ofertante;
        
        try {
            Statement st = conn.getConnection().createStatement();
            st.executeUpdate(sql);

            return "true";
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            conn.desconectar();
        }

        return "false";

    }

    //DEVOLVER
    @Override
    public String devolver(int id_ofertante, String username) {

        DBConnection conn = new DBConnection();
        
        String sql = "DELETE FROM reservas WHERE id_ofertante= " + id_ofertante + " AND username = '" 
                + username + "' LIMIT 1";

        try {
            Statement st = conn.getConnection().createStatement();
            st.executeQuery(sql);

            this.sumarCantidad(id_ofertante);

            return "true";
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            conn.desconectar();
        }

        return "false";
    }    
    
    //SUMAR CANTIDAD
    @Override
    public String sumarCantidad(int id_ofertante) {

        DBConnection conn = new DBConnection();

        String sql = "UPDATE ofertantes SET horas_disponibles = (SELECT horas_disponibles FROM ofertantes WHERE id_ofertante = " 
                + id_ofertante + ") + 1 WHERE id_ofertante = " + id_ofertante;

        try {
            Statement st = conn.getConnection().createStatement();
            st.executeUpdate(sql);

            return "true";
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            conn.desconectar();
        }

        return "false";

    }
        
}
