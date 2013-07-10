package com.dimuthuupeksha.viewer.android.uimodel;

import com.dimuthuupeksha.viewer.android.applib.representation.Homepage;
import com.dimuthuupeksha.viewer.android.applib.representation.Services;

public class Model {

    static Model instance;
    static final Object lock = new Object();
    /* The Model */
    private Homepage homePage;
    private Services services;
    
    public static Model getInstance() {
        synchronized (lock) {
            if (instance == null) {
                instance = new Model();
            }
            return instance;
        }
    }

    public Homepage getHomePage() {
        return homePage;
    }

    public void setHomePage(Homepage homePage) {
        this.homePage = homePage;
    }

    public Services getServices() {
        return services;
    }

    public void setServices(Services services) {
        this.services = services;
    }
    
    
}
