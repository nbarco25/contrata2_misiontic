package beans;

public class Usuarios {
    
    //CAMPOS BD
    private String username;
    private String contrasena;
    private String nombre;
    private String apellido;
    private String documento;
    private String email;
    private String departamento;
    private String ciudad;
    private double saldo;
    private boolean premium;

    //MÉTODO CONSTRUCTOR
    public Usuarios(String username, String contrasena, String nombre, String apellido, String documento, String email, String departamento, String ciudad, double saldo, boolean premium) {
        this.username = username;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.email = email;
        this.departamento = departamento;
        this.ciudad = ciudad;
        this.saldo = saldo;
        this.premium = premium;
    }

    //MÉTODOS GETTER AND SETTER
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    //MÉTODO TO STRING PARA CONVERTIR DATOS INGRESADOS DESDE EL FRONT-END COMO ENTERO A TEXTO
    @Override
    public String toString() {
        return "Usuarios{" + "username=" + username + ", "
                + "contrasena=" + contrasena + ", "
                + "nombre=" + nombre + ", "
                + "apellido=" + apellido + ", "
                + "documento=" + documento + ", "
                + "email=" + email + ", "
                + "departamento=" + departamento + ", "
                + "ciudad=" + ciudad + ", "
                + "saldo=" + saldo + ", "
                + "premium=" + premium + '}';
    }
    
}
