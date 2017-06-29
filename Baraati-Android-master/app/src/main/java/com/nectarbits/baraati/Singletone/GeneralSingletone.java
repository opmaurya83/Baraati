package com.nectarbits.baraati.Singletone;

import com.nectarbits.baraati.Models.VendorDetail.Moreimage;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for general purpose for temporary data store.
 * Created by root on 9/9/16.
 */
public class GeneralSingletone {
    Boolean isVendor=false;
    public List<Moreimage> getMoreimageList() {
        return moreimageList;
    }

    public void setMoreimageList(List<Moreimage> moreimageList) {
        this.moreimageList = moreimageList;
    }

    List<Moreimage> moreimageList=new ArrayList<>();
    List<String> chatImageList=new ArrayList<>();
    List<com.nectarbits.baraati.Models.ProductDetail.Moreimage> moreimageList_product=new ArrayList<>();
    private static GeneralSingletone ourInstance = new GeneralSingletone();

    public static GeneralSingletone getInstance() {
        return ourInstance;
    }

    private GeneralSingletone() {
    }

    public Boolean getVendor() {
        return isVendor;
    }

    public void setVendor(Boolean vendor) {
        isVendor = vendor;
    }

    public List<com.nectarbits.baraati.Models.ProductDetail.Moreimage> getMoreimageList_product() {
        return moreimageList_product;
    }

    public void setMoreimageList_product(List<com.nectarbits.baraati.Models.ProductDetail.Moreimage> moreimageList_product) {
        this.moreimageList_product = moreimageList_product;
    }

    public List<String> getChatImageList() {
        return chatImageList;
    }

    public void setChatImageList(List<String> chatImageList) {
        this.chatImageList = chatImageList;
    }
}
