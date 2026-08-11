package com.boot2.karat.clinic.solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Corrected ClinicManager for the Karat clinic appointment interview question.
 */
public class ClinicManager {

    public Map<Integer, Doctor> doctors;
    public List<Appointment> appointments;

    public ClinicManager() {
        this.doctors = new HashMap<>();
        this.appointments = new ArrayList<>();
    }

    public void addDoctor(Doctor doctor) {
        doctors.put(doctor.doctorId, doctor);
    }

    public void addAppointment(Appointment appointment) {
        if (doctors.containsKey(appointment.doctorId)) {
            appointments.add(appointment);
        }
    }

    public AppointmentStats getAppointmentStatistics() {
        int total = appointments.size();
        int completed = 0;
        int noShows = 0;

        for (Appointment a : appointments) {
            if (a.status == AppointmentStatus.COMPLETED) {
                completed++;
            } else if (a.status == AppointmentStatus.NO_SHOW) {
                noShows++;
            }
        }

        double noShowRate = total == 0 ? 0.0 : (double) noShows / total;
        return new AppointmentStats(total, completed, noShowRate);
    }

    /**
     * Returns average completed-appointment duration (minutes) by type for one doctor.
     * Only COMPLETED appointments are counted. Types with zero completed appointments
     * are omitted. Returns an empty map for unknown doctors or no completed appointments.
     */
    public Map<AppointmentType, Double> getAverageAppointmentDurationByType(int doctorId) {
        Map<AppointmentType, Double> averages = new HashMap<>();

        if (!doctors.containsKey(doctorId)) {
            return averages;
        }

        Map<AppointmentType, Integer> totals = new HashMap<>();
        Map<AppointmentType, Integer> counts = new HashMap<>();

        for (Appointment a : appointments) {
            if (a.doctorId != doctorId) {
                continue;
            }
            if (a.status != AppointmentStatus.COMPLETED) {
                continue;
            }

            totals.merge(a.appointmentType, a.durationMinutes, Integer::sum);
            counts.merge(a.appointmentType, 1, Integer::sum);
        }

        for (Map.Entry<AppointmentType, Integer> entry : totals.entrySet()) {
            AppointmentType type = entry.getKey();
            int count = counts.get(type);
            averages.put(type, (double) entry.getValue() / count);
        }

        return averages;
    }
}
