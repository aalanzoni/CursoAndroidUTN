package proy4.android.com.myresto;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import proy4.android.com.myresto.modelo.DetallePedido;
import proy4.android.com.myresto.modelo.Pedido;

public class PedidoAdapter extends ArrayAdapter {
    private Context context;
    private List<Pedido> listaPedidos;

    public PedidoAdapter(Context ctx, List<Pedido> lista){
        super(ctx, 0 , lista);
        this.context = ctx;
        this.listaPedidos = lista;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View fila = convertView;

        if(fila == null)
            fila = LayoutInflater.from(this.context).inflate(R.layout.fila_pedido, parent, false);

        final Pedido pedido = this.listaPedidos.get(position);
        double monto = 0.0;
        for (DetallePedido det : pedido.getItemsPedidos()) {
            monto += det.getCantidad() * det.getProductoPedido().getPrecio();
        }

        TextView tvNombre = (TextView) fila.findViewById(R.id.fila_PedidoNombre);
        TextView tvCantItems = (TextView) fila.findViewById(R.id.filaPedidoCantItems);
        TextView tvMonto = (TextView) fila.findViewById(R.id.filaPedidoMonto);
        TextView tvEstado = (TextView) fila.findViewById(R.id.filaPedidoEstado);
        ImageView imgPropina = (ImageView) fila.findViewById(R.id.filaPedidoImgPropina);
        Button btnVer = (Button) fila.findViewById(R.id.BtnVerPedido);
        Button btnEstado = (Button) fila.findViewById(R.id.btnEstado);

        tvNombre.setText("Cliente : "+pedido.getNombre());
        tvCantItems.setText("Items: "+pedido.getItemsPedidos().size());
        tvMonto.setText("$"+monto);
        tvEstado.setText(pedido.getEstado().toString());

        if(pedido.isIncluyePropina()){
            imgPropina.setImageResource(android.R.drawable.btn_star_big_on);
        }
        else{
            imgPropina.setImageResource(android.R.drawable.btn_star_big_off);
        }

        btnVer.setTag(pedido);
        btnVer.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
            int idPedido = ((Pedido) v.getTag()).getId();
            Intent intentDetalle = new Intent(context,MainActivity.class);
            intentDetalle.putExtra("idPedido",idPedido);
            context.startActivity(intentDetalle);
            }
        });

        switch (pedido.getEstado()){
            case CONFIRMADO:
                btnEstado.setText("Preparar");
                btnEstado.setEnabled(true);
                break;
            case EN_PREPARACION:
                btnEstado.setText("Preparando....");
                btnEstado.setEnabled(false);
                break;
            case EN_ENVIO:
                btnEstado.setText("Entregar");
                btnEstado.setEnabled(true);
                break;
            default:
                btnEstado.setText("Finalizado");
                btnEstado.setEnabled(false);
                break;
        }

        btnEstado.setTag(pedido);
        btnEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    switch (pedido.getEstado()){
                    case CONFIRMADO:
                        pedido.preparar();
                        ((Button) v).setText("Preparando....");
                        ((Button) v).setEnabled(false);
                        Runnable r = new Runnable() {
                            @Override
                            public void run() {
                                Random r = new Random();
                                int segundos = 5 + r.nextInt(11);
                                try {
                                    Thread.currentThread().sleep(1000 * segundos);
                                    pedido.enviar(); //__INVOCAR METODO NOTIFICAR que realizará en el PASO 3
                                    notificarEnvio(pedido.getNombre());
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        Thread t = new Thread(r);
                        t.start();
                        break;

                    case EN_ENVIO:
                        pedido.entregar();
                        break;
                }
            }
        });
        return fila;
    }

    private void notificarEnvio(String destinatario) {
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this.context, ListaPedidoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this.context, 0, intent, 0);
        Log.d("APP_MY_RESTO", "Creando notificacion");
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this.context, "1")
                .setSmallIcon(android.R.drawable.ic_menu_agenda)
                .setContentTitle("Pedido entregado")
                .setContentText("El pedido para " + destinatario + " ha sido entregado")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this.context);
        notificationManager.notify(100, mBuilder.build());
    }
}
