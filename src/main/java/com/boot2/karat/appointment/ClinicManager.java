package com.boot2.karat.appointment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages doctors and appointments and computes clinic statistics.
 *
 * <h2>Bug that failed the interview tests</h2>
 * The original {@link #getAverageAppointmentDurationByType(int)} had three mistakes:
 * <ol>
 *   <li><b>Integer division</b> — {@code duration / count} truncated {@code 71 / 2} to
 *       {@code 35} instead of {@code 35.5}. Cast with {@code (double)}.</li>
 *   <li><b>Empty types still inserted</b> — types with zero completed visits were put
 *       into the map with {@code 0.0}. Spec: only include types that have at least one
 *       completed appointment.</li>
 *   <li><b>Unknown doctor</b> — missing {@code doctorId} still returned a map of zeros.
 *       Spec: return an empty map when the doctor is unknown or has no completed visits.</li>
 * </ol>
 */
public class ClinicManager {

    public Map<Integer, Doctor> doctors;
    public List<Appointment> appointments;

    public ClinicManager() {
        doctors = new HashMap<>();
        appointments = new ArrayList<>();
    }

    /** Registers a doctor so appointments for that id can be accepted. */
    public void addDoctor(Doctor doctor) {
        doctors.put(doctor.doctorId, doctor);
    }

    /**
     * Adds an appointment only when its {@code doctorId} is already registered.
     * Unknown doctors are ignored (appointment is not stored).
     */
    public void addAppointment(Appointment appointment) {
        if (!doctors.containsKey(appointment.doctorId)) {
            return;
        }
        appointments.add(appointment);
    }

    /**
     * Clinic-wide totals: appointment count, completed count, and no-show rate.
     * No-show rate = {@code noShows / total} using floating-point division.
     */
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

        double noShowRate = total > 0 ? (double) noShows / total : 0.0;
        return new AppointmentStats(total, completed, noShowRate);
    }

    /**
     * Average duration (minutes) of <em>completed</em> appointments for one doctor,
     * grouped by {@link AppointmentType}.
     *
     * <p>Rules:</p>
     * <ul>
     *   <li>Only {@link AppointmentStatus#COMPLETED} appointments count.</li>
     *   <li>A type appears in the map only if that doctor has ≥ 1 completed visit of that type.</li>
     *   <li>Unknown doctor, or no completed appointments → empty map.</li>
     * </ul>
     *
     * @param doctorId doctor to summarize
     * @return map of type → average minutes; never {@code null}
     */
    public Map<AppointmentType, Double> getAverageAppointmentDurationByType(int doctorId) {
        Map<AppointmentType, Double> averages = new HashMap<>();

        if (!doctors.containsKey(doctorId)) {
            return averages;
        }

        Map<AppointmentType, Integer> totalMinutes = new HashMap<>();
        Map<AppointmentType, Integer> counts = new HashMap<>();

        for (Appointment a : appointments) {
            if (a.doctorId != doctorId) {
                continue;
            }
            if (a.status != AppointmentStatus.COMPLETED) {
                continue;
            }
            totalMinutes.merge(a.appointmentType, a.durationMinutes, Integer::sum);
            counts.merge(a.appointmentType, 1, Integer::sum);
        }

        for (Map.Entry<AppointmentType, Integer> entry : totalMinutes.entrySet()) {
            AppointmentType type = entry.getKey();
            int count = counts.get(type);
            // Must use floating-point division: (30 + 41) / 2 = 35.5, not 35
            averages.put(type, (double) entry.getValue() / count);
        }

        return averages;
    }
}
