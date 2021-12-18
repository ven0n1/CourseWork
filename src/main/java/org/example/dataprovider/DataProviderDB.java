package org.example.dataprovider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Constants;
import org.example.entity.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;


public class DataProviderDB implements IDataProvider{
    private static final Logger logger = LogManager.getLogger(DataProviderDB.class);

    public DataProviderDB() {
    }


    @Override
    public boolean createNote(int heartRate, String bloodPressure, Note.MedicationTime medicationTime,
                              boolean additionalDescription, String[] parameters) {
        Note note;
        // создаем констуктор Note
        note = new Note(heartRate, bloodPressure, medicationTime);
        boolean isCreated;
        // проверка, что parameters не равны null
        if (parameters == null){
            logger.error(Constants.ERROR_PARAMETERS_NULL);
            return false;
        }
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
        // Проверка, что передан не null объект
        if (note == null){
            logger.error(Constants.ERROR_OBJECT_NULL);
            return false;
        }
        CustomNote customNote = new CustomNote(note, description);
        String query = String.format(Constants.INSERT_CUSTOM_NOTE, customNote.getId(), customNote.getHeartRate(),
                customNote.getBloodPressure(), customNote.getMedicationTime(), customNote.getDescription());
        isCreated = executeQuery(Constants.CREATE_TABLE_CUSTOM_NOTE, query);
        // сохраняем историю в Mongo
        IDataProvider.saveHistory(getClass().getName(), HistoryContent.Status.SUCCESS, customNote);
        return isCreated;
    }

    @Override
    public boolean specifyStructuredParameters(Note note, String dyspnea, String sweating, String dizziness, String stateOfHealth) {
        boolean isCreated;
        // Проверка, что передан не null объект
        if (note == null){
            logger.error(Constants.ERROR_OBJECT_NULL);
            return false;
        }
        StructuredNote structuredNote = new StructuredNote(note, dyspnea, sweating, dizziness, stateOfHealth);
        String query = String.format(Constants.INSERT_STRUCTURED_NOTE, structuredNote.getId(), structuredNote.getHeartRate(),
                structuredNote.getBloodPressure(), structuredNote.getMedicationTime(), structuredNote.getDyspnea(),
                structuredNote.getSweating(), structuredNote.getDizziness(), structuredNote.getStateOfHealth());
        isCreated = executeQuery(Constants.CREATE_TABLE_STRUCTURED_NOTE, query);
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
        // проверка, что parameters не равны null
        if (parameters == null){
            logger.error(Constants.ERROR_PARAMETERS_NULL);
            return false;
        }
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
        // Проверка, что передан не null объект
        if (medicine == null){
            logger.error(Constants.ERROR_OBJECT_NULL);
            return false;
        }
        CustomMedicine customMedicine = new CustomMedicine(medicine, description);
        String query = String.format(Constants.INSERT_CUSTOM_MEDICINE, customMedicine.getId(), customMedicine.getName(),
                customMedicine.getForm(), customMedicine.getDate(), customMedicine.getDescription());
        isCreated = executeQuery(Constants.CREATE_TABLE_CUSTOM_MEDICINE, query);
        // сохраняем историю в Mongo
        IDataProvider.saveHistory(getClass().getName(), HistoryContent.Status.SUCCESS, customMedicine);
        return isCreated;
    }

    @Override
    public boolean addSections(Medicine medicine, String uses, String sideEffects, String precautions, String interaction, String overdose) {
        boolean isCreated;
        // Проверка, что передан не null объект
        if (medicine == null){
            logger.error(Constants.ERROR_OBJECT_NULL);
            return false;
        }
        StructuredMedicine structuredMedicine = new StructuredMedicine(medicine, uses, sideEffects, precautions,
                interaction, overdose);
        String query = String.format(Constants.INSERT_STRUCTURED_MEDICINE, structuredMedicine.getId(),
                structuredMedicine.getName(), structuredMedicine.getForm(), structuredMedicine.getDate(),
                structuredMedicine.getUses(), structuredMedicine.getSideEffects(), structuredMedicine.getPrecautions(),
                structuredMedicine.getInteraction(), structuredMedicine.getOverdose());
        isCreated = executeQuery(Constants.CREATE_TABLE_STRUCTURED_MEDICINE, query);
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

    public boolean executeQuery(String table, String query){
        boolean isCreated = false;
        try {
            Class.forName(Constants.JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            logger.error(e);
        }
        try (Connection connection = DriverManager.getConnection(Constants.URL + Constants.DATABASE_NAME,
                Constants.USER, Constants.PASSWORD)){
            Statement statement = connection.createStatement();
            statement.execute(table);
            statement.execute(query);
            isCreated = true;
        } catch (SQLException e) {
            logger.error(e);
        }
        return isCreated;
    }
}
