package com.appsdeveloperblog.app.ws.configuration;

import io.swagger.jaxrs.config.BeanConfig;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class SwaggerConfigurationServlet extends HttpServlet {

    private static final long serialVersionUID = -5003506539160213880L;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setBasePath("/SwaggerMobileAppWs/api/v1");
        beanConfig.setHost("localhost:9090");
        beanConfig.setTitle("Swagger Mobile App Web Services Documentation");
        beanConfig.setResourcePackage("com.appsdeveloperblog.app.ws.ui.entrypoints");
        beanConfig.setPrettyPrint(true);
        beanConfig.setScan(true);
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setVersion("1.0");
    }
}
