package com.example.restoapp.controladores;

import com.example.restoapp.modelos.Reservation;

import java.util.List;

public interface IReservationBD {

    Reservation elemento(int id);

    List<Reservation> lista();

    void agregar(Reservation reserve);
    void actualizar(int id, Reservation reserve);
    void borrar(int id);
}
