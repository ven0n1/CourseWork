package org.example.entity;

import com.opencsv.bean.CsvBindByName;
import org.example.Constants;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * Класс StructuredNote
 * используется для создания заметок о приеме лекарства
 * с структурированным описанием
 */
@XmlRootElement
public class StructuredNote extends Note{
    @CsvBindByName(column = Constants.NOTE_DYSPNEA)
    String dyspnea;
    @CsvBindByName(column = Constants.NOTE_SWEATING)
    String sweating;
    @CsvBindByName(column = Constants.NOTE_DIZZINESS)
    String dizziness;
    @CsvBindByName(column = Constants.NOTE_STATE_OF_HEALTH)
    String stateOfHealth;

    /**
     * Конструктор для создания заметок о приеме лекарства
     * @param heartRate - частота сердцебиения
     * @param bloodPressure - давление
     * @param medicationTime - время приема лекарства
     * @param dyspnea - одышка
     * @param sweating - потоотделение
     * @param dizziness - головокружение
     * @param stateOfHealth - описание состояния здоровья
     */
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
}
