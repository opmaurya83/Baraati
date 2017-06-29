package com.nectarbits.baraati.Interface;

import com.nectarbits.baraati.Models.InquiryModel.List.MyInqiry;
import com.nectarbits.baraati.Models.OrderList.List.OwnOrder;

import java.util.List;

/**
 * Created by nectarbits on 23/08/16.
 */
public interface OnPersonalOrderLoadedListener {

    void onPersonalOrderLoadedListener(List<OwnOrder> subCategories);

}
