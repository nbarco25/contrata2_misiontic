package controller;

public interface IOfertanteController {
    
    //Listar ofertantes
    public String listar(boolean ordenar, String orden);
    
    //Alquilar ofertante
    public String reservar(int id_ofertante, String username);
    
    //Modificar ofertante
    public String modificar(int id_ofertante);

    //Devolver ofertante
    public String devolver(int id_ofertante, String username);    

    //Sumar cantidad
    public String sumarCantidad(int id_ofertante);
    
}
