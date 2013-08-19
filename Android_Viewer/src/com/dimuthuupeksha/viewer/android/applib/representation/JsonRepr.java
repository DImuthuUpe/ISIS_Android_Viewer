package com.dimuthuupeksha.viewer.android.applib.representation;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonRepr implements Serializable {

    protected List<Link> links;

    public static <T> T fromString(Class<T> t, String str) {
        JsonParser jp;
        try {
            jp = new JsonFactory().createJsonParser(str);
            ObjectMapper objectMapper = new ObjectMapper();
            T res = objectMapper.readValue(jp, t);
            return res;
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String AsJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String data = mapper.writeValueAsString(this);
            return data;
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Link getLinkByRel(String rel) {
        if (links == null) {
            return null;
        } else {
            for (Link link : links) {
                if (link.getRel().replace("urn:org.restfulobjects:rels/", "").trim().equals(rel)) {
                    return link;
                }
            }
        }
        return null;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

}
