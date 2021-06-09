package by.sadko.training.validation.impl;


import by.sadko.training.exception.ValidationException;
import by.sadko.training.validation.BrokenField;
import by.sadko.training.validation.FieldValidator;
import by.sadko.training.validation.MinLength;

import java.lang.reflect.Field;

public class MinLengthFieldValidator implements FieldValidator {
    @Override
    public BrokenField validate(Object entity, Field field) {

        if (String.class.equals(field.getType())) {
            MinLength minLength = field.getAnnotation(MinLength.class);
            try {
                String fieldValue = (String) field.get(entity);
                if (fieldValue != null && fieldValue.trim().length() < minLength.value()) {

                    return new BrokenField(field.getName(), fieldValue, "minLength", minLength.value());
                }
            } catch (IllegalAccessException e) {
                throw new ValidationException(e);
            }
        }
        return null;
    }
}
