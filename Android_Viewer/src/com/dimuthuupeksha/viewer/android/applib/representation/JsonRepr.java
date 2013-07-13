package com.dimuthuupeksha.viewer.android.applib.representation;

import java.io.Serializable;
import java.util.List;

public class JsonRepr implements Serializable{
    
    protected List<Link> links;
    
    public static <T> T FromString(String str){
        return null;
    }
    private static <T> T AsT(Object list){
        return (T) list;
    }

    public String AsJson()
    {
        return "";
    }
    
    public Link getLinkByRel(String rel){
        if(links==null){
            return null;
        }else{
            for(Link link:links){
                if(link.getRel().replace("urn:org.restfulobjects:rels/", "").trim().equals(rel)){
                    return link;
                }
            }
        }
        return null;
    }

}
