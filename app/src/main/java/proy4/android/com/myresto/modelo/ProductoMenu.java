package proy4.android.com.myresto.modelo;

public class ProductoMenu {

    private int id;
    private String nombre;
    private double precio;

    public ProductoMenu(){
        super();
    }

    public ProductoMenu(int id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public ProductoMenu(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString(){
        return nombre + " - " + precio;
    }
}
