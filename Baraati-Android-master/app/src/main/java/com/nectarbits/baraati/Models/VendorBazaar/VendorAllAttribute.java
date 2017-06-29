
package com.nectarbits.baraati.Models.VendorBazaar;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VendorAllAttribute {

    @SerializedName("attribute_name")
    @Expose
    private String attributeName;
    @SerializedName("value")
    @Expose
    private List<String> value = new ArrayList<String>();

    /**
     * 
     * @return
     *     The attributeName
     */
    public String getAttributeName() {
        return attributeName;
    }

    /**
     * 
     * @param attributeName
     *     The attribute_name
     */
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    /**
     * 
     * @return
     *     The value
     */
    public List<String> getValue() {
        return value;
    }

    /**
     * 
     * @param value
     *     The value
     */
    public void setValue(List<String> value) {
        this.value = value;
    }

}
