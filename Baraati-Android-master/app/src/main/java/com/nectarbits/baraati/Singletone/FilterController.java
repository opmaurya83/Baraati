package com.nectarbits.baraati.Singletone;

import com.nectarbits.baraati.Models.VendorBazaar.ProductAllAttribute;
import com.nectarbits.baraati.Models.VendorBazaar.ProductRangePrice;
import com.nectarbits.baraati.Models.VendorBazaar.VendorAllAttribute;
import com.nectarbits.baraati.Models.filter.FilterListInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nectarbits on 22/06/16.
 */
public class FilterController {
    private static FilterController ourInstance = new FilterController();

    public static FilterController getInstance() {
        return ourInstance;
    }

    private FilterController() {
    }

    private List<ProductAllAttribute> productAllAttributes;
    private List<VendorAllAttribute> vendorAllAttributes;
    private List<ProductRangePrice> rangePriceArrayList;
    private List<FilterListInfo> filterListInfoList_Product=new ArrayList<>();
    private List<FilterListInfo> filterListInfoList_Vendor=new ArrayList<>();

    public List<ProductAllAttribute> getProductAllAttributes() {
        return productAllAttributes;
    }

    public void setProductAllAttributes(List<ProductAllAttribute> productAllAttributes) {
        this.productAllAttributes = productAllAttributes;
    }

    public static FilterController getOurInstance() {
        return ourInstance;
    }

    public static void setOurInstance(FilterController ourInstance) {
        FilterController.ourInstance = ourInstance;
    }

    public List<ProductRangePrice> getRangePriceArrayList() {
        return rangePriceArrayList;
    }

    public void setRangePriceArrayList(List<ProductRangePrice> rangePriceArrayList) {
        this.rangePriceArrayList = rangePriceArrayList;
    }

    public List<VendorAllAttribute> getVendorAllAttributes() {
        return vendorAllAttributes;
    }

    public void setVendorAllAttributes(List<VendorAllAttribute> vendorAllAttributes) {
        this.vendorAllAttributes = vendorAllAttributes;
    }

    public List<FilterListInfo> getFilterListInfoList_Product() {
        return filterListInfoList_Product;
    }

    public void setFilterListInfoList_Product(List<FilterListInfo> filterListInfoList_Product) {
        this.filterListInfoList_Product = filterListInfoList_Product;
    }

    public List<FilterListInfo> getFilterListInfoList_Vendor() {
        return filterListInfoList_Vendor;
    }

    public void setFilterListInfoList_Vendor(List<FilterListInfo> filterListInfoList_Vendor) {
        this.filterListInfoList_Vendor = filterListInfoList_Vendor;
    }
}

