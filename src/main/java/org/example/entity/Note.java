package org.example.entity;

import com.opencsv.bean.CsvBindByName;
import org.example.Constants;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;
import java.util.UUID;
@XmlRootElement
public class Note {
    @CsvBindByName(column = Constants.ID)
    UUID id;
    @CsvBindByName(column = Constants.NOTE_HEART_RATE)
    int heartRate;
    @CsvBindByName(column = Constants.NOTE_BLOOD_PRESSURE)
    String bloodPressure;
    public enum MedicationTime {
        BEFORE,
        DURING,
        AFTER
    }
    @CsvBindByName(column = Constants.NOTE_MEDICATION_TIME)
    MedicationTime medicationTime;

    public Note(int heartRate, String bloodPressure, MedicationTime medicationTime) {
        this.id = UUID.randomUUID();
        this.heartRate = heartRate;
        this.bloodPressure = bloodPressure;
        this.medicationTime = medicationTime;
    }

    public Note() {
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public MedicationTime getMedicationTime() {
        return medicationTime;
    }

    public void setMedicationTime(MedicationTime medicationTime) {
        this.medicationTime = medicationTime;
    }

    @Override
    public String toString() {
        return "Note{" +
                "heartRate=" + heartRate +
                ", bloodPressure='" + bloodPressure + '\'' +
                ", medicationTime=" + medicationTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return heartRate == note.heartRate && Objects.equals(bloodPressure, note.bloodPressure) && medicationTime == note.medicationTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(heartRate, bloodPressure, medicationTime);
    }
}
