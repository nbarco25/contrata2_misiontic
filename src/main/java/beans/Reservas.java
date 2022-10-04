package beans;

import java.sql.Date;

public class Reservas {
    
    //CAMPOS BD
    private int id_ofertante;
    private String username;
    private Date fecha_reserva;
    private String nombre_servicio;

    public Reservas(int id_ofertante, String username, Date fecha_reserva, String nombre_servicio) {
        this.id_ofertante = id_ofertante;
        this.username = username;
        this.fecha_reserva = fecha_reserva;
        this.nombre_servicio = nombre_servicio;
    }

    public int getId_ofertante() {
        return id_ofertante;
    }

    public void setId_ofertante(int id_ofertante) {
        this.id_ofertante = id_ofertante;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getFecha_reserva() {
        return fecha_reserva;
    }

    public void setFecha_reserva(Date fecha_reserva) {
        this.fecha_reserva = fecha_reserva;
    }

    public String getNombre_servicio() {
        return nombre_servicio;
    }

    public void setNombre_servicio(String nombre_servicio) {
        this.nombre_servicio = nombre_servicio;
    }

    @Override
    public String toString() {
        return "Reservas{" + "id_ofertante=" + id_ofertante + ", "
                + "username=" + username + ", "
                + "fecha_reserva=" + fecha_reserva + ", "
                + "nombre_servicio=" + nombre_servicio + '}';
    }

}
