package com.dimuthuupeksha.viewer.android.uimodel;

import java.util.ArrayList;
import java.util.List;

import com.dimuthuupeksha.viewer.android.applib.ROClient;
import com.dimuthuupeksha.viewer.android.applib.representation.HomepageRepresentation;
import com.dimuthuupeksha.viewer.android.applib.representation.LinkRepresentation;
import com.dimuthuupeksha.viewer.android.applib.representation.ListRepresentation;
import com.dimuthuupeksha.viewer.android.applib.representation.ServicesRepresentation;

public class HomePageModel {
    //private String[] 
    private List<DomainServiceModel> services;
    public static HomePageModel getByDefault(){
        ROClient client = ROClient .getInstance();
        HomepageRepresentation homePage=  client.homePage();
        List<LinkRepresentation> links = homePage.getLinks();
        LinkRepresentation domainServiceLink = null;
        for(int i=0;i<links.size();i++){
            if(links.get(i).getRel().equals("urn:org.restfulobjects:rels/services")){
                domainServiceLink = links.get(i);
                break;
            }
        }
        if(domainServiceLink!=null){
            ServicesRepresentation services= client.get(ServicesRepresentation.class, domainServiceLink.getHref(), null);
            List<LinkRepresentation> serviceList = services.getValue();
            List<DomainServiceModel> domainServiceModels = new ArrayList<DomainServiceModel>();
            for (LinkRepresentation linkRepresentation : serviceList) {
                System.out.println(linkRepresentation.getHref());
                domainServiceModels.add(DomainServiceModel.getByURL(linkRepresentation.getHref()));
            }
            HomePageModel pageModel = new HomePageModel();
            pageModel.setServices(domainServiceModels);
            return pageModel;
        }
        return null;
    }
    public List<DomainServiceModel> getServices() {
        return services;
    }
    public void setServices(List<DomainServiceModel> services) {
        this.services = services;
    }
    
}
