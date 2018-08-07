package proy4.android.com.myresto.modelo;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Pedido{

    private Integer id;
    private String nombre;
    private String pedido;
    private boolean envioDomicilio;
    private boolean bebidaXL;
    private boolean permiteCancelar;
    private boolean incluyePropina;
    private boolean enviarNotificaciones;
    private boolean pagoAuotomatico;
    private Estado estado;
    private List<DetallePedido> itemsPedidos;

    private static int idGenerator = 0;

    public Pedido(){
        this.id = ++Pedido.idGenerator;
        this.itemsPedidos = new ArrayList<>();
    }

    @Override
    public String toString() {
        return nombre + " - " + pedido;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPedido() {
        return pedido;
    }

    public void setPedido(String pedido) {
        this.pedido = pedido;
    }

    public boolean isEnvioDomicilio() {
        return envioDomicilio;
    }

    public void setEnvioDomicilio(boolean envioDomicilio) {
        this.envioDomicilio = envioDomicilio;
    }

    public boolean isBebidaXL() {
        return bebidaXL;
    }

    public void setBebidaXL(boolean bebidaXL) {
        this.bebidaXL = bebidaXL;
    }

    public boolean isPermiteCancelar() {
        return permiteCancelar;
    }

    public void setPermiteCancelar(boolean permiteCancelar) {
        this.permiteCancelar = permiteCancelar;
    }

    public boolean isIncluyePropina() {
        return incluyePropina;
    }

    public void setIncluyePropina(boolean incluyePropina) {
        this.incluyePropina = incluyePropina;
    }

    public boolean isEnviarNotificaciones() {
        return enviarNotificaciones;
    }

    public void setEnviarNotificaciones(boolean enviarNotificaciones) {
        this.enviarNotificaciones = enviarNotificaciones;
    }

    public boolean isPagoAuotomatico() {
        return pagoAuotomatico;
    }

    public void setPagoAuotomatico(boolean pagoAuotomatico) {
        this.pagoAuotomatico = pagoAuotomatico;
    }

    public List<DetallePedido> getItemsPedidos() {
        return itemsPedidos;
    }

    public void setItemsPedidos(List<DetallePedido> itemsPedidos) {
        this.itemsPedidos = itemsPedidos;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void addItemDetalle(DetallePedido prd){
        this.itemsPedidos.add(prd);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(id, pedido.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void preparar(){
        if(this.estado == Estado.CONFIRMADO){
            this.estado = Estado.EN_PREPARACION;
        }
    }
    public void enviar(){
        if(this.estado == Estado.EN_PREPARACION){
            this.estado = Estado.EN_ENVIO;
        }
    }
    public void entregar(){
        if(this.estado == Estado.EN_ENVIO){
            this.estado = Estado.ENTREGADO;
        }
    }
}
