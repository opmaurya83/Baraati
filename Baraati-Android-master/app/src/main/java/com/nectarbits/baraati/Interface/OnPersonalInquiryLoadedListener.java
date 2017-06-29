package com.nectarbits.baraati.Interface;

import com.nectarbits.baraati.Models.InquiryModel.List.MyInqiry;
import com.nectarbits.baraati.Models.UserEvent.SubCategory;

import java.util.List;

/**
 * Created by nectarbits on 23/08/16.
 */
public interface OnPersonalInquiryLoadedListener {

    void onPersonalInquiryLoadedListener(List<MyInqiry> subCategories);

}
