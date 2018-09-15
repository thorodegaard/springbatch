package com.thor.spring.batch.fileconvert;

import com.thor.model.ValueObject;
import org.springframework.batch.item.ItemProcessor;

import java.util.Objects;

public class FileConvertValueObjectProcessor implements ItemProcessor<ValueObject, ValueObject> {

    @Override
    public ValueObject process(ValueObject valueObject) throws Exception {
        valueObject.setValue(new StringBuilder(Objects.toString(valueObject.getValue())).reverse().toString());
        System.out.println("Processing Item= " + valueObject);
        return valueObject;
    }
}
