package com.boot2.karat.clinic.solution;

import java.util.Map;

/**
 * Runs the Karat clinic interview assertions against the corrected {@link ClinicManager}.
 */
public class SolutionDemo {

    public static void main(String[] args) {
        testGetAppointmentStatistics();
        testGetAverageAppointmentDurationByType();
        System.out.println("All tests pass!");
    }

    static void testGetAppointmentStatistics() {
        ClinicManager manager = new ClinicManager();
        manager.addDoctor(new Doctor(10, "Dr. Smith"));

        manager.addAppointment(new Appointment(1, 10, 100, 30, AppointmentStatus.COMPLETED, AppointmentType.CONSULTATION));
        manager.addAppointment(new Appointment(2, 10, 101, 45, AppointmentStatus.COMPLETED, AppointmentType.FOLLOWUP));
        manager.addAppointment(new Appointment(3, 10, 102, 30, AppointmentStatus.NO_SHOW, AppointmentType.EMERGENCY));
        manager.addAppointment(new Appointment(4, 10, 103, 60, AppointmentStatus.COMPLETED, AppointmentType.CONSULTATION));
        manager.addAppointment(new Appointment(5, 10, 104, 30, AppointmentStatus.SCHEDULED, AppointmentType.FOLLOWUP));

        AppointmentStats stats = manager.getAppointmentStatistics();
        assertEquals(5, stats.totalAppointments);
        assertEquals(3, stats.completedAppointments);
        assertEquals(0.2, stats.noShowRate, 0.0001);
    }

    static void testGetAverageAppointmentDurationByType() {
        ClinicManager manager = new ClinicManager();
        manager.addDoctor(new Doctor(1, "Dr. A"));
        manager.addDoctor(new Doctor(2, "Dr. B"));
        manager.addDoctor(new Doctor(3, "Dr. C"));
        manager.addDoctor(new Doctor(4, "Dr. D"));
        manager.addDoctor(new Doctor(5, "Dr. E"));

        manager.addAppointment(new Appointment(1, 1, 100, 30, AppointmentStatus.COMPLETED, AppointmentType.CONSULTATION));
        manager.addAppointment(new Appointment(2, 1, 101, 41, AppointmentStatus.COMPLETED, AppointmentType.CONSULTATION));
        manager.addAppointment(new Appointment(3, 1, 102, 20, AppointmentStatus.COMPLETED, AppointmentType.FOLLOWUP));
        manager.addAppointment(new Appointment(4, 2, 103, 40, AppointmentStatus.COMPLETED, AppointmentType.EMERGENCY));
        // not COMPLETED -> excluded from averages
        manager.addAppointment(new Appointment(5, 1, 104, 100, AppointmentStatus.CANCELLED, AppointmentType.CONSULTATION));
        manager.addAppointment(new Appointment(6, 4, 105, 25, AppointmentStatus.COMPLETED, AppointmentType.CONSULTATION));
        manager.addAppointment(new Appointment(7, 5, 106, 15, AppointmentStatus.COMPLETED, AppointmentType.FOLLOWUP));

        Map<AppointmentType, Double> avg1 = manager.getAverageAppointmentDurationByType(1);
        assertEquals(35.5, avg1.get(AppointmentType.CONSULTATION), 0.0001);
        assertEquals(20.0, avg1.get(AppointmentType.FOLLOWUP), 0.0001);
        assertTrue(!avg1.containsKey(AppointmentType.EMERGENCY));

        Map<AppointmentType, Double> avg2 = manager.getAverageAppointmentDurationByType(2);
        assertEquals(40.0, avg2.get(AppointmentType.EMERGENCY), 0.0001);
        assertTrue(!avg2.containsKey(AppointmentType.CONSULTATION));
        assertTrue(!avg2.containsKey(AppointmentType.FOLLOWUP));

        Map<AppointmentType, Double> avg4 = manager.getAverageAppointmentDurationByType(4);
        assertEquals(25.0, avg4.get(AppointmentType.CONSULTATION), 0.0001);

        Map<AppointmentType, Double> avg5 = manager.getAverageAppointmentDurationByType(5);
        assertEquals(15.0, avg5.get(AppointmentType.FOLLOWUP), 0.0001);

        Map<AppointmentType, Double> avg3 = manager.getAverageAppointmentDurationByType(3);
        assertTrue(avg3.isEmpty());

        Map<AppointmentType, Double> avgUnknown = manager.getAverageAppointmentDurationByType(999);
        assertTrue(avgUnknown.isEmpty());
    }

    private static void assertEquals(int expected, int actual) {
        if (expected != actual) {
            throw new AssertionError("Expected " + expected + " but was " + actual);
        }
    }

    private static void assertEquals(double expected, Double actual, double delta) {
        if (actual == null || Math.abs(expected - actual) > delta) {
            throw new AssertionError("Expected " + expected + " but was " + actual);
        }
    }

    private static void assertTrue(boolean condition) {
        if (!condition) {
            throw new AssertionError("Expected condition to be true");
        }
    }
}
