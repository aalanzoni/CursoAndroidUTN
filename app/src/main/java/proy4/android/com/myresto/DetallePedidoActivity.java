package proy4.android.com.myresto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import proy4.android.com.myresto.modelo.ProductoDAO;
import proy4.android.com.myresto.modelo.ProductoDAOMemory;
import proy4.android.com.myresto.modelo.ProductoMenu;

public class DetallePedidoActivity extends AppCompatActivity {
    private ProductoDAO productoDao;

    private ArrayAdapter<ProductoMenu> adaptadorLista;

    private ListView listaProductos;

    private Button btnMas;
    private Button btnMenos;
    private Button btnAgregar;

    private TextView tvCantidad;

    /**
     * Tareas:
     1. En el método oncreate asignarle a adaptadorLista, una instancia de un nuevo “ArrayAdapter”
     2. Definir que el layout de este “ArrayAdapter” sea android.R.layout.simple_list_item_single_choice
     3. Vincular el adaptador con la lista de productos ( this.productoDao.listarMenu() )
     4. Luego vincular el adaptador con la lista.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pedido);

        //Linkeos de elementos visuales de la pantalla
        this.listaProductos = (ListView) findViewById(R.id.listaProductos);
        this.btnMas = (Button) findViewById(R.id.btnMasProducto);
        this.btnMenos = (Button) findViewById(R.id.btnMenosProducto);
        this.btnAgregar = (Button) findViewById(R.id.btnAddProducto);
        this.tvCantidad = (TextView) findViewById(R.id.detPedProductoCantidad);

        this.productoDao = new ProductoDAOMemory();

        String[] listaProductos = getResources().getStringArray(R.array.listaProductos);

        this.productoDao.cargarDatos(listaProductos);

        this.adaptadorLista = new ArrayAdapter<>(DetallePedidoActivity.this,android.R.layout.simple_list_item_1, this.productoDao.listarMenu());
        this.listaProductos.setAdapter(this.adaptadorLista);

    }
}
