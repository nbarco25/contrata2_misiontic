package test;

import beans.Ofertantes;
import connection.DBConnection;
import java.sql.ResultSet;
import java.sql.Statement;

public class OperacionesDB {
    
    public static void main(String[] args) {
        
        //Mostrar la lista de ofertantes
        listarOfertantes();
    
    }
    
    //ACTUALIZAR OFERTANTE
    public static void actualizarOfertantes(int id_ofertante, String nombre_servicio){
        
        //Crear un objeto de la clase DBConnection para onectarnos con la base de datos
        DBConnection conn = new DBConnection();
        
        //Sentencia SQL
        String sql = "UPDATE ofertantes "
                + "SET nombre_servicio = '" + nombre_servicio 
                + "'WHERE id_ofertante = " + id_ofertante;
        
        //Cuando se establece la conexión
        try {
            Statement st = conn.getConnection().createStatement();
            st.executeUpdate(sql);
        } 
        //Error en el proceso de conexión
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        //Finalizar el proceso de conexión
        finally{
            conn.desconectar();
        }
        
    }
    
    //LISTA DE OFERTANTES
    public static void listarOfertantes(){

        //Crear un objeto de la clase DBConnection para conectarnos con la base de datos
        DBConnection conn = new DBConnection();
        
        //Sentencia SQL
        String sql = "SELECT * FROM ofertantes";

        //Cuando se establece la conexión
        try {
            
            //
            Statement st = conn.getConnection().createStatement();

            //
            ResultSet rs = st.executeQuery(sql);

            //
            while(rs.next()){
                
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
                                
                //Crear un objeto de la clase Ofertantes para almacenar la info de cada ofertante
                Ofertantes ofertante = new Ofertantes(id_ofertante, of_nombre, of_apellido, of_email, of_departamento, 
                        of_ciudad, nombre_servicio, descripcion_servicio, costo_servicio, horas_disponibles);
                
                //Mostrar en pantalla la info de las ofertantes
                System.out.println(ofertante.toString());      
                
            }           

            //
            st.executeQuery(sql);

        } 
        //Error en el proceso de conexión
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        //Finalizar el proceso de conexión
        finally{
            conn.desconectar();
        }
        
    }    
    
}
