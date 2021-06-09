package by.sadko.training.listener;

import by.sadko.training.AppContext;
import by.sadko.training.SecurityContext;
import by.sadko.training.service.ContractExecutor;
import by.sadko.training.service.ContractService;
import by.sadko.training.service.ProductService;
import by.sadko.training.util.AppConstants;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Application listener
 *
 * @author Sadko Kiryl
 * @version 1.0
 * @see ServletContextListener,AppContext
 */
public class AppListener implements ServletContextListener {

    /**
     * Initializing application context
     *
     * @param sce - servlet context event
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        //SecurityContext initialization
        SecurityContext securityContext = SecurityContext.getInstance();
        sce.getServletContext().setAttribute(AppConstants.SECURITY_CONTEXT, securityContext);

        //AppContext initialization
        AppContext.getInstance().initialize();

        //ContractExecutor initialization
        AppContext appContext = AppContext.getInstance();
        ProductService productService = appContext.getBean(ProductService.class);
        ContractService contractService = appContext.getBean(ContractService.class);
        ContractExecutor.getInstance().initialize(contractService, productService, 10);
    }

    /**
     * Destroying application context
     *
     * @param sce - servlet context event
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        ContractExecutor.getInstance().destroy();
        AppContext.getInstance().destroy();
    }
}
