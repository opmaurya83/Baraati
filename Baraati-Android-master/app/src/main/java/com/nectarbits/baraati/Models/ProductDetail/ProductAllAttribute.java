
package com.nectarbits.baraati.Models.ProductDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductAllAttribute {

    @SerializedName("attribute_name")
    @Expose
    private String attributeName;
    @SerializedName("atrribute")
    @Expose
    private String atrribute;

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
     *     The atrribute
     */
    public String getAtrribute() {
        return atrribute;
    }

    /**
     * 
     * @param atrribute
     *     The atrribute
     */
    public void setAtrribute(String atrribute) {
        this.atrribute = atrribute;
    }

}
