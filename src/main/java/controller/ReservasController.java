package controller;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.List;

import com.google.gson.Gson;

import beans.Reservas;
import connection.DBConnection;

public class ReservasController implements IReservasController {

    @Override
    public String listarReservas(String username) {

        Gson gson = new Gson();

        DBConnection conn = new DBConnection();

        String sql = "SELECT l.id_ofertante, l.of_nombre, l.nombre_servicio, a.fecha_reserva FROM ofertantes l "
                + "INNER JOIN reservas a on l.id_ofertante = a.id_ofertante INNER JOIN usuarios u on a.username = u.username "
                + "WHERE a.username = '" + username + "'";

        List<String> reservas = new ArrayList<String>();

        try {

            Statement st = conn.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int id_ofertante = rs.getInt("id_ofertante");
                String of_nombre = rs.getString("of_nombre");
                String nombre_servicio = rs.getString("nombre_servicio");
                Date fecha_reserva = rs.getDate("fecha_reserva");

                Reservas reserva = new Reservas(id_ofertante, of_nombre, fecha_reserva, nombre_servicio);

                reservas.add(gson.toJson(reserva));
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            conn.desconectar();
        }
        return gson.toJson(reservas);
    }
    
}