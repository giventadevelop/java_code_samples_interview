package com.boot2.karat.appointment;

/** Aggregate clinic statistics returned by {@link ClinicManager#getAppointmentStatistics()}. */
public class AppointmentStats {
    public int totalAppointments;
    public int completedAppointments;
    public double noShowRate;

    public AppointmentStats(int totalAppointments, int completedAppointments, double noShowRate) {
        this.totalAppointments = totalAppointments;
        this.completedAppointments = completedAppointments;
        this.noShowRate = noShowRate;
    }
}
