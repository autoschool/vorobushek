package ru.qatools.school.vorobushek;


import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;
import ru.qatools.school.vorobushek.service.DatabaseProvider;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;


/**
 * eroshenkoam
 * 15/11/14
 */
public class Server extends ResourceConfig {

    private static final long DEFAULT_TIMEOUT = 30;

    public Server() {
        register(FreemarkerMvcFeature.class);

        register(new DynamicFeature() {
            @Override
            public void configure(ResourceInfo resourceInfo, FeatureContext context) {
                context.register(DatabaseProvider.class);
            }
        });

        packages(Server.class.getPackage().getName());


    }


}
