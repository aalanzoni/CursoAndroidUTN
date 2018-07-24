package proy4.android.com.myresto.modelo;

import java.util.List;

public interface ProductoDAO {
    public List<ProductoMenu> listarMenu();
    public void cargarDatos(String[] datos);
}
