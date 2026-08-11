package com.boot2.karat.clinic.problem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Interview "problem" version — {@link #getAverageAppointmentDurationByType(int)} is buggy.
 * See the solution package for the corrected implementation.
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
        for (Appointment a : appointments) {
            if (a.status == AppointmentStatus.COMPLETED) {
                completed++;
            }
        }

        int noShows = 0;
        for (Appointment a : appointments) {
            if (a.status == AppointmentStatus.NO_SHOW) {
                noShows++;
            }
        }

        // Bug: integer division when both operands are int (e.g. 1/5 -> 0)
        double noShowRate = noShows / total;
        return new AppointmentStats(total, completed, noShowRate);
    }

    /**
     * Buggy interview attempt — fails with "Expected 35.5 for CONSULTATION".
     * Problems:
     * <ul>
     *   <li>does not filter by {@code doctorId}</li>
     *   <li>does not filter by {@code appointmentType}</li>
     *   <li>uses {@code duration++} instead of summing {@code durationMinutes}</li>
     *   <li>integer division for average</li>
     *   <li>puts entries even when count is zero</li>
     * </ul>
     */
    public Map<AppointmentType, Double> getAverageAppointmentDurationByType(int doctorId) {
        Doctor doctor = doctors.get(doctorId);
        Map<AppointmentType, Double> avgAppointmentType = new HashMap<>();

        for (AppointmentType type : AppointmentType.values()) {
            int counter = 0;
            int duration = 0;
            for (Appointment a : appointments) {
                if (a.status == AppointmentStatus.COMPLETED) {
                    counter++;
                    duration++;
                }
            }
            double average = duration / counter;
            System.out.println("counter" + counter);
            System.out.println("average" + average);
            avgAppointmentType.put(type, average);
        }
        return avgAppointmentType;
    }
}
