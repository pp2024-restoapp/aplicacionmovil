    package com.example.restoapp;

    import android.app.AlertDialog;
    import android.content.Context;
    import android.content.DialogInterface;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ArrayAdapter;
    import android.widget.ImageView;
    import android.widget.TextView;

    import com.example.restoapp.controladores.ReservationBD;
    import com.example.restoapp.modelos.Reservation;

    import java.text.SimpleDateFormat;
    import java.util.Date;
    import java.util.List;

    public class ReservationAdapter extends ArrayAdapter<Reservation> {
        private int layoutResourceId;
        private ReservationBD reservationBD;
        private Listener listener; // Listener para notificar eventos

        public interface Listener {
            void onReservationDeleted(int reservationId);
        }

        public ReservationAdapter(Context context, int layoutResourceId, List<Reservation> data) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.reservationBD = new ReservationBD(context);
        }

        // Método para establecer el listener
        public void setListener(Listener listener) {
            this.listener = listener;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ReservationHolder holder = null;

            if (row == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                row = inflater.inflate(layoutResourceId, parent, false);

                holder = new ReservationHolder();
                holder.textViewId = row.findViewById(R.id.textViewId);
                holder.textViewPersonas = row.findViewById(R.id.textViewPersonas);
                holder.textViewFecha = row.findViewById(R.id.textViewFecha);
                //holder.textViewCreada = row.findViewById(R.id.textViewCreada);
                holder.textViewTipo = row.findViewById(R.id.textViewTipo);
                holder.textViewMesa = row.findViewById(R.id.textViewMesa);
                holder.textViewObservacion = row.findViewById(R.id.textViewObservacion);
                holder.textViewEstado = row.findViewById(R.id.textViewEstado);
                holder.trash = row.findViewById(R.id.trash); // ImageView del ícono "trash"

                row.setTag(holder);
            } else {
                holder = (ReservationHolder) row.getTag();
            }

            Reservation reservation = getItem(position);
            holder.textViewId.setText("# " + reservation.getId());
            holder.textViewPersonas.setText("Personas: " + reservation.getNumber_of_people());
            holder.textViewFecha.setText("Fecha: " + reservation.getDateAndTime());


            Date createdDate = reservation.getCreated();
            Log.d("ReservationAdapter", "Fecha de creación: " + createdDate);


            // Dentro del método getView en ReservationAdapter
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String formattedDate = sdf.format(reservation.getCreated());

//            if (formattedDate != null) {
//                holder.textViewCreada.setText("Creada el: " + formattedDate);
//                Log.d("ReservationAdapter", "Fecha formateada correctamente: " + formattedDate);
//            } else {
//                holder.textViewCreada.setText("Fecha no disponible");
//                Log.e("ReservationAdapter", "Error al formatear la fecha");
//            }
            holder.textViewTipo.setText("Tipo: " + reservation.getType());
            holder.textViewMesa.setText("Mesa: " + reservation.getTable());
            holder.textViewObservacion.setText("Observacion: " + reservation.getObservations());
            holder.textViewEstado.setText("Estado: " + reservation.getStatus());

            // Establece el clic del ícono "trash" para eliminar la reserva
            holder.trash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDeleteConfirmationDialog(getContext(), reservation.getId());
                }
            });

            return row;
        }

        private void showDeleteConfirmationDialog(Context context, final int reservationId) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Eliminar Reserva");
            builder.setMessage("¿Está seguro de que desea eliminar esta reserva?");
            builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Llama a la función borrar en tu BD
                    reservationBD.borrar(reservationId);

                    if (listener != null) {
                        listener.onReservationDeleted(reservationId);
                    }
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // No hagas nada, simplemente cierra el diálogo
                }
            });
            builder.create().show();
        }

        static class ReservationHolder {
            public ImageView trash;
            TextView textViewId;
            TextView textViewPersonas;
            TextView textViewFecha;
            TextView textViewCreada;
            TextView textViewTipo;
            TextView textViewMesa;
            TextView textViewObservacion;
            TextView textViewEstado;
        }
    }

