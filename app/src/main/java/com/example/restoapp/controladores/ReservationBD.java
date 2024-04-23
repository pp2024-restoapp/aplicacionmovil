package com.example.restoapp.controladores;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.util.Log;

import com.example.restoapp.modelos.Reservation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservationBD extends SQLiteOpenHelper implements IReservationBD {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ReservationsDB";
    private static final String TABLE_RESERVATIONS = "reservas";

    public ReservationBD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableSQL = "CREATE TABLE " + TABLE_RESERVATIONS + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
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
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    new Date (cursor.getLong(3)),
                    cursor.getString(4),
                    cursor.getInt(5),
                    cursor.getString(6),
                    cursor.getString(7)
            );
            cursor.close();
            return reservation;

        }

        return null;
    }

    @Override
    public List<Reservation> lista() {
        List<Reservation> reserveList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_RESERVATIONS + " ORDER BY _id DESC";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Reservation reservation = new Reservation(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        new Date(cursor.getLong(3)),
                        cursor.getString(4),
                        cursor.getInt(5),
                        cursor.getString(6),
                        cursor.getString(7)
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
    public void actualizar(int id, Reservation reserve) {

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

}
