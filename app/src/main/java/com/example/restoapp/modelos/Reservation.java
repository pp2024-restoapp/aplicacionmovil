package com.example.restoapp.modelos;

import java.util.Date;

public class Reservation {
    private int id;
    private int number_of_people;
    private String dateAndTime;
    private Date created;
    private String type;
    private int table;
    private String observations;
    private String status;

    public Reservation(int id, int number_of_people, String dateAndTime,  Date created, String type, int table, String observations, String status) {
        this.id = id;
        this.number_of_people = number_of_people;
        this.dateAndTime = dateAndTime;
        if (created != null) {
            this.created = created;
        } else {
            this.created = new Date(); // Establece la fecha actual si created es null
        }
        this.type = type;
        this.table = table;
        this.observations = observations;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber_of_people() {
        return number_of_people;
    }

    public void setNumber_of_people(int number_of_people) {
        this.number_of_people = number_of_people;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTable() {
        return table;
    }

    public void setTable(int table) {
        this.table = table;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", number_of_people=" + number_of_people +
                ", dateAndTime=" + dateAndTime +
                ", created=" + created +
                ", type='" + type + '\'' +
                ", table=" + table +
                ", observations='" + observations + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
