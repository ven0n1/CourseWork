package org.example.entity;

import com.opencsv.bean.CsvBindByName;
import org.example.Constants;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;
@XmlRootElement
public class StructuredMedicine extends Medicine{
    @CsvBindByName(column = Constants.MEDICINE_USES)
    String uses;
    @CsvBindByName(column = Constants.MEDICINE_SIDE_EFFECTS)
    String sideEffects;
    @CsvBindByName(column = Constants.MEDICINE_PRECAUTION)
    String precautions;
    @CsvBindByName(column = Constants.MEDICINE_INTERACTION)
    String interaction;
    @CsvBindByName(column = Constants.MEDICINE_OVERDOSE)
    String overdose;

    public StructuredMedicine(String name, String form, String date, String uses, String sideEffects, String precautions, String interaction, String overdose) {
        super(name, form, date);
        this.uses = uses;
        this.sideEffects = sideEffects;
        this.precautions = precautions;
        this.interaction = interaction;
        this.overdose = overdose;
    }

    public StructuredMedicine() {
    }

    public String getUses() {
        return uses;
    }

    public void setUses(String uses) {
        this.uses = uses;
    }

    public String getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    public String getPrecautions() {
        return precautions;
    }

    public void setPrecautions(String precautions) {
        this.precautions = precautions;
    }

    public String getInteraction() {
        return interaction;
    }

    public void setInteraction(String interaction) {
        this.interaction = interaction;
    }

    public String getOverdose() {
        return overdose;
    }

    public void setOverdose(String overdose) {
        this.overdose = overdose;
    }

    @Override
    public String toString() {
        return "StructuredMedicine{" +
                "name='" + name + '\'' +
                ", form='" + form + '\'' +
                ", date='" + date + '\'' +
                ", uses='" + uses + '\'' +
                ", sideEffects='" + sideEffects + '\'' +
                ", precautions='" + precautions + '\'' +
                ", interaction='" + interaction + '\'' +
                ", overdose='" + overdose + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StructuredMedicine that = (StructuredMedicine) o;
        return Objects.equals(uses, that.uses) && Objects.equals(sideEffects, that.sideEffects) && Objects.equals(precautions, that.precautions) && Objects.equals(interaction, that.interaction) && Objects.equals(overdose, that.overdose);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), uses, sideEffects, precautions, interaction, overdose);
    }
}
