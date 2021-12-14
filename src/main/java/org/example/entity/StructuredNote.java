package org.example.entity;

import com.opencsv.bean.CsvBindByName;
import org.example.Constants;

import java.util.Objects;

public class StructuredNote extends Note{
    @CsvBindByName(column = Constants.NOTE_DYSPNEA)
    String dyspnea;
    @CsvBindByName(column = Constants.NOTE_SWEATING)
    String sweating;
    @CsvBindByName(column = Constants.NOTE_DIZZINESS)
    String dizziness;
    @CsvBindByName(column = Constants.NOTE_STATE_OF_HEALTH)
    String stateOfHealth;

    public StructuredNote(int heartRate, String bloodPressure, MedicationTime medicationTime, String dyspnea, String sweating, String dizziness, String stateOfHealth) {
        super(heartRate, bloodPressure, medicationTime);
        this.dyspnea = dyspnea;
        this.sweating = sweating;
        this.dizziness = dizziness;
        this.stateOfHealth = stateOfHealth;
    }

    public StructuredNote() {
    }

    public String getDyspnea() {
        return dyspnea;
    }

    public void setDyspnea(String dyspnea) {
        this.dyspnea = dyspnea;
    }

    public String getSweating() {
        return sweating;
    }

    public void setSweating(String sweating) {
        this.sweating = sweating;
    }

    public String getDizziness() {
        return dizziness;
    }

    public void setDizziness(String dizziness) {
        this.dizziness = dizziness;
    }

    public String getStateOfHealth() {
        return stateOfHealth;
    }

    public void setStateOfHealth(String stateOfHealth) {
        this.stateOfHealth = stateOfHealth;
    }

    @Override
    public String toString() {
        return "StructuredNote{" +
                "heartRate=" + heartRate +
                ", bloodPressure='" + bloodPressure + '\'' +
                ", medicationTime=" + medicationTime +
                ", dyspnea='" + dyspnea + '\'' +
                ", sweating='" + sweating + '\'' +
                ", dizziness='" + dizziness + '\'' +
                ", stateOfHealth='" + stateOfHealth + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StructuredNote that = (StructuredNote) o;
        return Objects.equals(dyspnea, that.dyspnea) && Objects.equals(sweating, that.sweating) && Objects.equals(dizziness, that.dizziness) && Objects.equals(stateOfHealth, that.stateOfHealth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dyspnea, sweating, dizziness, stateOfHealth);
    }
}
