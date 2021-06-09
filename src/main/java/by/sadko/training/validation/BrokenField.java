package by.sadko.training.validation;

public class BrokenField {

    private final String fieldName;
    private final Object fieldValue;
    private final String violatedRule;
    private final Object[] args;

    public BrokenField(String fieldName, Object fieldValue, String violatedRule, Object... args) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.violatedRule = violatedRule;
        this.args = args;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public String getViolatedRule() {
        return violatedRule;
    }

    public Object[] getArgs() {
        return args;
    }
}
