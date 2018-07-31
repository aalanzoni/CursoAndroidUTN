package proy4.android.com.myresto.modelo;

public class DetallePedido {
    private int id;
    private ProductoMenu productoPedido;
    private int cantidad;

    public DetallePedido() {
        super();
    }

    public DetallePedido(int id, ProductoMenu productoPedido, int cantidad) {
        this.id = id;
        this.productoPedido = productoPedido;
        this.cantidad = cantidad;
    }

    public DetallePedido(ProductoMenu productoPedido, int cantidad) {
        this.productoPedido = productoPedido;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductoMenu getProductoPedido() {
        return productoPedido;
    }

    public void setProductoPedido(ProductoMenu productoPedido) {
        this.productoPedido = productoPedido;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
