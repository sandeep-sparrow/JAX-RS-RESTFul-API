package com.appsdeveloperblog.app.ws;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/*
 *
 * @author: Sandeep prajapati
 *
 */
@ApplicationPath("/api/v1")
public class App extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        HashSet<Class<?>> resources = new HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.appsdeveloperblog.app.ws.ui.entrypoints.UsersEntryPoint.class);
    }
}
