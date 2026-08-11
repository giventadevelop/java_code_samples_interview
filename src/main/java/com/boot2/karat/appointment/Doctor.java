package com.boot2.karat.appointment;

/** Clinic doctor identified by {@code doctorId}. */
public class Doctor {
    public int doctorId;
    public String name;

    public Doctor(int doctorId, String name) {
        this.doctorId = doctorId;
        this.name = name;
    }
}
