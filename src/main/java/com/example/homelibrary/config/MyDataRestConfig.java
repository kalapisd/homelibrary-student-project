
package com.example.homelibrary.config;

import com.example.homelibrary.entity.Author;
import com.example.homelibrary.entity.Book;
import com.example.homelibrary.entity.Genre;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;


/**
 * Future role: Responsible for restricting HTTP methods for non-admin users
 * This function does not work, as security functions are not yet implemented
 */

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {


    private String theAllowedOrigins = "http://localhost:3000";


    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config,
                                                     CorsRegistry cors) {
        HttpMethod[] theUnsupportedActions = {
                HttpMethod.POST,
                HttpMethod.DELETE,
                HttpMethod.PUT};


        config.exposeIdsFor(Book.class);
        config.exposeIdsFor(Author.class);
        config.exposeIdsFor(Genre.class);

        disableHttpMethods(Book.class, config, theUnsupportedActions);
        disableHttpMethods(Author.class, config, theUnsupportedActions);
        disableHttpMethods(Genre.class, config, theUnsupportedActions);



        /* Configure CORS Mapping */
        cors.addMapping(config.getBasePath() + "/**")
                .allowedOrigins(theAllowedOrigins);
    }


    private void disableHttpMethods(Class theClass,
                                    RepositoryRestConfiguration config,
                                    HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metdata, httpMethods) ->
                        httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metdata, httpMethods) ->
                        httpMethods.disable(theUnsupportedActions));
    }

}

