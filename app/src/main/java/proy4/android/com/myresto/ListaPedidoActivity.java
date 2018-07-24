package proy4.android.com.myresto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import proy4.android.com.myresto.modelo.Pedido;
import proy4.android.com.myresto.modelo.PedidoDAO;
import proy4.android.com.myresto.modelo.PedidoDAOMemory;

public class ListaPedidoActivity extends AppCompatActivity {

    private PedidoDAO pedidoDAO;
    private ArrayAdapter<Pedido> adaptadorLista;
    private Button btnNuevoPedido;
    private ListView listaPedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lista_pedido);

        this.pedidoDAO = new PedidoDAOMemory();

        final Intent intent = getIntent();

        this.pedidoDAO = intent.getParcelableExtra("PedidoDAO");

        this.listaPedidos = (ListView) findViewById(R.id.listaPedidos);

        this.btnNuevoPedido = (Button) findViewById(R.id.btnNuevosPedidos);

        this.adaptadorLista = new ArrayAdapter<>(ListaPedidoActivity.this,android.R.layout.simple_list_item_1, pedidoDAO.listarTodos());

        this.listaPedidos.setAdapter(this.adaptadorLista);

        this.btnNuevoPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            // cuando se hace click se debe iniciar la actividad MainActivity
                finish( );
            }
        });

        this.listaPedidos.setOnItemLongClickListener(
            new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    int itemPosition = position;
                    Pedido itemValue = (Pedido) listaPedidos.getItemAtPosition(position);

                    pedidoDAO.eliminar(itemValue);

                    //Para actualizar el listview
                    adaptadorLista.notifyDataSetChanged();

                    Toast.makeText(getApplicationContext(), "Borrar elemento de posicion :"+itemPosition+ " Id: " +itemValue.getId()+ " nombre: "+itemValue.getNombre() , Toast.LENGTH_LONG).show();
                    return false;
                }
            });
        }
}
