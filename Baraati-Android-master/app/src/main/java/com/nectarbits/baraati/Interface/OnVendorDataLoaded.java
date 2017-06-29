package com.nectarbits.baraati.Interface;

import com.nectarbits.baraati.Models.Vendor.VendorDetail;
import com.nectarbits.baraati.Models.Vendor.VendorList;
import com.nectarbits.baraati.Models.VendorBazaar.VendorBazaarModel;

import java.util.List;

/**
 * Created by root on 7/9/16.
 */
public interface OnVendorDataLoaded {
    public void OnDataLoaded(VendorBazaarModel vendorList);
}
