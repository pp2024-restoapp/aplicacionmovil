package com.example.restoapp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.restoapp.controladores.ReservationBD;
import com.example.restoapp.modelos.Reservation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReservasFragment extends Fragment implements DatePickerFragment.DateSelectionListener, TimePickerFragment.TimeSelectionListener, ReservationAdapter.Listener {
    private ListView listView;
    private ArrayList<Integer> idReserve;
    private String userUid;
    private ReservationBD reservationBD;
    private Context context;
    private int selectedYear;
    private int selectedMonth;
    private int selectedDay;
    private int selectedHour;
    private int selectedMinute;
    private ReservationAdapter adapter;
    private List<Reservation> reservationList;
    private TextView noReservationsText;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            userUid = currentUser.getUid();
        } else {
            // El usuario no está autenticado, maneja este caso según lo necesites
            // Por ejemplo, redirecciona a la pantalla de inicio de sesión
        }
        reservationBD = new ReservationBD(context, userUid);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = requireContext();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            userUid = currentUser.getUid();
        } else {
            // El usuario no está autenticado, maneja este caso según lo necesites
            // Por ejemplo, redirecciona a la pantalla de inicio de sesión
        }

        reservationBD = new ReservationBD(context, userUid);
        reservationList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservas, container, false);
        listView = view.findViewById(R.id.reservation_list);
        noReservationsText = view.findViewById(R.id.noReservationsText);


        idReserve = new ArrayList<>();
        reservationList = new ArrayList<>();
        adapter = new ReservationAdapter(requireActivity(), R.layout.reservation_item, reservationList);
        adapter.setListener(this);

        listView.setAdapter(adapter);

        fillListView();

        Button botonAgregarReserva = view.findViewById(R.id.botonAgregarReserva);
        botonAgregarReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogAgregarReserva();
            }
        });

        return view;
    }

    private void fillListView() {
        reservationList.clear();
        reservationList.addAll(reservationBD.lista());
        adapter.notifyDataSetChanged();


        List<Reservation> reservationList = reservationBD.lista();

        //ReservationAdapter adapter = new ReservationAdapter(requireActivity(), R.layout.reservation_item, reservationList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Reservation reservation = reservationList.get(i);
                Bundle bolsa = new Bundle();
                bolsa.putInt("id", reservation.getId());
                bolsa.putInt("number_of_people", reservation.getNumber_of_people());
                bolsa.putString("dateAndTime", reservation.getDateAndTime());
                bolsa.putLong("created", reservation.getCreated().getTime());
                bolsa.putString("type",reservation.getType());
                bolsa.putInt("table", reservation.getTable());
                bolsa.putString("observations", reservation.getObservations());
                bolsa.putString("status", reservation.getStatus());
            }
        });

        if (reservationList.isEmpty()) {
            noReservationsText.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        } else {
            noReservationsText.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }

    }

    private void mostrarDialogAgregarReserva() {
        // Inflar el diseño del diálogo
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_reservation, null);

        // Recuperar las mesas seleccionadas de la base de datos
        List<Integer> mesasSeleccionadas = reservationBD.obtenerMesasSeleccionadas();

        // Iterar sobre cada mesa en el diseño del diálogo
        for (int i = 1; i <= 12; i++) {
            String cardViewId = "mesa" + i + "Container";
            int resId = getResources().getIdentifier(cardViewId, "id", requireContext().getPackageName());
            CardView cardView = dialogView.findViewById(resId);

            if (mesasSeleccionadas.contains(i)) {
                if (cardView != null) {
                    cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.gris));
                    Log.d("MesasSeleccionadas", "La mesa " + i + " está seleccionada.");
                }
            } else {
                if (cardView != null) {
                    cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.principal_naranja)); // Color naranja para no seleccionadas
                }
            }
        }

        Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView); // Establecer el diseño inflado en el diálogo
        TextView btnClose = dialog.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ReservasFragment", "Close button clicked");
                dialog.dismiss();
            }
        });
        Button selectDateTimeButton = dialog.findViewById(R.id.dateTimeButton);

        selectDateTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePickerDialog();
            }
        });


        dialog.getWindow().setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );

        Button typeOfReservationButton = dialog.findViewById(R.id.typeOfReservation);
        Spinner typeOfReservationSpinner = dialog.findViewById(R.id.typeOfReservationSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.type_of_reservations, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeOfReservationSpinner.setAdapter(adapter);

        typeOfReservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeOfReservationSpinner.getVisibility() == View.VISIBLE) {
                    typeOfReservationSpinner.setVisibility(View.GONE);
                } else {
                    typeOfReservationSpinner.setVisibility(View.VISIBLE);
                }
            }
        });

        Button cantPersonasButton = dialog.findViewById(R.id.number_of_people);
        EditText numberOfPeopleEditText = dialog.findViewById(R.id.numberOfPeopleEditText);

        cantPersonasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfPeopleEditText.getVisibility() == View.VISIBLE) {
                    numberOfPeopleEditText.setVisibility(View.GONE);
                } else {
                    numberOfPeopleEditText.setVisibility(View.VISIBLE);
                }
            }
        });

        Button selectTableButton = dialog.findViewById(R.id.select_table);
        EditText selectTableEditText = dialog.findViewById(R.id.selectTableEditText);

        selectTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectTableEditText.getVisibility() == View.VISIBLE) {
                    selectTableEditText.setVisibility(View.GONE);
                } else {
                    selectTableEditText.setVisibility(View.VISIBLE);
                }
            }
        });


        selectDateTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePickerDialog();
            }
        });

        Button botonConfirmarReserva = dialog.findViewById(R.id.botonConfirmarReserva);
        // ...

        botonConfirmarReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner typeOfReservationSpinner = dialog.findViewById(R.id.typeOfReservationSpinner);
                EditText numberOfPeopleEditText = dialog.findViewById(R.id.numberOfPeopleEditText);
                EditText selectTableEditText = dialog.findViewById(R.id.selectTableEditText);
                EditText observationsEditText = dialog.findViewById(R.id.reservation_comment);

                String typeOfReservation = typeOfReservationSpinner.getSelectedItem().toString();
                int numberOfPeople = 0;
                int selectedTable = 0;
                String observacion = observationsEditText.getText().toString();


                try {
                    numberOfPeople = Integer.parseInt(numberOfPeopleEditText.getText().toString());
                    selectedTable = Integer.parseInt(selectTableEditText.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Ingrese valores numéricos válidos", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (typeOfReservation.isEmpty()) {
                    Toast.makeText(getContext(), "Debe seleccionar un tipo de reserva", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (numberOfPeople <= 0) {
                    Toast.makeText(getContext(), "Debe ingresar un número de personas válido", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (selectedTable <= 0) {
                    Toast.makeText(getContext(), "Debe ingresar un número de mesa válido", Toast.LENGTH_SHORT).show();
                    return;
                }

                int year = selectedYear;
                int month = selectedMonth;
                int day = selectedDay;
                int hour = selectedHour;
                int minute = selectedMinute;

                // Comprueba si alguna de las variables de fecha es cero
                if (year == 0 || month == 0 || day == 0 || hour == 0 || minute == 0) {
                    Toast.makeText(getContext(), "Debe ingresar una fecha y hora válidas", Toast.LENGTH_SHORT).show();
                    return;
                }

                String dateAndTime = String.format("%04d-%02d-%02d %02d:%02d", year, month, day, hour, minute);

                Date createdDate = new Date();
                Reservation newReservation = new Reservation(0, userUid, numberOfPeople, dateAndTime, createdDate,
                        typeOfReservation, selectedTable, observacion, "Pendiente");

                reservationBD.agregar(newReservation);
                actualizarListaReservas();
                dialog.dismiss();
            }
        });


        dialog.show();
    }



    private void showDateTimePickerDialog() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getParentFragmentManager(), "datePicker");
        datePickerFragment.dateListener = new DatePickerFragment.DateSelectionListener() {
            @Override
            public void onDateSelected(int year, int month, int day) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, day);

                Calendar currentDate = Calendar.getInstance();

                if (selectedDate.before(currentDate)) {
                    Toast.makeText(getContext(), "Debe seleccionar una fecha futura", Toast.LENGTH_SHORT).show();
                } else {
                    selectedYear = year;
                    selectedMonth = month;
                    selectedDay = day;
                    showTimePickerDialog();
                }
            }
        };
    }

    private void actualizarListaReservas() {
        fillListView();
    }

    public void onReservationDeleted(int reservationId) {
        reservationBD.borrar(reservationId);
        actualizarListaReservas();
    }



    private void showTimePickerDialog() {
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.show(getParentFragmentManager(), "timePicker");
        timePickerFragment.timeListener = new TimePickerFragment.TimeSelectionListener() {
            @Override
            public void onTimeSelected(int hour, int minute) {
                selectedHour = hour;
                selectedMinute = minute;
            }
        };
    }

    @Override
    public void onDateSelected(int year, int month, int dayOfMonth) {
        selectedYear = year;
        selectedMonth = month;
        selectedDay = dayOfMonth;
    }

    @Override
    public void onTimeSelected(int hour, int minute) {
        selectedHour = hour;
        selectedMinute = minute;

    }



}

