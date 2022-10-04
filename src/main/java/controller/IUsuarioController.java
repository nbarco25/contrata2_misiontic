package controller;

import java.util.Map;

public interface IUsuarioController {
    
    /* Método para iniciar sesión */
    public String login(String username, String contrasena);

    /* Método para regístrarse */
    public String register(String username, String contrasena, String nombre, String apellido, 
            String documento, String email, String departamento, String ciudad, double saldo, boolean premium);
    
    /* Método pedir */
    public String pedir(String username);
    
    /* Método para restar dinero */
    public String restarDinero(String username, double nuevoSaldo);
    
    /* Método modificar */
    public String modificar(String username, String nuevaContrasena, 
            String nuevoNombre, String nuevosApellidos, String nuevoDocumento, String nuevoEmail, 
            String departamento, String ciudad, double nuevoSaldo, boolean nuevoPremium);
    
    /* Método para ver copias = horas */
    public String verCopias(String username);    
    
    /* Método para devolver ofertante  */
    public String devolverOfertantes(String username, Map<Integer, Integer> horas_disponibles);

    /* Método para eliminar */
    public String eliminar(String username);

}
