package by.sadko.training.validation.impl;


import by.sadko.training.exception.ValidationException;
import by.sadko.training.validation.BrokenField;
import by.sadko.training.validation.FieldValidator;
import by.sadko.training.validation.MaxLength;

import java.lang.reflect.Field;

public class MaxLengthFieldValidator implements FieldValidator {
    @Override
    public BrokenField validate(Object entity, Field field) {

        if (String.class.equals(field.getType())) {
            MaxLength maxLength = field.getAnnotation(MaxLength.class);
            try {
                String fieldValue = (String) field.get(entity);
                if (fieldValue != null && fieldValue.trim().length() > maxLength.value()) {

                    return new BrokenField(field.getName(), fieldValue, "maxLength", maxLength.value());
                }
            } catch (IllegalAccessException e) {
                throw new ValidationException(e);
            }
        }
        return null;
    }
}
