package org.example.entity;

import com.opencsv.bean.CsvBindByName;
import org.example.Constants;

import java.util.Objects;

public class CustomMedicine extends Medicine{
    @CsvBindByName(column = Constants.DESCRIPTION)
     String description;

    public CustomMedicine(String name, String form, String date, String description) {
        super(name, form, date);
        this.description = description;
    }

    public CustomMedicine() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CustomMedicine{" +
                "name='" + name + '\'' +
                ", form='" + form + '\'' +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CustomMedicine that = (CustomMedicine) o;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), description);
    }
}
