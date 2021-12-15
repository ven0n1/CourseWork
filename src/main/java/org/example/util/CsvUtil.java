package org.example.util;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Constants;

import java.io.*;
import java.util.List;
import java.util.Optional;

public class CsvUtil {
    private static final Logger logger = LogManager.getLogger(CsvUtil.class);

    /**
     * Сохранение объектов в Csv файл
     * @param list List объектов
     * @param path путь к файлу
     * @return результат сохранения
     */
    public static <T> boolean save(List<T> list, String path){
        boolean isSaved;
        try {
            CSVWriter csvWriter = new CSVWriter(new FileWriter(path));
            // превращаем наш bean в Csv строку
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(csvWriter).build();
            // записываем в файл
            beanToCsv.write(list);
            csvWriter.close();
            isSaved = true;
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            logger.error(e);
            isSaved = false;
        }
        return isSaved;
    }

    /**
     * Чтение объектов из Csv файла
     * @param type тип класса, в который мы считываем
     * @param path путь к файлу
     * @return List считанных объектов
     */
    public static <T> Optional<List<T>> read(Class<?> type, String path){
        List<T> list;
        File file = new File(path);
        Optional<List<T>> optionalList;
        // создаем директории к файлу, если они не существуют
        file.getParentFile().mkdirs();
        try {
            // создаем новый файл
            if(file.createNewFile()){
                logger.info(Constants.INFO_CREATE_FILE);
            }
        } catch (IOException e) {
            logger.error(e);
        }
        // если в файле есть данные
        if (file.length() != 0){
            try {
                // считываем из файла в bean, класс которогу указан в type
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
}
