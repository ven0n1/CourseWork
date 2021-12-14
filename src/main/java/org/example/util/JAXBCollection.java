package org.example.util;

import javax.xml.bind.annotation.XmlAnyElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JAXBCollection<T> {
    @XmlAnyElement(lax = true)
    private List<T> items;

    public JAXBCollection(Collection<T> contents) {
        if (contents instanceof List) {
            items = (List<T>) contents;
        }
        else {
            items = new ArrayList<T>(contents);
        }
    }

    public JAXBCollection() {
        this(new ArrayList<T>());
    }

    public List<T> getItems() {
        return items;
    }

}
