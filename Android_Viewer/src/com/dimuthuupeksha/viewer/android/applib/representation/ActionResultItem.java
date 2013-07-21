package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonIgnore;

public class ActionResultItem extends JsonRepr{
    private JsonNode value;//for lists and scalars
    private Map<String, JsonNode> extensions;//for lists,object
    private String title;//for object
    private String domainType;//for object
    private String instanceId;//for object
    private Map<String, ObjectMember> members;//for object
    
    @JsonIgnore
    public List<Link> getValueAsList(){
        List<Link> linksList   = new ArrayList<Link>();
        if(value.isArray()){
            for(int i=0;i<value.size();i++){
                Link link = JsonRepr.fromString(Link.class, value.get(i).toString());
                linksList.add(link);
            }
        }
        
        return linksList;
    }
    
    public JsonNode getValue() {
        return value;
    }
    public void setValue(JsonNode value) {
        this.value = value;
    }
    public Map<String, JsonNode> getExtensions() {
        return extensions;
    }
    public void setExtensions(Map<String, JsonNode> extensions) {
        this.extensions = extensions;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDomainType() {
        return domainType;
    }
    public void setDomainType(String domainType) {
        this.domainType = domainType;
    }
    public String getInstanceId() {
        return instanceId;
    }
    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }
    public Map<String, ObjectMember> getMembers() {
        return members;
    }
    public void setMembers(Map<String, ObjectMember> members) {
        this.members = members;
    }
    
    
}
