package org.example.dataprovider;

import org.example.entity.Medicine;
import org.example.entity.Note;

public class DataProviderDB implements IDataProvider{
    @Override
    public boolean createNote(int heartBlood, String bloodPressure, Note.MedicationTime medicationTime, boolean additionalDescription, String[] parameters) {
        return false;
    }

    @Override
    public boolean specifyAdditionalParameters(Note note, String description) {
        return false;
    }

    @Override
    public boolean specifyStructuredParameters(Note note, String dyspnea, String sweating, String dizziness, String stateOfHealth) {
        return false;
    }

    @Override
    public boolean addMedicine(String name, String form, int date, boolean sectionsOrDescription, String[] parameters) {
        return false;
    }

    @Override
    public boolean addDescription(Medicine medicine, String description) {
        return false;
    }

    @Override
    public boolean addSections(Medicine medicine, String uses, String sideEffects, String precautions, String interaction, String overdose) {
        return false;
    }
}
