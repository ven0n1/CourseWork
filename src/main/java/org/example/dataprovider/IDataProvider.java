package org.example.dataprovider;

import com.google.gson.Gson;
import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.example.Constants;
import org.example.entity.HistoryContent;
import org.example.entity.Medicine;
import org.example.entity.Note;
import org.example.util.ConfigurationUtil;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Интерфейс дата-провайдера с основным функционалом
 */
public interface IDataProvider {
    Logger logger = LogManager.getLogger(IDataProvider.class);
    /**
     * Создание заметки о лекарстве
     * @param heartRate указание частоты сердцебиения
     * @param bloodPressure указание давления
     * @param medicationTime указание времени приема лекарства
     * @param additionalDescription нужно ли дополнительное описание
     * @param parameters указание дополнительных параметров для создания заметки
     * @return результат сохранения
     */
    boolean createNote(int heartRate, String bloodPressure, Note.MedicationTime medicationTime,
                       boolean additionalDescription, String[] parameters);

    /**
     * Указание описания о заметке
     * @param note заметка, переданная из createNote
     * @param description дополнительное описание
     * @return результат сохранения
     */
    boolean specifyAdditionalParameters(Note note, String description);

    /**
     * Указание дополнительных параметров о заметке
     * @param note заметка, переданная из createNote
     * @param dyspnea одышка
     * @param sweating потоотделение
     * @param dizziness головокружение
     * @param stateOfHealth состояние здоровья
     * @return результат сохранения
     */
    boolean specifyStructuredParameters(Note note, String dyspnea, String sweating,
                                        String dizziness, String stateOfHealth);

    /**
     * Создание лекарства
     * @param name имя лекарства
     * @param form форма лекарства
     * @param date дата окончания срока годности лекарства
     * @param sectionsOrDescription указание структурированной информации или описания
     * @param parameters дополнительное описание
     * @return результат сохранения
     */
    boolean addMedicine(String name, String form, int date, boolean sectionsOrDescription, String[] parameters);

    /**
     * Добавление и преобразование срока годности
     * @param date количество месяцев до истечения срока годности
     * @return Строку с датой приведенной к виду: yyyy/MM/dd
     */
    default String addDate(int date){
        // загружаем нужный паттерн для даты
        String pattern = Constants.MEDICINE_DATE_PATTERN;
        DateFormat df = new SimpleDateFormat(pattern);
        // инициализируем текущую дату
        Calendar calendar = Calendar.getInstance();
        // добавляем к текущей дате кол-во месяцев в переданном параметре
        calendar.add(Calendar.MONTH, date);
        // преобразуем Calendar в Date
        Date expirationDate = calendar.getTime();
        return df.format(expirationDate);
    }

    /**
     * Указание дополнительного описания о лекарстве
     * @param medicine лекарство, переданное из метода addMedicine
     * @param description дополнительное описание
     * @return результат сохранения
     */
    boolean addDescription(Medicine medicine, String description);

    /**
     * Указание дополнительных параметров о лекарстве
     * @param medicine лекарство, переданное из метода addMedicine
     * @param uses - показания к применению
     * @param sideEffects - побочные эффекты
     * @param precautions - меры предосторожности
     * @param interaction - взаимодействие с другими лекарственными препаратами
     * @param overdose - передозировка
     * @return результат сохранения
     */
    boolean addSections(Medicine medicine, String uses, String sideEffects,
                        String precautions, String interaction, String overdose);

    /**
     * Сохранение истории об операциях с entity объектом в Mongo
     * @param className имя класса, откуда был вызван метод для изменения объекта
     * @param status статус изменения (SUCCESS, FAULT)
     * @param json json представление объекта
     * @return результат сохранения
     */
    static boolean saveHistory(String className, HistoryContent.Status status, Object json){
        String client;
        String databaseName;
        String collectionName;
        try {
            client = ConfigurationUtil.getConfigurationEntry(Constants.CLIENT);
            databaseName = ConfigurationUtil.getConfigurationEntry(Constants.DB_NAME);
            collectionName = ConfigurationUtil.getConfigurationEntry(Constants.COLLECTION);
        } catch (IOException e) {
            logger.error(e);
            return false;
        }
        try (MongoClient mongoClient = MongoClients.create(client)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            try{
                database.createCollection(collectionName);
            } catch (MongoCommandException ignored) {}
            String date = new SimpleDateFormat(Constants.MONGO_DATE_PATTERN).format(new Date());
            HistoryContent historyContent = new HistoryContent(className, date, status, new Gson().toJson(json));
            MongoCollection<Document> collection = database.getCollection(collectionName);
            collection.insertOne(Document.parse(new Gson().toJson(historyContent)));
        }
        return true;
    }
}
