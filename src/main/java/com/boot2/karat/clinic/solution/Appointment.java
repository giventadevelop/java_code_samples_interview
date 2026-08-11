package com.boot2.karat.clinic.solution;

public class Appointment {
    public int appointmentId;
    public int doctorId;
    public int patientId;
    public int durationMinutes;
    public AppointmentStatus status;
    public AppointmentType appointmentType;

    public Appointment(
            int appointmentId,
            int doctorId,
            int patientId,
            int durationMinutes,
            AppointmentStatus status,
            AppointmentType appointmentType) {
        this.appointmentId = appointmentId;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.durationMinutes = durationMinutes;
        this.status = status;
        this.appointmentType = appointmentType;
    }
}
