
package com.nectarbits.baraati.Models.Address;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("shippingAddress")
    @Expose
    private List<ShippingAddress> shippingAddress = new ArrayList<ShippingAddress>();

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The shippingAddress
     */
    public List<ShippingAddress> getShippingAddress() {
        return shippingAddress;
    }

    /**
     * 
     * @param shippingAddress
     *     The shippingAddress
     */
    public void setShippingAddress(List<ShippingAddress> shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

}
