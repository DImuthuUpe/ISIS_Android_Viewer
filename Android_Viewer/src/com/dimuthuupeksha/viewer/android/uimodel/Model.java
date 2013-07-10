package com.dimuthuupeksha.viewer.android.uimodel;

import com.dimuthuupeksha.viewer.android.applib.representation.HomepageRepresentation;
import com.dimuthuupeksha.viewer.android.applib.representation.ServicesRepresentation;

public class Model {

    static Model instance;
    static final Object lock = new Object();
    /* The Model */
    private HomepageRepresentation homePage;
    private ServicesRepresentation services;
    
    public static Model getInstance() {
        synchronized (lock) {
            if (instance == null) {
                instance = new Model();
            }
            return instance;
        }
    }

    public HomepageRepresentation getHomePage() {
        return homePage;
    }

    public void setHomePage(HomepageRepresentation homePage) {
        this.homePage = homePage;
    }

    public ServicesRepresentation getServices() {
        return services;
    }

    public void setServices(ServicesRepresentation services) {
        this.services = services;
    }
    
    
}
