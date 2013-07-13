package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.List;
import java.util.Map;

//This class loads Param Desriptions
// Ex : http://localhost:8080/restful/domain-types/objstore.jdo.todo.ToDoItemsJdo/actions/newToDo/params/Description
public class ParamDescription extends JsonRepr{
    private String id;
    private String name;
    private int number;
    private boolean optional;
    private Map<String, String> extensions;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public boolean isOptional() {
        return optional;
    }
    public void setOptional(boolean optional) {
        this.optional = optional;
    }
    public Map<String, String> getExtensions() {
        return extensions;
    }
    public void setExtensions(Map<String, String> extensions) {
        this.extensions = extensions;
    }
    public List<Link> getLinks() {
        return links;
    }
    public void setLinks(List<Link> links) {
        this.links = links;
    }
    
   
    
}
