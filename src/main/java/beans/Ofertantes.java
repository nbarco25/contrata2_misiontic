package beans;

public class Ofertantes {
    
    //CAMPOS BD
    private int id_ofertante;
    private String of_nombre;
    private String of_apellido;
    private String of_email;
    private String of_departamento;
    private String of_ciudad;
    private String nombre_servicio;
    private String descripcion_servicio;
    private double costo_servicio;
    private int horas_disponibles;
    
    //MÉTODO CONSTRUCTOR
    public Ofertantes(int id_ofertante, String of_nombre, String of_apellido, String of_email, String of_departamento, String of_ciudad, String nombre_servicio, String descripcion_servicio, double costo_servicio, int horas_disponibles) {
        this.id_ofertante = id_ofertante;
        this.of_nombre = of_nombre;
        this.of_apellido = of_apellido;
        this.of_email = of_email;
        this.of_departamento = of_departamento;
        this.of_ciudad = of_ciudad;
        this.nombre_servicio = nombre_servicio;
        this.descripcion_servicio = descripcion_servicio;
        this.costo_servicio = costo_servicio;
        this.horas_disponibles = horas_disponibles;
    }
    
    //MÉTODOS GETTER AND SETTER
    public int getId_ofertante() {
        return id_ofertante;
    }

    public void setId_ofertante(int id_ofertante) {
        this.id_ofertante = id_ofertante;
    }

    public String getOf_nombre() {
        return of_nombre;
    }

    public void setOf_nombre(String of_nombre) {
        this.of_nombre = of_nombre;
    }

    public String getOf_apellido() {
        return of_apellido;
    }

    public void setOf_apellido(String of_apellido) {
        this.of_apellido = of_apellido;
    }

    public String getOf_email() {
        return of_email;
    }

    public void setOf_email(String of_email) {
        this.of_email = of_email;
    }

    public String getOf_departamento() {
        return of_departamento;
    }

    public void setOf_departamento(String of_departamento) {
        this.of_departamento = of_departamento;
    }

    public String getOf_ciudad() {
        return of_ciudad;
    }

    public void setOf_ciudad(String of_ciudad) {
        this.of_ciudad = of_ciudad;
    }

    public String getNombre_servicio() {
        return nombre_servicio;
    }

    public void setNombre_servicio(String nombre_servicio) {
        this.nombre_servicio = nombre_servicio;
    }

    public String getDescripcion_servicio() {
        return descripcion_servicio;
    }

    public void setDescripcion_servicio(String descripcion_servicio) {
        this.descripcion_servicio = descripcion_servicio;
    }

    public double getCosto_servicio() {
        return costo_servicio;
    }

    public void setCosto_servicio(double costo_servicio) {
        this.costo_servicio = costo_servicio;
    }

    public int getHoras_disponibles() {
        return horas_disponibles;
    }

    public void setHoras_disponibles(int horas_disponibles) {
        this.horas_disponibles = horas_disponibles;
    }

    //MÉTODO TO STRING PARA CONVERTIR DATOS INGRESADOS DESDE EL FRONT-END COMO ENTERO A TEXTO
    @Override
    public String toString() {
        return "Ofertantes{" + "id_ofertante=" + id_ofertante + ", "
                + "of_nombre=" + of_nombre + ", "
                + "of_apellido=" + of_apellido + ", "
                + "of_email=" + of_email + ", "
                + "of_departamento=" + of_departamento + ", "
                + "of_ciudad=" + of_ciudad + ", "
                + "nombre_servicio=" + nombre_servicio + ", "
                + "descripcion_servicio=" + descripcion_servicio + ", "
                + "costo_servicio=" + costo_servicio + ", "
                + "horas_disponibles=" + horas_disponibles + '}';
    }
    
}
