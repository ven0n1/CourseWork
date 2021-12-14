package org.example.dataprovider;

import com.google.gson.Gson;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Constants;
import org.example.HistoryContent;
import org.example.entity.*;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataProviderCsv implements IDataProvider{
    private static final Logger logger = LogManager.getLogger(DataProviderCsv.class);

    public DataProviderCsv() {
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
        List<Object> list = new ArrayList<>();
        if(read(CustomNote.class, Constants.CSV_CUSTOM_NOTE).isPresent()){
            list = read(CustomNote.class, Constants.CSV_CUSTOM_NOTE).get();
        }
        list.add(customNote);
        isCreated = save(list, Constants.CSV_CUSTOM_NOTE);
        IDataProvider.saveHistory(getClass().getName(), HistoryContent.Status.SUCCESS, new Gson().toJson(customNote));
        return isCreated;
    }

    @Override
    public boolean specifyStructuredParameters(Note note, String dyspnea, String sweating, String dizziness, String stateOfHealth) {
        boolean isCreated;
        StructuredNote structuredNote = new StructuredNote(note.getHeartRate(), note.getBloodPressure(),
                note.getMedicationTime(), dyspnea, sweating, dizziness, stateOfHealth);
        List<Object> list = new ArrayList<>();
        if(read(StructuredNote.class, Constants.CSV_STRUCTURED_NOTE).isPresent()){
            list = read(StructuredNote.class, Constants.CSV_STRUCTURED_NOTE).get();
        }
        list.add(structuredNote);
        isCreated = save(list, Constants.CSV_STRUCTURED_NOTE);
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
        List<Object> list = new ArrayList<>();
        if(read(CustomMedicine.class, Constants.CSV_CUSTOM_MEDICINE).isPresent()){
            list = read(CustomMedicine.class, Constants.CSV_CUSTOM_MEDICINE).get();
        }
        list.add(customMedicine);
        isCreated = save(list, Constants.CSV_CUSTOM_MEDICINE);
        IDataProvider.saveHistory(getClass().getName(), HistoryContent.Status.SUCCESS, new Gson().toJson(customMedicine));
        return isCreated;
    }

    @Override
    public boolean addSections(Medicine medicine, String uses, String sideEffect, String precautions, String interaction, String overdose) {
        boolean isCreated;
        StructuredMedicine structuredMedicine = new StructuredMedicine(medicine.getName(), medicine.getForm(),
                medicine.getDate(), uses, sideEffect, precautions, interaction, overdose);
        List<Object> medicineList = new ArrayList<>();
        if(read(StructuredMedicine.class, Constants.CSV_STRUCTURED_MEDICINE).isPresent()){
            medicineList = read(StructuredMedicine.class, Constants.CSV_STRUCTURED_MEDICINE).get();
        }
        medicineList.add(structuredMedicine);
        isCreated = save(medicineList, Constants.CSV_STRUCTURED_MEDICINE);
        IDataProvider.saveHistory(getClass().getName(), HistoryContent.Status.SUCCESS, new Gson().toJson(structuredMedicine));
        return isCreated;
    }

    public <T> boolean save(List<T> list, String path){
        boolean isSaved;
        try {
            CSVWriter csvWriter = new CSVWriter(new FileWriter(path));
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(csvWriter).build();
            beanToCsv.write(list);
            csvWriter.close();
            isSaved = true;
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            logger.error(e);
            isSaved = false;
        }
        return isSaved;
    }

    public <T> Optional<List<T>> read(Class<?> type, String path){
        List<T> list;
        File file = new File(path);
        Optional<List<T>> optionalList;
        file.getParentFile().mkdirs();
        try {
            if(file.createNewFile()){
                logger.info(Constants.INFO_CREATE_FILE);
            }
        } catch (IOException e) {
            logger.error(e);
        }
        if (file.length() != 0){
            try {
                list = new CsvToBeanBuilder<T>(new FileReader(path)).withType((Class<? extends T>) type).build().parse();
                optionalList = Optional.of(list);
            } catch (FileNotFoundException e) {
                logger.error(e);
                optionalList = Optional.empty();
            }
        } else{
            logger.info(Constants.INFO_EMPTY_FILE);
            optionalList = Optional.empty();
        }
        return optionalList;
    }

    public String[] fillArguments(String[] parameters){
        String[] strings = new String[5];
        Arrays.fill(strings, Constants.DEFAULT);
        System.arraycopy(parameters, 0, strings, 0, parameters.length);
        return strings;
    }

}
