package com.thor.spring.batch.fileconvert;

import com.thor.model.ValueObject;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class ValueObjectMapper implements FieldSetMapper<ValueObject> {

    static ValueObject valueObject;

    public ValueObject mapFieldSet(FieldSet fieldSetObj) throws BindException {

        valueObject = new ValueObject();
        valueObject.setId(fieldSetObj.readLong(0));
        valueObject.setValue(fieldSetObj.readString(1));

        return valueObject;
    }
}
