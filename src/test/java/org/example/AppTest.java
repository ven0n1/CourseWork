package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dataprovider.DataProviderCsv;
import org.example.dataprovider.IDataProvider;
import org.example.entity.Medicine;
import org.example.entity.Note;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Проверка всех методов, используя негативные и позитивные сценарии
 */
public class AppTest {
    private static final Logger logger = LogManager.getLogger(AppTest.class);
    IDataProvider dataProvider = new DataProviderCsv();

    // позитивный сценарий для метода CreateNote
    @Test
    public void positiveCreateNote(){
        boolean check = dataProvider.createNote(60, "120/80", Note.MedicationTime.DURING, true, new String[]{"OK"});
        assertTrue(check);
    }

    // негативный сценарий для метода CreateNote
    @Test
    public void negativeCreateNote(){
        boolean check = dataProvider.createNote(60, null, null, true, null);
        assertFalse(check);
    }

    // позитивный сценарий для метода SpecifyAdditionalParameters
    @Test
    public void positiveSpecifyAdditionalParameters(){
        Note note = new Note(60, "120/80", Note.MedicationTime.DURING);
        boolean check = dataProvider.specifyAdditionalParameters(note, "OK");
        assertTrue(check);
    }

    // негативный сценарий для метода SpecifyAdditionalParameters
    @Test
    public void negativeSpecifyAdditionalParameters(){
        boolean check = dataProvider.specifyAdditionalParameters(null, null);
        assertFalse(check);
    }

    // позитивный сценарий для метода SpecifyStructuredParameters
    @Test
    public void positiveSpecifyStructuredParameters(){
        Note note = new Note(60, "120/80", Note.MedicationTime.DURING);
        boolean check = dataProvider.specifyStructuredParameters(note, "OK", "OK", "OK", "OK");
        assertTrue(check);
    }

    // негативный сценарий для метода SpecifyStructuredParameters
    @Test
    public void negativeSpecifyStructuredParameters(){
        boolean check = dataProvider.specifyStructuredParameters(null, null, null, null, null);
        assertFalse(check);
    }

    // позитивный сценарий для метода AddMedicine
    @Test
    public void positiveAddMedicine(){
        boolean check = dataProvider.addMedicine("Aspirin", "pills", 12, true, new String[]{"Разовая доза - от 250 мг до 1 г, суточная - от 250 мг до 3 г; кратность применения - 2-6 раз/сут."});
        assertTrue(check);
    }

    // негативный сценарий для метода AddMedicine
    @Test
    public void negativeAddMedicine(){
        boolean check = dataProvider.addMedicine(null, null, 12, true, null);
        assertFalse(check);
    }

    // позитивный сценарий для метода AddDescription
    @Test
    public void positiveAddDescription(){
        Medicine medicine = new Medicine("Aspirin", "pills", "2022/12/15");
        boolean check = dataProvider.addDescription(medicine, "Разовая доза - от 250 мг до 1 г, суточная - от 250 мг до 3 г; кратность применения - 2-6 раз/сут.");
        assertTrue(check);
    }

    // негативный сценарий для метода AddDescription
    @Test
    public void negativeAddDescription(){
        boolean check = dataProvider.addDescription(null, null);
        assertFalse(check);
    }

    // позитивный сценарий для метода AddSection
    @Test
    public void positiveAddSection(){
        Medicine medicine = new Medicine("Aspirin", "pills", "2022/12/15");
        boolean check = dataProvider.addSections(medicine,
                "Аспирин используется для снижения температуры и облегчения легкой и умеренной боли при таких состояниях, как мышечные боли, зубная боль, обычная простуда и головные боли.",
                "Может возникнуть расстройство желудка и изжога.", "Перед использованием этого лекарства проконсультируйтесь со своим врачом или фармацевтом, если у вас есть: кровотечение/нарушения свертываемости крови (такие как гемофилия, дефицит витамина К, низкое количество тромбоцитов).",
                "Некоторые продукты, которые могут взаимодействовать с этим препаратом, включают: ацетазоламид, другие \"разжижители крови\" (такие как варфарин, гепарин), кортикостероиды (такие как преднизолон), дихлорфенамид, растительные препараты (такие как гинкго билоба), кеторолак, метотрексат, мифепристон, вальпроевая кислота.",
                "Симптомы передозировки могут включать: жгучую боль в горле/желудке, спутанность сознания, изменения психики/настроения, обморок, слабость, звон в ушах, лихорадку, учащенное дыхание, изменение количества мочи, судороги, потерю сознания.");
        assertTrue(check);
    }

    // негативный сценарий для метода AddSection
    @Test
    public void negativeAddSection(){
        boolean check = dataProvider.addSections(null, null, null, null, null, null);
        assertFalse(check);
    }
}
