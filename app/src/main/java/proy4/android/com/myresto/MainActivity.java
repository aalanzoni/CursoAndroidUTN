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
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import proy4.android.com.myresto.modelo.Pedido;
import proy4.android.com.myresto.modelo.PedidoDAO;
import proy4.android.com.myresto.modelo.PedidoDAOMemory;

public class MainActivity extends AppCompatActivity {
    private Button btnConfirmar;//OK
    private Button btnCancelar;//OK
    private Button btnAddProducto;
    private EditText txtNombre;//OK
    private EditText txtDetalle;//OK

    private RadioButton envioADomicilio;//OK
    private RadioButton local;//OK

    private CheckBox bebidaXL;//OK
    private CheckBox permiteCancelar;//OK
    private CheckBox incluyePropina;//OK
    private Switch enviarNotificacion;//OK
    private ToggleButton pagoAutomatico;//OK

    private Pedido pedidoActual;
    private PedidoDAO persistencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pedidoActual = new Pedido();
        persistencia = new PedidoDAOMemory();

        txtNombre = (EditText) findViewById(R.id.txtNombreCliente);
        txtDetalle = (EditText)findViewById(R.id.txtDetallePedido);

        envioADomicilio = (RadioButton)findViewById(R.id.rbDelivery);
        bebidaXL = (CheckBox)findViewById(R.id.cbBebidaXL);
        permiteCancelar = (CheckBox)findViewById(R.id.cbPermitirCancelar);
        incluyePropina= (CheckBox)findViewById(R.id.cbIncluirPropina);
        enviarNotificacion = (Switch)findViewById(R.id.swtNotifica);
        pagoAutomatico = (ToggleButton)findViewById(R.id.toggleButtonPagar);

        btnConfirmar = (Button) findViewById(R.id.btnConfirmar);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnAddProducto = (Button)findViewById(R.id.btnAddProducto);

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

            persistencia.agregar(pedidoActual);

            muestroPedido();

            Toast.makeText(MainActivity.this,"Pedido creado", Toast.LENGTH_LONG).show();
            Log.d("APP_MY_RESTO","Pedido confirmado!!!! ");
            Log.d("APP_MY_RESTO", pedidoActual.toString());

            txtNombre.setText("");
            txtDetalle.setText("");
            pedidoActual = new Pedido();
            }
        });

        this.btnAddProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listaMenu= new Intent(MainActivity.this,DetallePedidoActivity.class);
                startActivity(listaMenu);
            }
        });
    }

    private void muestroPedido(){
        Intent intent = new Intent(this, ListaPedidoActivity.class);
        intent.putExtra("PedidoDAO",(Parcelable) this.persistencia);
        startActivity(intent);
    }
}