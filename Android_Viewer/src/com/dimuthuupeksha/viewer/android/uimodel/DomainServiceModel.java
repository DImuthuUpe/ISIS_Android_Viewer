package com.dimuthuupeksha.viewer.android.uimodel;

import com.dimuthuupeksha.viewer.android.applib.ROClient;
import com.dimuthuupeksha.viewer.android.applib.representation.ServiceRepresentation;

public class DomainServiceModel {
    private String title;
    private String servceId;
    private String url;
    
    
    
    public static DomainServiceModel getByURL(String url){
        ROClient client = ROClient.getInstance();
        ServiceRepresentation service= client.get(ServiceRepresentation.class, url , null);
        DomainServiceModel serviceModel = new DomainServiceModel();
        serviceModel.setServceId(service.getServiceId());
        serviceModel.setTitle(service.getTitle());
        serviceModel.setUrl(service.getLinks().get(0).getHref());
        return serviceModel;
    }



    public String getUrl() {
        return url;
    }



    public void setUrl(String url) {
        this.url = url;
    }



    public String getTitle() {
        return title;
    }



    public void setTitle(String title) {
        this.title = title;
    }



    public String getServceId() {
        return servceId;
    }



    public void setServceId(String servceId) {
        this.servceId = servceId;
    }
    
    
}
