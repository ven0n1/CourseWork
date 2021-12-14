package org.example.entity;

import com.opencsv.bean.CsvBindByName;
import org.example.Constants;

import java.util.Objects;

public class Medicine {
    @CsvBindByName(column = Constants.MEDICINE_NAME)
    String name;
    @CsvBindByName(column = Constants.MEDICINE_FORM)
    String form;
    @CsvBindByName(column = Constants.MEDICINE_DATE)
    String date;

    public Medicine(String name, String form, String date) {
        this.name = name;
        this.form = form;
        this.date = date;
    }

    public Medicine() {
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
    public String toString() {
        return "Medicine{" +
                "name='" + name + '\'' +
                ", form='" + form + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medicine medicine = (Medicine) o;
        return Objects.equals(name, medicine.name) && Objects.equals(form, medicine.form) && Objects.equals(date, medicine.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, form, date);
    }
}