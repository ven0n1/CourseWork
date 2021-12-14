package org.example.dataprovider;

import com.google.gson.Gson;
import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.Constants;
import org.example.HistoryContent;
import org.example.entity.Medicine;
import org.example.entity.Note;

import java.text.SimpleDateFormat;
import java.util.Date;


public interface IDataProvider {
    boolean createNote(int heartBlood, String bloodPressure, Note.MedicationTime medicationTime, boolean additionalDescription, String[] parameters);
    boolean specifyAdditionalParameters(Note note, String description);
    boolean specifyStructuredParameters(Note note, String dyspnea, String sweating, String dizziness, String stateOfHealth);
    boolean addMedicine(String name, String form, int date, boolean sectionsOrDescription, String[] parameters);
    String addDate(int date);
    boolean addDescription(Medicine medicine, String description);
    boolean addSections(Medicine medicine, String uses, String sideEffect, String precautions, String interaction, String overdose);

    static boolean saveHistory(String className, HistoryContent.Status status, String json){
        try (MongoClient mongoClient = MongoClients.create(Constants.MONGO_CLIENT)) {
            MongoDatabase database = mongoClient.getDatabase(Constants.DATABASE_NAME);
            try{
                database.createCollection(Constants.COLLECTION_NAME);
            } catch (MongoCommandException ignored) {}
            String date = new SimpleDateFormat(Constants.MONGO_DATE_PATTERN).format(new Date());
            HistoryContent historyContent = new HistoryContent(className, date, status, json);
            MongoCollection<Document> collection = database.getCollection(Constants.COLLECTION_NAME);
            collection.insertOne(Document.parse(new Gson().toJson(historyContent)));
        }
        return true;
    }
}
