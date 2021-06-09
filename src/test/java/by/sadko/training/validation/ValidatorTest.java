package by.sadko.training.validation;

import by.sadko.training.entity.UserAccount;
import by.sadko.training.validation.impl.AnnotationBasedBeanValidator;
import by.sadko.training.validation.impl.EmailFieldValidator;
import by.sadko.training.validation.impl.MaxLengthFieldValidator;
import by.sadko.training.validation.impl.MinLengthFieldValidator;
import by.sadko.training.validation.impl.PasswordFieldValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidatorTest {

    private static final Logger LOGGER = LogManager.getLogger(ValidatorTest.class);
    private BeanValidator beanValidator;

    @Before
    public void initValidator() {

        Map<Class<? extends Annotation>, FieldValidator> validatorMap = new HashMap<>();
        validatorMap.put(MaxLength.class, new MaxLengthFieldValidator());
        validatorMap.put(MinLength.class, new MinLengthFieldValidator());
        validatorMap.put(Email.class, new EmailFieldValidator());
        validatorMap.put(Password.class, new PasswordFieldValidator());
        beanValidator = new AnnotationBasedBeanValidator(validatorMap);
    }

    @Test
    public void shouldFailValidationOnEmailField() {

        UserAccount userAccount = new UserAccount();
        userAccount.setName("John");
        userAccount.setEmail("John@.com");
        userAccount.setPassword("John1234");

        ValidationResult result = beanValidator.validate(userAccount);
        Assert.assertNotNull(result);
        List<BrokenField> brokenFields = result.getBrokenFields();

        Assert.assertEquals(1, brokenFields.size());

        BrokenField brokenField = brokenFields.get(0);
        LOGGER.info("Broken field is {}", brokenField.getFieldName());

        Assert.assertEquals("email", brokenField.getViolatedRule());
        Assert.assertEquals("John@.com", brokenField.getFieldValue());
        Assert.assertEquals("email", brokenField.getFieldName());
    }

    @Test
    public void shouldFailValidationOnNameField() {

        UserAccount userAccount = new UserAccount();
        userAccount.setName("Jon");
        userAccount.setEmail("John@gmail.com");
        userAccount.setPassword("John1234");

        ValidationResult result = beanValidator.validate(userAccount);
        Assert.assertNotNull(result);
        List<BrokenField> brokenFields = result.getBrokenFields();

        Assert.assertEquals(1, brokenFields.size());

        BrokenField brokenField = brokenFields.get(0);
        LOGGER.info("Broken field is {}", brokenField.getFieldName());

        Assert.assertEquals("minLength", brokenField.getViolatedRule());
        Assert.assertEquals("Jon", brokenField.getFieldValue());
        Assert.assertEquals("name", brokenField.getFieldName());
    }

    @Test
    public void shouldFailValidationOnPasswordField() {

        UserAccount userAccount = new UserAccount();
        userAccount.setName("John");
        userAccount.setEmail("John@gmail.com");
        userAccount.setPassword("john123");

        ValidationResult result = beanValidator.validate(userAccount);
        Assert.assertNotNull(result);
        List<BrokenField> brokenFields = result.getBrokenFields();

        Assert.assertEquals(1, brokenFields.size());

        BrokenField brokenField = brokenFields.get(0);
        LOGGER.info("Broken field is {}", brokenField.getFieldName());

        Assert.assertEquals("password", brokenField.getViolatedRule());
        Assert.assertEquals("john123", brokenField.getFieldValue());
        Assert.assertEquals("password", brokenField.getFieldName());
    }
}
