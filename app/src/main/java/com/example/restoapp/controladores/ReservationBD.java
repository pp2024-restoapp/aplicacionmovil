package com.example.restoapp.controladores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.restoapp.modelos.Reservation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservationBD extends SQLiteOpenHelper implements IReservationBD {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ReservationsDB";
    private static final String TABLE_RESERVATIONS = "reservas";

    private String userUid;

    public ReservationBD(Context context, String userUid) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.userUid = userUid; // Asignar userUid al constructor
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableSQL = "CREATE TABLE " + TABLE_RESERVATIONS + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userUid TEXT, " +
                "personas INTEGER, " +
                "fecha TEXT, " +
                "creada DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "tipo TEXT, " +
                "mesa INTEGER, " +
                "observacion TEXT, " +
                "estado TEXT DEFAULT 'Pendiente')";

        sqLiteDatabase.execSQL(createTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Método necesario para actualizar la base de datos, no implementado en esta solución
    }

    @Override
    public Reservation elemento(int id) {
        SQLiteDatabase database = this.getReadableDatabase();
        String[] columns = {
                "_id", "personas", "fecha", "creada", "tipo", "mesa", "observacion", "estado"
        };
        String selection = "_id = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = database.query(
                TABLE_RESERVATIONS,
                columns,
                selection,
                selectionArgs,
                null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            Reservation reservation = new Reservation(
                    cursor.getInt(0),                   // id
                    cursor.getString(1),                   // userID
                    cursor.getInt(2),                   // number_of_people
                    cursor.getString(3),                // dateAndTime
                    new Date(cursor.getLong(4)),        // created
                    cursor.getString(5),                // type
                    cursor.getInt(6),                   // table
                    cursor.getString(7),                // observations
                    cursor.getString(8)                 // status
            );

            cursor.close();
            return reservation;

        }

        return null;
    }

    @Override
    public List<Reservation> lista() {
        List<Reservation> reserveList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        String[] columns = {
                "_id", "userUid", "personas", "fecha", "creada", "tipo", "mesa", "observacion", "estado"
        };
        String selection = "userUid = ?";
        String[] selectionArgs = { userUid };

        Cursor cursor = database.query(
                TABLE_RESERVATIONS,
                columns,
                selection,
                selectionArgs,
                null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Reservation reservation = new Reservation(
                        cursor.getInt(0),                   // id
                        cursor.getString(1),                   // userID
                        cursor.getInt(2),                   // number_of_people
                        cursor.getString(3),                // dateAndTime
                        new Date(cursor.getLong(4)),        // created
                        cursor.getString(5),                // type
                        cursor.getInt(6),                   // table
                        cursor.getString(7),                // observations
                        cursor.getString(8)                 // status
                );

                reserveList.add(reservation);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return reserveList;
    }

    @Override
    public void agregar(Reservation reserva) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("userUid", reserva.getUserUid());
        values.put("personas", reserva.getNumber_of_people());
        values.put("fecha", reserva.getDateAndTime());
        values.put("tipo", reserva.getType());
        values.put("mesa", reserva.getTable());
        values.put("observacion", reserva.getObservations());
        values.put("estado", reserva.getStatus());

        long id = database.insert(TABLE_RESERVATIONS, null, values);

        if (id != -1) {
            Log.d("Database", "Reserva insertada con éxito. ID: " + id);
        } else {
            Log.e("Database", "Error al insertar la reserva en la base de datos.");
        }

        database.close();
    }

    @Override
    public void actualizar(int id, Reservation reserva) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("personas", reserva.getNumber_of_people());
        values.put("fecha", reserva.getDateAndTime());
        values.put("tipo", reserva.getType());
        values.put("mesa", reserva.getTable());
        values.put("observacion", reserva.getObservations());
        values.put("estado", reserva.getStatus());

        String whereClause = "_id = ?";
        String[] whereArgs = {String.valueOf(id)};

        int updatedRows = database.update(TABLE_RESERVATIONS, values, whereClause, whereArgs);
        database.close();

        if (updatedRows > 0) {
            Log.d("Database", "Reserva actualizada con éxito. ID: " + id);
        } else {
            Log.e("Database", "Error al actualizar la reserva en la base de datos. ID: " + id);
        }
    }
    @Override
    public void borrar(int id) {
        SQLiteDatabase database = this.getWritableDatabase();
        String whereClause = "_id = ?";
        String[] whereArgs = {String.valueOf(id)};

        int deletedRows = database.delete(TABLE_RESERVATIONS, whereClause, whereArgs);
        database.close();

        if (deletedRows > 0) {
            Log.d("Database", "Reserva eliminada con éxito. ID: " + id);
        } else {
            Log.e("Database", "Error al eliminar la reserva de la base de datos. ID: " + id);
        }
    }

    public List<Integer> obtenerMesasSeleccionadas() {
        List<Integer> mesasSeleccionadas = new ArrayList<>();

        // Obtener una instancia de base de datos SQLiteDatabase para leer los datos
        SQLiteDatabase database = this.getReadableDatabase();

        // Definir las columnas que deseas recuperar de la tabla
        String[] columns = {"mesa"};

        // Realizar una consulta a la base de datos para obtener todas las mesas seleccionadas
        Cursor cursor = database.query(
                TABLE_RESERVATIONS,
                columns,
                null, // No hay condiciones de selección, así que seleccionamos todas las filas
                null,
                null, null, null);

        // Iterar sobre el cursor para obtener cada valor de la columna "mesa"
        if (cursor.moveToFirst()) {
            do {
                int mesa = cursor.getInt(0);
                if (mesa <= 12) { // Asumiendo que el número total de mesas es 12
                    mesasSeleccionadas.add(mesa);
                }
                // Agrega un log para imprimir el valor de la mesa obtenido
                Log.d("MesasSeleccionadas", "Mesa seleccionadaAAAA: " + mesa);
            } while (cursor.moveToNext());
        } else {
            Log.d("MesasSeleccionadas", "No se encontraron mesas seleccionadas");
        }

        // Cerrar el cursor y la base de datos
        cursor.close();
        database.close();

        return mesasSeleccionadas;
    }

}




