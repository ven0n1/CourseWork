package org.example.entity;

import com.opencsv.bean.CsvBindByName;
import org.example.Constants;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;
@XmlRootElement
public class CustomNote extends Note{
    @CsvBindByName(column = Constants.DESCRIPTION)
    String description;

    public CustomNote(int heartRate, String bloodPressure, MedicationTime medicationTime, String description) {
        super(heartRate, bloodPressure, medicationTime);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CustomNote that = (CustomNote) o;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), description);
    }
}
