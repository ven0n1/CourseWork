package org.example.entity;

import com.opencsv.bean.CsvBindByName;
import org.example.Constants;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * Класс CustomMedicine
 * используется для создания информации о лекарстве
 * с описанием, который напишет пользователь
 */
@XmlRootElement
public class CustomMedicine extends Medicine{
    @CsvBindByName(column = Constants.DESCRIPTION)
     String description;

    /**
     * Конструктор для создания лекарства
     * @param name - название лекарства
     * @param form - лекарственная форма
     * @param date - дата окончания срока годности
     * @param description - описание лекарства
     */
    public CustomMedicine(String name, String form, String date, String description) {
        super(name, form, date);
        this.description = description;
    }

    public CustomMedicine(Medicine medicine, String description){
        super(medicine.getName(), medicine.getForm(), medicine.getDate());
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
}
