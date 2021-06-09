package by.sadko.training.service;

import by.sadko.training.AppContext;
import by.sadko.training.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;

public class ProductServiceTest {

    private static final Logger LOGGER = LogManager.getLogger(ProductServiceTest.class);

    private static final AppContext CONTEXT = AppContext.getInstance();
    private static ProductService productService;

    @BeforeClass
    public static void setUp() {

        CONTEXT.initialize();
        productService = CONTEXT.getBean(ProductService.class);
    }

    @AfterClass
    public static void destroy() {

        CONTEXT.destroy();
    }

    @Test
    public void shouldCalculateTotalCost() throws DAOException {

        BigDecimal totalCost = productService.calculateProductCost(1L);
        BigDecimal startingCost = new BigDecimal(0);
        LOGGER.info("Total cost of the product is {}", totalCost);

        Assert.assertTrue(totalCost.compareTo(startingCost) > 0);
    }

    @Test
    public void shouldCalculateProcessTime() throws DAOException {

        double readinessTime = productService.calculateProcessMinutes(1L);
        LOGGER.info("The readiness time of the product is {} hours", readinessTime);

        Assert.assertTrue(readinessTime > 0);
    }
}
