package com.thor.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

@XmlRootElement(name = "record")
public class ValueObject implements Serializable {

    private Long id;
    private String value;

    public ValueObject() {

    }

    public ValueObject(Long id, String value) {
        this.id = id;
        this.value = value;
    }

    @XmlElement(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ID: " + Objects.toString(id) + ", value: " + Objects.toString(value);
    }
}
