package org.example.dataprovider;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Constants;
import org.example.entity.HistoryContent;
import org.example.entity.*;
import org.example.entity.JAXBCollection;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.example.util.XmlUtil.read;
import static org.example.util.XmlUtil.save;

public class DataProviderXml implements IDataProvider{
    private static final Logger logger = LogManager.getLogger(DataProviderCsv.class);

    public DataProviderXml() {
    }


    @Override
    public boolean createNote(int heartBlood, String bloodPressure, Note.MedicationTime medicationTime,
                              boolean additionalDescription, String[] parameters) {
        Note note;
        note = new Note(heartBlood, bloodPressure, medicationTime);
        boolean isCreated;
        String[] arguments = fillArguments(parameters);
        if (additionalDescription){
            isCreated = specifyAdditionalParameters(note, arguments[0]);
        } else {
            isCreated = specifyStructuredParameters(note, arguments[0], arguments[1], arguments[2], arguments[3]);
        }
        return isCreated;
    }

    @Override
    public boolean specifyAdditionalParameters(Note note, String description) {
        boolean isCreated;
        CustomNote customNote = new CustomNote(note.getHeartRate(), note.getBloodPressure(), note.getMedicationTime(), description);
        List<CustomNote> list = new ArrayList<>();
        if(read(CustomNote.class, Constants.XML_CUSTOM_NOTE).isPresent()){
            list = read(CustomNote.class, Constants.XML_CUSTOM_NOTE).get();
        }
        list.add(customNote);
        isCreated = save(Constants.XML_NOTES, list, Constants.XML_CUSTOM_NOTE);
        IDataProvider.saveHistory(getClass().getName(), HistoryContent.Status.SUCCESS, new Gson().toJson(customNote));
        return isCreated;
    }

    @Override
    public boolean specifyStructuredParameters(Note note, String dyspnea, String sweating, String dizziness, String stateOfHealth) {
        boolean isCreated;
        StructuredNote structuredNote = new StructuredNote(note.getHeartRate(), note.getBloodPressure(),
                note.getMedicationTime(), dyspnea, sweating, dizziness, stateOfHealth);
        List<StructuredNote> list = new ArrayList<>();
        if(read(StructuredNote.class, Constants.XML_STRUCTURED_NOTE).isPresent()){
            list = read(StructuredNote.class, Constants.XML_STRUCTURED_NOTE).get();
        }
        list.add(structuredNote);
        isCreated = save(Constants.XML_NOTES, list, Constants.XML_STRUCTURED_NOTE);
        IDataProvider.saveHistory(getClass().getName(), HistoryContent.Status.SUCCESS, new Gson().toJson(structuredNote));
        return isCreated;
    }

    @Override
    public boolean addMedicine(String name, String form, int date, boolean sectionsOrDescription, String[] parameters) {
        boolean isCreated;
        String dateResult = addDate(date);
        Medicine medicine = new Medicine(name, form, dateResult);
        String[] arguments = fillArguments(parameters);
        if (sectionsOrDescription){
            isCreated = addDescription(medicine, arguments[0]);
        } else {
            isCreated = addSections(medicine, arguments[0], arguments[1], arguments[2], arguments[3], arguments[4]);
        }
        return isCreated;
    }

    @Override
    public String addDate(int date) {
        String pattern = Constants.MEDICINE_DATE_PATTERN;
        DateFormat df = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, date);
        Date expirationDate = calendar.getTime();
        return df.format(expirationDate);
    }

    @Override
    public boolean addDescription(Medicine medicine, String description) {
        boolean isCreated;
        CustomMedicine customMedicine = new CustomMedicine(medicine.getName(), medicine.getForm(), medicine.getDate(), description);
        List<CustomMedicine> list = new ArrayList<>();
        if(read(CustomMedicine.class, Constants.XML_CUSTOM_MEDICINE).isPresent()){
            list = read(CustomMedicine.class, Constants.XML_CUSTOM_MEDICINE).get();
        }
        list.add(customMedicine);
        isCreated = save(Constants.XML_MEDICINES, list, Constants.XML_CUSTOM_MEDICINE);
        IDataProvider.saveHistory(getClass().getName(), HistoryContent.Status.SUCCESS, new Gson().toJson(customMedicine));
        return isCreated;
    }

    @Override
    public boolean addSections(Medicine medicine, String uses, String sideEffects, String precautions, String interaction, String overdose) {
        boolean isCreated;
        StructuredMedicine structuredMedicine = new StructuredMedicine(medicine.getName(), medicine.getForm(),
                medicine.getDate(), uses, sideEffects, precautions, interaction, overdose);
        List<StructuredMedicine> medicineList = new ArrayList<>();
        if(read(StructuredMedicine.class, Constants.XML_STRUCTURED_MEDICINE).isPresent()){
            medicineList = read(StructuredMedicine.class, Constants.XML_STRUCTURED_MEDICINE).get();
        }
        medicineList.add(structuredMedicine);
        isCreated = save(Constants.XML_MEDICINES, medicineList, Constants.XML_STRUCTURED_MEDICINE);
        IDataProvider.saveHistory(getClass().getName(), HistoryContent.Status.SUCCESS, new Gson().toJson(structuredMedicine));
        return isCreated;
    }

    public String[] fillArguments(String[] parameters){
        String[] strings = new String[5];
        Arrays.fill(strings, Constants.DEFAULT);
        System.arraycopy(parameters, 0, strings, 0, parameters.length);
        return strings;
    }
}
