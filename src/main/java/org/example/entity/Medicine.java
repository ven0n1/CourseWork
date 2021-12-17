package org.example.entity;

import com.opencsv.bean.CsvBindByName;
import org.example.Constants;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;
import java.util.UUID;

/**
 * Класс Medicine
 * Родительский класс лекарств
 * @see CustomMedicine
 * @see StructuredMedicine
 */
@XmlRootElement
public class Medicine {
    @CsvBindByName(column = Constants.ID)
    UUID id;
    @CsvBindByName(column = Constants.MEDICINE_NAME)
    String name;
    @CsvBindByName(column = Constants.MEDICINE_FORM)
    String form;
    @CsvBindByName(column = Constants.MEDICINE_DATE)
    String date;

    public Medicine(String name, String form, String date) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.form = form;
        this.date = date;
    }

    public Medicine() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medicine medicine = (Medicine) o;
        return Objects.equals(id, medicine.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "name='" + name + '\'' +
                ", form='" + form + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

}
