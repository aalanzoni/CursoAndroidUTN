package proy4.android.com.myresto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

    private ListView listaMenu;
    private ProductoMenu productoElegido;
    private Integer cantidadProducto = 0;

    private Button btnMas;
    private Button btnMenos;
    private Button btnAgregar;

    private TextView tvCantidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pedido);

        //Linkeos de elementos visuales de la pantalla
        this.listaMenu = (ListView) findViewById(R.id.listaProductos);

        this.listaMenu.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


        this.btnMas = (Button) findViewById(R.id.btnMasProducto);
        this.btnMenos = (Button) findViewById(R.id.btnMenosProducto);
        this.btnAgregar = (Button) findViewById(R.id.btnAddProducto);
        this.tvCantidad = (TextView) findViewById(R.id.detPedProductoCantidad);

        this.tvCantidad.setText(cantidadProducto.toString());

        btnAgregar.setEnabled(false);

        this.productoDao = new ProductoDAOMemory();

        final String[] listaProductos = getResources().getStringArray(R.array.listaProductos);

        this.productoDao.cargarDatos(listaProductos);

        this.adaptadorLista = new ArrayAdapter<>(DetallePedidoActivity.this,android.R.layout.simple_list_item_1, this.productoDao.listarMenu());
        this.listaMenu.setAdapter(this.adaptadorLista);

        this.listaMenu.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long id) {

            productoElegido = (ProductoMenu) listaMenu.getItemAtPosition(position);
            }

        });

        this.btnMenos.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cantidadProducto > 0) {
                        cantidadProducto--;
                        tvCantidad.setText(cantidadProducto.toString());
                        if(cantidadProducto == 0)
                            btnAgregar.setEnabled(false);
                    }
                }

        });

        this.btnMas.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cantidadProducto == 0)
                        btnAgregar.setEnabled(true);
                    cantidadProducto++;
                    tvCantidad.setText(cantidadProducto.toString());
                }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("producto", productoElegido);
                intent.putExtra("cantidad", cantidadProducto);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
