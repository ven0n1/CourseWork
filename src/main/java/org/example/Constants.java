package org.example;

public class Constants {
    public static final String PROPERTIES_PATH = "properties";

    // Csv файлы
    public static final String CSV_CUSTOM_NOTE = "./src/main/resources/csv/custom note.csv";
    public static final String CSV_STRUCTURED_NOTE = "./src/main/resources/csv/structured note.csv";
    public static final String CSV_CUSTOM_MEDICINE = "./src/main/resources/csv/custom medicine.csv";
    public static final String CSV_STRUCTURED_MEDICINE = "./src/main/resources/csv/structured medicine.csv";

    // Xml файлы
    public static final String XML_CUSTOM_NOTE = "./src/main/resources/xml/custom note.xml";
    public static final String XML_STRUCTURED_NOTE = "./src/main/resources/xml/structured note.xml";
    public static final String XML_CUSTOM_MEDICINE = "./src/main/resources/xml/custom medicine.xml";
    public static final String XML_STRUCTURED_MEDICINE = "./src/main/resources/xml/structured medicine.xml";
    public static final String XML_NOTES = "Notes";
    public static final String XML_MEDICINES = "Medicines";

    // сохранение истории в Mongo
    public static final String DEFAULT = "default";
    public static final String ACTOR = "System";
    public static final String DATABASE_NAME = "yaminov";
    public static final String COLLECTION_NAME = "historyContent";
    public static final String MONGO_CLIENT = "mongodb://localhost:27017";
    public static final String MONGO_DATE_PATTERN = "yyyy.MM.dd HH:mm:ss";

    // Названия столбцов в Csv файлах
    public static final String ID = "id";
    public static final String NOTE_HEART_RATE = "heartRate";
    public static final String NOTE_BLOOD_PRESSURE = "bloodPressure";
    public static final String NOTE_MEDICATION_TIME = "medicationTime";
    public static final String NOTE_DYSPNEA = "dyspnea";
    public static final String NOTE_SWEATING = "sweating";
    public static final String NOTE_DIZZINESS = "dizziness";
    public static final String NOTE_STATE_OF_HEALTH = "stateOfHealth";
    public static final String DESCRIPTION = "description";
    public static final String MEDICINE_NAME = "name";
    public static final String MEDICINE_FORM = "form";
    public static final String MEDICINE_DATE = "date";
    public static final String MEDICINE_USES = "uses";
    public static final String MEDICINE_SIDE_EFFECTS = "sideEffects";
    public static final String MEDICINE_PRECAUTION = "precautions";
    public static final String MEDICINE_INTERACTION = "interaction";
    public static final String MEDICINE_OVERDOSE = "overdose";
    public static final String MEDICINE_DATE_PATTERN = "yyyy/MM/dd";

    // JDBC, адрес, имя пользователя и пароль для MySQL server
    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String URL = "jdbc:mysql://localhost:3306/";
    public static final String USER = "root";
    public static final String PASSWORD = "qwerty";

    // Запросы Sql
    public static final String CREATE_TABLE_CUSTOM_NOTE = "CREATE TABLE IF NOT EXISTS CUSTOM_NOTE(" +
            "id TEXT, " +
            "heartRate INT, " +
            "bloodPressure TEXT, " +
            "medicationTime TEXT, " +
            "description TEXT);";
    public static final String CREATE_TABLE_STRUCTURED_NOTE = "CREATE TABLE IF NOT EXISTS STRUCTURED_NOTE(" +
            "id TEXT, " +
            "heartRate INT, " +
            "bloodPressure TEXT, " +
            "medicationTime TEXT, " +
            "dyspnea TEXT, " +
            "sweating TEXT, " +
            "dizziness TEXT, " +
            "stateOfHealth TEXT);";
    public static final String CREATE_TABLE_CUSTOM_MEDICINE = "CREATE TABLE IF NOT EXISTS CUSTOM_MEDICINE(" +
            "id TEXT, " +
            "name TEXT, " +
            "form TEXT, " +
            "date TEXT, " +
            "description TEXT);";
    public static final String CREATE_TABLE_STRUCTURED_MEDICINE = "CREATE TABLE IF NOT EXISTS STRUCTURED_MEDICINE(" +
            "id TEXT, " +
            "name TEXT, " +
            "form TEXT, " +
            "date TEXT, " +
            "uses TEXT, " +
            "sideEffects TEXT, " +
            "precautions TEXT, " +
            "interaction TEXT, " +
            "overdose TEXT);";
    public static final String INSERT_CUSTOM_NOTE = "INSERT INTO CUSTOM_NOTE VALUES " +
            "('%s', %d, '%s', '%s', '%s');";
    public static final String INSERT_STRUCTURED_NOTE = "INSERT INTO STRUCTURED_NOTE VALUES " +
            "('%s', %d, '%s', '%s', '%s', '%s', '%s', '%s');";
    public static final String INSERT_CUSTOM_MEDICINE = "INSERT INTO CUSTOM_MEDICINE VALUES " +
            "('%s', '%s', '%s', '%s', '%s');";
    public static final String INSERT_STRUCTURED_MEDICINE = "INSERT INTO STRUCTURED_MEDICINE VALUES " +
            "('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');";

    // Команды для CLI
    public static final String DATA_PROVIDER_CSV = "csv";
    public static final String DATA_PROVIDER_XML = "xml";
    public static final String DATA_PROVIDER_DB = "db";
    public static final String METHOD_CREATE_NOTE = "create_note";
    public static final String METHOD_ADD_MEDICINE = "add_medicine";
    public static final String PATTERN_BLOOD_PRESSURE = "\\d{2,3}\\/\\d{2,3}";
    public static final String MEDICATION_TIME_BEFORE = "before";
    public static final String MEDICATION_TIME_DURING = "during";
    public static final String MEDICATION_TIME_AFTER = "after";
    public static final String TRUE = "true";
    public static final String FALSE = "false";

    // Исключения
    public static final String INFO_EMPTY_FILE = "File empty";
    public static final String INFO_CREATE_FILE = "File is created";
    public static final String ERROR_PARAMETERS_NULL = "parameters can not be null";
    public static final String ERROR_OBJECT_NULL = "object can not be null";
    public static final String ERROR_DATA_PROVIDER_NAME = "could not find data provider with that name";
    public static final String ERROR_METHOD_NOT_FOUND = "Could not find method with that name";
    public static final String ERROR_NECESSARY_ARGUMENTS = "There are not enough necessary arguments";
    public static final String ERROR_TYPE_MISMATCH = "You have provided an inappropriate data type";
    public static final String ERROR_PATTERN_DONT_MATCH = "Pattern does not match";
}
