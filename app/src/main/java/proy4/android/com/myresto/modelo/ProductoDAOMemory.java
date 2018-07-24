package proy4.android.com.myresto.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ProductoDAOMemory implements ProductoDAO {

    private static List<ProductoMenu> MENU_PRODUCTOS;

    @Override
    public List<ProductoMenu> listarMenu() {
        if(MENU_PRODUCTOS==null){
            MENU_PRODUCTOS = new ArrayList<>();
        }
        return MENU_PRODUCTOS;
    }

    @Override
    public void cargarDatos(String[] datos) {
        MENU_PRODUCTOS = new ArrayList<>();
        for(String unaFila : datos){
            StringTokenizer tokens = new StringTokenizer(unaFila, "|");
            ProductoMenu aux = new ProductoMenu();
            aux.setId(Integer.valueOf(tokens.nextToken()));
            aux.setNombre(tokens.nextToken());
            aux.setPrecio(Double.valueOf(tokens.nextToken()));
            MENU_PRODUCTOS.add(aux);
        }
    }
}
