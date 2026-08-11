package com.boot2.karat.appointment;

import java.util.Map;

/**
 * Corrected Karat appointment tests (task 1-2 and task 2).
 *
 * <p>Replaces the broken nested {@code AppointMentTest} / {@code Solution} structure.
 * Uses explicit {@link AssertionError} checks so failures show up without {@code -ea}.</p>
 *
 * <p>Run: {@code java -cp target/classes com.boot2.karat.appointment.AppointMentTest}</p>
 */
public class AppointMentTest {

    /*
     * We are building the back-end for a clinic appointment management system.
     * The system tracks doctors and appointments.
     *
     * Definitions:
     * * A "doctor" has: doctorId, name.
     * * An "appointment" has: appointmentId, doctorId, patientId,
     *   durationMinutes, status, appointmentType.
     * * AppointmentStatus: SCHEDULED, COMPLETED, CANCELLED, NO_SHOW.
     * * AppointmentType: CONSULTATION, FOLLOWUP, EMERGENCY.
     * * ClinicManager manages doctors, appointments, and statistics.
     *
     * Tasks:
     * 1-1) Read and understand the model / ClinicManager.
     * 1-2) Fix the bug that made ClinicManager tests fail.
     * 2)   Implement getAverageAppointmentDurationByType (COMPLETED only,
     *      per-type averages, omit empty types, empty map for unknown/no data).
     */

    public static void main(String[] args) {
        testGetAppointmentStatistics();
        testGetAverageAppointmentDurationByType();
        System.out.println("All tests pass!");
    }

    public static void testGetAppointmentStatistics() {
        System.out.println("Running testGetAppointmentStatistics");
        ClinicManager cm = new ClinicManager();
        cm.addDoctor(new Doctor(10, "dr_smith"));

        cm.addAppointment(new Appointment(1, 10, 100, 30, AppointmentStatus.COMPLETED, AppointmentType.CONSULTATION));
        cm.addAppointment(new Appointment(2, 10, 101, 45, AppointmentStatus.COMPLETED, AppointmentType.FOLLOWUP));
        cm.addAppointment(new Appointment(3, 10, 102, 30, AppointmentStatus.NO_SHOW, AppointmentType.EMERGENCY));
        cm.addAppointment(new Appointment(4, 10, 103, 60, AppointmentStatus.CANCELLED, AppointmentType.CONSULTATION));
        cm.addAppointment(new Appointment(5, 10, 104, 30, AppointmentStatus.SCHEDULED, AppointmentType.FOLLOWUP));

        AppointmentStats stats = cm.getAppointmentStatistics();
        assertEquals(5, stats.totalAppointments, "totalAppointments");
        assertEquals(2, stats.completedAppointments, "completedAppointments");
        assertEquals(0.2, stats.noShowRate, 1e-4, "noShowRate");
    }

    public static void testGetAverageAppointmentDurationByType() {
        System.out.println("Running testGetAverageAppointmentDurationByType");
        ClinicManager cm = new ClinicManager();
        cm.addDoctor(new Doctor(1, "dr_smith"));
        cm.addDoctor(new Doctor(2, "dr_jones"));
        cm.addDoctor(new Doctor(3, "dr_brown"));
        cm.addDoctor(new Doctor(4, "dr_lee"));
        cm.addDoctor(new Doctor(5, "dr_kim"));

        cm.addAppointment(new Appointment(1, 1, 100, 30, AppointmentStatus.COMPLETED, AppointmentType.CONSULTATION));
        cm.addAppointment(new Appointment(2, 1, 101, 41, AppointmentStatus.COMPLETED, AppointmentType.CONSULTATION));
        cm.addAppointment(new Appointment(3, 1, 102, 20, AppointmentStatus.COMPLETED, AppointmentType.FOLLOWUP));
        // not COMPLETED -> excluded from averages
        cm.addAppointment(new Appointment(5, 1, 105, 100, AppointmentStatus.CANCELLED, AppointmentType.CONSULTATION));
        cm.addAppointment(new Appointment(4, 2, 103, 40, AppointmentStatus.COMPLETED, AppointmentType.EMERGENCY));
        cm.addAppointment(new Appointment(6, 4, 106, 25, AppointmentStatus.COMPLETED, AppointmentType.CONSULTATION));
        cm.addAppointment(new Appointment(7, 5, 107, 15, AppointmentStatus.COMPLETED, AppointmentType.FOLLOWUP));

        Map<AppointmentType, Double> avg1 = cm.getAverageAppointmentDurationByType(1);
        assertEquals(35.5, avg1.get(AppointmentType.CONSULTATION), 1e-4, "Expected 35.5 for CONSULTATION");
        assertEquals(20.0, avg1.get(AppointmentType.FOLLOWUP), 1e-4, "Expected 20.0 for FOLLOWUP");
        assertFalse(avg1.containsKey(AppointmentType.EMERGENCY), "EMERGENCY should not be in avg1");

        Map<AppointmentType, Double> avg2 = cm.getAverageAppointmentDurationByType(2);
        assertEquals(40.0, avg2.get(AppointmentType.EMERGENCY), 1e-4, "Expected 40.0 for EMERGENCY");
        assertFalse(avg2.containsKey(AppointmentType.CONSULTATION), "CONSULTATION should not be in avg2");
        assertFalse(avg2.containsKey(AppointmentType.FOLLOWUP), "FOLLOWUP should not be in avg2");

        Map<AppointmentType, Double> avg4 = cm.getAverageAppointmentDurationByType(4);
        assertEquals(25.0, avg4.get(AppointmentType.CONSULTATION), 1e-4, "Expected 25.0 for CONSULTATION");
        assertFalse(avg4.containsKey(AppointmentType.FOLLOWUP), "FOLLOWUP should not be in avg4");
        assertFalse(avg4.containsKey(AppointmentType.EMERGENCY), "EMERGENCY should not be in avg4");

        Map<AppointmentType, Double> avg5 = cm.getAverageAppointmentDurationByType(5);
        assertEquals(15.0, avg5.get(AppointmentType.FOLLOWUP), 1e-4, "Expected 15.0 for FOLLOWUP");
        assertFalse(avg5.containsKey(AppointmentType.CONSULTATION), "CONSULTATION should not be in avg5");
        assertFalse(avg5.containsKey(AppointmentType.EMERGENCY), "EMERGENCY should not be in avg5");

        assertTrue(cm.getAverageAppointmentDurationByType(3).isEmpty(),
                "Expected empty map for doctor with no appointments");
        assertTrue(cm.getAverageAppointmentDurationByType(999).isEmpty(),
                "Expected empty map for unknown doctorId");
    }

    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " — expected " + expected + ", was " + actual);
        }
    }

    private static void assertEquals(double expected, Double actual, double delta, String message) {
        if (actual == null || Math.abs(expected - actual) > delta) {
            throw new AssertionError(message + " — expected " + expected + ", was " + actual);
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void assertFalse(boolean condition, String message) {
        if (condition) {
            throw new AssertionError(message);
        }
    }
}
