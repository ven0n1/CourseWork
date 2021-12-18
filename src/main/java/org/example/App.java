package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dataprovider.DataProviderCsv;
import org.example.dataprovider.DataProviderDB;
import org.example.dataprovider.DataProviderXml;
import org.example.dataprovider.IDataProvider;
import org.example.entity.Note;

import java.util.Optional;


public class App {
    private static final Logger logger = LogManager.getLogger(App.class);
    static String[] additionalArgs;

    /**
     * Основная функция для вызова методов
     * @param args передаваемые в методы параметры
     */
    public static void main( String[] args ) {
        // Если запуск произошел без аргументов, то завершается работа
        if (args.length == 0){
            logger.error(Constants.ERROR_NECESSARY_ARGUMENTS);
            return;
        }
        IDataProvider dataProvider = null;
        if (chooseDataProvider(args[0]).isPresent()){
            dataProvider = chooseDataProvider(args[0]).get();
        }
        // Если дата провайдер не выбран, то завершается работа
        if (dataProvider == null){
            logger.error(Constants.ERROR_NECESSARY_ARGUMENTS);
            return;
        }
        String methodName;
        // Если метод не выбран, то завершается работа
        if (args.length == 1){
            logger.error(Constants.ERROR_NECESSARY_ARGUMENTS);
            return;
        } else {
            methodName = args[1];
        }
        switch (methodName){
            case Constants.METHOD_CREATE_NOTE:
                int heartRate;
                String bloodPressure;
                Note.MedicationTime medicationTime;
                boolean additionalDescription;
                // Если аргументов меньше необходимого минимума, то завершается работа
                if (args.length < 6){
                    logger.error(Constants.ERROR_NECESSARY_ARGUMENTS);
                    return;
                }
                // Если неправильный формат числа, то завершается работа
                try {
                    heartRate = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    logger.error(e);
                    return;
                }
                // Если не удовлетворяет паттерну, то завершается работа
                if (args[3].matches(Constants.PATTERN_BLOOD_PRESSURE)){
                    bloodPressure = args[3];
                } else {
                    logger.error(Constants.ERROR_PATTERN_DONT_MATCH);
                    return;
                }
                switch (args[4]){
                    case Constants.MEDICATION_TIME_BEFORE:
                        medicationTime = Note.MedicationTime.BEFORE;
                        break;
                    case Constants.MEDICATION_TIME_DURING:
                        medicationTime = Note.MedicationTime.DURING;
                        break;
                    case Constants.MEDICATION_TIME_AFTER:
                        medicationTime = Note.MedicationTime.AFTER;
                        break;
                    // Если выбран неправильный MedicationTime, то завершается работа
                    default:
                        logger.error(Constants.ERROR_TYPE_MISMATCH);
                        return;
                }
                switch (args[5]){
                    case Constants.TRUE:
                        additionalDescription = true;
                        break;
                    case Constants.FALSE:
                        additionalDescription = false;
                        break;
                    // Если выбрано не булевское значение, то завершается работа
                    default:
                        logger.error(Constants.ERROR_TYPE_MISMATCH);
                        return;
                }
                additionalArgs = fillUnnecessaryParameters(args);
                // Вызов метода createNote со всеми полученными параметрами
                dataProvider.createNote(heartRate, bloodPressure, medicationTime, additionalDescription, additionalArgs);
                break;
            case Constants.METHOD_ADD_MEDICINE:
                String name;
                String form;
                int date;
                boolean sectionsOrDescription;
                // Если аргументов меньше необходимого минимума, то завершается работа
                if (args.length < 6){
                    logger.error(Constants.ERROR_NECESSARY_ARGUMENTS);
                    return;
                }
                name = args[2];
                form = args[3];
                // Если неправильный формат числа, то завершается работа
                try {
                    date = Integer.parseInt(args[4]);
                } catch (NumberFormatException e) {
                    logger.error(e);
                    return;
                }
                switch (args[5]){
                    case Constants.TRUE:
                        sectionsOrDescription = true;
                        break;
                    case Constants.FALSE:
                        sectionsOrDescription = false;
                        break;
                    // Если выбрано не булевское значение, то завершается работа
                    default:
                        logger.error(Constants.ERROR_TYPE_MISMATCH);
                        return;
                }
                additionalArgs = fillUnnecessaryParameters(args);
                dataProvider.addMedicine(name, form, date, sectionsOrDescription, additionalArgs);
                break;
            // Если метод написан неправильно, то завершается работа
            default:
                logger.error(Constants.ERROR_METHOD_NOT_FOUND);
        }
    }

    /**
     * Выбор дата провайдера и учет возможности неправильного написания
     * @param argDataProvider название дата провайдера
     * @return выбранный дата провайдер либо Optional.empty()
     */
    private static Optional<IDataProvider> chooseDataProvider(String argDataProvider){
        Optional<IDataProvider> optional = Optional.empty();
        IDataProvider dataProvider;
        switch (argDataProvider){
            case Constants.DATA_PROVIDER_CSV:
                dataProvider = new DataProviderCsv();
                optional = Optional.of(dataProvider);
                break;
            case Constants.DATA_PROVIDER_XML:
                dataProvider = new DataProviderXml();
                optional = Optional.of(dataProvider);
                break;
            case Constants.DATA_PROVIDER_DB:
                dataProvider = new DataProviderDB();
                optional = Optional.of(dataProvider);
                break;
            // Если неправильно введен дата провайдер, то выводится ошибка
            default:
                logger.error(Constants.ERROR_DATA_PROVIDER_NAME);
        }
        return optional;
    }

    /**
     * заполнение дополнительных параметров для extend методов
     * в зависимости от кол-ва переданных аргументов
     * @param args принимаемые от main аргументы
     * @return массив строк с выбранными параметрами
     */
    private static String[] fillUnnecessaryParameters(String[] args){
        String[] additionalArgs = new String[]{};
        if (args.length == 7) {
            additionalArgs = new String[1];
            additionalArgs[0] = args[6];
        } else if (args.length == 8) {
            additionalArgs = new String[2];
            additionalArgs[0] = args[6];
            additionalArgs[1] = args[7];
        } else if (args.length == 9){
            additionalArgs = new String[3];
            additionalArgs[0] = args[6];
            additionalArgs[1] = args[7];
            additionalArgs[2] = args[8];
        }  else if (args.length == 10){
            additionalArgs = new String[4];
            additionalArgs[0] = args[6];
            additionalArgs[1] = args[7];
            additionalArgs[2] = args[8];
            additionalArgs[3] = args[9];
        } else if (args.length >= 11){
            additionalArgs = new String[5];
            additionalArgs[0] = args[6];
            additionalArgs[1] = args[7];
            additionalArgs[2] = args[8];
            additionalArgs[3] = args[9];
            additionalArgs[4] = args[10];
        }
        return additionalArgs;
    }
}
