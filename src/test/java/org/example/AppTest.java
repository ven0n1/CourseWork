package org.example;

import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dataprovider.DataProviderCsv;
import org.example.dataprovider.DataProviderXml;
import org.example.dataprovider.IDataProvider;
import org.example.entity.Note;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Unit test for simple App.
 */
public class AppTest {
    private static final Logger logger = LogManager.getLogger(AppTest.class);
    IDataProvider dataProvider = new DataProviderCsv();

    @Test
    public void a(){
        boolean ok = dataProvider.createNote(60, "120/80", Note.MedicationTime.BEFORE, true, new String[]{"1"});
        logger.info(ok);
        ok = dataProvider.createNote(60, "120/80", Note.MedicationTime.BEFORE, false, new String[]{"1", "2", "3", "4", "5"});
        logger.info(ok);
        ok = dataProvider.addMedicine("name", "form", 18, true, new String[]{"1"});
        logger.info(ok);
        ok = dataProvider.addMedicine("name", "form", 18, false, new String[]{"1"});
        logger.info(ok);
    }
}
