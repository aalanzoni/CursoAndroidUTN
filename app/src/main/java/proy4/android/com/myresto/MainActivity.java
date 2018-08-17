package proy4.android.com.myresto;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import proy4.android.com.myresto.modelo.DetallePedido;
import proy4.android.com.myresto.modelo.Estado;
import proy4.android.com.myresto.modelo.Pedido;
import proy4.android.com.myresto.modelo.PedidoDAO;
import proy4.android.com.myresto.modelo.PedidoDAOMemory;
import proy4.android.com.myresto.modelo.ProductoMenu;

public class MainActivity extends AppCompatActivity {
    private Button btnConfirmar;//OK
    private Button btnAddProducto;
    private EditText txtNombre;//OK
    private EditText txtDetalle;//OK
    private TextView tvTotalPedido;

    private RadioButton envioADomicilio;//OK
    private RadioButton local;//OK

    private CheckBox bebidaXL;//OK
    private CheckBox permiteCancelar;//OK
    private CheckBox incluyePropina;//OK
    private Switch enviarNotificacion;//OK
    private ToggleButton pagoAutomatico;//OK

    private Pedido pedidoActual;
    private PedidoDAO persistencia;
    public Double total = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pedidoActual = new Pedido();
        persistencia = new PedidoDAOMemory();

        txtNombre = (EditText) findViewById(R.id.txtNombreCliente);
        txtDetalle = (EditText)findViewById(R.id.txtDetallePedido);
        tvTotalPedido = (TextView)findViewById(R.id.txtTotalPedido);
        tvTotalPedido.setText("$ " + total.toString());//Inicializo en 0

        envioADomicilio = (RadioButton)findViewById(R.id.rbDelivery);
        bebidaXL = (CheckBox)findViewById(R.id.cbBebidaXL);
        permiteCancelar = (CheckBox)findViewById(R.id.cbPermitirCancelar);
        incluyePropina= (CheckBox)findViewById(R.id.cbIncluirPropina);
        enviarNotificacion = (Switch)findViewById(R.id.swtNotifica);
        pagoAutomatico = (ToggleButton)findViewById(R.id.toggleButtonPagar);

        btnConfirmar = (Button) findViewById(R.id.btnConfirmar);
        btnAddProducto = (Button)findViewById(R.id.btnAddProducto);

        if(getIntent().getExtras() != null){
            int idPedidoSeleccionado = getIntent().getExtras().getInt("idPedido",-1);
            if(idPedidoSeleccionado>0){
                Toast.makeText(this, "Pedido Selected", Toast.LENGTH_SHORT).show();
                this.loadPedido(idPedidoSeleccionado);
            }
        }

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            pedidoActual.setNombre(txtNombre.getText().toString());
            pedidoActual.setPedido(txtDetalle.getText().toString());
            pedidoActual.setEnvioDomicilio(envioADomicilio.isChecked());
            pedidoActual.setBebidaXL(bebidaXL.isChecked());
            pedidoActual.setPermiteCancelar(permiteCancelar.isChecked());
            pedidoActual.setIncluyePropina(incluyePropina.isChecked());
            pedidoActual.setEnviarNotificaciones(enviarNotificacion.isChecked());
            pedidoActual.setPagoAuotomatico(pagoAutomatico.isChecked());
            pedidoActual.setEstado(Estado.CONFIRMADO);

            persistencia.agregar(pedidoActual);

            Log.d("@MAIN, pedidos: ", persistencia.listarTodos().toString());
            Intent intent = new Intent(MainActivity.this,ListaPedidoActivity.class);
//            intent.putExtra("persistencia", (Parcelable) persistencia);
            startActivity(intent);

            Toast.makeText(MainActivity.this,"Pedido creado", Toast.LENGTH_LONG).show();
            Log.d("APP_MY_RESTO","Pedido confirmado!!!! ");
            Log.d("APP_MY_RESTO", pedidoActual.toString());

            txtNombre.setText("");
            txtDetalle.setText("");

            total = 0.00;

            tvTotalPedido.setText("$ " + total.toString());

            pedidoActual = new Pedido();
            }
        });

        this.btnAddProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent listaMenu= new Intent(MainActivity.this,DetallePedidoActivity.class);
            startActivityForResult(listaMenu, 999);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==999){

                int cantidad = data.getIntExtra("cantidad",0);
                ProductoMenu prod= (ProductoMenu) data.getParcelableExtra("producto");
                DetallePedido detalle = new DetallePedido();
                detalle.setCantidad(cantidad);
                detalle.setProductoPedido(prod);
                pedidoActual.addItemDetalle(detalle);
                txtDetalle.getText().append(prod.getNombre()+ " $"+ (prod.getPrecio()*cantidad)+"\r\n");
                total += prod.getPrecio() * cantidad;
                tvTotalPedido.setText("Total $"+total.toString());
            }
        }
    }

    private void loadPedido(int id){
        this.pedidoActual = this.persistencia.buscarPorId(id);
        if(this.pedidoActual == null){
            Log.d("APP_MY_RESTO","Encontro el pedido!!!! ");
        }
        this.txtNombre.setText(pedidoActual.getNombre());
        this.bebidaXL.setChecked(pedidoActual.isBebidaXL());

        this.incluyePropina.setChecked(pedidoActual.isIncluyePropina());
        this.enviarNotificacion.setChecked(pedidoActual.isEnviarNotificaciones());
        this.permiteCancelar.setChecked(pedidoActual.isPermiteCancelar());

        RadioButton rbDelivery = (RadioButton) findViewById(R.id.rbDelivery);
        RadioButton rbMesa = (RadioButton) findViewById(R.id.rbCasa);

        if(this.pedidoActual.isEnvioDomicilio()){
            rbDelivery.setChecked(true);
            rbMesa.setChecked(false);
        }
        else{
            rbDelivery.setChecked(false);
            rbMesa.setChecked(true);
        }
        this.pagoAutomatico.setChecked(this.pedidoActual.isPagoAuotomatico());
        double totalOrden = 0.0;
        for(DetallePedido det : this.pedidoActual.getItemsPedidos()){
            this.txtDetalle.getText().append(
                    det.getProductoPedido().getNombre()+ " $" +
                            (det.getProductoPedido().getPrecio()
                                    *
                             det.getCantidad())+"\r\n");
            totalOrden += det.getCantidad() * det.getProductoPedido().getPrecio();
        }
        this.tvTotalPedido.setText("$"+totalOrden);
    }
}
