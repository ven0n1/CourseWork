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
        // создаем констуктор Note
        note = new Note(heartBlood, bloodPressure, medicationTime);
        boolean isCreated;
        // заполняем аргументы, чтобы учесть возможность, когда пользователь задал меньшее количество аргументов
        String[] arguments = fillArguments(parameters);
        if (additionalDescription){
            // вызываем метод с дополнительным параметром (description)
            isCreated = specifyAdditionalParameters(note, arguments[0]);
        } else {
            // вызываем метод с дополнительными параметрами (dyspnea, sweating, dizziness, stateOfHealth)
            isCreated = specifyStructuredParameters(note, arguments[0], arguments[1], arguments[2], arguments[3]);
        }
        return isCreated;
    }

    @Override
    public boolean specifyAdditionalParameters(Note note, String description) {
        boolean isCreated;
        // создаем конструктор со всеми известными данными
        CustomNote customNote = new CustomNote(note, description);
        List<CustomNote> list = new ArrayList<>();
        // считываем данные из файла, если они есть
        if(read(CustomNote.class, Constants.XML_CUSTOM_NOTE).isPresent()){
            list = read(CustomNote.class, Constants.XML_CUSTOM_NOTE).get();
        }
        list.add(customNote);
        // после добавления, сохраняем данные в файл
        isCreated = save(Constants.XML_NOTES, list, Constants.XML_CUSTOM_NOTE);
        // сохраняем историю в Mongo
        IDataProvider.saveHistory(getClass().getName(), HistoryContent.Status.SUCCESS, customNote);
        return isCreated;
    }

    @Override
    public boolean specifyStructuredParameters(Note note, String dyspnea, String sweating, String dizziness, String stateOfHealth) {
        boolean isCreated;
        // создаем конструктор со всеми известными данными
        StructuredNote structuredNote = new StructuredNote(note, dyspnea, sweating, dizziness, stateOfHealth);
        List<StructuredNote> list = new ArrayList<>();
        // считываем данные из файла, если они есть
        if(read(StructuredNote.class, Constants.XML_STRUCTURED_NOTE).isPresent()){
            list = read(StructuredNote.class, Constants.XML_STRUCTURED_NOTE).get();
        }
        list.add(structuredNote);
        // после добавления, сохраняем данные в файл
        isCreated = save(Constants.XML_NOTES, list, Constants.XML_STRUCTURED_NOTE);
        // сохраняем историю в Mongo
        IDataProvider.saveHistory(getClass().getName(), HistoryContent.Status.SUCCESS, structuredNote);
        return isCreated;
    }

    @Override
    public boolean addMedicine(String name, String form, int date, boolean sectionsOrDescription, String[] parameters) {
        boolean isCreated;
        // вызываем метод addDate для нормализации даты
        String dateResult = addDate(date);
        // создаем констуктор Medicine
        Medicine medicine = new Medicine(name, form, dateResult);
        // заполняем аргументы, чтобы учесть возможность, когда пользователь задал меньшее количество аргументов
        String[] arguments = fillArguments(parameters);
        if (sectionsOrDescription){
            // вызываем метод с дополнительным параметром (description)
            isCreated = addDescription(medicine, arguments[0]);
        } else {
            // вызываем метод с дополнительными параметрами (uses, sideEffects, precautions, interaction, overdose)
            isCreated = addSections(medicine, arguments[0], arguments[1], arguments[2], arguments[3], arguments[4]);
        }
        return isCreated;
    }

    @Override
    public boolean addDescription(Medicine medicine, String description) {
        boolean isCreated;
        // создаем конструктор со всеми известными данными
        CustomMedicine customMedicine = new CustomMedicine(medicine, description);
        List<CustomMedicine> list = new ArrayList<>();
        // считываем данные из файла, если они есть
        if(read(CustomMedicine.class, Constants.XML_CUSTOM_MEDICINE).isPresent()){
            list = read(CustomMedicine.class, Constants.XML_CUSTOM_MEDICINE).get();
        }
        list.add(customMedicine);
        // после добавления, сохраняем данные в файл
        isCreated = save(Constants.XML_MEDICINES, list, Constants.XML_CUSTOM_MEDICINE);
        // сохраняем историю в Mongo
        IDataProvider.saveHistory(getClass().getName(), HistoryContent.Status.SUCCESS, customMedicine);
        return isCreated;
    }

    @Override
    public boolean addSections(Medicine medicine, String uses, String sideEffects, String precautions, String interaction, String overdose) {
        boolean isCreated;
        // создаем конструктор со всеми известными данными
        StructuredMedicine structuredMedicine = new StructuredMedicine(medicine, uses, sideEffects, precautions, interaction, overdose);
        List<StructuredMedicine> medicineList = new ArrayList<>();
        // считываем данные из файла, если они есть
        if(read(StructuredMedicine.class, Constants.XML_STRUCTURED_MEDICINE).isPresent()){
            medicineList = read(StructuredMedicine.class, Constants.XML_STRUCTURED_MEDICINE).get();
        }
        medicineList.add(structuredMedicine);
        // после добавления, сохраняем данные в файл
        isCreated = save(Constants.XML_MEDICINES, medicineList, Constants.XML_STRUCTURED_MEDICINE);
        // сохраняем историю в Mongo
        IDataProvider.saveHistory(getClass().getName(), HistoryContent.Status.SUCCESS, structuredMedicine);
        return isCreated;
    }

    public String[] fillArguments(String[] parameters){
        // создаем массив строк размером в 5 (макс. кол-во дополнительных параметров)
        String[] strings = new String[5];
        // заполняем их дефолтными строками
        Arrays.fill(strings, Constants.DEFAULT);
        // копируем данные из parameters в strings
        System.arraycopy(parameters, 0, strings, 0, parameters.length);
        return strings;
    }
}
