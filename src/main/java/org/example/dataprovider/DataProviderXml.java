package org.example.dataprovider;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Constants;
import org.example.HistoryContent;
import org.example.entity.*;
import org.example.util.JAXBCollection;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataProviderXml implements IDataProvider{
    private static final Logger logger = LogManager.getLogger(DataProviderCsv.class);

    public DataProviderXml() {
    }


    @Override
    public boolean createNote(int heartBlood, String bloodPressure, Note.MedicationTime medicationTime,
                              boolean additionalDescription, String[] parameters) {
        Note note;
        note = new Note(heartBlood, bloodPressure, medicationTime);
        boolean isCreated;
        String[] arguments = fillArguments(parameters);
        if (additionalDescription){
            isCreated = specifyAdditionalParameters(note, arguments[0]);
        } else {
            isCreated = specifyStructuredParameters(note, arguments[0], arguments[1], arguments[2], arguments[3]);
        }
        return isCreated;
    }

    @Override
    public boolean specifyAdditionalParameters(Note note, String description) {
        boolean isCreated;
        CustomNote customNote = new CustomNote(note.getHeartRate(), note.getBloodPressure(), note.getMedicationTime(), description);
        List<CustomNote> list = new ArrayList<>();
        if(read(CustomNote.class, Constants.XML_CUSTOM_NOTE).isPresent()){
            list = read(CustomNote.class, Constants.XML_CUSTOM_NOTE).get();
        }
        list.add(customNote);
        isCreated = save(Constants.XML_NOTES, list, Constants.XML_CUSTOM_NOTE);
        IDataProvider.saveHistory(getClass().getName(), HistoryContent.Status.SUCCESS, new Gson().toJson(customNote));
        return isCreated;
    }

    @Override
    public boolean specifyStructuredParameters(Note note, String dyspnea, String sweating, String dizziness, String stateOfHealth) {
        boolean isCreated;
        StructuredNote structuredNote = new StructuredNote(note.getHeartRate(), note.getBloodPressure(),
                note.getMedicationTime(), dyspnea, sweating, dizziness, stateOfHealth);
        List<StructuredNote> list = new ArrayList<>();
        if(read(StructuredNote.class, Constants.XML_STRUCTURED_NOTE).isPresent()){
            list = read(StructuredNote.class, Constants.XML_STRUCTURED_NOTE).get();
        }
        list.add(structuredNote);
        isCreated = save(Constants.XML_NOTES, list, Constants.XML_STRUCTURED_NOTE);
        IDataProvider.saveHistory(getClass().getName(), HistoryContent.Status.SUCCESS, new Gson().toJson(structuredNote));
        return isCreated;
    }

    @Override
    public boolean addMedicine(String name, String form, int date, boolean sectionsOrDescription, String[] parameters) {
        boolean isCreated;
        String dateResult = addDate(date);
        Medicine medicine = new Medicine(name, form, dateResult);
        String[] arguments = fillArguments(parameters);
        if (sectionsOrDescription){
            isCreated = addDescription(medicine, arguments[0]);
        } else {
            isCreated = addSections(medicine, arguments[0], arguments[1], arguments[2], arguments[3], arguments[4]);
        }
        return isCreated;
    }

    @Override
    public String addDate(int date) {
        String pattern = Constants.MEDICINE_DATE_PATTERN;
        DateFormat df = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, date);
        Date expirationDate = calendar.getTime();
        return df.format(expirationDate);
    }

    @Override
    public boolean addDescription(Medicine medicine, String description) {
        boolean isCreated;
        CustomMedicine customMedicine = new CustomMedicine(medicine.getName(), medicine.getForm(), medicine.getDate(), description);
        List<CustomMedicine> list = new ArrayList<>();
        if(read(CustomMedicine.class, Constants.XML_CUSTOM_MEDICINE).isPresent()){
            list = read(CustomMedicine.class, Constants.XML_CUSTOM_MEDICINE).get();
        }
        list.add(customMedicine);
        isCreated = save(Constants.XML_MEDICINES, list, Constants.XML_CUSTOM_MEDICINE);
        IDataProvider.saveHistory(getClass().getName(), HistoryContent.Status.SUCCESS, new Gson().toJson(customMedicine));
        return isCreated;
    }

    @Override
    public boolean addSections(Medicine medicine, String uses, String sideEffect, String precautions, String interaction, String overdose) {
        boolean isCreated;
        StructuredMedicine structuredMedicine = new StructuredMedicine(medicine.getName(), medicine.getForm(),
                medicine.getDate(), uses, sideEffect, precautions, interaction, overdose);
        List<StructuredMedicine> medicineList = new ArrayList<>();
        if(read(StructuredMedicine.class, Constants.XML_STRUCTURED_MEDICINE).isPresent()){
            medicineList = read(StructuredMedicine.class, Constants.XML_STRUCTURED_MEDICINE).get();
        }
        medicineList.add(structuredMedicine);
        isCreated = save(Constants.XML_MEDICINES, medicineList, Constants.XML_STRUCTURED_MEDICINE);
        IDataProvider.saveHistory(getClass().getName(), HistoryContent.Status.SUCCESS, new Gson().toJson(structuredMedicine));
        return isCreated;
    }

    public String[] fillArguments(String[] parameters){
        String[] strings = new String[5];
        Arrays.fill(strings, Constants.DEFAULT);
        System.arraycopy(parameters, 0, strings, 0, parameters.length);
        return strings;
    }


    public static <T> boolean save(String rootName, Collection<T> collection, String path) {
        boolean isSaved = false;
        try {
            // Create context with generic type
            JAXBContext jaxbContext = JAXBContext.newInstance(findTypes(collection));
            Marshaller marshaller = jaxbContext.createMarshaller();
            // Create wrapper collection
            JAXBElement collectionElement = createCollectionElement(rootName, collection);
            marshaller.marshal(collectionElement, new File(path));
            isSaved = true;
        } catch (JAXBException e) {
            logger.error(e);
        }
        return isSaved;
    }


    public static <T> Optional<List<T>> read(Class<T> tClass, String path) {
        Optional<List<T>> optional = Optional.empty();
        File file = new File(path);
        file.getParentFile().mkdirs();
        try {
            if(file.createNewFile()){
                logger.info(Constants.INFO_CREATE_FILE);
            }
        } catch (IOException e) {
            logger.error(e);
        }
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(JAXBCollection.class, tClass);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            JAXBCollection<T> collection = unmarshaller.unmarshal(new StreamSource(new File(path)), JAXBCollection.class).getValue();
            optional = Optional.of(collection.getItems());
        } catch (JAXBException e) {
            logger.error(e);
        }
        return optional;
    }

    protected static <T> Class[] findTypes(Collection<T> collection) {
        Set<Class> types = new HashSet<Class>();
        types.add(JAXBCollection.class);
        for (T object : collection) {
            if (object != null) {
                types.add(object.getClass());
            }
        }
        return types.toArray(new Class[0]);
    }

    /**
     * создает JAXBElement, который содержит JAXBCollection. Нужен для
     * marshall для generic коллекции без отдельного класса-оболочки
     *
     * @param rootName Имя корневого элемента в Xml
     * @param tCollection List для сохранения
     * @return JAXBElement, содержащий нашу Collection, обернутую в JAXBCollection.
     */
    protected static <T> JAXBElement createCollectionElement(String rootName, Collection<T> tCollection) {
        JAXBCollection collection = new JAXBCollection(tCollection);
        return new JAXBElement<JAXBCollection>(new QName(rootName), JAXBCollection.class, collection);
    }

}
