package com.dimuthuupeksha.viewer.android.applib.representation;

public class ArgumentNodeForLinkRepresentation extends GenericRepresentation{
    
    public HrefRepr value;

    public class HrefRepr extends JsonRepresentation
    {
        private String href;

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }
        
        
    }

    /// <summary>
    /// Populated by server if invalid request was submitted.
    /// </summary>
    private String invalidReason;

    public HrefRepr getValue() {
        return value;
    }

    public void setValue(HrefRepr value) {
        this.value = value;
    }

    public String getInvalidReason() {
        return invalidReason;
    }

    public void setInvalidReason(String invalidReason) {
        this.invalidReason = invalidReason;
    }
    
}
