package org.example.entity;

import com.opencsv.bean.CsvBindByName;
import org.example.Constants;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * Класс CustomNote
 * используется для создания заметок о приеме лекарства
 * с описанием, который напишет пользователь
 */
@XmlRootElement
public class CustomNote extends Note{
    @CsvBindByName(column = Constants.DESCRIPTION)
    String description;

    /**
     * Конструктор для создания заметки о приеме лекарства
     * @param heartRate - частота сердцебиения
     * @param bloodPressure - давление
     * @param medicationTime - время приема лекарства
     * @param description - дополнительная информация от пользователя
     */
    public CustomNote(int heartRate, String bloodPressure, MedicationTime medicationTime, String description) {
        super(heartRate, bloodPressure, medicationTime);
        this.description = description;
    }

    public CustomNote(Note note, String description){
        super(note.getHeartRate(), note.getBloodPressure(), note.getMedicationTime());
        this.description = description;
    }

    public CustomNote() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CustomNote{" +
                "heartRate=" + heartRate +
                ", bloodPressure='" + bloodPressure + '\'' +
                ", medicationTime=" + medicationTime +
                ", description='" + description + '\'' +
                '}';
    }
}
