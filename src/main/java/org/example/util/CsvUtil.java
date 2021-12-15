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

    public static <T> boolean save(List<T> list, String path){
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

    public static <T> Optional<List<T>> read(Class<?> type, String path){
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
}
