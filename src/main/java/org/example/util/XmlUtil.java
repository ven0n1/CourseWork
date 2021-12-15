package org.example.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Constants;
import org.example.entity.JAXBCollection;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class XmlUtil {
    private static final Logger logger = LogManager.getLogger(XmlUtil.class);

    /**
     * Сохранение List объектов в Xml
     * @param rootName название тега-обертки
     * @param collection List объектов
     * @param path путь к файлу
     * @param <T> generic для возможности сохранения любого entity
     * @return резултьтат сохранения
     */
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


    /**
     * Чтение объектов из файла
     * @param tClass класс, в который надо считать файл
     * @param path путь, откуда надо считать файл
     * @param <T> generic для возможности считывания в любой класс
     * @return List объектов определенного типа
     */
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

    /**
     * Определяет все классы в переданной коллекции.
     * Нужен для вызова marshal
     * @param collection коллекция, которую нужно просканировать
     * @return все классы, найденные в коллекции. В том числе JAXBCollection
     */
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
     * вызова marshal для generic коллекции без отдельного класса-оболочки
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
