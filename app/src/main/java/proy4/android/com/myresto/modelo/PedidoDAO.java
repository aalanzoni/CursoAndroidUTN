package proy4.android.com.myresto.modelo;

import java.util.List;

public interface PedidoDAO {
    public void agregar(Pedido pedido);
    public void eliminar(Pedido pedido);
    public List<Pedido> listarTodos();
}
