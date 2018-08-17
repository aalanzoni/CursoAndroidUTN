package proy4.android.com.myresto;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    private Button btnNuevoPedido;
    private ListView listaPedidos;
    private PedidoAdapter adaptadorPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lista_pedido);

        this.createNotificationChannel();

//        Intent intent = getIntent();

        this.pedidoDAO = new PedidoDAOMemory();

        Log.d("@ListaPedido", pedidoDAO.listarTodos().toString());


        Log.d("VER ACA: ",this.pedidoDAO.toString());

        this.listaPedidos = (ListView) findViewById(R.id.listaPedidos);

        this.btnNuevoPedido = (Button) findViewById(R.id.btnNuevosPedidos);

        //this.adaptadorLista = new ArrayAdapter<>(ListaPedidoActivity.this,android.R.layout.simple_list_item_1, pedidoDAO.listarTodos());
        //this.listaPedidos.setAdapter(this.adaptadorLista);

        this.adaptadorPedido = new PedidoAdapter(this, pedidoDAO.listarTodos());

        this.listaPedidos.setAdapter(this.adaptadorPedido);

        this.btnNuevoPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            // cuando se hace click se debe iniciar la actividad MainActivity
            Intent intent = new Intent(ListaPedidoActivity.this,MainActivity.class);
            intent.putExtra("persistencia", (Parcelable) pedidoDAO);
            startActivity(intent);
//                finish();

            }
        });

//        Borrado de Pedido seleccionado
        this.listaPedidos.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    int itemPosition = position;
                    Pedido itemValue = (Pedido) listaPedidos.getItemAtPosition(position);

                    pedidoDAO.eliminar(itemValue);

                    //Para actualizar el listview
                    adaptadorPedido.notifyDataSetChanged();

                    Toast.makeText(getApplicationContext(), "Borrar elemento de posicion :" + itemPosition + " Id: " + itemValue.getId() + " nombre: " + itemValue.getNombre(), Toast.LENGTH_LONG).show();
                    return false;
                }
        });
        }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "1",
                    "canal1",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("descipcion");
            NotificationManager notificationManager
                    = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
