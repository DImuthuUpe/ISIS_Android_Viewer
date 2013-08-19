package com.dimuthuupeksha.viewer.android.applib.representation;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;

public class DomainType extends JsonRepr {
    private String canonicalName;
    private List<Link> members;
    private List<Link> typeActions;
    private Map<String, JsonNode> extensions;

    public String getCanonicalName() {
        return canonicalName;
    }

    public void setCanonicalName(String canonicalName) {
        this.canonicalName = canonicalName;
    }

    public List<Link> getMembers() {
        return members;
    }

    public void setMembers(List<Link> members) {
        this.members = members;
    }

    public List<Link> getTypeActions() {
        return typeActions;
    }

    public void setTypeActions(List<Link> typeActions) {
        this.typeActions = typeActions;
    }

    public Map<String, JsonNode> getExtensions() {
        return extensions;
    }

    public void setExtensions(Map<String, JsonNode> extensions) {
        this.extensions = extensions;
    }

}
